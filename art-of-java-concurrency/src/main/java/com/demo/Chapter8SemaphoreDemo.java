package com.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Chapter8SemaphoreDemo {
    private static int THREAD_COUNT = 100;
    private static Semaphore semaphore = new Semaphore(10);
    private static ExecutorService es = Executors.newFixedThreadPool(THREAD_COUNT);

    public static void main(String[] args) {
        semaphoreTryAcquire();
    }

    private static void semaphoreAcquire() {
        for (int i = 0; i < THREAD_COUNT; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName() + " saved data");
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        es.shutdown();
    }

    private static void semaphoreTryAcquire() {
        for (int i = 0; i < THREAD_COUNT; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    if (semaphore.tryAcquire()) {
                        System.out.println(Thread.currentThread().getName() + " saved data");
                        semaphore.release();
                    } else {
                        System.out.println(Thread.currentThread().getName() + " is trying to acquire permit");
                    }
                }
            });
        }

        es.shutdown();
    }


}
