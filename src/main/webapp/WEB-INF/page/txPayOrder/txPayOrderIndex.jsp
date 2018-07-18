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
</head>
<body>
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
															<label class="control-label" for="orderType">类型：</label>
															<input type="text" class="form-control" id="orderType" name="orderType">
														</div>
														<div class="form-group">
															<label class="control-label" for="payNumber">缴费号：</label>
															<input type="text" class="form-control" id="payNumber" name="payNumber">
														</div>
														<div class="form-group">
															<label class="control-label" for="orderNumber">订单编号：</label>
															<input type="text" class="form-control" id="orderNumber" name="orderNumber">
														</div>
														<div class="form-group">
															<label class="control-label" for="queryNumber">银联流水号：</label>
															<input type="text" class="form-control" id="queryNumber" name="queryNumber">
														</div>
														<div class="form-group">
															<label class="control-label" for="state">状态：</label>
															<select class="form-control" id="state" name="state">
																<option value="">--全部--</option>
																<option value="1">已支付</option>
																<option value="0">未支付</option>
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
														    	<perm:tag permPath="/txPayOrder/addTxPayOrder" >
														    	<button type="button" onclick="to_add()" class="btn btn-primary">
															        <i class="glyphicon glyphicon-plus"></i>添加
															    </button>
																</perm:tag>
																<perm:tag permPath="/txPayOrder/removeAllTxPayOrder" >
															    <button type="button" onclick="del_all()" class="btn btn-warning">
															        <i class="glyphicon glyphicon-trash"></i>删除
															    </button>
																</perm:tag>
															</div>
															<table id="table" data-toggle="table"
																data-toolbar="#toolbar"
														        data-url="${ctx}/txPayOrder/getTxPayOrderList"
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
																        <th data-field="orderType" data-align="center" data-formatter="formatterOrderType">缴费类型</th>
																        <th data-field="payNumber" data-align="center">缴费号</th>
																        <th data-field="orderNumber" data-align="center">订单编号</th>
																        <th data-field="queryNumber" data-align="center">银联流水号</th>
																        <th data-field="state" data-sort-name="state" data-align="center" data-formatter="formatterState" data-sortable="true">状态</th>
																        <th data-field="createTime" data-sort-name="createTime" data-align="center" data-formatter="formatterCreateTime" data-sortable="true">下单时间</th>
																        <th data-field="fee" data-align="center">订单金额</th>
																        <th data-field="userName" data-align="center">用户</th>
																        <th data-field="promoterName" data-align="center">推广员</th>
																        <th data-field="payWay" data-formatter="formatterPayWay" data-align="center">支付方式</th>
																        <th data-field="settleDate" data-align="center" data-formatter="formatterSettleDate">清算日期</th>
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
				orderType:$("#orderType").val(),
				payNumber:$("#payNumber").val(),
				orderNumber:$("#orderNumber").val(),
				queryNumber:$("#queryNumber").val(),
				state:$("#state").val(),
				createTime:$("#createTime").val(),
				fee:$("#fee").val(),
				realFee:$("#realFee").val(),
				userId:$("#userId").val(),
				userName:$("#userName").val(),
				promoterId:$("#promoterId").val(),
				promoterName:$("#promoterName").val(),
				twoPromoterId:$("#twoPromoterId").val(),
				twoPromoterName:$("#twoPromoterName").val(),
				oneRate:$("#oneRate").val(),
				twoRate:$("#twoRate").val(),
				devRate:$("#devRate").val(),
				totalRate:$("#totalRate").val(),
				accNo:$("#accNo").val(),
				payWay:$("#payWay").val(),
				settleDate:$("#settleDate").val(),
				limit:params.limit,
   				offset:params.offset,
   				sort: params.sort,
   				order:	params.order
    		}
    		return queryParam;
    	}
    	//操作按钮设置
	    function actionFormatter(value, row, index) {
	        return [
	            '<div style="min-width: 50px;">',
	            '<perm:tag permPath="/txPayOrder/updateTxPayOrder" >',
	            '<a class="edit" href="javascript:void(0)" title="编辑">',
	            '<i class="glyphicon glyphicon-edit"></i>',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/txPayOrder/removeTxPayOrder" >',
	            '<a class="remove" href="javascript:void(0)" title="删除">',
	            '<i class="glyphicon glyphicon-remove"></i>',
	            '</a>',
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
    	    }
    	};
    	function to_add(){
    		layer.open({
				title : "添加",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/txPayOrder/toAdd'
			 });
    	}
    	function toUpdate(id){
    		layer.open({
				title : "编辑",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/txPayOrder/toUpdate/'+id 
			 });
    	}
    	function del(id){
   		   if (id != ""){ 
   				$.post("<c:url value='/txPayOrder/removeTxPayOrder'/>",
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
    	function del_all(){
   	    	var ids = getIdSelections();
   	    	if(ids && ids!=''){
	    		sys.confirm('确认全部删除',
	    				'',
	    				'全部删除',
	    				'取消',
	    			function(){
	    	    	$.post("<c:url value='/txPayOrder/removeAllTxPayOrder'/>",
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
	    function formatterState(state){
	    	var s = "-";
	    	if(state!=null){
	    		if(state == '1'){
	    			s = '已支付';
	    		}
	    		if(state == '0'){
	    			s = '未支付';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterOrderType(orderType){
	    	var s = "-";
	    	if(orderType!=null){
	    		if(orderType == '001'){
	    			s = '水费';
	    		}
	    		if(orderType == '002'){
	    			s = '电费';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterPayWay(payWay){
	    	var s = "-";
	    	if(payWay!=null){
	    		if(payWay == '3'){
	    			s = '银联快捷支付';
	    		}
	    		if(payWay == '4'){
	    			s = '银联wap支付';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterCreateTime(createTime){
	    	return getFormatDateByLong(createTime,"yyyy-MM-dd");
	    }
	    function formatterSettleDate(settleDate){
	    	return getFormatDateByLong(settleDate,"yyyy-MM-dd");
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
    </script>
</body>
</html>
