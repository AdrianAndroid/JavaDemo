package cn.kuwo.javalib.blokingQueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static void main(String[] args) {

    }

}

//1、简单使用
class ReentrantLockTest {
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                test();
            }
        };
        new Thread(runnable, "线程A ").start();
        new Thread(runnable, "线程B ").start();
    }

    public static void test() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "获取了锁");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了锁");
            lock.unlock();
        }
    }
}

//公平锁的含义我们上面已经说了，就是谁等的时间最长，谁就先获取锁。
class ReentrantLockTest2 {
    private static final Lock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                test();
            }
        };
        new Thread(runnable, "线程A ").start();
        new Thread(runnable, "线程B ").start();
        new Thread(runnable, "线程C ").start();
        new Thread(runnable, "线程D ").start();
        new Thread(runnable, "线程E ").start();
    }

    public static void test() {
        for (int i = 0; i < 2; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "获取了锁");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}

// 响应中断
class ReentrantLockTest3 {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadDemo(lock1, lock2));
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));
        thread.start();
        thread1.start();
        thread.interrupt(); //是第一个线程中断
    }

    // 在这里我们定义了两个锁lock1和lock2。
    // 然后使用两个线程thread和thread1构造死锁场景。
    // 正常情况下，这两个线程相互等待获取资源而处于死循环状态。
    // 但是我们此时thread中断，另外一个线程就可以获取资源，正常地执行了。
    static class ThreadDemo implements Runnable {

        Lock firstLock;
        Lock secondLock;

        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {
                System.out.println("开始firstLock lockInterruptibly");
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(50);
                System.out.println("开始secondLock lockInterruptibly");
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName() + "获取到了资源，正常结束！");
            }
        }
    }
}


// 等待响应
class ReentrantLockTest4 {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadDemo(lock1, lock2));
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));
        thread.start();
        thread1.start();
    }

    //在这个案例中，一个线程获取lock1时候第一次失败，那就等10毫秒之后第二次获取，就这样一直不停的调试，一直等到获取到相应的资源为止。
    //当然，我们可以设置tryLock的超时等待时间tryLock(long timeout,TimeUnit unit)，也就是说一个线程在指定的时间内没有获取锁，那就会返回false，就可以再去做其他事了。
    //OK，到这里我们就把ReentrantLock常见的方法说明了，所以其原理，还是主要通过源码来解释。而且分析起来还需要集合AQS和CAS机制来分析。我也会在下一篇文章来分析。感谢大家的持续关注和支持。
    static class ThreadDemo implements Runnable {

        Lock firstLock;
        Lock secondLock;

        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {
                System.out.println("开始firstLock tryLock");
                if (!lock1.tryLock()) TimeUnit.MILLISECONDS.sleep(10);
                System.out.println("开始secondLock tryLock");
                if (!lock2.tryLock()) TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName() + "获取到了资源，正常结束！");
            }
        }
    }
}

