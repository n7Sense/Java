package Multithreading.z_example.join.eg3;

public class DeadLockWithJoinThread extends Thread {

    public static Thread mainThread;

    @Override
    public void run() {
        try{
            mainThread.join();
        }catch (InterruptedException ie){

        }
        System.out.println("Main Thread");
    }
}
