package com.avalon.emiya.core.container;

/**
 *
 * @author huhao
 * @since 2019/3/11 16:07
 */
public interface InjectorData {

    void setBean(BeanDefinition beanDefinition);

    BeanDefinition getBean();

    void setDefaultName(String defaultName);

    String getDefaultName();

    String getRefName();

    Class<?> getType();

    boolean isMatch(BeanDefinition beanDefinition);

    boolean isRequired();

    void setProvider(boolean provider);

    boolean isProvider();

    void setProvidedType(Class<?> providedType);
}
