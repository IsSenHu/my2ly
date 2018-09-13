package com.husen.controller;

import com.husen.dao.po.RolePo;
import com.husen.base.CommonResponse;
import com.husen.dao.vo.DatatablesView;
import com.husen.dao.vo.TreeNode;
import com.husen.exception.ParamException;
import com.husen.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/7/4 9:38.
 */
@RestController
public class RoleController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    private ModelAndView roles() {
        return new ModelAndView("pages/tables/roles");
    }

    @PostMapping("/pageRole")
    private DatatablesView<RolePo> pageRole(HttpServletRequest request, RolePo rolePo) {
        Map<String, String[]> map = request.getParameterMap();
        return roleService.pageRole(map, rolePo);
    }

    /**
     * 保存角色
     * @param rolePo 角色
     * @param result 校验结果
     * @return 操作结果
     * @throws ParamException 参数异常
     */
    @PostMapping("/saveRole")
    private CommonResponse<RolePo> save(@Valid @RequestBody RolePo rolePo, BindingResult result) throws ParamException {
        log.info("接收到的参数为:[{}]", rolePo);
        if(result.hasErrors()){
            throw new ParamException("参数错误", result.getFieldErrors());
        }
        return roleService.save(rolePo);
    }

    @PostMapping("/deleteRoleById")
    private CommonResponse<Long> deleteRoleById(Long roleId) {
        return roleService.deleteRoleById(roleId);
    }

    @PostMapping("/showAboutRolePermission")
    private List<TreeNode> showAboutRolePermission(Long roleId) {
        return roleService.showAboutRolePermission(roleId);
    }

    @PostMapping("/saveRolePermission")
    private CommonResponse<String> saveRolePermission(@RequestBody List<TreeNode> treeNodes, HttpServletRequest request) {
        return roleService.saveRolePermission(treeNodes, request.getSession());
    }
}
