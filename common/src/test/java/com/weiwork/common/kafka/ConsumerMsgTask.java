package com.weiwork.common.kafka;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
@SuppressWarnings("rawtypes")
public class ConsumerMsgTask implements Runnable {
	private KafkaStream m_stream;
 
    public ConsumerMsgTask(KafkaStream stream) {
        m_stream = stream;
    }
 
    @SuppressWarnings("unchecked")
	public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        while(it.hasNext()){
        	System.out.println("cusumer Thread  : " + new String(it.next().message()));
        }
    }
}
