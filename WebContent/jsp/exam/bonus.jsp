<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/tags/loushang-web" prefix="l"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>年终奖最优避税</title>

    <!-- 需要引用的CSS -->
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/jsp/public/images/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/bootstrap.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/font-awesome.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/ui.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/form.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='salary/css/portal.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/datatables.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<l:asset path='demo/css/demo.css'/>" />
    <!-- 需要引用的JS -->
    <script  type="text/javascript" src="<l:asset path='knockout.js'/>"></script>
    <script type="text/javascript" src="<l:asset path='jquery.js'/>"></script>
    <script type="text/javascript" src="<l:asset path='bootstrap.js'/>"></script>
    <script type="text/javascript" src="<l:asset path='form.js'/>"></script>
    <script  type="text/javascript" src="<l:asset path='datatables.js'/>"></script>
    <script  type="text/javascript" src="<l:asset path='ui.js'/>"></script>
    <script  type="text/javascript" src="<l:asset path='bonus.js'/>"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<l:asset path='html5shiv.js'/>"></script>
      <script src="<l:asset path='respond.js'/>"></script>
    <![endif]-->
	<style>
	  .container {
	  	width: 100%;
	  },
	  body{
	  overflow:auto !importan;  
	  }
	  .my_number{
	  	text-align: right !important;
	  }
	  .my_text{
	  	text-align: center !important;
	  }
	  .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
	  	padding-right:10px;
	  }
	</style>
    <script id="searchpanel" type="text/html">
	  <div id="searchtemp">
		<div class="form-group">
			<label for="staffId" class="text-name">工号:</label> 
			<input type="text" class="form-control ue-form" id="staffId" placeholder="工号">
			<button id="search" class="btn ue-btn-primary">搜索</button>
		</div>
      <div>
	</script>
    <script type="text/javascript">
  //项目上下文
	var context="<%=request.getContextPath()%>";
	$(document).ready(function() {
     	//初始化datatable
	    oTable= initTable();
	    $("#add").bind("click",add);
	    $("#edit").bind("click",edit);
	    $("#delete").bind("click",del);
	    $("#export").bind("click",download);
	    $("#exportTemplate").bind("click",downloadTemplate);
	    $("#refresh").bind("click",refresh);
	    //增加
	    function add(){
			window.location.href = context + "/jsp/exam/addBonus.jsp";
		}
	    //修改
		function edit(){
			var ids = getCheckBoxValue("checkList");
			var recordId = ids.split(",");
			
			if(ids==""||recordId.length==0||recordId.length > 1){
				$.dialog({
					type: 'alert',
					content: '请选择一条记录！'
				});
				return;
			}
			window.location.href = context + "/service/bonus/edit/"+recordId;
		}
	    //删除
		function del(){
			var recordIds = getCheckBoxValue("checkList");
			if(recordIds.length < 1){
				$.dialog({
					type: 'alert',
					content: '请选择一条记录！'
				});
				return;
			}
			$.dialog({
				type: 'confirm',
				content: '确认删除选中记录?',
				ok: function(){window.location.href=context+"/service/bonus/delete/"+recordIds;},
				cancel: function(){}
			});
		}
		 
		 

		//导入文件
		$("#import").on("click",function(){
	    		$.dialog({
	    			type: 'iframe',
					url: context + '/jsp/exam/importBonus.jsp',
					title: '导入员工年终奖',
					width: 500,
					height: 300,
					onshow: function () {
					},    		    
					onclose: function () {
						 oTable.ajax.reload();
					},
					oniframeload: function () {
					}
	    	});
		});
		 
	  });
	//导出年终奖模板
	function downloadTemplate(){
		window.location.href = context + "/service/download/xsl/bonusTemplate";
	}
	
	//导出年终奖最佳避税
	function download(){
		window.location.href = context + "/service/download/xsl/bonus";
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
  
  //刷新--重新加载列表数据
  function refresh(){
		 oTable.ajax.url(context + "/service/bonus/data").load();
  }
	 //datatables初始化对象
	function initTable(){
		var table = $("#bonusList").dtable({      
			  "ajax" : {
				  "url" : context + "/service/bonus/data",
				  "error" : function(data, textstatus) {
				  	  alert(data.responseText);
				  }
			  },
			  "scrollY": "200px",
			   "scrollCollapse": "true",
			  "paging": false,
		      "columns": [
		                  {"data": "id","bSortable": false},
		                  {"data": "staffId","className":"my_text","bSortable": false},
		                  {"data": "staffName","bSortable": false,"className":"my_text"},
		                  {"data": "wages","className":"my_number"},
		                  {"data": "totalBonus", "bSortable":false,"className":"my_number"},
		                  {"data": "totalTax", "bSortable":false,"className":"my_number"},
		                  {"data": "afterTaxBonus", "bSortable":false,"className":"my_number"},
		                  {"data": "bonus1", "bSortable":false,"className":"my_number"},
		                  {"data": "bonus2", "bSortable":false,"className":"my_number"},
		                  {"data": "bonus3", "bSortable":false,"className":"my_number"},
		                  {"data": "escapeTax","bSortable":false,"className":"my_number"},
		                  {"data": "afterEscapeBonus","bSortable":false,"className":"my_number"}
		                ],
		      "columnDefs":[
		    		      {
		    		        "targets":0,	    		       
		    		        "data":"id",
		    		        "render": function(data, type, full) {
		    		           return '<input type="checkbox" value="' + data + '" title="' + data + '" id="checkbox" name="checkList"/>';
		    		         }
		    		      }
		    		    ],
		    });
		
		return table;
	}
	</script>
  </head>
  <body >
	<!-- 页面结构 -->
		<div class="iframe-banner">
		   <div class="instruction">
		      <span>明明白白发工资</span>
              <span class="ms">快速、便捷、准确、贴心</span>
		   </div>
	    </div>
	    <div class="topdist"></div>
		<div class="container">
		     <div class="row">
			  <form class="form-inline" onsubmit="return false;">										
				<div class="input-group">
			        <input class="form-control ue-form" type="text" id="staffName" placeholder="请输入姓名"/>
			        <div class="input-group-addon ue-form-btn" id="query">
			        	<span class="fa fa-search"></span>
			        </div>
		        </div>
			    <a class="btn ue-btn dbtn" id="moresearch">更多搜索<i class="fa fa-angle-down"></i></a>
			
				<div class="btn-group pull-right">
					<button id="refresh" type="button"
						class="btn ue-btn">
						<span class="fa fa-refresh"></span>刷新
					</button>
				</div>
				<div class="btn-group pull-right">
					<button id="export" type="button"
						class="btn ue-btn">
						<span class="fa fa-upload"></span> 导出结果
					</button>
				</div>	
				<div class="btn-group pull-right">
					<button id="import" type="button"
						class="btn ue-btn">
						<span class="fa fa-download"></span> 导入数据
					</button>
				</div>
				<div class="btn-group pull-right">
					<button id="exportTemplate" type="button"
						class="btn ue-btn">
						<span class="fa fa-upload"></span> 导出模板
					</button>
				</div>
				<div class="btn-group pull-right">
					<button id="delete" type="button"
						class="btn ue-btn">
						<span class="fa fa-minus"></span> 删除
					</button>
				</div>
				<div class="btn-group pull-right">
					<button id="edit" type="button"
						class="btn ue-btn">
						<span class="fa fa-edit"></span>编辑
					</button>
				</div>
				<div class="btn-group pull-right">
					<button id="add" type="button"
						class="btn ue-btn">
						<span class="fa fa-plus"></span>增加
					</button>
				</div>
		<!-- 		 <div class="btn-group pull-right">
					<button id="caculate" type="button"
						class="btn ue-btn">
						<span class="fa fa-calculator"></span> 计算
					</button>
				</div> -->
			  </form>
		    </div>
			<div class="row">
				<table id="bonusList" class="table table-bordered table-hover">
					<thead >
				        <tr>
					        <th rowspan="2" width="5%"><input type="checkbox" id="selectAll"
							onchange="selectAll(this,'checkList')" /></th>
				            <th rowspan="2" width="8%" style="text-align: center;">工号</th>
							<th rowspan="2" width="8%" style="text-align: center;">姓名</th>
							<th rowspan="2" width="8%" style="text-align: center !important;">月工资标准</th>
							<th rowspan="2" width="8%" style="text-align: center !important;">年终奖</th>
							<th rowspan="2" width="8%" style="text-align: center !important;">应纳税额</th>
							<th rowspan="2" width="8%" style="text-align: center !important;">应发年终奖</th>
				            <th colspan="5" style="text-align: center;">最优避税</th>
				        </tr>
				        <tr class="autoNewline">
				            <th style="text-align: center !important;border-left:1px solid #ddd;">综合计税<br>应发年终奖</th>
				            <th style="text-align: center !important;">第1个月<br>随工资发放奖金</th>
				            <th style="text-align: center !important;">第2个月<br>随工资发放奖金</th>
				            <th style="text-align: center !important;">避税金额</th>
				            <th style="text-align: center !important;">实发年终奖</th>
				        </tr>
				    </thead>
				</table>
			</div>
		</div>

    
    <!--左菜单模板-->
    <script type="text/html" id="subMenu-template" >
	<!-- ko if: isLeaf=='true' -->
	<li data-bind="attr: { id: id}">
        <a data-bind="click:loadUrl" class="clearfix" data-role="leaf">
       		<span data-bind="text:text"></span>
			<i class="fa fa-star-o ue-vmenu-icon-r" data-bind="click:saveFavorites.bind($data,$index()),clickBubble: false" data-toggle="tooltip" data-placement="right" title="收藏"></i>
        </a>
    </li>
	<!-- /ko -->
	<!-- ko if: isLeaf=='false' -->
	<li data-bind='attr: { id: id}'><a data-bind=" text: text"></a>
		<ul  data-bind="template: {name:'subMenu-template',foreach:children}">
			<li data-bind="attr: { id: id}">
            <a data-bind="click:loadUrl" class="clearfix" data-role="leaf"><span data-bind="text:text"></span>
			<i class="fa fa-star-o ue-vmenu-icon-r" data-bind="click:saveFavorites.bind($data,$index()),clickBubble: false" data-toggle="tooltip" data-placement="right" title="收藏"></i>
       	    </a>
    		</li>
		</ul>
    </li> 
	<!-- /ko -->
    </script>
  </body>
</html>