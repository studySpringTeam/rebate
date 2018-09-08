<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>请登录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <script src="${c_static}/managesys/js/jquery.min.js" type="text/javascript"></script>
</head>

<body>
<input type="hidden" id="ctxLogin" value="${ctx}" />
<form action="${ctx}/login" method="post">
    用户名：<input type="text" name="username" />
    密码：<input type="text" name="password" />
    <input type="hidden" id="redirectUrl" name="redirectUrl" value="${redirectUrl!}" />
    <input type="submit" value="登录">
</form>
</body>
<@shiro.authenticated>
<script>
    debugger;
    var redirectUrl = $("#redirectUrl").val();
    if(redirectUrl != null && redirectUrl != '') {
        window.location.href = redirectUrl;
    } else {
        var ctx = $("#ctxLogin").val();
        if(typeof(ctx) == 'undefined') {
            ctx = '';
        }
        window.location.href =  ctx + "/test/index";
    }
</script>
</@shiro.authenticated>
</html>