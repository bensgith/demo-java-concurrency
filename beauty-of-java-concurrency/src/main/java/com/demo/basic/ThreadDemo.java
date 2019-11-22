package com.demo.basic;

public final class ThreadDemo {

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("I am a child thread.");
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new ThreadDemo.MyThread();
        myThread.start();

//        AnotherThread anotherThread = new AnotherThread();
//        anotherThread.start();

        ThirdThread thirdThread = new ThreadDemo().new ThirdThread();
        thirdThread.start();
    }

    public class ThirdThread extends Thread {
        @Override
        public void run() {
            System.out.println("I am a third thread.");
        }
    }
}

//class AnotherThread extends Thread {
//    @Override
//    public void run() {
//        System.out.println("I am another child thread.");
//    }
//}