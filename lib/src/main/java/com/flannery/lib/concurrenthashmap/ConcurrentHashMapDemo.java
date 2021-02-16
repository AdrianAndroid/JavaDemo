package com.flannery.lib.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {


    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("111", "111");
        map.put("222", "222");

        System.out.println(map.get("111"));
        System.out.println(map.get("333"));

        map.remove("222");
        System.out.println(map.get("222"));
    }


}
