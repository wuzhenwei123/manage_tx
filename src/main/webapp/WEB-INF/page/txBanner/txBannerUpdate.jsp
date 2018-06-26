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
							<form id="update_form" action="${ctx}/txBanner/updateTxBanner" method="post" class="form-horizontal">
								<input type="hidden" id="id" name="id" value="${txBanner.id}" class="form-control"/>
								<div class="form-group">
									<label class="col-md-3 control-label">图片：</label>
									<div class="col-md-7">
										<table>
											<tr>
												<div id="pic_list">
																<img class="img_view" src="${txBanner.imgUrl}" onerror="${ctx }/images/default.png" style="width: 100px;height: 100px;">
												</div>
												
												<td id="upload_btn">
													<span id="picker">选择图片</span>
												</td>
											</tr>
										</table>
										<input type="hidden" id="imgUrl" name="imgUrl" value="${txBanner.imgUrl}" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
		                            <label class="col-sm-3 control-label">状态：</label>
		                            <div class="col-sm-7">
		                                <select id="state" name="state" class="form-control">
		                                 	<option <c:if test="${txBanner.state== 1}" >selected="selected"</c:if>  value="1">正常</option> 
		                        			<option <c:if test="${txBanner.state== 0}" >selected="selected"</c:if> value="0">禁用</option>
		                                </select>
		                        	</div>
		                        </div>
								<div class="form-group">
									<label class="col-md-3 control-label">跳转地址：</label>
									<div class="col-md-7">
										<input type="text" id="targetUrl" name="targetUrl" value="${txBanner.targetUrl}" class="form-control"/>
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
			upload('${ctx}','picker',pic_callback,'','sys_pic','pic');//上传插件
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
		        	 imgUrl: {
		                validators: {
		                    notEmpty: {
		                        message: 'imgUrl不能为空'
		                    }
		                }
		             },
		        	 state: {
		                validators: {
		                    notEmpty: {
		                        message: 'state不能为空'
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
		
		function pic_callback(response){
			if(response.code == '1'){
				var fileList = response.list;
				if(fileList.length == 1){
					var newFileName = fileList[0].filePath;
					var fileUrl = "${pic}"+newFileName;
					$("#pic_list").html(getpichtml(fileUrl));
					$("#imgUrl").val(fileUrl);
				}
			}else{
				tipError("上传异常");
			}
		}
		function getpichtml(src){
			var img_view_len = $(".img_view").length;
			var id = "td_"+img_view_len;
			var html = '';
			html += '<td id="'+id+'">';
			html += '	<div class="fileinput fileinput-new" data-provides="fileinput">';
			html += '		<div class="fileinput-preview thumbnail" data-trigger="fileinput">';
			html += '			<img class="img_view" src="'+src+'" onerror="${ctx }/images/default.png" style="width: 100px;height: 100px;">';
			html += '		</div>';
			html += '	</div>';
			html += '</td>';
			return html;
			
		}
    </script>
</body>
</html>