package com.husen.service;

import com.husen.base.CommonResponse;
import com.husen.dao.po.MenuPo;
import com.husen.dao.po.UserPo;
import com.husen.dao.po.UserRolePo;
import com.husen.dao.vo.TreeNode;
import com.husen.dao.vo.UserVo;
import com.husen.dao.vo.DatatablesView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/6/28 12:31.
 */
public interface UserService extends UserDetailsService {
    List<UserPo> findAll();

    CommonResponse<UserPo> save(UserVo userPo) throws ParseException;

    CommonResponse<UserRolePo> saveUserRole(UserRolePo userRolePo);

    DatatablesView<UserPo> pageUser(Map<String,String[]> map, UserPo userPo);

    CommonResponse<Long> deleteUser(Long userId) throws IOException;

    UserPo findUserById(Long userId);

    List<TreeNode> showAboutUserRoles(Long userId);

    CommonResponse<String> saveUserRoles(List<TreeNode> treeNodes);

    List<MenuPo> allUrls();

    void initAuthorities(UserPo user, SecurityContext securityContext, Authentication authentication);
}
