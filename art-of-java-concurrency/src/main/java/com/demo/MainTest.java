package com.demo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MainTest {

    public static void main(String[] args) throws Exception {


    }

    public static void javaLocalTimeApiTest() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println(date);
        System.out.println(time);
        System.out.println(dateTime);

        System.out.println(dateTime.toLocalDate());
        System.out.println(dateTime.toLocalTime());

        System.out.println(LocalDate.of(2011, 11, 22));
        System.out.println(LocalTime.of(11, 22, 33, 44));
        System.out.println(LocalDateTime.of(2011, 11, 22, 11, 22,  33, 44));
        System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.now()));

        System.out.println(">>>>>>>");
        LocalDateTime start = LocalDateTime.of(2011, 11, 11, 11, 11, 11);
        LocalDateTime end = LocalDateTime.of(2022, 12, 22, 22, 22, 22);
        System.out.println(start);
        System.out.println(end);
        Duration d = Duration.between(start, end);
        System.out.println(d);
    }

    public static void javaReflectionTest() throws Exception {
        Class<?> userClass = Class.forName("com.demo.User");
        for (Field f : userClass.getDeclaredFields()) {
            System.out.println(f);
        }
        System.out.println();
        for (Method m : userClass.getDeclaredMethods()) {
            System.out.println(m);
        }
        System.out.println();

        User user = (User)userClass.newInstance();
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println();

        Field fieldId = userClass.getDeclaredField("id");
        fieldId.setInt(user,100);
        Field fieldName = userClass.getDeclaredField("name");
        fieldName.setAccessible(true);
        fieldName.set(user, "test");
        System.out.println(user.getId());
        System.out.println(user.getName());
    }

    class People {
        public long longId;

        public void say() {
            System.out.println("hello");
        }
    }

    class User extends People {
        public int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

