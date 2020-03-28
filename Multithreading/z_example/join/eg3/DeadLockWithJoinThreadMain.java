package Multithreading.z_example.join.eg3;

public class DeadLockWithJoinThreadMain {

    public static void main(String[] args) throws InterruptedException {

        DeadLockWithJoinThread t1 = new DeadLockWithJoinThread();
        DeadLockWithJoinThread.mainThread = Thread.currentThread();
        t1.start();
        t1.join();
        System.out.println("Main Thread");
    }
}
/**
 * DedLock
 *
 * If the main thread call join method on Child Thread and child thread call join method
 * of Mian Thread object then both the thread will wait forever, and the program will
 * stucked, this is something like DeadLock
 */