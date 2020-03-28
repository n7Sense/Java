package Multithreading.z_example.join.eg2;

public class ChildThreadWantToWaitForMainThreadMain {

    //Note In this wxample how Current Thread wait for main thread how to apply join on main thread
    public static void main(String[] args) throws InterruptedException{

        ChildThreadWantToWaitForMainThreadDemo.mainThread = Thread.currentThread();
        ChildThreadWantToWaitForMainThreadDemo t1 = new ChildThreadWantToWaitForMainThreadDemo();
        t1.start();
        System.out.println("Main Thread");
    }
}
