package com.demo;

public class Chapter4ThreadInterruptionDemo {

    public static void main(String[] args) {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);

        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        SleepUtils.sleep(5);

        sleepThread.interrupt();
        busyThread.interrupt();

        /**
         * 如果该线程已经处于终结状态，即使该线程被中断过，在调用该线程对象的isInterrupted()时依旧会反回false
         */
        System.out.println("SleepThread interrupted=" + sleepThread.isInterrupted());
        System.out.println("BussyThread interrupted=" + busyThread.isInterrupted());

        SleepUtils.sleep(2);
    }

    public static final class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.sleep(10);
            }
        }
    }

    public static final class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                // empty
            }
        }
    }
}
