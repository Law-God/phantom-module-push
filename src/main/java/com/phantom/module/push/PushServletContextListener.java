package com.phantom.module.push;


import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author 张志凯 https://github.com/Law-God/phantom-module-push
 * phantom-module-push
 * com.phantom.module.push.PushServletContextListener
 * 2016-12-27 11:52
 */
public class PushServletContextListener implements ServletContextListener {
    private Logger log = Logger.getLogger(PushServletContextListener.class);
    private IPush push = null;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String className = null;
        try {
            className = PushProperties.getProperty("pushClassName");
            Class<?> pushClass = Push.class.getClassLoader().loadClass(className);
            try {
                push = (IPush)pushClass.newInstance();
            } catch (InstantiationException e) {
                log.error("类（" + className + "）初始化失败异常");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                log.error("类（" + className + "）权限访问异常");
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            log.error("类（" + className + "）未找到");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if(push != null){
            push = null;
        }
    }
}
