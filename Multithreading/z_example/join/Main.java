package Multithreading.z_example.join;

public class Main {

    public static void main(String[] args) throws InterruptedException{

        MyThread t1 = new MyThread();
        t1.start();
        t1.join(10000);

        for(int i=0; i<10; i++) {
            System.out.println("Ram Threadd");
        }
    }
}
