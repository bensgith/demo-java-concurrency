package com.demo;

import java.util.concurrent.TimeUnit;

public final class SleepUtils {

    public static final void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
