package cn.kuwo.javalib.cas;

import java.util.concurrent.atomic.AtomicBoolean;

import cn.kuwo.javalib.T;

/**
 * @author hrabbit
 * 2018/07/16.
 */
public class AtomicBooleanTest implements Runnable {

    private static AtomicBoolean flag = new AtomicBoolean(true);// 默认是true

    public static void main(String[] args) {
        AtomicBooleanTest ast = new AtomicBooleanTest();
        Thread thread1 = new Thread(ast,"Thread A");
        Thread thread = new Thread(ast, "Thread B");
        thread1.start();
        thread.start();

        // 1运行
        // 改成false
        // 2 运行
        // 此时是false， 运行下边的get
    }

    @Override
    public void run() {
        System.out.println("thread:" + Thread.currentThread().getName() + ";flag:" + flag.get());
        // 期待值是true， 要改编成false
        if (flag.compareAndSet(true, false)) {
            System.out.println(Thread.currentThread().getName() + "" + flag.get()); // 现在已经是false
            T.sleepSeconds(5);
            flag.set(true);
        } else {
            System.out.println("重试机制thread:" + Thread.currentThread().getName() + ";flag:" + flag.get());
            T.sleepMillis(500);
            run(); //再次运行
        }

    }
}
