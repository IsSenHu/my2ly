package com.husen.service;

import com.husen.dao.po.UserPo;
import com.husen.dao.vo.DatatablesView;

import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/6/28 12:31.
 */
public interface UserService {
    List<UserPo> findAll();
    DatatablesView<UserPo> pageUser(Map<String, String[]> map, UserPo userPo);
}
