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
							<form id="update_form" action="${ctx}/txWxOrder/updateTxWxOrder" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" value="${txWxOrder.id}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">wxUserId：</label>
									<div class="col-md-7">
										<input type="text" id="wxUserId" name="wxUserId" value="${txWxOrder.wxUserId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">money：</label>
									<div class="col-md-7">
										<input type="text" id="money" name="money" value="${txWxOrder.money}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">createTime：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="createTime" name="createTime" value="<fmt:formatDate value="${txWxOrder.createTime}" type="both"/>" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">orderCode：</label>
									<div class="col-md-7">
										<input type="text" id="orderCode" name="orderCode" value="${txWxOrder.orderCode}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">accNo：</label>
									<div class="col-md-7">
										<input type="text" id="accNo" name="accNo" value="${txWxOrder.accNo}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
		                            <label class="col-sm-3 control-label">状态：</label>
		                            <div class="col-sm-7">
		                                <select id="state" name="state" class="form-control">
		                                 	<option <c:if test="${txWxOrder.state== 1}" >selected="selected"</c:if>  value="1">正常</option> 
		                        			<option <c:if test="${txWxOrder.state== 0}" >selected="selected"</c:if> value="0">禁用</option>
		                                </select>
		                        	</div>
		                        </div>
								<div class="form-group">
									<label class="col-md-3 control-label">style：</label>
									<div class="col-md-7">
										<input type="text" id="style" name="style" value="${txWxOrder.style}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">queryId：</label>
									<div class="col-md-7">
										<input type="text" id="queryId" name="queryId" value="${txWxOrder.queryId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">xfSettleDate：</label>
									<div class="col-md-7">
										<input type="text" id="xfSettleDate" name="xfSettleDate" value="${txWxOrder.xfSettleDate}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">xfFlg：</label>
									<div class="col-md-7">
										<input type="text" id="xfFlg" name="xfFlg" value="${txWxOrder.xfFlg}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">xfState：</label>
									<div class="col-md-7">
										<input type="text" id="xfState" name="xfState" value="${txWxOrder.xfState}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">dfState：</label>
									<div class="col-md-7">
										<input type="text" id="dfState" name="dfState" value="${txWxOrder.dfState}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">counterFee：</label>
									<div class="col-md-7">
										<input type="text" id="counterFee" name="counterFee" value="${txWxOrder.counterFee}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">dfFlg：</label>
									<div class="col-md-7">
										<input type="text" id="dfFlg" name="dfFlg" value="${txWxOrder.dfFlg}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">dfSettleDate：</label>
									<div class="col-md-7">
										<input type="text" id="dfSettleDate" name="dfSettleDate" value="${txWxOrder.dfSettleDate}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">traceNo：</label>
									<div class="col-md-7">
										<input type="text" id="traceNo" name="traceNo" value="${txWxOrder.traceNo}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">dfQueryId：</label>
									<div class="col-md-7">
										<input type="text" id="dfQueryId" name="dfQueryId" value="${txWxOrder.dfQueryId}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">orderAmt：</label>
									<div class="col-md-7">
										<input type="text" id="orderAmt" name="orderAmt" value="${txWxOrder.orderAmt}" class="form-control"/>
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
		        	 wxUserId: {
		                validators: {
		                    notEmpty: {
		                        message: 'wxUserId不能为空'
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
		        	 orderCode: {
		                validators: {
		                    notEmpty: {
		                        message: 'orderCode不能为空'
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
		        	 state: {
		                validators: {
		                    notEmpty: {
		                        message: 'state不能为空'
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
		        	 queryId: {
		                validators: {
		                    notEmpty: {
		                        message: 'queryId不能为空'
		                    }
		                }
		             },
		        	 xfSettleDate: {
		                validators: {
		                    notEmpty: {
		                        message: 'xfSettleDate不能为空'
		                    }
		                }
		             },
		        	 xfFlg: {
		                validators: {
		                    notEmpty: {
		                        message: 'xfFlg不能为空'
		                    }
		                }
		             },
		        	 xfState: {
		                validators: {
		                    notEmpty: {
		                        message: 'xfState不能为空'
		                    }
		                }
		             },
		        	 dfState: {
		                validators: {
		                    notEmpty: {
		                        message: 'dfState不能为空'
		                    }
		                }
		             },
		        	 counterFee: {
		                validators: {
		                    notEmpty: {
		                        message: 'counterFee不能为空'
		                    }
		                }
		             },
		        	 dfFlg: {
		                validators: {
		                    notEmpty: {
		                        message: 'dfFlg不能为空'
		                    }
		                }
		             },
		        	 dfSettleDate: {
		                validators: {
		                    notEmpty: {
		                        message: 'dfSettleDate不能为空'
		                    }
		                }
		             },
		        	 traceNo: {
		                validators: {
		                    notEmpty: {
		                        message: 'traceNo不能为空'
		                    }
		                }
		             },
		        	 dfQueryId: {
		                validators: {
		                    notEmpty: {
		                        message: 'dfQueryId不能为空'
		                    }
		                }
		             },
		        	 orderAmt: {
		                validators: {
		                    notEmpty: {
		                        message: 'orderAmt不能为空'
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