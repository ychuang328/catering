package com.weiwork.common;

import junit.framework.Assert;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.weiwork.common.utils.db.JedisPrimarykeyGenerator;

public class JedisPrimeryKeyGeneratorTest {
	
	@Test
	public void testGenerator(){
		Jedis jedis = new Jedis("192.168.1.9",6380);
		JedisPrimarykeyGenerator  generator = new JedisPrimarykeyGenerator();
		generator.setJedis(jedis);
		
		String dbName = "testDB";
		String tbName = "testTable";
		
		long id = generator.generateId(dbName, tbName);
		long id2 = generator.generateId(dbName, tbName);
		Assert.assertEquals(id+1, id2);
	}
	
	public static void main(String[] args) {
		String key = "uni:sso1:f7a27e95202745929644e3b3bf217914";
		Jedis jedis0 = new Jedis("192.168.1.9",6380);
		String value0 = jedis0.get(key);
		Jedis jedis1 = new Jedis("192.168.1.9",6381);
		String value1 = jedis1.get(key);
		Jedis jedis2 = new Jedis("192.168.1.9",6382);
		String value2 = jedis2.get(key);
		Jedis jedis3 = new Jedis("192.168.1.9",6383);
		String value3 = jedis3.get(key);
		System.out.println();
	    
	}
}
