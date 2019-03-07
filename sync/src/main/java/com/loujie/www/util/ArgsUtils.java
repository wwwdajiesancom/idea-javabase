package com.loujie.www.util;

/**
 * @name loujie
 * @date 2019/2/20
 */
public class ArgsUtils {

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
