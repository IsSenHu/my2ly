package com.husen.dao.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuSen on 2018/7/4 13:37.
 */
public class DatatablesView<T> implements Serializable {
    private List<T> data;

    private int recordsTotal;

    private int recordsFiltered;

    private int draw;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
