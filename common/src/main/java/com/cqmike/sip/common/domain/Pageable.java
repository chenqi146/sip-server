package com.cqmike.sip.common.domain;

/**
 *  分页接口
 *
 * @author chen qi
 **/
public interface Pageable {

    /**
     * 返回一个空分页接口
     *
     * @author chen qi
     * @since 1.0.0
     * @return
     */
    static Pageable unpaged() {
        return PageableEmpty.INSTANCE;
    }

    /**
     * 是否分页
     * @ignore
     * @return
     */
    default boolean isPaged() {
        return true;
    }

    /**
     * 获取页码
     * @mock 1
     * @return
     */
    int getPageNum();


    /**
     * 获取页数量(小于等于0为不分页)
     * @mock 10
     * @return
     */
    int getPageSize();


    /**
     * 获取偏移量
     * @ignore
     * @return
     */
    long getOffset();

}
