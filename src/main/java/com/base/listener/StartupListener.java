package com.base.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import com.base.utils.ConfigConstants;
import com.unionpay.acp.sdk.CertUtil;
import com.unionpay.acp.sdk.SDKConfig;


public class StartupListener extends ContextLoaderListener {
	protected final Logger logger = LogManager.getLogger(getClass());
	private static ServletContext servletContext;

	public static ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("web contextDestroyed-----");
		super.contextDestroyed(event);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("web contextInitialized-----");
		ConfigConstants.init();
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		super.contextInitialized(event);
	}

}
