package com.avalon.emiya.core.container;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:09
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(Class<?> clazz) {
        return clazz.getSimpleName();
    }
}
