$(function () {
	loadRoleInfo();
	listenTab();
});

function submitSystemNotice() {
	var content = UE.getEditor('editor').getContentTxt();
	if(content.length <= 0){
		Ewin.confirm({ message: "请输入内容！" })
	}else if(content.length >= 200){
		Ewin.confirm({ message: "系统公告最多输入200汉字！" });
	}
	else{
		$.ajax({
            type: "post",
            url: "./system/createNoticeInfo",
            data: {content:content},
            dataType: "json",
            async:false,
            success: function(data){
            	Ewin.confirm({ message: "发布完成" }).on(function (e) {
                    if (!e) {
                        return;
                    }
                    UE.getEditor('editor').setContent('');
            	});
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            	if(XMLHttpRequest.status == 403){
        			Ewin.confirm({ message: "您当前无权限！" });
                }
             }
    	 });
	}
	
}

function listenTab() {
	$('#myTab').on('show.bs.tab', function (e) {
		var tabId = $(e.target).attr("href");
		if("#systemRoleList" == tabId){
			loadRoleInfo();
		}else if("#systemModelList" == tabId){
			loadModelInfo();
		}else if("#systemURL"==tabId){
			loadUrlInfo();
		}else if("#systemNotice"==tabId){
			createEditor();
		}
	})
}

function deleteUrl(obj) {
	var urlId = obj.id;
	Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            type: "post",
            url: "./system/deleteUrlByid",
            data: {urlId:urlId},
            dataType: "json",
            async:false,
            success: function(data){
            	loadUrlInfo();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            	if(XMLHttpRequest.status == 403){
        			Ewin.confirm({ message: "您当前无权限！" });
                }
             }
    	 });
	})
};

function updateUrl (obj) {
	var content = "<ul class=\"list-group\">"
		 +"<li class=\"list-group-item\"><input id=\"updateUrl\" type=\"text\" class=\"form-control\" placeholder=\"URL\" value = '"+obj.urlName+"'></li>"
		 +"<li class=\"list-group-item\"><input id=\"updateUrlDescribe\" type=\"text\" class=\"form-control\" placeholder=\"URL描述\" value = '"+obj.urlDescribe+"'></li>"
		 +"</ul>"
Ewin.confirm({ message:content }).on(function (e) {
if (!e) {
   return;
}
var data = {urlName:$("#updateUrl").val(),
			urlDescribe:$("#updateUrlDescribe").val(),
			urlId:obj.id
};
$.ajax({
   type: "post",
   url: "./system/updateUrl",
   data: data,
   dataType: "json",
   async:false,
   success: function(data){
	   loadUrlInfo();
   },
   error: function(XMLHttpRequest, textStatus, errorThrown){
	   if(XMLHttpRequest.status == 403){
			Ewin.confirm({ message: "您当前无权限！" });
       }
    }
});
});
}

function loadUrlInfo() {
	$(".appendUrlTr").remove();
	$.ajax({
        type: "post",
        url: "./system/getModelAndUrlList",
        data: {},
        dataType: "json",
        async:false,
        success: function(data){
        	$.each(data.urlList,function(i,o){
        		var content = "<tr class=\"appendUrlTr\">"
        		+"<td>"+StringUtil(o.url)+"</td>"
        		+"<td>"+StringUtil(o.url_describe)+"</td>"
        		+"<td>"+StringUtil(o.create_user)+"</td>"
        		+"<td>"+StringUtil(o.create_time)+"</td>"
        		+"<td>"+StringUtil(o.update_user)+"</td>"
        		+"<td>"+StringUtil(o.update_time)+"</td>"
        		+"<td>"
        		+"<span class=\"glyphicon glyphicon-pencil pointer\" aria-hidden=\"true\" title=\"修改\" onclick=\"updateUrl({'id':'"+o.system_url_id+"','type':'url','urlName':'"+StringUtil(o.url)+"','urlDescribe':'"+StringUtil(o.url_describe)+"'})\"></span>"
        		+"<span onclick=\"deleteUrl({'id':'"+o.system_url_id+"','type':'url'})\" class=\"glyphicon glyphicon-trash pointer\" aria-hidden=\"true\" title=\"删除\"></span>"
        		+"</td>"
        		+"</tr>"
    			$("#urlTable").append(content)
        	});
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	if(XMLHttpRequest.status == 403){
    			Ewin.confirm({ message: "您当前无权限！" });
            }
         }
	 });
};

