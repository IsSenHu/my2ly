package com.husen.service.impl;

import com.husen.base.Base;
import com.husen.config.security.WebSecurityConstant;
import com.husen.dao.mapper.*;
import com.husen.dao.po.*;
import com.husen.base.CommonResponse;
import com.husen.dao.vo.*;
import com.husen.service.UserService;
import com.husen.service.WebSocketService;
import com.husen.service.id.IdService;
import com.husen.trans.PageMap2PageReqVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by HuSen on 2018/6/28 12:31.
 */
@Service("userService")
public class UserServiceImpl extends Base implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final IdService idService;

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final MenuMapper menuMapper;

    private final static String USERNAME_NOT_EXIST = "用户名不存在";

    @Autowired
    public UserServiceImpl(UserMapper userMapper, IdService idService, RoleMapper roleMapper, UserRoleMapper userRoleMapper, RolePermissionMapper rolePermissionMapper, MenuMapper menuMapper) {
        this.userMapper = userMapper;
        this.idService = idService;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public List<UserPo> findAll() {
        List<UserPo> pos = userMapper.findAll();
        log.debug("findAll():[{}]", pos);
        return pos;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<UserPo> save(UserVo userVo) throws ParseException {
        UserPo userPo = new UserPo();
        BeanUtils.copyProperties(userVo, userPo);
        userPo.setBirthday(getLocalDate(Constant.DATE_PATTERN_ONE, userVo.getBirthday()));
        log.debug("save():[{}]", userVo);
        UserPo po = userMapper.findByUsername(userPo.getUsername());
        if(Objects.isNull(po)){
            userPo.setPassword(new BCryptPasswordEncoder().encode(userVo.getPassword()));
            userPo.setUserId(idService.getId());
            userMapper.save(userPo);
            return commonResponse(userPo, Constant.SUCCESS);
        }else {
            log.info("该用户名已存在:[{}]", userPo.getUsername());
            return commonResponse(userPo, Constant.USERNAME_EXISTED);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<UserRolePo> saveUserRole(UserRolePo userRolePo) {
        log.debug("saveUserRole():[{}]", userRolePo);
        UserPo userPo = userMapper.findById(userRolePo.getUserId());
        RolePo rolePo = roleMapper.findById(userRolePo.getRoleId());
        if(Objects.isNull(userPo) || Objects.isNull(rolePo)){
            log.info("用户或角色不存在:[{}]", userRolePo);
            return commonResponse(userRolePo, Constant.NOT_EXISTED);
        }
        UserRolePo po = userRoleMapper.findByUserIdAndRoleId(userRolePo);
        if(!Objects.isNull(po)){
            log.info("该用户角色关系已存在:[{}]", userRolePo);
            return commonResponse(userRolePo, Constant.EXISTED);
        }else {
            userRolePo.setId(idService.getId());
            userRoleMapper.save(userRolePo);
            WebSecurityConstant.USER_PERMISSION_IF_MODIFIED_MAP.put(userPo.getUserId(), Boolean.TRUE);
            return commonResponse(userRolePo, Constant.SUCCESS);
        }
    }

    @Override
    public DatatablesView<UserPo> pageUser(Map<String, String[]> map, UserPo userPo) {
        PageReqVo<UserPo> poPageReqVo = new PageMap2PageReqVo<UserPo>().apply(map);
        poPageReqVo.setParams(userPo);
        int count  = userMapper.count(userPo);
        DatatablesView<UserPo> view = new DatatablesView<>();
        view.setRecordsTotal(count);
        List<UserPo> list = userMapper.page(poPageReqVo);
        //年龄应该由生日来计算出来，建议写个定时任务，每天更新一次年龄
        view.setRecordsFiltered(count);
        view.setData(list);
        view.setDraw(poPageReqVo.getDraw());
        return view;
    }

    /**
     * 查找和用户相关的角色，已拥有和未拥有的角色
     * @param userId 用户ID
     * @return 查询结果
     */
    @Override
    public List<TreeNode> showAboutUserRoles(Long userId) {
        UserPo po = userMapper.findUserById(userId);
        log.info("查找出的用户是:{}", po);
        //用户已拥有的角色
        List<RolePo> userRoles = userRoleMapper.findByUserId(userId);
        //所有的角色
        List<RolePo> allRoles = roleMapper.findAll();
        //过滤掉用户已有的角色
        List<RolePo> collect = allRoles.stream().filter(role -> !userRoles.contains(role)).collect(Collectors.toList());
        List<TreeNode> treeNodes = new ArrayList<>();
        //定义一个父节点，用户
        TreeNode father = new TreeNode();
        father.setChkDisabled(Boolean.TRUE);
        father.setNodeId(userId);
        father.setName(po.getUsername());
        father.setParent(Boolean.TRUE);
        father.setOpen(Boolean.TRUE);
        father.setHidden(Boolean.FALSE);
        father.setNocheck(Boolean.TRUE);
        //添加所有的子节点
        List<TreeNode> childs = new ArrayList<>();
        userRoles.forEach(role -> childs.add(role2TreeNode(role, Boolean.TRUE)));
        collect.forEach(role -> childs.add(role2TreeNode(role, Boolean.FALSE)));
        father.setChildren(childs);
        treeNodes.add(father);
        return treeNodes;
    }

    /**
     * 删除用户
     * @param userId 用户ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<Long> deleteUser(Long userId) throws IOException {
        if(null == userId) {
            return commonResponse(null, "用户ID不能为空");
        }
        UserPo user = userMapper.findUserById(userId);
        //删除一个用户要删除已有的用户角色关系
        userRoleMapper.deleteByUserId(userId);
        userMapper.delete(userId);
        //如果该用户正在线上，则强制退出该用户
        logoutUser(user);
        return commonResponse(userId, Constant.SUCCESS);
    }


    @Override
    public UserPo findUserById(Long userId) {
        if(null == userId) {
            return null;
        }
        return userMapper.findUserById(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CommonResponse<String> saveUserRoles(List<TreeNode> treeNodes) {
        if(CollectionUtils.isNotEmpty(treeNodes)) {
            //首先获得userId
            TreeNode father = treeNodes.get(0);
            if(null != father) {
                Long userId = father.getNodeId();
                //再获得所有被选中的角色ID
                List<TreeNode> childrens = father.getChildren();
                if(CollectionUtils.isNotEmpty(childrens)) {
                    //获得所有选中的角色并且对生成的用户角色关系收集为集合
                    List<UserRolePo> collect = childrens.stream().filter(TreeNode::getChecked)
                            .map(children -> new UserRolePo(idService.getId(), userId, children.getNodeId())).collect(Collectors.toList());

                    //首先删除该用户之前所拥有的用户角色关系
                    userRoleMapper.deleteByUserId(userId);
                    //然后保存新的用户角色关系
                    if(CollectionUtils.isNotEmpty(collect)) {
                        userRoleMapper.saveAll(collect);
                    }
                    //成功
                    WebSecurityConstant.USER_PERMISSION_IF_MODIFIED_MAP.put(userId, Boolean.TRUE);
                    return commonResponse("success", Constant.SUCCESS);
                }
            }
        }
        return commonResponse("error", Constant.PARAM_EXCEPTION);
    }

    /**
     * 重写loadUserByUsername方法获得userDetail类型用户
     * @param username 用户帐号
     * @return userDetail类型用户
     * @throws UsernameNotFoundException 没有找到该用户异常
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username:{}", username);
        UserPo user = userMapper.findByUsername(username);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException(USERNAME_NOT_EXIST);
        }
        //初始化用户权限集合
        initAuthorities(user, null, null);
        return user;
    }

    @Override
    public void initAuthorities(UserPo user, SecurityContext securityContext, Authentication authentication) {
        //初始化用户权限集合
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //获得该用户的所有角色
        List<RolePo> allRole = userRoleMapper.findByUserId(user.getUserId());
        if(CollectionUtils.isNotEmpty(allRole)) {
            //再获得每个角色所拥有的权限，一个权限对应一个资源
            allRole.forEach(role -> {
                List<PermissionPo> allPermission = rolePermissionMapper.findByRoleId(role.getRoleId());
                if(CollectionUtils.isNotEmpty(allPermission)) {
                    allPermission.forEach(permission -> {
                        MenuPo menu = menuMapper.findByPermissionId(permission.getPermissionId());
                        if(!Objects.isNull(menu) && StringUtils.isNotBlank(menu.getUrl()) && menu.getIsEnable().equals(1)) {
                            authorities.add(new SimpleGrantedAuthority(String.valueOf(menu.getMenuId())));
                        }
                    });
                }
            });
        }
        user.setAuthorities(authorities);
        if(!Objects.isNull(securityContext) && !Objects.isNull(authentication)) {
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);
            result.setDetails(authentication.getDetails());
            securityContext.setAuthentication(result);
        }
        WebSecurityConstant.USER_PERMISSION_IF_MODIFIED_MAP.put(user.getUserId(), Boolean.FALSE);
    }

    @Override
    public List<MenuPo> allUrls() {
        List<MenuPo> all = menuMapper.findAll();
        return CollectionUtils.isNotEmpty(all) ?
                all : Collections.EMPTY_LIST;
    }

    private TreeNode role2TreeNode(RolePo role, Boolean checked) {
        TreeNode child = new TreeNode();
        child.setNodeId(role.getRoleId());
        child.setName(role.getName());
        child.setChecked(checked);
        child.setChkDisabled(Boolean.FALSE);
        child.setHalfCheck(Boolean.FALSE);
        child.setParent(Boolean.FALSE);
        child.setOpen(Boolean.TRUE);
        child.setHidden(Boolean.FALSE);
        child.setNocheck(Boolean.FALSE);
        return child;
    }

    private void logoutUser(UserPo user) throws IOException {
        if(!Objects.isNull(user)) {
            WebSocketService socketService = WebSecurityConstant.USER_WEB_SOCKET_SESSION_MAP.get(user.getUsername());
            if(!Objects.isNull(socketService) && socketService.currentSession.isOpen()) {
                socketService.currentSession.getBasicRemote().sendText("logout");
            }
        }
    }
}
