package com.weiwork.common.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.vko.component.pageframework.util.StringUtil;

public class Receiver {
	private Logger log = LoggerFactory.getLogger(Receiver.class);
	private String zookeeperConnect;
	private String groupId;
	private String topic;
	private VkoConsumer vkoConsumer;
	
	/**
	 * 创建收件人
	 * @param zookeeperConnect zk集群地址，逗号分隔
	 * @param groupId 组id
	 * @param topic 主题
	 * @param vkoConsumer 处理器
	 */
	public Receiver(String zookeeperConnect, String groupId, String topic,VkoConsumer vkoConsumer) {
		super();
		if(StringUtil.isEmpty(zookeeperConnect)){
			String message = "zookeeperConnect 不可以为空";
			log.error(message);
			throw new RuntimeException(message);
		}
		if(StringUtil.isEmpty(groupId)){
			String message = "groupId 不可以为空";
			log.error(message);
			throw new RuntimeException(message);
		}
		if(StringUtil.isEmpty(topic)){
			String message = "topic 不可以为空";
			log.error(message);
			throw new RuntimeException(message);
		}
		if(vkoConsumer == null){
			String message = "vkoConsumer 不可以为空";
			log.error(message);
			throw new RuntimeException(message);
		}
		this.zookeeperConnect = zookeeperConnect;
		this.groupId = groupId;
		this.topic = topic;
		this.vkoConsumer = vkoConsumer;
		log.info("kafka vkoConsumer 创建完成：groupId：{},topic:{},zookeeperConnect:{}",groupId, topic, zookeeperConnect);
		receive();
	}

	private void receive(){
		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeperConnect);
		props.put("group.id", groupId);
		props.put("zookeeper.session.timeout.ms", "14000");
		props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        //增加下边两个配置为了解决kafka.common.ConsumerRebalanceFailedException can't rebalance after 4 retries异常
        //rebalance.max.retries * rebalance.backoff.ms 的值要大于 zookeeper.session.timeout.ms 的值
        props.put("rebalance.max.retries", "5");
        props.put("rebalance.backoff.ms", "12000");
        ConsumerConfig conf = new ConsumerConfig(props);
		ConsumerConnector cc = Consumer.createJavaConsumerConnector(conf);
		 Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		 // 目前每个topic都是2个分区
	     topicCountMap.put(topic,2);
	     Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = cc.createMessageStreams(topicCountMap);
	        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
	        for (final KafkaStream<byte[], byte[]> stream : streams) {
	        	new Thread(){
	        		public void run(){
	        			ConsumerIterator<byte[], byte[]> it = stream.iterator();
	    		        while(it.hasNext()){
	    		        	String msg = new String(it.next().message());
	    		        	try{
	    		        	 vkoConsumer.dealMsg(msg);
	    		        	}catch(Exception e){
	    		        		log.error("kafka vkoConsumer topic:{} 收到消息：{} 消费异常 xxxxxxxxxxxxxxxxxx", topic, msg,e);
	    		        	}
	    		        	log.info("kafka vkoConsumer topic:{} 收到消息：{}", topic, msg);
	    		        }
	        		}
	        	}.start();
	        	log.info("kafka vkoConsumer 启动完成：groupId：{},topic:{},zookeeperConnect:{}",groupId, topic, zookeeperConnect);
	        }
	        log.info("kafka vkoConsumer 准备接收消息：groupId：{},topic:{},zookeeperConnect:{}",groupId, topic, zookeeperConnect);
	}
}
