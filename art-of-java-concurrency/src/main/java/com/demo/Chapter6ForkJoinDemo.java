package com.demo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class Chapter6ForkJoinDemo {

    static class CountTask extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                int middle = (end - start) / 2;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle + 1, end);
                // execute sub tasks
                leftTask.fork();
                rightTask.fork();
                // get results from sub tasks
                int leftResult = leftTask.join();
                int rightResult = rightTask.join();
                sum = leftResult + rightResult;

            }
            return sum;
        }
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 5);
        Future<Integer> result = forkJoinPool.submit(task);
        System.out.println(result.get());
    }
}
