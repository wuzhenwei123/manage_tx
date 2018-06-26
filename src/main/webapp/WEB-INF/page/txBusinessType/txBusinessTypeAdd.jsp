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
							<form id="add_form" action="${ctx}/txBusinessType/addTxBusinessType" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">billType：</label>
									<div class="col-md-7">
										<input type="text" id="billType" name="billType" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">feeTypeDesc：</label>
									<div class="col-md-7">
										<input type="text" id="feeTypeDesc" name="feeTypeDesc" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">billArea：</label>
									<div class="col-md-7">
										<input type="text" id="billArea" name="billArea" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">chargeUnit：</label>
									<div class="col-md-7">
										<input type="text" id="chargeUnit" name="chargeUnit" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">isNeedDate：</label>
									<div class="col-md-7">
										<input type="text" id="isNeedDate" name="isNeedDate" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">cityCode：</label>
									<div class="col-md-7">
										<input type="text" id="cityCode" name="cityCode" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">serviceType：</label>
									<div class="col-md-7">
										<input type="text" id="serviceType" name="serviceType" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">addInfo：</label>
									<div class="col-md-7">
										<input type="text" id="addInfo" name="addInfo" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">bigLetters：</label>
									<div class="col-md-7">
										<input type="text" id="bigLetters" name="bigLetters" class="form-control"/>
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
		        	 billType: {
		                validators: {
		                    notEmpty: {
		                        message: 'billType不能为空'
		                    }
		                }
		             },
		        	 feeTypeDesc: {
		                validators: {
		                    notEmpty: {
		                        message: 'feeTypeDesc不能为空'
		                    }
		                }
		             },
		        	 billArea: {
		                validators: {
		                    notEmpty: {
		                        message: 'billArea不能为空'
		                    }
		                }
		             },
		        	 chargeUnit: {
		                validators: {
		                    notEmpty: {
		                        message: 'chargeUnit不能为空'
		                    }
		                }
		             },
		        	 isNeedDate: {
		                validators: {
		                    notEmpty: {
		                        message: 'isNeedDate不能为空'
		                    }
		                }
		             },
		        	 cityCode: {
		                validators: {
		                    notEmpty: {
		                        message: 'cityCode不能为空'
		                    }
		                }
		             },
		        	 serviceType: {
		                validators: {
		                    notEmpty: {
		                        message: 'serviceType不能为空'
		                    }
		                }
		             },
		        	 addInfo: {
		                validators: {
		                    notEmpty: {
		                        message: 'addInfo不能为空'
		                    }
		                }
		             },
		        	 bigLetters: {
		                validators: {
		                    notEmpty: {
		                        message: 'bigLetters不能为空'
		                    }
		                }
		             },
		        	 state: {
		                validators: {
		                    notEmpty: {
		                        message: 'state不能为空'
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