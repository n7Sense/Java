package Multithreading.z_example.join.eg4;

public class DeadLockBySelfThreadMain {

    public static void main(String[] args) throws InterruptedException{

        Thread.currentThread().join();
    }
}

/**
 * DeadLock
 *
 * Main thraed call join method on it'self means main thread want to wait(infinite time)
 * until Main thread, hear main thread wait for itself for complete, and program will
 * stuck is something like DeadLock
 */
