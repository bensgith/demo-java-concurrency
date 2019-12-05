package com.demo;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Chapter8ExchangerDemo {

    private static final Exchanger<String> exchanger = new Exchanger<>();
    private static ExecutorService es = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("thread a running immediately");
                    String messageA = "bank statement a";
                    String message = exchanger.exchange(messageA);
                    System.out.println("thread a: " + message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("thread b sleeping for 3 seconds");
                    TimeUnit.SECONDS.sleep(3);
                    String messageB = "bank statement b";
                    String message = exchanger.exchange(messageB);
                    System.out.println("thread b: " + message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        es.shutdown();
    }
}
