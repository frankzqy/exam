	var topMenu = [{
		appCode: "-1",
		id: "example011",
		isLeaf: "true",
		text: "首页",
		url: "/jsp/public/rewardmain.jsp",
},{
			appCode: "-1",
			id: "example011",
			isLeaf: "true",
			text: "薪资计算器",
			url: "/jsp/exam/salarycalculator.jsp",
	},{
		appCode: "-1",
		id: "example012",
		isLeaf: "true",
		text: "员工月工资",
		url: "/jsp/exam/staffTax.jsp",
},{
	appCode: "-1",
	id: "example013",
	isLeaf: "true",
	text: "年终奖最优避税",
	url: "/jsp/exam/bonus.jsp",
}];
	//加载工作区页面
	function loadUrl(data) {
		var url = "";
		if(data.url!=null&&data.url!=undefined&&data.url!=""){
			$('.ue-right-top').text(data.text || data.name);
	 		url = context + data.url;
	 		$('#mainFrame').attr('src', url);
		}
	  }

	//模块菜单视图
    var topMenuView = {
    	//data
    	menuList :  ko.observableArray(topMenu),
    	hideList : ko.observableArray([]),
    	actionUrl : ko.observable("/service/home/topmenu"),
    	viewId :ko.observable("topMenuView"),
	 };
	 ko.applyBindings(topMenuView,$("#topMenu")[0]);
