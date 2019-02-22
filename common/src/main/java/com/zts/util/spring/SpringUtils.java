package com.zts.util.spring;

import com.zts.util.collection.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringUtils implements ApplicationContextAware, PriorityOrdered {
	public static ApplicationContext ctx;
	
	public static <T> T getBean(ApplicationContext ctx, Class<T> clazz) {
		Map<String, T> beans = ctx.getBeansOfType(clazz);
		if(CollectionUtils.isNotEmpty(beans)) {
			for(String keys : beans.keySet()) {
				return beans.get(keys);
			}
		}
		return null;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return getBean(getApplicationContext(), clazz);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String n) {
		return (T) getApplicationContext().getBean(n);
	}

	public static ApplicationContext getApplicationContext(){
		return ctx;
	}
	
	public static void publishEvent(ApplicationEvent event) {
		getApplicationContext().publishEvent(event);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.ctx = applicationContext;

	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}