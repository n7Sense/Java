package Multithreading.z_example.synchronization.eg1;

public class Display {

    public  void wish(String name)throws InterruptedException{

        for(int i=0; i<5; i++){
            System.out.println("Good Morning : "+name);
            Thread.sleep(1000);
        }
    }

}
