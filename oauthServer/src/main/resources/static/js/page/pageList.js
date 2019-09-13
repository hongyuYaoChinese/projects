$(function(){
	load(1);
})

function load(pageNum){
	$.ajax({
        type: "post",
        url: "../page/getPageList",
        data: {pageNum:pageNum,
        	   pageSize:"5"},
        dataType: "json",
        async:false,
        success: function(data){
        	$("#listGroup").html("");
        	$("#paging").html("");
        	
        	$.each(data.items,function(i,o){
	        	var content = "	<a onclick=\"pageInfo({'id':'"+o.id+"'})\"  class=\"list-group-item pointer\">"+ 
	        		"<h4 class=\"list-group-item-heading\">"+ 
	        		o.title+
	        		"&nbsp;<span class=\"date\">"+o.create_time+"</span>"+ 
	        		"</h4>"+ 
	        		"<p class=\"list-group-item-text\" style=\"\">"+ 
	        		o.contentStr+
	        		"</p>"+ 
	        		"</a>"
    			$("#listGroup").append(content);
        	});
        	
        	var pagingContent1 = "<ul class=\"pagination pull-right\">" + 
    		"<li onClick='leftClick(this)' id='left'><a href=\"javascript:;\" aria-label=\"Previous\"> <span" + 
    		"aria-hidden=\"true\">&laquo;</span>" + 
    		"</a></li>";
    		
    		
        	for(var i=1;i<=data.totalPage;i++){
        		var pagingContent2 = "";
        		if(i == data.currentPage){
        			pagingContent2 = "<li class = 'active' ><a id='active' href=\"javascript:;\">"+i+"</a></li>";
        		}else{
        			pagingContent2 = "<li><a href=\"javascript:;\" onClick='pageClick(this)'>"+i+"</a></li>";
        		}
        		pagingContent1+=pagingContent2;
        	}
    		 var pagingContent3 = pagingContent1+"<li onClick='rightClick(this)' id='right'><a href=\"javascript:;\" aria-label=\"Next\"> <span" + 
    		"aria-hidden=\"true\">&raquo;</span>" + 
    		"</a></li>" + 
    		"</ul>";
    		 $("#paging").append(pagingContent3);
    		 if(data.currentPage == data.totalPage){
    			 $("#right").attr("class","disabled");
    		 }
    		 if(data.currentPage == 1){
    			 $("#left").attr("class","disabled");
    		 }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            
         }
	 });
}
function pageInfo(obj) {
	var pageId = obj.id;
	window.open("../page/pageInfo?pageId="+pageId);
}
function pageClick(obj) {
	var pageNum = $(obj).html();
	load(pageNum);
}
function rightClick(obj){
		if($(obj).attr("class") != 'disabled'){
			var currentPage = $("#active").html();
			 var nextPage = parseInt(currentPage)+1;
			 load(nextPage);
		}
}
function leftClick(obj){
	if($(obj).attr("class") != 'disabled'){
		 var currentPage = $("#active").html();
		 var nextPage = parseInt(currentPage)-1;
		 load(nextPage);
	}
}