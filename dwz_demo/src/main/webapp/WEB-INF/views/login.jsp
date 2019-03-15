<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="include.inc.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${contextMap.projectName}</title>
    <link href="${contextPath}/static/css/dwz/css/login.css"
          rel="stylesheet" type="text/css"/>
    <script src="${contextPath}/static/js/jquery/jquery-1.11.3.js" type="text/javascript"></script>
    <script src="${contextPath}/static/js/jquery/jquery.validate.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            $("#formId").validate({
                messages:{
                    username:"用户名必填",
                    password:"密码必填",
                    captcha_key:"验证码必填",
                },
                errorElement:"div",
            });
            $("#captchaImg").on("click",function(){
                $(this).attr("src","${contextPath}/captcha.jpg?time"+new Date());
            });
        });
    </script>
    <style>
        div.error{
            color: red;
            font-style: italic;
            margin-left: 80px;
            font-size: 12px;
        }
    </style>
</head>
<body>
<div id="login">
    <div id="login_header">
        <h1 class="login_logo">
            <a href="http://www.loujie.com"><img src="${contextPath}/static/css/dwz/default/images/login_logo.gif"/></a>
        </h1>
        <div class="login_headerContent">
            <div class="navList">
                <ul>
                    <%--<li><a href="#">设为首页</a></li>
                    <li><a href="http://www.loujie.com">反馈</a></li>--%>
                </ul>
            </div>
            <h2 class="login_title"><img src="${contextPath}/static/css/dwz/default/images/login_title.png"/></h2>
        </div>
    </div>
    <div id="login_content">
        <div class="loginForm">
            <form action="index.html" method="post" id="formId">
                <p>
                    <label>用户名：</label>
                    <input type="text" style="width: 150px;height: 30px;" class="login_input" name="username" id="username" required/>
                </p>
                <p>
                    <label>密码：</label>
                    <input type="password" style="width: 150px;height: 30px;" class="login_input" name="password" id="password" required />
                </p>
                <p>
                    <label>验证码：</label>
                    <span><img src="${contextPath}/captcha.jpg" alt="刷新更新验证码" style="width: 75px;height: 30px;" id="captchaImg"/></span>
                    <input class="code" type="text" style="width: 70px;height: 30px;" name="captcha_key" id="captcha_key" required/>
                </p>
                <div class="login_bar" style="disply: block; float: left;">
                    <input class="sub" type="submit" value=" "/>
                </div>
            </form>
        </div>
        <div class="login_banner"><img src="${contextPath}/static/css/dwz/default/images/login_banner.jpg"/></div>
        <div class="login_main">
            <ul class="helpList">
                <%--<li><a href="#">忘记密码怎么办？</a></li>
                <li><a href="#">为什么登录失败？</a></li>--%>
            </ul>
            <div class="login_inner">
                <p>推荐用Google Chrome浏览器。</p>
            </div>
        </div>
    </div>
    <div id="login_footer">
        Copyright &copy; 2019 loujie.com Inc. All Rights Reserved.
    </div>
</div>
</body>
</html>
