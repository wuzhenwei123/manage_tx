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
							<form id="update_form" action="${ctx}/txSellingOrder/updateTxSellingOrder" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" value="${txSellingOrder.id}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">code：</label>
									<div class="col-md-7">
										<input type="text" id="code" name="code" value="${txSellingOrder.code}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">money：</label>
									<div class="col-md-7">
										<input type="text" id="money" name="money" value="${txSellingOrder.money}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">createTime：</label>
									<div class="col-md-7">
										<input type="text" id="createTime" name="createTime" value="${txSellingOrder.createTime}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">accNo：</label>
									<div class="col-md-7">
										<input type="text" id="accNo" name="accNo" value="${txSellingOrder.accNo}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">endTime：</label>
									<div class="col-md-7">
										<input type="text" id="endTime" name="endTime" value="${txSellingOrder.endTime}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
		                            <label class="col-sm-3 control-label">状态：</label>
		                            <div class="col-sm-7">
		                                <select id="state" name="state" class="form-control">
		                                 	<option <c:if test="${txSellingOrder.state== 1}" >selected="selected"</c:if>  value="1">正常</option> 
		                        			<option <c:if test="${txSellingOrder.state== 0}" >selected="selected"</c:if> value="0">禁用</option>
		                                </select>
		                        	</div>
		                        </div>
								<div class="form-group">
									<label class="col-md-3 control-label">queryId：</label>
									<div class="col-md-7">
										<input type="text" id="queryId" name="queryId" value="${txSellingOrder.queryId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">promoterId：</label>
									<div class="col-md-7">
										<input type="text" id="promoterId" name="promoterId" value="${txSellingOrder.promoterId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">twoPromoterId：</label>
									<div class="col-md-7">
										<input type="text" id="twoPromoterId" name="twoPromoterId" value="${txSellingOrder.twoPromoterId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">wxUserName：</label>
									<div class="col-md-7">
										<input type="text" id="wxUserName" name="wxUserName" value="${txSellingOrder.wxUserName}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">oneRate：</label>
									<div class="col-md-7">
										<input type="text" id="oneRate" name="oneRate" value="${txSellingOrder.oneRate}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">twoRate：</label>
									<div class="col-md-7">
										<input type="text" id="twoRate" name="twoRate" value="${txSellingOrder.twoRate}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">devRate：</label>
									<div class="col-md-7">
										<input type="text" id="devRate" name="devRate" value="${txSellingOrder.devRate}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">wxUserId：</label>
									<div class="col-md-7">
										<input type="text" id="wxUserId" name="wxUserId" value="${txSellingOrder.wxUserId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">profitManey：</label>
									<div class="col-md-7">
										<input type="text" id="profitManey" name="profitManey" value="${txSellingOrder.profitManey}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">profits：</label>
									<div class="col-md-7">
										<input type="text" id="profits" name="profits" value="${txSellingOrder.profits}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">backCard：</label>
									<div class="col-md-7">
										<input type="text" id="backCard" name="backCard" value="${txSellingOrder.backCard}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">refundState：</label>
									<div class="col-md-7">
										<input type="text" id="refundState" name="refundState" value="${txSellingOrder.refundState}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">refundQueryId：</label>
									<div class="col-md-7">
										<input type="text" id="refundQueryId" name="refundQueryId" value="${txSellingOrder.refundQueryId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">refundAccNo：</label>
									<div class="col-md-7">
										<input type="text" id="refundAccNo" name="refundAccNo" value="${txSellingOrder.refundAccNo}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">refundCode：</label>
									<div class="col-md-7">
										<input type="text" id="refundCode" name="refundCode" value="${txSellingOrder.refundCode}" class="form-control"/>
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