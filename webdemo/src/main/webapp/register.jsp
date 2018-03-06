<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
</head>

<body>

<div>
    <form action="/register" method="post">
        <fieldset>
            <div>
                <input type="text" name="id"
                       placeholder="输入用户编号 (只能为纯数字)">
            </div>
            <div>
                <input type="text" name="name"
                       placeholder="输入用户姓名 ">
            </div>
            <div>
                <input type="text" name="age"
                       placeholder="输入用户年龄 (只能为纯数字)">
            </div>
            <div>
                <input type="password" name="pwd"
                       placeholder="请输入密码">
            </div>
            <p>
                <button type="submit">注册</button>
            </p>
        </fieldset>
    </form>
</div>

</body>

</html>