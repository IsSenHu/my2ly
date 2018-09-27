package com.husen.jian.dao.repository;

import com.husen.jian.dao.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by HuSen on 2018/9/21 15:45.
 */
@Repository
public interface UserRepository extends JpaRepository<UserPo, Long> {

    UserPo findByUsername(String username);

    UserPo findByToken(String token);
}
