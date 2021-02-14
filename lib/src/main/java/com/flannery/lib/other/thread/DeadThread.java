package cn.kuwo.javalib.thread;

import cn.kuwo.javalib.T;

public class DeadThread {
    private static String a = "a";
    private static String b = "b";


    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            T.printWithThreadName("进入Thread A线程");
            synchronized (a) {
                T.printWithThreadName("进入A同步块，执行中。。。");
                T.sleepSeconds(2);
                synchronized (b) {
                    T.printWithThreadName("进入B同步块，执行中。。。");
                }
            }
        }, "Thread A ");
        Thread threadB = new Thread(() -> {
            T.printWithThreadName("进入Thread B线程");
            synchronized (b) {
                T.printWithThreadName("进入B同步块，执行中。。。");
                synchronized (a) {
                    T.printWithThreadName("进入A同步块，执行中。。。");
                }
            }
        }, "Thread B ");

        threadA.start();
        threadB.start();
    }


}
