package Multithreading.z_example.synchronization.eg1;

public class NonSynchronizedDemo {

    public static void main(String[] args) {
        Display d = new Display();
        MyThread t1 = new MyThread(d, "Sunita");
        MyThread t2 = new MyThread(d, "Rahul");

        t1.start();
        t2.start();
    }
}

/**
 * In This Example We will get Mixed out Put
 * Good Morning : Sunita
 * Good Morning : Rahul
 * Good Morning : Rahul
 * Good Morning : Sunita
 * Good Morning : Sunita
 * Good Morning : Rahul
 * Good Morning : Sunita
 * Good Morning : Rahul
 * Good Morning : Sunita
 * Good Morning : Rahul
 * */
