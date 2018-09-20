$(function() {
	// select function
	$('#add').on('click', function() {
		var selected = $('#left_select option:selected');
		if(selected.length > 0)
			$('#right_select').append(selected);
	});
	$('#remove').on('click', function() {
		var selected = $('#right_select option:selected');
		if(selected.length > 0)
			$('#left_select').append(selected);
	});
	$('#addAll').on('click', function() {
		$('#right_select').append($('#left_select option'));
	});
	$('#removeAll').on('click', function() {
		$('#left_select').append($('#right_select option'));
	});
    changeSys(true);
});

var userRoldIds = "";

function clickSys() {
    var allOption="";
    var options = $("#right_select").find("option");
    for(var i=0;i<options.length;i++) {
        allOption = allOption+options.eq(i).val()+",";//option中的值
    }
    var roleIds = allOption.substr(0,allOption.length-1)
    var sysCode = $("#sysCode").val();//系统编码
    var userId = $("#userId").val();
    $.ajax({
        type: "POST",
        url: "/ssouser/changeUserRole",
        data:{"userId":userId,"systemId":sysCode,"roleIds":roleIds},
        dataType: "json",
        success: function(data){
            if(data.result){
            }else{
            }
        }
    });
}

function changeSys(newRule) {
    var sysCode = $("#sysCode").val();//系统编码
    var userId = $("#userId").val();
    $.ajax({
        type: "POST",
        url: "/ssouser/ajaxUserRole",
        data:{"systemId":sysCode,"userId":userId},
        dataType: "json",
        async:false,
        success: function(data){
            var notUserRoleList = data.notUserRoleList;
            var userRoleList = data.userRoleList;
            $("#left_select").html("");
            for(var i=0;i<notUserRoleList.length;i++){
                $("#left_select").append("<option value='"+notUserRoleList[i].id+"'>"+notUserRoleList[i].roleName+"</option>")
			}
            $("#right_select").html("");
            for(var i=0;i<userRoleList.length;i++){
                $("#right_select").append("<option value='"+userRoleList[i].id+"'>"+userRoleList[i].roleName+"</option>")
            }
        }
    });
}

function userRoleSubmit(newRule) {
   var url = "/ssouser/editCacUser";//修改
   if(newRule){//新增
       url = "/ssouser/addCacUser";
   }

    jQuery.validator.addMethod("isphoneNum", function(value, element) {
        var length = value.length;
        var mobile = /^(0|86|17951)?(1[0-9])[0-9]{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");

    $("#userRoleForm").validate({
        rules: {
            userName: {
                required: true
            },
            password: {
                required: true,
                minlength: 5
            },
            confirmPassword: {
                required: true,
                minlength: 5,
                equalTo: "#password"
            },
            entryTime:{
                required: true,
            },
            birthday:{
                required: true,
            },
            email: {
                required: true,
                email: true
            },
            mobile: {
                required: true,
                isphoneNum: true
            },
        },
        messages: {
            username: {
                required: "请输入用户名",
                minlength: "用户名必需由两个字母组成"
            },
            password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母"
            },
            confirmPassword: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母",
                equalTo: "两次密码输入不一致"
            },
            entryTime:{
                required: "请选择入职时间"
            },
            birthday:{
                required: "请选择出生日期"
            },
            email:{
                required: "请输入邮箱",
                email:"请输入一个正确的邮箱"
            },
            mobile:{
                required: "请输入手机号",
                isphoneNum:"请输入一个正确的手机号"
            }
        }
    });
    if($("#userRoleForm").valid()){
        $.ajax({
            type: "POST",
            url: url,
            data:$('#userRoleForm').serialize(),
            dataType: "json",
            async:false,
            success: function(data){
                if(data.result){
                    if(newRule){//新增
                        alert("新增成功。");
                    }else{//编辑
                        alert("编辑成功。");
                    }
                    window.location.href="/ssouser/gotoList";
                }else{
                    if(newRule){//新增
                        alert("新增失败。");
                    }else{//编辑
                        alert("编辑失败。");
                    }
                }
            },
            error:function (data) {
                window.location.href="/client/noPermission";
            }


        });
    }

}

function userdel(userId) {
    if(confirm("确认删除么？")){
        $.ajax({
            type: "POST",
            url: "/ssouser/delUser",
            data:{"userId":userId},
            dataType: "json",
            success: function(data){
                if(data.result){
                    alert("删除成功。");
                    window.location.reload();
                }else{
                    alert("删除失败。");
                }
            }
        });
    }
}


function userRoleSub() {
    var allOption="";
    var options = $("#right_select").find("option");
    for(var i=0;i<options.length;i++) {
        allOption = allOption+options.eq(i).val()+",";//option中的值
    }
    var roleIds = allOption.substr(0,allOption.length-1)
    var sysCode = $("#sysCode").val();//系统编码
    var userId = $("#userId").val();
    $.ajax({
        type: "POST",
        url: "/ssouser/changeUserRole",
        data:{"userId":userId,"systemId":sysCode,"roleIds":roleIds},
        dataType: "json",
        success: function(data){
            if(data.result){
                alert("分配成功。");
                window.location.href="/ssouser/gotoList";
            }else{
                alert("分配失败。");
            }
        }
    });
}

function search(){
    var userNameSearch = $("#userNameSearch").val();
    if(userNameSearch==''){
        alert("请输入查询条件");
        return;
    }
    window.location.href="/ssouser/gotoList?userName="+userNameSearch;
}


function changePwdSubmit() {
    $("#changePwdForm").validate({
        rules: {
            password: {
                required: true,
                minlength: 5
            },
            newPassword:{
                required: true,
                minlength: 5
            },
            confirmPassword: {
                required: true,
                minlength: 5,
                equalTo: "#newPassword"
            }
        },
        messages: {
            password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母"
            },
            confirmPassword: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母",
                equalTo: "两次密码输入不一致"
            }
        }
    });

    if($("#changePwdForm").valid()){
        $.ajax({
            type: "POST",
            url: "/ssouser/changePwd",
            data:$('#changePwdForm').serialize(),
            dataType: "json",
            async:false,
            success: function(data){
                if(data.result==='success'){
                    alert("修改成功。");
                    window.location.href="/ssouser/gotoList";
                }else{
                    alert(data.result);
                }
            },
            error:function (data) {
                window.location.href="/client/noPermission";
            }


        });
    }
}