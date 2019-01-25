$(function() {
    load();
    initDataGrid();

});

function load(){
    $("#User").css('display','none');
    $("#manageDept").css('display','none');
    //click事件可以自己用jquery绑定, easyui提供了选中事件
    $('#accordionid').accordion({
        onSelect:function(title, index)
        {
            if (index==0){
                $("#User").css('display','block');
                initDataGrid();
            }
        }
    });
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
                find();
            }else {
                $.messager.alert("消息","添加失败！");
			}
        }
    });
}
function addexcel_user(){
    $("#addexcel_user").window('open');
}
function downloadTemplate_excel(){
    window.location.href ='admin/downloadTemplateExcel'
}

function uploadAddexcel_user() {
    $("#exceladd_user").form({
        type : 'post',
        url : 'admin/addeUsers',
        dataType : "json",
        onSubmit: function() {
            var fileName= $('#excel').filebox('getValue');
            //对文件格式进行校验
            var d1=/\.[^\.]+$/.exec(fileName);
            if (fileName == "") {
                $.messager.alert('Excel批量用户导入', '请选择将要上传的文件!');
                return false;
            }else if(d1!=".xls"){
                $.messager.alert('提示','请选择xls格式文件！','info');
                return false;
            }
            $("#booten").linkbutton('disable');
            return true;
        },
        success : function(result) {
            var map=eval('('+result+')');
            console.info(result);
            console.info(map.msg);
            if (map.msg==="上传成功") {
                $.messager.alert('提示!', '上传成功','info',
                    function() {
                        $("#booten").linkbutton('enable');
                        $("#addexcel_user").window('close');
                    });
            } else {
                $.messager.confirm('提示',"上传失败!");
                $("#booten").linkbutton('enable');
            }
        }
    });
    $("#exceladd_user").form('submit');
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
                find();
            }
        });

    }

}

function deptMag(){
    load();
    $("#manageDept").css('display','block');
    datagriddept();
}

function datagriddept() {
    $('#boxDept').datagrid({
        width:'99.5%',
        height:408,
        remoteSort:false,
        iconCls : 'icon-search',
        columns : [[
            {
                field : 'deptid',
                title : '编号',
                checkbox : true
            },
            {
                field : 'dept',
                title : '名称',
                sortable:true,
                formatter:dwxx_formatter
            }

        ]],
        rownumbers:true,
        pagination:true,
    });
}