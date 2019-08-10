<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">

    <title>Moltran - Responsive Admin Dashboard Template</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <link href="/plugins/sweetalert/dist/sweetalert.css" rel="stylesheet" type="text/css">

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/core.css" rel="stylesheet" type="text/css">
    <link href="/css/icons.css" rel="stylesheet" type="text/css">
    <link href="/css/components.css" rel="stylesheet" type="text/css">
    <link href="/css/pages.css" rel="stylesheet" type="text/css">
    <link href="/css/menu.css" rel="stylesheet" type="text/css">
    <link href="/css/responsive.css" rel="stylesheet" type="text/css">

    <style>
        .loading {
            width: 40%;
            height: 56px;
            position: absolute;
            top: 30%;
            left: 30%;
            right: 30%;
            line-height: 56px;
            color: #fff;
            padding-left: 10%;
            padding-right: 10%;
            font-size: 15px;
            background: #000 url(../loading/image/loading.gif) no-repeat 10px 50%;
            opacity: 0.7;
            z-index: 9999;
            -moz-border-radius: 20px;
            -webkit-border-radius: 20px;
            border-radius: 20px;
            filter: progid:DXImageTransform.Microsoft.Alpha(opacity=70);
        }
    </style>
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
                        <form action="/student/index" method="get">
                            <button class="button-menu-mobile">
                                <div class="glyphicon glyphicon-menu-left"></div>
                            </button>
                        </form>
                    </div>
                    <div class="pull-left">
                        <div class="button-menu-mobile">
                            我的课程
                        </div>
                    </div>
                    <ul class="nav navbar-nav navbar-right pull-right">
                        <li class="dropdown">
                            <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><img
                                    src="/img/avatar-1.jpg" alt="user-img" class="img-circle"> </a>
                            <ul class="dropdown-menu dropdown-menu-lg">
                                <li><a href="/student/index"><h4><i class="md md-home"></i>&nbsp;个人页</h4></a></li>
                                <li><a href="/student/chooseCourse"><h4><i class="md md-layers"></i>&nbsp;讨论课</h4></a></li>
                            </ul>
                        </li>
                    </ul>
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

                <div class="col-lg-12 col-md-12 col-xs-12">
                    <div class="panel-group panel-group-joined" id="accordion-test">


                        <#if courseAndKlassList??>
                        <#list courseAndKlassList as list>

                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion-test" href="#course${list.course.id}"
                                       class="collapsed">
                                        ${list.course.courseName}——${list.grade}(${list.klassSerial})
                                    </a>
                                </h4>
                            </div>
                            <div id="course${list.course.id}" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <table class="table">

                                        <tbody>
                                        <tr>
                                            <td>
                                                <form action="/student/course/info" method="post">
                                                    <input type="hidden" name="courseId" value=${list.courseId}>
                                                    <button style="border: transparent"
                                                            class="btn-lg btn-default btn-block  waves-effect waves-light "
                                                            type="submit">
                                                        课程信息
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <form action="/student/course/score" method="post">
                                                    <input type="hidden" name="courseId" value=${list.courseId}>
                                                    <input type="hidden" name="klassId" value=${list.id}>
                                                    <button style="border: transparent" class="  btn-lg btn-default btn-block  waves-effect waves-light " type="submit"
                                                            onclick="HtmlLoad()">
                                                        我的成绩
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <form action="/student/course/team" method="get">
                                                    <input type="hidden" name="klassId" value=${list.id}>
                                                    <input type="hidden" name="courseId" value=${list.courseId}>
                                                    <button style="border: transparent"
                                                            class="  btn-lg btn-default btn-block  waves-effect waves-light ">
                                                        我的组队
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>


                        </#list>
                        </#if>


                    </div>
                </div>
            </div> <!-- end row -->


        </div> <!-- End row -->

    </div>
    <div id="myModal" class="modal fade" data-keyboard="false"
         data-backdrop="static" data-role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div id="loading" class="loading">加载中  orz</div>
    </div>
</div>
<!-- END wrapper -->

<!-- jQuery  -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>

<script>
    function  HtmlLoad(){
        $('#myModal').modal('show');

        setTimeout(function () {
            $('#myModal').modal('hide');
        }, 3000000);

    }
</script>
</body>
</html>