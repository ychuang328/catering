package com.weiwork.common.kafka;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.commons.lang3.StringUtils;

import com.weiwork.common.kafka.template.IKafkaConsumerCallback;

@Deprecated
/**
 * 作废
 * 请使用 Receiver
 * @author pengfeizhang
 */
public class KafkaConsumer {

    // 消费者
    private ConsumerConnector consumer;
    private Properties props = new Properties();
    private ZkClient client;

    private String topic;
    
    private IKafkaConsumerCallback callback;
    
    public static KafkaConsumer newKafkaConsumer(String propFile,String topic,IKafkaConsumerCallback callback) {
        if (StringUtils.isBlank( propFile )) {
            return new KafkaConsumer( topic,callback );
        }
        return new KafkaConsumer( propFile,topic,callback );
    }
    
    /**
     * 初始化消费者
     * 
     * @return
     */
    public KafkaConsumer(String propFile,String topic,IKafkaConsumerCallback callback) {
        this.callback = callback;
        try {
            
            this.props.load( Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile) );
//            File f = new File("D:/workspace/common0.0.1/resources/" + propFile);
//            props.load( new FileInputStream( f ) );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int zkTimeout = 15000;
        String strZkTimeout = props.getProperty( "zookeeper.connect.timeout" );
        
        if (StringUtils.isNotBlank( strZkTimeout ) 
                && StringUtils.isNumeric( strZkTimeout )) {
            zkTimeout = Integer.valueOf( strZkTimeout );
        }
        if (StringUtils.isBlank( topic )) {
            
            this.topic = props.getProperty( "kafka.topics" );
        } else {
            this.topic = topic;
        }
        System.out.println("topic:" + topic);
        for(Map.Entry<Object, Object> e : props.entrySet()){
        	System.out.println(e.getKey() + "------------------" + e.getValue());
        }
        this.client = new ZkClient(new ZkConnection(props.getProperty( "zookeeper.connect" )),
                zkTimeout);
        this.consumer = kafka.consumer.Consumer
                        .createJavaConsumerConnector(new ConsumerConfig(props));
    }
    

    public KafkaConsumer(String topic,IKafkaConsumerCallback callback) {
        this("kafka.properties", topic,callback);
    }
    
    public Properties getProps()
    {
        return props;
    }
    
    public String getTopic()
    {
        return topic;
    }

    /**
     * 创建目录
     * 
     * @param dir
     */
    public void createzkcir(String dir) {
        String[] dirs = dir.split("/");
        String dirjar = "";
        for (String strdir : dirs) {
            if (null != strdir && !strdir.equals("")) {

                dirjar += "/" + strdir;
                if (!client.exists(dirjar)) {
                    client.createPersistent(dirjar);
                }
            }
        }
    }

    public String getProperty(String name) {
        return props.getProperty( name );
    }
    
    public void start() {
        if (consumer == null) {
            consumer = kafka.consumer.Consumer
                            .createJavaConsumerConnector(new ConsumerConfig(
                                            props));
        }

        // topicCountMap是topic和#stream的map对
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();

        // 一次读取多个topic
//        for (String topic : KafkaProperties.topics) {
//            topicCountMap.put(topic, new Integer(1));
//        }
        topicCountMap.put(topic, new Integer(1));
        
        // 用默认的decoder为每个topic生成类型T的事件流列表,
        // 返回topic和kafka stream的列表（列表大小是#stream,每个stream支持迭代message/offset对）。
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
                        .createMessageStreams(topicCountMap);

//        createzkcir(KafkaProperties.filedir);
//        // 启动监控线程
//        // new Thread(new
//        // ZkWatchThread(KafkaProperties.zkConnect,"/kafkaconsumer/top1")).start();
//        client.create(KafkaProperties.filedir + "/test2", "1".getBytes(),
//                        CreateMode.EPHEMERAL_SEQUENTIAL);

        // 线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // 获取队列中的信息

            // 获取不同topic里面的数据
            for (KafkaStream<byte[], byte[]> kafkaStream : consumerMap
                            .get(topic)) {
                final ConsumerIterator<byte[], byte[]> iterator = kafkaStream
                                .iterator();
                // 将读取放入线程池
                executor.submit(new Runnable() {
                    public void run() {
                        // 这里会阻塞 当相应主题中有未消费的消息的时候 就会消费掉
                        while (iterator.hasNext()) {
                            MessageAndMetadata<byte[], byte[]> next = iterator
                                            .next();
                            
//System.out.println();
//System.out.println("partiton:" + next.partition()
//            + "-----" + "offset:"
//            + next.offset());
                            try {
                                callback.doRowDate( new String(next.message(),
                                                                "utf-8") );
                                // 处理方法
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                });
            }
        }
    
    // 做成可执行Jar的方式
    public static void main(String[] args) throws InterruptedException {
        KafkaConsumer.newKafkaConsumer( "kafka.properties", "aaa", new tempCallBack() );
    }
}

class tempCallBack implements IKafkaConsumerCallback {

    @Override
    public void doRowDate( String rowDate )
    {
        System.out.println(rowDate);
    }
    
}