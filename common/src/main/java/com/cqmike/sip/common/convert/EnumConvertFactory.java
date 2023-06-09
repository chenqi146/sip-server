package com.cqmike.sip.common.convert;

import com.cqmike.sip.common.enums.IEnum;
import com.cqmike.sip.common.exceptions.ParameterException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 枚举转换工厂
 *
 * @author chen qi
 **/
public class EnumConvertFactory implements ConverterFactory<String , IEnum<?>> {

    private static final Map<Class<?>, Converter<String, ?>> converterMap = new WeakHashMap<>();

    @Override
    public <T extends IEnum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = converterMap.get(targetType);
        if(result == null) {
            result = new StringToIEnum<>(targetType);
            converterMap.put(targetType, result);
        }
        return result;
    }

    static class StringToIEnum<T extends IEnum<?>> implements Converter<String, T> {
        private final Map<String, T> enumMap = new HashMap<>();

        public StringToIEnum(Class<T> enumType) {
            T[] enums = enumType.getEnumConstants();
            for(T e : enums) {
                enumMap.put(e.getCode() + "", e);
            }

        }
        @Override
        public T convert(String source) {
            T result = enumMap.get(source);
            if(result == null) {
                throw new ParameterException("No element matches " + source);
            }
            return result;
        }

    }
}