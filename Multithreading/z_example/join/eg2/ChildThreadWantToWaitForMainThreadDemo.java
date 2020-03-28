package Multithreading.z_example.join.eg2;

import java.util.concurrent.TimeUnit;

public class ChildThreadWantToWaitForMainThreadDemo extends Thread {

    //Note In this wxample how Current Thread wait for main thread how to apply join on main thread
    public static Thread mainThread;

    @Override
    public void run() {

        try{
            mainThread.join();
        }catch (InterruptedException ie){

        }
        System.out.println("Child Thread");
    }
}
