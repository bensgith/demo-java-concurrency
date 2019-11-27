package com.demo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    private static AtomicInteger ai = new AtomicInteger(0);

//    public static int getValue() {
//        return ai.get();
//    }
//
//    private static void evenIncrement() {
////        ai.addAndGet(2);
//        ai.getAndIncrement();
//    }



//    public static void testWithTimer() {
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.err.println("Aborting");
//                System.exit(0);
//            }
//        }, 5000);
//
//        ExecutorService es = Executors.newCachedThreadPool();
//        AtomicIntegerDemo aid = new AtomicIntegerDemo();
//        es.execute(aid);
//        while (true) {
//            int val = aid.getValue();
//            if (val % 2 != 0) {
//                System.out.println(val);
//                System.exit(0);
//            }
//        }
//    }

    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(10);

//        while (true) {
//            ai.getAndSet(0);

            for (int i = 0; i < 10; i++) {
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 10000; j++) {
                            ai.getAndIncrement();
                        }
                    latch.countDown();
                    }
                });
            }

            latch.await();


            int result = ai.get();
            System.out.println(result);

//            if (result < 100000)
//                break;

//            TimeUnit.SECONDS.sleep(1);
//        }


        es.shutdown();
    }
}
