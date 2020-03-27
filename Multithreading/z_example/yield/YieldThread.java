package Multithreading.z_example.yield;

public class YieldThread extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("Chile Thread");
        Thread.yield();
    }
}
