package com.flannery.lib.hashmap;

import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {
        System.out.println("hello wold!");
        try {
            int count = Integer.MAX_VALUE;
            HashMap<String, String> h = new HashMap<>(count);
            for (int i = 0; i < count; i++) {
                h.put(String.valueOf(i), "");
                System.out.println(System.currentTimeMillis());
            }
            System.out.println(h.size());
            h.put("xx", "");
            h.put("xx2", "");
            h.put("xx3", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
