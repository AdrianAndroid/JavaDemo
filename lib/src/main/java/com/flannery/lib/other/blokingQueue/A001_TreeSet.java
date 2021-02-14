package cn.kuwo.javalib.blokingQueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class A001_TreeSet {

    public static void main(String[] args) {
        test3();
    }

    /*
    输出结果：
        abc
        rst
        xyz
     */
    static void test1() {
        Set<String> ts = new TreeSet<String>();
        ts.add("abc");
        ts.add("xyz");
        ts.add("rst");
        Iterator<String> it = ts.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    // 实现了Comparable
    /*
        学号：4		姓名：wangwu
        学号：3		姓名：mazi
        学号：3		姓名：wangmazi
        学号：2		姓名：lisi
        学号：1		姓名：zhangsan
     */
    static void test2() {
        Set<Teacher> ts = new TreeSet<Teacher>();
        ts.add(new Teacher("zhangsan", 1));
        ts.add(new Teacher("lisi", 2));
        ts.add(new Teacher("wangmazi", 3));
        ts.add(new Teacher("wangwu", 4));
        ts.add(new Teacher("mazi", 3));
        Iterator<Teacher> it = ts.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    static class Teacher implements Comparable {
        int num;
        String name;

        Teacher(String name, int num) {
            this.num = num;
            this.name = name;
        }

        public String toString() {
            return "学号：" + num + "\t\t姓名：" + name;
        }

        //o中存放时的红黑二叉树中的节点，从根节点开始比较
        public int compareTo(Object o) {
            Teacher ss = (Teacher) o;
            int result = Integer.compare(ss.num, num);//降序
            //int result = num > ss.num ? 1 : (num == ss.num ? 0 : -1);//升序
            if (result == 0) {
                result = name.compareTo(ss.name);
            }
            return result;
        }
    }

    /*
    学号：1    姓名：lisi
    学号：2    姓名：zhangsan
    学号：3    姓名：mazi
    学号：3    姓名：wangmazi
     */
    static void test3() {
        TreeSet<Teacher2> ts = new TreeSet<Teacher2>(new TeacherCompare());
        ts.add(new Teacher2("zhangsan", 2));
        ts.add(new Teacher2("lisi", 1));
        ts.add(new Teacher2("wangmazi", 3));
        ts.add(new Teacher2("mazi", 3));
        Iterator<Teacher2> it = ts.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    static class Teacher2 {
        int num;
        String name;

        Teacher2(String name, int num) {
            this.num = num;
            this.name = name;
        }

        public String toString() {
            return "学号：" + num + "    姓名：" + name;
        }
    }

    static class TeacherCompare implements Comparator<Teacher2> {// 老师自带的一个比较器

        //o1中存放的事目标节点
        //o2中存放时的红黑二叉树中的节点，从根节点开始比较
        public int compare(Teacher2 o1, Teacher2 o2) {
//            Teacher2 s1 = (Teacher2) o1;// 转型
//            Teacher2 s2 = (Teacher2) o2;// 转型
            int result = Integer.compare(o1.num, o2.num);
            if (result == 0) {
                result = o1.name.compareTo(o2.name);
            }
            return result;
        }

    }

}


class TreeSetIteratorTest {

    public static void main(String[] args) {
        TreeSet set = new TreeSet();
        set.add("aaa");
        set.add("aaa");
        set.add("bbb");
        set.add("eee");
        set.add("ddd");
        set.add("ccc");

        // 顺序遍历TreeSet
        ascIteratorThroughIterator(set);
        // 逆序遍历TreeSet
        descIteratorThroughIterator(set);
        // 通过for-each遍历TreeSet。不推荐！此方法需要先将Set转换为数组
        foreachTreeSet(set);
    }

    // 顺序遍历TreeSet
    public static void ascIteratorThroughIterator(TreeSet set) {
        System.out.print("\n ---- Ascend Iterator ----\n");
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            System.out.printf("asc : %s\n", iter.next());
        }
    }

    // 逆序遍历TreeSet
    public static void descIteratorThroughIterator(TreeSet set) {
        System.out.printf("\n ---- Descend Iterator ----\n");
        for (Iterator iter = set.descendingIterator(); iter.hasNext(); )
            System.out.printf("desc : %s\n", (String) iter.next());
    }

    // 通过for-each遍历TreeSet。不推荐！此方法需要先将Set转换为数组
    private static void foreachTreeSet(TreeSet set) {
        System.out.printf("\n ---- For-each ----\n");
        String[] arr = (String[]) set.toArray(new String[0]); // 参数起到一个缓冲区的作用吧
        for (String str : arr)
            System.out.printf("for each : %s\n", str);
    }
}


class TreeSetTest {

    public static void main(String[] args) {
        testTreeSetAPIs();
    }

    // 测试TreeSet的api
    public static void testTreeSetAPIs() {
        String val;

        // 新建TreeSet
        TreeSet tSet = new TreeSet();
        // 将元素添加到TreeSet中
        tSet.add("aaa");
        // Set中不允许重复元素，所以只会保存一个“aaa”
        tSet.add("aaa");
        tSet.add("bbb");
        tSet.add("eee");
        tSet.add("ddd");
        tSet.add("ccc");
        System.out.println("TreeSet:" + tSet);

        // 打印TreeSet的实际大小
        System.out.printf("size : %d\n", tSet.size());

        for (Iterator iter = tSet.iterator(); iter.hasNext(); ) {
            System.out.printf("asc : %s\n", iter.next());
        }

        // 导航方法
        // floor(小于、等于)
        System.out.printf("floor bbb: %s\n", tSet.floor("bbb"));
        // lower(小于)
        System.out.printf("lower bbb: %s\n", tSet.lower("bbb"));
        // ceiling(大于、等于)
        System.out.printf("ceiling bbb: %s\n", tSet.ceiling("bbb"));
        System.out.printf("ceiling eee: %s\n", tSet.ceiling("eee"));
        // ceiling(大于)
        System.out.printf("higher bbb: %s\n", tSet.higher("bbb"));
        // subSet()
        System.out.printf("subSet(aaa, true, ccc, true): %s\n", tSet.subSet("aaa", true, "ccc", true));
        System.out.printf("subSet(aaa, true, ccc, false): %s\n", tSet.subSet("aaa", true, "ccc", false));
        System.out.printf("subSet(aaa, false, ccc, true): %s\n", tSet.subSet("aaa", false, "ccc", true));
        System.out.printf("subSet(aaa, false, ccc, false): %s\n", tSet.subSet("aaa", false, "ccc", false));
        // headSet()
        System.out.printf("headSet(ccc, true): %s\n", tSet.headSet("ccc", true));
        System.out.printf("headSet(ccc, false): %s\n", tSet.headSet("ccc", false));
        // tailSet()
        System.out.printf("tailSet(ccc, true): %s\n", tSet.tailSet("ccc", true));
        System.out.printf("tailSet(ccc, false): %s\n", tSet.tailSet("ccc", false));


        // 删除“ccc”
        tSet.remove("ccc");
        // 将Set转换为数组
        String[] arr = (String[]) tSet.toArray(new String[0]);
        for (String str : arr)
            System.out.printf("for each : %s\n", str);

        // 打印TreeSet
        System.out.printf("TreeSet:%s\n", tSet);

        // 遍历TreeSet
        for (Iterator iter = tSet.iterator(); iter.hasNext(); ) {
            System.out.printf("iter : %s\n", iter.next());
        }

        // 删除并返回第一个元素
        val = (String) tSet.pollFirst();
        System.out.printf("pollFirst=%s, set=%s\n", val, tSet);

        // 删除并返回最后一个元素
        val = (String) tSet.pollLast();
        System.out.printf("pollLast=%s, set=%s\n", val, tSet);

        // 清空HashSet
        tSet.clear();

        // 输出HashSet是否为空
        System.out.printf("%s\n", tSet.isEmpty() ? "set is empty" : "set is not empty");
    }
}



