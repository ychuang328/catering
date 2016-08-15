package com.weiwork.common.utils.db;

import redis.clients.jedis.Jedis;

/**
 * 基于jedis递增序列工具类
 * 
 * @author 
 * 
 */
public class JedisPrimarykeyGenerator implements PrimarykeyGenerator {
	private Jedis jedis;

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	@Override
	public Long generateId(String dbName, String tableName) {
		return generateId(PREFIX_DB.concat(dbName).concat(PREFIX_TB).concat(tableName));
	}

	private Long generateId(String key) {
		return jedis.incr(key);
	}
}
