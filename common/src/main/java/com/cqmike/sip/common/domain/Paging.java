package com.cqmike.sip.common.domain;

import java.util.Objects;
import java.util.Optional;

/**
 * 分页对象
 *
 * @author chen qi
 **/
public class Paging {

    /**
     * 页数量
     * @mock 10
     */
    private int pageSize;

    /**
     * 页码
     * @mock 1
     */
    private int pageNum;

    /**
     * 总数
     * @mock 33
     */
    private long total;

    public static Paging of(Pageable pageable) {
        return new Paging(pageable);
    }

    public static Paging of(Pageable pageable, long total) {
        return new Paging(pageable, total);
    }

    public static Paging of(int pageSize, int pageNum) {
        return new Paging(pageSize, pageNum);
    }

    public static Paging of(int pageSize, int pageNum, long total) {
        return new Paging(pageSize, pageNum, total);
    }

    public Paging() {
        Pageable unpaged = Pageable.unpaged();
        this.pageSize = unpaged.getPageSize();
        this.pageNum = unpaged.getPageNum();
    }

    public Paging(int pageSize, int pageNum) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = 0L;
    }

    public Paging(int pageSize, int pageNum, long total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    public Paging(Pageable pageable) {
        this(pageable, 0L);
    }


    public Paging(Pageable pageable, long total) {
        Pageable p = Objects.isNull(pageable) ? Pageable.unpaged() : pageable;
        this.pageSize = p.getPageSize();
        this.pageNum = p.getPageNum();
        this.total = total; 
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = Optional.ofNullable(pageSize).orElse(0);
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = Optional.ofNullable(pageNum).orElse(0);
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = Optional.ofNullable(total).orElse(0L);
    }

    @Override
    public String toString() {
        return "Paging{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paging paging = (Paging) o;
        return getPageSize() == paging.getPageSize() && getPageNum() == paging.getPageNum() && getTotal() == paging.getTotal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageSize(), getPageNum(), getTotal());
    }
}
