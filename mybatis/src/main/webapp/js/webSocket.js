$(function () {
    var username = $("#user-name").text();
    console.log(username);
    //初始化一个WebSocket对象
    var ws = new WebSocket("ws://localhost:8080/userLogoutHandler/" + username);
    //建立 web socket 连接成功触发事件
    ws.onopen = function () {
        //使用send方法发送数据
        if(ws && ws.readyState === 1) {
            console.log("连接已建立");
        }
    };

    //接收服务端数据时触发事件
    ws.onmessage = function (ev) {
        var data = ev.data;
        console.log(data);
        if(data === 'logout') {
            location.href = '/logout';
        }
    };

    //断开web socket 时触发事件
    ws.onclose = function () {
        console.log("连接断开");
    };
});