function createUrl() {
	var content = "<ul class=\"list-group\">"
				 +"<li class=\"list-group-item\"><input id=\"saveUrl\" type=\"text\" class=\"form-control\" placeholder=\"URL\"></li>"
				 +"<li class=\"list-group-item\"><input id=\"saveUrlDescribe\" type=\"text\" class=\"form-control\" placeholder=\"URL描述\"></li>"
				 +"</ul>"
	Ewin.confirm({ message:content }).on(function (e) {
        if (!e) {
            return;
        }
        var data = {urlName:$("#saveUrl").val(),
         	   		urlDescribe:$("#saveUrlDescribe").val()
        };
        $.ajax({
            type: "post",
            url: "./system/saveUrl",
            data: data,
            dataType: "json",
            async:false,
            success: function(data){
            	loadUrlInfo();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            	if(XMLHttpRequest.status == 403){
        			Ewin.confirm({ message: "您当前无权限！" });
                }
             }
    	 });
	});
}

function createModel() {
	var content = "<ul class=\"list-group\">"
				 +"<li class=\"list-group-item\"><input id=\"saveModelName\" type=\"text\" class=\"form-control\" placeholder=\"模块名称\"></li>"
				 +"<li class=\"list-group-item\"><input id=\"saveModelUrl\" type=\"text\" class=\"form-control\" placeholder=\"模块URL\"></li>"
				 +"<li class=\"list-group-item\"><input id=\"saveModelDescribe\" type=\"text\" class=\"form-control\" placeholder=\"模块描述\"></li>"
				 +"</ul>"
	Ewin.confirm({ message:content }).on(function (e) {
        if (!e) {
            return;
        }
        var data = {modelName:$("#saveModelName").val(),
         	   modelUrl:$("#saveModelUrl").val(),
        	   modelDescribe:$("#saveModelDescribe").val()
        };
        $.ajax({
            type: "post",
            url: "./system/saveModel",
            data: data,
            dataType: "json",
            async:false,
            success: function(data){
            	loadModelInfo();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            	if(XMLHttpRequest.status == 403){
        			Ewin.confirm({ message: "您当前无权限！" });
                }
             }
    	 });
	});
}
function saveRole() {
	Ewin.confirm({ message: "<input id=\"saveRoleName\" type=\"text\" class=\"form-control\" placeholder=\"角色名称\">" }).on(function (e) {
        if (!e) {
            return;
        }
        
        $.ajax({
            type: "post",
            url: "./system/saveRole",
            data: {roleName:$("#saveRoleName").val()},
            dataType: "json",
            async:false,
            success: function(data){
            	loadRoleInfo();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            	if(XMLHttpRequest.status == 403){
        			Ewin.confirm({ message: "您当前无权限！" });
                }
             }
    	 });
	});
}

function updateRole(obj) {
	Rolemodel();
	$("#submit").removeAttr("parameter");
	$("#roleName").val("");
	$("#submit").attr("parameter",obj.id);
	getModelInfo(obj.id);
	$('#myModal').modal('show');
	$('#myTab2').on('show.bs.tab', function (e) {
		var tabId = $(e.target).attr("href");
		if("#roleInfoUpdate" == tabId){
			$("#submit").attr("submitType","update");
		}else if ("#systemModel" == tabId){
			$("#submit").attr("submitType","model");
		}
	})
}

