package com.husen.controller;

import com.husen.dao.po.UserPo;
import com.husen.dao.po.UserRolePo;
import com.husen.base.CommonResponse;
import com.husen.dao.vo.DatatablesView;
import com.husen.dao.vo.TreeNode;
import com.husen.dao.vo.UserVo;
import com.husen.exception.ParamException;
import com.husen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/7/3 16:30.
 */
@RestController
public class UserController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 到登录页面
     * @return 登录页面渲染视图
     */
    @GetMapping("/login")
    private ModelAndView login() {
        return new ModelAndView("/login");
    }

    /**
     * 保存用户
     * @param userVo 用户
     * @param result 校验结果
     * @return 操作结果
     * @throws ParamException 参数异常
     */
    @PostMapping("/saveUser")
    private @ResponseBody CommonResponse<UserPo> save(@Valid @RequestBody UserVo userVo, BindingResult result) throws ParamException, ParseException {
        log.info("接收到的用户数据:[{}]", userVo);
        if(result.hasErrors()){
            throw new ParamException("参数错误", result.getFieldErrors());
        }
        return userService.save(userVo);
    }

    /**
     * 保存用户角色关系
     * @param userRolePo 用户角色关系
     * @param result 校验结果
     * @return 操作结果
     */
    @PostMapping("/saveUserRole")
    private CommonResponse<UserRolePo> saveUserRole(@Valid @RequestBody UserRolePo userRolePo, BindingResult result) throws ParamException {
        log.info("接收到的用户角色关系:[{}]", userRolePo);
        if(result.hasErrors()){
            throw new ParamException("参数错误", result.getFieldErrors());
        }
        return userService.saveUserRole(userRolePo);
    }

    /**
     * 根据ID删除用户
     * @param userId 用户ID
     * @return 操作结果
     */
    @PostMapping("/deleteUser")
    private CommonResponse<Long> deleteUser(Long userId) throws IOException {
        return userService.deleteUser(userId);
    }

    @PostMapping("/pageUser")
    private DatatablesView<UserPo> pageUser(HttpServletRequest request, UserPo userPo){
        Map<String, String[]> map =  request.getParameterMap();
        return userService.pageUser(map, userPo);
    }

    @GetMapping("/users")
    private ModelAndView users(){
        return new ModelAndView("pages/tables/users");
    }

    @PostMapping("/findUserById")
    private UserPo findUserById(Long userId) {
        return userService.findUserById(userId);
    }

    @PostMapping("/showAboutUserRoles")
    private List<TreeNode> showAboutUserRoles(Long userId) {
        return userService.showAboutUserRoles(userId);
    }

    @PostMapping("/saveUserRoles")
    private CommonResponse<String> saveUserRoles(@RequestBody List<TreeNode> treeNodes) {
        return userService.saveUserRoles(treeNodes);
    }
}
