package Multithreading.z_example.interrupt;

public class InterruptingThreadMain {

    public static void main(String[] args) {

        InterruptingThread t1 = new InterruptingThread();
        t1.start();
        t1.interrupt();
        System.out.println("Main : Thread End");
    }
}

/**
 * Interrupt
 *
 * If we comment line 9 then main thread wont interrupt child thread. in
 * this case child thread execute 5 times in loop,
 * If we not comment line 9 main thread interrupt child thread in this case
 * out/put thread is:
 *
 * Main : Thread End
 * Chile : I Am Lazy Thread
 * RE : IE : Chile : I Got Interrupted
 *
 * Process finished with exit code 0
 */
