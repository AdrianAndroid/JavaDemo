package cn.kuwo.javalib.blokingQueue;

import java.util.concurrent.PriorityBlockingQueue;

public class A011_PriorityBlockingQueue {

    public static void main(String[] args) {
        PriorityBlockingQueue<Integer> concurrentLinkedQueue = new PriorityBlockingQueue<>();
        concurrentLinkedQueue.offer(10);
        concurrentLinkedQueue.offer(20);
        concurrentLinkedQueue.offer(5);
        concurrentLinkedQueue.offer(1);
        concurrentLinkedQueue.offer(25);
        concurrentLinkedQueue.offer(30);
        // 输出元素排列
        concurrentLinkedQueue.stream().forEach(e-> System.out.println(e));
    }

}
