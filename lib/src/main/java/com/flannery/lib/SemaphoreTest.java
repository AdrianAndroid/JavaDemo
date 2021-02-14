package com.flannery.lib;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//Semaphore的主要方法摘要：
//　　void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
//　　void release():释放一个许可，将其返回给信号量。
//　　int availablePermits():返回此信号量中当前可用的许可数。
//　　boolean hasQueuedThreads():查询是否有线程正在等待获取。
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore sp = new Semaphore(3); // 创建Semaphore信号量，初始化许可大小

        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    T.sleepMillis(100);
                    T.printWithThreadName("进入，当前已有", 3 - sp.availablePermits(), "个并发");
                    T.sleepMillis((long) (Math.random() * 10000));
                    T.printWithThreadName("即将离开");
                    sp.release(); //释放许可'
                    //下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
                    T.printWithThreadName("已经离开，当前已有" + sp.availablePermits(), "个并发");

                }
            });
        }

    }

}


//　单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
class LockTest {

    public static void main(String[] args) {
        final Business business = new Business();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executor.execute(
                    new Runnable() {
                        public void run() {
                            business.service();
                        }
                    }

            );
        }
        executor.shutdown();
    }

    private static class Business {
        private int count;
        Lock lock = new ReentrantLock();
        Semaphore sp = new Semaphore(1);

        public void service() {
            //lock.lock();
            try {
                sp.acquire(); //当前线程使用count变量的时候将其锁住，不允许其他线程访问
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(count);
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
                //lock.unlock();
                sp.release();  //释放锁
            }
        }
    }
}


class SemaphoreDemo {
    class MyTask implements Runnable {

        private Semaphore semaphore; //信号量
        private int user; //第几个人

        public MyTask(Semaphore semaphore, int user) {
            this.semaphore = semaphore;
            this.user = user;
        }

        @Override
        public void run() {
            try {
                // 获取信号量许可才可以占用窗口
                semaphore.acquire(); // 可以被中断的
                //运行到这里说明占用了窗口，可以买票了
                T.printWithThreadName("用户", user, "进入窗口，准备买票");
                T.sleepMillis(4000);
                T.printWithThreadName("用户", user, "买完了准备离开");
                T.sleepMillis(4000);
                T.printWithThreadName("用户", user, "已经买完，离开");
                semaphore.release(); // 释放窗口占有
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void execute() {
        // 定义窗口个数
        Semaphore semaphore = new Semaphore(2);
        // 线程池
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) { //20个人买票
            service.submit(new MyTask(semaphore, i + 1));
        }
//        service.shutdownNow();
    }

    public static void main(String[] args) {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        semaphoreDemo.execute();
    }
}