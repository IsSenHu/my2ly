package com.husen.dao.mapper;

import com.husen.dao.po.MenuPo;
import com.husen.dao.vo.PermissionMenuVo;

import java.util.List;

/**
 * Created by HuSen on 2018/8/2 16:21.
 */
public interface MenuMapper {
    void deleteMenuById(Long menuId);

    void save(MenuPo menuPo);

    List<MenuPo> topMenus();

    int countByFatherMenuId(Long fatherMenuId);

    List<MenuPo> middleMenus(Long menuId);

    List<MenuPo> bottomMenus(Long menuId);

    List<MenuPo> buttons(Long menuId);

    int countById(Long menuId);

    void updateMenusStatus(List<MenuPo> nodes);

    MenuPo findById(Long menuId);

    void updateMenuById(MenuPo menuPo);

    MenuPo findByPermissionId(Long permissionId);

    List<MenuPo> notBeSelected();

    void clearPermission(Long permissionId);

    void addPermission(PermissionMenuVo vo);

    List<MenuPo> findAll();

    MenuPo findByUrl(String requestURI);

    List<MenuPo> findByFatherId(Long menuId);
}
