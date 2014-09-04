package org.zfun.eight;

@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}