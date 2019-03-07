package com.loujie.www.util.spring;

import org.springframework.context.ApplicationContext;

/**
 * @name loujie
 * @date 2019/3/6
 */
public class SpringUtils {

    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> cla) {
        if (applicationContext != null) {
            return applicationContext.getBean(cla);
        }
        return null;
    }
}