function getModelAndUrl(id) {
	$.ajax({
        type: "post",
        url: "./system/getModelAndUrlList",
        data: {modelId:id},
        dataType: "json",
        async:false,
        success: function(data){
        	$("#modelSelect1").html("");
        	$("#modelSelect2").html("");
        	$.each(data.urlList,function(i,o){
    			$("#modelSelect1").append("<option id='model_"+o.system_url_id+"' value='"+o.system_url_id+"'>"+o.url+"</option>")
        	});
        	$.each(data.urlListByMid,function(i,o){
    			$("#modelSelect2").append("<option value='"+o.system_url_id+"'>"+o.url+"</option>")
    			$("#model_"+o.system_url_id).remove();
        	});
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	if(XMLHttpRequest.status == 403){
    			Ewin.confirm({ message: "您当前无权限！" });
            }
         }
	 });
}

function updateModel(obj) {
	getModelAndUrl(obj.id);
	$("#modelName").val("");
	$("#modelURL").val("");
	$("#modelDescribe").val("");
	$("#modelSubmit").removeAttr("parameter");
	$("#modelSubmit").removeAttr("parameterType");
	$("#modelSubmit").attr("parameter",obj.id);
	$('#modelModal').modal('show');
	$("#modelName").val(StringUtil(obj.modelName));
	$("#modelURL").val(StringUtil(obj.modelURL));
	$("#modelDescribe").val(StringUtil(obj.modelDescribe));
	ModelSelect();
	$('#myTab3').on('show.bs.tab', function (e) {
		var tabId = $(e.target).attr("href");
		if("#modelInfoUpdate" == tabId){
			$("#modelSubmit").attr("submitType","update");
		}else if ("#systemModelAndURL" == tabId){
			$("#modelSubmit").attr("submitType","modelAndUrl");
			getModelAndUrl(obj.id);
		}
	})
	
}

function modelInfoSubmit(obj) {
	var parameter = $("#modelSubmit").attr("parameter");
	var submitType = $("#modelSubmit").attr("submitType");
	if("update" == submitType){
		var modelName = $("#modelName").val();
		var modelURL = $("#modelURL").val();
		var modelDescribe = $("#modelDescribe").val();
		if(modelName.length <= 0){
			Ewin.confirm({ message: "模块名称不能为空！" });
		}else if (modelURL.length <= 0){
			Ewin.confirm({ message: "模块URL不能为空！" });
		}else if (modelDescribe.length <= 0){
			Ewin.confirm({ message: "模块描述不能为空！" });
		}else{
			$.ajax({
	            type: "post",
	            url: "./system/updateModelInfo",
	            data: {modelName:modelName,
	            		modelUrl:modelURL, 
	            		modelDescribe:modelDescribe,
	            		modelId:parameter},
	            dataType: "json",
	            success: function(data){
	            			$('#modelModal').modal('hide');
	            			loadModelInfo();
	                     },
	            error: function(XMLHttpRequest, textStatus, errorThrown){
	                if(XMLHttpRequest.status == 403){
	        			Ewin.confirm({ message: "您当前无权限！" });
	                }
	             }
	        });
		}
	}else if("modelAndUrl" == submitType){
		var options = $("#modelSelect2")[0].options;
		var optionLength = options.length;
		if(optionLength <= 0){
			Ewin.confirm({ message: "模块所属URL不能为空！" });
		}else{
			var urlIds="";
			$.each(options,function(i,o) {
				urlIds+=$(o).attr("value")+";"
			})
			$.ajax({
	            type: "post",
	            url: "./system/saveModelUrlInfo",
	            data: {modelId:parameter, urlIds:urlIds},
	            dataType: "json",
	            success: function(data){
	            			$('#modelModal').modal('hide');
	                     },
	            error: function(XMLHttpRequest, textStatus, errorThrown){
	            	if(XMLHttpRequest.status == 403){
	        			Ewin.confirm({ message: "您当前无权限！" });
	                }
	             }
	        });
		}
	}
};
function roleInfoSubmit(obj) {
	var parameter = $("#submit").attr("parameter");
	var submitType = $("#submit").attr("submitType");
	if("update" == submitType){
		var roleName = $("#roleName").val();
		if(roleName.length <= 0){
			Ewin.confirm({ message: "角色名称不能为空！" });
		}else{
			$.ajax({
	            type: "post",
	            url: "./system/updateRoleInfo",
	            data: {roleName:roleName, roleId:parameter},
	            dataType: "json",
	            success: function(data){
	            			$('#myModal').modal('hide');
	            			loadRoleInfo();
	                     },
	            error: function(XMLHttpRequest, textStatus, errorThrown){
	            	if(XMLHttpRequest.status == 403){
	        			Ewin.confirm({ message: "您当前无权限！" });
	                }
	             }
	        });
		}
	}else if("model" == submitType){
		var options = $("#select2")[0].options;
		var optionLength = options.length;
		if(optionLength <= 0){
			Ewin.confirm({ message: "角色模块不能为空！" });
		}else{
			var modelIds="";
			$.each(options,function(i,o) {
				modelIds+=$(o).attr("value")+";"
			})
			$.ajax({
	            type: "post",
	            url: "./system/saveModelInfo",
	            data: {roleId:parameter, modelIds:modelIds},
	            dataType: "json",
	            success: function(data){
	            			$('#myModal').modal('hide');
	                     },
	            error: function(XMLHttpRequest, textStatus, errorThrown){
	            	if(XMLHttpRequest.status == 403){
	        			Ewin.confirm({ message: "您当前无权限！" });
	                }
	             }
	        });
		}
	}
	
}

