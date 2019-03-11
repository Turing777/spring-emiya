package com.avalon.emiya.core.container;

public class ObjectFactory<T> implements BeanFactory<T> {

    private T instance;

    public ObjectFactory(T instance) {
        this.instance = instance;
    }

    @Override
    public T get() {
        return instance;
    }
}