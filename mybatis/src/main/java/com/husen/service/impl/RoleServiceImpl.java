package com.husen.service.impl;

import com.husen.base.Base;
import com.husen.dao.mapper.PermissionMapper;
import com.husen.dao.mapper.RoleMapper;
import com.husen.dao.mapper.RolePermissionMapper;
import com.husen.dao.mapper.UserRoleMapper;
import com.husen.dao.po.PermissionPo;
import com.husen.dao.po.RolePermissionPo;
import com.husen.dao.po.RolePo;
import com.husen.base.CommonResponse;
import com.husen.dao.vo.DatatablesView;
import com.husen.dao.vo.PageReqVo;
import com.husen.dao.vo.TreeNode;
import com.husen.service.RoleService;
import com.husen.service.id.IdService;
import com.husen.trans.PageMap2PageReqVo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by HuSen on 2018/7/4 9:39.
 */
@Service
public class RoleServiceImpl extends Base implements RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleMapper roleMapper;

    private final PermissionMapper permissionMapper;

    private final UserRoleMapper userRoleMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final IdService idService;
    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, IdService idService, PermissionMapper permissionMapper, RolePermissionMapper rolePermissionMapper, UserRoleMapper userRoleMapper) {
        this.roleMapper = roleMapper;
        this.idService = idService;
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<RolePo> save(RolePo rolePo) {
        RolePo po = roleMapper.findByName(rolePo.getName());
        if(Objects.isNull(po)){
            rolePo.setRoleId(idService.getId());
            roleMapper.save(rolePo);
            return commonResponse(rolePo, Constant.SUCCESS);
        }else {
            log.info("该角色已存在:[{}]", rolePo);
            return commonResponse(rolePo, Constant.NAME_EXISTED);
        }
    }

    @Override
    @CacheEvict(value = "menus_cache", allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<String> saveRolePermission(List<TreeNode> treeNodes, HttpSession session) {
        log.info("树节点数据为:{}", treeNodes);
        if(CollectionUtils.isNotEmpty(treeNodes)) {
            TreeNode father = treeNodes.get(0);
            //首先获得roleId
            if(null != father) {
                Long roleId = father.getNodeId();
                //再获得所有被选中的权限Id，并且收集成角色权限集合
                List<TreeNode> childrens = father.getChildren();
                if(CollectionUtils.isNotEmpty(childrens)) {
                    List<RolePermissionPo> collect = childrens.stream().filter(TreeNode::getChecked)
                            .map(children -> new RolePermissionPo(idService.getId(), roleId, children.getNodeId())).collect(Collectors.toList());

                    //首先删除该角色之前所关联的角色权限关系
                    rolePermissionMapper.deleteByRoleId(roleId);
                    //然后保存所有新的角色权限关系
                    if(CollectionUtils.isNotEmpty(collect)) {
                        rolePermissionMapper.saveAll(collect);
                    }
                    AllUsersPermissionChanged();
                    return commonResponse("success", Constant.SUCCESS);
                }
            }
        }
        return commonResponse("error", Constant.PARAM_EXCEPTION);
    }

    @Override
    public DatatablesView<RolePo> pageRole(Map<String, String[]> map, RolePo rolePo) {
        PageReqVo<RolePo> pageReqVo = new PageMap2PageReqVo<RolePo>().apply(map);
        pageReqVo.setParams(rolePo);
        int count = roleMapper.count(rolePo);
        DatatablesView<RolePo> view = new DatatablesView<>();
        List<RolePo> data = roleMapper.page(pageReqVo);
        view.setRecordsFiltered(count);
        view.setRecordsTotal(count);
        view.setDraw(pageReqVo.getDraw());
        view.setData(data);
        return view;
    }

    @Override
    @CacheEvict(value = "menus_cache", allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<Long> deleteRoleById(Long roleId) {
        if(null == roleId) {
            return commonResponse(null, Constant.PARAM_EXCEPTION);
        }
        //删除一个角色首先要删除该角色的用户角色关系
        userRoleMapper.deleteByRoleId(roleId);
        //然后再删除该角色的角色权限关系
        rolePermissionMapper.deleteByRoleId(roleId);
        //最后在删除该角色
        roleMapper.deleteRoleById(roleId);
        AllUsersPermissionChanged();
        return commonResponse(roleId, Constant.SUCCESS);
    }

    @Override
    public List<TreeNode> showAboutRolePermission(Long roleId) {
        RolePo po = roleMapper.findById(roleId);
        //首先查询该角色所拥有的权限
        List<PermissionPo> hads = permissionMapper.findByRoleId(roleId);
        //再查询出所有未被设置的权限
        List<PermissionPo> all = permissionMapper.findAll();
        //过滤出未拥有的权限
        List<PermissionPo> notBeSelected = all.stream().filter(permission -> !hads.contains(permission)).collect(Collectors.toList());
        //创建父节点
        List<TreeNode> treeNodes = new ArrayList<>();
        TreeNode father = new TreeNode();
        father.setChkDisabled(Boolean.TRUE);
        father.setNodeId(roleId);
        father.setName(po.getName());
        father.setParent(Boolean.TRUE);
        father.setOpen(Boolean.TRUE);
        father.setHidden(Boolean.FALSE);
        father.setNocheck(Boolean.TRUE);
        //添加子节点
        List<TreeNode> childens = new ArrayList<>();
        hads.forEach(permission -> childens.add(permission2TreeNode(permission, Boolean.TRUE)));
        notBeSelected.forEach(permission -> childens.add(permission2TreeNode(permission, Boolean.FALSE)));
        father.setChildren(childens);
        treeNodes.add(father);
        return treeNodes;
    }

    private TreeNode permission2TreeNode(PermissionPo permission, Boolean checked) {
        TreeNode child = new TreeNode();
        child.setNodeId(permission.getPermissionId());
        child.setName(permission.getName());
        child.setChecked(checked);
        child.setChkDisabled(Boolean.FALSE);
        child.setHalfCheck(Boolean.FALSE);
        child.setParent(Boolean.FALSE);
        child.setOpen(Boolean.TRUE);
        child.setHidden(Boolean.FALSE);
        child.setNocheck(Boolean.FALSE);
        return child;
    }
}
