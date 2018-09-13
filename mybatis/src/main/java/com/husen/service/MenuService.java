package com.husen.service;

import com.husen.base.CommonResponse;
import com.husen.dao.po.MenuPo;
import com.husen.dao.po.UserPo;
import com.husen.dao.vo.MenuVo;
import com.husen.dao.vo.MenusVo;
import com.husen.dao.vo.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/8/2 16:13.
 */
public interface MenuService {
    CommonResponse<MenuPo> saveMenu(MenuPo menuPo);

    CommonResponse<Long> deleteMenuById(Long menuId);

    List<Map<String, String>> getAllTopMenus();

    List<Map<String,String>> getAllMiddleMenus(Long fatherMenuId);

    List<MenuVo> findAllTopMenus();

    List<MenuVo> getMiddleMenusByFatherId(Long fatherMenuId);

    List<Map<String,String>> getAllBottomMenus(Long fatherMenuId);

    List<TreeNode> getAllTreeNodes();

    CommonResponse<String> saveMenuTree(List<TreeNode> treeNodes);

    MenuVo getMenuById(Long menuId);

    CommonResponse<MenuPo> updateMenuById(MenuPo menuPo);

    List<TreeNode> menusVoList(UserPo userPo);

    MenusVo isPage(String requestURI);
}
