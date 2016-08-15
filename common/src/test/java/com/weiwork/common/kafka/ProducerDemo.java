package com.weiwork.common.kafka;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
public class ProducerDemo {
	public static void main(String[] args) {
        int events=1;
        // 设置配置属性
        Properties props = new Properties();
        props.put("metadata.broker.list","192.168.1.9:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        // key.serializer.class默认为serializer.class
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        // 可选配置，如果不配置，则使用默认的partitioner
        props.put("partitioner.class", "cn.vko.kafka.PartitionerDemo");
        // 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
        // 值为0,1,-1,可以参考
        // http://kafka.apache.org/08/configuration.html
        props.put("request.required.acks", "1");
        props.put("producer.type", "async");
        props.put("queue.buffering.max.ms", "5000");
        props.put("queue.buffering.max.messages", "30000");
        props.put("queue.enqueue.timeout.ms", "-1");
        props.put("batch.num.messages", "300");
        ProducerConfig config = new ProducerConfig(props);
        // 创建producer
        Producer<String, String> producer = new Producer<String, String>(config);
        // 产生并发送消息
        long start=System.currentTimeMillis();
        for (long i = 0; i < events; i++) {
            String ip = "192.168.2." + i;//rnd.nextInt(255);
            String msg = "{\"articleId\":2255}";
            //如果topic不存在，则会自动创建，默认replication-factor为1，partitions为0
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("group_article_read_count", ip, msg);
            producer.send(data);
            try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
        // 关闭producer
        producer.close();
    }
}
