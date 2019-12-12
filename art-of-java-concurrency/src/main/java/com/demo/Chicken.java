package com.demo;

import java.util.concurrent.Callable;

interface Bird {
    Egg lay();
}

public class Chicken implements Bird {
    public Chicken() {
    }

    @Override
    public Egg lay() {
        Callable<Bird> createBird = new Callable<Bird>() {
            @Override
            public Bird call() throws Exception {
                return new Chicken();
            }
        };
        return new Egg(createBird);
    }

    public static void main(String[] args) throws Exception {
        Chicken chicken = new Chicken();
        System.out.println(chicken instanceof Bird);
    }
}

class Egg {
    private int hatchCount = 0;
    private Callable<Bird> createBird;

    public Egg(Callable<Bird> createBird) {
        this.createBird = createBird;
    }

    public Bird hatch() throws Exception {
        hatchCount++;
        if (hatchCount > 1) {
            throw new IllegalStateException();
        }
        return createBird.call();
    }
}
