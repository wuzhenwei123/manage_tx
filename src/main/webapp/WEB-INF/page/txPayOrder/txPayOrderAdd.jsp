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
							<form id="add_form" action="${ctx}/txPayOrder/addTxPayOrder" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">orderType：</label>
									<div class="col-md-7">
										<input type="text" id="orderType" name="orderType" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">payNumber：</label>
									<div class="col-md-7">
										<input type="text" id="payNumber" name="payNumber" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">orderNumber：</label>
									<div class="col-md-7">
										<input type="text" id="orderNumber" name="orderNumber" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">queryNumber：</label>
									<div class="col-md-7">
										<input type="text" id="queryNumber" name="queryNumber" class="form-control"/>
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
									<label class="col-md-3 control-label">createTime：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="createTime" name="createTime" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">fee：</label>
									<div class="col-md-7">
										<input type="text" id="fee" name="fee" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">realFee：</label>
									<div class="col-md-7">
										<input type="text" id="realFee" name="realFee" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">userId：</label>
									<div class="col-md-7">
										<input type="text" id="userId" name="userId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">userName：</label>
									<div class="col-md-7">
										<input type="text" id="userName" name="userName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">promoterId：</label>
									<div class="col-md-7">
										<input type="text" id="promoterId" name="promoterId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">promoterName：</label>
									<div class="col-md-7">
										<input type="text" id="promoterName" name="promoterName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">twoPromoterId：</label>
									<div class="col-md-7">
										<input type="text" id="twoPromoterId" name="twoPromoterId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">twoPromoterName：</label>
									<div class="col-md-7">
										<input type="text" id="twoPromoterName" name="twoPromoterName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">oneRate：</label>
									<div class="col-md-7">
										<input type="text" id="oneRate" name="oneRate" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">twoRate：</label>
									<div class="col-md-7">
										<input type="text" id="twoRate" name="twoRate" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">devRate：</label>
									<div class="col-md-7">
										<input type="text" id="devRate" name="devRate" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">totalRate：</label>
									<div class="col-md-7">
										<input type="text" id="totalRate" name="totalRate" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">accNo：</label>
									<div class="col-md-7">
										<input type="text" id="accNo" name="accNo" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">payWay：</label>
									<div class="col-md-7">
										<input type="text" id="payWay" name="payWay" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">settleDate：</label>
									<div class="col-md-7">
										<input type="text" id="settleDate" name="settleDate" class="form-control"/>
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
		        	 orderType: {
		                validators: {
		                    notEmpty: {
		                        message: 'orderType不能为空'
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
		        	 orderNumber: {
		                validators: {
		                    notEmpty: {
		                        message: 'orderNumber不能为空'
		                    }
		                }
		             },
		        	 queryNumber: {
		                validators: {
		                    notEmpty: {
		                        message: 'queryNumber不能为空'
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
		        	 createTime: {
		                validators: {
		                    notEmpty: {
		                        message: 'createTime不能为空'
		                    }
		                }
		             },
		        	 fee: {
		                validators: {
		                    notEmpty: {
		                        message: 'fee不能为空'
		                    }
		                }
		             },
		        	 realFee: {
		                validators: {
		                    notEmpty: {
		                        message: 'realFee不能为空'
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
		        	 userName: {
		                validators: {
		                    notEmpty: {
		                        message: 'userName不能为空'
		                    }
		                }
		             },
		        	 promoterId: {
		                validators: {
		                    notEmpty: {
		                        message: 'promoterId不能为空'
		                    }
		                }
		             },
		        	 promoterName: {
		                validators: {
		                    notEmpty: {
		                        message: 'promoterName不能为空'
		                    }
		                }
		             },
		        	 twoPromoterId: {
		                validators: {
		                    notEmpty: {
		                        message: 'twoPromoterId不能为空'
		                    }
		                }
		             },
		        	 twoPromoterName: {
		                validators: {
		                    notEmpty: {
		                        message: 'twoPromoterName不能为空'
		                    }
		                }
		             },
		        	 oneRate: {
		                validators: {
		                    notEmpty: {
		                        message: 'oneRate不能为空'
		                    }
		                }
		             },
		        	 twoRate: {
		                validators: {
		                    notEmpty: {
		                        message: 'twoRate不能为空'
		                    }
		                }
		             },
		        	 devRate: {
		                validators: {
		                    notEmpty: {
		                        message: 'devRate不能为空'
		                    }
		                }
		             },
		        	 totalRate: {
		                validators: {
		                    notEmpty: {
		                        message: 'totalRate不能为空'
		                    }
		                }
		             },
		        	 accNo: {
		                validators: {
		                    notEmpty: {
		                        message: 'accNo不能为空'
		                    }
		                }
		             },
		        	 payWay: {
		                validators: {
		                    notEmpty: {
		                        message: 'payWay不能为空'
		                    }
		                }
		             },
		        	 settleDate: {
		                validators: {
		                    notEmpty: {
		                        message: 'settleDate不能为空'
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