package com.husen.service.impl;

import com.husen.base.Base;
import com.husen.base.CommonResponse;
import com.husen.config.security.WebSecurityConstant;
import com.husen.dao.mapper.IconMapper;
import com.husen.dao.mapper.MenuMapper;
import com.husen.dao.mapper.PermissionMapper;
import com.husen.dao.po.IconPo;
import com.husen.dao.po.MenuPo;
import com.husen.dao.po.PermissionPo;
import com.husen.dao.po.UserPo;
import com.husen.dao.vo.*;
import com.husen.service.MenuService;
import com.husen.service.UserService;
import com.husen.service.id.IdService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by HuSen on 2018/8/2 16:13.
 */
@Service
public class MenuServiceImpl extends Base implements MenuService {
    private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
    /**默认图标*/
    private static final String DEFAULT_ICON = "nav-icon fa fa-tree";
    /**默认按钮*/
    private static final String DEFAULT_BUTTON = "btn-default";
    /**顶级菜单*/
    private static final Integer TOP_MENU = 1;
    /**三级菜单*/
    private static final Integer BOTTOM_MENU = 3;
    /**是菜单*/
    private static final Integer MENU = 1;
    /**是按钮*/
    private static final Integer BUTTON = 2;
    /**默认启用*/
    private static final Integer ENABLE = 1;
    /**不启用*/
    private static final Integer DISENABLE = 2;
    /**不能起作用的Url*/
    private static final String UNENABLE_URL = "javascript:void(0)";
    /**图标前缀*/
    private static final String ICON_PREFIX = "http://192.168.162.128/";

    private static final String GLOBAL_BUTTON_SIGN = "global";

    private static final String TREE_MENUS_CACHE = "TreeMenusCache";

