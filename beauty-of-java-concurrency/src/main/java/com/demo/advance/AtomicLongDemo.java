package com.demo.advance;

import java.util.concurrent.atomic.AtomicLong;

public final class AtomicLongDemo {

    private final static AtomicLong atomicLong = new AtomicLong();

    private final static Integer[] array1 = new Integer[]{0, 1, 2, 3, 0, 5, 6, 0, 56, 0};
    private final static Integer[] array2 = new Integer[]{10, 1, 2, 3, 0, 5, 6, 0, 56, 0};

    public static void main(String[] args) throws InterruptedException {
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = array1.length;
                for (int i = 0; i < size; ++i) {
                    if (array1[i].intValue() == 0) {
                        atomicLong.incrementAndGet();
                    }
                }
            }
        });

        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = array2.length;
                for (int i = 0; i < size; ++i) {
                    if (array2[i].intValue() == 0) {
                        atomicLong.incrementAndGet();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("count 0: " + atomicLong.get());
    }
}
