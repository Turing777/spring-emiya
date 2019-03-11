package com.avalon.emiya.core.container;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

public class ConstructorInjector extends AbstractInjector {

    private Constructor<?> constructor;

    public ConstructorInjector(Constructor<?> constructor, List<InjectorData> injectorDatas) {
        super(injectorDatas);
        this.constructor = constructor;
    }

    public Object inject(BeanDefinition beanDefinition) {
        return inject(null, beanDefinition);
    }

    @Override
    public Object inject(Object instance, BeanDefinition beanDefinition) {
        if (injectorDatas == null && injectorDatas.size() < 1) {
            return instance;
        }
        List<Object> args = new LinkedList<>();
        //遍历构造函数的参数依赖信息
        for (InjectorData injectorData : injectorDatas) {
            BeanDefinition bean = injectorData.getBean();
            try {
                if (bean != null) {
                    //判断是否是Provider
                    if (injectorData.isProvider()) {
                        //添加实例到Provider参数
                        args.add(new ObjectFactory<>(bean.getInstance()));
                    } else {
                        //添加实例作为参数
                        args.add(bean.getInstance());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("failed to inject entity: %s by constructor!", beanDefinition.getName()), e);
            }
        }
        try {
            if (args.size() > 0) {
                //反射调用构造器，构造对象实例
                instance = constructor.newInstance(args.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("failed to inject entity: %s by constructor!", beanDefinition.getName()), e);
        }

        return instance;
    }

    public List<InjectorData> getConstructorParameterDatas() {
        return injectorDatas;
    }
}