package Multithreading.z_example.join;

public class JoinThread extends Thread {

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("Current thread: "+ t.getName());
        // checks if current thread is alive
        System.out.println("Is Alive? "+ t.isAlive());
    }
}
