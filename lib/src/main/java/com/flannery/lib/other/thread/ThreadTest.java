package cn.kuwo.javalib.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTest {

    public static void main2(String[] args) throws InterruptedException {
        StopThread thread = new StopThread();
        thread.start();
        // 休眠1秒，确保线程进入运行
        Thread.sleep(1000);
        // 暂停线程
//        thread.stop(); //x=1 y=0  得到的结果不安全
        thread.interrupt(); //x=1 y=1
        /*
        java.lang.InterruptedException: sleep interrupted
            at java.base/java.lang.Thread.sleep(Native Method)
            at cn.kuwo.javalib.thread.ThreadTest$StopThread.run(ThreadTest.java:34)
         */
        // 确保线程已经销毁
        while (thread.isAlive()) {
            System.out.print("。");
        }
        System.out.println();
        // 输出结果
        thread.print();
    }


    private static class StopThread extends Thread {
        private int x = 0;
        private int y = 0;

        @Override
        public void run() {
            //这是一个同步原子操作
            synchronized (this) {
                ++x;
                try {
                    // 休眠3秒，模拟耗时操作
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ++y;
            }
        }

        public void print(){
            System.out.println("x=" + x + " y=" + y);
        }
    }


    public static void main3(String[] args) {
        Thread t = new Thread(new Task("1"));
        t.start();
        t.interrupt();

        //thread has been interrupt!
        //isInterrupted: false
        //task 1 is over
    }

    static class Task implements Runnable{

        String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("thread has been interrupt!");
            }
            System.out.println("isInterrupted: " + Thread.currentThread().isInterrupted());
            System.out.println("task " + name + " is over");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Task2("1"));
        t.start();
        t.interrupt();
    }
    static class Task2 implements Runnable{
        String name;

        public Task2(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("first :" + Thread.interrupted());
            System.out.println("second:" + Thread.interrupted());
            System.out.println("task " + name + " is over");
        }
    }
}


class WaitqueueTest {
    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        for(int i = 1; i <= 100 ; i++){
            workQueue.add(new Task(String.valueOf(i)));
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, workQueue);
        executor.execute(new Task("0"));
        executor.shutdownNow();
        System.out.println("workQueue size = " + workQueue.size() + " after shutdown");
    }

    static class Task implements Runnable{
        String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for(int i = 1; i <= 10; i++){
                System.out.println("task " + name + " is running");
            }
            System.out.println("task " + name + " is over");
        }
    }
}