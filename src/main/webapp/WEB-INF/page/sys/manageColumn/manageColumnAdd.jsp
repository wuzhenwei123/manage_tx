<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>${_title}</title>
	<link href="favicon1.ico" rel="icon" type="image/x-icon" />
	<%@ include file="/WEB-INF/page/common/css.jsp" %>
	<style type="text/css">
		body {
			background: #ffffff none repeat scroll 0 0!important;
		  	width: 97%!important;
		  	margin-left:5px!important;
		}
	</style>
</head>
<body class="dig-body">
	<div class="main-content-wrapper">
		<div class="main-content">
			<section>
				<div class="container-fluid container-padded">
					<div class="row">
						<div class="col-md-12">
							<form id="add_form" action="${ctx}/manageColumn/addManageColumn" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">菜单名称：</label>
									<div class="col-md-7">
										<input type="text" id="columnName" name="columnName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">链接：</label>
									<div class="col-md-7">
										<input type="text" id="columnUrl" name="columnUrl" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">父菜单：</label>
									<div class="col-md-7">
										<select id="parentColumnID" onchange="chan()" name="parentColumnID" class="form-control"> 
					                        <option value="-1">父菜单</option>
					                        <c:forEach items="${parentList }" var="column">
					                        <option value="${column.columnId }">
					                         &nbsp;&nbsp;|-${column.columnName }
					                        </option> 
					                        </c:forEach> 
					                	</select>  
									</div>
								</div>
								<div class="form-group">
		                            <label class="col-sm-3 control-label">状态：</label>
		                            <div class="col-sm-7">
		                                <select id="state" name="state" class="form-control">
		                                 	<option value="1">正常</option>
		                                 	<option value="0">禁用</option>
		                                </select>
		                        	</div>
		                        </div>
								<div class="form-group" id="img_form">
									<label class="col-md-3 control-label">菜单图片：</label>
									<div class="col-md-7">
										<div class="btn-group">
			                                <button id="1" class="btn btn-primary" type="button"><i class="fa fa-desktop"></i></button>
			                                <button id="2" class="btn" type="button"><i class="fa fa-comment-o"></i></button>
			                                <button id="3" class="btn" type="button"><i class="fa fa-area-chart"></i></button>
			                                <button id="4" class="btn" type="button"><i class="fa fa-bars"></i></button>
			                                <button id="5" class="btn" type="button"><i class="fa fa-cog"></i></button>
			                                <button id="6" class="btn" type="button"><i class="fa fa-bar-chart"></i></button>
			                                <button id="7" class="btn" type="button"><i class="fa fa-line-chart"></i></button>
			                                <button id="8" class="btn" type="button"><i class="fa fa-briefcase"></i></button>
			                                <button id="9" class="btn" type="button"><i class="fa fa-calendar"></i></button>
			                                <button id="10" class="btn" type="button"><i class="fa fa-camera"></i></button>
			                                <button id="11" class="btn" type="button"><i class="fa fa-database"></i></button>
			                                <button id="12" class="btn" type="button"><i class="fa fa-download"></i></button>
			                                <button id="13" class="btn" type="button"><i class="fa fa-envelope"></i></button>
			                                <button id="14" class="btn" type="button"><i class="fa fa-picture-o"></i></button>
			                                <button id="15" class="btn" type="button"><i class="fa fa-phone"></i></button>
			                                <button id="16" class="btn" type="button"><i class="fa fa-print"></i></button>
			                                <button id="17" class="btn" type="button"><i class="fa fa-sitemap"></i></button>
			                                <button id="18" class="btn" type="button"><i class="fa fa-user"></i></button>
			                                <button id="19" class="btn" type="button"><i class="fa fa-wrench"></i></button>
			                            </div>
										<input type="hidden" id="columnImg" name="columnImg" value="fa fa-desktop" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">备注：</label>
									<div class="col-md-7">
										<input type="text" id="remark" name="remark" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-7 col-md-push-3">
										<button type="reset" class="btn btn-default">重置</button>
										<button type="submit" class="btn btn-primary">保存</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
	<%@ include file="/WEB-INF/page/common/js.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#add_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
// 		        	 column_en_name: {
// 		                validators: {
// 		                    notEmpty: {
// 		                        message: 'column_en_name不能为空'
// 		                    }
// 		                }
// 		             },
		        	 columnName: {
		                validators: {
		                    notEmpty: {
		                        message: '菜单名称不能为空'
		                    }
		                }
		             },
		        	 columnUrl: {
		                validators: {
		                    notEmpty: {
		                        message: '链接不能为空'
		                    }
		                }
		             },
		        	 parentColumnID: {
		                validators: {
		                    notEmpty: {
		                        message: '父菜单不能为空'
		                    }
		                }
		             },
		        	 state: {
		                validators: {
		                    notEmpty: {
		                        message: '状态不能为空'
		                    }
		                }
		             },
		        	 sort_id: {
		                validators: {
		                    notEmpty: {
		                        message: '排序不能为空'
		                    }
		                }
		             },
		        	 columnImg: {
		                validators: {
		                    notEmpty: {
		                        message: '菜单图片不能为空'
		                    }
		                }
		             }
		        }
		    }).on('success.form.bv', function(e) {
	            e.preventDefault();
	            var $form = $(e.target);
	            var bv = $form.data('bootstrapValidator');
	            
	            var parentColumnID = $("#parentColumnID").val();
		    	if(parentColumnID != -1){
		    		$("#img_form").val('');
		    	}
	            
	            $.post($form.attr('action'), $form.serialize(), function(result) {
	            	if(result.code == 1){
		                parent.sys.success(result.message,'',function(){
		                	P.close_win();
		                	parent.search();
		                });
	            	}else{
	            		parent.sys.error(result.message,'',function(){
		                });
	            	}
	            }, 'json');
	        });
			
			$(".btn-group button").click(function(){
	        	var id = $(this).attr("id");
	        	$(".btn-group button").each(function(){
	        		$(this).removeClass("btn-primary");
	        		$(this).removeClass("btn-white");
	        		
	        		var idd = $(this).attr("id");
	        		if(id == idd){
	        			$(this).addClass("btn-primary");
	        		}else{
	        			$(this).addClass("btn-white");
	        		}
	        	 });
	        	var cc = $(this).find("i").attr("class");
	        	$("#columnImg").val(cc);
	        })
		});
		function chan(){
	    	var parentColumnID = $("#parentColumnID").val();
	    	if(parentColumnID == -1){
	    		$("#img_form").show();
	    	}else{
	    		$("#img_form").hide();
	    	}
	    }
    </script>
</body>
</html>