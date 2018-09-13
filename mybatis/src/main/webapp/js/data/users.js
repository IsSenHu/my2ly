var table;
$(function () {
    var setting = {
        "paging": true,
        // "lengthChange": false,可以改变每页显示的数量
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "language" : Zh_Ch,
        "scrollY" : "100%",
        "scrollX" : true,
        "scrollCollapse" : true,
        "processing": true,
        "serverSide": true,
        "ajax" : {
            "type" : "post",
            "url" : "/pageUser"
        },
        "fnServerParams" : function (aoData) {
            aoData.userId = $("#userId").val();
            aoData.realName = $("#find_realName").val();
            aoData.email = $("#find_email").val();
            aoData.username = $("#find_username").val();
        },
        "columns" : [
            {"data" : "userId"},
            {"data" : "username"},
            {"data" : "realName"},
            {"data" : "email"},
            {"data" : "phone"},
            {"data" : "qq"},
            {"data" : "weixin"},
            {"data" : "gender",
                "render" : function (data) {
                    if(data === 0){
                        return '男';
                    }else {
                        return "女";
                    }
                }
            },
            {"data" : "birthday",
                "render" : function (data) {
                    return data.year + '年' + data.monthValue + '月' + data.dayOfMonth + '日';
                }
            },
            {"orderable" : false,
                "render" :  function (data, type, row) {
                    var userId = row.userId;
                    var buttons = '';
                    current_page_row_buttons.forEach(function (value) {
                        buttons = buttons + value.replace(/{{userId}}/g, userId) + '&nbsp;';
                    });
                    return buttons;
                }
            }
        ]
    };

    $("#password2").focus(resetInput);

    $("#birthday").inputmask('yyyy-mm-dd', { 'placeholder': 'yyyy-mm-dd' });

    table = $('#example1').DataTable(setting);

    $("#search").click(function () {
        table.draw();
    });

    $("#reset").click(function () {
        $("#userId").val('');
        $("#find_username").val('');
        $("#find_realName").val('');
        $("#find_email").val('');
        table.draw();
    });

    $("#add").click(function () {
        $("#addCard").css("display", "block");
    });

    /**
     * 保存用户
     * */
    $(".saveUser").click(function () {
        var password = getRealValue($("#password"));
        var password2 = getRealValue($("#password2"));
        if(password !== password2){
            showError("#password2", "两次密码输入不一致");
            return;
        }
        var username = getRealValue($("#username"));
        var realName = getRealValue($("#realName"));
        var email = getRealValue($("#email"));
        var gender = getRealValue($("#gender"));
        var birthday = getRealValue($("#birthday"));
        var phone = getRealValue($("#phone"));
        var qq = getRealValue($("#qq"));
        var weixin = getRealValue($("#weixin"));
        var data = {
            username : username,
            password : password,
            realName : realName,
            email : email,
            gender : gender,
            birthday : birthday,
            phone : phone,
            qq : qq,
            weixin : weixin
        };
        postAndGetData('/saveUser', data, function(resp) {
            switch (resp.code){
                case 200 : {
                    showMsg("添加成功");
                    $("#dialog").dialog("close");
                    table.draw();
                    break;
                }
                case 402 : {
                    resp.data.forEach(function (obj) {
                        console.log(obj);
                        showError('#' + obj.field, obj.defaultMessage);
                    });
                    break;
                }
                case 401 : {
                    showError('#username', "该用户名已存在");
                    break;
                }
            }
        });
    });

    /**
     * 取消添加用户
     * */
    $(".cancelAdd").click(function () {
        $("#addCard").css("display", "none");
        $("#username").val("");
        $("#realName").val("");
        $("#password").val("");
        $("#password2").val("");
        $("#qq").val("");
        $("#phone").val("");
        $("#email").val("");
        $("#weixin").val("");
        $("#birthday").val("");
    });

    /**
     * 重置添加用户表格
     * */
    $(".resetAdd").click(function () {
        $("#username").val("");
        $("#realName").val("");
        $("#password").val("");
        $("#password2").val("");
        $("#qq").val("");
        $("#phone").val("");
        $("#email").val("");
        $("#weixin").val("");
        $("#birthday").val("");
    });
});

function deleteRow(obj) {
    var userId = $(obj).val();
    $.ajax({
        type : 'post',
        url : '/deleteUser?userId=' + userId,
        success : function (resp) {
            if(resp.code === 200) {
                showMsg("删除成功");
                table.draw();
            }
        }
    });
}

//树设置
var setting = {
    check : {
        enable: true
    },
    view: {
        showLine: true, //显示辅助线
        dblClickExpand: true
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: 0
        }
    },
    callback : {
        onClick : function (event, treeId, treeNode) {
            updateNodeId = treeNode.nodeId;
            var ifAdd = true;
            nodes.forEach(function (value) {
                if(value.nodeId === treeNode.nodeId) {
                    ifAdd = false;
                    return;
                }
            });
            if(ifAdd) {
                nodes[nodes.length] = treeNode;
            }
        }
    }
};

/**
 * 角色管理
 * */
function manageRole(obj) {
    var userId = $(obj).val();
    postAndGetData('/showAboutUserRoles?userId=' + userId, null, function (data) {
        var div =
            '<tr>' +
                '<td colspan="10">' +
                    '<div class="card">' +
                        '<div class="card-header" style="text-align: left; background-color: #FFC107; color: white;">用户角色树</div>' +
                        '<div class="card-body">' +
                            '<ul id="tree' + userId + '" class="ztree"></ul>' +
                        '</div>' +
                        '<div class="card-footer" style="text-align: left;">' +
                            '<button class="btn btn-primary" id="saveRole' + userId +'" value="' + userId +'">保存</button> ' +
                            '<button class="btn btn-dark" value="' + userId + '" onclick="closeDetail(this)">收起</button>' +
                        '</div>' +
                    '</div>' +
                '</td>' +
            '</tr>';
        showDetail(obj, div);
        //加载菜单树
        var zTreeObj = $.fn.zTree.init($("#tree" + userId), setting, data);
        $(obj).prop("disabled", true);
        $("#saveRole" + userId).click(function () {
            saveTreeNodes(zTreeObj);
        })
    });
}

/**
 * 保存角色树节点
 * */
function saveTreeNodes(obj) {
    var nodes = obj.getNodes();
    postAndGetData('/saveUserRoles', nodes, function (data) {
       if(data.code === 200) {
           showMsgNoReload("保存成功");
       }else {
           showMsgNoReload("保存失败");
       }
    });
}

function showDetail(obj, node) {
    var $button;
    if(obj === undefined || obj == null){
        $button = this;
    }else {
        $button = $(obj);
    }
    $button.parent().parent().after(node);
}


function closeDetail(obj) {
    var $button;
    if(obj === undefined || obj == null){
        $button = this;
    }else {
        $button = $(obj);
    }
    $button.parent().parent().parent().parent().remove();
    $('#btn' + $(obj).val()).prop("disabled", false);
}