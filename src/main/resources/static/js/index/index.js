$(function() {
	load();
});

function load() {
	
	$('#mpanel3').codeVerify({
		type: 2,
		figure: 100, //位数，仅在type=2时生效
		arith: 0, //算法，支持加减乘，不填为随机，仅在type=2时生效
		width: '200px',
		height: '50px',
		fontSize: '30px',
		btnId: 'check-btn2',
		ready: function() {},
		success: function() {
			login()
		},
		error: function() {
			alert('验证码不匹配！');
		}
	});
}


function login(){
	var username=$("#username").val();
	var password=$("#password").val();
	if (!(username.length>4&&username.length<19)) {
		$("#usernameerro").html("用户名至少5个字符,最多18个字符")
        $("#username").val("输入账号");
		return;
	}
    if (!(password.length>5&&password.length<51)) {
        $("#passworderro").html("密码至少6个字符,最多50个字符")
        $("#password").val("");
        return;
    }
    var form = document.getElementById("login-form");
    form.submit();
    var username=$("#username").val("");
    var password=$("#password").val("");
}
