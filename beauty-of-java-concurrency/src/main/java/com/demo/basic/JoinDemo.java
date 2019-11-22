package com.demo.basic;

public final class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        tenThreadsExample();
    }

    public static void twoThreadsExample() throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Child threadOne completed");
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Child threadTwo completed");
            }
        });

//        System.out.println("starting thread: " + threadOne.getId());
        threadOne.start();
//        System.out.println("starting thread: " + threadTwo.getId());
        threadTwo.start();
        System.out.println("Waiting all child threads to finish");

        threadOne.join();
        threadTwo.join();
        System.out.println("All child threads completed");
    }

    public static void tenThreadsExample() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " finished");
                }
            });
            thread.setName("thread-" + i);
            thread.start();
            thread.join();
        }
    }
}
