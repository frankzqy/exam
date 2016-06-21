<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/loushang-web" prefix="l"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户详细信息</title>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/bootstrap.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/font-awesome.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/ui.css'/>"/>
	<link rel="stylesheet" type="text/css" href="<l:asset path='css/form.css'/>"/>
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
	
	<script id="searchpanel" type="text/html">
	  <div id="searchtemp">
		<div class="form-group">
			<label for="userId" class="text-name">用户ID:</label> 
			<input type="text" class="form-control ue-form" id="userId" placeholder="用户ID">
		</div>
        <div style="margin:0px 50px">
			<button id="search" class="btn ue-btn-primary">搜索</button>
		</div>
      <div>
	</script>
	<script type="text/javascript">
	var context="<%=request.getContextPath()%>";
	$(function() {
		var uploader = new plupload.Uploader({
			runtimes: 'html5,flash,silverlight,html4',
			browse_button: 'importBonus',
			url: context + '/service//upload/staff',
			max_file_size: '50mb',
            unique_names: true,
           /*  filters: [
                 {title: "Image files", extensions: "jpg,gif,png"}, 
                 {title: "Zip files", extensions: "zip"}
            ], */
            flash_swf_url: '../../dist/js/Moxie.swf',
            silverlight_xap_url: '../../dist/js/Moxie.xap',
            init: {
    			FilesAdded: function(up, files) {
    				plupload.each(files, function(file) {
    					document.getElementById('cresult').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <div class="progress"><div class="progress-bar"><span></span></div></div></div>';
    				});
    				uploader.start();
    			},

    			UploadProgress: function(up, file) {
    				$("#" + file.id).find('.progress-bar')[0].style.width = $("#" + file.id).find('.progress-bar')[0].innerText = file.percent + "%";
    			},
    			
    			Error: function(up, err) {
    				//document.getElementById('cresult').appendChild(document.createTextNode("\nError #" + err.code + ": " + err.message));
    			}
    		}
		});
		uploader.init();
	});	
	</script>
</head>
<body>
	<div class="topdist"></div>
	<div class="container">
		<div class="col-xs-12 col-md-12">		   
			<div class="row">
				<form class="form-inline" id="fileimportform" action="" style="margin-top:100px;margin-left:160px" >										
					<a class="btn ue-btn-primary" id="importBonus"><span class="fa fa-upload"></span>导入员工月工资</a>
			    </form>
			    <div id="cresult"></div>
			</div>
			
		</div>
	</div>

</body>
</html>