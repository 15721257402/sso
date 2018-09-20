
 $(function(){
	 $("#formSubmit").click(function(){
		 var arr = [], 
			selectedNodes = $("#tree").fancytree(
					"getTree").getSelectedNodes();
			for ( var n in selectedNodes) {
				if(selectedNodes[n].key!='null' && selectedNodes[n].key!=""){
					arr.push(selectedNodes[n].key);
				}
			}
			console.info(arr);
			if(arr.length>0){
				$.ajax({
					type: 'POST',
					url: "/role/allocation",
					data: {'roleId' : $("#roleId").val(),"longData" : arr},
					dataType: "json",
					success: function(data){
						if(data.successFlag=="true"){
							alert("分配成功");
							location.reload();
						}else{
							alert("分配失败");
						}
					}
				});
			}else{
				alert("请选择有效的权限");
			}
	 });
 })
 