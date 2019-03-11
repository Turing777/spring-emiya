package com.avalon.emiya.core.container;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:08
 */
public interface BeanNameGenerator {

    String generateBeanName(Class<?> clazz);
}
