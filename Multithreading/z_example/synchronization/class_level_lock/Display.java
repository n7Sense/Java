package Multithreading.z_example.synchronization.class_level_lock;

public class Display {

    public static synchronized void wish(String name)throws InterruptedException{

        for(int i=0; i<5; i++){
            System.out.println("Good Morning : "+name);
            Thread.sleep(1000);
        }
    }

}
