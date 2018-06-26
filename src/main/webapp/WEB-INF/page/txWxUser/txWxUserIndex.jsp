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
	<%@ include file="/WEB-INF/page/common/css.jsp" %>
	<style type="text/css">
	.maskbg {
			background: rgba(0, 0, 0, .7);
			display: none;
			height: 100%;
			left: 0;
			position: fixed;
			top: 0;
			width: 100%;
			z-index: 19999;
			overflow: auto;
			text-align: center;
		}
	</style>
</head>
<body>
	<c:set value="<%=ConfigConstants.DB_ROLE_ID %>" var="dbrole"></c:set>
	<c:set value="<%=ConfigConstants.TWO_DB_ROLE_ID %>" var="dbrole2"></c:set>
	<!--头部导航开始-->
	<%@ include file="/WEB-INF/page/common/head.jsp" %>
	<!--头部导航开始-->
	<div class="${sys_layout }" id="content-container">
		<div class="content-wrapper">
			<div class="row">
				<div class="side-nav-content">
					 <!--左侧导航开始-->
			        <%@ include file="/WEB-INF/page/common/left.jsp" %>
			        <!--左侧导航结束-->
			        
			        <div class="main-content-wrapper">
						<div class="main-content">
							<section>
								<div class="container-fluid container-padded">
									<div class="row">
										<div class="col-md-12">
											<div class="panel panel-plain">
												<div class="panel-heading">
													<h3 class="panel-title">查询</h3>
													<ul class="panel-tools pull-right">
														<li>
															<a href="javascript:;" class="btn btn-sm search-tools-btn"><i class="fa fa-chevron-down"></i></a>
														</li>
													</ul>
												</div>
													<form class="form-inline search-form" role="form" id="query_form">
													<div class="panel-body">
														<div class="form-group">
															<label class="control-label" for="realName">姓名：</label>
															<input type="text" class="form-control" id="realName" name="realName">
														</div>
														<div class="form-group">
															<label class="control-label" for="IDNumber">身份证号：</label>
															<input type="text" class="form-control" id="IDNumber" name="IDNumber">
														</div>
														<div class="form-group">
															<label class="control-label" for="cardNumber">银行卡号：</label>
															<input type="text" class="form-control" id="cardNumber" name="cardNumber">
														</div>
														<div class="form-group">
															<label class="control-label" for="mobile">手机：</label>
															<input type="text" class="form-control" id="mobile" name="mobile">
														</div>
														<div class="form-group">
															<label class="control-label" for="state">状态：</label>
															<select class="form-control" id="state" name="state">
																<option value="">--全部--</option>
																<option value="1">正常</option>
																<option value="0">禁用</option>
															</select>
														</div>
														<div class="form-group">
															<label class="control-label" for="checkState">接口验证状态：</label>
															<select class="form-control" id="checkState" name="checkState">
																<option value="">--全部--</option>
																<option value="1">通过</option>
																<option value="0">失败</option>
															</select>
														</div>
														<div class="form-group">
															<button type="reset" class="btn btn-default">重置</button>
															<button type="button" class="btn btn-primary" onclick="search()">查询</button>
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="panel panel-plain">
												<div class="panel-heading">数据列表</div>
									    		<div class="panel-body" style="padding-top: 0px;">
										    		<div class="row">
										            	<div class="col-sm-12">
											    			<div id="toolbar">
														    	<perm:tag permPath="/txWxUser/addTxWxUser" >
														    	<button type="button" onclick="to_add()" class="btn btn-primary">
															        <i class="glyphicon glyphicon-plus"></i>添加
															    </button>
																</perm:tag>
																<perm:tag permPath="/txWxUser/removeAllTxWxUser" >
															    <button type="button" onclick="del_all()" class="btn btn-warning">
															        <i class="glyphicon glyphicon-trash"></i>删除
															    </button>
																</perm:tag>
															</div>
															<table id="table" data-toggle="table"
																data-toolbar="#toolbar"
														        data-url="${ctx}/txWxUser/getTxWxUserList"
														        data-pagination="true"
														        data-show-refresh="true"
														        data-side-pagination="server"
														        data-query-params="queryParams"
														        data-page-list="[10, 50, 100, 200]"
														        data-show-toggle="true"
									           					data-show-columns="true"
									           					data-striped=true   
									           					data-sort-name='id'
									           					data-page-number=1
									           					data-page-size=10
									           					data-sort-order="desc"
									           					data-id-field="id"
														        >
															    <thead>
																    <tr>
																        <th data-checkbox="true"></th>
																        <th data-field="mobile" data-align="center">手机</th>
																        <th data-field="realName" data-align="center">姓名</th>
																        <th data-field="nickName" data-align="center">昵称</th>
																        <c:if test="${dbrole!=admin_user.role_id&&dbrole2!=admin_user.role_id}">
																        <th data-field="iDNumber" data-align="center" data-formatter="formatterIDNumber">身份证号</th>
																        <th data-field="cardNumber" data-align="center" data-formatter="formatterCardNumber">银行卡号</th>
																        </c:if>
																        <th data-field="state" data-sort-name="state" data-align="center" data-formatter="formatterState" data-sortable="true">状态</th>
																        <th data-field="checkState" data-formatter="formatterCheckState" data-align="center">信息验证（易付通）</th>
																        <c:if test="${dbrole!=admin_user.role_id&&dbrole2!=admin_user.role_id}">
																        <th data-field="promoterName" data-align="center">一级业务员</th>
																        <th data-field="twoPromoterName" data-align="center">二级级业务员</th>
																		</c:if>								        
																        <th data-field="createTime" data-sort-name="create_time" data-align="center" data-formatter="formatterCreateTime" data-sortable="true">注册时间</th>
																        <th data-field="msg" data-align="center">验证失败原因</th>
																		<th data-align="center" data-formatter="actionFormatter" data-events="actionEvents">操作</th>
																    </tr>
															    </thead>
															</table>
										            	</div>
										            </div>
									    		</div>
									    	</div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="preview maskbg" data-p="" onclick="closePreview()">
		<img src="images/icon.jpg" />
	</div>
	<%@ include file="/WEB-INF/page/common/js.jsp" %>
    <script type="text/javascript">
	    var $table = $('#table');
		$(document).ready(function() {
	    	P.init_date("query_form","createTime");
	    });
    	//检索入口
    	function search(){
    		$table.bootstrapTable('refresh');
    	}
    	function queryParams(params){
    		var queryParam = {
				id:$("#id").val(),
				nickName:$("#nickName").val(),
				openId:$("#openId").val(),
				headUrl:$("#headUrl").val(),
				sex:$("#sex").val(),
				createTime:$("#createTime").val(),
				realName:$("#realName").val(),
				IDNumber:$("#IDNumber").val(),
				cardNumber:$("#cardNumber").val(),
				IDUrl:$("#IDUrl").val(),
				IDFanUrl:$("#IDFanUrl").val(),
				IDPersonUrl:$("#IDPersonUrl").val(),
				cardUrl:$("#cardUrl").val(),
				mobile:$("#mobile").val(),
				state:$("#state").val(),
				openBank:$("#openBank").val(),
				promoterId:$("#promoterId").val(),
				checkState:$("#checkState").val(),
				limit:params.limit,
   				offset:params.offset,
   				sort: params.sort,
   				order:	params.order
    		}
    		return queryParam;
    	}
    	//操作按钮设置
	    function actionFormatter(value, row, index) {
	    	var shenhe = '';
	    	if(row.state=="0"&&row.checkState=="1"){
	    		shenhe = '<a class="setState" href="javascript:void(0)" title="审核">审核</a>';
    		}
	        return [
	            '<div style="min-width: 50px;">',
	            '<perm:tag permPath="/txWxUser/updateTxWxUser" >',
	            '<a class="edit" href="javascript:void(0)" title="编辑">',
	            '<i class="glyphicon glyphicon-edit"></i>',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/txWxUser/removeTxWxUser" >',
	            '<a class="remove" href="javascript:void(0)" title="删除">',
	            '<i class="glyphicon glyphicon-remove"></i>',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/txWxUser/setState" >',
	            shenhe,
	            '</perm:tag>',
	            '</div>'
	        ].join('');
	    }
    	//操作事件
	    window.actionEvents = {
    	    'click .edit': function (e, value, row, index) {
    	        toUpdate(row.id);
    	    },
    	    'click .remove': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'是否要删除',
	    			'',
	    			'删除',
	    			'取消',
	    		function(){
	    			del(row.id);
	    		},'')
    	    },
    	    'click .setState': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'是否要审核',
	    			'',
	    			'确定',
	    			'取消',
	    		function(){
    	    		setState(row.id);
	    		},'')
    	    }
    	};
    	function to_add(){
    		layer.open({
				title : "添加",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/txWxUser/toAdd'
			 });
    	}
    	function toUpdate(id){
    		layer.open({
				title : "编辑",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/txWxUser/toUpdate/'+id 
			 });
    	}
    	function del(id){
   		   if (id != ""){ 
   				$.post("<c:url value='/txWxUser/removeTxWxUser'/>",
	        	{
					id	:id,
					ranNum:Math.random()
				},
	        	function(data){
		        	var result = eval('('+data+')'); 
		            if (result.code == '1') {
		            	sys.success(result.message,'',function(){
		                	search();
		                });
		             } else {
		             	sys.error(result.message,'',function(){
		                });
		             }
		        });
	   	  	}
	  	 }
    	function setState(id){
   		   if (id != ""){ 
   				$.post("<c:url value='/txWxUser/setState'/>",
	        	{
					id	:id,
					state	:1,
					ranNum:Math.random()
				},
	        	function(data){
		        	var result = eval('('+data+')'); 
		            if (result.code == '1') {
		            	sys.success(result.message,'',function(){
		                	search();
		                });
		             } else {
		             	sys.error(result.message,'',function(){
		                });
		             }
		        });
	   	  	}
	  	 }
    	function del_all(){
   	    	var ids = getIdSelections();
   	    	if(ids && ids!=''){
	    		sys.confirm('确认全部删除',
	    				'',
	    				'全部删除',
	    				'取消',
	    			function(){
	    	    	$.post("<c:url value='/txWxUser/removeAllTxWxUser'/>",
	            	{
	    	    		ids :JSON.stringify(ids),
	    				ranNum:Math.random()},
	    	        	function(data){
    		        	var result = eval('('+data+')'); 
    		            if (result.code == '1') {
    		            	sys.success(result.message,'',function(){
			                	search();
			                });
    		             }else{
    		             	sys.error(result.message,'',function(){});
    		             }
	    	        });
		    	},'')
   	    	}
    	}
    	// 以下是扩展
	    function formatterCreateTime(createTime){
	    	return getFormatDateByLong(createTime,"yyyy-MM-dd");
	    }
	    function formatterState(state){
	    	var s = "-";
	    	if(state!=null){
	    		if(state == '1'){
	    			s = '正常';
	    		}
	    		if(state == '0'){
	    			s = '禁用';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterCheckState(checkState){
	    	var s = "-";
	    	if(checkState!=null){
	    		if(checkState == '1'){
	    			s = '通过';
	    		}
	    		if(checkState == '0'){
	    			s = '失败';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterIDNumber(value, row, index){
	    	var s = "<a href='javascript:showID("+row.id+")'>"+row.iDNumber+"</a>";
	    	return s;	
	    }
	    function formatterCardNumber(value, row, index){
	    	var s = "<a href='javascript:showCard("+row.id+")'>"+row.cardNumber+"</a>";
	    	return s;	
	    }
	    /**
	     * 获取表格checkbox
	     * @returns
	     */
	    function getIdSelections() {
	        return $.map($table.bootstrapTable('getSelections'), function (row) {
	            return row.id;
	        });
	    }
	    
	    function showID(id){
	    	layer.open({
				title : "查看详情",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/txWxUser/showID/'+id 
			 });
	    }
	    function showCard(id){
	    	layer.open({
				title : "查看详情",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/txWxUser/showCard/'+id 
			 });
	    }
	    
	    function showPic(obj) {
			//获取图片的宽和高
			var image = new Image();
			image.src = obj.src;
			var naturalWidth = image.width;
			var naturalHight = image.height;
			$(".preview").find("img").attr('src', obj.src);
			$(".preview").find("img").attr('width', naturalWidth);
			$(".preview").find("img").attr('height', naturalHight);
			$(".preview").show();
		}

		function closePreview() {
			$(".preview").hide();
		}
    </script>
</body>
</html>
