package cn.kuwo.javalib.blokingQueue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class A002_failfast {
    private static List<String> list = new ArrayList<String>();

    public static void main(String[] args) {
        // 两个线程对同一个ArrayList进行操作
        new ThreadOne().start();
        new ThreadTwo().start();
    }

    private static void printAll() {
        String value = null;
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            value = iter.next();
            System.out.println(value);
        }
    }

    // 线程一：向list中一次添加数据，然后printAll整个list
    private static class ThreadOne extends Thread {
        @Override
        public void run() {

            for (int i = 0; i < 5; i++) {
                list.add(String.valueOf("线程一：java的架构师技术栈" + i));
                printAll();
            }
        }
    }

    // 线程二：对ArrayList实现同样的操作
    private static class ThreadTwo extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 6; i++) {
                list.add(String.valueOf("线程二：java的架构师技术栈" + i));
                printAll();
            }
        }
    }
}
