package com.chenyanwu.erp.erpframework.common;

/**
 * @ClassName PageResultBean
 * @Description TODO
 * @Author chenyanwu
 * @Date 2019/1/15 16:36
 * @Version 1.0
 */
public class PageResultBean<T> extends ResultBean<T> {
    private int pageNum;
    private int pageSize;
    private long total;

    public PageResultBean() {
    }

    /**
     * total的值需要自己给根据实际数据设置
     *
     * @param data
     * @param pageNum
     * @param pageSize
     */
    public PageResultBean(T data, int pageNum, int pageSize, long total) {
        super(data);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
