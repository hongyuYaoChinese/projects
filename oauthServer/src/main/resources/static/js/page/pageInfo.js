$(function(){
	load();
	messageLoad();
	loadTextarea();
	$(document).click(function(){
		$(".replybox").remove();
	 });
})
function messageFun() {
	window.location.href='../page/'+$("#pageId").html();
}
function loadTextarea() {
	var textArea = $("#MessageContent");
	var word = $("#word");
	//调用
	statInputNum(textArea, word);
}
function statInputNum(textArea, numItem) {
	var max = numItem.text(),
	curLength;
	textArea[0].setAttribute("maxlength", max);
	curLength = textArea.val().length;
	numItem.text(max - curLength);
	textArea.on('input propertychange', function() {
		numItem.text(max - $(this).val().length);
	});
}

function messageLoad()  {
	$.ajax({
        type: "post",
        url: "../page/getMessageInfo",
        data: {pageId:$("#pageId").html()},
        dataType: "json",
        success: function(data){
        	$(".comment-list").addCommentList({data:data,add:""});
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            
         }
	 });
	
}
function submitMessage() {
	var obj = new Object();
	obj.replyName=username;
	obj.content=$("#MessageContent").val();
	obj.id = $("#pageId").html();
	obj.time = getNowDateFormat();
	obj.replyBody = "";
	$.ajax({
        type: "post",
        url: "../page/saveMessage",
        data: obj,
        dataType: "json",
        success: function(data){
        	$(".comment-list").addCommentList({data:[],add:obj});
        	$("#MessageContent").val("");
        	$("#word").html("500");
        	$("#messageSize").html(parseInt($("#messageSize").html())+1)
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            
         }
	 });
}

function load() {
	$.ajax({
        type: "post",
        url: "../page/getPageInfo",
        data: {pageId:$("#pageId").html()},
        dataType: "json",
        success: function(data){
        	var clicks = 0;
        	if(data.clicks != null || data.clicks != ''){
        		clicks = data.clicks;
        	}
        	$("#title").html(data.title);
        	$("#info").html("创建人："+data.create_user+"&nbsp;&nbsp;浏览次数："+clicks+"次&nbsp;&nbsp;发布时间："+data.create_time);
        	$("#content").html(data.content);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            
         }
	 });
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