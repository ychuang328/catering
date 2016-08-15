package com.weiwork.common.kafka;
public class ConsumerDemo {
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//	private final ConsumerConnector consumer;
//    private final String topic;
//    private ExecutorService executor;
//    private Properties props ;
//    
// 
//    private ConsumerDemo(String a_topic) {
//        consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(a_topic));
//        this.topic = a_topic;
//    }
//    
// 
//    public void shutdown() {
//        if (consumer != null)
//            consumer.shutdown();
//        if (executor != null)
//            executor.shutdown();
//    }
// 
//    public void run(int numThreads) {
//        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//        topicCountMap.put(topic, new Integer(numThreads));
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
//        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
//        executor = Executors.newFixedThreadPool(numThreads);
//        int threadNumber = 0;
//        for (final KafkaStream<byte[], byte[]> stream : streams) {
//            executor.submit(new ConsumerMsgTask(stream, threadNumber));
//            threadNumber++;
//        }
//    }
// 
//    private ConsumerConfig createConsumerConfig(String a_topic) {
//    	  if(StringUtil.isEmpty(a_topic)){
//    		  logger.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx kafka topic 为空");
//    		  return null;
//    	  }
//    	  if(){
//    		  
//    	  }
////        Properties props = new Properties();
////        props.put("zookeeper.connect", "192.168.1.8:2181,192.168.1.16:2181,192.168.1.13:2181");
////        props.put("group.id", "group-1");
////        props.put("zookeeper.session.timeout.ms", "400");
////        props.put("zookeeper.sync.time.ms", "200");
////        props.put("auto.commit.interval.ms", "1000");
// 
//        return new ConsumerConfig(props);
//    }
// 
//    public static void main(String[] arg) {
//        String[] args = { "192.168.1.8:2181,192.168.1.16:2181,192.168.1.13:2181", "group-1", "page_visits", "12" };
//        String zooKeeper = args[0];
//        String groupId = args[1];
//        String topic = args[2];
//        int threads = Integer.parseInt(args[3]);
//        ConsumerDemo demo = new ConsumerDemo(zooKeeper, groupId, topic);
//        demo.run(threads);
////        try {
////            Thread.sleep(10000);
////        } catch (InterruptedException ie) {
////        	ie.printStackTrace();
////        }
////        demo.shutdown();
//    }
}
