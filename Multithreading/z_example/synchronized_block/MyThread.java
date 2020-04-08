package Multithreading.z_example.synchronized_block;

public class MyThread extends Thread {

    Display d;
    MyThread(Display d){
        this.d = d;
    }

    @Override
    public void run() {
        d.display("Sunita");
    }
}
