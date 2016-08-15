package com.weiwork.common.mybatis;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weiwork.common.utils.db.IdHelperCenter;
import com.weiwork.common.utils.db.PrimarykeyGenerator;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class GenIdInterceptor implements Interceptor {

	private Logger log = LoggerFactory.getLogger(GenIdInterceptor.class);


	@Resource
	public PrimarykeyGenerator primarykeyGenerator;

	public PrimarykeyGenerator getPrimarykeyGenerator() {
		return primarykeyGenerator;
	}

	public void setPrimarykeyGenerator(PrimarykeyGenerator primarykeyGenerator) {
		this.primarykeyGenerator = primarykeyGenerator;
	}


	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		boolean isInsert = isInsert(boundSql);
		if (!isInsert) {
			return invocation.proceed();
		}
		Object result=null;
		try{
			result = handleCreate(invocation, boundSql, mappedStatement);
		} catch(InvocationTargetException e){
			Throwable te=e.getTargetException();
			System.out.println(te.getClass());
			String dbName=this.getDbName(mappedStatement, invocation);
			String tbName=this.getTName(boundSql);
			this.log.error("插入异常已经捕获,db:{},tb:{},已经触发设置最小值的动作,再试试",dbName,tbName,te);
			IdHelperCenter.INSTANCE.initForErr(dbName, tbName);
		}
		return result;
	}

	public Object handleCreate(Invocation invocation, BoundSql boundSql, MappedStatement mappedStatement)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Configuration configuration = mappedStatement.getConfiguration();
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		//String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				//sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						if (!configuration.isUseGeneratedKeys() && "ID".equals(propertyName.toUpperCase()) && (metaObject.getValue(propertyName) == null || Long.valueOf(String.valueOf(metaObject.getValue(propertyName))).longValue() <= 0) ) {
							metaObject.setValue(propertyName,
									genID(getDbName(mappedStatement, invocation), getTName(boundSql)));
						}
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						if (!configuration.isUseGeneratedKeys()
								&& "ID".equals(propertyName.split("\\.")[1].toUpperCase())) {
							boundSql.setAdditionalParameter(propertyName,
									genID(getDbName(mappedStatement, invocation), getTName(boundSql)));
						}
					}
				}
			}
		}
		return exeInsert(invocation, mappedStatement);
	}

	/**
	 * 获取数据库连接
	*/
	protected Connection getConnection(Transaction transaction, Log statementLog) throws SQLException {
		Connection connection = transaction.getConnection();
		if (statementLog.isDebugEnabled()) {
			return ConnectionLogger.newInstance(connection, statementLog);
		} else {
			return connection;
		}
	}

	public String getTName(BoundSql boundSql) {
		String sql = boundSql.getSql();
		String[] sqls = sql.split("\\(")[0].replaceAll("[\\s]+", " ").split("\\s");
		return sqls[sqls.length - 1].trim();
	}

	public String getDbName(MappedStatement mappedStatement, Invocation invocation) {
		Executor exe = (Executor) invocation.getTarget();
		Connection connection = null;
		String url = "";
		try {
			connection = getConnection(exe.getTransaction(), mappedStatement.getStatementLog());
			url = connection.getMetaData().getURL();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return url.substring((url.lastIndexOf("/") + 1),
				(url.lastIndexOf("?") == -1 ? url.length() : url.lastIndexOf("?")));
	}

	private long genID(String dbName, String tName) {
		
		PrimarykeyGenerator primarykeyGenerator = IdHelperCenter.INSTANCE;
		Long generateId = primarykeyGenerator.generateId(dbName, tName);
		log.info(String.format("===GenerateID-KEY:===[%s_%s:%s]", dbName, tName, generateId));
		return generateId;
	}

	public Object exeInsert(Invocation invocation, MappedStatement insert_statement) throws IllegalAccessException,
			InvocationTargetException {
		Object[] args = invocation.getArgs();
		return invocation.getMethod().invoke(invocation.getTarget(), new Object[] { insert_statement, args[1] });
	}

	public boolean isInsert(BoundSql boundSql) {
		return boundSql.getSql().toUpperCase().indexOf(" INTO ") != -1;
	}

	@SuppressWarnings("unused")
	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}
		}
		return value;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties0) {
	}
}
