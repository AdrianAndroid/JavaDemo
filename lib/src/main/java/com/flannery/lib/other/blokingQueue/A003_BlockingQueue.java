package cn.kuwo.javalib.blokingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class A003_BlockingQueue {

    private static final int DEFAULT_RANGE_FOR_SLEEP = 1000;
    private BlockingQueue queue;


    public static void main(String[] args) throws InterruptedException {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

        //new了是那个生产者和一个消费者
        Producer producer1 = new Producer(queue, "producer1");
        Producer producer2 = new Producer(queue, "producer2");
        Producer producer3 = new Producer(queue, "producer3");
        Consumer consumer = new Consumer(queue);

        // 借助线程池
        ExecutorService service = Executors.newCachedThreadPool();

        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);

        // 执行10s
        TimeUnit.SECONDS.sleep(10);
        producer1.stop();
        producer2.stop();
        producer3.stop();


        TimeUnit.SECONDS.sleep(2);
        //退出
        service.shutdown();
    }

    // 生产者线程
    static class Producer implements Runnable {

        private volatile boolean isRunning = true;//是否在运行的标志 加上lock，立即从cpu缓存读写到主内存
        private BlockingQueue<String> queue; //阻塞队列
        private static AtomicInteger count = new AtomicInteger();//自动更新的值
        private final String name;

        public Producer(BlockingQueue<String> queue, String name) {
            this.queue = queue;
            this.name = name;
        }

        public void stop() {
            isRunning = false;
        }


        //offer 可以设定等待的时间，如果在指定的时间内，还不能往队列中加入BlockingQueue，则返回失败。　　　　　　
        @Override
        public void run() {
            String data = null;
            Random r = new Random();
            System.out.println("启动生产者线程！");
            try {
                while (isRunning) {
                    System.out.println("正在生产数据。。。");
                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP)); // 取0~DEFAULT_RANGE_FOR_SLEEP值的一个随机数

                    data = "data:" + count.incrementAndGet(); // 从原子方式将count当前值+1
                    System.out.println(name + " 将数据：" + data + "放入队列。。。");
                    if (!queue.offer(data, 2, TimeUnit.SECONDS)) { //设定的等待时间为2s，如果超过2s还没加进去返回true
                        System.out.println("放入数据失败：" + data + "-----------------------"+queue.size());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("退出生产者线程！");
            }
        }
    }

    // 消费者
    static class Consumer implements Runnable {

        private BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }


        @Override
        public void run() {
            System.out.println("启动消费者线程！");
            Random r = new Random();
            boolean isRunning = true;
            try {
                while (isRunning) {
                    System.out.println("正从队列获取数据。。。");
                    String data = queue.poll(2, TimeUnit.SECONDS); ////有数据时直接从队列的队首取走，无数据时阻塞，在2s内有数据，取走，超过2s还没数据，返回失败
                    if (null != data) {
                        System.out.println("拿到数据：" + data);
                        System.out.println("正在消费数据：" + data);
                        Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                    } else {
                        // // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                        isRunning = false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("退出消费者线程！");
            }
        }
    }


}




