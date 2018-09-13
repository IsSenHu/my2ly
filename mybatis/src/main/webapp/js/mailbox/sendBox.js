$(function () {
    var offset = 0;
    var end = 9;
    var url = '/getEmailData?flag=send&offset=' + offset + '&end=' + end;
    postAndGetData(url, null, function (data) {
        var app = new Vue({
            el : '#app',
            data : data
        });
        $("#app").css("display", "block");
    });
});
