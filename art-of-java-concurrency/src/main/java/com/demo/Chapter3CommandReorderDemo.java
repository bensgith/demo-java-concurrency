package com.demo;

import java.util.concurrent.CountDownLatch;

/**
 * this might not be a good example of command reordering
 */
public class Chapter3CommandReorderDemo {

    static class ReorderExample {
        private int a = 0;
        private boolean flag = false;

        public void write() {
            a = 1;
            flag = true;
        }

        public void read() {
            if (flag) {
                int i = a * a;
                System.out.println("flag=true i=" + i);
            } else {
                System.out.println("flag=false a=" + a);
            }
        }
    }

    public static void main(String[] args) {
        ReorderExample example = new ReorderExample();
        CountDownLatch latch = new CountDownLatch(1);

        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                example.write();
            }
        });

        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                example.read();
            }
        });

        readThread.start();
        writeThread.start();
        latch.countDown();
    }
}
