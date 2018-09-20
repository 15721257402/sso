$(function(){

	 $("#formSubmit").click(function(){
		 $("#permissionAddForm").validate({
		        rules: {
		        	name: {
		                required: true,
		                maxlength: 10
		            },
		            systemId: {
		                required: true,
		                maxlength: 18
		            },
		            url: {
		                required: true,
		            },
		            description: {
		                required: true,
		                maxlength: 50
		            },
		        },
		        messages: {
		        	name: {
		                required: "权限名称不能为空",
		                maxlength: "权限名称长度不能超过10"
		            },
		            systemId: {
		                required: "请选择系统",
		                maxlength: "系统id长度不能超过18"
		            },
		            url:{
		                required: "请输入url"
		            },
		            description:{
		                required: "请输入权限描述",
		                maxlength:"权限描述长度不能超过50"
		            }
		        }

		    });
		 var name=$("#name").val();
		 var type=$('input:radio:checked').val();
		 var systemId=$("#systemId").val();
		 var parentId=$("#parentId").val();
		 var description=$("#description").val();
		 var url=$("#url").val();
		 if( $("#permissionAddForm").valid()){
			 $.ajax({
				  type: 'POST',
				  url: "/permission/addOrUpdate",
				  data: {'name' : name,"type" : type, "systemId" : systemId, "parentId" : parentId , "description" : description , "url" : url},
				  dataType: "json",
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
 