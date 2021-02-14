package cn.kuwo.javalib.blokingQueue;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.LinkedBlockingQueue;

public class A007_LinkedBlockingQueue {

    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("111");
        queue.add("222");
        queue.add("333");
        queue.add("444");
        queue.add("555");

        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
            iterator.remove();
        }




    }

}
