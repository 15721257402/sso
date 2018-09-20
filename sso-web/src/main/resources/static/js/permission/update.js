$(function(){
	 $("#systemId,#type").bind("change",function(){
		 var newsystemSelectValue=$("#systemId").val();
		 if(newsystemSelectValue=="" ){
			 $("#parentId").empty().append("<option value=''>请选择</option>");
			 return;
		 }
		 $.ajax({
			  type: 'POST',
			  url: "/permission/getSystemResource",
			  data: {systemId:newsystemSelectValue},
			  dataType: "json",
			  success: function(data){
				  $("#parentId").empty();
                  var jsonData = JSON.parse(data);
                  for(i in jsonData){
                      var str="<option value="+jsonData[i].id+">"+jsonData[i].name+"</option>"
                      $("#parentId").append(str);
                  }
//				  jQuery.each(data, function(i,item){
//					  var str="<option value="+item.id+">"+item.name+"</option>"
//					  $("#parentId").append(str);
//				      });
			  }
			});
     });
	 
	 
	 $("#formSubmit").click(function(){
		 $("#permissionUpdateForm").validate({
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
		                minlength: "权限名称长度不能超过10"
		            },
		            systemId: {
		                required: "请选择系统",
		                minlength: "系统id长度不能超过18"
		            },
		            url:{
		                required: "请输入url"
		            },
		            description:{
		                required: "请输入权限描述",
		                minlength:"权限描述长度不能超过50"
		            }
		        }

		    });
		 var id=$("#id").val();
		 var name=$("#name").val();
		 var type=$('input:radio:checked').val();
		 var systemId=$("#systemId").val();
		 var parentId=$("#parentId").val();
		 var description=$("#description").val();
		 var url=$("#url").val();
		 if(isnull(id,"参数主键")){return;}
		 if( $("#permissionUpdateForm").valid()){
		 $.ajax({
			  type: 'POST',
			  url: "/permission/addOrUpdate",
			  data: {'name' : name,"type" : type, "systemId" : systemId, "parentId" : parentId , "description" : description , "url" : url, "id" : id},
			  dataType: "json",
			  success: function(data){
				 if(data.successFlag=="true"){
					 alert("修改成功");
					 window.location.href="/permission/list?systemId="+systemId+"&parentId="+parentId;
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
 