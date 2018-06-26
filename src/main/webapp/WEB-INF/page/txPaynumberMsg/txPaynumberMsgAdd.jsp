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
							<form id="add_form" action="${ctx}/txPaynumberMsg/addTxPaynumberMsg" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">userId：</label>
									<div class="col-md-7">
										<input type="text" id="userId" name="userId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">BillType：</label>
									<div class="col-md-7">
										<input type="text" id="BillType" name="BillType" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">payNumber：</label>
									<div class="col-md-7">
										<input type="text" id="payNumber" name="payNumber" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">tradeName：</label>
									<div class="col-md-7">
										<input type="text" id="tradeName" name="tradeName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">nickName：</label>
									<div class="col-md-7">
										<input type="text" id="nickName" name="nickName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">userAddress：</label>
									<div class="col-md-7">
										<input type="text" id="userAddress" name="userAddress" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">createTime：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="createTime" name="createTime" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">status：</label>
									<div class="col-md-7">
										<input type="text" id="status" name="status" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">remark1：</label>
									<div class="col-md-7">
										<input type="text" id="remark1" name="remark1" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">remark2：</label>
									<div class="col-md-7">
										<input type="text" id="remark2" name="remark2" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">remark3：</label>
									<div class="col-md-7">
										<input type="text" id="remark3" name="remark3" class="form-control"/>
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
	    	P.init_date("add_form","createTime");
			$('#add_form').bootstrapValidator({
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
		        	 userId: {
		                validators: {
		                    notEmpty: {
		                        message: 'userId不能为空'
		                    }
		                }
		             },
		        	 BillType: {
		                validators: {
		                    notEmpty: {
		                        message: 'BillType不能为空'
		                    }
		                }
		             },
		        	 payNumber: {
		                validators: {
		                    notEmpty: {
		                        message: 'payNumber不能为空'
		                    }
		                }
		             },
		        	 tradeName: {
		                validators: {
		                    notEmpty: {
		                        message: 'tradeName不能为空'
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
		        	 userAddress: {
		                validators: {
		                    notEmpty: {
		                        message: 'userAddress不能为空'
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
		        	 status: {
		                validators: {
		                    notEmpty: {
		                        message: 'status不能为空'
		                    }
		                }
		             },
		        	 remark1: {
		                validators: {
		                    notEmpty: {
		                        message: 'remark1不能为空'
		                    }
		                }
		             },
		        	 remark2: {
		                validators: {
		                    notEmpty: {
		                        message: 'remark2不能为空'
		                    }
		                }
		             },
		        	 remark3: {
		                validators: {
		                    notEmpty: {
		                        message: 'remark3不能为空'
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