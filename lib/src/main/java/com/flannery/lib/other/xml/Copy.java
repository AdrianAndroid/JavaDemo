package cn.kuwo.javalib.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Copy {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("shadowCop_constructor");
        shadowCop_constructor();
        System.out.println("=============================");
        System.out.println("shadowCop_clone");
        shadowCop_clone();
        System.out.println("=============================");
        System.out.println("deepCopy_clone");
        deepCopy_clone();
        System.out.println("=============================");
        System.out.println("deepCopy_serialize");
        deepCopy_serialize();
        System.out.println("=============================");
    }

    // 浅拷贝-一、通过拷贝构造方法实现浅拷贝：
    public static void shadowCop_constructor() {
        Age a = new Age(20);
        Person p1 = new Person(a, "摇头耶稣");
        Person p2 = new Person(p1);
        System.out.println("p1是" + p1);
        System.out.println("p2是" + p2);
        //修改p1的各属性值，观察p2的各属性值是否跟随变化
        p1.setName("小傻瓜");
        a.setAge(99);
        System.out.println("修改后的p1是" + p1);
        System.out.println("修改后的p2是" + p2);
    }

    // 浅拷贝-二、通过重写clone()方法进行浅拷贝：
    public static void shadowCop_clone() {
        Age a = new Age(20);
        Student stu1 = new Student("摇头耶稣", a, 175);

        //通过调用重写后的clone方法进行浅拷贝
        Student stu2 = (Student) stu1.clone();
        System.out.println(stu1.toString());
        System.out.println(stu2.toString());

        //尝试修改stu1中的各属性，观察stu2的属性有没有变化
        stu1.setName("大傻子");
        //改变age这个引用类型的成员变量的值
        a.setAge(99);
        //stu1.setaAge(new Age(99));    使用这种方式修改age属性值的话，stu2是不会跟着改变的。因为创建了一个新的Age类对象而不是改变原对象的实例值
        stu1.setLength(216);
        System.out.println(stu1.toString());

        System.out.println(stu2.toString());
    }


    static class Person {
        // 两个属性值：分别代表值传递和引用传递
        private Age age; // 引用传递
        private String name; // 值传递

        public Person(Age age, String name) {
            this.age = age;
            this.name = name;
        }

        //拷贝构造方法
        public Person(Person p) {
            this.name = p.name;
            this.age = p.age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name + " " + this.age;
        }
    }


    static class Age implements Serializable {
        private int age;

        public Age(int age) {
            this.age = age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return this.age;
        }

        public String toString() {
            return getAge() + "";
        }
    }

    /*
     * 创建学生类
     */
    static class Student implements Cloneable, Serializable {
        //学生类的成员变量（属性）,其中一个属性为类的对象
        private String name;
        private Age aage;
        private int length;

        //构造方法,其中一个参数为另一个类的对象
        public Student(String name, Age a, int length) {
            this.name = name;
            this.aage = a;
            this.length = length;
        }

        //eclipe中alt+shift+s自动添加所有的set和get方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Age getaAge() {
            return this.aage;
        }

        public void setaAge(Age age) {
            this.aage = age;
        }

        public int getLength() {
            return this.length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        //设置输出的字符串形式
        public String toString() {
            return "姓名是： " + this.getName() + "， 年龄为： " + this.getaAge().toString() + ", 长度是： " + this.getLength();
        }

        //重写Object类的clone方法
        public Object clone() {
            Object obj = null;
            //调用Object类的clone方法，返回一个Object实例
            try {
                obj = super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return obj;
        }
    }


    // 深拷贝-一、通过重写clone方法来实现深拷贝
    public static void deepCopy_clone() {
        Age2 a = new Age2(20);
        Student2 stu1 = new Student2("摇头耶稣", a, 175);

        //通过调用重写后的clone方法进行浅拷贝
        Student2 stu2 = (Student2) stu1.clone(); // clone 里面处理了深拷贝
        System.out.println(stu1.toString());
        System.out.println(stu2.toString());
        System.out.println();

        //尝试修改stu1中的各属性，观察stu2的属性有没有变化
        stu1.setName("大傻子");
        //改变age这个引用类型的成员变量的值
        a.setAge(99);
        //stu1.setaAge(new Age(99));    使用这种方式修改age属性值的话，stu2是不会跟着改变的。因为创建了一个新的Age类对象而不是改变原对象的实例值
        stu1.setLength(216);
        System.out.println(stu1.toString());
        System.out.println(stu2.toString());
    }

    // 深拷贝-二、通过对象序列化实现深拷贝
    public static void deepCopy_serialize() throws IOException, ClassNotFoundException {
        Age a = new Age(20);
        Student stu1 = new Student("摇头耶稣", a, 175);
        // 通过序列化方法实现深拷贝
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(stu1);
        oos.flush();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Student stu2 = (Student) ois.readObject();
        System.out.println(stu1.toString());
        System.out.println(stu2.toString());
        System.out.println();

        //尝试修改stu1中的各属性，观察stu2的属性有没有变化
        stu1.setName("大傻子");
        //改变age这个引用类型的成员变量的值
        a.setAge(99);
        stu1.setLength(216);
        System.out.println(stu1.toString());
        System.out.println(stu2.toString());
    }

    static class Age2 implements Cloneable {
        //年龄类的成员变量（属性）
        private int age;

        //构造方法
        public Age2(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String toString() {
            return this.age + "";
        }

        //重写Object的clone方法

        @Override
        protected Object clone() throws CloneNotSupportedException {

            return super.clone();
        }
    }

    /*
     * 创建学生类
     */
    static class Student2 implements Cloneable {
        //学生类的成员变量（属性）,其中一个属性为类的对象
        private String name;
        private Age2 aage;
        private int length;

        //构造方法,其中一个参数为另一个类的对象
        public Student2(String name, Age2 a, int length) {
            this.name = name;
            this.aage = a;
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Age2 getaAge() {
            return this.aage;
        }

        public void setaAge(Age2 age) {
            this.aage = age;
        }

        public int getLength() {
            return this.length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String toString() {
            return "姓名是： " + this.getName() + "， 年龄为： " + this.getaAge().toString() + ", 长度是： " + this.getLength();
        }

        //重写Object类的clone方法
        public Object clone() {
            Object obj = null;
            //调用Object类的clone方法——浅拷贝
            try {
                obj = super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            //调用Age类的clone方法进行深拷贝
            //先将obj转化为学生类实例
            Student2 stu = (Student2) obj;
            //学生类实例的Age对象属性，调用其clone方法进行拷贝
            try {
                stu.aage = (Age2) stu.getaAge().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return obj;
        }
    }

}
