package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrnamentalGarden {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++)
            exec.execute(new Entrance(i));

        // Run for a while, then stop and collect the data:
        TimeUnit.SECONDS.sleep(3);

        Entrance.cancel();
        exec.shutdown();

        if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
            System.out.println("Some tasks were not terminated!");

        System.out.println("Total: " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
    }
}

class Count {
    private int count = 0;
    private Random random = new Random(47);

    /**
     * If you comment out the synchronized keyword, the program breaks because multiple tasks will be accessing
     * and modifying count simultaneously (the yield() causes the problem to happen more quickly).
     * @return
     */
    //remove the synchronized keyword to see counting fail:
    public synchronized int increment() {
        int temp = count;
        if (random.nextBoolean()) { //yield half the time
            Thread.yield();
        }
        return (count = ++temp);
    }

    public synchronized int value() {
        return count;
    }
}

class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<>();
    private static volatile boolean canceled = false;
    private int number = 0;
    private int id;

    public Entrance(int id) {
        this.id = id;
        //keep this task in a list. Also prevents garbage collection of dead tasks
        entrances.add(this);
    }

    //atomic operation on a volatile field
    public static void cancel() {
        canceled = true;
    }

    public synchronized int getValue() {
        return number;
    }

    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }

    public static int getTotalCount() {
        return count.value();
    }

    public static int sumEntrances() {
        int sum = 0;
        for(Entrance entrance : entrances)
            sum += entrance.getValue();
        return sum;
    }

    @Override
    public void run() {
        while(!canceled) {
            synchronized(this) {
                ++number;
            }

            System.out.println(this + " Total: " + count.increment());

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch(InterruptedException e) {
                System.out.println("sleep interrupted");
            }

        }
        System.out.println("Stopping " + this);
    }
}
