package com.weiwork.common.kafka;

import java.io.IOException;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.commons.lang3.StringUtils;
@Deprecated
public class KafkaProducter {
	//生产者
	private  Producer<Integer, String> producer;
	//链接参数
	private  Properties props = new Properties();
	
	private String topic;
	
	public static KafkaProducter newKafkaProducter(String propFile,String topic) {
	    if (StringUtils.isBlank( propFile )) {
	        return new KafkaProducter( topic );
	    }
	    return new KafkaProducter( propFile,topic );
	}

    private KafkaProducter(String propFile,String topic) {
	    try {
            props.load( Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile) );
//	        File f = new File("D:/workspace/common0.0.1/resources/kafka.properties");
//	        props.load( new FileInputStream( f ) );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	    if (StringUtils.isBlank( topic )) {
	        this.topic = props.getProperty( "kafka.topics" );
	    } else {
	        this.topic = topic;
	    }
        producer = new Producer<Integer, String>(new ProducerConfig(props));
    /*	    
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");//对key进行序列化 默认和 serializer.class 保持一致
        batch.num.messages or queue.buffering.max.ms 都会触发发送
        props.put("batch.num.messages", "15000");// 异步发送 每次批量发送的条目
        props.put("queue.buffering.max.messages", "15000");//最多一次发送数
        props.put("compression.codec", "1");//  压缩算法0不压缩1gzip压缩2Snappy，一般选择gzip，但是使用Snappy压缩效率更高
        props.put("compressed.topics", null);// 对topic分别设置压缩算法
        props.put("zookeeper.connect",  KafkaProperties.zkConnect);//zk集群集合
        props.put("serializer.class", "kafka.serializer.StringEncoder");//序列化消息
        props.put("metadata.broker.list", KafkaProperties.mblist);//kafka 集群实例集合
        props.put("producer.type", KafkaProperties.kafkaproducertype);//是否采用异步的方式发送数据
        props.put("queue.buffering.max.ms", "6000");//异步发送的时候 发送时间间隔 
    */
	}
	
	private KafkaProducter(String topic) {
	   this("kafka.properties", topic);
	}
	
	/**
	 * 单条插入队列
	 * @param msg 消息
	 * @param topic 主题
	 * @return
	 */
	@SuppressWarnings("finally")
    public String setMsgSing(String msg) {
		String returnstr="ok";
		
		try{
		if(null==producer){
			producer = new Producer<Integer, String>(new ProducerConfig(props));			
		}
		    producer.send(new KeyedMessage<Integer, String>(this.topic, msg));
		}catch(Exception e){
			e.printStackTrace();
			returnstr=e.getMessage();
		}finally{
			    
		    return returnstr;
		}
	}
	
	public Properties getProps()
	{
	    return props;
	}
	
	public String getTopic()
	{
	    return topic;
	}
	
	public static void main(String[] args) throws IOException {
	    int numsOfProducer = 1;

        for(int i = 1; i <= numsOfProducer; i ++ ){
            String name = "Producer" + i;
            KafkaProducter.newKafkaProducter( "kafka.properties", "aaa" ).setMsgSing(name); 
        }

	}
}
