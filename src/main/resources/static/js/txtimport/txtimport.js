$(function () {
    load();
});


function load() {
    $("#txt").filebox({
        accept: '*.txt',
        buttonText: '选择文件',
        buttonAlign: 'right'
    });
}


function uploadTxt() {
    $("#uploadTxt").form({
        type : 'post',
        url : 'upload/txt',
        dataType : "json",
        onSubmit: function() {
            var fileName= $('#txt').filebox('getValue');
            //对文件格式进行校验
            var d1=/\.[^\.]+$/.exec(fileName);
            if (fileName == "") {
                $.messager.alert('Excel批量用户导入', '请选择将要上传的文件!');
                return false;
            }else if(d1!=".txt"){
                $.messager.alert('提示','请选择txt格式文件！','info');
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
                        window.location.href = "show/paper";
                    });
            } else {
                $.messager.confirm('提示',"上传失败!");
                $("#booten").linkbutton('enable');
            }
        }
    });
    $("#uploadTxt").form('submit');
}