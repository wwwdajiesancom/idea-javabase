package com.loujie.www.test.theads;

import com.loujie.www.threads.Actor;
import org.junit.Test;

/**
 * @name loujie
 * @date 2019/3/6
 */
public class TestThread {

    @Test
    public void abc() throws InterruptedException {
        Thread actor = new Actor();
        actor.start();
        actor.join();
        System.out.println("------------------------------------------end");
    }

}
