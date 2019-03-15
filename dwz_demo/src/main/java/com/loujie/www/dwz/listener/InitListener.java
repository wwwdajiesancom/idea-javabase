package com.loujie.www.dwz.listener;

import com.loujie.www.util.properties.PropertiesUtils;
import com.loujie.www.util.spring.SpringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

        // 加载项目的名称
        this.loadingConfigToContext(servletContextEvent.getServletContext());
    }


    public void loadingConfigToContext(ServletContext servletContext) {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put("projectName", PropertiesUtils.getProperty("projectName"));
        servletContext.setAttribute("contextMap", contextMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
