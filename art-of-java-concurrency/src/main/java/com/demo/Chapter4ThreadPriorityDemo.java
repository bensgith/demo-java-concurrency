package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Chapter4ThreadPriorityDemo {

    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws InterruptedException {
        List<Job> jobList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobList.add(job);
            Thread thread = new Thread(job, "Thread-" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (Job job : jobList) {
            System.out.println("Job priority: " + job.priority + ", count: " + job.jobCount);
        }
    }

    public static final class Job implements Runnable {
        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " state: " + Thread.currentThread().getState());
            /**
             * yield()方法
             *
             * yield()应该做的是让当前运行线程回到可运行状态，以允许具有相同优先级的其他线程获得运行机会。
             * 因此，使用yield()的目的是让相同优先级的线程之间能适当的轮转执行。
             * 但是，实际中无法保证yield()达到让步目的，因为让步的线程还有可能被线程调度程序再次选中。
             *
             * 结论：yield()从未导致线程转到等待/睡眠/阻塞状态。在大多数情况下，yield()将导致线程从运行状态转到可运行状态，但有可能没有效果。
             *
             * 理论上，yield意味着放手，放弃，投降。一个调用yield()方法的线程告诉虚拟机它乐意让其他线程占用自己的位置。
             * 这表明该线程没有在做一些紧急的事情。注意，这仅是一个暗示，并不能保证不会产生任何影响。
             *
             * 让我们列举一下关于以上定义重要的几点：
             *
             * Yield是一个静态的原生(native)方法
             * Yield告诉当前正在执行的线程把运行机会交给线程池中拥有相同优先级的线程。
             * Yield不能保证使得当前正在运行的线程迅速转换到可运行的状态
             * 它仅能使一个线程从运行状态转到可运行状态，而不是等待或阻塞状态
             *
             * 作者：小小少年Boy
             * 链接：https://www.jianshu.com/p/873f799153fb
             * 来源：简书
             * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
             *
             *
             * MY NOTE:
             * yield() will give up CPU resources but not give up lock
             *
             */
            while (notStart) {
                Thread.yield();
            }

            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}
