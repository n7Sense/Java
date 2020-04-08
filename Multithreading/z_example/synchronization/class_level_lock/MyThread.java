package Multithreading.z_example.synchronization.class_level_lock;

public class MyThread extends  Thread{

    Display d;
    String name;

    MyThread(Display d, String name){
        this.d = d;
        this.name = name;
    }

    @Override
    public void run(){
        try {
            d.wish(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
