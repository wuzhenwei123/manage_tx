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
							<form id="update_form" action="${ctx}/txPayLog/updateTxPayLog" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" value="${txPayLog.id}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">style：</label>
									<div class="col-md-7">
										<input type="text" id="style" name="style" value="${txPayLog.style}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">type：</label>
									<div class="col-md-7">
										<input type="text" id="type" name="type" value="${txPayLog.type}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">content：</label>
									<div class="col-md-7">
										<input type="text" id="content" name="content" value="${txPayLog.content}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">createTime：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="createTime" name="createTime" value="<fmt:formatDate value="${txPayLog.createTime}" type="both"/>" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">userId：</label>
									<div class="col-md-7">
										<input type="text" id="userId" name="userId" value="${txPayLog.userId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">mobile：</label>
									<div class="col-md-7">
										<input type="text" id="mobile" name="mobile" value="${txPayLog.mobile}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">nickName：</label>
									<div class="col-md-7">
										<input type="text" id="nickName" name="nickName" value="${txPayLog.nickName}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">accNo：</label>
									<div class="col-md-7">
										<input type="text" id="accNo" name="accNo" value="${txPayLog.accNo}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-7 col-md-push-3">
										<button type="submit" class="btn btn-primary">更新</button>
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
	    	P.init_date("update_form","createTime");
			$('#update_form').bootstrapValidator({
		        feedbackIcons: {
		                valid: 'glyphicon glyphicon-ok',
		                invalid: 'glyphicon glyphicon-remove',
		                validating: 'glyphicon glyphicon-refresh'
		            },
		        fields: {
		        	 id: {
		                validators: {
		                    notEmpty: {
		                        message: 'id不能为空'
		                    }
		                }
		             },
		        	 style: {
		                validators: {
		                    notEmpty: {
		                        message: 'style不能为空'
		                    }
		                }
		             },
		        	 type: {
		                validators: {
		                    notEmpty: {
		                        message: 'type不能为空'
		                    }
		                }
		             },
		        	 content: {
		                validators: {
		                    notEmpty: {
		                        message: 'content不能为空'
		                    }
		                }
		             },
		        	 createTime: {
		                validators: {
		                    notEmpty: {
		                        message: 'createTime不能为空'
		                    }
		                }
		             },
		        	 userId: {
		                validators: {
		                    notEmpty: {
		                        message: 'userId不能为空'
		                    }
		                }
		             },
		        	 mobile: {
		                validators: {
		                    notEmpty: {
		                        message: 'mobile不能为空'
		                    }
		                }
		             },
		        	 nickName: {
		                validators: {
		                    notEmpty: {
		                        message: 'nickName不能为空'
		                    }
		                }
		             },
		        	 accNo: {
		                validators: {
		                    notEmpty: {
		                        message: 'accNo不能为空'
		                    }
		                }
		             }
		        }
		    }).on('success.form.bv', function(e) {
	            e.preventDefault();
	            var $form = $(e.target);
	            var bv = $form.data('bootstrapValidator');
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
		});
    </script>
</body>
</html>