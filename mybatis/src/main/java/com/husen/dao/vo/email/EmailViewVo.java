package com.husen.dao.vo.email;

import java.io.Serializable;

/**
 * Created by HuSen on 2018/9/3 11:37.
 */
public class EmailViewVo implements Serializable {
    private String id;
    private String mailboxStar;
    private String mailboxName;
    private String mailboxSubject;
    private String mailboxContent;
    private boolean mailboxAttachment;
    private String mailboxDate;
    private String showDraft;

    public String getShowDraft() {
        return showDraft;
    }

    public void setShowDraft(String showDraft) {
        this.showDraft = showDraft;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMailboxStar() {
        return mailboxStar;
    }

    public void setMailboxStar(String mailboxStar) {
        this.mailboxStar = mailboxStar;
    }

    public String getMailboxName() {
        return mailboxName;
    }

    public void setMailboxName(String mailboxName) {
        this.mailboxName = mailboxName;
    }

    public String getMailboxSubject() {
        return mailboxSubject;
    }

    public void setMailboxSubject(String mailboxSubject) {
        this.mailboxSubject = mailboxSubject;
    }

    public String getMailboxContent() {
        return mailboxContent;
    }

    public void setMailboxContent(String mailboxContent) {
        this.mailboxContent = mailboxContent;
    }

    public boolean isMailboxAttachment() {
        return mailboxAttachment;
    }

    public void setMailboxAttachment(boolean mailboxAttachment) {
        this.mailboxAttachment = mailboxAttachment;
    }

    public String getMailboxDate() {
        return mailboxDate;
    }

    public void setMailboxDate(String mailboxDate) {
        this.mailboxDate = mailboxDate;
    }
}
