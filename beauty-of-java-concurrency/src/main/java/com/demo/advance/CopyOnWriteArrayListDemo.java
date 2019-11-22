package com.demo.advance;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CopyOnWriteArrayListDemo {

    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        arrayList.add("hello");
        arrayList.add("alibaba");
        arrayList.add("welcome");
        arrayList.add("to");
        arrayList.add("shenzhen");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                arrayList.set(1, "baba");
                arrayList.remove(2);
                arrayList.remove(3);

                System.out.println("The arrayList from thread:");
                for(String s : arrayList) {
                    System.out.println(s);
                }
            }
        });

        Iterator<String> iterator = arrayList.iterator();

        thread.start();
        thread.join();

        System.out.println("The arrayList from iterator(main thread):");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("The arrayList from for loop(main thread):");
        for (String s : arrayList) {
            System.out.println(s);
        }
    }
}
