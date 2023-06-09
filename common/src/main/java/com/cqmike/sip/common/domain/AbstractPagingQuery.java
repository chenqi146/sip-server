package com.cqmike.sip.common.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 抽象分页
 * @see org.springframework.data.domain.AbstractPageRequest
 * @author chen qi
 **/
public abstract class AbstractPagingQuery implements Pageable, Serializable {


    private static final long serialVersionUID = 2460282482354557327L;


    /**
     * 页数量(默认值为0,小于等于0不分页)
     * @mock 10
     */
    private final int pageSize;

    /**
     * 页码(第几页默认值为0, 0-不分页, 必须>=0)
     * @mock 1
     */
    private final int pageNum;

    public AbstractPagingQuery(Integer pageSize, Integer pageNum) {
        this.pageNum = Optional.ofNullable(pageNum).orElse(0);
        this.pageSize = Optional.ofNullable(pageSize).orElse(0);
    }

    /**
     * 是否分页
     *
     * @return
     */
    @Override
    public boolean isPaged() {
        return pageSize > 0 && pageNum > 0;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getOffset() {
        long l = (long) pageSize * (pageNum - 1);
        if (l < 0) {
            return 0;
        }
        return l;
    }

    @Override
    public String toString() {
        return "AbstractPagingQuery{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPagingQuery that = (AbstractPagingQuery) o;
        return getPageSize() == that.getPageSize() && getPageNum() == that.getPageNum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageSize(), getPageNum());
    }
}
