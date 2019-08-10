<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">
    <title>翻转课堂登录</title>
    <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/core.css" rel="stylesheet" type="text/css">
    <link href="/css/icons.css" rel="stylesheet" type="text/css">
    <link href="/css/components.css" rel="stylesheet" type="text/css">
    <link href="/css/pages.css" rel="stylesheet" type="text/css">
    <link href="/css/menu.css" rel="stylesheet" type="text/css">
    <link href="/css/responsive.css" rel="stylesheet" type="text/css">

</head>
<body>


<div class="wrapper-page">
    <div class="panel panel-color panel-primary panel-pages">
        <div class="panel-heading bg-img">
            <div class="bg-overlay"></div>
            <h3 class="text-center m-t-10 text-white"><strong>翻转课堂移动端登录</strong></h3>
        </div>


        <div class="panel-body">

            <div class="form-group">
                <div class="col-xs-12">
                    <input id="userName" class="form-control input-lg" type="text" required="" placeholder="Username"
                           value="">
                </div>
            </div>

            <div class="form-group">
                <div class="col-xs-12">
                    <input id="userPassword" class="form-control input-lg" type="password" required=""
                           placeholder="Password" value="">
                </div>
            </div>

            <div class="form-group">
                <div class="col-xs-12">
                    <div class="checkbox checkbox-primary">
                        <div id="tipBar"></div>
                    </div>

                </div>
            </div>




            <div class="form-group text-center m-t-40">
                <div class="col-xs-12">
                    <button class="btn btn-primary btn-lg w-lg waves-effect waves-light" onclick="login()">Log In
                    </button>
                </div>
            </div>


            <div class="form-group">
                <div class="col-xs-3 pull-right">
                    <div class="form-group text-center m-t-40">
                        <form action="/forgetPwd" method="get">
                        <button  type="submit" class="md-trigger btn btn-default waves-effect waves-light pull-right">
                            忘记密码
                        </button>
                        </form>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>

<script type="text/javascript">

    function login() {
        var username = document.getElementById("userName").value;
        var password = document.getElementById("userPassword").value;
        if (!username || username == "") {
            return false;
        }
        if (!password || password == "") {
            return false;
        }
        $.ajax({
            url: "/login",
            data: {
                "account": username,
                "password": password
            },
            type: "POST",
            success: function (JSON, status) {
                if (JSON.toString() == "[ROLE_STUDENT]")
                    window.location.href = "/student/index";
                if (JSON.toString() == "[ROLE_TEACHER]")
                    window.location.href = "/teacher/index";
            },
            error: function (data) {
                document.getElementById("tipBar").innerText = "用户名或密码错误";
            }
        });
    }
</script>


</body>
</html>