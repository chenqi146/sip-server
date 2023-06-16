package com.cqmike.sip.common.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.jetbrains.annotations.Contract;

import java.util.*;
import java.util.function.Supplier;

/**
 * 对象拷贝工具类
 *
 * @author cqmike
 **/
public final class BeanConvertUtil {

    /**
     * <p>转换单个实体并处理</p>
     *
     * <pre>
     *  Demo demo = new Demo();
     *  DemoVO vo = BeanConvertUtil.convert(demo, DemoVO::new, (source, target) -> {
     *      target.setName("prefix" + source.getName());
     *  });
     * </pre>
     *
     * @param source         源对象
     * @param supplierTarget 目标对象获取器
     * @param callBack       转换回调函数, 在copyProperties之后生效
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Contract("!null, !null, _, _ -> !null")
    public static <S, T> T convert(S source, Supplier<T> supplierTarget, ConvertCallBack<S, T> callBack, String ...ignoreProperties) {
        if (Objects.isNull(supplierTarget) || Objects.isNull(source)) {
            return null;
        }
        T target = supplierTarget.get();
        BeanUtil.copyProperties(source, target, ignoreProperties);
        if (Objects.isNull(callBack)) {
            return target;
        }
        callBack.convert(source, target);
        return target;
    }


    /**
     * <p>深拷贝</p>
     *
     * <pre>
     *  Demo demo = new Demo();
     *  DemoVO vo = BeanConvertUtil.deepConvert(demo, DemoVO.class, (source, target) -> {
     *      target.setName("prefix" + source.getName());
     *  });
     * </pre>
     *
     * @param source   源对象
     * @param tClass   目标类型
     * @param callBack 转换回调函数, 在拷贝之后生效
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Contract("!null, !null, _, _ -> !null")
    public static <S, T> T deepConvert(S source, Class<T> tClass, ConvertCallBack<S, T> callBack, String ...ignoreProperties) {
        if (Objects.isNull(tClass) || Objects.isNull(source)) {
            return null;
        }
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(ignoreProperties);
        T target = JSON.parseObject(JSON.toJSONString(source, filter), tClass);
        if (Objects.isNull(callBack)) {
            return target;
        }
        callBack.convert(source, target);
        return target;
    }


    /**
     * 转换单个实体
     * <pre>
     *  Demo demo = new Demo();
     *  DemoVO vo = BeanConvertUtil.convert(demo, DemoVO::new);
     * </pre>
     *
     * @param source         源对象
     * @param supplierTarget 目标对象获取器
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Contract("!null, !null, _ -> !null")
    public static <S, T> T convert(S source, Supplier<T> supplierTarget, String ...ignoreProperties) {
        return convert(source, supplierTarget, null, ignoreProperties);
    }


    /**
     * 转换单个实体
     * <pre>
     *  Demo demo = new Demo();
     *  DemoVO vo = BeanConvertUtil.deepConvert(demo, DemoVO.class);
     * </pre>
     *
     * @param source 源对象
     * @param tClass 目标类型
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    @Contract("!null, !null, _ -> !null")
    public static <S, T> T deepConvert(S source, Class<T> tClass, String ...ignoreProperties) {
        return deepConvert(source, tClass, null, ignoreProperties);
    }


    /**
     * <p>转换数组</p>
     * <pre>
     *   Demo demo = new Demo();
     *   List<DemoVO> voList = BeanConvertUtil.convertList(new ArrayList(demo), DemoVO::new);
     * </pre>
     *
     * @param sources        源数据
     * @param supplierTarget 对象目前获取器
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public static <S, T> List<T> convertList(Collection<S> sources, Supplier<T> supplierTarget, String ...ignoreProperties) {
        return convertList(sources, supplierTarget, null, ignoreProperties);
    }


    /**
     * <p>转换数组</p>
     * <pre>
     *   Demo demo = new Demo();
     *   List<DemoVO> voList = BeanConvertUtil.convertList(new ArrayList(demo), DemoVO::new, (source, target) -> {
     *      target.setName("prefix" + source.getName());
     *   });
     * </pre>
     *
     * @param sources        源数据
     * @param supplierTarget 对象目标获取器
     * @param callBack       处理函数
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public static <S, T> List<T> convertList(Collection<S> sources, Supplier<T> supplierTarget, ConvertCallBack<S, T> callBack, String ...ignoreProperties) {
        if (Objects.isNull(supplierTarget) || CollUtil.isEmpty(sources)) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            if (Objects.isNull(source)) {
                continue;
            }

            T target = supplierTarget.get();
            BeanUtil.copyProperties(source, target, ignoreProperties);
            if (Objects.nonNull(callBack)) {
                callBack.convert(source, target);
            }
            list.add(target);
        }

        return list;
    }


    /**
     * <p>深拷贝数组</p>
     * <pre>
     *   Demo demo = new Demo();
     *   List<DemoVO> voList = BeanConvertUtil.deepConvertList(new ArrayList(demo), DemoVO.class);
     * </pre>
     *
     * @param sources 源数据
     * @param tClass  对象类型
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public static <S, T> List<T> deepConvertList(Collection<S> sources, Class<T> tClass, String ...ignoreProperties) {
        return deepConvertList(sources, tClass, null, ignoreProperties);
    }


    /**
     * <p>深拷贝数组</p>
     * <pre>
     *   Demo demo = new Demo();
     *   List<DemoVO> voList = BeanConvertUtil.deepConvertList(new ArrayList(demo), DemoVO.class, (source, target) -> {
     *      target.setName("prefix" + source.getName());
     *   });
     * </pre>
     *
     * @param sources  源数据
     * @param tClass   对象类型
     * @param callBack 处理函数
     * @return
     * @author cqmike
     * @since 1.0.0
     */
    public static <S, T> List<T> deepConvertList(Collection<S> sources, Class<T> tClass, ConvertCallBack<S, T> callBack, String ...ignoreProperties) {
        if (Objects.isNull(tClass) || CollUtil.isEmpty(sources)) {
            return Collections.emptyList();
        }
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(ignoreProperties);

        if (Objects.isNull(callBack)) {
            return JSON.parseArray(JSON.toJSONString(sources, filter), tClass);
        }

        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            if (Objects.isNull(source)) {
                continue;
            }

            T target = JSON.parseObject(JSON.toJSONString(source, filter), tClass);
            callBack.convert(source, target);
            list.add(target);
        }

        return list;
    }


    /**
     * 转换函数定义
     *
     * @author cqmike
     * @return
     * @since 1.0.0
     */
    @FunctionalInterface
    public interface ConvertCallBack<S, T> {
        /**
         * 转换
         *
         * @param source 源对象
         * @param target 目标对象
         * @return
         * @author cqmike
         * @since 1.0.0
         */
        void convert(S source, T target);
    }
}
