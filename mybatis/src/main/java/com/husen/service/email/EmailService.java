package com.husen.service.email;

import com.husen.base.CommonResponse;
import com.husen.dao.vo.email.EmailData;
import com.husen.vo.email.EmailVo;

import java.io.IOException;

/**
 * Created by HuSen on 2018/8/31 15:15.
 */
public interface EmailService {
    CommonResponse<com.husen.vo.email.EmailVo> operateEmail(String flag, com.husen.vo.email.EmailVo emailVo) throws IOException;

    EmailData getEmailData(String flag, Long offset, Long end);

    EmailVo findById(String emailId);
}
