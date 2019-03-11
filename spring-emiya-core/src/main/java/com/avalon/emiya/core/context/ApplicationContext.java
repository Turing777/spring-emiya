package com.avalon.emiya.core.context;

import com.avalon.emiya.core.container.BeanNameGenerator;

import java.util.Map;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:04
 */
public interface ApplicationContext {

    Object getBean(String beanName);

    <T> T getBean(Class<T> clazz);

    <T> Map<String, T> getBeans(Class<T> clazz);

    void setBeanNmaeGenerator(BeanNameGenerator beanNmaeGenerator);
}
