package com.java.kafka.thread;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: threadPool.execute(() -> {...})方式：因为每一个-->{..}里的执行代码不一样，因此不能使用
 * for循环控制多个线程，而要写多个execute(()->{})执行多个不同线程任务。
 * 读到execute(()->{})后会额外开一个线程执行，而main方法会继续往下执行。
 * run()方法在线程池对象调用execute()方法时才会执行。
 * @author: ws
 * @time: 2021/4/26 10:44
 */

public class Test implements Runnable{
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 20, 1,
            TimeUnit.MINUTES, new SynchronousQueue<>());

    public static void main(String[] args) {
        Test test = new Test();
        threadPool.execute(test);   //老版本多线程方法：new Thread(test).start();

        //额外启一个线程任务（异步执行，非阻塞，程序会继续往下执行，不需要继承runnable接口，不会读取run()方法，只会执行{}内的代码）
        threadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println("ws111");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("ws222");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("ws333");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("ws444-没有sleep延迟");
    }
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("threadName=" + threadName);
        System.out.println("----哈哈-----");
    }
}
