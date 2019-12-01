package com.demo;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class Chapter7AtomicIntegerFieldUpdaterDemo {

    static class User {
        private String name;
        public volatile int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        User user = new User("aaa", 10);
        System.out.println(user.getAge());
        updater.getAndAdd(user, 10);
        System.out.println(user.getAge());
    }
}
