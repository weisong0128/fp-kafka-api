package com.java.kafka.thread;

/**
 * @description: threadPool.execute(() -> {...})方式：因为每一个-->{..}里的执行代码不一样，因此不能使用
 * for循环控制多个线程，而要写多个execute(()->{})执行多个不同线程任务
 * @author: ws
 * @time: 2021/4/26 10:44
 */

public class Test implements Runnable{

}
