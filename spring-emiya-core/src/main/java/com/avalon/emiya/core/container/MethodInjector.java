package com.avalon.emiya.core.container;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class MethodInjector extends AbstractInjector {

    private List<MethodInjectorAttribute> methodInjectorAttributes;

    public MethodInjector(List<MethodInjectorAttribute> methodInjectorAttributes, List<InjectorData> injectorDatas) {
        super(injectorDatas);
        this.methodInjectorAttributes = methodInjectorAttributes;
    }

    @Override
    public Object inject(Object instance, BeanDefinition beanDefinition) {
        if (methodInjectorAttributes != null && methodInjectorAttributes.size() > 0) {
            for (MethodInjectorAttribute attribute : methodInjectorAttributes) {
                Method method = attribute.getMethod();
                List<InjectorData> parameterInjectorDatas = attribute.getParameterInjectorDatas();
                if (parameterInjectorDatas != null && parameterInjectorDatas.size() > 0) {
                    List<Object> args = new LinkedList<>();
                    for (InjectorData injectorData : parameterInjectorDatas) {
                        BeanDefinition bean = injectorData.getBean();
                        try {
                            if (bean != null) {
                                if (injectorData.isProvider()) {
                                    args.add(new ObjectFactory<>(bean.getInstance()));
                                } else {
                                    args.add(bean.getInstance());
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(String.format("failed to inject entity: %s by method!", beanDefinition.getName()), e);
                        }
                    }
                    try {
                        if (args.size() > 0) {
                            method.invoke(instance, args.toArray());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(String.format("failed to inject entity: %s by method!", beanDefinition.getName()), e);
                    }
                }
            }
        }
        return instance;
    }

    public List<MethodInjectorAttribute> getMethodInjectorAttributes() {
        return methodInjectorAttributes;
    }
}
