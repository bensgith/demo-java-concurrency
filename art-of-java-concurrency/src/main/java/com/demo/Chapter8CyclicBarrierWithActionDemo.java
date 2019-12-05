package com.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Chapter8CyclicBarrierWithActionDemo {

    static class Action implements Runnable {
        @Override
        public void run() {
            System.out.println("barrier action is called");
        }
    }

    static CyclicBarrier cb = new CyclicBarrier(2, new Action());

    public static void main(String[] args) {
        // create a new sub thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("sub thread has done something and await");
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("sub thread is finished");
            }
        }).start();

        // main thread
        try {
            System.out.println("main thread has done something and await");
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("main thread is finished");

    }
}
