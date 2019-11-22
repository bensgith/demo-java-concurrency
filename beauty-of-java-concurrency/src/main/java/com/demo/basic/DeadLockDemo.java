package com.demo.basic;

public final class DeadLockDemo {

    private static Object objA = new Object();
    private static Object objB = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (objA) {
                    System.out.println(Thread.currentThread() + " has got objA");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread() + " is waiting for objB");

                    synchronized (objB) {
                        System.out.println(Thread.currentThread() + " has finally got objB");
                    }
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (objB) {
                    System.out.println(Thread.currentThread() + " has got objB");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread() + " is waiting for objA");

                    synchronized (objA) {
                        System.out.println(Thread.currentThread() + " has finally got objA");
                    }
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
