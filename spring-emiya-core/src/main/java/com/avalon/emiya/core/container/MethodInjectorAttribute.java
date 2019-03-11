package com.avalon.emiya.core.container;

import java.lang.reflect.Method;
import java.util.List;

public class MethodInjectorAttribute {

    private Method method;

    private List<InjectorData> parameterInjectorDatas;

    private boolean isRequired;

    public MethodInjectorAttribute(Method method, List<InjectorData> parameterInjectorDatas, boolean isRequired) {
        this.method = method;
        this.parameterInjectorDatas = parameterInjectorDatas;
        this.isRequired = isRequired;
    }

    public Method getMethod() {
        return method;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public List<InjectorData> getParameterInjectorDatas() {
        return parameterInjectorDatas;
    }
}