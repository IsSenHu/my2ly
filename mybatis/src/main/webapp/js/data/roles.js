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
            "url" : "/pageRole"
        },
        "fnServerParams" : function (aoData) {
            aoData.roleId = $("#find_roleId").val();
            aoData.name = $("#find_name").val();
        },
        "columns" : [
            {"data" : "roleId"},
            {"data" : "name"},
            {"data" : "description"},
            {"orderable" : false,
                "render" :  function (data, type, row) {
                    var roleId = row.roleId;
                    var buttons = '';
                    current_page_row_buttons.forEach(function (value) {
                        buttons = buttons + value.replace(/{{roleId}}/g, roleId) + '&nbsp;';
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
        $("#find_roleId").val('');
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
     * 保存角色
     * */
    $(".saveRole").click(function () {
        var name = getRealValue($("#name"));
        var description = getRealValue($("#description"));
        var data = {
            name : name,
            description : description
        };
        postAndGetData('/saveRole', data, function (resp) {
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
                case 406 : {
                    showError('#name', "该角色名已存在");
                    break;
                }
            }
        })
    });
    
    /**
     * 取消添加面板
     * */
    $(".cancelAdd").click(function () {
        $("#addCard").css("display", "none");
        $("#name").val("");
        $("#description").val("");
    });

    /**
     * 重置添加面板
     * */
    $(".resetAdd").click(function () {
        $("#name").val("");
        $("#description").val("");
    });
});

function deleteRow(obj) {
    var roleId = $(obj).val();
    $.ajax({
        type : 'post',
        url : '/deleteRoleById?roleId=' + roleId,
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
 * 权限管理
 * */
function managePermission(obj) {
    var roleId = $(obj).val();
    postAndGetData('/showAboutRolePermission?roleId=' + roleId, null, function (data) {
        var div =
            '<tr>' +
                '<td colspan="10">' +
                    '<div class="card">' +
                        '<div class="card-header" style="text-align: left; background-color: #FFC107; color: white;">角色权限树</div>' +
                        '<div class="card-body">' +
                            '<ul id="tree' + roleId + '" class="ztree"></ul>' +
                        '</div>' +
                        '<div class="card-footer" style="text-align: left;">' +
                            '<button class="btn btn-primary" id="savePermission' + roleId +'" value="' + roleId +'">保存</button> ' +
                            '<button class="btn btn-dark" value="' + roleId + '" onclick="closeDetail(this)">收起</button>' +
                        '</div>' +
                    '</div>' +
                '</td>' +
            '</tr>';
        showDetail(obj, div);
        //加载菜单树
        var zTreeObj = $.fn.zTree.init($("#tree" + roleId), setting, data);
        $(obj).prop("disabled", true);
        $("#savePermission" + roleId).click(function () {
            saveTreeNodes(zTreeObj);
        })
    });
}

/**
 * 保存树
 * */
function saveTreeNodes(obj) {
    var nodes = obj.getNodes();
    postAndGetData('/saveRolePermission', nodes, function (data) {
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