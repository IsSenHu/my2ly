package com.husen.dao.mapper;

import com.husen.dao.po.IconPo;
import com.husen.dao.vo.PageReqVo;
import java.util.List;

/**
 * Created by HuSen on 2018/8/16 16:01.
 */
public interface IconMapper {
    int countByName(String name);
    void save(IconPo iconPo);

    int count(IconPo iconPo);

    List<IconPo> pageIcon(PageReqVo<IconPo> poPageReqVo);

    void deleteIconById(Integer iconId);

    List<IconPo> findAll();
}
