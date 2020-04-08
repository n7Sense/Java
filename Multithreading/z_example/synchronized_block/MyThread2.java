package Multithreading.z_example.synchronized_block;

public class MyThread2 extends Thread {

    Display d;
    MyThread2(Display d){
        this.d = d;
    }

    @Override
    public void run() {
        d.display("Rahul");
    }
}
