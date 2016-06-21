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
	$("#staffName").bind('keypress',function(event){
	    if(event.keyCode == "13")    
	    {
	    	query();
	    }
	});
});
function query() {
	var staffName = $("#staffName").val();
	var staffId = $("#staffId").val();
	if(staffId == undefined){
		staffId = "";
	}
	 var url = context + "/service/bonus/data?staffName="+staffName+"&staffId="+staffId;
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