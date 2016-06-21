$(function(){
	//更多搜索
	$("body").on("click","#search",function(){
		query();		
	});
	$("#moresearch").morequery({
    	"title":"",
    	"content":$("#searchpanel").html()    	
	});
	//条件查询
    $("#query").bind("click", query);
});
function query() {
	var name = $("#name").val();
	var fileName = $("#fileName").val();
	if(fileName == undefined){
		fileName = "";
	}
	 var url = context + "/service/lemmas/data?name="+name+"&fileName="+fileName;
	 url=encodeURI(encodeURI(url,"utf-8")); 
	 oTable.ajax.url(url).load();
}
//checkbox全选 
function selectAll(obj,iteName){
	  if (obj.checked) {
    	$("input[name='checkList']").each(function(){this.checked=true;}); 
    } else {
    	$("input[name='checkList']").each(function(){this.checked=false;}); 
    }
}


//获取选中的复选框的记录
function getCheckBoxValue(attrKey){	 
		var confCheckBox = $("input:checkbox[name="+attrKey+"]");
		var selectedValue = "";
		for (var i = 0; i < confCheckBox.length; i++) {
			if (confCheckBox[i].checked) {
				if ("" == selectedValue) {
					selectedValue = confCheckBox[i].value;
				} else {
					
					selectedValue = selectedValue + "," + confCheckBox[i].value;
				}
			}
		}
		return selectedValue;
	}