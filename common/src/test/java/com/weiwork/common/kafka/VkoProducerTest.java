package com.weiwork.common.kafka;


public class VkoProducerTest {
	public static void main(String[] args) throws InterruptedException {
		VkoProducer vkoProducer = new VkoProducer("kafka.server1.vko.cn:9092,kafka.server2.vko.cn:9092");
		for(int i = 0; i <100000; i++){
			vkoProducer.send("zpftesttopic"+(i%10), "我是苹果-"+i);
//			Thread.sleep(500L);
		}
	}
}
