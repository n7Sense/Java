package Multithreading.z_example.basic;

public class MyThread extends Thread{

    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.start();
        mt.start();
    }
}
