package Multithreading.z_example.synchronized_block;

public class SynchronizedBlockDemo {

    public static void main(String[] args) {
        Display d = new Display();
        Display d2 = new Display();
        MyThread t1 = new MyThread(d);
        MyThread2 t2 = new MyThread2(d2);
        t1.start();
        t2.start();

    }
}
/**
 * Display d = new Display();
*  Display d2 = new Display();
*  MyThread t1 = new MyThread(d);
*  MyThread2 t2 = new MyThread2(d2);
*  t1.start();
*  t2.start();
 * ir-regular output
 *
 * Object Level : Sunita
 * Object Level : Sunita
 * Object Level : Sunita
 * Object Level : Rahul
 * Object Level : Rahul
 * Object Level : Rahul
 * Object Level : Rahul
 * Object Level : Rahul
 * Object Level : Sunita
 * Object Level : Sunita
 *
 * --------------------------------------------------
 *
 * but if synchronized(Disaply.class)
 * then we will get Regular OutPut
 * because of class level lock
 *
 *Object Level : Sunita
 * Object Level : Sunita
 * Object Level : Sunita
 * Object Level : Sunita
 * Object Level : Sunita
 * Object Level : Rahul
 * Object Level : Rahul
 * Object Level : Rahul
 * Object Level : Rahul
 * Object Level : Rahul
 * */