$(function() {
    load();
    initDataGrid();

});

function load(){
    $("#addUser").css('display','none');
    var w=document.getElementById("User").style.width;
    var h=document.getElementById("User").style.height;
    var inputh=parseInt(h)-106;
}

function add_user(){

    $("#user_add").window('open');

}

function user_add_save(){

    var dlzh = $("#dlzh_input").val()==null?"":$("#dlzh_input").val().replace(/\s*/g,"").replace(/,/g, "").replace(/，/g, "");
    var userName = $("#userName_input").val()==null?"":$("#userName_input").val().replace(/\s*/g,"").replace(/,/g, "").replace(/，/g, "");
    var dept = $("#ssxy_input").val()==null?"":$("#ssxy_input").val().replace(/\s*/g,"").replace(/,/g, "").replace(/，/g, "");
    var major = $("#sszy_input").val()==null?"":$("#sszy_input").val().replace(/\s*/g,"").replace(/,/g, "").replace(/，/g, "");
    var type = $("#type_input").combobox('getValue')==null?"":$("#type_input").combobox('getValue');
    $.ajax({
        url : "admin/saveAdduser",
        type : "post",
        dataType : "text",
        data : {
            dlzh:dlzh,
            userName:userName,
            dept:dept,
            major:major,
            type:type
        },
        error:function (data) {
            console.info(data);
        },
        success : function(data) {
        	console.info(data);
            if(data=="添加成功"){
                $.messager.alert("消息","添加成功！");
                $("#user_add").window('close');
            }else {
                $.messager.alert("消息","添加失败！");
			}
        }
    });
}
function initDataGrid(){
    $('#box').datagrid({
        width:'99.5%',
        height:408,
        remoteSort:false,
        iconCls : 'icon-search',
        columns : [[
            {
                field : 'id',
                title : '编号',
                checkbox : true
            },
            {
                field : 'accountid',
                title : '账号',
                sortable:true,
                formatter:dwxx_formatter
            },
            {
                field : 'username',
                title : '姓名',
                sortable:true,
                formatter:dwxx_formatter
            },
            {
                field : 'password',
                title : '密码',
                sortable:true,
                formatter:dwxx_formatter
            },
            {
                field : 'dept',
                title : 'dept',
                sortable:true,
                formatter:dwxx_formatter
            },
            {
                field : 'major',
                title : 'major',
                sortable:true,
                formatter:dwxx_formatter
            },

        ]],
        rownumbers:true,
        pagination:true,
    });
    find();
}
function find() {
    var opts = $('#box').datagrid("options");
    if(null == opts.url||""==opts.url){
        opts.url = '/admin/find';
    }
    $('#box').datagrid('load', {

    });
}
function dwxx_formatter(value, row){
    if(value == undefined) {
        value = "";
    }
    return "<lable title='"+value+"'>"+value+"</lable>"
}
function deluser(){
    var users = $('#box').datagrid('getSelections');
    if (users.length<1){
        $.messager.alert("消息","至少选择一项");
    }else {

        var array = new Array()
        for ( var i = 0; i < users.length; i++) {
            var obj = new Object();
            obj.id = users[i].id == null?"": users[i].id+"";
            array.push(obj);
        }
        $.ajax({
            url : "/admin/delUser",
            type : "post",
            dataType : "text",
            data : {datas:JSON.stringify(array)},
            success : function(data) {
                $.messager.alert("消息",data);
            }
        });

    }

}