function getModelInfo (id) {
	$.ajax({
        type: "post",
        url: "./system/getModelList",
        data: {roleId:id},
        dataType: "json",
        async:false,
        success: function(data){
        	$("#select1").html("");
        	$("#select2").html("");
        	$.each(data.modelList,function(i,o){
    			$("#select1").append("<option id='model_"+o.system_model_id+"' value='"+o.system_model_id+"'>"+o.model_name+"</option>")
        	});
        	$.each(data.modelListByRole,function(i,o){
    			$("#select2").append("<option value='"+o.system_model_id+"'>"+o.model_name+"</option>")
    			$("#model_"+o.system_model_id).remove();
        	});
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	if(XMLHttpRequest.status == 403){
    			Ewin.confirm({ message: "您当前无权限！" });
            }
         }
	 });
}



function createEditor() {
	var ue = UE.getEditor('editor',{
		maximumWords:200
	});
	function isFocus(e){
	    alert(UE.getEditor('editor').isFocus());
	    UE.dom.domUtils.preventDefault(e)
	}
	function createEditor() {
	    enableBtn();
	    UE.getEditor('editor');
	}
}

function loadModelInfo() {
	$(".appendModelTr").remove();
	$.ajax({
        type: "post",
        url: "./system/getModelList",
        data: {userId:""},
        dataType: "json",
        async:false,
        success: function(data){
        	$.each(data.modelList,function(i,o){
        		var content = "<tr class=\"appendModelTr\">"
        		+"<td>"+StringUtil(o.model_name)+"</td>"
        		+"<td>"+StringUtil(o.model_url)+"</td>"
        		+"<td>"+StringUtil(o.model_describe)+"</td>"
        		+"<td>"+StringUtil(o.create_user)+"</td>"
        		+"<td>"+StringUtil(o.create_time)+"</td>"
        		+"<td>"+StringUtil(o.update_user)+"</td>"
        		+"<td>"+StringUtil(o.update_time)+"</td>"
        		+"<td>"
        		+"<span class=\"glyphicon glyphicon-pencil pointer\" aria-hidden=\"true\" title=\"修改\" onclick=\"updateModel({'id':'"+o.system_model_id+"','type':'model','modelName':'"+StringUtil(o.model_name)+"','modelURL':'"+StringUtil(o.model_url)+"','modelDescribe':'"+StringUtil(o.model_describe)+"'})\"></span>"
        		+"<span onclick=\"deleteModel({'id':'"+o.system_model_id+"','type':'model'})\" class=\"glyphicon glyphicon-trash pointer\" aria-hidden=\"true\" title=\"删除\"></span>"
        		+"</td>"
        		+"</tr>"
    			$("#modelTable").append(content)
        	});
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	if(XMLHttpRequest.status == 403){
    			Ewin.confirm({ message: "您当前无权限！" });
            }
         }
	 });
}

