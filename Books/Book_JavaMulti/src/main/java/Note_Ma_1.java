public class Note_Ma_1 {

    private class Test1 {
        private int count = 10;
        private final Object ob = new Object(); // act like a watcher
        // ob => heap => lock is stored in heap object, not this reference
        // 互斥锁（英语：英语：Mutual exclusion，缩写Mutex）是一种用于多线程编程中，
        // 防止两条线程同时对同一公共资源（比如全域變數）进行读写的机制

        public void something() {
            synchronized (ob) { // Any thread need ob's lock (in heap) to do following code
                count--;
                System.out.println(Thread.currentThread().getName() + " count = " + count);
            }
        }
    }

    private class Test2 {
        private int count = 10;
        // same as last one. Instead lock itself
        // synchronized locked an object not a block of code

        public void something() {
            synchronized (this) { // Any thread need this's lock to do following code
                count--;
                System.out.println(Thread.currentThread().getName() + " count = " + count);
            }
        }
    }

    private class Test3 {
        private int count = 10;

        public synchronized void something() { // same as test2, is locking current object
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}

class Test4 {
    private static int count = 10; // 静态

    // synchronized static => lock this class
    public synchronized static void something() { // Same as synchronized(Test4.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void other() {
        // Test4.class is the Class Object in java.lang
        synchronized (Test4.class) { // is it ok to write synchronized(this) ?
            // no b/c static field or method don't need new object to visit
            count--;
        }
    }
}

class Test5 implements Runnable {
    private int count = 10;

    // Add synchronized to run and solve below problems
    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
    // a block of synchronized is atomic operation => can't be divided

    public static void main(String[] args) {
        Test5 t = new Test5();
        for (int i = 0; i < 10; i++) { // Note 10 thread is visiting same one object t
            // if object is newed here, then won't have a problem either
            // Test5 t = new Test5(); this will all give 9 !!!
            new Thread(t, "Thread " + i).start(); // sometimes they are reading the same count !!!
        } // also there are no 9 !!!
        // when first thread done count-- then go for print
        // second thread done count-- again before first one done printing
        // Thus Thread0 and Thread1 both print 8
    }
}

class Test6 {
    // Can synchronized method and normal method being called at the same time?

    private synchronized void something1() { // object is lock, but something2 DON'T NEED lock at all
        System.out.println(Thread.currentThread().getName() + " something1 starts...");
        try {
            Thread.sleep(5000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " something1 ends...");
    }

    private void something2() { // don't need lock
        System.out.println(Thread.currentThread().getName() + " something2 starts...");
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " something2 ends...");
    }

    public static void main(String[] args) {
        Test6 t = new Test6();

        // new Thread(() -> t.something1(), "t1").start();
        // new Thread(() -> t.something2(), "t2").start();
        // when synchronized method is running, normal method can still run


        new Thread(t::something1, "t1").start();
        new Thread(t::something2, "t2").start();

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                t.something1();
            }
        });
        */
    }
}
