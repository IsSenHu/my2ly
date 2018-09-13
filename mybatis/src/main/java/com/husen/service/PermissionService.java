package com.husen.service;

import com.husen.dao.po.MenuPo;
import com.husen.dao.po.PermissionPo;
import com.husen.base.CommonResponse;
import com.husen.dao.vo.DatatablesView;
import com.husen.dao.vo.PermissionMenuVo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/7/4 10:45.
 */
@Service
public interface PermissionService {
    CommonResponse<PermissionPo> savePermission(PermissionPo permissionPo);

    DatatablesView<PermissionPo> pagePermission(Map<String,String[]> map, PermissionPo permissionPo);

    CommonResponse<Long> deletePermissionById(Long permissionId);

    Map<String, List<MenuPo>> showMenusByPermissionId(Long permissionId);

    CommonResponse<Map<String, List<MenuPo>>> savePermissionMenu(PermissionMenuVo vo);
}
