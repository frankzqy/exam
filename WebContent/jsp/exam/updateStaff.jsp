<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/loushang-web" prefix="l"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>增加员工</title>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/bootstrap.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/font-awesome.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/ui.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/form.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<l:asset path='salary/css/portal.css'/>" />
	<style>
	    /*设置“新增用户 修改用户”标题的文字样式*/
		.htext {
			font-size: 14px;
			font-weight: bold;
		}
	    /*设置分割线的上下间距*/
		hr.fenge{
			margin-top: 10px;
			margin-bottom: 10px;
		}
		
		.Validform_right {
		   color: #71b83d;
		}
	</style>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<l:asset path='html5shiv.js'/>"></script>
      <script src="<l:asset path='respond.js'/>"></script>
    <![endif]-->
	<script  type="text/javascript" src="<l:asset path='jquery.js'/>"></script>
	<script  type="text/javascript" src="<l:asset path='bootstrap.js'/>"></script>
	<script  type="text/javascript" src="<l:asset path='form.js'/>"></script>
    <script  type="text/javascript" src="<l:asset path='ui.js'/>"></script>
	<script type="text/javascript">
	var context="<%=request.getContextPath()%>";
	$(function(){
		$("#saveForm").Validform({
			btnSubmit:"#saveBtn",			
			tiptype:function(msg,o,cssctl){
				if(!o.obj.is("form")){
					var objtip=o.obj.siblings(".Validform_checktip");
					//根据单复选框的DOM结构查找其验证结果框
					 if(objtip.length == 0){
						if(o.obj.parent("div").length != 0){   //普通文本框
						    objtip=o.obj.parents("div").siblings(".Validform_checktip");
						}else if(o.obj.parent("label").length != 0){
							objtip=o.obj.parent("label").siblings(".Validform_checktip"); //单复选框
						}
					}
				    cssctl(objtip,o.type);
				    objtip.text(msg);
				} else{
					var objtip=o.obj.find("#msgdemo");
					cssctl(objtip,o.type);
					objtip.text(msg);
				} 
			},
			datatype:{//传入自定义datatype类型;
			     "math" : math
			},
			callback:function(form){
				$.dialog({
					type: 'confirm',
					content: '您确定要提交表单吗？',
					ok: function(){save();},
					cancel: function(){}
				});
			}
		});
		$("#returnBtn").click(function(){
			window.location.href = context + "/jsp/exam/staffTax.jsp";
		})
	})
	//保存实例
	function save(){
		var url = context + "/service/stafftax/updateSave";
	    saveForm.action = url;
	    saveForm.method = "POST";
	    saveForm.submit();
	}
	function math(gets,obj,curform,datatype){
		var regx = /^(-?\d+)(\.\d+)?$/;
		return regx.test(gets);
	}
	</script>
</head>
<body>
<div class="iframe-banner">
		   <div class="instruction">
		      <span>明明白白发工资</span>
              <span class="ms">快速、便捷、准确、贴心</span>
		   </div>
	    </div>
	<div class="topdist"></div>
	<div class="container text-center" id="sandbox-container">
	   <h2 class="text-left htext">修改员工</h2>
	   <hr class="fenge"/>
	   <br/>
	  <div class="col-xs-10 col-md-10">
		<form class="form-horizontal" id="saveForm" name="staff" onsubmit="return false">
			<div class="form-group">
				<label class="col-xs-3 col-md-3  text-right ">工号:</label>
				<div class="col-xs-8 col-md-8">
					<input type="text" class="form-control ue-form Validform_input" id="staffId"
						name="staffId" value="${staff.staffId }"
						placeholder="工号" datatype="s1-12" errormsg="名称1~12字符！" nullmsg="请设置正确工号"  readonly="readonly" />						
					<span class="Validform_checktip Validform_span"></span>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-xs-3 col-md-3  text-right ">姓名</label>
				<div class="col-xs-8 col-md-8">
					<input type="text" class="form-control ue-form Validform_input" id="name"
						name="name" value="${staff.name }" 
						placeholder="姓名"  datatype="s1-18" errormsg="名称1~18字符！" nullmsg="请设置正确名称"/>
					<span class="Validform_checktip Validform_span"></span>	
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 col-md-3  text-right ">月工资标准</label>
				<div class="col-xs-8 col-md-8">
					<input type="text" class="form-control ue-form Validform_input" id="wages"
						name="wages" value="${staff.wages }" datatype="math" errormsg="请输入数字！" ignore="ignore"
						placeholder="月工资标准" />	
					<span class="Validform_checktip Validform_span"></span>	
				</div>
			</div>
			<div class="form-group" >
			  <label class="col-xs-3 col-md-3 control-label"></label>
			  <div class="col-xs-8 col-md-8">
					<button type="button" class="btn ue-btn-primary" id="saveBtn" >
						保存
					</button>
					<button type="button" class="btn ue-btn" id="returnBtn">
						 返回
					</button>
					<span id="msgdemo"></span>
			</div>
		  </div>
		</form>
		</div>
	</div>
</body>
</html>