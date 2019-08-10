<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">

    <title>共享设置</title>

    <link href="/plugins/sweetalert/dist/sweetalert.css" rel="stylesheet" type="text/css">
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
                        <form action="/teacher/course" method="get">
                            <button class="button-menu-mobile">
                                <div class="glyphicon glyphicon-menu-left"></div>
                            </button>
                        </form>

                    </div>
                    <div class="pull-left">
                        <div class="button-menu-mobile">
                            共享设置
                        </div>
                    </div>
                    <ul class="nav navbar-nav navbar-right pull-right">
                        <li class="dropdown">
                            <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><img
                                    src="/img/avatar-1.jpg" alt="user-img" class="img-circle"> </a>
                            <ul class="dropdown-menu dropdown-menu-lg">
                                <li><a href="/teacher/backlog"><h4><i class="md md-home"></i>&nbsp;待办</h4></a></li>
                                <li><a href="/teacher/index"><h4><i class="md md-home"></i>&nbsp;个人页</h4></a></li>
                                <li><a href="/teacher/seminar"><h4><i class="md md-layers"></i>&nbsp;讨论课</h4></a></li>
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
                    <div id="sharingCourseList" class="panel panel-default">


                            <#list subCourseList as shareTeamApp>
                                <div id="sharing${shareTeamApp.mainCourseId}">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion-test" href="#collapse${shareTeamApp.id}"
                                               class="collapsed">
                                                ${shareTeamApp.subCourse.courseName}(${shareTeamApp.subCourse.teacher.teacherName}老师)
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapse${shareTeamApp.id}" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <table class="table">

                                                <tbody>


                                                <tr>
                                                    <td>
                                                        <p>共享类型：</p>
                                                    </td>
                                                    <td>
                                                        <p>共享分组</p>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>
                                                        <p>课程身份：</p>
                                                    </td>
                                                    <td>
                                                        <p>主课程</p>
                                                    </td>
                                                </tr>


                                                </tbody>
                                            </table>
                                        <div class="row">
                                            <div class="col-lg-12 col-md-12 col-xs-12">
                                                <button class="btn btn-lg btn-default btn-block waves-effect waves-light "
                                                        onclick="cancelSharing(${shareTeamApp.subCourseId},${shareTeamApp.id})">
                                                    取消共享
                                                </button>

                                            </div>
                                        </div>

                                        </div>
                                    </div>
                                </div>
                            </#list>

                                                    <#list mainCourseList as shareTeamApp>
                                <div id="sharing${shareTeamApp.mainCourseId}">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion-test" href="#collapseOne"
                                               class="collapsed">
                                                ${shareTeamApp.mainCourse.courseName}(${shareTeamApp.mainCourse.teacher.teacherName}老师)
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <table class="table">

                                                <tbody>


                                                <tr>
                                                    <td>
                                                        <p>共享类型：</p>
                                                    </td>
                                                    <td>
                                                        <p>共享分组</p>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>
                                                        <p>课程身份：</p>
                                                    </td>
                                                    <td>
                                                        <p>从课程</p>
                                                    </td>
                                                </tr>


                                                </tbody>
                                            </table>
                                            <div class="row">
                                                <div class="col-lg-12 col-md-12 col-xs-12">
                                                    <button class="btn btn-lg btn-default btn-block waves-effect waves-light "
                                                            onclick="cancelSharing(${shareTeamApp.subCourseId},${shareTeamApp.id})">
                                                        取消共享
                                                    </button>

                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                          </#list>
                    </div>


                </div>
            </div>
        </div> <!-- end row -->


    <div class="col-lg-12 col-md-12 col-xs-12">
        <form action="/teacher/course/share/new" method="post">
            <input type="hidden" name="courseId" value="${courseId}">
            <button class="btn btn-lg btn-default btn-block waves-effect waves-light">新建共享
            </button>
        </form>
    </div>
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
    function cancelSharing(followCourseId,appId) {
        var msg = confirm("确定与该门老师取消共享?");
        if (msg == true) {
            $.ajax({
                url: "/teacher/course/share/cancel",
                method: "post",
                data: {
                    "courseId": followCourseId,
                    "shareTeamAppId":appId
                },
                success: function () {
                    alert("已取消!");
                    var parent = document.getElementById("sharingCourseList");
                    var child = document.getElementById("sharing" + followCourseId);
                    parent.removeChild(child);

                },
                error: function () {
                    alert("取消共享失败!");
                }
            });
        }
    }
</script>
</body>
</html>