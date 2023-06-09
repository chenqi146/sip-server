package com.cqmike.sip.common.domain;

/**
 * 空分页枚举单例
 *
 * @author chen qi
 **/
public enum PageableEmpty implements Pageable {
    INSTANCE;

    /**
     * 是否分页
     *
     * @return
     */
    @Override
    public boolean isPaged() {
        return false;
    }

    /**
     * 获取页码
     *
     * @return
     */
    @Override
    public int getPageNum() {
        return 0;
    }

    /**
     * 获取页数量(小于等于0为不分页)
     *
     * @return
     */
    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public long getOffset() {
        return 0;
    }


}
