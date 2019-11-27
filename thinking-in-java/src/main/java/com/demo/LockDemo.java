package com.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    static ReentrantLock lock = new ReentrantLock();
    private static int value;

    public static void testLock() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int j = 0; j < 10000; j++) {
                        lock.lock();
                        try {
                            incValue();
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(5);
        System.out.println(getValue());
        System.out.println("time spent: " + (System.currentTimeMillis() - start));
    }

    public static void testLockWithExecutor() throws Exception {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService es = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        lock.lock();
                        try {
                            value++;
                        } finally {
                            lock.unlock();
                        }
                    }
                    latch.countDown();
                }
            });
        }
        latch.await();
        /**
         * if shutdown() is called, the running threads will be cancelled immediately,
         * even though they are in the middle of some tasks
         *
         * solution: to use CountDownLatch to make sure all jobs completed before the
         * executor shuts down
         */
        es.shutdown();

        System.out.println(getValue());
        System.out.println("time spent: " + (System.currentTimeMillis() - start));

    }

    public static int getValue() {
        return value;
    }

    public static void incValue() {
        value++;
    }

    public static void main(String[] args) throws Exception {
        testLockWithExecutor();
    }
}
