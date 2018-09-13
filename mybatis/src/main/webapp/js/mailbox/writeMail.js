/**
 * 邮件构造对象函数
 * */
function Email(emailId, to, subject, text, attachment, forward) {
    this.emailId = emailId;
    this.to = to === '' ? null : to.split(',');
    this.subject = subject;
    this.text = text;
    this.attachment = attachment === '' ? null : attachment.split('{{file}}');
    this.forward = forward === '' ? null : forward.split(',');
}

var app = new Vue({
    el : '#app',
    data : {
        emailId : '',
        to : '',
        subject : '',
        text : '',
        attachment : '',
        forward : '',
        fileName : ''
    },
    methods : {
        draft : function () {
            var email = new Email(this.emailId, this.to, this.subject, this.text, this.attachment, this.forward);
            postAndGetData('/operateEmail?flag=draft', email, function (resp) {
                if(resp.code === 200) {
                    showMsgRedirectUrl('已保存到草稿箱', '/draftBox');
                }else {
                    showMsgNoReload("保存到草稿箱失败");
                }
            });
        },
        sendEmail : function () {
            var email = new Email(this.emailId, this.to, this.subject, this.text, this.attachment, this.forward);
            postAndGetData('/operateEmail?flag=send', email, function (resp) {
                if(resp.code === 200) {
                    showMsgRedirectUrl('邮件发送成功', '/sendBox');
                }else {
                    showMsgNoReload("邮件发送失败，请与管理员联系");
                }
            });
        }
    }
});

$(function () {
    if(email) {
        var to = email.to;
        if(to) {
            app.to = to.join(',');
        }
        var subject = email.subject;
        if(subject) {
            app.subject = subject;
        }
        var text = email.text;
        if(text) {
            app.text = text;
        }
        var forward = email.forward;
        if(forward) {
            app.forward = forward.join(',');
        }
        var attachment = email.attachment;
        if(attachment) {
            app.attachment = attachment[0].split(":")[0];
            app.fileName = attachment[0].split(":")[1] ? attachment[0].split(":")[1] : '';
        }
        var emailId = email.emailId;
        if(emailId) {
            app.emailId = emailId;
        }
    }
});

/**
 * 异步提交附件
 * */
function ajaxAttachment() {
    $("#attachmentForm").ajaxSubmit({
        url : '/ajaxAttachment',
        type : 'post',
        success : function (resp) {
            if(resp.code === 200) {
                var fullPath = resp.data;
                app.attachment = fullPath;
                var split = fullPath.split(':');
                if(split.length > 1) {
                    app.fileName = resp.data.split(':')[1];
                }
            }else {
                if(resp.data !== '附件为空') {
                    showMsgNoReload(resp.data);
                }
                app.attachment = '';
                app.fileName = '';
            }
        }
    });
}