(function($){
	function crateCommentInfo(obj){
		if(typeof(obj.time) == "undefined" || obj.time == ""){
			obj.time = getNowDateFormat();
		}
		var el = "<div class='comment-info'><div class='comment-right'><h3>"+obj.replyName+"</h3>"
				+"<div class='comment-content-header'><span><i class='glyphicon glyphicon-time'></i>"+obj.time+"</span>";
		el = el+"</div><p class='content'>"+obj.content+"</p><div class='comment-content-footer'><div class='row'><div class='col-md-10'>";
		el = el + "</div><div class='col-md-2'><span class='reply-btn' messageId = '"+obj.id+"' >回复</span></div></div></div><div class='reply-list'>";
		if(obj.replyBody != "" && obj.replyBody.length > 0){
			var arr = obj.replyBody;
			for(var j=0;j<arr.length;j++){
				var replyObj = arr[j];
				el = el+createReplyComment(replyObj);
			}
		}
		el = el+"</div></div></div>";
		return el;
	}
	
	//返回每个回复体内容
	function createReplyComment(reply){
		var replyEl = "<div class='reply'><div><a href='javascript:void(0)' class='replyname'>"+reply.replyName+"</a>:<a href='javascript:void(0)'>@"+reply.beReplyName+"</a><span>"+reply.content+"</span></div>"
						+ "<p><span>"+reply.time+"</span> <span class='reply-list-btn' messageId = '"+reply.replyId+"'>回复</span></p></div>";
		return replyEl;
	}
	function getNowDateFormat(){
		var nowDate = new Date();
		var year = nowDate.getFullYear();
		var month = filterNum(nowDate.getMonth()+1);
		var day = filterNum(nowDate.getDate());
		var hours = filterNum(nowDate.getHours());
		var min = filterNum(nowDate.getMinutes());
		var seconds = filterNum(nowDate.getSeconds());
		return year+"-"+month+"-"+day+" "+hours+":"+min+":"+seconds;
	}
	function filterNum(num){
		if(num < 10){
			return "0"+num;
		}else{
			return num;
		}
	}
	function loadReplyTextarea() {
		var textArea = $("#MessageReplyContent");
		var word = $("#replyWord");
		//调用
		statInputNum(textArea, word);
		$(".replybox").click(function(e){
			e.stopPropagation();
		});
	}
	function replyClick(el,e){
		e.stopPropagation();
		$(".replybox").remove();
		el.parent().parent().append("<div class='replybox wordCount'><textarea cols='80' rows='50' placeholder='来说几句吧......' class='mytextarea' id='MessageReplyContent'></textarea><span class='wordwrap'><var id='replyWord' class='word'>500</var>/500</span><span class='send' messageId = '"+$(el).attr("messageid")+"'>发送</span></div>")
		.find(".send").click(function(event){
			e.stopPropagation();
			var content = $("#MessageReplyContent").val();
			if(content != ""){
				var parentEl = $(this).parent().parent().parent().parent();
				var obj = new Object();
				obj.replyName=username;
				obj.id=$(this).attr("messageid");
				if(el.parent().parent().hasClass("reply")){
					obj.beReplyName = el.parent().parent().find("a:first").text();
				}else{
					obj.beReplyName=parentEl.find("h3").text();
				}
				obj.content=content;
				obj.time = getNowDateFormat();
				if(obj.replyName == obj.beReplyName){
					Ewin.confirm({ message: "不能自己回复自己！" });
					return;
				}
				
				$.ajax({
			        type: "post",
			        url: "../page/saveMessageReply",
			        data: obj,
			        dataType: "json",
			        success: function(data){
			        	obj.replyId = data;
			        	var replyString = createReplyComment(obj);
			        	parentEl.find(".reply-list").append(replyString);
						$(".replybox").remove();
			        },
			        error: function(XMLHttpRequest, textStatus, errorThrown){
			            
			         }
				 });
			}else{
				alert("回复空内容");
			}
		});
		loadReplyTextarea();
	}
	
	
	$.fn.addCommentList=function(options){
		var defaults = {
			data:[],
			add:""
		}
		var option = $.extend(defaults, options);
		//加载数据
		if(option.data.length > 0){
			var dataList = option.data;
			var totalString = "";
			for(var i=0;i<dataList.length;i++){
				var obj = dataList[i];
				var objString = crateCommentInfo(obj);
				totalString = totalString+objString;
			}
			$(this).append(totalString).find(".reply-btn").click(function(e){
				if($(this).parent().parent().find(".replybox").length > 0){
					$(".replybox").remove();
				}else{
					$(".replybox").remove();
					replyClick($(this),e);
				}
			});
			$(".reply-list-btn").click(function(e){
				if($(this).parent().parent().find(".replybox").length > 0){
					$(".replybox").remove();
				}else{
					$(".replybox").remove();
					replyClick($(this),e);
				}
			})
		}
		
		//添加新数据
		if(option.add != ""){
			obj = option.add;
			var str = crateCommentInfo(obj);
			$(this).prepend(str).find(".reply-btn").click(function(e){
				replyClick($(this),e);
			});
		}
	}
})(jQuery);