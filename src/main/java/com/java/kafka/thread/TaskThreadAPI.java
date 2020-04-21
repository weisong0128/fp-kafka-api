package com.java.kafka.thread;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.util.Properties;

/**
 * @description: 创建kafka消费任务（线程）
 * @author: ws
 * @time: 2020/4/16 20:44
 */
public class TaskThreadAPI implements Runnable{
    private static Properties props;
    private Producer<String, byte[]> producer;  //多线程不可设为static变量

    static {
        props = new Properties();
        InputStream inputStream = TaskThreadAPI.class.getClassLoader().getResourceAsStream("producer.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        producerMethod();	// 一个线程执行一个完整的方法
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "耗时：" + (endTime - startTime) + "ms");
    }

    /**
     * 数据生产方法
     */
    private void producerMethod() {
        producer =new KafkaProducer<>(props);
        int messageNo = 0;
        //创建源
//        File file = new File(inFilePath);
        File file = new File("test_20w.bcp");
        //选择流
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            long startTime = System.currentTimeMillis();
            while((line=reader.readLine())!=null){
                /**
                 * bcp数据转成相应json格式
                 */
                String[] strArray = null;
                strArray = line.split(",");
                StringBuilder sb = new StringBuilder();
                sb.append("{\"tablename\":\"part_kafka_0414_A\",\"partition\":\"20200417\",\"data\":{\"s_high\":\"");
                sb.append(strArray[0]).append("\",\"s_middle\":\"").append(strArray[0]).append("\",\"s_low\":\"")
                        .append(strArray[0]).append("\",\"l_high\":\"").append(strArray[0]).append("\",\"l_middle\":\"")
                        .append(strArray[0]).append("\",\"l_low\":\"").append(strArray[0]).append("\"}}");
                byte[] a = sb.toString().getBytes();
                ProducerRecord record = new ProducerRecord<>("autotest_fp_0228", a);
                producer.send(record);
                messageNo++;
                System.out.println(messageNo + "-->" + Thread.currentThread().getName());
            }
            long endTime = System.currentTimeMillis();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null!=reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
