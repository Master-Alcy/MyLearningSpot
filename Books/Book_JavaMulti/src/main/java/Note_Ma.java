import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Note_Ma {

    //-----------------------------------------------------------------------------------
    //---------------------------------------Test1---------------------------------------
    //-----------------------------------------------------------------------------------
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

    //-----------------------------------------------------------------------------------
    //---------------------------------------Test2---------------------------------------
    //-----------------------------------------------------------------------------------
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

    //-----------------------------------------------------------------------------------
    //---------------------------------------Test3---------------------------------------
    //-----------------------------------------------------------------------------------
    private class Test3 {
        private int count = 10;

        public synchronized void something() { // same as test2, is locking current object
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}

//-----------------------------------------------------------------------------------
//---------------------------------------Test4---------------------------------------
//-----------------------------------------------------------------------------------
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

//-----------------------------------------------------------------------------------
//---------------------------------------Test5---------------------------------------
//-----------------------------------------------------------------------------------
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

//-----------------------------------------------------------------------------------
//---------------------------------------Test6---------------------------------------
//-----------------------------------------------------------------------------------
class Test6 {
    // Can synchronized method and normal method being called at the same time?

    private synchronized void something1() { // object is lock, but something2 DON'T NEED lock at all
        System.out.println(Thread.currentThread().getName() + " something1 starts...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " something1 ends...");
    }

    private void something2() { // don't need lock
        System.out.println(Thread.currentThread().getName() + " something2 starts...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
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

//-----------------------------------------------------------------------------------
//---------------------------------------Test7---------------------------------------
//-----------------------------------------------------------------------------------

/**
 * Lock Task-Write and don't Lock Task-Read
 * => DirtyRead problem => also read un-submitted data?
 * => Could be solved by Copy-On-Write
 */
class Test7 {
    String name;
    double balance;

    public synchronized void set(String name, double balance) {
        this.name = name;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.balance = balance;
    }

    // need this lock even when reading to solve this problem
    public /*synchronized*/ double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        Test7 t = new Test7();
        new Thread(() -> t.set("zhangsan", 100.1)).start();

        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getBalance("zhangsan"));

        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getBalance("zhangsan"));
    }
}

//-----------------------------------------------------------------------------------
//---------------------------------------Test8---------------------------------------
//-----------------------------------------------------------------------------------

/**
 * One synchronized method can call another synchronized method
 * If a thread already owns a object's lock, then re-apply would
 * still get this lock =>
 * synchronized 's lock could be reenter 重入锁
 */
class Test8 {

    synchronized void some1() {
        System.out.println("some1 starts");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        some2(); // <== reenter, this lock some1 already has for same thread
    }

    synchronized void some2() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("some2 ends");
    }

    public static void main(String[] args) {
        Test8 t = new Test8();

//        new Thread(t::some1, "t1").start();
//        new Thread(t::some1, "t2").start();

        new Test88().some1();
    }
}

/**
 * child use parent's synchronized method
 * reenter lock example
 */
class Test88 extends Test8 {
    @Override
    synchronized void some1() {
        System.out.println("child some1 starts");
        super.some1();
        System.out.println("child some1 ends");
    }
}

//-----------------------------------------------------------------------------------
//---------------------------------------Test9---------------------------------------
//-----------------------------------------------------------------------------------

/**
 * Exceptions will release lock
 */
class Test9 {
    int count = 0;

    synchronized void some() {
        System.out.println(Thread.currentThread().getName() + " starts");

        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + " count = " + count);

            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (count == 5) {
                int i = 1 / 0; // Error here. lock would be released
                // to save the lock, we need to catch here and let loop continue
            }
        } // end loop
    } // end some

    public static void main(String[] args) {
        Test9 t = new Test9();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                t.some();
            }
        };

        new Thread(r, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(r, "t2").start();
    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test10---------------------------------------
//-----------------------------------------------------------------------------------

/**
 * volatile, makes a field visible to all threads
 * normally if B thread modified a field, A thread wouldn't know it
 * <p>
 * running field is saved in heap memory's Test10 Object
 * then t1 starts, it made a copy of running, thus when main
 * thread changed running, t1 wouldn't know
 * <p>
 * Note that volatile cannot guaranteed if multiple thread modifying
 * same field would give some error like dirtyRead
 * Thus, volatile cannot take over synchronized
 */
class Test10 {
    /**
     * JMM, volatile is notifying cpu memory of other thread that
     * their cache is out of date, read from main memory again
     * NOT reading from main memory from now on!
     * <p>
     * Also volatile is much faster than synchronized
     * if we can use volatile, then don't use lock
     * 无锁同步
     */
    /*volatile*/ boolean running = true; // see the difference

    void some() {
        System.out.println("some starts");
        while (running) {
            // if sleep here for like 10 milliseconds then the running might be refreshed
            // if CPU is busy, it would read from cache, but when it got some time
            // it >might< refresh the field with main memory
            /*
            try {
                TimeUnit.MILLISECONDS.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
        }
        System.out.println("some ends");
    }

    public static void main(String[] args) {
        Test10 t = new Test10();

        new Thread(t::some, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.running = false;
    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test11---------------------------------------
//-----------------------------------------------------------------------------------
class Test11 {
    /**
     * Thread A (100) => Main (100)
     * <p>
     * Thread B (100) ++ => Main(101)
     * Thread A (100) ++ => Main(101)
     * <p>
     * Thread C (101)
     * <p>
     * This is the difference between volatile and synchronized
     * volatile => Visibility, synchronized => both Visibility and Atomic
     */
    /*volatile*/ int count = 0;

    void some() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    /**
     * This synchronized should solve both visibility and atomic
     */
    synchronized void someX() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        Test11 t = new Test11();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
//            threads.add(new Thread(t::some, "thread-" + i));
            threads.add(new Thread(t::someX, "thread-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                o.join(); // wait this thread to die
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count); // should be 100,000
    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test12---------------------------------------
//-----------------------------------------------------------------------------------

/**
 * Faster way of solving problems in Test11, using AtomXXX
 * AtomXXX are atomic themselves, but can't guarantee multiple methods' calling are atomic
 * => if you use multiple AtomicXXX class's methods to manipulating,
 * => then that would still cause trouble without proper lock
 * Atomicity is non-interruptible (my own thought)
 */
class Test12 {
    /*volatile*/ //int count = 0;
    /**
     * Also guaranteed memory visibility
     */
    AtomicInteger count = new AtomicInteger(0);

    /*synchronized*/ void some() {
        for (int i = 0; i < 10000; i++) {
//            if (count.get() < 1000) {
//                // other threads can get in here
//                count.incrementAndGet(); // count++
//            } // this two methods don't have atomic !

            count.incrementAndGet(); // count++
        }
    }

    public static void main(String[] args) {
        Test12 t = new Test12();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
//            threads.add(new Thread(t::some, "thread-" + i));
            threads.add(new Thread(t::some, "thread-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                o.join(); // wait this thread to die
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count); // should be 100,000
    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test13---------------------------------------
//-----------------------------------------------------------------------------------
class Test13 {
    int count = 0;

    synchronized void some1() {
        // do something doesn't need sync
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // only next line need sync, now we shouldn't lock whole method
        count++;

        // do something doesn't need sync
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void some2() {
        // do something doesn't need sync
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // only next line need sync, now we shouldn't lock whole method
        // reduce lock time, improve efficiency. 细粒度的锁
        synchronized (this) {
            count++;
        }

        // do something doesn't need sync
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test14---------------------------------------
//-----------------------------------------------------------------------------------
class Test14 {
    /**
     * lock some object o, if o's properties change, won't affect the lock
     * however, if o becomes a new Object, then the lock object changes either
     * We should avoid this
     */
    Object o = new Object();

    void some() {
        /**
         * lock is locking the object in heap, not the reference in stack
         * java lock only lock in heap not stack
         */
        synchronized (o) {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            } // end loop
        }// end sync
    }// end method

    public static void main(String[] args) {
        Test14 t = new Test14();

        new Thread(t::some, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(t::some, "t2");

        // without next line, t2 would never run
        t.o = new Object(); // lock's object changes, so t2 can run

        t2.start(); // now t1 and t2 are running at the same time
    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test15---------------------------------------
//-----------------------------------------------------------------------------------
// @formatter:off
class Test15 {
    /**
     * Do NOT lock on String. In this example, some1 and some2 are locking
     * on the same object. Might be deadlock somethings if you are using the
     * same string as the on in external jars (jetty bug?)
     */
    String s1 = "Hello";
    String s2 = "Hello";
    void some1() { synchronized (s1) { } }
    void some2() { synchronized (s2) { } }
}
// @ formatter:on
//-----------------------------------------------------------------------------------
//--------------------------------------Test16---------------------------------------
//-----------------------------------------------------------------------------------

/**
 * Implement an container, with two methods add and size. Write two threads,
 * thread one adds 10 elements to container, thread two watch on real time
 * of the number of elements. When there are 5 elements, thread two gives
 * a notice and terminated. Can following program do this?
 * <p>
 * 1. add volatile can let t2 receive message, but t2's dead loop is a waste of CPU,
 * how to do this without dead loop? Also, this approach is not very accurate.
 * <p>
 * 2. We can use wait and notify here. However, we need to make sure t2 starts before t1
 * Also, wait will release lock, notify will not release lock
 * Thus, t2 doesn't exit on size=5, but when t1 ended t2 would receive this notify then exits
 * <p>
 * 3. after notify, t1 must release lock. after t2 finished, it also need to
 * notify t1 to continue
 */
class Test16 {
    // 1. add volatile
    volatile List<Object> lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Test16 t = new Test16();

//        // 1.
//        new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                t.add(new Object());
//                System.out.println("add " + i);
//
//                try {
//                    TimeUnit.SECONDS.sleep(1L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } // end for loop
//        }, "t1").start();
//
//        new Thread(() -> {
//            while(true) {
//                if (t.size() == 5) {
//                    break;
//                }
//            }
//            System.out.println("t2 ends");
//        }, "t2").start();

        final Object lock = new Object();

        /**
         * wait() need to lock this object first, then call wait(), then release this lock
         * would only start again with notify() (activate one of the waiting threads) or notifyAll()
         */
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 starts");
                if (t.size() != 5) { // in product and consume use while
                    try {
                        lock.wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 ends when size = " + t.size());
                lock.notify(); // 3. notify t1 to continue, then t2 ends and auto release this lock
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 starts");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    t.add(new Object());
                    System.out.println("add " + i);

                    if (t.size() == 5) {
                        lock.notify();

                        try {
                            lock.wait(); // 3. release lock and let t2 go
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();

    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test17---------------------------------------
//-----------------------------------------------------------------------------------

/**
 * Better way than Test16: Use Latch instead of wait and notify
 * <p>
 * 1. simple and can set wait time
 * 2. use await and countdown instead of wait and notify
 * 3. CountDownLatch doesn't involve lock, when count = 0 then current thread keeps working
 * 4. When nothing about sync, only talking through threads, 'synchronized + wait/notify' is too heavy
 * <p>
 * Consider using countdownlatch / cyclicbarrier / semaphore
 */
class Test17 {
    volatile List<Object> lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Test17 t = new Test17();

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("t2 starts");
            if (t.size() != 5) {
                try {
                    latch.await();

                    // can also set waiting time
                    // latch.await(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2 ends when size = " + t.size());
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 starts");
            for (int i = 0; i < 10; i++) {
                t.add(new Object());
                System.out.println("add " + i);

                if (t.size() == 5) {
                    latch.countDown(); // t2 can run, and t1 can still working
                }

                try {
                    TimeUnit.SECONDS.sleep(1L); // note this sleep keeps the accuracy either mhm
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test18---------------------------------------
//-----------------------------------------------------------------------------------
/**
 * Side Note: synchronized in method is locking this Object, whereas static synchronized
 * is locking this.class, which means all instance or objects from this class is locked
 */
/**
 * reentrantlock used to take over synchronized. some1 and some2 are the same as
 * some11 and some22. Thus, lock and do the same thing as synchronized.
 * However, reentrantlock can use trylock, so the thread can decide to wait or not
 */
class Test18 {
    /**
     * 1. read don't block read. 2. write blocks write. 3. write b locks read
     * Multiple thread can read at the same time, but only one thread
     * can write at a time
     */
    ReentrantReadWriteLock lockRW = new ReentrantReadWriteLock();
    Lock lock = new ReentrantLock(); // 手工锁, JVM won't release on Exception

    synchronized void some1() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

    void some11() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1L);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    synchronized void some2() {
        System.out.println("Some2 ...");
    }

    void some22() {
        lock.lock();
        System.out.println("some22 ...");
        lock.unlock();
    }

    void some222() {
        boolean locked2 = lock.tryLock();
        System.out.println("some222 ... 1:" + locked2);
        if (locked2) {
            lock.unlock();
        }


        boolean locked = false;
        try {
            /**
             * No matter locked or not, the program would continue to execute
             */
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            // we could use locked to decide what to do here
            System.out.println("Some222 ... 2:" + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Test18 t = new Test18();

//        new Thread(t::some1).start();
//        try {
//            TimeUnit.SECONDS.sleep(1L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        new Thread(t::some2).start();

        new Thread(t::some11).start();
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(t::some222).start();
    }
}

//-----------------------------------------------------------------------------------
//--------------------------------------Test18---------------------------------------
//-----------------------------------------------------------------------------------

class Test19 {


    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 started");
                TimeUnit.DAYS.sleep(1);
                System.out.println("t1 ended");
            } catch (InterruptedException e) {
                System.out.println("t1 Interrupted!");
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
//                lock.lock();
                lock.lockInterruptibly(); // can act to interrupt()
                System.out.println("t2 started");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 ended");
            } catch (InterruptedException e) {
                System.out.println("t2 Interrupted!");
            } finally {
                lock.unlock();
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }
}