package cn.kuwo.javalib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import jdk.jfr.ContentType;

public class MyClass {

//    @ContentType
    private static volatile String test;

    public static void main(String[] args) {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


    public static class Child extends Parent {

        static String childName = "hello";

        {
            System.out.println("child block");
        }

        static {
            System.out.println("child static block");
        }

        public Child() {
            System.out.println("child constructor");
        }
    }


    public static class Parent {

        static String name = "hello";

        {
            System.out.println("parent block");
        }

        static {
            System.out.println("parent static block");
        }

        public Parent() {
            System.out.println("parent constructor");
        }
    }


    public static void main2(String[] args) {
        String format = String.format("%02d:%02d", 128 / 60, 28 % 60);
        System.out.println(format);
    }


    public static Integer hashCode(String str, Integer multiplier) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = multiplier * hash + str.charAt(i);
        }
        return hash;
    }


    /**
     * https://www.zhihu.com/question/24381016
     * 计算 hash code 冲突率，顺便分析一下 hash code 最大值和最小值，并输出
     */
//    public static void calculateConflictRate(Integer multiplier, List<Integer> hashs) {
//        Comparator<Integer> cp = (x, y) -> x > y ? 1 :
//    }
    public static int hashCode(char[] value, int prim) {
        int h = 0;
        for (int i = 0; i < value.length; i++) {
            h = prim * h + value[i];
        }
        return h;
    }

    public static void calculateConflictRate(Integer multiplier, List<Integer> hashes) {
        //Comparator<Integer> cp = (x, y) -> x > y ? 1 : (x < y ? -1 : 0);
//        Comparator<Integer> cp2 = (x, y) -> x.compareTo(y);
        Comparator<Integer> cp = new Comparator<Integer>() {
            @Override
            public int compare(Integer x, Integer y) {
                return x.compareTo(y);
            }
        };
        int maxHash = hashes.stream().max(cp).get();
        int minHash = hashes.stream().min(cp).get();

        // 计算冲突数及冲突率
        int uniqueHashNum = (int) hashes.stream().distinct().count();
        int conflictNum = hashes.size() - uniqueHashNum;
        double conflictRate = (conflictNum * 1.0) / hashes.size();

        System.out.println(String.format("multiplier=%4d, minHash=%11d, maxHash=%10d, conflictNum=%6d, conflictRate=%.4f%%",
                multiplier, minHash, maxHash, conflictNum, conflictRate * 100));
    }

    public static void main3(String[] args2) throws IOException {
        List<String> list = new ArrayList<>(235886);
        Reader reader = new FileReader("/usr/share/dict/words");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String string;
        while ((string = bufferedReader.readLine()) != null) {
            list.add(string);
        }
        bufferedReader.close();
        reader.close();

        List<Integer> prims = Arrays.asList(2, 3, 5, 7, 11, 17, 30, 31, 32, 33, 36, 37, 41, 47, 101, 199);

        for (Integer prim : prims) {
            List<Integer> result = new ArrayList<>();
            for (String s : list) {
                result.add(hashCode(s.toCharArray(), prim));
            }
            calculateConflictRate(prim, result);
        }
    }
}