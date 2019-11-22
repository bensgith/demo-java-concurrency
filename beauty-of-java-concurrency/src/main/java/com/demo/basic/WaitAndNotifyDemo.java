package com.demo.basic;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Example from https://www.jianshu.com/p/1dafbf42cc54
 */
public final class WaitAndNotifyDemo {

    public static class Data {
        private String packet;

        // True if receiver should wait
        // False if sender should wait
        private boolean transfer = true;

        public synchronized void send(String packet) {
            while (!transfer) {
                try {
                    System.out.println(packet + " is waiting to be sent");
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread interrupted: ");
                    e.printStackTrace();
                }
            }
            transfer = false;
            this.packet = packet;
            notifyAll();
        }

        public synchronized String receive() {
            while (transfer) {
                try {
                    System.out.println("waiting to receive new data");
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Thread interrupted: ");
                    e.printStackTrace();
                }
            }
            transfer = true;
            notifyAll();
            return packet;
        }
    }

    public static class Sender implements Runnable {
        private Data data;

        public Sender(Data data) {
            this.data = data;
        }

        @Override
        public void run() {
            String packets[] = {
                    "First packet",
                    "Second packet",
                    "Third packet",
                    "Fourth packet",
                    "End"
            };

            for (String packet : packets) {
                System.out.println("Sending==> " + packet);
                data.send(packet);

                // Thread.sleep() to mimic heavy server-side processing
                try {
                    long randomSleepTime = ThreadLocalRandom.current().nextInt(1000, 5000);
//                    System.out.println("Sender Thread sleeping: " + randomSleepTime);
                    Thread.sleep(randomSleepTime);
                } catch (InterruptedException e)  {
                    System.out.println("Sender Sleeping thread interrupted: ");
                    e.printStackTrace();
                }
            }

        }
    }

    public static class Receiver implements Runnable {
        private Data load;

        public Receiver(Data load) {
            this.load = load;
        }

        // standard constructors
        @Override
        public void run() {
            for(String receivedMessage = load.receive();  !"End".equals(receivedMessage); receivedMessage = load.receive()) {

                System.out.println("Received==> " + receivedMessage);

                // ...
                try {
                    long randomSleepTime = ThreadLocalRandom.current().nextInt(1000, 5000);
//                    System.out.println("Receiver Thread sleeping: " + randomSleepTime);
                    Thread.sleep(randomSleepTime);
                } catch (InterruptedException e) {
                    System.out.println("Receiver Sleeping thread interrupted: ");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data));
        Thread receiver = new Thread(new Receiver(data));

        sender.start();
        receiver.start();
    }
}
