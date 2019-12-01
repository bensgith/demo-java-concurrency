package com.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestHarness {
    private static int N_THREADS = 100;
    private static CountDownLatch startLatch = new CountDownLatch(1);
    private static CountDownLatch endLatch = new CountDownLatch(N_THREADS);
    private static ExecutorService es = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        runWithNormalThread();

        long start = System.nanoTime();
        System.out.println("starting in 3 seconds ====>>>");
        TimeUnit.SECONDS.sleep(3);
        startLatch.countDown();
        endLatch.await();
        long end = System.nanoTime();
        System.out.println(end - start);
        es.shutdown();
    }

    private static void runWithNormalThread() {
        for (int i = 0; i < N_THREADS; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " started");
                    endLatch.countDown();
                }
            }).start();
        }
    }

    private static void runWithExecutors() {
        for (int i = 0; i < N_THREADS; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        startLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " started");
                    endLatch.countDown();
                }
            });
        }
    }
}
