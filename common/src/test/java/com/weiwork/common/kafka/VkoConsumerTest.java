package com.weiwork.common.kafka;


public class VkoConsumerTest implements VkoConsumer{
	private String name;
	public VkoConsumerTest(String name) {
		super();
		this.name = name;
	}
	public void dealMsg(String strings) {
		System.out.println("我是："+ name +",消息：" + strings  + " 已经我吃了！！！哈哈哈。。。");
	}
}
