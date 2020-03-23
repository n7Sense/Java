
class ThreadDemo{

    public static void main(String[] args) {

        MyRunnable mr = new MyRunnable();
        Thread t1 = new Thread();
        Thread t2 = new Thread(mr);

        case 1:  t1.start();    "Normal Method Call"
        case 2:  t1.run();      "Normal Method Call"
        case 3:  t2.start();    "New Thread Created"
        case 4:  t2.run();      "Normal Method Call"
        case 5:  mr.start();     RE: can not find symbol method start() location class MyRunnable;
        case 6:  mr.run();       "Normal Method Call"


    }
}
