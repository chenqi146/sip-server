package com.cqmike.sip.common.domain;

/**
 * PagingQuery
 *
 * @author chen qi
 **/
public class PagingQuery extends AbstractPagingQuery {

    private static final long serialVersionUID = 5927287262891216300L;


    public PagingQuery(Integer pageSize, Integer pageNum) {
        super(pageSize, pageNum);
    }

    public static PagingQuery of(Integer pageSize, Integer pageNum) {
        return new PagingQuery(pageSize, pageNum);
    }

    public static PagingQuery empty() {
        Pageable unpaged = Pageable.unpaged();
        return new PagingQuery(unpaged.getPageSize(), unpaged.getPageNum());
    }

}
