package Multithreading.z_example.synchronization.eg2;

public class SynchronizedDemo {

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
 * Display d1 = new Display();
 *         MyThread t1 = new MyThread(d1, "Sunita");
 *         MyThread t2 = new MyThread(d1, "Rahul");
 *
 *         t1.start();
 *         t2.start();
 * according to above In This example we will get output in order
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

/**
 * Display d1 = new Display();
 *         Display d2 = new Display();
 *         MyThread t1 = new MyThread(d1, "Sunita");
 *         MyThread t2 = new MyThread(d2, "Rahul");
 *         t1.start();
 *         t2.start();
 *
 *  In This example we will get irregular-output or un-ordered
 *
 * Good Morning : Sunita
 * Good Morning : Rahul
 * Good Morning : Sunita
 * Good Morning : Rahul
 * Good Morning : Sunita
 * Good Morning : Rahul
 * Good Morning : Sunita
 * Good Morning : Rahul
 * Good Morning : Sunita
 * Good Morning : Rahul
 *
 * */
