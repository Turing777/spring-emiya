package com.avalon.emiya.core.container;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:12
 */
public class BeansContainer {

    private Map<String, BeanDefinition> beans = new HashMap<>();

    public void register(String beanName, BeanDefinition beanDefinition) {
        if (beans.containsKey(beanName)) {
            throw new RuntimeException(String.format("the entity named: %s has conflicted ! ", beanName));
        }
        beans.put(beanName, beanDefinition);
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beans.get(beanName);
        return beanDefinition.getInstance();
    }

    public BeanDefinition getBean(Class<?> clazz) {
        for (BeanDefinition beanDefinition : beans.values()) {
            if (beanDefinition.isType(clazz)) {
                return beanDefinition;
            }
        }
        return null;
    }

    public Map<String, BeanDefinition> getBeans(Class<?> clazz) {
        Map<String, BeanDefinition> beans = new HashMap<>(4);
        for (Map.Entry<String, BeanDefinition> entry : this.beans.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            if (beanDefinition.isType(clazz) || beanDefinition.isSubType(clazz)) {
                beans.put(entry.getKey(), beanDefinition);
            }
        }
        return beans;
    }

    public Map<String, BeanDefinition> getBeanDefinations() {
        return beans;
    }
}
