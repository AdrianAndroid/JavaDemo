package cn.kuwo.javalib.blokingQueue;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cn.kuwo.javalib.T;

public class A006_Condition {
}

class ConditionUseCase {
    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    private static void test1() {
        final ConditionUseCase useCase = new ConditionUseCase();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                useCase.conditionWait();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                useCase.conditionSignal();
            }
        });

        try {
            TimeUnit.SECONDS.sleep(10);
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void conditionWait() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "拿到锁了");
            System.out.println(Thread.currentThread().getName() + "等待信号");
            condition.await();
            System.out.println(Thread.currentThread().getName() + "拿到信号");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "完事儿了");
        }
    }

    public void conditionSignal() {
        lock.lock();
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "拿到锁了");
            condition.signal();
            System.out.println(Thread.currentThread().getName() + "发出信号");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "完事儿了");
        }
    }

    public static void test2() {
        BoundedQueue<String> queue = new BoundedQueue<>(10);
        // 3个生产者+1个消费者
        Producer producer1 = new Producer(queue, "线程1");
        Producer producer2 = new Producer(queue, "线程2");
        Producer producer3 = new Producer(queue, "线程3");
        Consumer consumer = new Consumer(queue);

        // 借助线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(producer1);
        executorService.execute(producer2);
        executorService.execute(producer3);
        executorService.execute(consumer);

        // 让他们执行10s
        T.shortWaitSeconds(10);
        producer1.stop();
        producer2.stop();
        producer3.stop();
        consumer.stop();


        T.shortWaitSeconds(2);
        executorService.shutdownNow();
    }


    public static void main(String[] args) {
//        test1();
        test2();
    }

}

//生产者
class Producer implements Runnable {
    private BoundedQueue<String> queue;

    private AtomicInteger count = new AtomicInteger();
    private String name;

    private volatile boolean isRunning = true;

    public void stop() {
        isRunning = false;
    }


    public Producer(BoundedQueue<String> queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " 启动生产者线程");
        try {
            while (isRunning) {
                int interval = new Random().nextInt(1000);
                T.shortWait(interval);
                System.out.println(name + " 等待 " + interval);
                queue.put(name + " 生产数据 数据 : " + count.incrementAndGet());

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(name + " 结束生产者线程");
        }
    }
}

// 消费者
class Consumer implements Runnable {
    private BoundedQueue<String> queue;
    private volatile boolean isRunning = true;

    public void stop() {
        isRunning = false;
    }

    public Consumer(BoundedQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                String o = queue.get();
                System.out.println("消费数据：" + o);
                T.shortWait(new Random().nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class BoundedQueue<T> {
    private LinkedList<T> buffer;    //生产者容器
    private int maxSize;           //容器最大值是多少
    private Lock lock;
    private Condition fullCondition;
    private Condition notFullCondition;

    BoundedQueue(int maxSize) {
        this.maxSize = maxSize;
        buffer = new LinkedList<>();
        lock = new ReentrantLock();
        fullCondition = lock.newCondition();
        notFullCondition = lock.newCondition();
    }

    /**
     * 生产者
     *
     * @param obj
     * @throws InterruptedException
     */
    public void put(T obj) throws InterruptedException {
        lock.lock();    //获取锁
        try {
            while (maxSize == buffer.size()) {
                notFullCondition.await();       //满了，添加的线程进入等待状态
            }
            buffer.add(obj);
            fullCondition.signal(); //通知
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费者
     *
     * @return
     * @throws InterruptedException
     */
    public T get() throws InterruptedException {
        T obj;
        lock.lock();
        try {
            while (buffer.size() == 0) { //队列中没有数据了 线程进入等待状态
                fullCondition.await();
            }
            obj = buffer.poll();
            notFullCondition.signal(); //通知
        } finally {
            lock.unlock();
        }
        return obj;
    }

}


class ConTest2 {
    static private int queueSize = 10;
    static private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
    static private Lock lock = new ReentrantLock();
    static private Condition notFull = lock.newCondition();
    static private Condition notEmpty = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Produceer producer = new Produceer();
        Consumer consumer = new Consumer();
        producer.start();
        consumer.start();
        Thread.sleep(0);
        producer.interrupt();
        consumer.interrupt();
    }

    static class Consumer extends Thread {
        volatile boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("队列空，等待数据");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            flag = false;
                        }
                    }
                    queue.poll();                //每次移走队首元素
                    notFull.signal();
                    System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Produceer extends Thread {
        volatile boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.size() == queueSize) {
                        try {
                            System.out.println("队列满，等待有空余空间");
                            notFull.await();
                        } catch (InterruptedException e) {

                            flag = false;
                        }
                    }
                    queue.offer(1);        //每次插入一个元素
                    notEmpty.signal();
                    System.out.println("向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
                } finally {
                    lock.unlock();
                }
            }
        }
    }


}