package com.weiwork.common;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.9",6380);
		jedis.sadd("zpfList","zpf1","zpf2","zpf3");
		Set<String> set = jedis.smembers("zpfList");
		for(String str : set){
			System.out.println(str);
		}
	}

}
