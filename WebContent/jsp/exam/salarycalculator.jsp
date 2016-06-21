<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/tags/loushang-web" prefix="l"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>薪资计算器</title>

    <!-- 需要引用的CSS -->
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/jsp/public/images/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/bootstrap.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/font-awesome.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/ui.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/form.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='salary/css/portal.css'/>" />
    <style>
    	/*设置分割线的上下间距*/
		hr.fenge{
			margin-top: 10px;
			margin-bottom: 10px;
		}
		.Validform_input {
		   width: 61%;
		}
    </style>
    <script  type="text/javascript" src="<l:asset path='knockout.js'/>"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<l:asset path='html5shiv.js'/>"></script>
      <script src="<l:asset path='respond.js'/>"></script>
    <![endif]-->
   
  </head>
  <body >
	<!-- 页面结构 -->
		<div class="iframe-banner">
		   <div class="instruction">
		      <span>明明白白发工资</span>
              <span class="ms">快速、便捷、准确、贴心</span>
		   </div>
	    </div>
		<div class="container" style="margin-top:20px;margin-bottom:0px">		   
		    <form class="form-horizontal" id="calform" onsubmit="return false">
		      <h4>薪资计算器</h4>
		      <hr class="fenge">
			   <div class="form-group">
					<label class="col-xs-3 col-md-3  text-right text-name">收入类型</label>
					<div class="col-xs-5 col-md-5">
						<select class="form-control input-sm ue-form" id="salarytype" onchange="typechange(this)">
							<option value="0">工资、薪金所得（月薪）</option>
							<option value="1">年终奖</option>
							<option value="2">最优拆分方案</option>
						</select>
					</div>
			    </div>
			    <div class="form-group" style="display:none" id="yearbonusdiv">
					<label class="col-xs-3 col-md-3  text-right text-name">年终奖</label>
					<div class="col-xs-8 col-md-8">
						<input type="text" class="form-control ue-form Validform_input" id="yearbonus"
							name="yearbonus" value="" placeholder="年终奖" datatype="math" errormsg="请输入数字！" ignore="ignore"/>	
						<span class="Validform_checktip Validform_span" ></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 col-md-3  text-right text-name">税前工资</label>
					<div class="col-xs-8 col-md-8">
						<input type="text" class="form-control ue-form Validform_input" id="presalary"
							name="presalary" value="" 
							placeholder="税前工资" datatype="math" errormsg="请输入数字！" ignore="ignore"/>	
						<span class="Validform_checktip Validform_span" ></span>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 col-md-3  text-right text-name">起征点</label>
					<div class="col-xs-5 col-md-5">
						<input type="text" class="form-control ue-form" id="insur"
							name="insur" value="3500" disabled/>						
					</div>
				</div>
				<div class="form-group" >
					<label class="col-xs-3 col-md-3  text-right text-name"></label>
					<div class="col-xs-5 col-md-5">
						<div>
							<button type="button" class="btn ue-btn-primary pull-left" id="calBtn" >
								计算
							</button>
							<button type="button" class="btn ue-btn-primary pull-left" style="margin-left:5px" id="reset">
								 重置
							</button>
							<span id="msgdemo" style="margin-left:30px;"></span>
						</div>
					</div>
			    </div>
	    	</form>
	    	<form class="form-horizontal">
		      <h4>计算结果</h4>
		      <hr class="fenge">
				<div class="form-group">
					<label class="col-xs-3 col-md-3  text-right text-name">应缴税款</label>
					<div class="col-xs-5 col-md-5">
						<input type="text" class="form-control ue-form" id="tax"
							name="presalary" value="" 
							placeholder="应缴税款" />						
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 col-md-3  text-right text-name" id="behincome">实发工资</label>
					<div class="col-xs-5 col-md-5">
						<input type="text" class="form-control ue-form " id="behsalary"
							name="insur" value="" 
							placeholder="实发工资"/>						
					</div>
				</div>
				<div class="form-group" style="display:none" id="bonusdiv">
					<label class="col-xs-3 col-md-3  text-right text-name">一次性年终奖</label>
					<div class="col-xs-5 col-md-5">
						<input type="text" class="form-control ue-form " id="bonus1"
							name="bonus1" value="" 
							placeholder="一次性年终奖"/>						
					</div>
				</div>
				 <div class="form-group" style="display:none" id="firstdiv">
					<label class="col-xs-3 col-md-3  text-right text-name">第一个月分发奖金</label>
					<div class="col-xs-5 col-md-5">
						<input type="text" class="form-control ue-form " id="firstmonth"
							name="firstmonth" value="" 
							placeholder="第一个月分发奖金" />						
					</div>
				</div>
				<div class="form-group" style="display:none" id="seconddiv">
					<label class="col-xs-3 col-md-3  text-right text-name">第二个月分发奖金</label>
					<div class="col-xs-5 col-md-5">
						<input type="text" class="form-control ue-form " id="secondmonth"
							name="secondmonth" value="" 
							placeholder="第二个月分发奖金" />						
					</div>
				</div>
	    	</form>
		</div>
    <!-- 需要引用的JS -->
    <script type="text/javascript" src="<l:asset path='jquery.js'/>"></script>
    <script type="text/javascript" src="<l:asset path='bootstrap.js'/>"></script>
    <script type="text/javascript" src="<l:asset path='form.js'/>"></script>
    <script  type="text/javascript" src="<l:asset path='ui.js'/>"></script>  
    <script>
	    var context="<%=request.getContextPath()%>";
		$(document).ready(function(){
			$("#calform").Validform({
				btnSubmit:"#calBtn",
				tiptype:function(msg,o,cssctl){
					if(!o.obj.is("form")){
						var objtip=o.obj.siblings(".Validform_checktip");
					    cssctl(objtip,o.type);
					    objtip.text(msg);
					} else{
						var objtip=o.obj.find("#msgdemo");
						cssctl(objtip,o.type);
						objtip.text(msg);
					} 
				},
				datatype:{//传入自定义datatype类型;
				      "math":math,
				},
				callback:function(form){
					calculator();
				}
			});
			
			
			
			$("#reset").click(function(){
				$("#presalary").val("");
				$("#yearbonus").val("");
			})
		});
		function typechange(obj){
		   	if(obj.value == "2"){
		   		$("#bonusdiv").css("display","block");
        		$("#firstdiv").css("display","block");
        		$("#seconddiv").css("display","block");
		   	}else{
		   		$("#bonusdiv").css("display","none");
        		$("#firstdiv").css("display","none");
        		$("#seconddiv").css("display","none");
		   	}
		   	if(obj.value == "1" || obj.value=="2"){
        		$("#behincome").text("实发奖金");
        		$("#yearbonusdiv").css("display","block");
        	}else{
        		$("#behincome").text("实发工资");
        		$("#yearbonusdiv").css("display","none");

        	}
		}
		function math(gets,obj,curform,datatype){
			var regx = /^(-?\d+)(\.\d+)?$/;
			return regx.test(gets);
		}

		function calculator(){
			var monthsalary = $("#presalary").val();
			var type = $("#salarytype").val();
			var yearbonus = $("#yearbonus").val();
			var url = "/service/calculator/wageTax?presalary="+ monthsalary;
		    if(type == "1"){
				url = "/service/calculator/bonusTax?presalary="+ monthsalary+"&yearbonus="+yearbonus;
			}else if(type == "2"){
				url = "/service/calculator/optTax?presalary="+ monthsalary+"&yearbonus="+yearbonus;
			}else{
				url = "/service/calculator/wageTax?presalary="+ monthsalary;
			}
            $.ajax({
            	"url": context+url,
            	"type": "GET",
                "success": function(data){
                	data = eval("("+data+")");
                	$("#tax").val(data.tax);
                	$("#behsalary").val(data.behsalary);
                	if(type == "2"){
                		$("#firstmonth").val(data.firstmonth);
                		$("#secondmonth").val(data.secondmonth);
                		$("#bonus1").val(data.bonus1);
                	}
                	
                }
            });
		}
	    </script>
  </body>
</html>