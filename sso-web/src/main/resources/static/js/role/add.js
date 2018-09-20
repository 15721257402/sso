$(function(){

	 $("#formSubmit").click(function(){
		 
		 $("#roleAddForm").validate({
		        rules: {
		        	roleName: {
		                required: true,
		                maxlength: 10
		            },
		            systemId: {
		                required: true,
		                maxlength: 18
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
		            systemId: {
		                required: "请选择系统",
		                minlength: "系统id长度不能超过18"
		            },
		            description:{
		                required: "请输入权限描述",
		                minlength:"角色描述长度不能超过50"
		            }
		        }

		    });
		 
		 var roleName=$("#roleName").val();
		 var systemId=$("#systemId").val();
		 var description=$("#description").val();
         if($("#roleAddForm").valid()){
        	 $.ajax({
        		 type: 'POST',
        		 url: "/role/addOrUpdate",
        		 data: {'roleName' : roleName,"systemId" : systemId, "description" : description},
        		 traditional: true,
        		 success: function(data){
        			 if(data.successFlag=="true"){
        				 alert("添加成功");
        				 location.reload();
        			 }else{
        				 alert("添加失败");
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
 