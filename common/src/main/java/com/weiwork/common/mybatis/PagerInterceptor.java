package com.weiwork.common.mybatis;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

import com.weiwork.common.utils.ApplicationUtil;
import com.weiwork.common.utils.db.PrimarykeyGenerator;

@Intercepts({ @Signature(type = Executor.class, method = "select", args = { MappedStatement.class, Map.class }) })
public class PagerInterceptor implements Interceptor {

	private Logger log = LoggerFactory.getLogger(PagerInterceptor.class);

	@SuppressWarnings("unused")
	private Properties properties;

	@Resource
	public PrimarykeyGenerator primarykeyGenerator;

	public PrimarykeyGenerator getPrimarykeyGenerator() {
		return primarykeyGenerator;
	}

	public void setPrimarykeyGenerator(PrimarykeyGenerator primarykeyGenerator) {
		this.primarykeyGenerator = primarykeyGenerator;
	}

	public Properties getProperties() {
		return properties;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		 Object param2 = invocation.getArgs()[1];
		 if(param2 != null){
			 if(!(param2 instanceof Map)){
				 return null;
			 }
			 Map map = (Map)param2;
			 
			 
		 }
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = null;
		if (invocation.getArgs().length > 1) {
			parameter = invocation.getArgs()[1];
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		boolean isInsert = isInsert(boundSql);
		if (!isInsert) {
			return invocation.proceed();
		} else {
			return handleCreate(invocation, boundSql, mappedStatement);
		}
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
						if (!configuration.isUseGeneratedKeys() && "ID".equals(propertyName.toUpperCase())) {
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

	private synchronized long genID(String dbName, String tName) {
		primarykeyGenerator = (PrimarykeyGenerator) ApplicationUtil.getBean("primarykeyGenerator");
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
		this.properties = properties0;
	}
}
