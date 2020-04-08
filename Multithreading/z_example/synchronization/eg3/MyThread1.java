package Multithreading.z_example.synchronization.eg3;

import Multithreading.z_example.basic.MyThread;

public class MyThread1 extends Thread{
    Display d;
    MyThread1(Display d){
        this.d = d;
    }

    @Override
    public void run() {
        try{
            d.displayNumber();
        }catch (InterruptedException ie){

        }
    }
}
