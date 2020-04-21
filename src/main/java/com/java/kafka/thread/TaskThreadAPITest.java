package com.java.kafka.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 描述
 * @author: ws
 * @time: 2020/4/16 21:00
 */
public class TaskThreadAPITest {
    private static final int THREAD_NUMBER = 5;
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10,20,1, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_NUMBER; i++) {
            threadPool.execute(new TaskThreadAPI());
        }
    }
}
