package cn.kuwo.javalib.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class DemoCallable implements Callable<String> {

    @Override
    public String call() {
        return null;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DemoCallable c = new DemoCallable();
        FutureTask<String> future = new FutureTask<>(c);
        Thread thread = new Thread(future);
        thread.start();
        String result = future.get();
        System.out.println(result);
    }
}
