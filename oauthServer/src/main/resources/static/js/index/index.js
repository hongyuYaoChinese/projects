$(function() {
	rotationInit();//轮播图初始化

	
});
function pageInfo(obj) {
	var pageId = $(obj).attr("pageId");
	window.open("./page/pageInfo?pageId="+pageId);
}
function rotationInit() {
	$('#rotation').flexslider({
		animation : "slide",
		direction : "horizontal",
		easing : "swing"
	});
}
