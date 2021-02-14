package cn.kuwo.javalib.blokingQueue;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class A009_TransferQueue {
    private static TransferQueue<String> queue = new LinkedTransferQueue<>();


    public static void main(String[] args) throws InterruptedException {
        new Productor(1).start();

        Thread.sleep(100);

        System.out.println("over.size=" + queue.size());
    }

    //可以看到生产者线程会阻塞，因为调用transfer()的时候并没有消费者在等待获取数据。队列长度变成了1，说明元素e没有移交成功的时候，会被插入到阻塞队列的尾部。
    static class Productor extends Thread {
        private int id;

        public Productor(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                String result = "id=" + this.id;
                System.out.println("begin to produce." + result);
                queue.transfer(result);
                System.out.println("success to produce." + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

}

/*
2.tryTransfer(E e)若当前存在一个正在等待获取的消费者线程，则该方法会即刻转移e，并返回true;
  若不存在则返回false，但是并不会将e插入到队列中。这个方法不会阻塞当前线程，要么快速返回true，要么快速返回false。

3.hasWaitingConsumer()和getWaitingConsumerCount()用来判断当前正在等待消费的消费者线程个数。

4.tryTransfer(E e, long timeout, TimeUnit unit) 若当前存在一个正在等待获取的消费者线程，会立即传输给它;
  否则将元素e插入到队列尾部，并且等待被消费者线程获取消费掉。若在指定的时间内元素e无法被消费者线程获取，则返回false，同时该元素从队列中移除。
 */
class TransferQueueDemo {

    private static TransferQueue<String> queue = new LinkedTransferQueue<String>();

    public static void main(String[] args) throws Exception {

        new Productor(1).start();

        Thread.sleep(100);

        System.out.println("over.size=" + queue.size());//1

        Thread.sleep(1500);

        System.out.println("over.size=" + queue.size());//0
    }

    static class Productor extends Thread {
        private int id;

        public Productor(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                String result = "id=" + this.id;
                System.out.println("begin to produce." + result);
                queue.tryTransfer(result, 1, TimeUnit.SECONDS);
                System.out.println("success to produce." + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

//第一次还没到指定的时间，元素被插入到队列中了，所有队列长度是1；第二次指定的时间片耗尽，元素从队列中移除了，所以队列长度是0。