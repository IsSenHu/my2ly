package com.husen.service.impl;

import com.husen.base.Base;
import com.husen.base.CommonResponse;
import com.husen.dao.mapper.IconMapper;
import com.husen.dao.po.IconPo;
import com.husen.dao.vo.DatatablesView;
import com.husen.dao.vo.PageReqVo;
import com.husen.service.IconService;
import com.husen.trans.PageMap2PageReqVo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/8/16 15:56.
 */
@Service
public class IconServiceImpl extends Base implements IconService {
    private static final Logger log = LoggerFactory.getLogger(IconServiceImpl.class);
    private static final String IMG_HOST = "http://192.168.162.128/";
    private final IconMapper iconMapper;
    @Autowired
    public IconServiceImpl(IconMapper iconMapper) {
        this.iconMapper = iconMapper;
    }

    @Override
    @Transactional
    public void saveIcon(IconPo iconPo) {
        iconMapper.save(iconPo);
    }

    @Override
    public boolean ifExisted(String name) {
        log.info("检查该图标的名称:[{}]", name);
        return iconMapper.countByName(name) > 0;
    }

    @Override
    public DatatablesView<IconPo> pageIcon(Map<String, String[]> map, IconPo iconPo) {
        PageReqVo<IconPo> pageReqVo = new PageMap2PageReqVo<IconPo>().apply(map);
        pageReqVo.setParams(iconPo);
        int count = iconMapper.count(iconPo);
        DatatablesView<IconPo> view = new DatatablesView<>();
        List<IconPo> data = iconMapper.pageIcon(pageReqVo);
        if(CollectionUtils.isNotEmpty(data)) {
            data.forEach(icon -> icon.setPath(IMG_HOST + icon.getPath()));
        }
        view.setRecordsFiltered(count);
        view.setRecordsTotal(count);
        view.setDraw(pageReqVo.getDraw());
        view.setData(data);
        return view;
    }

    @Override
    @Transactional
    public CommonResponse<Integer> deleteIconById(Integer iconId) {
        if(null == iconId) {
            return commonResponse(null, Constant.PARAM_EXCEPTION);
        }
        iconMapper.deleteIconById(iconId);
        return commonResponse(iconId, Constant.SUCCESS);
    }

    @Override
    public List<IconPo> getAllIcons() {
        return iconMapper.findAll();
    }
}
