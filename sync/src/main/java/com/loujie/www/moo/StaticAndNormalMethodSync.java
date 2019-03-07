package com.loujie.www.moo;

import com.loujie.www.util.ArgsUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * 静态及普通同步方法同时调用
 *
 * @name loujie
 * @date 2019/2/22
 */
public class StaticAndNormalMethodSync implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        StaticAndNormalMethodSync s1 = new StaticAndNormalMethodSync();
        StaticAndNormalMethodSync s2 = new StaticAndNormalMethodSync();
        Thread a = new Thread(s1, "a");
        Thread b = new Thread(s1, "a");
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println("-----------------------------end");
    }

    public void run() {
        String name = Thread.currentThread().getName();
        if ("a".equals(name))
            printNormal();
        else printStatic();
    }

    public synchronized void printNormal() {
        System.out.println("printNormal:" + Thread.currentThread().getName());
        ArgsUtils.sleep(2000);
        System.out.println("printNormal:" + Thread.currentThread().getName() + ",end");
    }

    public void printStatic() {
        synchronized (this) {
            System.out.println("printStatic:" + Thread.currentThread().getName());
            ArgsUtils.sleep(2000);
            System.out.println("printStatic:" + Thread.currentThread().getName() + ",end");
        }
    }

}
