package cn.kuwo.javalib.volatiletest;

import cn.kuwo.javalib.T;

public class VisibilityTest {

// -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xcomp
    private volatile boolean flag = true;

    private static void print(String text) {
        System.out.println(Thread.currentThread().getName() + " " + text);
    }

    public void refresh() {
        flag = false;
        print("修改flag=" + flag);
    }

    public void load() {
        print("开始执行...");
        int i = 0;
        while (flag) {
            // 1. 光i++这个不能跳出循环， 加入volatile关键字之后才能跳出循环
            i++;


            // 2. 添加sleep可以跳出循环
            //T.sleepSeconds(1);

            // 3. System.out.print(".");能跳出循环
            //System.out.print(".");

            //T.shortWait(0);
        }
        print("跳出循环：i=" + i);
    }

    public static void main(String[] args) {
        VisibilityTest test = new VisibilityTest();
        new Thread(test::load, "thread-A ").start();
        T.sleepSeconds(2);
        new Thread(test::refresh, "thread-B ").start();
        //shortWait(2000);
    }

}
