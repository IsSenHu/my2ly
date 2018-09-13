package com.husen.controller;

import com.husen.dao.po.MenuPo;
import com.husen.dao.po.PermissionPo;
import com.husen.base.CommonResponse;
import com.husen.dao.vo.DatatablesView;
import com.husen.dao.vo.PermissionMenuVo;
import com.husen.exception.ParamException;
import com.husen.service.PermissionService;
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
 * Created by HuSen on 2018/7/4 10:34.
 */
@RestController
public class PermissionController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/permissions")
    private ModelAndView permissions() {
        return new ModelAndView("pages/tables/permissions");
    }

    /**
     * 保存权限
     * @param permissionPo 权限
     * @param result 校验结果
     * @return 操作结果
     * @throws ParamException 参数异常
     */
    @PostMapping("/savePermission")
    private CommonResponse<PermissionPo> savePermission(@Valid @RequestBody PermissionPo permissionPo, BindingResult result) throws ParamException {
        log.info("接收到的权限数据:[{}]", permissionPo);
        if(result.hasErrors()){
            throw new ParamException("参数错误", result.getFieldErrors());
        }
        return permissionService.savePermission(permissionPo);
    }

    @PostMapping("/pagePermission")
    private DatatablesView<PermissionPo> pagePermission(HttpServletRequest request, PermissionPo permissionPo) {
        Map<String, String[]> map = request.getParameterMap();
        return permissionService.pagePermission(map, permissionPo);
    }

    @PostMapping("/deletePermissionById")
    private CommonResponse<Long> deletePermissionById(Long permissionId) {
        return permissionService.deletePermissionById(permissionId);
    }

    @PostMapping("/showMenusByPermissionId")
    private Map<String, List<MenuPo>> showMenusByPermissionId(Long permissionId) {
        return permissionService.showMenusByPermissionId(permissionId);
    }

    @PostMapping("/savePermissionMenu")
    private CommonResponse<Map<String, List<MenuPo>>> savePermissionMenu(@RequestBody PermissionMenuVo vo) {
        return permissionService.savePermissionMenu(vo);
    }
}
