package com.avalon.emiya.core.container;

import java.lang.reflect.Field;

public class FieldInjectorData extends AbstractInjectorData {

    private Field field;

    public FieldInjectorData(String defalultName, String refName, boolean required, Field field) {
        super(defalultName, refName, required);
        this.field = field;
    }

    @Override
    public Class<?> getType() {
        if (isProvider()) {
            return getProvidedType();
        }
        return field.getType();
    }

    public Field getField() {
        return field;
    }

}