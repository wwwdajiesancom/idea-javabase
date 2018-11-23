package com.loujie.www.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @name loujie
 * @date 2018/11/21
 */
public class LogDemoTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void soutTest() throws InterruptedException {
        logger.debug("-----------soutTest----------");
        logger.info("-----------soutTest----------");
        logger.warn("-----------soutTest----------");
        logger.error("-----------soutTest----------");
        new Thread(new Runnable() {
            public void run() {
                abc();
            }
        }).start();
        this.abc();
        Thread.sleep(3000);
    }

    public void abc(){
        logger.debug("------abc------debug");
        logger.info("------abc------info");
        logger.warn("------abc------warn");
        logger.error("------abc------error");
    }
}
