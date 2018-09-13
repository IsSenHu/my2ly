var zTreeObj;
//要修改的节点ID
var updateNodeId = null;
//要删除的节点集合
var node = null;
//当前的菜单等级
var level = null;
//要修改还是添加
var add = true;
// zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
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
            node = !node || treeNode.nodeId !== node.nodeId ? treeNode : null;
            if(node != null && node !== undefined) {
                updateNodeId = treeNode.nodeId;
                if(node.type === 'btn') {
                    $(".addNode").prop("disabled", true);
                }else {
                    $(".addNode").prop("disabled", false);
                }
            }
        }
    }
};

$(function () {
    //加载菜单树
    $.ajax({
        type : 'post',
        url : '/getAllTreeNodes',
        success : function (data) {
            zTreeObj = $.fn.zTree.init($("#tree"), setting, data);
            //保存树结构
            $(".saveTree").click(function () {
                var nodes = zTreeObj.getNodes();
                $.ajax({
                    type : 'post',
                    url : '/saveMenuTree',
                    contentType : 'application/json;charset=UTF-8',
                    data : JSON.stringify(nodes),
                    success : function (resp) {
                        if(resp.code === 200) {
                            showMsg("修改成功");
                        }else {
                            showMsg("修改失败");
                        }
                    }
                });
            });
        }
    });

    //修改菜单或按钮
    $(".updateNode").click(function () {
        add = false;
        if(updateNodeId === null) {
            showMsgNoReload("请先选中一个菜单或按钮");
            return;
        }
        var select = $("#buttonOrMenu");
        select.empty();
        postAndGetData('/getMenuById?menuId=' + updateNodeId, null, function (resp) {
            if(resp != null) {
                if(node.type === 'btn') {
                    $("#addWhat").html("修改按钮");
                    if(node.icon && node.icon.split(";")[0] === 'row') {
                        select.append('<option value="21">页面按钮</option>');
                        select.append('<option value="22" selected>行按钮</option>');
                    }else {
                        select.append('<option value="21" selected>页面按钮</option>');
                        select.append('<option value="22">行按钮</option>');
                    }
                    select.append('<option value="21">页面按钮</option>');
                    select.append('<option value="22">行按钮</option>');
                    csses(resp);
                }else {
                    $("#addWhat").html("修改菜单");
                    select.append('<option value="1">菜单</option>');
                    icons(resp.icons);
                }
                var nameSelect = $("#name");
                var urlSelect = $("#url");
                nameSelect.val(resp.name);
                urlSelect.val(resp.url);
                $("#addNewCard").css("display", "block");
            }
        });
    });

    //删除菜单
    $(".deleteNodes").click(function () {
        if(updateNodeId === null) {
            showMsgNoReload("请先选中一个菜单或按钮");
            return;
        }
        $.ajax({
            type : 'post',
            url : '/deleteMenuById?menuId=' + updateNodeId,
            success : function (resp) {
                if(resp.code === 200) {
                    showMsg("删除成功");
                }
            }
        });
    });

    $(".addNode").click(function () {
        getAndGetData('/getAllIcons', null, function (resp) {
            var select = $("#buttonOrMenu");
            select.empty();
            add = true;
            if(node === null) {
                $("#addNewCard").css("display", "block");
                $("#addWhat").html("添加主菜单");
                select.append('<option value="1">菜单</option>');
                icons(resp);
            }
            //如果三级菜单，就只能添加按钮
            else if(node.level === 2) {
                $("#addNewCard").css("display", "block");
                $("#addWhat").html("添加按钮");
                select.append('<option value="21">页面按钮</option>');
                select.append('<option value="22">行按钮</option>');
                csses(false);
            }
            //如果是按钮，直接return
            else if(node.type === 'btn') {
                return;
            }
            //如果是一、二级菜单并且子节点是按钮则可以添加按钮，子节点是菜单则可以添加菜单，如果没有自己点则两个都可以添加
            else if(node.level === 0 || node.level === 1) {
                $("#addNewCard").css("display", "block");
                var downNodes = node.children;
                if(downNodes === null || downNodes === undefined || downNodes.length === 0) {
                    $("#addWhat").html("添加菜单/按钮");
                    level = (node.level + 1) + 1;
                    select.append('<option value="1">菜单</option>');
                    select.append('<option value="21">页面按钮</option>');
                    select.append('<option value="22">行按钮</option>');
                    icons(resp);
                    select.change(function () {
                       if($(this).val() === '1') {
                           icons(resp);
                       }else {
                            csses(false);
                       }
                    });
                }else {
                    if(downNodes[0].type === 'btn') {
                        $("#addWhat").html("添加按钮");
                        select.append('<option value="21">页面按钮</option>');
                        select.append('<option value="22">行按钮</option>');
                        //class样式
                        csses(false);
                    }else {
                        level = (node.level + 1) + 1;
                        $("#addWhat").html("添加菜单");
                        select.append('<option value="1">菜单</option>');
                        //图标
                        icons(resp);
                    }
                }
            }
        });
    });

    //取消添加
    $(".cancelAdd").click(function () {
        $("#addNewCard").css("display", "none");
        $("#name").val("");
        $("#url").val("");
        $("#icon").val("");
    });
    
    //重置输入框
    $(".resetAdd").click(function () {
        $("#name").val("");
        $("#url").val("");
        $("#icon").val("");
    });

    //保存菜单/按钮
    $(".saveAdd").click(function () {
        var menuId = updateNodeId;
        var fatherMenuId = node ? node.nodeId : null;
        var name = getRealValue($("#name"));
        var url = getRealValue($("#url"));
        var icon = getRealValue($("#icon"));
        var af = icon.split(';');
        if(af.length > 1) {
            icon = af[1];
        }
        var buttonOrMenu = $("#buttonOrMenu").val();
        if(buttonOrMenu === 21 || buttonOrMenu === '21') {
            buttonOrMenu = 2;
            icon = 'global;' + icon;
            level = null;
        }
        if(buttonOrMenu === 22 || buttonOrMenu === '22') {
            buttonOrMenu = 2;
            icon = 'row;' + icon;
            level = null;
        }
        var data;
        if(add) {
            data = {
                fatherMenuId : fatherMenuId,
                name : name,
                url : url,
                icon : icon,
                buttonOrMenu : buttonOrMenu,
                isEnable : 1,
                level : level
            };
            postAndGetData('/saveMenu', data, function (resp) {
                switch (resp.code){
                    case 200 : {
                        showMsg("添加成功");
                        $("#dialog").dialog("close");
                        break;
                    }
                    case 402 : {
                        resp.data.forEach(function (obj) {
                            showError('#' + obj.field, obj.defaultMessage);
                        });
                        break;
                    }
                    case 406 : {
                        showError('#name', "该名称已存在");
                        break;
                    }
                }
            });
        }else {
            data = {
                menuId : menuId,
                name : name,
                icon : icon,
                url : url
            };
            postAndGetData('/updateMenuById', data, function (resp) {
                if(resp.code === 200) {
                    showMsg("修改成功");
                    updateNodeId = null;
                }else if(resp.code === 402) {
                    resp.data.forEach(function (obj) {
                        showError('#update_' + obj.field, obj.defaultMessage);
                    });
                }
            });
        }
    });

    //菜单---省市联动
    $("#fatherMenuId1").change(function () {
        $.ajax({
            type : 'post',
            url : '/getAllMiddleMenus?fatherMenuId=' + $(this).val(),
            success : function (data) {
                var select2 = $("#fatherMenuId11");
                select2.empty();
                if(data.length === 0) {
                    select2.append('<option value="">该主菜单下无二级菜单</option>');
                    select2.prop("disabled", true);
                }else {
                    data.forEach(function (obj) {
                        select2.append('<option value="' + obj.menuId +'">' + obj.name +'</option>');
                    });
                    select2.prop("disabled", false);
                }
            }
        });
    });

    //菜单---省市联动
    $("#fatherMenuId2").change(function () {
        $.ajax({
            type : 'post',
            url : '/getAllMiddleMenus?fatherMenuId=' + $(this).val(),
            success : function (data) {
                var select2 = $("#fatherMenuId22");
                select2.empty();
                if(data.length === 0) {
                    select2.append('<option value="">该主菜单下无二级菜单</option>');
                    select2.prop("disabled", true);
                }else {
                    data.forEach(function (obj) {
                        select2.append('<option value="' + obj.menuId +'">' + obj.name +'</option>');
                    });
                    select2.prop("disabled", false);
                }
                var middleId = select2.val();
                $.ajax({
                    type : 'post',
                    url : '/getAllBottomMenus?fatherMenuId=' + middleId,
                    success : function (data) {
                        var select3 = $("#fatherMenuId222");
                        select3.empty();
                        if(data.length === 0) {
                            select3.append('<option value="">该主菜单下无三级菜单</option>');
                            select3.prop("disabled", true);
                        }else {
                            data.forEach(function (obj) {
                                select3.append('<option value="' + obj.menuId +'">' + obj.name +'</option>');
                            });
                            select3.prop("disabled", false);
                        }
                    }
                });
            }
        });
    });

    //菜单---省市联动
    $("#fatherMenuId22").change(function () {
        $.ajax({
            type : 'post',
            url : '/getAllBottomMenus?fatherMenuId=' + $(this).val(),
            success : function (data) {
                var select3 = $("#fatherMenuId222");
                select3.empty();
                if(data.length === 0) {
                    select3.append('<option value="">该二级菜单下无三级菜单</option>');
                    select3.prop("disabled", true);
                }else {
                    data.forEach(function (obj) {
                        select3.append('<option value="' + obj.menuId +'">' + obj.name +'</option>');
                    });
                    select3.prop("disabled", false);
                }
            }
        });
    });
});

