package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class Chapter2AtomicOperationDemo {

    private AtomicInteger atomicInt = new AtomicInteger(0);
    private int i = 0;

    /**
     * counter with CAS operation
     */
    private void safeCount() {
//        for (;;) {
//            int i = atomicInt.get();
//            boolean success = atomicInt.compareAndSet(i, ++i);
//            if (success) {
//                break;
//            }
//        }
        atomicInt.getAndIncrement();
    }

    /**
     * the normal counter without CAS operation
     */
    private void count() {
        i++;
    }

    public static void main(String[] args) {
        final Chapter2AtomicOperationDemo casDemo = new Chapter2AtomicOperationDemo();
        List<Thread> threadList = new ArrayList<>(100);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        casDemo.count();
                        casDemo.safeCount();
                    }
                }
            });
            threadList.add(thread);
        }

        for (Thread t : threadList) {
            t.start();
        }

        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("i=" + casDemo.i);
        System.out.println("atomicInt=" + casDemo.atomicInt.get());
        System.out.println("time spent(ms)=" + (System.currentTimeMillis() - start));
    }
}
