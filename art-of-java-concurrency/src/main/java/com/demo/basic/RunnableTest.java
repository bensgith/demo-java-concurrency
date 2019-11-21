package com.demo.basic;

public class RunnableTest {

    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("I'm a runnable child thread.");
        }
    }

    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();
    }
}
