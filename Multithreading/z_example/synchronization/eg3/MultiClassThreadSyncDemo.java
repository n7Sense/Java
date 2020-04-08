package Multithreading.z_example.synchronization.eg3;

public class MultiClassThreadSyncDemo {
    public static void main(String[] args) {

        Display d = new Display();
        MyThread1 mt1 = new MyThread1(d);
        MyThread2 mt2 = new MyThread2(d);

        mt1.start();
        mt2.start();
    }
}

/**
 * Without synchronization we will get irregular out put
 *
 * 0 A 1 B 2 C D 3 E 4 F G H I J
 *
 * with synchronized  method we will get regular out put
 *
 * 0 1 2 3 4 A B C D E F G H I J
 * */
