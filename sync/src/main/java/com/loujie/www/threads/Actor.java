package com.loujie.www.threads;

import static java.lang.Thread.sleep;

/**
 * 演员类
 *
 * @name loujie
 * @date 2019/3/6
 */
public class Actor extends Thread {


    @Override
    public void run() {
        System.out.println(getName() + "：我是一个演员");

        boolean keppAlive = true;
        int count = 0;
        do {
            System.out.println(getName() + "：登台演出了" + (++count) + "次");
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 20) {
                keppAlive = false;
            }
        } while (keppAlive);

        System.out.println(getName() + "：我的演出结束了");
    }

    public static void main(String[] args) {
        Thread actor = new Actor();
        actor.start();
        System.out.println("-------------------哈哈哈哈哈");
    }
}
