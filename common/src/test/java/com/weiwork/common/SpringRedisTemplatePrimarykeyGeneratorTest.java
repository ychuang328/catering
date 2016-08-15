package com.weiwork.common;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisShardInfo;

import com.weiwork.common.utils.db.SpringRedisTemplatePrimarykeyGenerator;

public class SpringRedisTemplatePrimarykeyGeneratorTest {
	
	@Test
	public void testGenerator(){
		JedisConnectionFactory jf = new JedisConnectionFactory();
		jf.setHostName("192.168.1.9");
		jf.setPort(6379);
		jf.setShardInfo(new JedisShardInfo("192.168.1.9"));
		
		RedisTemplate<String,Long> redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jf);
		redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		
		SpringRedisTemplatePrimarykeyGenerator  generator = new SpringRedisTemplatePrimarykeyGenerator();
		generator.setRedisTemplate(redisTemplate);
		
		String dbName = "testDB";
		String tbName = "testTable";
		
		long id = generator.generateId(dbName, tbName);
		long id2 = generator.generateId(dbName, tbName);
		
		System.out.println(id + " " + id2 );
		Assert.assertEquals(id+1, id2);
	}
}
