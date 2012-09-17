package org.zfun.generic;

import org.zfun.PlaceHolder;

import com.google.gson.Gson;

/**
 * User: skuo
 * Date: Jun 10, 2011
 */
public class Tyrant<V extends PlaceHolder> {

    private Class<V> parameterizedType;
    
    protected Tyrant(Class<V> parameterizedType) {
        this.parameterizedType = parameterizedType;
    }
    
    public Class<V> getParameterizedType() {
        return parameterizedType;
    }
    
    public String parse(String jsonStr) {
        return parameterizedType.getName();
    }

    public <K> void put(K key, V value) {
        System.out.format("%s=%s\n", key, value);
    }
    
    public <K> void update(K key, Class<V> clazz, String jsonStr) {
        Gson gson = new Gson();
        V value = gson.fromJson(jsonStr, clazz);
        put(key,value);
    }
    
}

