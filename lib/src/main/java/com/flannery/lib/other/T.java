package cn.kuwo.javalib;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class T {

    public static String currentThreadName() {
        return Thread.currentThread().getName();
    }

    public static void sleepMillis(long timeout){
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 休眠
    public static void sleepSeconds(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 休眠
    public static void sleepSeconds(long timeout, Runnable runnable) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
            runnable.run();
        }
    }


    public static void shortWaitSeconds(long interval) {
        shortWait(interval * 1000);
    }


    // 不阻塞
    public static void shortWait(long interval) {
        long start = System.currentTimeMillis();
        long end;
        do {
            end = System.currentTimeMillis();
        } while (start + interval >= end);
    }

    public static void printWithThreadName(Object... str) {
        System.out.println(Thread.currentThread().getName() + ":" + Arrays.toString(str));
    }

    public static void printWithThreadName(String... str) {
        System.out.println(Thread.currentThread().getName() + ":" + Arrays.toString(str));
    }

    public static void printWithThreadName(Exception e, String... str) {
        System.out.println(Thread.currentThread().getName() + ":" + e.getMessage() + ":" + Arrays.toString(str));
    }
}
