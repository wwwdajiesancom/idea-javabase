package com.loujie.www.dwz.listener;

import com.loujie.www.util.spring.SpringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 初始化Listener
 * 使用了注解
 *
 * @name loujie
 * @date 2019/3/6
 */
@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 加载ApplicationContext
        ApplicationContext applicationContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContextEvent.getServletContext());
        SpringUtils.setApplicationContext(applicationContext);

        //
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
