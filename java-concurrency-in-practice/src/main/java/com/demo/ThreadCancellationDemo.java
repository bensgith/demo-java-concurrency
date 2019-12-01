package com.demo;

import java.util.concurrent.TimeUnit;

public class ThreadCancellationDemo {
    private static int value;

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(System.nanoTime() + " Sleeping for 10 seconds >>>>");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    System.err.println(System.nanoTime() + " interrupted in sleep");
                }
                System.out.println(System.nanoTime() + " Task completed");
            }
        });

        t.start();

        System.out.println(System.nanoTime() + " Main thread sleeping for 2 seconds(before interrupted) >>>>>");
        System.out.println(System.nanoTime() + " t.isInterrupted(before)=" + t.isInterrupted());
        TimeUnit.SECONDS.sleep(2);
        System.out.println(System.nanoTime() + " t.interrupt is called");
        t.interrupt();
        System.out.println(System.nanoTime() + " Main thread sleeping for 1 seconds(after interrupted) >>>>>");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(System.nanoTime() + " t.isInterrupted(after)=" + t.isInterrupted());

        while (true) {
            System.out.print(System.nanoTime() + " Checking thread status: ");
            System.out.println(t.getState());
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
