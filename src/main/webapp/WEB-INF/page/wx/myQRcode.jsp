<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/config.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn" data-dpr="1" max-width="540" style="font-size: 25.875px;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="hotcss" content="initial-dpr=1">
    <title>分享</title>
    <link rel="stylesheet" href="${ctx }/css/wx/base.css">
    <link rel="stylesheet" href="${ctx }/css/wx/cart.css?t=<%=Math.random()%>">

</head>

<body class="wxshare">
    <%@ include file="/WEB-INF/page/common/share1.jsp" %>
    
    <div class="wxshare-container">
        
        <div class="main-container">
            <img class="bg" src="${ctx }/images/bg.png" alt="">
            <img class="wx" src="${manageAdminUser.qr_code_url}" alt="">
        </div>
        <div>
<!--             <p>${manageAdminUser.realName}邀您加入吉云生活团购<br>开启财富之旅</p> -->
            <p class="tg">推广号:${manageAdminUser.mobile}</p>
        </div>
        
    </div>
</body>

</html>