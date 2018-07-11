<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>首页</title>
    <link rel="stylesheet" href="${ctx}/css/index/swiper.css">
    <link rel="stylesheet" href="${ctx}/css/index/myindex.css">
    <script src="${ctx}/js/jquery.1.9.0.min.js"></script>
    <script src="${ctx}/js/swiper.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
</head>
<body>
<div class="wrap">
    <div class="swipe_box">
        <div class="swiper-container swiper-container-horizontal">
            <div class="swiper-wrapper" style="transition-duration: 0ms; transform: translate3d(-9720px, 0px, 0px);">
            	<c:forEach items="${list}" var="banner">
            		<div class="swiper-slide" <c:if test="${not empty banner.targetUrl}">onclick="javascript:window.location.href='${banner.targetUrl}'"</c:if>><img src="${banner.imgUrl}" alt="" height="220.8px;" width="100%"></div>
            	</c:forEach>
            </div>
            <!-- Add Pagination -->
            <div class="swiper-pagination swiper-pagination-bullets"></div>
            <span class="swiper-notification" aria-live="assertive" aria-atomic="true"></span></div>
    </div>
    <div class="m_top">
        <div class="sel_box">
        	<c:forEach items="${listType}" var="buss" varStatus="status">
        		<c:if test="${status.index==0}">
        			<input type="hidden" value="${buss.cityCode}" id="cityCode">
            		<a href="${ctx}/other/toPlace">${buss.billArea}</a>
        		</c:if>
        	</c:forEach>
        	
        </div>
    </div>
    <div class="m_grid clear">
    	<c:forEach items="${listType}" var="buss">
    		<c:if test="${buss.billType=='002'}"><a href="${ctx}/index/toElectric?billType=002&cityCode=${buss.cityCode}&billArea=${buss.billArea}"><img src="${ctx}/images/index/3.png" alt=""><p>电费</p></a></c:if>
    		<c:if test="${buss.billType=='001'}"><a href="javascript:tishi()"><img src="${ctx}/images/index/2.png" alt=""><p>水费</p></a></c:if>
    		<c:if test="${buss.billType=='003'}"><a href="javascript:tishi()"><img src="${ctx}/images/index/4.png" alt=""><p>燃气费</p></a></c:if>
    		<c:if test="${buss.billType=='004'}"><a href="javascript:tishi()"><img src="${ctx}/images/index/8.png" alt=""><p>通讯费</p></a></c:if>
    		<c:if test="${buss.billType=='006'}"><a href="javascript:tishi()"><img src="${ctx}/images/index/5.png" alt=""><p>手机充值</p></a></c:if>
    		<c:if test="${buss.billType=='007'}"><a href="javascript:tishi()"><img src="${ctx}/images/index/10.png" alt=""><p>物业费</p></a></c:if>
    		<c:if test="${buss.billType=='008'}"><a href="javascript:tishi()"><img src="${ctx}/images/index/9.png" alt=""><p>交通罚款</p></a></c:if>
    		<c:if test="${buss.billType=='009'}"><a href="javascript:tishi()"><img src="${ctx}/images/index/7.png" alt=""><p>加油卡</p></a></c:if>
    		<c:if test="${buss.billType=='010'}"><a href="javascript:tishi()"><img src="${ctx}/images/index/6.png" alt=""><p>供暖费</p></a></c:if>
    	</c:forEach>
    </div>
</div>


</body>
<script>

    $(function () {
        var swiper = new Swiper('.swiper-container', {
            pagination: {
                el: '.swiper-pagination',
            },
            loop:true,
            autoplay:true
        });
        $(".sel_box p").on("click",function () {
            $(".sel_list").toggle()
            $(this).toggleClass("active");
        })
        $(".sel_list li").on("click",function () {
            $(".sel_box p").text($(this).text()).removeClass("active")
            $(".sel_list").hide();
        })
    })
    
    function toElectric(){
    	var cityCode = $("#cityCode").val();
    	if("010"==cityCode){
    		window.location.href = "${ctx}/index/toElectric?billType=002&cityCode=010";
    	}
    }
    
    function tishi(){
    	layer.msg("此功能暂未开通");
    }
</script>
</html>