package com.husen.dao.vo;

/**
 * 分页工具
 * Created by HuSen on 2018/7/6 9:20.
 */
public class PageReqVo<T> {
    private int draw;
    private int page;
    private int size;
    private String sortData;
    private String sortType;
    private T params;

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortData() {
        return sortData;
    }

    public void setSortData(String sortData) {
        this.sortData = sortData;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "PageReqVo{" +
                "draw=" + draw +
                ", page=" + page +
                ", size=" + size +
                ", sortData='" + sortData + '\'' +
                ", sortType='" + sortType + '\'' +
                ", params=" + params +
                '}';
    }
}
