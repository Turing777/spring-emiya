package com.avalon.emiya.core.container;

import com.avalon.emiya.core.proxy.CglibMethodInterceptor;
import com.avalon.emiya.core.proxy.JdkInvocationHandler;

import java.util.Objects;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:12
 */
public class BeanDefinition {

    private Class<?> clazz;

    private String name;

    private Object instance;

    private boolean isSingleton;

    private InjectorProvider injectorProvider;

    private boolean isResolved;

    public BeanDefinition(Class<?> clazz, String name, boolean isSingleton) {
        this.clazz = clazz;
        this.name = name;
        this.isSingleton = isSingleton;
    }

    public String getName() {
        return name;
    }

    public boolean isType(Class<?> clazz) {
        return this.clazz == clazz;
    }

    public boolean isSuperType(Class<?> clazz) {
        return this.clazz.isAssignableFrom(clazz);
    }

    public boolean isSubType(Class<?> clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }

    public Object getInstance() {
        if (instance == null) {
            synchronized (Object.class) {
                if (instance == null) {
                    instance = newBean();
                }
            }
        }
        return instance;
    }

    private Object newBean() {
        Object instance = injectorProvider.doInject(this);
        Class<?>[] classes = clazz.getInterfaces();
        if (classes.length != 0) {
            JdkInvocationHandler jdkInvocationHandler = new JdkInvocationHandler();
            return jdkInvocationHandler.newProxyInstance(instance);
        } else {
            CglibMethodInterceptor cglibMethodInterceptor = new CglibMethodInterceptor();
            return cglibMethodInterceptor.newProxyInstance(instance);
        }
    }

    private Object getSingleInstance() {
        if (instance == null) {
            synchronized (Object.class) {
                if (instance == null) {
                    instance = newBean();
                }
            }
        }
        return instance;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setInjectorProvider(InjectorProvider injectorProvider) {
        this.injectorProvider = injectorProvider;
    }

    public InjectorProvider getInjectorProvider() {
        return injectorProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeanDefinition that = (BeanDefinition) o;
        return isSingleton == that.isSingleton && isResolved == that.isResolved && Objects.equals(clazz, that.clazz) && Objects.equals(name, that.name)
                && Objects.equals(instance, that.instance) && Objects.equals(injectorProvider, that.injectorProvider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, name, instance, isSingleton, isResolved, injectorProvider);
    }
}
