package Multithreading.z_example.synchronization.eg3;

public class MyThread2 extends Thread{
    Display d;
    MyThread2(Display d){
        this.d = d;
    }

    @Override
    public void run() {
        try{
            d.displayChar();
        }catch (InterruptedException ie){

        }
    }
}
