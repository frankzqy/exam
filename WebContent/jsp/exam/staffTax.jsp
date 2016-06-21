<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/loushang-web" prefix="l"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>个人所得税列表</title>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/bootstrap.css'/>" />
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/font-awesome.css'/>" />
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/ui.css'/>" />
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/form.css'/>" />
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/datatables.css'/>" />
	<link rel="stylesheet" type="text/css" href="<l:asset path='salary/css/portal.css'/>" />
	<link rel="stylesheet" type="text/css" href="<l:asset path='demo/css/demo.css'/>" />
	<style>
	.container {
		width: 100%;
	}
	</style>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<l:asset path='html5shiv.js'/>"></script>
      <script src="<l:asset path='respond.js'/>"></script>
    <![endif]-->
	<script type="text/javascript" src="<l:asset path='jquery.js'/>"></script>
	<script type="text/javascript" src="<l:asset path='bootstrap.js'/>"></script>
	<script type="text/javascript" src="<l:asset path='form.js'/>"></script>
	<script type="text/javascript" src="<l:asset path='datatables.js'/>"></script>
	<script  type="text/javascript" src="<l:asset path='ui.js'/>"></script>
</head>
<body>
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
					<input class="form-control ue-form" type="text" id="staffId"
						placeholder="员工工号" />
				</div>
				<div class="input-group">
					<input class="form-control ue-form" type="text" id="staffName"
						placeholder="员工姓名" />
					<div class="input-group-addon ue-form-btn" id="query">
						<span class="fa fa-search"></span>
					</div>
				</div>
				<div class="btn-group pull-right">
					<button id="add" type="button" class="btn ue-btn">
						<span class="fa fa-plus"></span>增加
					</button>
					<button id="delete" type="button" class="btn ue-btn">
						<span class="fa fa-minus"></span>删除
					</button>
					<button id="exportTemplate" type="button"
						class="btn ue-btn">
						<span class="fa fa-upload"></span> 导出模板
					</button>
					<button id="importStaff" type="button" class="btn ue-btn">
						<span class="fa fa-download"></span>导入数据
					</button>
					<button id="exportStaff" type="button" class="btn ue-btn">
						<span class="fa fa-upload"></span>导出结果
					</button>
					<button id="refresh" type="button"
						class="btn ue-btn">
						<span class="fa fa-refresh"></span>刷新
					</button>
				</div>
			</form>
		</div>

		<div class="row">
			<table id="empList" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th width="10%"><input type="checkbox" id="selectAll" /></th>
						<th width="15%">工号</th>
						<th width="15%">姓名</th>
						<th width="15%">月工资</th>
						<th width="15%">应纳税</th>
						<th width="15%">实际收入</th>
						<th>操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	var context="<%=request.getContextPath()%>";
	$(document).ready(function() {
		oTable = init();
		function init(){
			var table = $("#empList").dtable({      
				  "ajax" : {
					  "type": "post",
				       "url": context + "/service/stafftax",
				       "data":  {
				    	   "staffId" : function() {return $("#staffId").val();},
				    	   "staffName" : function(){return $("#staffName").val();}
				    	   }
				  },
			      "columns": [
			                  {"data": "staffId", "bSortable":false},
			                  {"data": "staffId", "bSortable":false},
			                  {"data": "name", "bSortable":false},
			                  {"data": "wages", "bSortable":false},
			                  {"data": "tax", "bSortable":false},
			                  {"data": "afterTax", "bSortable":false},
			                  {"data": "staffId", "bSortable":false}
			                ],
			      "columnDefs":[
			    		      {
			    		        "targets":0,	    		       
			    		        "data":"staffId",
			    		        "render": function(data, type, full) {
			    		           return '<input type="checkbox" value="' + data + '" title="' + data + '" id="checkbox" name="checkList"/>';
			    		         }
			    		      },
			    		      {
				    		    	"targets":6,
				    		    	"data":"staffId",
				    		    	"render":function(data, type, full){
				    		    		return '<div class="btn-group"><button id="modify" staff="' + data + '" type="button" class="btn btn-xs ue-btn dbtn">编辑</button><button id="del" staff="' + data + '" type="button" class="btn btn-xs ue-btn dbtn">删除</button></div>'
				    		    	}
				    		  }
			    		    ]
			    });
			return table;
		}
		$("#importStaff").on("click",function(){
    		$.dialog({
    			type: 'iframe',
				url: context + '/jsp/exam/importStaff.jsp',
				title: '导入员工月工资',
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
		})
	
		$("#query").click(function(){
			oTable.ajax.reload();
		});
		
		$("#add").click(function(){
			window.location.href= context +'/jsp/exam/addStaff.jsp'
		});
		
		// 全选
		$("#selectAll").click(function(){
			var checked = $(this).prop('checked');
			$(this).parents('thead').siblings('tbody').find('input').each(function() {
				$(this).prop('checked', checked);
	        });
		});
		
		//编辑
		 $("#empList tbody").on("click", "#modify", function(){
			 var straffId =  $(this).attr("staff");
			 window.location.href = context + "/service/stafftax/update?staffId=" + straffId;
		  });
		
		//删除
		 $("#empList tbody").on("click", "#del", function(){
			 var straffId =  $(this).attr("staff");
			 deleteStaff(straffId + ",");
		  });
		
		$("#delete").click(function(){
			var ids = "";
			$("#empList").find('input').each(function(){
				if ($(this).prop('checked')) {
					ids += $(this).val() + ",";
				}
			});
			if(ids==""){
				$.dialog({
	    			type: 'alert',
	    		    content: '请选择需要删除的数据！'
	    	    });
			}else{
				deleteStaff(ids);
			}
		});
		function deleteStaff(ids) {
			$.ajax({
				method : "POST",
				url : context + "/service/stafftax/delete",
				data: {"ids" : ids},
				datatype :"JSON",
				success: function(result){
					$.dialog({
		    			type: 'alert',
		    		    content: '删除成功'
		    	    });
					oTable.ajax.reload();
				},
				error:function(){
					alert("系统异常!");
				}
			});
		}
		
		$("#exportStaff").click(function(){
			window.location.href = context + "/service/download/xsl/wages";
		});
		$("#exportTemplate").click(function(){
			window.location.href = context + "/service/download/xsl/wagesTemplate";
		});
		
		$("#refresh").click(function(){
			oTable.ajax.reload();
		});
	});
	</script>
</html>