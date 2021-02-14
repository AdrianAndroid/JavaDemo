package cn.kuwo.javalib.blokingQueue;

import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class A010_DelayQueue {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayElement> delayQueue = new DelayQueue<>();

        delayQueue.add(new DelayElement<String>("test1", 1000));
        System.out.println("获取刚存入的test1 - " + delayQueue.take()); // 没有过期的返回null
        delayQueue.add(new DelayElement<String>("test2", 800));
        delayQueue.add(new DelayElement<String>("test3", 2000));
        delayQueue.add(new DelayElement<String>("test4", 3000));

        for (DelayElement delayElement : delayQueue) {
            System.out.println(delayElement);
        }

        System.out.println("队列大小：" + delayQueue.size());
        //立即返回，同时删除元素，如果没有过期元素则返回null
        System.out.println("poll方法获取：" + delayQueue.poll());
        //立即返回，不会删除元素，如果没有过期元素也会返回该元素，只有当队列为null时才会返回null
        System.out.println("peek方法获取：" + delayQueue.peek());
        //进行阻塞
        System.out.println("take方法获取：" + delayQueue.take().getData());

        //立即返回，但不能保证顺序
        Iterator<DelayElement> iterator = delayQueue.iterator();
        while (iterator.hasNext()) {
            DelayElement element = iterator.next();
            System.out.println("iterator获取：" + element);
        }
    }


    static class DelayElement<E> implements Delayed {
        private final long expireTime;
        private final E e;

        DelayElement(E e, long expireTime) {
            this.expireTime = expireTime;
            this.e = e;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            // 判断是否过期
            long convert = unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            return convert;
        }

        @Override
        public int compareTo(Delayed delayed) {
            //排序规则
            DelayElement that = (DelayElement) delayed;
            if (this.expireTime < that.expireTime) {
                return -1;
            } else if (this.expireTime > that.expireTime) {
                return 1;
            } else {
                return 0;
            }
        }

        public E getData() {
            return e;
        }

        @Override
        public String toString() {
            return "DelayElement{" +
                    "expireTime=" + expireTime +
                    ", e=" + e +
                    '}';
        }
    }

}
