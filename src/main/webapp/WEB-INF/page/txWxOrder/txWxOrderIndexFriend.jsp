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
															<label class="control-label" for="wxUserId">二级业务员：</label>
															<select class="form-control" id="wxUserId" name="wxUserId">
																<option value="">--全部--</option>
																<c:forEach items="${listDb}" var="db">
																	<option value="${db.adminId}">${db.realName}</option>
																</c:forEach>
															</select>
														</div>
														
														<div class="form-group">
															<label class="control-label" for="orderCode">订单号：</label>
															<input type="text" class="form-control" id="orderCode" name="orderCode">
														</div>
														<div class="form-group">
															<label class="control-label" for="accNo">银行卡号：</label>
															<input type="text" class="form-control" id="accNo" name="accNo">
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
															<label class="control-label" for="createTime">交易日期：</label>
															<input type="text" class="form-control" id="startTime" name="startTime">
															至
															<input type="text" class="form-control" id="endTime" name="endTime">
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
														    	<perm:tag permPath="/txWxOrder/addTxWxOrder" >
														    	<button type="button" onclick="to_add()" class="btn btn-primary">
															        <i class="glyphicon glyphicon-plus"></i>添加
															    </button>
																</perm:tag>
																<perm:tag permPath="/txWxOrder/removeAllTxWxOrder" >
															    <button type="button" onclick="del_all()" class="btn btn-warning">
															        <i class="glyphicon glyphicon-trash"></i>删除
															    </button>
																</perm:tag>
															</div>
															<table id="table" data-toggle="table"
																data-toolbar="#toolbar"
														        data-url="${ctx}/txWxOrder/getTxWxOrderListByMyFriend"
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
																        <th data-field="wxUserName" data-align="center">用户名称</th>
																        <th data-field="money" data-formatter="formatterMoney" data-align="center">金额</th>
																        <th data-field="orderCode" data-align="center">订单号</th>
																        <c:if test="${dbrole!=admin_user.role_id&&dbrole2!=admin_user.role_id}">
																        <th data-field="accNo" data-align="center">银行卡号</th>
																        <th data-field="queryId" data-align="center">银行流水号</th>
																        </c:if>
																        <th data-field="xfState" data-formatter="formatterXFState" data-align="center">消费状态</th>
																        <th data-field="dfState" data-formatter="formatterDFState" data-align="center">代付状态</th>
																        <th data-field="createTime" data-sort-name="create_time" data-align="center" data-formatter="formatterCreateTime" data-sortable="true">交易时间</th>
																		<th data-field="twoPromoterName" data-align="center">二级业务员</th>
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
				wxUserId:$("#wxUserId").val(),
				money:$("#money").val(),
				createTime:$("#createTime").val(),
				orderCode:$("#orderCode").val(),
				accNo:$("#accNo").val(),
				startTime:$("#startTime").val(),
    			endTime:$("#endTime").val(),
				state:$("#state").val(),
				style:1,
				queryId:$("#queryId").val(),
				xfSettleDate:$("#xfSettleDate").val(),
				xfFlg:$("#xfFlg").val(),
				xfState:$("#xfState").val(),
				dfState:$("#dfState").val(),
				counterFee:$("#counterFee").val(),
				dfFlg:$("#dfFlg").val(),
				dfSettleDate:$("#dfSettleDate").val(),
				traceNo:$("#traceNo").val(),
				dfQueryId:$("#dfQueryId").val(),
				orderAmt:$("#orderAmt").val(),
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
	    	if(row.xfState=="1"&&row.dfState=="0"){
	    		shenhe = '<perm:tag permPath="/txWxOrder/updateDF" ><a class="updateDF" href="javascript:void(0)" title="设为代付">设为代付</a></perm:tag>';
    		}
    		
	        return [
	            '<div style="min-width: 50px;">',
	            '<perm:tag permPath="/txWxOrder/updateTxWxOrder" >',
	            '<a class="edit" href="javascript:void(0)" title="编辑">',
	            '<i class="glyphicon glyphicon-edit"></i>',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/txWxOrder/removeTxWxOrder" >',
	            '<a class="remove" href="javascript:void(0)" title="删除">',
	            '<i class="glyphicon glyphicon-remove"></i>',
	            '</a>',
	            '</perm:tag>',
	            shenhe,
	            '</div>'
	        ].join('');
	    }
    	//操作事件
	    window.actionEvents = {
    	    'click .edit': function (e, value, row, index) {
    	        toUpdate(row.id);
    	    },
    	    'click .updateDF': function (e, value, row, index) {
    	    	sys.confirm(
    	    		'确定要执行此操作吗',
	    			'',
	    			'确定',
	    			'取消',
	    		function(){
    	    		updateDF(row.id);
	    		},'')
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
			  	content: '${ctx}/txWxOrder/toAdd'
			 });
    	}
    	function toUpdate(id){
    		layer.open({
				title : "编辑",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/txWxOrder/toUpdate/'+id 
			 });
    	}
    	function del(id){
   		   if (id != ""){ 
   				$.post("<c:url value='/txWxOrder/removeTxWxOrder'/>",
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
    	function updateDF(id){
   		   if (id != ""){ 
   				$.post("<c:url value='/txWxOrder/updateDF'/>",
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
	    	    	$.post("<c:url value='/txWxOrder/removeAllTxWxOrder'/>",
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
	    function formatterXFState(xfState){
	    	var s = "-";
	    	if(xfState!=null){
	    		if(xfState == '1'){
	    			s = '已消费';
	    		}
	    		if(xfState == '0'){
	    			s = '未消费';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterDFState(dfState){
	    	var s = "-";
	    	if(dfState!=null){
	    		if(dfState == '1'){
	    			s = '已代付';
	    		}
	    		if(dfState == '0'){
	    			s = '未代付';
	    		}
	    	}
	    	return s;	
	    }
	    function formatterMoney(money){
	    	var totalFeeStr1 = null;
			if(money!=null&&money>0){
				var money1 = money + "";
				if(money1.length>2){
					var fen = money%100;
					if(fen>0&&fen<10){
						totalFeeStr1 = money/100 + ".0" + fen;
					}else if(fen>=10){
						totalFeeStr1 = money/100 + "." + fen;
					}else{
						totalFeeStr1 = money/100 + ".00";
					}
				}else if(money1.length==1){
					totalFeeStr1 = "0.0"+money;
				}else{
					totalFeeStr1 = "0."+money;
				}
			}else{
				totalFeeStr1 = "0.00";
			}
	    	return totalFeeStr1;	
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
