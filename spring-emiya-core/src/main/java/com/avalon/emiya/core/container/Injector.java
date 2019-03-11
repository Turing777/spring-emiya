package com.avalon.emiya.core.container;

/**
 *
 * @author huhao
 * @since 2019/3/11 16:05
 */
public interface Injector {

    boolean hasDependence(BeanDefinition beanDefinition);

    Object inject(Object instance, BeanDefinition beanDefinition);
}
