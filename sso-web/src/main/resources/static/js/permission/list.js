
 $(function(){
	 $("#queryData").click(function(){
		 var systemId=$("#systemSelectId").val();
		 window.location.href="/permission/list?systemId="+systemId+"&pageNum=1"
	 });
	 
	 $("#systemSelectId").change(function(){
		 var systemId=$("#systemSelectId").val();
		 window.location.href="/permission/list?systemId="+systemId+"&pageNum=1"
	 })
	 
 })
 //删除权限
 function deleteResource(obj){
	 if(!confirm("确认删除么？")){
		 return;
	 }
	 var id = $(obj).attr("id");
	 if(id != null && id != ""){
		 $.ajax({
			 type: 'POST',
			 url: "/permission/addOrUpdate",
			 data: {'id' : id,'delFlag' : '02'},
			 dataType: "json",
			 success: function(data){
				 if(data.successFlag=="true"){
					 alert("删除权限成功");
					 location.reload();
				 }else{
					 alert("删除权限失败");
				 }
			 }
		 });
	 }
 }
 
 function selectSonResource(obj){
	 var id = $(obj).attr("id");
	 var systemId=$("#systemSelectId").val();
	 window.location.href="/permission/list?systemId="+systemId+"&parentId="+id+"&pageNum=1"
 }