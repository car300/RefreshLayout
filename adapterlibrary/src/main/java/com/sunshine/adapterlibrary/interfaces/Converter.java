package com.sunshine.adapterlibrary.interfaces;


/**
 * Created by 耿 on 2016/8/11.
 */
@FunctionalInterface
public interface Converter<T> {
    void convert(Holder holder, T item);

}