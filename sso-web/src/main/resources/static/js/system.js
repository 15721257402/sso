$(function() {

});


function sysSubmitBtn(newRule) {
    $("#systemForm").validate({
        rules: {
            shortName: {
                required: true,
                minlength:3
            },
            name: {
                required: true,
                minlength:5
            },
            url: {
                required: true
            }
        },
        messages: {
            shortName: {
                required: "请输入短名称",
                minlength: "短名称最少3个字符"
            },
            name: {
                required: "请输入系统名称",
                minlength: "系统名称最少5个字符"
            },
            confirmPassword: {
                required: "请输入系统地址"
            }
        }
    });
    if($("#systemForm").valid()){
        var url = "/ssoSystem/editCacSystem";//修改
        if(newRule){//新增
            url = "/ssoSystem/addCacSystem";
        }
        var systemForm = $('#systemForm').serialize();
        $.ajax({
            type: "POST",
            url: url,
            data:systemForm,
            dataType: "json",
            success: function(data){
                if(data.result){
                    if(newRule){//新增
                        alert("新增成功。");
                    }else{//编辑
                        alert("编辑成功。");
                    }
                    window.location.href="/ssoSystem/gotoList";
                }else{

                    if(newRule){//新增
                        alert("新增失败。");
                    }else{//编辑
                        alert("编辑失败。");
                    }
                }
            }
        });
    }
}

function sysdel(sysId) {
    if(confirm("确认删除么？")){
        $.ajax({
            type: "POST",
            url: "/ssoSystem/delSystem",
            data:{"sysId":sysId},
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

function search(){
    var systemName = $("#systemName").val();
    if(systemName==''){
        alert("请输入查询条件");
        return;
    }
    window.location.href="/ssoSystem/gotoList?name="+systemName;
}
