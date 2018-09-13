package com.husen.controller;

import com.husen.base.CommonResponse;
import com.husen.dao.po.MenuPo;
import com.husen.dao.vo.MenuVo;
import com.husen.dao.vo.TreeNode;
import com.husen.exception.ParamException;
import com.husen.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/8/2 15:50.
 */
@RestController
public class MenuController extends BaseController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 用户登录的时候去获取该用户所拥有的权限，然后加载到cookie中去。
     * 针对按钮:1，如果是按钮则在js中去读取cookie中的数据，如果没有cookie则用户没有登录，跳转到注销地址即可。
     *          2，如果有则根据用户的权限去加载按钮。
     * 针对菜单:也是一样的。
     * 通过控制权限和菜单按钮的树形结构来控制权限。
     */

    @GetMapping("/menus")
    private ModelAndView menus() {
        List<MenuVo> list = menuService.findAllTopMenus();
        return new ModelAndView("pages/tables/menus")
                .addObject("list", list);
    }

    @PostMapping("/saveMenu")
    private CommonResponse<MenuPo> saveMenu(@Valid @RequestBody MenuPo menuPo, BindingResult result) throws ParamException {
        if(result.hasErrors()) {
            throw new ParamException(result.getFieldErrors());
        }
        return menuService.saveMenu(menuPo);
    }

    @PostMapping("/deleteMenuById")
    private CommonResponse<Long> deleteMenuById(Long menuId) {
        return menuService.deleteMenuById(menuId);
    }

    @PostMapping("/getAllTopMenus")
    private List<Map<String, String>> getAllTopMenus() {
        return menuService.getAllTopMenus();
    }

    @PostMapping("/getAllMiddleMenus")
    private List<Map<String, String>> getAllMiddleMenus(Long fatherMenuId) {
        return menuService.getAllMiddleMenus(fatherMenuId);
    }

    @PostMapping("/getMiddleMenusByFatherId")
    private List<MenuVo> getMiddleMenusByFatherId(Long fatherMenuId) {
        return menuService.getMiddleMenusByFatherId(fatherMenuId);
    }

    @PostMapping("/getAllBottomMenus")
    private List<Map<String, String>> getAllBottomMenus(Long fatherMenuId) {
        return menuService.getAllBottomMenus(fatherMenuId);
    }

    @PostMapping("/getAllTreeNodes")
    private List<TreeNode> getAllTreeNodes() {
        return menuService.getAllTreeNodes();
    }

    @PostMapping("/saveMenuTree")
    private CommonResponse<String> saveMenuTree(@RequestBody List<TreeNode> treeNodes) {
        return menuService.saveMenuTree(treeNodes);
    }

    @PostMapping("/getMenuById")
    private MenuVo getMenuById(Long menuId) {
        return menuService.getMenuById(menuId);
    }

    @PostMapping("/updateMenuById")
    private CommonResponse<MenuPo> updateMenuById(@Valid @RequestBody MenuPo menuPo, BindingResult result) throws ParamException {
        if(result.hasErrors()) {
            throw new ParamException(result.getFieldErrors());
        }
        return menuService.updateMenuById(menuPo);
    }

    @PostMapping("/menusList")
    private List<TreeNode> menusList() {
        return menuService.menusVoList(getUser());
    }
}
