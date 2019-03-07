package com.loujie.www.moo;

import com.loujie.www.util.ArgsUtils;

/**
 * 消失的请求数
 *
 * @name loujie
 * @date 2019/2/20
 */
public class DisapperRequest implements Runnable {
    static DisapperRequest dr = new DisapperRequest();

    private int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(dr, "a");
        Thread t2 = new Thread(dr, "b");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        // 或者
        // while (t1.isAlive() || t2.isAlive()) {
        // }
        System.out.println(dr.count);
        System.out.println("---------------end");
    }

    public void run() {
        for (int i = 0; i < 1000000; i++) {
            count++;
        }
        System.out.println("end:" + Thread.currentThread().getName());
    }
}
