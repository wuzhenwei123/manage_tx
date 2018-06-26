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
							<form id="add_form" action="${ctx}/txSellingOrder/addTxSellingOrder" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">code：</label>
									<div class="col-md-7">
										<input type="text" id="code" name="code" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">money：</label>
									<div class="col-md-7">
										<input type="text" id="money" name="money" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">createTime：</label>
									<div class="col-md-7">
										<input type="text" id="createTime" name="createTime" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">accNo：</label>
									<div class="col-md-7">
										<input type="text" id="accNo" name="accNo" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">endTime：</label>
									<div class="col-md-7">
										<input type="text" id="endTime" name="endTime" class="form-control"/>
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
									<label class="col-md-3 control-label">queryId：</label>
									<div class="col-md-7">
										<input type="text" id="queryId" name="queryId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">promoterId：</label>
									<div class="col-md-7">
										<input type="text" id="promoterId" name="promoterId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">twoPromoterId：</label>
									<div class="col-md-7">
										<input type="text" id="twoPromoterId" name="twoPromoterId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">wxUserName：</label>
									<div class="col-md-7">
										<input type="text" id="wxUserName" name="wxUserName" class="form-control"/>
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
									<label class="col-md-3 control-label">wxUserId：</label>
									<div class="col-md-7">
										<input type="text" id="wxUserId" name="wxUserId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">profitManey：</label>
									<div class="col-md-7">
										<input type="text" id="profitManey" name="profitManey" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">profits：</label>
									<div class="col-md-7">
										<input type="text" id="profits" name="profits" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">backCard：</label>
									<div class="col-md-7">
										<input type="text" id="backCard" name="backCard" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">refundState：</label>
									<div class="col-md-7">
										<input type="text" id="refundState" name="refundState" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">refundQueryId：</label>
									<div class="col-md-7">
										<input type="text" id="refundQueryId" name="refundQueryId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">refundAccNo：</label>
									<div class="col-md-7">
										<input type="text" id="refundAccNo" name="refundAccNo" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">refundCode：</label>
									<div class="col-md-7">
										<input type="text" id="refundCode" name="refundCode" class="form-control"/>
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
		        	 code: {
		                validators: {
		                    notEmpty: {
		                        message: 'code不能为空'
		                    }
		                }
		             },
		        	 money: {
		                validators: {
		                    notEmpty: {
		                        message: 'money不能为空'
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
		        	 accNo: {
		                validators: {
		                    notEmpty: {
		                        message: 'accNo不能为空'
		                    }
		                }
		             },
		        	 endTime: {
		                validators: {
		                    notEmpty: {
		                        message: 'endTime不能为空'
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
		        	 queryId: {
		                validators: {
		                    notEmpty: {
		                        message: 'queryId不能为空'
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
		        	 twoPromoterId: {
		                validators: {
		                    notEmpty: {
		                        message: 'twoPromoterId不能为空'
		                    }
		                }
		             },
		        	 wxUserName: {
		                validators: {
		                    notEmpty: {
		                        message: 'wxUserName不能为空'
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
		        	 wxUserId: {
		                validators: {
		                    notEmpty: {
		                        message: 'wxUserId不能为空'
		                    }
		                }
		             },
		        	 profitManey: {
		                validators: {
		                    notEmpty: {
		                        message: 'profitManey不能为空'
		                    }
		                }
		             },
		        	 profits: {
		                validators: {
		                    notEmpty: {
		                        message: 'profits不能为空'
		                    }
		                }
		             },
		        	 backCard: {
		                validators: {
		                    notEmpty: {
		                        message: 'backCard不能为空'
		                    }
		                }
		             },
		        	 refundState: {
		                validators: {
		                    notEmpty: {
		                        message: 'refundState不能为空'
		                    }
		                }
		             },
		        	 refundQueryId: {
		                validators: {
		                    notEmpty: {
		                        message: 'refundQueryId不能为空'
		                    }
		                }
		             },
		        	 refundAccNo: {
		                validators: {
		                    notEmpty: {
		                        message: 'refundAccNo不能为空'
		                    }
		                }
		             },
		        	 refundCode: {
		                validators: {
		                    notEmpty: {
		                        message: 'refundCode不能为空'
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