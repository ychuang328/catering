package com.weiwork.common.utils.db;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 基于SpringRedisTemplate递增序列工具类
 * 
 * @author 
 * 
 */
public class SpringRedisTemplatePrimarykeyGenerator implements PrimarykeyGenerator {

	private RedisTemplate<String, Long> redisTemplate;

	public RedisTemplate<String, Long> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Long> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Long generateId(String dbName, String tableName) {
		return generateId(PREFIX_DB.concat(dbName).concat(PREFIX_TB).concat(tableName));
	}

	private Long generateId(final String key) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				@SuppressWarnings("synthetic-access")
				byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
				return connection.incr(keyByte);
			}

		});
	}
}
