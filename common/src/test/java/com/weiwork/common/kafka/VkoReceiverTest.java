package com.weiwork.common.kafka;


public class VkoReceiverTest {
	public static void main(String[] args) {
//		for(int i = 0; i < 10; i++){
			VkoConsumerTest vct = new VkoConsumerTest("zhangsan");
			new Receiver("192.168.1.8:2181,192.168.1.16:2181,192.168.1.13:2181","zpfgroup" , "group_article_read_count", vct);
//		}
	}
}
