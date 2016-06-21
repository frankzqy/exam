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
    
    <script  type="text/javascript" src="<l:asset path='knockout.js'/>"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<l:asset path='html5shiv.js'/>"></script>
      <script src="<l:asset path='respond.js'/>"></script>
    <![endif]-->
    <script type="text/javascript">
	    //项目上下文
		var context="<%=request.getContextPath()%>";
		
		var assetPath = context;
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
		<div class="container" style="margin-top:20px;margin-bottom:0px">
		   
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