package cn.kuwo.javalib.blokingQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ArrayListDemo {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();

//        test__();

        testLiterator();
    }

    static void testLiterator() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }
        Iterator<String> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            printArrayString(list);
            iterator2.next();
            iterator2.remove();
        }

    }

    static void testUmodify() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("" + i);
        }
        List<String> strings1 = Collections.unmodifiableList(strings);
        strings1.add("z");
    }

    interface PValue<T> {
        Object call(T t);
    }

    static <T> void printArrayString(T... obj) {

        for (T t : obj) {
            System.out.println(t);
        }
        System.out.println("==============");
    }

    static <T> void printArray(PValue<T> pValue, T... obj) {
        for (T t : obj) {
            System.out.println(pValue.call(t));
        }
        System.out.println("==============");
    }

    static void test__() {
        Object[] elementOld = new String[]{"1", "2", "3", "4"};
        String[] elementData = (String[]) elementOld;
        elementData[3] = "z";

        printArray(new PValue<Object>() {
            @Override
            public Object call(Object s) {
                return s;
            }
        }, elementOld);
        printArray(new PValue<String>() {
            @Override
            public Object call(String s) {
                return s;
            }
        }, elementData);
    }

    static void testSystemArraycopy() {
        byte[] srcBytes = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};  // 源数组

        byte[] destBytes = new byte[5]; // 目标数组

        System.arraycopy(srcBytes, 0, destBytes, 0, destBytes.length);
        System.out.println(destBytes.length);
        System.out.println("==========");
        for (byte destByte : destBytes) {
            System.out.println(destByte);
        }
    }


    // 初始化容量大小
    static void test001() {
        ArrayList<String> list = new ArrayList(1);
        //list.add("001");
        //list.add("002");
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }

        ArrayList<String> list2 = new ArrayList();
        list2.add("" + 3);

        ArrayList<String> list3 = new ArrayList();
        list2.add("" + 3);

        boolean b = list.removeAll(list2);
        boolean b1 = list2.retainAll(list3);
        System.out.println("=====" + b);
        System.out.println("=====" + b1);

        System.out.println("=====");

        // list ===>
        for (String s : list) {
            System.out.println(s);
        }
        // list2 ==>
    }

    void test() {
        List<String> strings = Collections.unmodifiableList(Arrays.asList("1", "2"));
    }
}
