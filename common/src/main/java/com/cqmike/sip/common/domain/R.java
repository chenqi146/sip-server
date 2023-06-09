package com.cqmike.sip.common.domain;


import java.beans.Transient;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * 响应类
 *
 * @author chen qi
 **/
public class R<T> {
    /**
     * 时间戳
     *
     * @mock 1649474265703
     */
    private final String timestamp = String.valueOf(System.currentTimeMillis());
    /**
     * 提示消息
     *
     * @mock 成功
     */
    private String msg;
    /**
     * 状态码
     *
     * @mock 0
     */
    private int status;
    /**
     * 数据
     */
    private T body;

    public R() {
    }

    public R(String msg, int code, T body) {
        this.msg = msg;
        this.status = code;
        this.body = body;
    }


    public static <T> R<T> error() {
        return new R<>("", 403, null);
    }

    public static <T> R<T> error(String msg) {
        return new R<>(msg, 403, null);
    }

    public static <T> R<T> error(int code, String msg) {
        return new R<>(msg, code, null);
    }

    public static <T> R<T> error(String msg, T data) {
        return new R<>(msg, 403, data);
    }

    public static <T> R<T> error(int code, String msg, T data) {
        return new R<>(msg, code, data);
    }

    public static <T> R<T> fail() {
        return new R<>("", 403, null);
    }

    public static <T> R<T> fail(String msg) {
        return new R<>(msg, 403, null);
    }

    public static <T> R<T> fail(int code, String msg) {
        return new R<>(msg, code, null);
    }

    public static <T> R<T> fail(String msg, T data) {
        return new R<>(msg, 403, data);
    }

    public static <T> R<T> fail(int code, String msg, T data) {
        return new R<>(msg, code, data);
    }

    public static <T> R<T> ok() {
        return new R<>();
    }

    public static <T> R<T> ok(T data) {
        return new R<>("", 0, data);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<>(msg, 0, data);
    }

    public static <T> R<T> ok(int code, String msg, T data) {
        return new R<>(msg, code, data);
    }

    public static <E> R<Page<E>> page(Collection<E> list) {
        return R.ok(Page.of(list));
    }

    public static <E> R<Page<E>> page(Pageable pageable, long total, Collection<E> list) {
        return R.ok(Page.of(pageable, total, list));
    }

    public static <E> R<Page<E>> page(Paging paging, Collection<E> list) {
        return R.ok(Page.of(paging, list));
    }

    public static <E> R<Page<E>> emptyPage() {
        return R.ok(Page.empty());
    }

    public static <E> R<Page<E>> emptyPage(Pageable pageable) {
        return R.ok(Page.empty(pageable));
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public T getBody() {
        return body;
    }

    /**
     * 返回一个optional对象
     * @ignore
     * @return
     */
    public Optional<T> op() {
        return Optional.ofNullable(body);
    }

    @Transient
    public boolean isOk() {
        return status == 0;
    }

    @Override
    public String toString() {
        return "R{" +
                "timestamp='" + timestamp + '\'' +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                ", body=" + body +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        R<?> r = (R<?>) o;
        return getStatus() == r.getStatus() && Objects.equals(getTimestamp(), r.getTimestamp()) && Objects.equals(getMsg(), r.getMsg()) && Objects.equals(getBody(), r.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimestamp(), getMsg(), getStatus(), getBody());
    }
}

