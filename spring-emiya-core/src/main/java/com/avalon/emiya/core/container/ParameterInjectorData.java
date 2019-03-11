package com.avalon.emiya.core.container;

import java.lang.reflect.Parameter;

public class ParameterInjectorData extends AbstractInjectorData {

    private Parameter parameter;

    public ParameterInjectorData(String defalultName, String refName, boolean required, Parameter parameter) {
        super(defalultName, refName, required);
        this.parameter = parameter;
    }

    @Override
    public Class<?> getType() {
        if (isProvider()) {
            return getProvidedType();
        }
        return parameter.getType();
    }

}