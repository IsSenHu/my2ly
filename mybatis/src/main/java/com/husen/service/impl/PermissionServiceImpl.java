package com.husen.service.impl;

import com.husen.base.Base;
import com.husen.dao.mapper.MenuMapper;
import com.husen.dao.mapper.PermissionMapper;
import com.husen.dao.mapper.RolePermissionMapper;
import com.husen.dao.po.MenuPo;
import com.husen.dao.po.PermissionPo;
import com.husen.base.CommonResponse;
import com.husen.dao.vo.DatatablesView;
import com.husen.dao.vo.PageReqVo;
import com.husen.dao.vo.PermissionMenuVo;
import com.husen.service.PermissionService;
import com.husen.service.id.IdService;
import com.husen.trans.PageMap2PageReqVo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Created by HuSen on 2018/7/4 10:45.
 */
@Service
public class PermissionServiceImpl extends Base implements PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);

    private final PermissionMapper permissionMapper;
    private final IdService idService;
    private final MenuMapper menuMapper;
    private final RolePermissionMapper rolePermissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionMapper permissionMapper, IdService idService, MenuMapper menuMapper, RolePermissionMapper rolePermissionMapper) {
        this.permissionMapper = permissionMapper;
        this.idService = idService;
        this.menuMapper = menuMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<PermissionPo> savePermission(PermissionPo permissionPo) {
        log.info("接收到的权限:[{}]", permissionPo);
        PermissionPo po = permissionMapper.findByName(permissionPo.getName());
        if(Objects.isNull(po)){
            permissionPo.setPermissionId(idService.getId());
            permissionMapper.save(permissionPo);
            return commonResponse(permissionPo, Constant.SUCCESS);
        }else {
            log.info("该权限已存在:[{}]", permissionPo);
            return commonResponse(permissionPo, Constant.EXISTED);
        }
    }

    @Override
    public DatatablesView<PermissionPo> pagePermission(Map<String, String[]> map, PermissionPo permissionPo) {
        PageReqVo<PermissionPo> pageReqVo = new PageMap2PageReqVo<PermissionPo>().apply(map);
        pageReqVo.setParams(permissionPo);
        int count = permissionMapper.count(permissionPo);
        List<PermissionPo> data = permissionMapper.page(pageReqVo);
        DatatablesView<PermissionPo> view = new DatatablesView<>();
        view.setData(data);
        view.setRecordsTotal(count);
        view.setRecordsFiltered(count);
        view.setDraw(pageReqVo.getDraw());
        return view;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<Long> deletePermissionById(Long permissionId) {
        if(null == permissionId) {
            return commonResponse(null, Constant.PARAM_EXCEPTION);
        }
        //首先删除该权限的角色权限关系
        rolePermissionMapper.deleteByPermissionId(permissionId);
        //然后再把该权限对饮菜单的permissonId设为null
        menuMapper.clearPermission(permissionId);
        //最后再删除该权限
        permissionMapper.deletePermissionById(permissionId);
        AllUsersPermissionChanged();
        return commonResponse(permissionId, Constant.SUCCESS);
    }

    @Override
    public Map<String, List<MenuPo>> showMenusByPermissionId(Long permissionId) {
        Map<String, List<MenuPo>> result = new HashMap<>(2);
        if(null == permissionId) {
            List<MenuPo> all = menuMapper.findAll();
            result.put("notBeSelected", idAndNames(all));
        }else {
            //获取当前权限所关联的菜单
            MenuPo currentMenu = menuMapper.findByPermissionId(permissionId);
            //获取所有还未被关联的菜单
            List<MenuPo> notBeSelected = menuMapper.notBeSelected();

            result.put("current", null == currentMenu ? null : idAndNames(currentMenu));
            result.put("notBeSelected", idAndNames(notBeSelected));
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<Map<String, List<MenuPo>>> savePermissionMenu(PermissionMenuVo vo) {
        //如果传递过来的menuId=0则是清除当前权限所关联的菜单
        if(vo.getMenuId() == 0) {
            permissionMapper.clearMenu(vo.getPermissionId());
            menuMapper.clearPermission(vo.getPermissionId());
        }
        //否则就是修改为新的菜单
        else {
            menuMapper.clearPermission(vo.getPermissionId());
            menuMapper.addPermission(vo);
            permissionMapper.addMenu(vo);
        }
        AllUsersPermissionChanged();
        return commonResponse(showMenusByPermissionId(null), Constant.SUCCESS);
    }

    private List<MenuPo> idAndNames(List<MenuPo> notBeSelected) {
        List<MenuPo> maps = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(notBeSelected)) {
            notBeSelected.forEach(x -> {
                MenuPo map = new MenuPo();
                map.setMenuId(x.getMenuId());
                map.setName(x.getName());
                maps.add(map);
            });
        }
        return maps;
    }

    private List<MenuPo> idAndNames(MenuPo currentMenu) {
        List<MenuPo> maps = new ArrayList<>();
        MenuPo map = new MenuPo();
        map.setMenuId(currentMenu.getMenuId());
        map.setName(currentMenu.getName());
        maps.add(map);
        return maps;
    }
}
