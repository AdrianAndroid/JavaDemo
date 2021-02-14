package cn.kuwo.javalib.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// 我们可以给自己一个集合加上锁让它变成安全的
public class MyCollections {

    public static void main(String[] args) {
        Mylist list = new Mylist(new ArrayList());
    }

    private static class Mylist implements List {

        private List list;

        public Mylist(List list) {
            this.list = list;
        }

        Object lock = new Object(); //锁

        @Override
        public int size() {
            synchronized (lock) {
                return list.size();
            }
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public boolean add(Object o) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean addAll(Collection collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, Collection collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Object get(int i) {
            return null;
        }

        @Override
        public Object set(int i, Object o) {
            return null;
        }

        @Override
        public void add(int i, Object o) {

        }

        @Override
        public Object remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator listIterator() {
            return null;
        }

        @Override
        public ListIterator listIterator(int i) {
            return null;
        }

        @Override
        public List subList(int i, int i1) {
            return null;
        }

        @Override
        public boolean retainAll(Collection collection) {
            return false;
        }

        @Override
        public boolean removeAll(Collection collection) {
            return false;
        }

        @Override
        public boolean containsAll(Collection collection) {
            return false;
        }

        @Override
        public Object[] toArray(Object[] objects) {
            return new Object[0];
        }


    }


}
