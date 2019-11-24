package com.demo;

public final class Chapter4ThreadStateDemo {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();

        //using 2 blocked thread, one has obtained the lock while the other one is blocked
        new Thread(new Blocked(), "BlockThread-1").start();
        new Thread(new Blocked(), "BlockThread-2").start();
    }

    public static final class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.sleep(100);
            }
        }
    }

    public static final class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static final class Blocked implements Runnable {
        @Override
        public void run() {
//            boolean flag = true;
            synchronized (Blocked.class) {
                while (true) {

                    System.out.println(Thread.currentThread().getName() + " is sleeping");
                    SleepUtils.sleep(10);
                    /**
                     * TODO
                     * How to implement this logic?
                     *
                     * 2 or 3 threads are running in parallel:
                     * 1) one of them sleeping a period of time, while the others are blocked
                     * 2) when the sleeping time ends, wake up one of the other threads which is waiting
                     *
                     */
//                    try {
//                        wait(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
//            notifyAll();
        }
    }

}
