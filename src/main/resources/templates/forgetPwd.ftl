<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/images/favicon_1.ico">

    <title>忘记密码</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/core.css" rel="stylesheet" type="text/css">
    <link href="/css/icons.css" rel="stylesheet" type="text/css">
    <link href="/css/components.css" rel="stylesheet" type="text/css">
    <link href="/css/pages.css" rel="stylesheet" type="text/css">
    <link href="/css/menu.css" rel="stylesheet" type="text/css">
    <link href="/css/responsive.css" rel="stylesheet" type="text/css">

    <script src="/js/modernizr.min.js"></script>


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>


<body class="fixed-left">
<!-- Begin page -->
<div id="wrapper">
    <!-- Top Bar Start -->
    <div class="topbar">
        <!-- LOGO -->
        <!-- Button mobile view to collapse sidebar menu -->
        <div class="navbar navbar-default" role="navigation">
            <div class="container">
                <div class="">
                    <div class="pull-left">
                        <form action="/login" method="get">
                        <button type="submit" class="button-menu-mobile">
                            <div class="glyphicon glyphicon-menu-left"></div>
                        </button>
                        </form>

                    </div>
                    <div class="pull-left">
                        <div class="button-menu-mobile">
                            找回密码
                        </div>
                    </div>

                </div>
                <!--/.nav-collapse -->
            </div>
        </div>
    </div>
    <!-- Top Bar End -->


    <br/>
    <br/>
    <br/>
    <br/>


    <div class="content">
        <div class="container">
            <!-- col -->

            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="form">
                                <div class="form-group">
                                    <label class="control-label col-md-2">输入学号/教工号</label>
                                    <div class="col-md-10">
                                        <input id="account" type="text" class="form-control" placeholder="输入学号/教工号">
                                    </div>
                                        <div class="col-xs-3 pull-right">
                                            <div class="form-group text-center m-t-40">
                                                <button class="md-trigger btn btn-default waves-effect waves-light pull-right"
                                                        onclick="submitAccount()">
                                                    提交
                                                </button>
                                            </div>
                                        </div>
                                </div>
                            </div> <!-- .form -->
                        </div> <!-- panel-body -->
                    </div> <!-- panel -->
                </div> <!-- col -->

            </div> <!-- End row -->


        </div> <!-- End row -->


    </div>


</div>
<!-- END wrapper -->

<!-- jQuery  -->

<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>
<script>
    function submitAccount() {
        alert("提交成功");
        var account=document.getElementById("account").value;
        $.ajax({
            url:"/password",
            data:{
                "account":account
            },
            method:"post",
            success:function () {
            },
            error:function () {
                alert("网络异常");
            }
        });
    }
</script>

</body>
</html>

