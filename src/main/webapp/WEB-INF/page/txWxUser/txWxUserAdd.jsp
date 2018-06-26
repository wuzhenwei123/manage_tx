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
							<form id="add_form" action="${ctx}/txWxUser/addTxWxUser" method="post" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-3 control-label">id：</label>
									<div class="col-md-7">
										<input type="text" id="id" name="id" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">nickName：</label>
									<div class="col-md-7">
										<input type="text" id="nickName" name="nickName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">openId：</label>
									<div class="col-md-7">
										<input type="text" id="openId" name="openId" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">headUrl：</label>
									<div class="col-md-7">
										<input type="text" id="headUrl" name="headUrl" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">sex：</label>
									<div class="col-md-7">
										<input type="text" id="sex" name="sex" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">createTime：</label>
									<div class="col-md-7">
										<input type="text" readonly="readonly" id="createTime" name="createTime" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">realName：</label>
									<div class="col-md-7">
										<input type="text" id="realName" name="realName" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">IDNumber：</label>
									<div class="col-md-7">
										<input type="text" id="IDNumber" name="IDNumber" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">cardNumber：</label>
									<div class="col-md-7">
										<input type="text" id="cardNumber" name="cardNumber" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">IDUrl：</label>
									<div class="col-md-7">
										<input type="text" id="IDUrl" name="IDUrl" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">IDFanUrl：</label>
									<div class="col-md-7">
										<input type="text" id="IDFanUrl" name="IDFanUrl" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">IDPersonUrl：</label>
									<div class="col-md-7">
										<input type="text" id="IDPersonUrl" name="IDPersonUrl" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">cardUrl：</label>
									<div class="col-md-7">
										<input type="text" id="cardUrl" name="cardUrl" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">mobile：</label>
									<div class="col-md-7">
										<input type="text" id="mobile" name="mobile" class="form-control"/>
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
									<label class="col-md-3 control-label">openBank：</label>
									<div class="col-md-7">
										<input type="text" id="openBank" name="openBank" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-3 control-label">promoterId：</label>
									<div class="col-md-7">
										<input type="text" id="promoterId" name="promoterId" class="form-control"/>
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
		        	 nickName: {
		                validators: {
		                    notEmpty: {
		                        message: 'nickName不能为空'
		                    }
		                }
		             },
		        	 openId: {
		                validators: {
		                    notEmpty: {
		                        message: 'openId不能为空'
		                    }
		                }
		             },
		        	 headUrl: {
		                validators: {
		                    notEmpty: {
		                        message: 'headUrl不能为空'
		                    }
		                }
		             },
		        	 sex: {
		                validators: {
		                    notEmpty: {
		                        message: 'sex不能为空'
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
		        	 realName: {
		                validators: {
		                    notEmpty: {
		                        message: 'realName不能为空'
		                    }
		                }
		             },
		        	 IDNumber: {
		                validators: {
		                    notEmpty: {
		                        message: 'IDNumber不能为空'
		                    }
		                }
		             },
		        	 cardNumber: {
		                validators: {
		                    notEmpty: {
		                        message: 'cardNumber不能为空'
		                    }
		                }
		             },
		        	 IDUrl: {
		                validators: {
		                    notEmpty: {
		                        message: 'IDUrl不能为空'
		                    }
		                }
		             },
		        	 IDFanUrl: {
		                validators: {
		                    notEmpty: {
		                        message: 'IDFanUrl不能为空'
		                    }
		                }
		             },
		        	 IDPersonUrl: {
		                validators: {
		                    notEmpty: {
		                        message: 'IDPersonUrl不能为空'
		                    }
		                }
		             },
		        	 cardUrl: {
		                validators: {
		                    notEmpty: {
		                        message: 'cardUrl不能为空'
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
		        	 state: {
		                validators: {
		                    notEmpty: {
		                        message: 'state不能为空'
		                    }
		                }
		             },
		        	 openBank: {
		                validators: {
		                    notEmpty: {
		                        message: 'openBank不能为空'
		                    }
		                }
		             },
		        	 promoterId: {
		                validators: {
		                    notEmpty: {
		                        message: 'promoterId不能为空'
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