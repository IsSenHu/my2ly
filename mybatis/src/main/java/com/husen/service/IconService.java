package com.husen.service;

import com.husen.base.CommonResponse;
import com.husen.dao.po.IconPo;
import com.husen.dao.vo.DatatablesView;

import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/8/16 15:55.
 */
public interface IconService {
    void saveIcon(IconPo iconPo);

    boolean ifExisted(String name);

    DatatablesView<IconPo> pageIcon(Map<String,String[]> map, IconPo iconPo);

    CommonResponse<Integer> deleteIconById(Integer iconId);

    List<IconPo> getAllIcons();
}
