package Multithreading.z_example.synchronized_block;

public class Display {

    public void display(String name){

        // 1lack Lines of code
        synchronized (Display.class) {
            for (int i = 0; i < 5; i++) {
                System.out.println("Object Level : "+name);
            }
        }

        // 1lack Lines of code

    }
}
