package com.demo;

import java.util.Map;
import java.util.concurrent.*;

public class Chapter8CyclicBarrierBankStatementServiceDemo {

    static class BankStatementService implements Runnable {
        @Override
        public void run() {
            int result = 0;
            for (Map.Entry<String, Integer> sheet : sheetBankStatementCount.entrySet()) {
                result += sheet.getValue();
            }
            sheetBankStatementCount.put("result", result);
            System.out.println(result);
        }
    }

    private static CyclicBarrier cb = new CyclicBarrier(4, new BankStatementService());
    private static ExecutorService es = Executors.newFixedThreadPool(4);
    private static ConcurrentHashMap<String, Integer> sheetBankStatementCount = new ConcurrentHashMap<>();


    public static void main(String[] args) {

        for (int i = 0; i < 4; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    sheetBankStatementCount.put(Thread.currentThread().getName(), 1);
                    try {
                        cb.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        es.shutdown();
    }
}
