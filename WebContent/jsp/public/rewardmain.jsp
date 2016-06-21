<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/tags/loushang-web" prefix="l"%>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>奖金管理</title>

    <!-- 需要引用的CSS -->
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/jsp/public/images/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/bootstrap.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/font-awesome.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/ui.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='css/form.css'/>" />
    <link rel="stylesheet" type="text/css" href="<l:asset path='salary/css/portal.css'/>" />
    <style>
    .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
	    padding: 5px;
	    text-align:center;
    }
    </style>
    <script  type="text/javascript" src="<l:asset path='knockout.js'/>"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<l:asset path='html5shiv.js'/>"></script>
      <script src="<l:asset path='respond.js'/>"></script>
      <script type="text/javascript" src="<l:asset path='PIE.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
	    //项目上下文
		var context="<%=request.getContextPath()%>";
		
		var assetPath = context;
    </script>
  </head>
  <body >
	<!-- 页面结构 -->
		<div class="banner">
		   <div class="instruction">
		      <span>明明白白发工资</span>
              <span class="ms">快速、便捷、准确、贴心</span>
		   </div>
			<div class="warn-list ellipsis" style="text-overflow:clip;">
				<div class="item">
					<a class="ue-pie" href="<%=request.getContextPath()%>/jsp/exam/salarycalculator.jsp">薪资<br>计算器</a>
				</div>
				<div class="item">
					<a class="ue-pie" href="<%=request.getContextPath()%>/jsp/exam/staffTax.jsp">员工<br>月工资</a>
					
				</div>
				<div class="item">
					<a class="ue-pie" href="<%=request.getContextPath()%>/jsp/exam/bonus.jsp">年终奖<br>最优避税</a>
					
				</div>
			</div>
	    </div>
		<div class="container" style="margin-top:10px;margin-bottom:0px">
		    <div style="margin-bottom:10px;font-size:25px;text-align:center"><span>个人所得税税率表(工资、薪金所得适用）</span></div>
			<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th>级数</th>
					<th>月工资应纳税所得额</th>
					<th>适用税率(%)</th>
					<th>速算扣除数</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td>
					<td>不超过1,500元的部分( X ≤ 1500)</td>
					<td>3</td>
					<td>0</td>
				</tr>
				<tr>
					<td>2</td>
					<td>超过1,500元至4,500元的部分</td>
					<td>10</td>
					<td>105</td>
				</tr>
				<tr>
					<td>3</td>
					<td>超过4,500元至9,000元的部分</td>
					<td>20</td>
					<td>555</td>
				</tr>
				<tr>
					<td>4</td>
					<td>超过9,000元至35,000元的部分</td>
					<td>25</td>
					<td>1,005</td>
				</tr>
				<tr>
					<td>5</td>
					<td>超过35,000元至55,000元的部分</td>
					<td>30</td>
					<td>2,755</td>
				</tr>
				<tr>
					<td>6</td>
					<td>超过55,000元至80,000元的部分</td>
					<td>35</td>
					<td>5,505</td>
				</tr>
				<tr>
					<td>7</td>
					<td>超过80,000元的部分( X > 80,000)</td>
					<td>45</td>
					<td>13,505</td>
				</tr>
			</tbody>
		</table>
		</div>
    <!-- 需要引用的JS -->
    <script type="text/javascript" src="<l:asset path='jquery.js'/>"></script>
    <script type="text/javascript" src="<l:asset path='bootstrap.js'/>"></script>
    <script type="text/javascript" src="<l:asset path='form.js'/>"></script>
    <script  type="text/javascript" src="<l:asset path='ui.js'/>"></script>
    
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