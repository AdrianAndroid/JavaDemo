package cn.kuwo.javalib.thread;

import java.util.Timer;
import java.util.TimerTask;

public class DemoTimerTask {
    public static void main(String[] args) {
        Timer timer = new Timer();
        long start = System.currentTimeMillis();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long end = System.currentTimeMillis();
                long l = (end - start) / 1000;
                System.out.println("定制任务1执行了 --> " + l);
            }
        }, 2000, 1000);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long end = System.currentTimeMillis();
                long l = (end - start) / 1000;
                System.out.println("定制任务2执行了 --> " + l);
            }
        }, 2000, 1000);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long end = System.currentTimeMillis();
                long l = (end - start) / 1000;
                System.out.println("定制任务3执行了 --> " + l);
            }
        }, 2000, 1000);

    }


}
