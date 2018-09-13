var app = new Vue({
    el : '#app',
    data : {
        emailId : '',
        to : '',
        subject : '',
        text : '',
        from : '',
        time : '',
        attachmentName : '',
        attachmentSize : '',
        attachmentUrl : '',
        forward : '',
        fileName : ''
    },
    methods : {
        download : function () {
            var $eleForm = $("<form method='get'></form>");
            $eleForm.attr("action", this.attachmentUrl);
            $eleForm.attr("style", "display:none");
            $eleForm.attr("target", "_blank");
            $(document.body).append($eleForm);
            //提交表单实现下载
            $eleForm.submit();
        }
    },
    mounted : function () {
        $("#app").css("display", "block");
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
        var emailId = email.emailId;
        if(emailId) {
            app.emailId = emailId;
        }
        var from = email.from;
        if(from) {
            app.from = from;
        }
        var attachment = email.attachment;
        if(attachment) {
            app.attachmentUrl = 'http://192.168.162.128/' + attachment[0].split(":")[0];
            app.attachmentName = attachment[0].split(":")[1];
            app.attachmentSize = attachment[0].split(":")[2] / 1024 / 1024 + 'MB';
        }
    }
});