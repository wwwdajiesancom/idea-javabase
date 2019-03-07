package com.loujie.www.test.sync;

import com.loujie.www.sync.Sync1;
import com.loujie.www.sync.Sync2;
import com.loujie.www.sync.Sync3;
import com.loujie.www.util.ArgsUtils;
import org.junit.Test;

/**
 * @name loujie
 * @date 2019/2/20
 */
public class SyncTest {

    @Test
    public void sync1() throws InterruptedException {
        // 测试,synchronized修饰代码块
        // 多个线程，操作同一个对象,修饰的代码是同步的
        Sync1 sync1 = new Sync1();
        Thread threadA = new Thread(sync1, "A");
        Thread threadB = new Thread(sync1, "B");
        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
        System.out.println("---------------end");
    }

    @Test
    public void sync1_2() {
        // 多个线程操作不同的对象,代码块可以并行,证明synchronized锁住的是[对象]
        Sync1 sync1 = new Sync1();
        Sync1 sync2 = new Sync1();
        Thread threadA = new Thread(sync1, "A");
        Thread threadB = new Thread(sync2, "B");
        threadA.start();
        threadB.start();

        ArgsUtils.sleep(3000);
        System.out.println("---------------end");
    }

    @Test
    public void sync2() {

        Sync2 sync2 = new Sync2(3);
        Thread threadA = new Thread(sync2, "A");
        Thread threadB = new Thread(sync2, "B");

        threadB.start();
        threadA.start();

        ArgsUtils.sleep(3000);
        System.out.println("---------------end");
    }

    public void sync3() {
        Sync3.Accounter accounter = new Sync3.Accounter("jiege", 200);
        Sync3 sync3 = new Sync3(accounter);


    }

}
