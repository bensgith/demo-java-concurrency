package com.demo.basic;

public final class ThreadNotSafeCount {

    private Long value;

    public synchronized Long getCount() {
        return value;
    }

    public synchronized void inc() {
        ++value;
    }
}
