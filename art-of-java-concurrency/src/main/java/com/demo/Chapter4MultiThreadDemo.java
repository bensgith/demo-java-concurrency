package com.demo;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public final class Chapter4MultiThreadDemo {

    public static void main(String[] args) {
        //获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor信息和synchronized,仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        /**
         * 遍历线程信息，进打印线程ID和线程名称
         * OUTPUT:
         * [5] Monitor Ctrl-Break
         * [4] Signal Dispatcher
         * [3] Finalizer
         * [2] Reference Handler
         * [1] main
         */
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }
}