    private static final String ROW_BUTTON_SIGN = "row";
    private final MenuMapper menuMapper;
    private final PermissionMapper permissionMapper;
    private final IdService idService;
    private final UserService userService;
    private final IconMapper iconMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, IdService idService, PermissionMapper permissionMapper, UserService userService, IconMapper iconMapper) {
        this.menuMapper = menuMapper;
        this.idService = idService;
        this.permissionMapper = permissionMapper;
        this.userService = userService;
        this.iconMapper = iconMapper;
    }

    /**
     * 根据fatherMenuId获取子菜单或按钮
     * @param fatherMenuId 父菜单ID
     * @return 子菜单或按钮
     * @Cacheable 启用缓存 使用Cache : menus_cache, key : getAllBottomMenus + fatherMenuId
     */
    @Override
    @Cacheable(value = "menus_cache", key = "'getAllBottomMenus'.concat(fatherMenuId)")
    public List<Map<String, String>> getAllBottomMenus(Long fatherMenuId) {
        List<Map<String, String>> result = new ArrayList<>();
        List<MenuPo> topMenus = menuMapper.bottomMenus(fatherMenuId);
        topMenus.forEach(menu -> result.add(idAndNames(menu)));
        return result;
    }

    /**
     * 1，菜单等级，如果为空，默认为一级菜单，一共可以有3级{1, 2, 3}
     * 2，菜单图标，如果为空，设为默认图标
     * 3，父菜单ID，除了最高等级菜单，其余菜单都不能为空
     * 4，是菜单还是按钮，默认是菜单，菜单的地址是访问地址，按钮的地址是按钮的资源权限名称
     **/
    @Override
    @CacheEvict(value = "menus_cache", allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<MenuPo> saveMenu(MenuPo menuPo) {
        //默认是菜单
        if(null == menuPo.getButtonOrMenu() || (ObjectUtils.notEqual(MENU, menuPo.getButtonOrMenu()) && ObjectUtils.notEqual(BUTTON, menuPo.getButtonOrMenu()))) {
            menuPo.setButtonOrMenu(MENU);
        }
        //如果是菜单进行菜单的设置
        if(MENU.equals(menuPo.getButtonOrMenu())) {
            //菜单默认等级为1
            menuPo.setLevel((null == menuPo.getLevel() ? TOP_MENU : menuPo.getLevel()));
            //菜单默认图标是nav-icon fa fa-tree
            menuPo.setIcon((StringUtils.isBlank(menuPo.getIcon()) ? DEFAULT_ICON : menuPo.getIcon()));
            //如果菜单等级为1，有父菜单ID，则将父菜单ID设置为null
            menuPo.setFatherMenuId((StringUtils.isNotBlank(menuPo.getFatherMenuId()) && TOP_MENU.equals(menuPo.getLevel()) ? null : menuPo.getFatherMenuId()));
        }
        //如果是按钮，进行按钮的设置
        else if(BUTTON.equals(menuPo.getButtonOrMenu())) {
            //按钮没有等级这个属性
            menuPo.setLevel(null);
            //默认按钮是btn-default
            menuPo.setIcon((StringUtils.isBlank(menuPo.getIcon()) ? DEFAULT_BUTTON : menuPo.getIcon()));
        }
        //开始校验
        CommonResponse<MenuPo> validate = validate(menuPo);
        if (null != validate) {
            //校验不通过
            return validate;
        }
        //开始保存
        menuPo.setMenuId(idService.getId());
        menuPo.setIsEnable(ENABLE);
        menuMapper.save(menuPo);
        //更新认证权限集合
        refreshAuthorizeMenus();
        return commonResponse(menuPo, Constant.SUCCESS);
    }

    @Override
    @CacheEvict(value = "menus_cache", allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<Long> deleteMenuById(Long menuId) {
        if(null == menuId) {
            return commonResponse(null, Constant.PARAM_EXCEPTION);
        }
        //将关联的权限的menuId设为Null
        permissionMapper.clearMenu(menuId);
        //删除该菜单
        menuMapper.deleteMenuById(menuId);
        //更新认证权限集合
        refreshAuthorizeMenus();
        AllUsersPermissionChanged();
        return commonResponse(menuId, Constant.SUCCESS);
    }

    @Override
    @Cacheable(value = "menus_cache", key = "'getAllTopMenus'")
    public List<Map<String, String>> getAllTopMenus() {
        List<Map<String, String>> result = new ArrayList<>();
        List<MenuPo> topMenus = menuMapper.topMenus();
        topMenus.forEach(menu -> result.add(idAndNames(menu)));
        return result;
    }

    @Override
    @Cacheable(value = "menus_cache", key = "'getAllMiddleMenus'.concat(fatherMenuId)")
    public List<Map<String, String>> getAllMiddleMenus(Long fatherMenuId) {
        List<Map<String, String>> result = new ArrayList<>();
        List<MenuPo> topMenus = menuMapper.middleMenus(fatherMenuId);
        topMenus.forEach(menu -> result.add(idAndNames(menu)));
        return result;
    }

    @Override
    @Cacheable(value = "menus_cache", key = "'findAllTopMenus'")
    public List<MenuVo> findAllTopMenus() {
        List<MenuPo> pos = menuMapper.topMenus();
        return po2Vo(pos);
    }

    @Override
    @Cacheable(value = "menus_cache", key = "'getMiddleMenusByFatherId'.concat(fatherMenuId)")
    public List<MenuVo> getMiddleMenusByFatherId(Long fatherMenuId) {
        List<MenuPo> pos = menuMapper.middleMenus(fatherMenuId);
        return po2Vo(pos);
    }

    @Override
    @Cacheable(value = "menus_cache", key = "'getAllTreeNodes'")
    public List<TreeNode> getAllTreeNodes() {
        List<MenuPo> topMenus = menuMapper.topMenus();
        List<TreeNode> treeNodes = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(topMenus)) {
            topMenus.forEach(top -> {
                TreeNode topNode = createTreeNode(top, Boolean.TRUE);
                //加载二级菜单
                List<MenuPo> middleMenus = menuMapper.middleMenus(top.getMenuId());
                if(CollectionUtils.isNotEmpty(middleMenus)) {
                    List<TreeNode> treeMiddleNodes = new ArrayList<>();
                    middleMenus.forEach(middle -> {
                        TreeNode middleNode = createTreeNode(middle, Boolean.FALSE);
                        //加载三级菜单
                        List<MenuPo> bottomMenus = menuMapper.bottomMenus(middle.getMenuId());
                        if(CollectionUtils.isNotEmpty(bottomMenus)) {
                            List<TreeNode> treeBottomNodes = new ArrayList<>();
                            bottomMenus.forEach(bottom -> {
                                TreeNode bottomNode = createTreeNode(bottom, Boolean.FALSE);
                                //加载按钮
                                List<MenuPo> buttons = menuMapper.buttons(bottom.getMenuId());
                                if(CollectionUtils.isNotEmpty(buttons)) {
                                    List<TreeNode> treebuttonNodes = new ArrayList<>();
                                    buttons.forEach(button -> {
                                        TreeNode buttonNode = createTreeNode(button, Boolean.FALSE);
                                        treebuttonNodes.add(buttonNode);
                                    });
                                    bottomNode.setChildren(treebuttonNodes);
                                }
                                treeBottomNodes.add(bottomNode);
                            });
                            middleNode.setChildren(treeBottomNodes);
                        }
                        treeMiddleNodes.add(middleNode);
                    });
                    topNode.setChildren(treeMiddleNodes);
                }
                treeNodes.add(topNode);
            });
        }
        return treeNodes;
    }

    @Override
    @CacheEvict(value = "menus_cache", allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<String> saveMenuTree(List<TreeNode> treeNodes) {
        log.info("修改后的菜单树结构为:{}", treeNodes);
        if(CollectionUtils.isNotEmpty(treeNodes)) {
            List<MenuPo> nodes = new ArrayList<>();
            treeNodes.forEach(top -> {
                nodes.add(node2MenuPo(top));
                List<TreeNode> middles = top.getChildren();
                if(CollectionUtils.isNotEmpty(middles)) {
                    middles.forEach(middle -> {
                        nodes.add(node2MenuPo(middle));
                        List<TreeNode> bottoms = middle.getChildren();
                        if(CollectionUtils.isNotEmpty(bottoms)) {
                            bottoms.forEach(bottom -> {
                                nodes.add(node2MenuPo(bottom));
                                List<TreeNode> buttons = bottom.getChildren();
                                if(CollectionUtils.isNotEmpty(buttons)) {
                                    buttons.forEach(button -> nodes.add(node2MenuPo(button)));
                                }
                            });
                        }
                    });
                }
            });
            menuMapper.updateMenusStatus(nodes);
        }
        //更新认证权限集合
        refreshAuthorizeMenus();
        AllUsersPermissionChanged();
        return commonResponse("success", Constant.SUCCESS);
    }

    @Override
    @Cacheable(value = "menus_cache", key = "#menuId")
    public MenuVo getMenuById(Long menuId) {
        MenuPo po = menuMapper.findById(menuId);
        MenuVo vo = new MenuVo();
        BeanUtils.copyProperties(po, vo);
        List<IconPo> icons = iconMapper.findAll();
        vo.setIcons(icons);
        return null == menuId ? null : vo;
    }

    @Override
    @CacheEvict(value = "menus_cache", allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<MenuPo> updateMenuById(MenuPo menuPo) {
        MenuPo byId = menuMapper.findById(menuPo.getMenuId());
        byId.setName(menuPo.getName());
        byId.setUrl(menuPo.getUrl());
        byId.setIcon(menuPo.getIcon());
        menuMapper.updateMenuById(menuPo);
        return commonResponse(menuPo, Constant.SUCCESS);
    }

    @Override
    @Transactional
    @Cacheable(value = "menus_cache", key = "#user.userId")
    public List<TreeNode> menusVoList(UserPo user) {
        List<TreeNode> result = new ArrayList<>();
        if(Objects.isNull(user)) {
            return result;
        }

        //获得该用户所拥有的菜单ID
        List<Long> ids = user.getAuthorities().stream().map(authority -> Long.valueOf(authority.getAuthority())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(ids)) {
            return result;
        }

        //再获取所有的菜单
        List<MenuPo> menus = ids.stream().map(menuMapper::findById).filter(menu -> menu.getIsEnable() == 1).collect(Collectors.toList());

        //首先获得一级菜单
        List<MenuPo> tops = menus.stream().filter(menu -> (MENU.equals(menu.getButtonOrMenu()) && menu.getLevel() == 1)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(tops)) {
            return result;
        }
        List<MenuPo> temp = new ArrayList<>();
        tops.forEach(menu -> {
            if("主页".equals(menu.getName())) {
                temp.add(menu);
            }
        });
        tops.forEach(menu -> {
            if(!"主页".equals(menu.getName())) {
                temp.add(menu);
            }
        });
        tops = temp;
        //所有二级菜单
        List<MenuPo> middles = menus.stream().filter(menu -> (MENU.equals(menu.getButtonOrMenu()) && menu.getLevel() == 2)).collect(Collectors.toList());
        //所有三级菜单
        List<MenuPo> bottoms = menus.stream().filter(menu -> (MENU.equals(menu.getButtonOrMenu()) && menu.getLevel() == 3)).collect(Collectors.toList());

        tops.forEach(top -> {
            TreeNode one = menu2Menus(top, true);
            List<TreeNode> twos = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(middles)) {
                //获取二级菜单
                List<MenuPo> seconds = middles.stream().filter(middle -> String.valueOf(top.getMenuId()).equals(middle.getFatherMenuId())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(seconds)) {
                    seconds.forEach(second -> {
                        TreeNode two = menu2Menus(second, false);
                        List<TreeNode> threes = new ArrayList<>();
                        if(CollectionUtils.isNotEmpty(bottoms)) {
                            //获取三级菜单
                            List<MenuPo> thirds = bottoms.stream().filter(bottom -> String.valueOf(second.getMenuId()).equals(bottom.getFatherMenuId())).collect(Collectors.toList());
                            if(CollectionUtils.isNotEmpty(thirds)) {
                                thirds.forEach(third -> threes.add(menu2Menus(third, false)));
                            }
                        }
                        two.setChildren(CollectionUtils.isNotEmpty(threes) ? threes : null);
                        if(CollectionUtils.isNotEmpty(threes)) {
                            two.setUrl(UNENABLE_URL);
                        }
                        twos.add(two);
                    });
                }
            }
            one.setChildren(CollectionUtils.isNotEmpty(twos) ? twos : null);
            if(CollectionUtils.isNotEmpty(twos)) {
                one.setUrl(UNENABLE_URL);
            }
            result.add(one);

        });
        return result;
    }

    @Override
    @Cacheable(value = "menus_cache", key = "#requestURI")
    public MenusVo isPage(String requestURI) {
        MenusVo vo = new MenusVo();
        MenuPo urlMenu = menuMapper.findByUrl(requestURI);
        //首先是按钮或者没有这个菜单则不是页面
        if(Objects.isNull(urlMenu) || BUTTON.equals(urlMenu.getButtonOrMenu())) {
            return null;
        }
        //如果该页面下有子菜单则不是页面
        List<MenuPo> childrens = menuMapper.findByFatherId(urlMenu.getMenuId());
        if(CollectionUtils.isNotEmpty(childrens)) {
            for(MenuPo children : childrens) {
                if(MENU.equals(children.getButtonOrMenu())) {
                    return null;
                }
            }
        }
        //获取当前用户的所有权限
        UserPo user = getUser();
        if(!Objects.isNull(user)) {
            List<String> userAuthorizes = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            //获取当前页面下的所有按钮
            List<MenuPo> pageButtons = menuMapper.findByFatherId(urlMenu.getMenuId());
            if(CollectionUtils.isNotEmpty(pageButtons)) {
                vo.setPageGlobalButtons(
                        pageButtons.stream()
                                .filter(button -> StringUtils.indexOfIgnoreCase(button.getIcon(), GLOBAL_BUTTON_SIGN) >= 0)
                                .filter(button -> userAuthorizes.contains(String.valueOf(button.getMenuId())))
                                .map(button -> button.getIcon().split(";")[1])
                                .collect(Collectors.toList()));

                vo.setPageRowButtons(
                        pageButtons.stream()
                                .filter(button -> StringUtils.indexOfIgnoreCase(button.getIcon(), ROW_BUTTON_SIGN) >= 0)
                                .filter(button -> userAuthorizes.contains(String.valueOf(button.getMenuId())))
                                .map(button -> button.getIcon().split(";")[1])
                                .collect(Collectors.toList()));
            }
        }

        MenuPo byIdOne = NumberUtils.isNumber(urlMenu.getFatherMenuId()) ? menuMapper.findById(Long.valueOf(urlMenu.getFatherMenuId())) : null;
        if(!Objects.isNull(byIdOne)) {
            if(StringUtils.isNotBlank(byIdOne.getFatherMenuId())) {
                MenuPo byIdTwo = menuMapper.findById(Long.valueOf(byIdOne.getFatherMenuId()));
                if(!Objects.isNull(byIdTwo)) {
                    vo.setName(byIdTwo.getName());
                    vo.setUrl(byIdTwo.getUrl());

                    MenusVo voChild = new MenusVo();
                    voChild.setName(byIdOne.getName());
                    voChild.setUrl(byIdOne.getUrl());
                    vo.setChild(voChild);

                    MenusVo voLastChild = new MenusVo();
                    voLastChild.setName(urlMenu.getName());
                    voLastChild.setUrl(urlMenu.getUrl());
                    voChild.setChild(voChild);
                }
            }else {
                vo.setName(byIdOne.getName());
                vo.setUrl(byIdOne.getUrl());

                MenusVo voChild = new MenusVo();
                voChild.setName(urlMenu.getName());
                voChild.setUrl(urlMenu.getUrl());
                vo.setChild(voChild);
            }
        }else {
            vo.setName(urlMenu.getName());
            vo.setUrl(urlMenu.getUrl());
        }
        return vo;
    }

    /**
     * 更新认证权限集合
     */
    private void refreshAuthorizeMenus() {
        List<MenuPo> allUrls = userService.allUrls();
        Map<String, String> authorizeMenus = WebSecurityConstant.AUTHORIZE_MENUS;
        authorizeMenus.clear();
        if(CollectionUtils.isNotEmpty(allUrls)) {
            allUrls.forEach(menu -> {
                if(!Objects.isNull(menu)) {
                    authorizeMenus.put(menu.getUrl(), NumberUtils.compare(ENABLE, menu.getIsEnable()) == 0 ? String.valueOf(menu.getMenuId()) : String.valueOf(idService.getId()));
                }
            });
        }
    }

    private CommonResponse<MenuPo> validate(MenuPo menuPo) {
        CommonResponse<MenuPo> commonResponse = null;
        //如果不是父菜单，则父菜单ID必须不能为空，或者菜单等级不在预设范围之内，则校验不通过
        if((ObjectUtils.notEqual(TOP_MENU, menuPo.getLevel()) && null == menuPo.getFatherMenuId()) ||
                (null != menuPo.getLevel() && (0 < NumberUtils.compare(TOP_MENU, menuPo.getLevel()) || NumberUtils.compare(BOTTOM_MENU, menuPo.getLevel()) < 0))) {
            commonResponse = commonResponse(menuPo, Constant.PARAM_EXCEPTION);
        }

        //父菜单ID必须真实存在
        else if(ObjectUtils.notEqual(null, menuPo.getFatherMenuId())) {
            int total = menuMapper.countById(Long.valueOf(menuPo.getFatherMenuId()));
            commonResponse = total >= 1 ? null : commonResponse(menuPo, Constant.PARAM_EXCEPTION);
        }
        return commonResponse;
    }

    private MenuPo node2MenuPo(TreeNode top) {
        MenuPo menuPo = new MenuPo();
        menuPo.setMenuId(top.getNodeId());
        menuPo.setIsEnable(top.getChecked() ? ENABLE : DISENABLE);
        return menuPo;
    }


    private Map<String, String> idAndNames(MenuPo menu) {
        HashMap<String, String> map = new HashMap<>(2);
        map.put("menuId", String.valueOf(menu.getMenuId()));
        map.put("name", menu.getName());
        return map;
    }

    private List<MenuVo> po2Vo(List<MenuPo> pos) {
        List<MenuVo> vos = new ArrayList<>();
        pos.forEach(po -> {
            MenuVo vo = new MenuVo();
            BeanUtils.copyProperties(po, vo);
            vo.setIcon("[{" + po.getIcon() + "}]");
            if(null != po.getPermissionId()) {
                PermissionPo permissionPo = permissionMapper.findById(po.getPermissionId());
                if(null != permissionPo) {
                    vo.setPermissionName(permissionPo.getName());
                }
            }
            vos.add(vo);
        });
        return vos;
    }

    private TreeNode createTreeNode(MenuPo menuPo, Boolean isParent) {
        TreeNode node = new TreeNode();
        node.setNodeId(menuPo.getMenuId());
        node.setChecked(ENABLE.equals(menuPo.getIsEnable()) ? Boolean.TRUE : Boolean.FALSE);
        node.setChkDisabled(Boolean.FALSE);
        node.setHidden(Boolean.FALSE);
        node.setName(menuPo.getName());
        node.setNocheck(Boolean.FALSE);
        node.setOpen(Boolean.TRUE);
        node.setParent(isParent);
        node.setHalfCheck(Boolean.FALSE);
        node.setType(BUTTON.equals(menuPo.getButtonOrMenu()) ? "btn" : "menu");
        return node;
    }

    private TreeNode menu2Menus(MenuPo menuPo, Boolean isParent) {
        TreeNode menusVo = new TreeNode();
        menusVo.setNodeId(menuPo.getMenuId());
        menusVo.setChecked(Boolean.FALSE);
        menusVo.setChkDisabled(Boolean.TRUE);
        menusVo.setHidden(Boolean.FALSE);
        menusVo.setName(menuPo.getName());
        menusVo.setNocheck(Boolean.TRUE);
        menusVo.setOpen(Boolean.FALSE);
        menusVo.setParent(isParent);
        menusVo.setHalfCheck(Boolean.FALSE);
        menusVo.setUrl(menuPo.getUrl());
        menusVo.setTarget("_self");
        menusVo.setIcon(ICON_PREFIX + menuPo.getIcon());
        return menusVo;
    }
 }
