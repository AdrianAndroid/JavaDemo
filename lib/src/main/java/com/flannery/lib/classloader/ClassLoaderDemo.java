package com.flannery.lib.classloader;

public class ClassLoaderDemo {

    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoaderDemo.class.getClassLoader();
        System.out.println(classLoader.toString());

        ClassLoader parent = classLoader.getParent();
        System.out.println(parent);

        ClassLoader parent1 = parent.getParent();
        System.out.println(parent1);
    }

}
