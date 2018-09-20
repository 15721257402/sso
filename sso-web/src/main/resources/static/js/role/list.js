
 $(function(){
	 $("#queryData").click(function(){
		 var systemId=$("#systemSelectId").val();
		 window.location.href="/role/list?systemId="+systemId+"&pageNum=1"
	 });
	 $("#systemSelectId").change(function(){
		 var systemId=$("#systemSelectId").val();
		 window.location.href="/role/list?systemId="+systemId+"&pageNum=1"
	 })
 })
 
 //删除角色
 function deleteRole(obj){
	 if(!confirm("确认删除么？")){
		 return;
	 }
	 var id = $(obj).attr("id");
	 if(id != null && id != ""){
		 $.ajax({
			 type: 'POST',
			 url: "/role/delete",
			 data: {'roleId' : id},
			 dataType: "json",
			 success: function(data){
				 if(data.successFlag=="true"){
					 alert("删除角色成功");
					 location.reload();
				 }else{
					 alert("删除角色失败");
				 }
			 }
		 });
	 }
 }
 
 //去分配角色
 function goAllocation(obj){
	 var id = $(obj).attr("id");
	 var roleName = $(obj).attr("roleName");
	 var systemId = $(obj).attr("systemId");
	 window.location.href="/role/goAllocation?roleId="+id+"&roleName="+roleName+"&systemId="+systemId;
 }