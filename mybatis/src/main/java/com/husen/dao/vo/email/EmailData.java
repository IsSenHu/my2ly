package com.husen.dao.vo.email;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuSen on 2018/9/3 11:39.
 */
public class EmailData implements Serializable {
    private Long total;
    private Long offset;
    private Long end;
    private Long draftNumber;
    private List<EmailViewVo> emails;

    public Long getDraftNumber() {
        return draftNumber;
    }

    public void setDraftNumber(Long draftNumber) {
        this.draftNumber = draftNumber;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public List<EmailViewVo> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailViewVo> emails) {
        this.emails = emails;
    }
}