function loadRoleInfo() {
	$(".appendTr").remove();
	$.ajax({
        type: "post",
        url: "./user/getUserRoleInfo",
        data: {userId:""},
        dataType: "json",
        async:false,
        success: function(data){
        	$.each(data,function(i,o){
        		var content = "<tr class=\"appendTr\">"
        		+"<td>"+StringUtil(o.role_name)+"</td>"
        		+"<td>"+StringUtil(o.create_user)+"</td>"
        		+"<td>"+StringUtil(o.create_time)+"</td>"
        		+"<td>"+StringUtil(o.update_user)+"</td>"
        		+"<td>"+StringUtil(o.update_time)+"</td>"
        		+"<td>"
        		+"<span class=\"glyphicon glyphicon-pencil pointer\" aria-hidden=\"true\" title=\"修改\" onclick=\"updateRole({'id':'"+o.role_id+"','type':'role'})\"></span>"
        		+"<span onclick=\"deleteRole({'id':'"+o.role_id+"','type':'role'})\" class=\"glyphicon glyphicon-trash pointer\" aria-hidden=\"true\" title=\"删除\"></span>"
        		+"</td>"
        		+"</tr>"
    			$("#roleTable").append(content)
        	});
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
        	if(XMLHttpRequest.status == 403){
    			Ewin.confirm({ message: "您当前无权限！" });
            }
         }
	 });
}
function StringUtil(str) {
	if(null == str || "" == str){
		return "";
	}else{
		return str;
	}
}

function deleteModel(obj) {
	var modelId = obj.id;
	Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            type: "post",
            url: "./system/deleteModelByid",
            data: {modelId:modelId},
            dataType: "json",
            async:false,
            success: function(data){
            	loadModelInfo();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            	if(XMLHttpRequest.status == 403){
        			Ewin.confirm({ message: "您当前无权限！" });
                }
             }
    	 });
	})
}

function deleteRole(obj) {
	var roleId = obj.id;
	Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
        if (!e) {
            return;
        }
        $.ajax({
            type: "post",
            url: "./system/deleteRoleByid",
            data: {roleId:roleId},
            dataType: "json",
            async:false,
            success: function(data){
            	loadRoleInfo();
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            	if(XMLHttpRequest.status == 403){
        			Ewin.confirm({ message: "您当前无权限！" });
                }
             }
    	 });
	})
	
}

function ModelSelect(){
	//移到右边
    $('#modelAdd').click(function() {
    //获取选中的选项，删除并追加给对方
        $('#modelSelect1 option:selected').appendTo('#modelSelect2');
    });
    //移到左边
    $('#modelRemove').click(function() {
        $('#modelSelect2 option:selected').appendTo('#modelSelect1');
    });
    //全部移到右边
    $('#modelAdd_all').click(function() {
        //获取全部的选项,删除并追加给对方
        $('#modelSelect1 option').appendTo('#modelSelect2');
    });
    //全部移到左边
    $('#modelRemove_all').click(function() {
        $('#modelSelect2 option').appendTo('#modelSelect1');
    });
    //双击选项
    $('#modelSelect1').dblclick(function(){ //绑定双击事件
        //获取全部的选项,删除并追加给对方
        $("option:selected",this).appendTo('#modelSelect2'); //追加给对方
    });
    //双击选项
    $('#modelSelect2').dblclick(function(){
       $("option:selected",this).appendTo('#modelSelect1');
    });
}

function Rolemodel(){
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