package com.loujie.www.sync;

import com.loujie.www.util.ArgsUtils;

/**
 * 修饰代码块
 *
 * @name loujie
 * @date 2019/2/20
 */
public class Sync1 implements Runnable {

    private static int count = 0;

    public void run() {
        synchronized (this) {
            // 修饰代码块
            for (int i = 0; i < 5; i++) {
                count++;
                System.out.println(Thread.currentThread().getName() + ":" + count);
                ArgsUtils.sleep(100);
            }
        }
    }
}
