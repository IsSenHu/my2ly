package com.husen.service;

import com.husen.dao.po.RolePo;
import com.husen.base.CommonResponse;
import com.husen.dao.vo.DatatablesView;
import com.husen.dao.vo.TreeNode;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/7/4 9:39.
 */
public interface RoleService {
    CommonResponse<RolePo> save(RolePo rolePo);

    CommonResponse<String> saveRolePermission(List<TreeNode> treeNodes, HttpSession session);

    DatatablesView<RolePo> pageRole(Map<String,String[]> map, RolePo rolePo);

    CommonResponse<Long> deleteRoleById(Long roleId);

    List<TreeNode> showAboutRolePermission(Long roleId);
}
