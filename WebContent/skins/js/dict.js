	var topMenu = [{
			appCode: "-1",
			icon: "platform/img/case.png",
			id: "example01",
			isLeaf: "false",
			text: "词典管理",
			url: "/jsp/dict/dictManager.jsp"
	}];
	var leftMenu = [{
			appCode: "-1",
			id: "example011",
			isLeaf: "true",
			text: "词典管理",
			url: "/jsp/dict/dictManager.jsp"
	},{
		appCode: "-1",
		id: "example012",
		isLeaf: "true",
		text: "词条管理",
		url: "/jsp/dict/lemma.jsp"
},{
	appCode: "-1",
	id: "example013",
	isLeaf: "true",
	text: "分词管理",
	url: "/jsp/dict/participle.jsp"
}];
	var favoriteMenu = [];
	//指定初始加载的模块
	var initModelName="词典管理";
	var favoritesModel = {name:"我的收藏",
						  iconPath:assetPath + "/skins/skin/platform/img/collect.png",
						  url:'/jsp/public/intrudoce.jsp'};
	//用户信息视图
	var userInfoView = {
		//data
    	userName :  ko.observable("系统管理员"),
    	//behaviors
    	userUpdate:function(data) {
    		userName(data.userName);
 		},
	};
//    ko.applyBindings(userInfoView,$("#userInfo")[0]);
	
	function menuRender() {
    	// beautify scroller
		$('.ue-vmenu').slimScroll({
			height: $('.ue-vmenu').height()
		});
		// initialize menu
		$('.ue-vmenu').vmenu({
			autostart: false
		});
    }
	
	function afterRender(){
		  var leafItem =$('.fa.fa-star-o.ue-vmenu-icon-r').parent().parent();
			for(var i= 0; i < leafItem.length; ++i){
        		for(var j = 0; j < favoriteMenu.length; ++j){
        			if(leafItem[i].id == favoriteMenu[j].menuId){
        				$(leafItem[i]).find("i:last-child")[0].className = "fa fa-star ue-vmenu-icon-r";
        				$(leafItem[i]).find("i:last-child")[0].title = "取消收藏"; 
        			}
        		}
        	}
	  }
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
    	menuList :  ko.observableArray([]),
    	hideList : ko.observableArray([]),
    	actionUrl : ko.observable("/service/home/topmenu"),
    	viewId :ko.observable("topMenuView"),
    	//behaviors
    	showMore:function() {
   		 		if( $("#topMore").text()=="更多"){
	   		 		this.menuList(this.menuList().concat(this.hideList()));
	   		 	    $("#topMore").text("收起");
   		 		}else if($("#topMore").text()=="收起"){
   		 			this.menuList(this.menuList().slice(0,6));
   		 			$("#topMore").text("更多");
   		 		}
   		    },
		getSubMenu: function (data){ 
				leftMenuView.pMenuName(data.text);
				leftMenuView.pMenuId(data.id);
				var iconPath = topMenuView.topMenuIcon(data)
				leftMenuView.PMenuIcon(iconPath);
				leftMenuView.pUrl(data.url);
				leftMenuView.menuList([{
					appCode: "-1",
					id: "example011",
					isLeaf: "true",
					text: "词典管理",
					url: "/jsp/dict/dictManager.jsp"
			},{
				appCode: "-1",
				id: "example012",
				isLeaf: "true",
				text: "词条管理",
				url: "/jsp/dict/lemma.jsp"
		},{
			appCode: "-1",
			id: "example013",
			isLeaf: "true",
			text: "分词管理",
			url: "/jsp/dict/participle.jsp"
		}]);
				loadUrl(data);
			    menuRender();
    		    afterRender();
		    },
        topMenuIcon: function (menuItem){
        	return menuItem.icon ? assetPath+"/skins/skin/"+menuItem.icon : assetPath + "/skins/skin/platform/img/bsp.png";
        }
	 };
	 ko.applyBindings(topMenuView,$("#topMenu")[0]);
	//收藏菜单视图 
    var favoritesMenuView = {
    		//data
    		menuList: ko.observableArray([]),
    		hideList : ko.observableArray([]),
    		actionUrl : ko.observable("/service/home/menu/favourite"),
    		viewId :ko.observable("favoritesMenuView"),
   			//behaviors
 			showMore:function() {
   		 		if( $("#favoritesMore").text()=="更多"){
	   		 		this.menuList(this.menuList().concat(this.hideList()));
	   		 	    $("#favoritesMore").text("收起");
   		 		}else if($("#favoritesMore").text()=="收起"){
   		 			this.menuList(this.menuList().slice(0,6));
   		 			$("#favoritesMore").text("更多");
   		 		}
   		    },
   		    deleteFavorite:function(){
   		    	alert("delete!"); 		    	
   		    }
    }; 
  //  ko.applyBindings(favoritesMenuView,$("#favoritesMenu")[0]);
   //左边菜单视图
    var leftMenuView = {
    		//data
    		pMenuId : ko.observable(""),
    		pMenuName : ko.observable(initModelName),
    		PMenuIcon : ko.observable(favoritesModel.iconPath),
    		menuList: ko.observableArray([]),
   			actionUrl : ko.observable("/service/home/menu/submenu"),
   			viewId :ko.observable("leftMenuView"),
   			pUrl : ko.observable('/jsp/public/intrudoce.jsp')
    }; 
    ko.applyBindings(leftMenuView,$("#leftMenu")[0]);
    

    /**
     * 获取菜单项
     * @param {ModelView} view 视图模型
     * @param {String} domId 展示模型的dom区域id
     */
    function getMenu(view,domId){
    	var self = view;
    	var url = context + view.actionUrl();
    	
    	var moreNode = $("#"+domId).find(".more");
    	if(moreNode.length>1){
    		moreNode = $(moreNode[moreNode.length-1]);
    	}
    	var menuItem;
    	if(view.viewId()=="topMenuView"){
    		menuItem= topMenu;
    	}else{
    		menuItem= favoriteMenu;
    	}
		if(menuItem.length == 0){
			moreNode.removeClass("more");
			if(view.viewId()=="topMenuView"){
				moreNode.text("无应用");
        	}else if(view.viewId()=="favoritesMenuView"){
        		moreNode.css({"text-align":'center',"cursor":'default',"background-color":'#ffffff'});
        		moreNode.text("无收藏");
        	}
    	}else if(menuItem.length > 6){
    		self.hideList(menuItem.slice(6));
    		menuItem=menuItem.slice(0,6);
    		if(view.viewId()=="favoritesMenuView"){
    			$("#favoritesMore").css({"cursor":'pointer',"background-color":'#eee'});
    		}
    		moreNode.text("更多");
    	}else{
    		moreNode.removeClass("more");
    		moreNode.text("");
    	}
    	self.menuList(menuItem); 
    	if(view.viewId()=="topMenuView"){
    		topMenu = menuItem;
    	}else if(view.viewId()=="favoritesMenuView"){
    		favoriteMenu = menuItem;
//    		leftMenu = menuItem;
    	}	
    }
    //页面初始化操作：事件绑定及数据加载
    $(function() {
    	$('#favoritesMenu').hover(function(){
    		if(favoriteMenu.length ==0){
    			getMenu(favoritesMenuView,"favoritesMenu");  
    		}
    		$(this).children().show();
    	}, function() {
    		$(this).children().hide();
    		$($(this).children()[0]).show();
    	});
    	$('#topMenu').hover(function(){
     	    if(topMenu.length ==0){
    			getMenu(topMenuView,"topMenu");  
    		}
     	   $(this).children().show();
    	},function() {
    		$(this).children().hide();
    		$($(this).children()[0]).show();
    	});
//    	$('#userInfo').hover(function(){
//    		$($(this).children("div")).show(); 		
//    	},function() {
//    		$($(this).children("div")).hide();		
//    	});
    	//加载指定的初始模块
    	loadData();
    });
    
    /**
     * 保存对收藏菜单的操作
     * @param index 在leftMenu数组中的下标
     * @param data
     * @param event
     */
    function saveFavorites(index,data,event){
    	var url = context + favoritesMenuView.actionUrl();
    	var menuInfo = "";
    	var optType = "";
    	var msg = "";
    	var continueFlag = true;
    	//不同浏览器下获取当前点击元素
    	var clickSymbol = event.currentTarget;
        if(clickSymbol==null)clickSymbol = event.srcElement;
        if(clickSymbol==null)clickSymbol = event.originalTarget;
    	if(clickSymbol.className.indexOf("fa-star")>0){
    		optType = "cancel";
    		msg = "确定取消收藏当前菜单？";
    	}
    	if(clickSymbol.className.indexOf("fa-star-o")>0){
    		optType = "add";
    	}
    	if(clickSymbol.className.indexOf("fa-times")>0){
    		optType = "delete";
    		msg = "确定删除当前的收藏菜单？";
    	}
    	if(optType=="add"){
    		url += "/add";
    		menuInfo = {"menuId":data.id, "name":data.text,"parentId":leftMenuView.pMenuId(),"url":data.url};
    	}else{
    		url += "/delete";
    		continueFlag=false;
    		if(optType == "cancel")
    			menuInfo = {"menuId":data.id};
    		else
    			menuInfo = {"menuId":data.menuId};
    	}
    	var arg = {url:url,optType:optType,index:index};
    	if(!continueFlag){
        	$.dialog({
        		type:'confirm',
    		    content: msg,
    		    ok: function () {sendFavorRequest(event,menuInfo,arg);},
    		    cancel:function(){},
    		    autofocus: true
    	    }).showModal();
        	return;
    	}else{
    		sendFavorRequest(event,menuInfo,arg);
    	}
    }
    /**
     * 获取菜单项
     * @param {event} event 
     * @param {String} domId 展示模型的dom区域id
     */
    function sendFavorRequest(event,menuInfo,arg){
    	$.ajax({
            url:arg.url,
            data:menuInfo,
            type:"post",
            async:false,
            success:function(data){
            	if(data=="error") return;
            	//不同浏览器下获取当前点击元素
                var clickSymbol = event.currentTarget;
                if(clickSymbol==null)clickSymbol = event.srcElement;
                if(clickSymbol==null)clickSymbol = event.originalTarget;
                switch(arg.optType){
                case "add":
                	clickSymbol.className = "fa fa-star ue-vmenu-icon-r";
                	clickSymbol.title = "取消收藏";
            		var item = eval("(" + data + ")");
                	favoriteMenu.push(item);
                    break;
                case "cancel":
                	clickSymbol.className = "fa fa-star-o ue-vmenu-icon-r";
                	clickSymbol.title = "收藏";
            		favoriteMenu.removeByMenuId(menuInfo.menuId);
                    break;
                case "delete":
                	favoriteMenu.removeByMenuId(menuInfo.menuId);
                	if(leftMenuView.pMenuName()=="我的收藏"){
                		leftMenu.removeByIndex(arg.index);
                    	leftMenuView.menuList(leftMenu);
                	}else{
                		var leafNodes = $(".leaf").parent().parent();
                		for(var i = 0 ; i < leafNodes.length; i++){
                			if(leafNodes[i].id==menuInfo.menuId){
                				$(leafNodes[i]).find(".fa.fa-star.ue-vmenu-icon-r").removeClass("fa fa-star ue-vmenu-icon-r").addClass("fa fa-star-o ue-vmenu-icon-r");
                				break;
                			}
                		}
                	}
                    break;
                default:
                    break;
               }
                if(!$("#favoritesMore").hasClass("more"))
            		$("#favoritesMore").addClass("more");
                
                if(favoriteMenu.length == 0){
                	favoritesMenuView.menuList(favoriteMenu);
                	$("#favoritesMore").removeClass("more");
                	$("#favoritesMore").css({"text-align":'center',"cursor":'default',"background-color":'#ffffff'});
            		$("#favoritesMore").text("无收藏");
            	}else if(favoriteMenu.length > 6){
            		favoritesMenuView.hideList(favoriteMenu.slice(6));
            		favoritesMenuView.menuList(favoriteMenu.slice(0,6));
            		$("#favoritesMore").css({"cursor":'pointer',"background-color":'#eee'});
            		$("#favoritesMore").text("更多");
            	}else{
            		favoritesMenuView.menuList(favoriteMenu);
            		$("#favoritesMore").removeClass("more");
            		$("#favoritesMore").text("");
            	}
            },
            error:function(){
            	$.dialog({
        	        type: 'alert',
        	        content: '操作出错！',
        	        autofocus: true        	        
        	    }).showModal();
              }
        });
    }
    
    function logout(){
    	$.dialog({
			type: 'confirm',
		    content: '您确定要退出系统吗？',
		    ok: function () {document.location.href= context + "/logout";},
		    cancel: function () {},
		    autofocus: true
	    }).showModal();
    }    
    
	Array.prototype.removeByIndex=function(index){
        if(isNaN(index)||index>=this.length){
            return false;
        }
        for(var i=0,n=0;i<this.length;i++){
            if(this[i]!=this[index]){
                this[n++]=this[i];
            }
        }
        this.length-=1;
    };
    
	Array.prototype.removeByMenuId=function(menuId){
		for(var i = 0 ; i < this.length; i++){
			if(this[i].menuId==menuId){
				this.removeByIndex(i);
				break;
			}
		}
    };
    
    function toggleSide() {
    	if (!$('.ue-menu-left').data('isClose')) {
        	$('.ue-menu-right').css({'border-left':'1px solid #ddd'}).animate({left:'0px'}, "slow");
        	$('.ue-menu-left').css({'z-index':'auto'});
        	$('.ue-menu-left').data('isClose', true);
    	} else {
        	$('.ue-menu-right').css({'border-left':'none'}).animate({left:'260px'}, "slow");
        	$('.ue-menu-left').data('isClose', false);
    	}
    }
    
    function showSearch(searchImg){
    	$("#searchview").show();
    	$(searchImg).hide();
    	
    }
    
    function searchCancel(){
    	$("#searchform-input").val("");
    	$("#searchview").hide();
    	$("#searchImg").show();
    }
    //查询
    function doQuery(){
    	var menuName = $("#searchform-input").val();
    	
      	$.ajax({
            url:context+"/service/home/menu/query",
            data:{menuName:menuName},
            type:"post",
            async:false,
            success:function(data){
            	leftMenuView.menuList([]);
            	leftMenuView.pMenuName("查询结果");
            	
            	var menuArray = eval(data);
            	if(menuArray.length!=0){
            		leftMenuView.menuList(menuArray);
                	menuRender();
    	    		afterRender();
            		loadUrl(menuArray[0]);
            	}
            }
        });
    	
    }
    
    function query(e){
   	  var ev= window.event||e;
   	  //回车键
   	  if (ev.keyCode == 13) {
      //执行搜索
      doQuery();
   	  }
    }
    
    function showFavorites(data){
//		leftMenuView.pMenuName("我的收藏"); 
//		leftMenuView.PMenuIcon(favoritesModel.iconPath);
//	  	leftMenuView.menuList(favoriteMenu);
//		leftMenuView.pUrl(favoritesModel.url);
//		leftMenu = favoriteMenu.slice(0,favoriteMenu.length);
//	    menuRender();
//	    afterRender();
//	    if(data.url!=undefined){
//	    	favoritesModel.url = data.url;
//	    	favoritesModel.name = data.name;
//	    }else{
//	    	favoritesModel.url = '/jsp/public/intrudoce.jsp';
//	    	favoritesModel.name = "我的收藏";
//	    }
//		loadUrl(favoritesModel);
    }
    //根据initModelName加载模块
    function loadData(){
    	if(initModelName==""||initModelName==null||initModelName=="我的收藏"){
    		getMenu(favoritesMenuView,"favoritesMenu");  
    		showFavorites();
    	}else{
    		getMenu(topMenuView,"topMenu"); 
    		for(var i=0; i < topMenu.length; i++){
    			if(topMenu[i].text==initModelName){
    				topMenuView.getSubMenu(topMenu[i]);
    				break;
    			}
    		}
    	}
    }