$(function() {
	model();
	
	
});
function update(obj) {
	var userId = $(obj).attr("userId");
	var obj = "{'userId':'"+userId+"'}";
	$("#submitUserUpdate").removeAttr("onclick");
	$("#submitUserUpdate").attr("onclick","submitUserUpdate("+obj+")");
	var roles;
	$.ajax({
        type: "post",
        url: "../user/getUserRoleInfo",
        data: {userId:""},
        dataType: "json",
        async:false,
        success: function(data){
        	$("#select1").html("");
        	$.each(data,function(i,o){
    			$("#select1").append("<option id='role_"+o.role_id+"' value='"+o.role_id+"'>"+o.role_name+"</option>")
        	});
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            
         }
	 });
        $.ajax({
            type: "post",
            url: "../user/getUserRoleInfo",
            data: {userId:userId},
            dataType: "json",
            async:false,
            success: function(data){
            	$("#select2").html("");
            	$.each(data,function(i,o){
        			$("#select2").append("<option value='"+o.role_id+"'>"+o.role_name+"</option>")
        			$("#role_"+o.role_id).remove();
            	});
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                
             }
        });
   
        
	
	$('#myModal').modal('show');
}
function submitUserUpdate(obj) {
	var options = $("#select2")[0].options;
	var optionLength = options.length;
	if(optionLength <= 0){
		Ewin.confirm({ message: "用户角色不能为空！" });
	}else{
		var roleIds="";
		$.each(options,function(i,o) {
			roleIds+=$(o).attr("value")+";"
		})
		$.ajax({
            type: "post",
            url: "../user/saveUserRoleInfo",
            data: {userId:obj.userId, roleIds:roleIds},
            dataType: "json",
            success: function(data){
            			$('#myModal').modal('hide');
                     },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                
             }
        });
	}
}
function deleteUser(obj) {
	var userId = $(obj).attr("userId");
	Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            type: "post",
            url: "../user/deleteUserInfo",
            data: {userId:userId},
            dataType: "json",
            success: function(data){
            	window.location.reload();
                     },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                
             }
        });
	})
}
function model(){
	//移到右边
    $('#add').click(function() {
    //获取选中的选项，删除并追加给对方
        $('#select1 option:selected').appendTo('#select2');
    });
    //移到左边
    $('#remove').click(function() {
        $('#select2 option:selected').appendTo('#select1');
    });
    //全部移到右边
    $('#add_all').click(function() {
        //获取全部的选项,删除并追加给对方
        $('#select1 option').appendTo('#select2');
    });
    //全部移到左边
    $('#remove_all').click(function() {
        $('#select2 option').appendTo('#select1');
    });
    //双击选项
    $('#select1').dblclick(function(){ //绑定双击事件
        //获取全部的选项,删除并追加给对方
        $("option:selected",this).appendTo('#select2'); //追加给对方
    });
    //双击选项
    $('#select2').dblclick(function(){
       $("option:selected",this).appendTo('#select1');
    });
}

