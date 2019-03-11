package com.avalon.emiya.core.container;

import com.avalon.emiya.core.annotation.Autowired;

/**
 *
 * @author huhao
 * @since 2019/3/11 16:16
 */
public abstract class AbstractInjectorData implements InjectorData {

    /**
     * 默认依赖名称
     */
    private String defaultName;

    /**
     * 指定依赖名称
     */
    private String refName;

    /**
     * 依赖的BeanDefination实例
     */
    private BeanDefinition bean;

    /**
     * 是否必须
     */
    private boolean isRequired;

    /**
     * 是否是Provider或者BeanFactory依赖
     */
    private boolean isProvider;

    /**
     * Provider或者BeanFactory提供的依赖类
     */
    private Class<?> providedType;

    public AbstractInjectorData(String defaultName, String refName, boolean isRequired) {
        this.defaultName = defaultName;
        this.refName = refName;
        this.isRequired = isRequired;
    }

    @Override
    public String getDefaultName() {
        return defaultName;
    }

    @Override
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    @Override
    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    @Override
    public BeanDefinition getBean() {
        return bean;
    }

    @Override
    public void setBean(BeanDefinition bean) {
        this.bean = bean;
    }

    @Override
    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    @Override
    public boolean isProvider() {
        return isProvider;
    }

    @Override
    public void setProvider(boolean provider) {
        isProvider = provider;
    }

    public Class<?> getProvidedType() {
        return providedType;
    }

    @Override
    public void setProvidedType(Class<?> providedType) {
        this.providedType = providedType;
    }

    @Override
    public boolean isMatch(BeanDefinition beanDefinition) {
        if (refName != null && refName.equals(beanDefinition.getName())) {
            return true;
        } else if (defaultName.equals(beanDefinition.getName())) {
            return true;
        } else {
            Class<?> type = getType();
            return beanDefinition.isType(type);
        }
    }
}
