package com.avalon.emiya.core.container;

import java.lang.reflect.Field;
import java.util.List;

/**
 *
 * @author huhao
 * @since 2019/3/11 16:26
 */
public class FieldInjector extends AbstractInjector {

    public FieldInjector(List<InjectorData> injectorDatas) {
        super(injectorDatas);
    }

    List<InjectorData> getFieldInjectorDatas() {
        return injectorDatas;
    }

    @Override
    public Object inject(Object instance, BeanDefinition beanDefinition) {
        if (injectorDatas == null || injectorDatas.size() < 1) {
            return instance;
        }
        for (InjectorData injectorData : injectorDatas) {
            FieldInjectorData fieldInjectorData = (FieldInjectorData) injectorData;
            Field field = fieldInjectorData.getField();
            field.setAccessible(true);
            try {
                BeanDefinition bean = injectorData.getBean();
                if (bean != null) {
                    if (injectorData.isProvider()) {
                        field.set(instance, new ObjectFactory<>(bean.getInstance()));
                    } else {
                        field.set(instance, bean.getInstance());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
