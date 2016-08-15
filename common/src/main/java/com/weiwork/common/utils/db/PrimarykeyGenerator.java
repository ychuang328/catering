package com.weiwork.common.utils.db;

/**
 * 递增序列工具类接口
 * @author 
 *
 */
public interface PrimarykeyGenerator {
	/** 自定义key实际存储时会增加的前缀，最终存入的key是：PREFIX +  自定义key  */
//	public static final String PREFIX = "GET_PRIMERYKEY_OF_TABLE_";
	/** 最终存入的key是：PREFIX_DB + dbName + PREFIX_TB + tableName */
	public static final String PREFIX_DB = "GET_PRIMERYKEY_OF_TABLE_DB_";
	/** 最终存入的key是：PREFIX_DB + dbName + PREFIX_TB + tableName */
	public static final String PREFIX_TB = "_DB_";
	
	/**
	 * 根据数据库名，表名获取递增主键
	 * <br>
	 * 最终存入的key是：PREFIX_DB + dbName + PREFIX_TB + tableName
	 * @param dbName 数据库名
	 * @param tableName 表名
	 * @return 
	 */
	public Long  generateId(String dbName, String tableName);
}
