package com.avalon.emiya.core.container;

import java.util.Map;

public class Resolver {

    private BeansContainer BeansContainer;

    public Resolver(BeansContainer BeansContainer) {
        this.BeansContainer = BeansContainer;
    }

    public void resolve(BeanDefinition beanDefinition) {
        //如果已经解析过了，则返回
        if (beanDefinition.isResolved()) {
            return;
        }
        //优先解析父类
        Class<?> superClass = beanDefinition.getClazz().getSuperclass();
        if (superClass != null && superClass != Object.class) {

            for (BeanDefinition bean : BeansContainer.getBeans(superClass).values()) {
                if (bean != beanDefinition) {
                    //递归解析父类
                    resolve(bean);
                }
            }
        }

        InjectorProvider injectorProvider = beanDefinition.getInjectorProvider();
        if (injectorProvider != null) {

            //如果有构造器注入，则先解析构造器注入依赖
            if (injectorProvider.getConstructorParameterDatas() != null) {
                for (InjectorData parameterInjectorData : injectorProvider.getConstructorParameterDatas()) {
                    doResolve(beanDefinition, injectorProvider, parameterInjectorData, parameterInjectorData.isRequired());
                }
            }

            //如果有字段注入，则解析字段注入依赖
            if (injectorProvider.getFieldInjectorDatas() != null) {
                for (InjectorData fieldInjectorData : injectorProvider.getFieldInjectorDatas()) {
                    doResolve(beanDefinition, injectorProvider, fieldInjectorData, fieldInjectorData.isRequired());
                }
            }

            //如果有方法注入，则解析方法注入依赖
            if (injectorProvider.getMethodInjectorAttributes() != null) {
                for (MethodInjectorAttribute methodInjectorAttribute : injectorProvider.getMethodInjectorAttributes()) {
                    if (methodInjectorAttribute.getParameterInjectorDatas() != null) {
                        for (InjectorData parameterInjectorData : methodInjectorAttribute.getParameterInjectorDatas()) {
                            doResolve(beanDefinition, injectorProvider, parameterInjectorData, methodInjectorAttribute.isRequired());
                        }
                    }
                }
            }

        }

        beanDefinition.setResolved(true);

    }

    private void doResolve(BeanDefinition beanDefinition, InjectorProvider injectorProvider, InjectorData injectorData, boolean isRequired) {
        BeanDefinition ref = null;

        Map<String, BeanDefinition> beanDefinationMap = BeansContainer.getBeanDefinations();
        if (injectorData.getRefName() != null && beanDefinationMap.containsKey(injectorData.getRefName())) {
            ref = beanDefinationMap.get(injectorData.getRefName());
        } else if (beanDefinationMap.containsKey(injectorData.getDefaultName())) {
            ref = beanDefinationMap.get(injectorData.getDefaultName());
        } else {
            for (BeanDefinition bean : beanDefinationMap.values()) {
                if (bean.isType(injectorData.getType())) {
                    ref = bean;
                    break;
                } else if (bean.isSubType(injectorData.getType())) {
                    ref = bean;
                    break;
                }
            }
        }

        if (ref == null) {
            if (isRequired) {
                throw new RuntimeException("unsatisfied entity , the entity named " + injectorData.getType() + " don't exists");
            }
        } else if (beanDefinition == ref || injectorProvider.hasDependence(beanDefinition)) {
            throw new RuntimeException("unsatisfied entity , there two entity ref each other !");
        } else {
            //设置依赖信息
            injectorData.setBean(ref);
        }
    }
}