package com.husen.controller.email;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.husen.base.CommonResponse;
import com.husen.controller.BaseController;
import com.husen.dao.vo.email.EmailData;
import com.husen.service.email.EmailService;
import com.husen.vo.email.EmailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;

/**
 * Created by HuSen on 2018/8/24 17:18.
 */
@RestController
public class EmailController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    private final EmailService emailService;
    private final FastFileStorageClient fastFileStorageClient;
    @Autowired
    public EmailController(EmailService emailService, FastFileStorageClient fastFileStorageClient) {
        this.emailService = emailService;
        this.fastFileStorageClient = fastFileStorageClient;
    }

    @GetMapping("/email")
    private ModelAndView email() {
        return new ModelAndView("pages/mailbox/mail-box");
    }

    @GetMapping("/writeEmail")
    private ModelAndView writeEmail(String emailId) {
        return new ModelAndView("pages/mailbox/write-email")
                .addObject("email", emailService.findById(emailId));
    }

    @GetMapping("/readEmail")
    private ModelAndView readEmail(String emailId) {
        return new ModelAndView("pages/mailbox/read-email")
                .addObject("email", emailService.findById(emailId));
    }

    @GetMapping("/draftBox")
    private ModelAndView draftBox() {
        return new ModelAndView("pages/mailbox/draft-box");
    }

    @GetMapping("/sendBox")
    private ModelAndView sendBox() {
        return new ModelAndView("pages/mailbox/send-box");
    }

    /**
     * @param flag 操作标识
     * @param emailVo 邮件Vo
     * @return 操作结果
     */
    @PostMapping("/operateEmail")
    private CommonResponse<EmailVo> operateEmail(String flag, @RequestBody EmailVo emailVo) throws IOException {
        return emailService.operateEmail(flag, emailVo);
    }

    /**
     * 提交附件
     * @param attachment 文件
     * @return 文件路径
     */
    @PostMapping("/ajaxAttachment")
    private CommonResponse<String> ajaxAttachment(MultipartFile attachment) {
        if(attachment != null) {
            if(attachment.getSize() > 1024 * 1024 * 32) {
                return commonResponse("文件不能大于32MB", Constant.PARAM_EXCEPTION);
            }
            String originalFilename = attachment.getOriginalFilename();
            long size = attachment.getSize();
            log.info("上传的文件为:[{}]", originalFilename);
            try {
                String path = fastFileStorageClient.uploadFile(null, attachment.getInputStream(), size, originalFilename.substring(originalFilename.lastIndexOf(".") + 1)).getFullPath();
                return commonResponse(path + ":" + originalFilename + ":" + size, Constant.SUCCESS);
            }catch (Exception e) {
                log.error("上传附件失败，发生异常:[{}]", e.getMessage());
                e.printStackTrace();
                return commonResponse("上传附件失败", Constant.ERROR);
            }
        }
        return commonResponse("附件为空", Constant.PARAM_EXCEPTION);
    }

    /**
     * @param flag 邮件标识
     * @return 邮件数据
     */
    @PostMapping("/getEmailData")
    private EmailData getEmailData(String flag, Long offset, Long end) {
        return emailService.getEmailData(flag, offset, end);
    }
}
