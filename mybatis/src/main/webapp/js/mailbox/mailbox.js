new Vue({
    el : '#app',
    data : {
        total : 2,
        offset : 1,
        end : 2,
        emails : [
            {
                id : 1,
                mailboxStar : 'fa-star',
                mailboxName : '邮件名字',
                mailboxSubject : '主题',
                mailboxContent : '内容......',
                mailboxAttachment : true,
                mailboxDate : '时间'
            },
            {
                id : 2,
                mailboxStar : 'fa-star-o',
                mailboxName : '邮件名字',
                mailboxSubject : '主题',
                mailboxContent : '内容......',
                mailboxAttachment : false,
                mailboxDate : '时间'
            }
        ]
    }
});