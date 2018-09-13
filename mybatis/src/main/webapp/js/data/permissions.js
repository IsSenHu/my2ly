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
            "url" : "/pagePermission"
        },
        "fnServerParams" : function (aoData) {
            aoData.permissionId = $("#find_permissionId").val();
            aoData.name = $("#find_name").val();
        },
        "columns" : [
            {"data" : "permissionId"},
            {"data" : "name"},
            {"data" : "description"},
            {"orderable" : false,
                "render" :  function (data, type, row) {
                    var permissionId = row.permissionId;
                    var buttons = '';
                    current_page_row_buttons.forEach(function (value) {
                        buttons = buttons + value.replace(/{{permissionId}}/g, permissionId) + '&nbsp;';
                    });
                    return buttons;
                }
            }
        ]
    };

    table = $('#example1').DataTable(setting);
    $("#search").click(function () {
        table.draw();
    });

    $("#reset").click(function () {
        $("#find_permissionId").val('');
        $("#find_name").val('');
        table.draw();
    });

    /**
     * 显示添加面板
     * */
    $("#add").click(function () {
        $("#addCard").css("display", "block");
    });

    /**
     * 重置输入框
     * */
    $(".resetAdd").click(function () {
       $("#name").val("");
       $("#description").val("");
    });

    /**
     * 收起添加面板
     * */
    $(".cancelAdd").click(function () {
        $("#addCard").css("display", "none");
        $("#name").val("");
        $("#description").val("");
    });
    
    /**
     * 保存权限
     * */
    $(".savePermission").click(function () {
        var name = getRealValue($("#name"));
        var description = getRealValue($("#description"));
        var data = {
            name : name,
            description : description
        };
        postAndGetData('/savePermission', data, function (resp) {
            switch (resp.code){
                case 200 : {
                    showMsg("添加成功");
                    $("#dialog").dialog("close");
                    table.draw();
                    break;
                }
                case 402 : {
                    resp.data.forEach(function (obj) {
                        showError('#' + obj.field, obj.defaultMessage);
                    });
                    break;
                }
                case 406 : {
                    showError('#name', "该权限名已存在");
                    break;
                }
            }
        });
    });
});

function deleteRow(obj) {
    var permissionId = $(obj).val();
    $.ajax({
        type : 'post',
        url : '/deletePermissionById?permissionId=' + permissionId,
        success : function (resp) {
            if(resp.code === 200) {
                showMsg("删除成功");
                table.draw();
            }
        }
    });
}

/**
 * 菜单按钮设置
 * */
function menuSet(obj) {
    var permissionId = $(obj).val();
    postAndGetData('/showMenusByPermissionId?permissionId=' + permissionId, null, function (data) {
        var div =
            '<tr>' +
                '<td colspan="10">' +
                '<div class="card">' +
                    '<div class="card-header" style="text-align: left; background-color: #FFC107; color: white;">菜单/按钮设置</div>' +
                    '<div class="card-body">' +
                            '<span><button class="btn btn-dark" id="btnCurrent' + permissionId + '">当前的权限:' + (data.current ? data.current[0].name : '无') + '</button></span><br><br>' +
                            '<span><select class="form-control" style="width: 100%;" id="select2' + permissionId + '"></select></span>' +
                    '</div>' +
                    '<div class="card-footer" style="text-align: left;">' +
                        '<button class="btn btn-primary" id="saveMenu' + permissionId +'" value="' + permissionId +'">保存</button> ' +
                        '<button class="btn btn-dark" value="' + permissionId + '" onclick="closeDetail(this)">收起</button>' +
                    '</div>' +
                '</div>' +
                '</td>' +
            '</tr>';
        var notBeSelected = data.notBeSelected;
        showDetail(obj, div, notBeSelected);
        $(obj).prop("disabled", true);
        $("#saveMenu" + permissionId).click(function () {
            var menuId = $("#select2" + permissionId).val();
            var data = {
                permissionId : permissionId,
                menuId : menuId
            };
            postAndGetData('/savePermissionMenu', data, function (data) {
                var menus = data.data.notBeSelected;
                if(data.code === 200) {
                    showMsgNoReload("保存成功");
                    if(menuId === 0 || menuId === '0') {
                        $("#btnCurrent" + permissionId).html('当前的权限:无');
                    }else {
                        menus.forEach(function (e) {
                            if(e.menuId === menuId) {
                                $("#btnCurrent" + permissionId).html('当前的权限:' + e.name);
                                return;
                            }
                        });
                    }
                    //还要重新去加载选择下拉列表
                    var select = $("#select2" + permissionId);
                    select.empty();
                    select.append('<option value="0">清除现在所关联的菜单/按钮</option>');
                    //处理menus 排除当前的菜单
                    var newMenus = [];
                    for(var i = 0; i < menus.length; i++) {
                        if(menus[i].menuId !== menuId) {
                            newMenus.push(menus[i]);
                        }
                    }
                    newMenus.forEach(function (node) {
                        select.append('<option value="' + node.menuId + '">' + node.name +'</option>')
                    });
                    select.select2();
                }else {
                    showMsgNoReload("保存失败");
                }
            });
        })
    });
}

/**
 * 展示面板
 * */
function showDetail(obj, node, menus) {
    var permissionId = $(obj).val();
    var $button;
    if(obj === undefined || obj == null){
        $button = this;
    }else {
        $button = $(obj);
    }
    $button.parent().parent().after(node);
    var select = $("#select2" + permissionId);
    select.empty();
    select.append('<option value="0">清除现在所关联的菜单/按钮</option>');
    menus.forEach(function (node) {
        select.append('<option value="' + node.menuId + '">' + node.name +'</option>')
    });
    select.select2();
}

/**
 * 收起面板
 * */
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