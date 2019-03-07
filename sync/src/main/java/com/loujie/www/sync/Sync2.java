package com.loujie.www.sync;

import com.loujie.www.util.ArgsUtils;
import sun.awt.windows.ThemeReader;

/**
 * @name loujie
 * @date 2019/2/20
 */
public class Sync2 implements Runnable {

    private int counter;

    public Sync2(int counter) {
        this.counter = counter;
    }

    public synchronized void add5() {
        for (int i = 0; i < 5; i++) {
            this.counter++;
            System.out.println(Thread.currentThread().getName() + ":" + this.counter);
            ArgsUtils.sleep(100);
        }
    }

    public void print() {
        // 不受synchronized影响
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + this.counter);
            ArgsUtils.sleep(100);
        }
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        ArgsUtils.sleep(100);
        if ("A".equals(threadName)) {
            this.add5();
        } else {
            this.print();
        }
    }
}