function showMenu(obj) {
    var menuId = $(obj).prop("title");
    var table;
    $.ajax({
        type : 'post',
        url : "/getMiddleMenusByFatherId?fatherMenuId=" + menuId,
        success : function (data) {
            if(data.length === 0) {
                $.Pop("暂无子菜单或子按钮",{
                    Close : false,
                    Title : "子菜单或子按钮",
                    BoxDrag : false,
                    BoxBgopacity : 0.3
                },'confirm',function(){

                });
                return;
            }
            var flag = true;
            data.forEach(function (obj) {
                if(obj.level === 2 && flag) {
                    //说明是二级菜单
                    table =
                        '<div class="card-body p-0">' +
                        '<table class="table table-condensed">' +
                        '<tr>' +
                        '<th>二级菜单</th>' +
                        '<th>菜单地址</th>' +
                        '<th>菜单等级</th>' +
                        '<th>菜单图标</th>' +
                        '<th>菜单所属权限</th>' +
                        '<th>是否启用</th>' +
                        '</tr>';
                    flag = false;
                }else if(obj.level === 3 && flag) {
                    //说明是三级菜单
                    table =
                        '<div class="card-body p-0">' +
                        '<table class="table table-condensed">' +
                        '<tr>' +
                        '<th>三级菜单</th>' +
                        '<th>菜单地址</th>' +
                        '<th>菜单等级</th>' +
                        '<th>菜单图标</th>' +
                        '<th>菜单所属权限</th>' +
                        '<th>是否启用</th>' +
                        '</tr>';
                    flag = false;
                }else if (obj.level === null && flag) {
                    //说明是按钮
                    table =
                        '<div class="card-body p-0">' +
                        '<table class="table table-condensed">' +
                        '<tr>' +
                        '<th>按钮名称</th>' +
                        '<th>按钮地址</th>' +
                        '<th>按钮等级</th>' +
                        '<th>按钮样式</th>' +
                        '<th>按钮所属权限</th>' +
                        '<th>是否启用</th>' +
                        '</tr>';
                    flag = false;
                }
               table = table +
                   '<tr><td>' +
                       '<div class="btn-group">' +
                           '<button type="button" class="btn btn-danger">' + obj.name +'</button>';
                if(obj.buttonOrMenu === 1) {
                    table = table + '<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown"></button>' +
                        '<div class="dropdown-menu" role="menu">' + '<a class="dropdown-item" href="javascript:void(0)" title="' + obj.menuId +'" onclick="showMenu(this)">查看子菜单或子按钮</a>';
                }
               table = table +
                            '</div>' +
                       '</div>' +
                   '</td>' +
                   '<td>' + obj.url +'</td>' +
                   '<td><button type="button" class="btn btn-success">' + obj.level +'</button></td>' +
                   '<td>' + obj.icon + '</i></td>';
                    if(obj.permissionName === null) {
                        table = table + '<td>无</td>';
                    }else {
                        table = table + '<td>' + obj.permissionName +'</td>';
                    }
                    if(obj.isEnable === 1) {
                        table = table + '<td>是</td>';
                    }else {
                        table = table + '<td>否</td></tr>'
                    }
            });
            table = table + '</table></div>';
            $.Pop(table,{
                Close : false,
                Title : "子菜单或子按钮",
                BoxDrag : true,
                BoxBgopacity : 0.3
            },'confirm',function(){

            });
        }
    });
}

function icons(resp) {
    $("#icon").remove();
    $("#div-icon").append('<select class="form-control" id="icon"></select>');
    var iconSelect = $("#icon");
    for(var i = 0; i < resp.length; i++) {
        var obj = resp[i];
        iconSelect.append('<option value="' + obj.path + '">' + obj.name + '</option>');
    }
}

function csses(resp) {
    $("#icon").remove();
    $("#div-icon").append('<input type="text" class="form-control" id="icon" placeholder="样式">');
    $("#icon").val(resp ? resp.icon : '');
}