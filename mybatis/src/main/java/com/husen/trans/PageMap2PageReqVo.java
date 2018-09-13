package com.husen.trans;

import com.husen.dao.vo.PageReqVo;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 将请求参数转为分页参数
 * Created by HuSen on 2018/7/6 9:30.
 */
@Component
public class PageMap2PageReqVo<T> implements Function<Map<String, String[]>, PageReqVo> {
    @Override
    public PageReqVo<T> apply(Map<String, String[]> map) {
        PageReqVo<T> vo = new PageReqVo<>();
        String[] draws = map.get("draw");
        if(!Objects.isNull(draws) && draws.length > 0){
            vo.setDraw(Integer.valueOf(draws[0]));
        }
        String[] starts = map.get("start");
        if(!Objects.isNull(starts) && starts.length > 0){
            vo.setPage(Integer.valueOf(starts[0]));
        }
        String[] lengths = map.get("length");
        if(!Objects.isNull(lengths) && lengths.length > 0){
            vo.setSize(Integer.valueOf(lengths[0]));
        }
        String[] orderData = map.get("order[0][column]");
        if(!Objects.isNull(orderData) && orderData.length > 0){
            String[] fields = map.get("columns[" + orderData[0] + "][data]");
            if(!Objects.isNull(fields) && fields.length > 0){
                vo.setSortData(fields[0]);
            }
        }
        String[] orderType = map.get("order[0][dir]");
        if(!Objects.isNull(orderData) && orderData.length > 0){
            vo.setSortType(orderType[0]);
        }
        return vo;
    }
}
