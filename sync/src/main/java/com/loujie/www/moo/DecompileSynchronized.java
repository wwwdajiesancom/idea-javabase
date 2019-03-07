package com.loujie.www.moo;

/**
 * @name loujie
 * @date 2019/3/5
 */
public class DecompileSynchronized {

    byte[] bytes = new byte[0];

    public synchronized void abc() {
        System.out.println("public synchronized void abc()");
    }

    public void abc2() {
        synchronized (this) {
            System.out.println("public void abc2()");
        }
    }

    public void abc3() {
        synchronized (bytes) {
            System.out.println("public void abc3()");
        }
    }

    public synchronized static void abc4() {
        System.out.println("public synchronized static void abc4()");
    }

    public static void abc5() {
        synchronized (DecompileSynchronized.class) {
            System.out.println("public static void abc5()");
        }
    }
}
