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
															<label class="control-label" for="id">id：</label>
															<input type="text" class="form-control" id="id" name="id">
														</div>
														<div class="form-group">
															<label class="control-label" for="billType">billType：</label>
															<input type="text" class="form-control" id="billType" name="billType">
														</div>
														<div class="form-group">
															<label class="control-label" for="feeTypeDesc">feeTypeDesc：</label>
															<input type="text" class="form-control" id="feeTypeDesc" name="feeTypeDesc">
														</div>
														<div class="form-group">
															<label class="control-label" for="billArea">billArea：</label>
															<input type="text" class="form-control" id="billArea" name="billArea">
														</div>
														<div class="form-group">
															<label class="control-label" for="chargeUnit">chargeUnit：</label>
															<input type="text" class="form-control" id="chargeUnit" name="chargeUnit">
														</div>
														<div class="form-group">
															<label class="control-label" for="isNeedDate">isNeedDate：</label>
															<input type="text" class="form-control" id="isNeedDate" name="isNeedDate">
														</div>
														<div class="form-group">
															<label class="control-label" for="cityCode">cityCode：</label>
															<input type="text" class="form-control" id="cityCode" name="cityCode">
														</div>
														<div class="form-group">
															<label class="control-label" for="serviceType">serviceType：</label>
															<input type="text" class="form-control" id="serviceType" name="serviceType">
														</div>
														<div class="form-group">
															<label class="control-label" for="addInfo">addInfo：</label>
															<input type="text" class="form-control" id="addInfo" name="addInfo">
														</div>
														<div class="form-group">
															<label class="control-label" for="bigLetters">bigLetters：</label>
															<input type="text" class="form-control" id="bigLetters" name="bigLetters">
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
															<label class="control-label" for="remark1">remark1：</label>
															<input type="text" class="form-control" id="remark1" name="remark1">
														</div>
														<div class="form-group">
															<label class="control-label" for="remark2">remark2：</label>
															<input type="text" class="form-control" id="remark2" name="remark2">
														</div>
														<div class="form-group">
															<label class="control-label" for="remark3">remark3：</label>
															<input type="text" class="form-control" id="remark3" name="remark3">
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
														    	<perm:tag permPath="/txBusinessType/addTxBusinessType" >
														    	<button type="button" onclick="to_add()" class="btn btn-primary">
															        <i class="glyphicon glyphicon-plus"></i>添加
															    </button>
																</perm:tag>
																<perm:tag permPath="/txBusinessType/removeAllTxBusinessType" >
															    <button type="button" onclick="del_all()" class="btn btn-warning">
															        <i class="glyphicon glyphicon-trash"></i>删除
															    </button>
																</perm:tag>
															</div>
															<table id="table" data-toggle="table"
																data-toolbar="#toolbar"
														        data-url="${ctx}/txBusinessType/getTxBusinessTypeList"
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
																        <th data-field="id" data-align="center">id</th>
																        <th data-field="billType" data-align="center">billType</th>
																        <th data-field="feeTypeDesc" data-align="center">feeTypeDesc</th>
																        <th data-field="billArea" data-align="center">billArea</th>
																        <th data-field="chargeUnit" data-align="center">chargeUnit</th>
																        <th data-field="isNeedDate" data-align="center">isNeedDate</th>
																        <th data-field="cityCode" data-align="center">cityCode</th>
																        <th data-field="serviceType" data-align="center">serviceType</th>
																        <th data-field="addInfo" data-align="center">addInfo</th>
																        <th data-field="bigLetters" data-align="center">bigLetters</th>
																        <th data-field="state" data-sort-name="state" data-align="center" data-formatter="formatterState" data-sortable="true">state</th>
																        <th data-field="remark1" data-align="center">remark1</th>
																        <th data-field="remark2" data-align="center">remark2</th>
																        <th data-field="remark3" data-align="center">remark3</th>
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
	    });
    	//检索入口
    	function search(){
    		$table.bootstrapTable('refresh');
    	}
    	function queryParams(params){
    		var queryParam = {
				id:$("#id").val(),
				billType:$("#billType").val(),
				feeTypeDesc:$("#feeTypeDesc").val(),
				billArea:$("#billArea").val(),
				chargeUnit:$("#chargeUnit").val(),
				isNeedDate:$("#isNeedDate").val(),
				cityCode:$("#cityCode").val(),
				serviceType:$("#serviceType").val(),
				addInfo:$("#addInfo").val(),
				bigLetters:$("#bigLetters").val(),
				state:$("#state").val(),
				remark1:$("#remark1").val(),
				remark2:$("#remark2").val(),
				remark3:$("#remark3").val(),
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
	            '<perm:tag permPath="/txBusinessType/updateTxBusinessType" >',
	            '<a class="edit" href="javascript:void(0)" title="编辑">',
	            '<i class="glyphicon glyphicon-edit"></i>',
	            '</a> | ',
	            '</perm:tag>',
	            '<perm:tag permPath="/txBusinessType/removeTxBusinessType" >',
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
			  	content: '${ctx}/txBusinessType/toAdd'
			 });
    	}
    	function toUpdate(id){
    		layer.open({
				title : "编辑",
			  	type: 2,
			  	zIndex:900,
			  	area: ['60%', '80%'],
			  	shade: false,
			  	content: '${ctx}/txBusinessType/toUpdate/'+id 
			 });
    	}
    	function del(id){
   		   if (id != ""){ 
   				$.post("<c:url value='/txBusinessType/removeTxBusinessType'/>",
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
	    	    	$.post("<c:url value='/txBusinessType/removeAllTxBusinessType'/>",
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
	    			s = '正常';
	    		}
	    		if(state == '0'){
	    			s = '禁用';
	    		}
	    	}
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
    </script>
</body>
</html>
