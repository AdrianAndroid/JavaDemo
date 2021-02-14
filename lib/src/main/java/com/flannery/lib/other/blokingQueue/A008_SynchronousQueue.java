package cn.kuwo.javalib.blokingQueue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import cn.kuwo.javalib.T;

public class A008_SynchronousQueue {

    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        Thread putThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("put thread start");
                try {
                    queue.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("put thread end");
            }
        });

        Thread takeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("take thread start");
                try {
                    System.out.println("take from putThread:" + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("take thread end");
            }
        });
        putThread.start();
        Thread.sleep(1000);
        takeThread.start();
    }


}

class CountDownLatchDemo {
    public static void useCountdownLatch() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final AtomicReference<Object> atomicReference = new AtomicReference<>();
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                Object object = new Object();
                atomicReference.set(object);
                System.out.println("produced {}" + object);
                countDownLatch.countDown();
            }
        };


        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    Object object = atomicReference.get();
                    System.out.println(Thread.currentThread().getName() + "consumed {}" + object);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };


        executor.submit(producer);
        executor.submit(consumer);

        executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }


    public static void useSynchronousQueue() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        final SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                Object object = new Object();
                try {
                    synchronousQueue.put(object);
                } catch (InterruptedException ex) {
                    T.printWithThreadName(ex);
                }
                T.printWithThreadName("produced {}", object);
                T.printWithThreadName();
            }
        };
        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                try {
                    Object object = synchronousQueue.take();
                    T.printWithThreadName("consumed {}", object);
                } catch (InterruptedException ex) {
                    T.printWithThreadName(ex.getMessage(), ex);
                }
            }
        };

        executor.submit(producer);
        executor.submit(consumer);

        executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        useSynchronousQueue();
    }
}
