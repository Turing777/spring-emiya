package com.avalon.emiya.core.context;

import com.avalon.emiya.core.annotation.*;
import com.avalon.emiya.core.container.*;
import com.avalon.emiya.core.utils.ReflectUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:10
 */
public class DefaultApplicationContext implements ApplicationContext {

    private String[] scanPaths;

    private BeansContainer beansContainer;

    private BeanNameGenerator beanNameGenerator;

    public DefaultApplicationContext(String... scanPaths) {
        if (scanPaths.length < 1) {
            throw new RuntimeException("nothing");
        }
        this.scanPaths = scanPaths;
        this.beanNameGenerator = new DefaultBeanNameGenerator();
        this.beansContainer = new BeansContainer();
        scan();
    }

    private void scan() {
        Set<Class<?>> candidates = new HashSet<>();
        for (String scanPath : scanPaths) {
            candidates.addAll(ReflectUtil.getClasses(scanPath));
        }

        for (Class<?> candidate : candidates) {
            if (candidate.isAnnotation() || candidate.isInterface() || Modifier.isAbstract(candidate.getModifiers())) {
                continue;
            }
            String name = getComponentName(candidate);
            if (name == null) {
                continue;
            }

            boolean isSingleton = false;

            Scope scope = candidate.getAnnotation(Scope.class);
            if (scope != null) {
                if (ScopeType.SINGLETON.equals(scope.value())) {
                    isSingleton = true;
                } else if (!ScopeType.PROTOTYPE.equals(scope.value())) {
                    throw new RuntimeException("the value of scope is error !");
                }
            } else if (candidate.getAnnotation(Singleton.class) != null) {
                isSingleton = true;
            }
            if ("".equals(name)) {
                name = this.beanNameGenerator.generateBeanName(candidate);
            }
            BeanDefinition beanDefinition = new BeanDefinition(candidate, name, isSingleton);
            beanDefinition.setInjectorProvider(new InjectorProvider(candidate, this.beanNameGenerator));
            beansContainer.register(beanDefinition.getName(), beanDefinition);
        }
        Map<String, BeanDefinition> beanDefinationMap = beansContainer.getBeanDefinations();
        Resolver resolver = new Resolver(beansContainer);
        for (Map.Entry<String, BeanDefinition> entry : beanDefinationMap.entrySet()) {
            resolver.resolve(entry.getValue());
        }
    }

    @Override
    public Object getBean(String beanName) {
        return beansContainer.getBean(beanName);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        BeanDefinition beanDefinition = beansContainer.getBean(clazz);
        if (beanDefinition != null) {
            return (T) beanDefinition.getInstance();
        }
        return null;
    }

    @Override
    public <T> Map<String, T> getBeans(Class<T> clazz) {
        Map<String, T> beanMap = new HashMap<>(4);
        for (Map.Entry<String, BeanDefinition> entry : beansContainer.getBeans(clazz).entrySet()) {
            beanMap.put(entry.getKey(), (T) entry.getValue().getInstance());
        }
        return beanMap;
    }

    @Override
    public void setBeanNmaeGenerator(BeanNameGenerator beanNmaeGenerator) {
        this.beanNameGenerator = beanNmaeGenerator;
    }

    private String getComponentName(Class<?> candidate) {
        Component component = candidate.getAnnotation(Component.class);
        if (component != null) {
            return component.value();
        }
        Service service = candidate.getAnnotation(Service.class);
        if (service != null) {
            return service.value();
        }
        Repository repository = candidate.getAnnotation(Repository.class);
        if (repository != null) {
            return repository.value();
        }
        Controller controller = candidate.getAnnotation(Controller.class);
        if (controller != null) {
            return controller.value();
        }
        Named named = candidate.getAnnotation(Named.class);
        if (named != null) {
            return named.value();
        }
        return null;
    }

}
