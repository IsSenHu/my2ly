package com.husen.service.email;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.husen.base.Base;
import com.husen.base.CommonResponse;
import com.husen.dao.po.UserPo;
import com.husen.dao.vo.email.EmailData;
import com.husen.dao.vo.email.EmailViewVo;
import com.husen.service.id.IdService;
import com.husen.utils.DateTimeFormatterUtil;
import com.husen.utils.HttpConnectionUtils;
import com.husen.vo.email.EmailVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by HuSen on 2018/8/31 15:15.
 */
@Service
public class EmailServiceImpl extends Base implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final StringRedisTemplate stringRedisTemplate;
    private final IdService idService;

    private static final String USER_EMAIL = "user_email:";
    private static final String EMAIL = "email:";

    private static final String DRAFT = "draft";
    private static final String SEND = "send";

    private static final String SEND_EMAIL_API = "http://127.0.0.1:23459/email/sendEmail";

    @Autowired
    public EmailServiceImpl(StringRedisTemplate stringRedisTemplate, IdService idService) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.idService = idService;
    }

    /**
     * @param flag 操作标识
     * @param emailVo 邮件Vo
     * @return 操作结果
     */
    @Override
    public CommonResponse<EmailVo> operateEmail(String flag, EmailVo emailVo) throws IOException {
        log.info("处理的邮件内容为:[{}]", emailVo);
        //存放邮件 key为email: + emailId value为JSON序列化字符串
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        //存放用户与邮件的关系 key为userEmail: + userId value为emailId，secore为时间戳
        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
        LocalDateTime now = LocalDateTime.now();
        //进入草稿箱
        if(DRAFT.equals(flag)) {
            if(canDraft(emailVo)) {
                UserPo user = getUser();
                if(null != user) {
                    //判断是修改草稿还是新增草稿
                    String emailId = emailVo.getEmailId();
                    //设置Id
                    emailVo.setEmailId(StringUtils.isNotBlank(emailId) ? emailId : String.valueOf(idService.getId()));
                    //设置邮件时间
                    emailVo.setTime(DateTimeFormatterUtil.formatter(now, DateTimeFormatterUtil.ONE_MATTER));
                    //设置邮件状态
                    emailVo.setState(EmailVo.DRAFT);
                    //设置发件人
                    emailVo.setFrom(user.getEmail());
                    stringRedisTemplate.multi();
                    //保存邮件
                    hash.putAll(EMAIL + emailVo.getEmailId(), getMap(emailVo));
                    //保存用户邮件信息
                    zSet.add(DRAFT + "_" + USER_EMAIL + user.getUserId(), emailVo.getEmailId(), System.currentTimeMillis());
                    stringRedisTemplate.exec();
                }
            }
        }
        //发送邮件 并进入发件箱
        else if(SEND.equals(flag)) {
            UserPo user = getUser();
            if(null != user) {
                //设置发件人
                emailVo.setFrom(user.getEmail());
                if(canSend(emailVo)) {
                    long id = idService.getId();
                    //设置ID
                    emailVo.setEmailId(String.valueOf(id));
                    //设置邮件时间
                    emailVo.setTime(DateTimeFormatterUtil.formatter(now, DateTimeFormatterUtil.ONE_MATTER));
                    //调用发邮件接口发送邮件
                    String resp = HttpConnectionUtils.doPost(SEND_EMAIL_API, JSON.toJSONString(emailVo), true);
                    Map map = JSONObject.parseObject(resp, Map.class);
                    //发送成功进入草稿箱
                    if(null != map && "200".equals(map.get("code"))) {
                        //设置发送成功
                        emailVo.setState(EmailVo.SEND_SUCCESS);
                        stringRedisTemplate.multi();
                        //保存邮件
                        hash.putAll(EMAIL + emailVo.getEmailId(), getMap(emailVo));
                        //保存用户邮件信息
                        zSet.add(SEND + "_" + USER_EMAIL + user.getUserId(), emailVo.getEmailId(), System.currentTimeMillis());
                        stringRedisTemplate.exec();
                    }
                    //发送失败进入草稿箱
                    else {
                        //设置邮件状态
                        emailVo.setState(EmailVo.SEND_FAIL);
                        //设置发件人
                        emailVo.setFrom(user.getEmail());
                        stringRedisTemplate.multi();
                        //保存邮件
                        hash.putAll(EMAIL + emailVo.getEmailId(), getMap(emailVo));
                        //保存用户邮件信息
                        zSet.add(DRAFT + "_" + USER_EMAIL + user.getUserId(), emailVo.getEmailId(), System.currentTimeMillis());
                        stringRedisTemplate.exec();
                    }
                }
            }
        }
        return commonResponse(emailVo, Constant.SUCCESS);
    }

    /**
     * @param flag 邮件标识
     * @return 邮件数据
     */
    @Override
    public EmailData getEmailData(String flag, Long offset, Long end) {
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
        UserPo user = getUser();
        EmailData data = new EmailData();
        if(null != user) {
            Set<String> emailIdSet = zSet.range(flag + "_" + USER_EMAIL + user.getUserId(), offset, end);
            List<EmailViewVo> emails = new ArrayList<>();
            if(null != emailIdSet) {
                emailIdSet.forEach(id -> {
                    Map<Object, Object> entries = hash.entries(EMAIL + id);
                    EmailViewVo emailViewVo = getEmail(entries);
                    switch (flag) {
                        case DRAFT: {
                            emailViewVo.setShowDraft("/writeEmail?emailId=" + emailViewVo.getId());
                            break;
                        }
                        case SEND: {
                            emailViewVo.setShowDraft("/readEmail?emailId=" + emailViewVo.getId());
                            break;
                        }
                    }
                    emails.add(emailViewVo);
                });
            }
            data.setEmails(emails);
            data.setOffset(offset);
            Long count = zSet.count(flag + "_" + USER_EMAIL + user.getUserId(), Long.MIN_VALUE, Long.MAX_VALUE);
            data.setEnd(end <= count ? end : count);
            data.setTotal(count);
            data.setDraftNumber(count);
        }
        return data;
    }

    /**
     * 根据ID查询邮件
     * @param emailId ID
     * @return 邮件内容
     */
    @Override
    public EmailVo findById(String emailId) {
        if(StringUtils.isBlank(emailId)) {
            return null;
        }
        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
        Map<Object, Object> entries = hash.entries(EMAIL + emailId);
        return getEmailVo(entries);
    }

    /**
     * 是否可以保存到草稿箱
     * @param emailVo 邮件内容
     * @return 是否可以保存到草稿箱
     */
    private boolean canDraft(com.husen.vo.email.EmailVo emailVo) {
        //只要该邮件的内容有不为空的就可以保存
        if(null == emailVo) {
            return false;
        }
        boolean can = false;
        if(CollectionUtils.isNotEmpty(emailVo.getTo())
                || CollectionUtils.isNotEmpty(emailVo.getForward())
                || CollectionUtils.isNotEmpty(emailVo.getAttachment())
                || StringUtils.isNotBlank(emailVo.getSubject())
                || StringUtils.isNotBlank(emailVo.getText())) {
            can = true;
        }
        return can;
    }

    /**
     * 是否可以发送邮件
     * @param emailVo 邮件内容
     * @return 是否可以发送邮件
     */
    private boolean canSend(EmailVo emailVo) {
        boolean can = true;
        if(null == emailVo) {
            can = false;
        }else {
            //邮件必有属性不能为空
            if(StringUtils.isBlank(emailVo.getFrom())
                    || CollectionUtils.isEmpty(emailVo.getTo())
                    || StringUtils.isBlank(emailVo.getSubject())
                    || StringUtils.isBlank(emailVo.getText())) {
                    can = false;
            }
        }
        return can;
    }

    private Map<String, String> getMap(EmailVo vo) {
        Map<String, String> map = new HashMap<>(10);
        if(StringUtils.isNotBlank(vo.getEmailId())) {
            map.put("emailId", vo.getEmailId());
        }
        if(StringUtils.isNotBlank(vo.getSubject())) {
            map.put("subject", vo.getSubject());
        }
        if(StringUtils.isNotBlank(vo.getText())) {
            map.put("text", vo.getText());
        }
        if(StringUtils.isNotBlank(vo.getTime())) {
            map.put("time", vo.getTime());
        }
        if(StringUtils.isNotBlank(vo.getFrom())) {
            map.put("from", vo.getFrom());
        }
        if(null != vo.getState()) {
            map.put("state", String.valueOf(vo.getState()));
        }
        if(null != vo.getLab()) {
            map.put("lab", String.valueOf(vo.getLab()));
        }
        if(CollectionUtils.isNotEmpty(vo.getAttachment())) {
            map.put("attachment", JSONArray.toJSONString(vo.getAttachment()));
        }
        if(CollectionUtils.isNotEmpty(vo.getTo())) {
            map.put("to", JSONArray.toJSONString(vo.getTo()));
        }
        if(CollectionUtils.isNotEmpty(vo.getForward())) {
            map.put("forward", JSONArray.toJSONString(vo.getForward()));
        }
        return map;
    }

    private EmailViewVo getEmail(Map<Object, Object> map) {
        EmailViewVo vo = new EmailViewVo();
        if(MapUtils.isNotEmpty(map)) {
            vo.setId((String) map.get("emailId"));
            vo.setMailboxStar(StringUtils.isNotBlank((String)map.get("lab")) ? "fa-star" : "");
            vo.setMailboxAttachment(StringUtils.isNotBlank((String)map.get("attachment")));
            vo.setMailboxDate((String) map.get("time"));
            vo.setMailboxName((String) map.get("subject"));
            vo.setMailboxSubject((String) map.get("subject"));
            String text = (String)map.get("text");
            vo.setMailboxContent(StringUtils.isNotBlank(text) ? (text).substring(0, 16 > text.length() ? text.length() - 1 : 16) + "......" : "");
        }
        return vo;
    }

    private EmailVo getEmailVo(Map<Object, Object> map) {
        EmailVo vo = new EmailVo();
        if(MapUtils.isNotEmpty(map)) {
            vo.setEmailId((String) map.get("emailId"));
            Object state = map.get("state");
            if(state != null) {
                vo.setState(Integer.valueOf((String) state));
            }
            vo.setFrom((String) map.get("from"));
            vo.setTime((String) map.get("time"));
            vo.setSubject((String) map.get("subject"));
            vo.setText((String) map.get("text"));
            Object lab = map.get("lab");
            if(null != lab) {
                vo.setLab(Integer.valueOf((String) lab));
            }
            vo.setAttachment(JSONArray.parseArray((String) map.get("attachment"), String.class));
            vo.setForward(JSONArray.parseArray((String) map.get("forward"), String.class));
            vo.setTo(JSONArray.parseArray((String) map.get("to"), String.class));
        }
        return vo;
    }

    public static void main(String[] args) {
        JSONArray.parseArray(null, String.class);
    }
}
