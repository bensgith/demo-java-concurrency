package com.demo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.CharacterCodingException;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {
    private static boolean stopRequested = false;

    public static void main(String[] args) throws Exception {
        System.out.println(transform("aabbcc"));

    }

    public static String transform(String input) {
        char[] ch = input.toCharArray();
        Set<Character> set = new HashSet<>();
        for (char c : ch) {
            set.add(c);
        }
        Object[] obj = set.toArray();
        StringBuilder sb = new StringBuilder();
        for (Object o : obj) {
            sb.append(o);
        }
        return sb.toString();
    }

    public static void folderNames() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<folder name=\"c\">" +
                        "<folder name=\"program files\">" +
                        "<folder name=\"uninstall information\" />" +
                        "</folder>" +
                        "<folder name=\"users\" />" +
                        "</folder>";

        String test = "abc";
        Pattern pattern = Pattern.compile("[abc]");
        Matcher matcher = pattern.matcher(xml);
        System.out.println(matcher.matches());
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

//        Collection<String> names = folderNames(xml, 'u');
//        for(String name: names) {
//            System.out.println(name);
//        }
    }

    public static void stringChar() {

        char c = '1';
        String s = "string";
        if ("0123456789".indexOf(c) == -1)
            s = s + c;
        System.out.println(s);
    }

    public static void stopThread() throws Exception {
        new Thread(() -> {
            int i = 0;
            while (!stopRequested)
                System.out.println(i++);;
        }).start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }

    public static void elementCount() {
        int[] arr = {1,4,1,4,2,5,4,5,8,7,8,77,88,5,4,9,6,2,4,1,5};
        Map<Integer, Integer> map = new HashMap<>();
        for (int e : arr) {
            if (map.get(e) == null) {
                map.put(e, 1);
            } else {
                int newValue = map.get(e) + 1;
                map.put(e, newValue);
            }
        }

        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            System.out.println(e.getKey() + " count=" + e.getValue());
        }
    }

    public static void arrayListTest() {
        String[] s = {"a", "b", "c", "d", "e"};
        int size = s.length;
        int index = 2;
        int numMoved = size - index - 1;
        System.arraycopy(s, index + 1, s, index, numMoved);
        s[--size] = null;
        System.out.println(s.length);
        for (String str : s) {
            System.out.print(str);
        }
    }

    public static void deepNShallowCopy() throws CloneNotSupportedException {
        User u = new UserWithDeepClone(123456789, "Ben", 100, LocalDateTime.now());
        User n = (User) u.clone();
        System.out.println(u.getId() == n.getId());
        System.out.println(u.getName() == n.getName());
        System.out.println(u.getAge() == n.getAge());
        System.out.println(u.getTime() == n.getTime());
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

        System.out.println(">>>>>>>>>");
        System.out.println(Instant.now());
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

    static class People {
        public long longId;

        public void say() {
            System.out.println("hello");
        }
    }

    static class User extends People implements Cloneable {
        public int id;
        private String name;
        private Integer age;
        private LocalDateTime time;

        public User(int id, String name, Integer age, LocalDateTime time) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.time = time;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

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

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    static class UserWithDeepClone extends User {
        public UserWithDeepClone(int id, String name, Integer age, LocalDateTime time) {
            super(id, name, age, time);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Object o = new UserWithDeepClone(super.getId(), new String(super.getName()), new Integer(super.getAge()), super.getTime());
            return o;
        }
    }
}

