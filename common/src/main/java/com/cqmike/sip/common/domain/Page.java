package com.cqmike.sip.common.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  分页数据包装类
 *
 * @author chen qi
 **/
public class Page<T> implements Streamable<T> {

    /**
     * 分页对象
     */
    private final Paging paging;

    /**
     * 列表数据
     */
    private final Collection<T> list = new ArrayList<>();

    /**
     * 分页构造器
     * 分页参数为总数或者不分页
     *
     * @author chen qi
     * @param t
     * @since 1.0.0
     * @return
     */
    public static <T> Page<T> of(Collection<T> t) {
        return new Page<>(Paging.of(Pageable.unpaged(), t.size()), t);
    }


    public static <T> Page<T> of(Paging paging, Collection<T> t) {
        return new Page<>(paging, t);
    }


    /**
     * 分页构造器
     *
     * @author chen qi
     * @param pageable 分页参数
     * @param total 总数
     * @param t 数据
     * @since 1.0.0
     * @return
     */
    public static <T> Page<T> of(Pageable pageable, long total, Collection<T> t) {
        return new Page<>(Paging.of(pageable, total), t);
    }

    /**
     * 返回一个空分页
     *
     * @author chen qi
     * @since 1.0.0
     * @return
     */
    public static <T> Page<T> empty() {
        return empty(null);
    }

    /**
     * 返回一个空分页对象
     *
     * @author chen qi
     * @param pageable 分页参数
     * @since 1.0.0
     * @return
     */
    public static <T> Page<T> empty(Pageable pageable) {
        return new Page<>(Paging.of(pageable), Collections.emptyList());
    }
    /**
     * 返回一个空分页对象
     *
     * @author chen qi
     * @param pageable 分页参数
     * @param total 总数
     * @since 1.0.0
     * @return
     */
    public static <T> Page<T> empty(Pageable pageable, long total) {
        return new Page<>(Paging.of(pageable, total), Collections.emptyList());
    }

    /**
     * map分页对象返回
     *
     * @author chen qi
     * @param convert
     * @since 1.0.0
     * @return
     */
    public <U> Page<U> map(Function<? super T, ? extends U> convert) {
        return Page.of(paging, list.stream().map(convert).collect(Collectors.toList()));
    }


    public Page(Paging paging, Collection<T> list) {
        this.paging = paging;
        this.list.addAll(Optional.ofNullable(list).orElse(Collections.emptyList()));
    }

    public Paging getPaging() {
        return paging;
    }

    public Collection<T> getList() {
        return Collections.unmodifiableCollection(list);
    }

    @Override
    public String toString() {
        return "Page{" +
                "paging=" + paging +
                ", list=" + list +
                '}';
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        return Objects.equals(getPaging(), page.getPaging()) && Objects.equals(getList(), page.getList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaging(), getList());
    }
}
