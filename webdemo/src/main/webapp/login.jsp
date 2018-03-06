<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <script type="text/javascript" src="/js/jquery-1.9.1.js"></script>
    <script type="text/javascript">
        function subForm() {
            var id = $("#id").val();
            var pwd = $("#pwd").val();
            alert(id + "---" + pwd);
            $.post("/security_login", {
                id: id,
                pwd: pwd
            }, function (result) {
                // alert(result.code + "---" + result.msg);
                if (result.code == 200) {
                    window.location.href = "welcome.jsp";
                }
            });
        }
    </script>
</head>

<body>

<div>
    <form action="/security_login" method="post">
        <fieldset>
            <div>
                <input type="text" name="id" id="id"
                       placeholder="输入用户编号 (只能为纯数字)">
            </div>
            <div>
                <input type="password" name="pwd" id="pwd"
                       placeholder="请输入密码">
            </div>
            <p>
                <button type="submit">登录</button>&emsp;&emsp;
                <a href="register.jsp">注册</a>
            </p>
        </fieldset>
    </form>
</div>

</body>

</html>