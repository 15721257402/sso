$(function(){

	 $("#formSubmit").click(function(){
		 
		 $("#roleUpdateForm").validate({
		        rules: {
		        	roleName: {
		                required: true,
		                maxlength: 10
		            },
		            description: {
		                required: true,
		                maxlength: 50
		            },
		        },
		        messages: {
		        	roleName: {
		                required: "角色名称不能为空",
		                minlength: "权限名称长度不能超过10"
		            },
		            description:{
		                required: "请输入权限描述",
		                minlength:"角色描述长度不能超过50"
		            }
		        }

		    });
		 
		 var roleName=$("#roleName").val();
		 var description=$("#description").val();
		 var id=$("#id").val();
         if($("#roleUpdateForm").valid()){
        	 $.ajax({
        		 type: 'POST',
        		 url: "/role/addOrUpdate",
        		 data: {'roleName' : roleName, "description" : description,"id":id},
        		 dataType: "json",
        		 success: function(data){
        			 if(data.successFlag=="true"){
        				 alert("修改成功");
        				 window.location.href="/role/list";
        			 }else{
        				 alert("修改失败");
        			 }
        		 }
        	 });
        	 
         }
	 });
	 
	 function isnull(str,str2){
		 if(str==null || str==""){
			 alert(str2+"不能为空");
			 return true;
		 }
		 return false;
	 }
	 
 })
 