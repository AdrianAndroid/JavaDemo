package cn.kuwo.javalib.blokingQueue;

import java.security.PublicKey;

public class A005_TryFinallyReturn {
    public static void main(String[] args) {
        System.out.println(test());
    }

    public static String test() {
        try {
            System.out.println("这里是try");
            return test2();//x"try的返回值";
        } finally {
            System.out.println("这里是Finllay");
        }
    }


    public static String test2() {
        System.out.println("return调用的内容");
        return "test2的返回值";
    }


}
