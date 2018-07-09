<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctx}/css/index/myindex.css">
    <script src="${ctx}/js/jquery.1.9.0.min.js"></script>
</head>
<body>
<div class="wrap">
    <div class="search_box">
        <input type="text" id="name" value="${name}">
        <i>搜索</i>
    </div>

<!--     <div class="search_place"> -->
<!--         <p>当前定位城市：<i>北京</i></p> -->
<!--     </div> -->

    <div class="place_lists">
        <ul>
        	<c:forEach items="${map}" var="place">
        		<li>
	                <div class="zimu">${place.key}</div>
	                <div class="places">
               			<c:forEach items="${place.value}" var="obj" varStatus="status">
               				<c:if test="${status.index%4==0}"><div class="tc"></c:if>
               				<a href="${ctx}/other/toIndex?cityCode=${obj.cityCode}">${obj.billArea}</a>
               				<c:if test="${(status.index+1)%4==0}"></div></c:if>
               			</c:forEach>
	                </div>
	            </li>
        	</c:forEach>
        </ul>
    </div>
</div>


</body>
<script>

    $(function () {

        $(".search_box i").on("click",function () {
            $(this).hide();
            $(".search_box input").focus();
        })
        $(".search_box input").on("focus",function () {
            $(".search_box i").hide();
        }).blur(function () {
            if($.trim($(this).val()) == ""){
                $(".search_box i").show();
            }
        })
        
//         $(".tc a").on("click",function () {
//             $(this).text() ?$(".search_place i").text($(this).text()):''
//         })
    })
    
    $(document).ready(function(e) {
		$(this).keydown(function (e){
			if(e.which == "13"){
				var name = $("#name").val();
				if(name!=""){
					window.location.href = "${ctx}/other/toPlace?name="+name;
				}
		  	}
		})
	});
</script>
</html>