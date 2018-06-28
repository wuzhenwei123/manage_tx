<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${ctx}/css/index/myindex.css">
    <script src="${ctx}/js/jquery.1.9.0.min.js"></script>
    <script src="${ctx}/js/lib/layer/layer.js"></script>
</head>
<body>

<div class="wrap">
    <div class="bus_en">
        <ul>
            <li class="clear">
                <p>预批电费金额：</p>
                <input type="text"  placeholder="5千至2万之间，1000整倍数" id="money"/>
                <i>元</i>
            </li>
            <li class="clear">
                <p>委托完成时间：</p>
                <input type="text" readonly class="sel_time"  placeholder="请选择委托售完时间"/>
                <i>个月</i>
                <div class="sel_list">
                    <ul>
                        <li>1</li>
                        <li>3</li>
                        <li>6</li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
    <div class="bus_con">
        <div class="msg_box">
            <p>1.如果您是智能表客户，请在电力公司发给您的 《居民客户用电登记表》右上角找到您的客户编号。</p>
            <p>2.如果您是抄表电客户，请在您以往的缴费通知 单或电费发票上找到您的电费缴费号。您也可以 拨打电力公司客服电话95598提供电地址查询。</p>
        </div>
    </div>

    <div class="btn_boxs">
        <a href="javascript:;">确认</a>
    </div>
</div>
</body>
<script>
    $(function () {
        $(".sel_box input").on("click",function () {
            $(this).toggleClass("active");
            $(".sel_list").toggle();
        })
        $(".sel_list li").on("click",function () {
            $(".sel_box input").val($(this).text()).removeClass("active")
            $(".sel_list").hide()
        })

        $(".sel_time").on("click",function () {
            $(this).toggleClass("active");
            $(".sel_list").toggle();
        })
        $(".sel_list li").on("click",function () {
            $(".sel_time").val($(this).text()).removeClass("active")
            $(".sel_list").hide()
        })
        
        $(".btn_boxs").on("click",function () {
            var money = $("#money").val();
            if(money==""){
            	layer.msg("请输入预批电费金额");
            	return false;
            }else if(money%100 > 0){
            	layer.msg("请输入1000的整数倍");
   				return false;
            }else if(parseFloat(money) > 20000){
            	layer.msg("最大充值金额不能超过20000");
   				return false;
            }else if(parseFloat(money) < 5000){
            	layer.msg("最大充值金额不能低于5000");
   				return false;
            }
            var sel_time = $(".sel_time").val();
            if(sel_time==""){
            	layer.msg("请选择委托完成时间");
            	return false;
            }
            window.location.href = "${ctx}/unionpay/toSellOrder?sel_time="+sel_time+"&money="+money;
        })
    })
</script>
</html>