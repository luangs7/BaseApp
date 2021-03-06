package br.com.luan2.baseapp.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Dev_Maker on 19/10/2016.
 */
public class JsonListHelper<T> implements ParameterizedType {

    private Class<?> wrapped;

    public JsonListHelper(Class<T> wrapped) {
        this.wrapped = wrapped;
    }

    public Type[] getActualTypeArguments() {
        return new Type[] {wrapped};
    }

    public Type getRawType() {
        return List.class;
    }

    public Type getOwnerType() {
        return null;
    }

}
