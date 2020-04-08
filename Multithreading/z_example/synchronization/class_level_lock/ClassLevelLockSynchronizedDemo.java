package Multithreading.z_example.synchronization.class_level_lock;

public class ClassLevelLockSynchronizedDemo {

    public static void main(String[] args) {
        Display d1 = new Display();
        Display d2 = new Display();
        MyThread t1 = new MyThread(d1, "Sunita");
        MyThread t2 = new MyThread(d2, "Rahul");
        t1.start();
        t2.start();
    }
}
/**
 * In This example we will get output in order
 *
 * Good Morning : Sunita
 * Good Morning : Sunita
 * Good Morning : Sunita
 * Good Morning : Sunita
 * Good Morning : Sunita
 * Good Morning : Rahul
 * Good Morning : Rahul
 * Good Morning : Rahul
 * Good Morning : Rahul
 * Good Morning : Rahul
 *
 */
