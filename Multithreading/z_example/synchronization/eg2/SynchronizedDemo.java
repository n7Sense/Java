package Multithreading.z_example.synchronization.eg2;

public class SynchronizedDemo {

    public static void main(String[] args) {
        Display d = new Display();
        MyThread t1 = new MyThread(d, "Sunita");
        MyThread t2 = new MyThread(d, "Rahul");

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
