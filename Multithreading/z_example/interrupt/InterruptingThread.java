package Multithreading.z_example.interrupt;

public class InterruptingThread extends Thread{

    @Override
    public void run() {
        try{
            for(int i=0; i<5; i++){
                System.out.println("Chile : I Am Lazy Thread");
                Thread.sleep(2000);
            }
        }catch (InterruptedException ie){
            System.out.println("RE : IE : Child : I Got Interrupted");
        }

    }


}
