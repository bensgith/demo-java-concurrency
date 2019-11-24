package com.demo;

public class Chapter4ThreadShutdownDemo {

    public static void main(String[] args) {
        Thread countThread = new Thread(new CountRunner(), "CountThread-1");
        countThread.start();

        SleepUtils.sleep(1);
        countThread.interrupt();

        CountRunner countRunner = new CountRunner();
        countThread = new Thread(countRunner, "CountThread-2");
        countThread.start();

        SleepUtils.sleep(2);
        countRunner.cancel();
    }

    public static final class CountRunner implements Runnable {

        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println(Thread.currentThread().getName() + " count i=" + i);
        }

        private void cancel() {
            on = false;
        }
    }
}
