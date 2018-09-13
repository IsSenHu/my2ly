package com.husen.vo.email;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuSen on 2018/8/31 10:55.
 */
public class EmailVo implements Serializable {
    /**发送成功*/
    public static final Integer SEND_SUCCESS = 1;
    /**发送失败 进入草稿箱*/
    public static final Integer SEND_FAIL = 2;
    /**草稿*/
    public static final Integer DRAFT = 3;
    /**垃圾邮件*/
    public static final Integer JUNK = 4;
    /**回收*/
    public static final Integer RECYCLE = 5;

    /**重要邮件*/
    public static final Integer LAB_IMPORTANT = 11;
    /**宣传邮件*/
    public static final Integer LAB_PROPAGANDA = 12;
    /**社交邮件*/
    public static final Integer LAB_SOCIAL = 13;


    /**ID*/
    private String emailId;
    /**发件人*/
    private String from;
    /**收件人集合*/
    private List<String> to;
    /**转发人集合*/
    private List<String> forward;
    /**发送时间*/
    private String time;
    /**标题*/
    private String subject;
    /**内容*/
    private String text;
    /**附件地址*/
    private List<String> attachment;
    /**状态*/
    private Integer state;
    /**标签*/
    private Integer lab;

    public Integer getLab() {
        return lab;
    }

    public void setLab(Integer lab) {
        this.lab = lab;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getForward() {
        return forward;
    }

    public void setForward(List<String> forward) {
        this.forward = forward;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<String> attachment) {
        this.attachment = attachment;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EmailVo{" +
                "emailId='" + emailId + '\'' +
                ", from='" + from + '\'' +
                ", to=" + to +
                ", forward=" + forward +
                ", time='" + time + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", attachment=" + attachment +
                ", state=" + state +
                ", lab=" + lab +
                '}';
    }
}
