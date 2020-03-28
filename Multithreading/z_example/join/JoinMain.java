package Multithreading.z_example.join;

public class JoinMain {
    public static void main(String[] args) throws InterruptedException{
        Thread t = new Thread(new JoinThread());
        t.start();

        // Waits for 1000ms this thread to die.
        t.join(1000);

        System.out.println("\nJoining after 1000"+ " mili seconds: \n");
        System.out.println("Current thread: "+t.getName());
        // Checks if this thread is alive
        System.out.println("Is alive? " + t.isAlive());
    }
}
