package com.demo.basic;

public final class DaemonThreadDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    //empty loop
                }
            }
        });

        thread.setDaemon(true);
        thread.start();

        System.out.println("Main thread is finished");
    }
}
