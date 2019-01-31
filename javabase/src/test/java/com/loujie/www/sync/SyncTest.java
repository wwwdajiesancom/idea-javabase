package com.loujie.www.sync;

import org.junit.Test;
import org.omg.CORBA.INTERNAL;

/**
 * @name loujie
 * @date 2019/1/29
 */
public class SyncTest {

    @Test
    public void syncMethod() throws InterruptedException {
        Runnable run = new Runnable() {
            private int count = 0;

            public void run() {
                synchronized (this) {
                    for (int i = 0; i < 5; i++) {
                        System.out.println(Thread.currentThread().getName() + ":" + count++);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread thread1 = new Thread(run, "thread1");
        Thread thread2 = new Thread(run, "thread2");
        thread1.start();
        thread2.start();
        Thread.sleep(5000);
        System.out.println("---ok---");
    }

    @Test
    public void yinhang() {

        final Account account = new Account("loujie", 1000);

        Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    account.add(500);
                    account.add(-500);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":totalAmount:" + account.getTotalAmount());

                }
            }, "thread" + i);
        }

    }

}

class Account {

    private Integer totalAmount = 0;
    private String name;

    public Account(String name, Integer totalAmount) {
        this.name = name;
        this.totalAmount = totalAmount;
    }

    public Integer add(Integer add) {
        this.totalAmount += add;
        return this.totalAmount;
    }

    public Integer getTotalAmount() {
        return this.totalAmount;
    }
}