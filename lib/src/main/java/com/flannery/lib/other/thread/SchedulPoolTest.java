package cn.kuwo.javalib.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.kuwo.javalib.T;

public class SchedulPoolTest {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("hh:mm:ss");
    private static final Random RANDOM = new Random();


    public static void schedule() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        printTime();
        scheduledExecutorService.schedule(new Task(), 3, TimeUnit.SECONDS);
    }

    public static void scheduleAtFixedRate() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        printTime();
        scheduledExecutorService.scheduleAtFixedRate(new Task(), 2, 10, TimeUnit.SECONDS);
    }

    public static void scheduleWithFixedDelay() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        printTime();
        scheduledExecutorService.scheduleWithFixedDelay(new Task(), 2, 10, TimeUnit.SECONDS);
    }


    public static void main(String[] args) {
//        schedule();
        //01:50:40
        //01:50:43

//        scheduleAtFixedRate();
        //01:51:19
        //01:51:21
        //01:51:31
        //01:51:41
        //01:51:51

        scheduleWithFixedDelay();
        //01:52:07
        //01:52:09
        //01:52:19
        //01:52:33
        //01:52:45
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            printTime();
            T.sleepSeconds(RANDOM.nextInt(5));
        }
    }

    public static void printTime() {
        System.out.println(FORMAT.format(new Date()));
    }

}
