package com.weiwork.common.utils.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;


public final class IdHelper {
	//private Logger logger=LoggerFactory.getLogger(this.getClass());
	private static final Long defaultNum=10l;
	private Long minValue=0l;  		//最小值
	private Long applyNum=defaultNum;  	//一次申请多少个id
	private String initSql;   		//从数据库中读取最大id的sql语句
	private String dbName;   		//数据库名
	private String tbName;  		//数据表名
	private DataSource dataSource;  //数据源实例
	
	public Long getMinValue() {
		return minValue;
	}
	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}
	public String getInitSql() {
		if(initSql==null||"default".equals(initSql)){
			return "select max(id) from "+this.tbName;
		}
		return initSql;
	}
	public void setInitSql(String initSql) {
		this.initSql = initSql;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getTbName() {
		return tbName;
	}
	public void setTbName(String tbName) {
		this.tbName = tbName;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public Long getApplyNum() {
		return applyNum<1?defaultNum:applyNum;
	}
	public void setApplyNum(Long applyNum) {
		this.applyNum = applyNum;
	}
	public Long initId(){
		Long maxId=null;
		Connection conn=null;
		Statement stat=null;
		ResultSet rs=null;
		try{
			conn= getDataSource().getConnection();
			stat=conn.createStatement();
			rs=stat.executeQuery(this.getInitSql());
			if(rs.next()){
				maxId=rs.getLong(1);
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(stat!=null){
					stat.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxId<this.minValue?this.minValue:maxId;
	}
	@Override
	public String toString() {
		return "IdHelpper [minValue=" + minValue + ", initSql=" + initSql
				+ ", dbName=" + dbName + ", tbName=" + tbName + "]";
	}
	
}
