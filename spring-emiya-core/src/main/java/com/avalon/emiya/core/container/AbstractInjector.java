package com.avalon.emiya.core.container;

import java.util.List;

/**
 *
 * @author huhao
 * @since 2019/3/11 16:07
 */
public abstract class AbstractInjector implements Injector {

    protected List<InjectorData> injectorDatas;

    public AbstractInjector(List<InjectorData> injectorDatas) {
        this.injectorDatas = injectorDatas;
    }

    @Override
    public boolean hasDependence(BeanDefinition beanDefinition) {
        for (InjectorData injectorData : injectorDatas) {
            if (injectorData.isMatch(beanDefinition)) {
                return true;
            }
        }
        return false;
    }
}
