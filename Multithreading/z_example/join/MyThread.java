package Multithreading.z_example.join;

public class MyThread extends Thread {

    @Override
    public void run(){

        for(int i=0; i<10; i++){
            System.out.println("Sheeta Threadd");
            try{
                Thread.sleep(2000);
            }catch (InterruptedException ie){

            }
        }
    }
}
