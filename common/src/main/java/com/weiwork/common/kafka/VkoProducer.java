package com.weiwork.common.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.commons.lang3.StringUtils;

public class VkoProducer {
//	private Logger log = LoggerFactory.getLogger(VkoProducer.class);
	private String metadataBrokerList;
	private Producer<String, String> producer;

	public VkoProducer(String metadataBrokerList) {
		super();
		if(StringUtils.isEmpty(metadataBrokerList)){
			String message = "metadataBrokerList 不可以为空";
//			log.error(message);
			throw new RuntimeException(message);
		}
		this.metadataBrokerList = metadataBrokerList;
		// 设置配置属性
		Properties props = new Properties();
		props.put("metadata.broker.list", metadataBrokerList);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		props.put("request.required.acks", "1");        
        //props.put("producer.type", "async");
        props.put("queue.buffering.max.ms", "5000");
        props.put("queue.buffering.max.messages", "30000");
        props.put("queue.enqueue.timeout.ms", "-1");
        props.put("batch.num.messages", "1");
		// 可选配置，如果不配置，则使用默认的partitioner
        //props.put("partitioner.class", "cn.vko.kafka.PartitionerDemo");
		// 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
		// 值为0,1,-1,可以参考
		// http://kafka.apache.org/08/configuration.html
		ProducerConfig config = new ProducerConfig(props);
		producer = new Producer<String, String>(config);
	}

	/**
	 * 单条插入队列
	 * @param msg
	 * @param topic 主题
	 * @return
	 */
	public String send(String topic, String msg) {
//		Long start = System.currentTimeMillis();
		KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, msg);
		producer.send(data);
//		log.info("发送消息耗时：{}",System.currentTimeMillis()- start);
		return "ok";
	}
}
