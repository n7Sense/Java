package Multithreading.z_example.synchronization.eg3;

public class Display {

    public synchronized void displayNumber() throws InterruptedException{

        for(int i=0; i<5; i++){
            System.out.print(i+" ");
            Thread.sleep(1000);
        }

    }
    public synchronized void displayChar() throws InterruptedException{

        for(int i=65; i<75; i++){
            System.out.print((char)i+" ");
            Thread.sleep(1000);
        }

    }
}
