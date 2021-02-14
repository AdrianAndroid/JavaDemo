package cn.kuwo.javalib.volatiletest;

//import cn.kuwo.javalib.T;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VisibilityTest2 {

    private int i = 0;

    public void write(int j) {
        i = j;
    }

    public int read() {
        return i;
    }

    public static void main(String[] args) {
        VisibilityTest2 visibilityTest2 = new VisibilityTest2();
//        Thread thread = new Thread(() -> visibilityTest2.write(5), "BBB ");
//        Thread thread1 = new Thread(() -> {
//            int read = visibilityTest2.read();
//            int i = read;
//        }, "AAA ");
//        thread.start();
//        thread1.start();

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(() -> {
            visibilityTest2.write(5);
        });
        service.submit(() -> {
            int read = visibilityTest2.read();
            int i = read;
        });
    }

}
