package java.lang;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.misc.Contended;
import sun.misc.VM;
import sun.nio.ch.Interruptible;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.security.util.SecurityConstants;

public class Thread implements Runnable {
    private volatile char[] name;

    private int priority;

    private Thread threadQ;

    private long eetop;

    private boolean single_step;

    static {
        registerNatives();
    }

    private boolean daemon = false;

    private boolean stillborn = false;

    private Runnable target;

    private ThreadGroup group;

    private ClassLoader contextClassLoader;

    private AccessControlContext inheritedAccessControlContext;

    private static int threadInitNumber;

    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }

    ThreadLocal.ThreadLocalMap threadLocals = null;

    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;

    private long stackSize;

    private long nativeParkEventPointer;

    private long tid;

    private static long threadSeqNumber;

    private volatile int threadStatus = 0;

    volatile Object parkBlocker;

    private volatile Interruptible blocker;

    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }

    private final Object blockerLock = new Object();

    public static final int MIN_PRIORITY = 1;

    public static final int NORM_PRIORITY = 5;

    public static final int MAX_PRIORITY = 10;

    void blockedOn(Interruptible paramInterruptible) {
        synchronized (this.blockerLock) {
            this.blocker = paramInterruptible;
        }
    }

    public static void sleep(long paramLong, int paramInt) throws InterruptedException {
        if (paramLong < 0L)
            throw new IllegalArgumentException("timeout value is negative");
        if (paramInt < 0 || paramInt > 999999)
            throw new IllegalArgumentException("nanosecond timeout value out of range");
        if (paramInt >= 500000 || (paramInt != 0 && paramLong == 0L))
            paramLong++;
        sleep(paramLong);
    }

    private void init(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong) {
        init(paramThreadGroup, paramRunnable, paramString, paramLong, null);
    }

    private void init(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong, AccessControlContext paramAccessControlContext) {
        if (paramString == null)
            throw new NullPointerException("name cannot be null");
        this.name = paramString.toCharArray();
        Thread thread = currentThread();
        SecurityManager securityManager = System.getSecurityManager();
        if (paramThreadGroup == null) {
            if (securityManager != null)
                paramThreadGroup = securityManager.getThreadGroup();
            if (paramThreadGroup == null)
                paramThreadGroup = thread.getThreadGroup();
        }
        paramThreadGroup.checkAccess();
        if (securityManager != null &&
                isCCLOverridden(getClass()))
            securityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
        paramThreadGroup.addUnstarted();
        this.group = paramThreadGroup;
        this.daemon = thread.isDaemon();
        this.priority = thread.getPriority();
        if (securityManager == null || isCCLOverridden(thread.getClass())) {
            this.contextClassLoader = thread.getContextClassLoader();
        } else {
            this.contextClassLoader = thread.contextClassLoader;
        }
        this
                .inheritedAccessControlContext = (paramAccessControlContext != null) ? paramAccessControlContext : AccessController.getContext();
        this.target = paramRunnable;
        setPriority(this.priority);
        if (thread.inheritableThreadLocals != null)
            this
                    .inheritableThreadLocals = ThreadLocal.createInheritedMap(thread.inheritableThreadLocals);
        this.stackSize = paramLong;
        this.tid = nextThreadID();
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Thread() {
        init(null, null, "Thread-" + nextThreadNum(), 0L);
    }

    public Thread(Runnable paramRunnable) {
        init(null, paramRunnable, "Thread-" + nextThreadNum(), 0L);
    }

    Thread(Runnable paramRunnable, AccessControlContext paramAccessControlContext) {
        init(null, paramRunnable, "Thread-" + nextThreadNum(), 0L, paramAccessControlContext);
    }

    public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable) {
        init(paramThreadGroup, paramRunnable, "Thread-" + nextThreadNum(), 0L);
    }

    public Thread(String paramString) {
        init(null, null, paramString, 0L);
    }

    public Thread(ThreadGroup paramThreadGroup, String paramString) {
        init(paramThreadGroup, null, paramString, 0L);
    }

    public Thread(Runnable paramRunnable, String paramString) {
        init(null, paramRunnable, paramString, 0L);
    }

    public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString) {
        init(paramThreadGroup, paramRunnable, paramString, 0L);
    }

    public Thread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong) {
        init(paramThreadGroup, paramRunnable, paramString, paramLong);
    }

    public synchronized void start() {
        if (this.threadStatus != 0)
            throw new IllegalThreadStateException();
        this.group.add(this);
        boolean bool = false;
        try {
            start0();
            bool = true;
        } finally {
            try {
                if (!bool)
                    this.group.threadStartFailed(this);
            } catch (Throwable throwable) {}
        }
    }

    public void run() {
        if (this.target != null)
            this.target.run();
    }

    private void exit() {
        if (this.group != null) {
            this.group.threadTerminated(this);
            this.group = null;
        }
        this.target = null;
        this.threadLocals = null;
        this.inheritableThreadLocals = null;
        this.inheritedAccessControlContext = null;
        this.blocker = null;
        this.uncaughtExceptionHandler = null;
    }

    @Deprecated
    public final void stop() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            checkAccess();
            if (this != currentThread())
                securityManager.checkPermission(SecurityConstants.STOP_THREAD_PERMISSION);
        }
        if (this.threadStatus != 0)
            resume();
        stop0(new ThreadDeath());
    }

    @Deprecated
    public final synchronized void stop(Throwable paramThrowable) {
        throw new UnsupportedOperationException();
    }

    public void interrupt() {
        if (this != currentThread())
            checkAccess();
        synchronized (this.blockerLock) {
            Interruptible interruptible = this.blocker;
            if (interruptible != null) {
                interrupt0();
                interruptible.interrupt(this);
                return;
            }
        }
        interrupt0();
    }

    public static boolean interrupted() {
        return currentThread().isInterrupted(true);
    }

    public boolean isInterrupted() {
        return isInterrupted(false);
    }

    @Deprecated
    public void destroy() {
        throw new NoSuchMethodError();
    }

    @Deprecated
    public final void suspend() {
        checkAccess();
        suspend0();
    }

    @Deprecated
    public final void resume() {
        checkAccess();
        resume0();
    }

    public final void setPriority(int paramInt) {
        checkAccess();
        if (paramInt > 10 || paramInt < 1)
            throw new IllegalArgumentException();
        ThreadGroup threadGroup;
        if ((threadGroup = getThreadGroup()) != null) {
            if (paramInt > threadGroup.getMaxPriority())
                paramInt = threadGroup.getMaxPriority();
            setPriority0(this.priority = paramInt);
        }
    }

    public final int getPriority() {
        return this.priority;
    }

    public final synchronized void setName(String paramString) {
        checkAccess();
        this.name = paramString.toCharArray();
        if (this.threadStatus != 0)
            setNativeName(paramString);
    }

    public final String getName() {
        return new String(this.name, true);
    }

    public final ThreadGroup getThreadGroup() {
        return this.group;
    }

    public static int activeCount() {
        return currentThread().getThreadGroup().activeCount();
    }

    public static int enumerate(Thread[] paramArrayOfThread) {
        return currentThread().getThreadGroup().enumerate(paramArrayOfThread);
    }

    public final synchronized void join(long paramLong) throws InterruptedException {
        long l1 = System.currentTimeMillis();
        long l2 = 0L;
        if (paramLong < 0L)
            throw new IllegalArgumentException("timeout value is negative");
        if (paramLong == 0L) {
            while (isAlive())
                wait(0L);
        } else {
            while (isAlive()) {
                long l = paramLong - l2;
                if (l <= 0L)
                    break;
                wait(l);
                l2 = System.currentTimeMillis() - l1;
            }
        }
    }

    public final synchronized void join(long paramLong, int paramInt) throws InterruptedException {
        if (paramLong < 0L)
            throw new IllegalArgumentException("timeout value is negative");
        if (paramInt < 0 || paramInt > 999999)
            throw new IllegalArgumentException("nanosecond timeout value out of range");
        if (paramInt >= 500000 || (paramInt != 0 && paramLong == 0L))
            paramLong++;
        join(paramLong);
    }

    public final void join() throws InterruptedException {
        join(0L);
    }

    public static void dumpStack() {
        (new Exception("Stack trace")).printStackTrace();
    }

    public final void setDaemon(boolean paramBoolean) {
        checkAccess();
        if (isAlive())
            throw new IllegalThreadStateException();
        this.daemon = paramBoolean;
    }

    public final boolean isDaemon() {
        return this.daemon;
    }

    public final void checkAccess() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null)
            securityManager.checkAccess(this);
    }

    public String toString() {
        ThreadGroup threadGroup = getThreadGroup();
        if (threadGroup != null)
            return "Thread[" + getName() + "," + getPriority() + "," + threadGroup
                    .getName() + "]";
        return "Thread[" + getName() + "," + getPriority() + "," + "" + "]";
    }

    @CallerSensitive
    public ClassLoader getContextClassLoader() {
        if (this.contextClassLoader == null)
            return null;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null)
            ClassLoader.checkClassLoaderPermission(this.contextClassLoader,
                    Reflection.getCallerClass());
        return this.contextClassLoader;
    }

    public void setContextClassLoader(ClassLoader paramClassLoader) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null)
            securityManager.checkPermission(new RuntimePermission("setContextClassLoader"));
        this.contextClassLoader = paramClassLoader;
    }

    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];

    public StackTraceElement[] getStackTrace() {
        if (this != currentThread()) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null)
                securityManager.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
            if (!isAlive())
                return EMPTY_STACK_TRACE;
            StackTraceElement[][] arrayOfStackTraceElement = dumpThreads(new Thread[] { this });
            StackTraceElement[] arrayOfStackTraceElement1 = arrayOfStackTraceElement[0];
            if (arrayOfStackTraceElement1 == null)
                arrayOfStackTraceElement1 = EMPTY_STACK_TRACE;
            return arrayOfStackTraceElement1;
        }
        return (new Exception()).getStackTrace();
    }

    public static Map<Thread, StackTraceElement[]> getAllStackTraces() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.GET_STACK_TRACE_PERMISSION);
            securityManager.checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
        }
        Thread[] arrayOfThread = getThreads();
        StackTraceElement[][] arrayOfStackTraceElement = dumpThreads(arrayOfThread);
        HashMap<Object, Object> hashMap = new HashMap<>(arrayOfThread.length);
        for (byte b = 0; b < arrayOfThread.length; b++) {
            StackTraceElement[] arrayOfStackTraceElement1 = arrayOfStackTraceElement[b];
            if (arrayOfStackTraceElement1 != null)
                hashMap.put(arrayOfThread[b], arrayOfStackTraceElement1);
        }
        return (Map)hashMap;
    }

    private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION = new RuntimePermission("enableContextClassLoaderOverride");

    private volatile UncaughtExceptionHandler uncaughtExceptionHandler;

    private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    @Contended("tlr")
    long threadLocalRandomSeed;

    @Contended("tlr")
    int threadLocalRandomProbe;

    @Contended("tlr")
    int threadLocalRandomSecondarySeed;

    private static class Caches {
        static final ConcurrentMap<Thread.WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap<>();

        static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue<>();
    }

    private static boolean isCCLOverridden(Class<?> paramClass) {
        if (paramClass == Thread.class)
            return false;
        processQueue(Caches.subclassAuditsQueue, (ConcurrentMap)Caches.subclassAudits);
        WeakClassKey weakClassKey = new WeakClassKey(paramClass, Caches.subclassAuditsQueue);
        Boolean bool = Caches.subclassAudits.get(weakClassKey);
        if (bool == null) {
            bool = Boolean.valueOf(auditSubclass(paramClass));
            Caches.subclassAudits.putIfAbsent(weakClassKey, bool);
        }
        return bool.booleanValue();
    }

    private static boolean auditSubclass(final Class<?> subcl) {
        Boolean bool = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
                Class<Thread> clazz = subcl;
                for (; clazz != Thread.class;
                     clazz = (Class)clazz.getSuperclass()) {
                    try {
                        clazz.getDeclaredMethod("getContextClassLoader", new Class[0]);
                        return Boolean.TRUE;
                    } catch (NoSuchMethodException noSuchMethodException) {
                        try {
                            Class[] arrayOfClass = { ClassLoader.class };
                            clazz.getDeclaredMethod("setContextClassLoader", arrayOfClass);
                            return Boolean.TRUE;
                        } catch (NoSuchMethodException noSuchMethodException1) {}
                    }
                }
                return Boolean.FALSE;
            }
        });
        return bool.booleanValue();
    }

    public long getId() {
        return this.tid;
    }

    public enum State {
        NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED;
    }

    public State getState() {
        return VM.toThreadState(this.threadStatus);
    }

    public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler paramUncaughtExceptionHandler) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null)
            securityManager.checkPermission(new RuntimePermission("setDefaultUncaughtExceptionHandler"));
        defaultUncaughtExceptionHandler = paramUncaughtExceptionHandler;
    }

    public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
        return defaultUncaughtExceptionHandler;
    }

    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return (this.uncaughtExceptionHandler != null) ? this.uncaughtExceptionHandler : this.group;
    }

    public void setUncaughtExceptionHandler(UncaughtExceptionHandler paramUncaughtExceptionHandler) {
        checkAccess();
        this.uncaughtExceptionHandler = paramUncaughtExceptionHandler;
    }

    private void dispatchUncaughtException(Throwable paramThrowable) {
        getUncaughtExceptionHandler().uncaughtException(this, paramThrowable);
    }

    static void processQueue(ReferenceQueue<Class<?>> paramReferenceQueue, ConcurrentMap<? extends WeakReference<Class<?>>, ?> paramConcurrentMap) {
        Reference<? extends Class<?>> reference;
        while ((reference = paramReferenceQueue.poll()) != null)
            paramConcurrentMap.remove(reference);
    }

    private static native void registerNatives();

    public static native Thread currentThread();

    public static native void yield();

    public static native void sleep(long paramLong) throws InterruptedException;

    private native void start0();

    private native boolean isInterrupted(boolean paramBoolean);

    public final native boolean isAlive();

    @Deprecated
    public native int countStackFrames();

    public static native boolean holdsLock(Object paramObject);

    private static native StackTraceElement[][] dumpThreads(Thread[] paramArrayOfThread);

    private static native Thread[] getThreads();

    private native void setPriority0(int paramInt);

    private native void stop0(Object paramObject);

    private native void suspend0();

    private native void resume0();

    private native void interrupt0();

    private native void setNativeName(String paramString);

    static class WeakClassKey extends WeakReference<Class<?>> {
        private final int hash;

        WeakClassKey(Class<?> param1Class, ReferenceQueue<Class<?>> param1ReferenceQueue) {
            super(param1Class, param1ReferenceQueue);
            this.hash = System.identityHashCode(param1Class);
        }

        public int hashCode() {
            return this.hash;
        }

        public boolean equals(Object param1Object) {
            if (param1Object == this)
                return true;
            if (param1Object instanceof WeakClassKey) {
                Class<?> clazz = get();
                return (clazz != null && clazz == ((WeakClassKey)param1Object)
                        .get());
            }
            return false;
        }
    }

    @FunctionalInterface
    public static interface UncaughtExceptionHandler {
        void uncaughtException(Thread param1Thread, Throwable param1Throwable);
    }
}
