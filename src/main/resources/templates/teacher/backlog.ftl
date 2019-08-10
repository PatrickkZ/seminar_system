<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">

    <title>代办</title>

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
                        <button class="button-menu-mobile">
                            <div class="glyphicon glyphicon-menu-left"></div>
                        </button>

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
<div class="panel panel-default">

                            <#list shareTeamAppList as teamApplication>
                            <div id="teamApplication${teamApplication.id}">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion-test"
                                           href="#shareTeam${teamApplication.id}" class="collapsed">
                                            ${teamApplication.mainCourse.courseName} ——${teamApplication.mainCourse.teacher.teacherName}的共享分组邀请
                                        </a>
                                    </h4>
                                </div>
                                <div id="shareTeam${teamApplication.id}" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="row">
                                            <button class="pull-right btn btn-default waves-effect waves-light"
                                                    type="button"
                                                    onclick="cancelTeamApplication(${teamApplication.id})">
                                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                            </button>
                                            <button class="pull-right btn btn-primary waves-effect waves-light"
                                                    type="button"
                                                    onclick="okTeamApplication(${teamApplication.id},${teamApplication.mainCourseId},${teamApplication.subCourseId})">
                                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </#list>


                        <#list teamValidAppList as specialTeamApplication>
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion-test"
                                   href="#valid${specialTeamApplication.id}" class="collapsed">
                                    ${specialTeamApplication.team.teamName}小组的特殊组队申请
                                </a>
                            </h4>
                        </div>
                        <div id="valid${specialTeamApplication.id}" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="row">
                                    ${specialTeamApplication.reason}
                                    <button class="pull-right btn btn-default waves-effect waves-light" type="button"
                                            onclick="denyApplication(${specialTeamApplication.id})">
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    </button>
                                    <button class="pull-right btn btn-primary waves-effect waves-light" type="button"
                                            onclick="acceptApplication(${specialTeamApplication.id},${specialTeamApplication.teamId})">
                                        <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </#list>


                </div>
            </div>
        </div> <!-- end row -->


    </div> <!-- End row -->

</div>

</div>
<!-- END wrapper -->
</body>
<!-- jQuery  -->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>



<script>
    function cancelTeamApplication(teamApplicationId) {
        $.ajax({
            url:"/teacher/backlog/reject",
            method:"post",
            data:{
                "shareTeamAppId":teamApplicationId
            },
            success:function () {
                document.getElementById("teamApplication"+teamApplicationId).remove();

            },
            error:function () {
                alert("失败");
            }
        });
    }


</script>


<script>

    function okTeamApplication(teamApplicationId,mainCourseId,subCourseId) {
        var obj={
            "id":teamApplicationId,
            "mainCourseId":mainCourseId,
            "subCourseId":subCourseId
        };
        $.ajax({
            url:"/teacher/backlog/agreeShare",
            method:"post",
            data:JSON.stringify(obj),
            contentType: "application/json;charset=utf-8",
            success:function () {
                alert("共享成功!");
                document.getElementById("teamApplication"+teamApplicationId).remove();
            },
            error:function () {
                alert("共享失败!");
            }
        });

    }
</script>

<script>
    function denyApplication(id) {
        $.ajax({
            url:"/teacher/backlog/rejectValid",
            method:"post",
            data:{
                "teamApplicationId":id
            },
            success:function () {
                document.getElementById("teamApplication"+id).remove();

            },
            error:function () {
                alert("失败!");
            }
        });
    }
</script>



<script>
    function acceptApplication(specialTeamApplicationId,teamId)
    {
        $.ajax({
            url:"/teacher/backlog/acceptValid",
            method:"post",
            data:{
                "id":specialTeamApplicationId,
                "teamId":teamId
            },
            success:function () {
                alert("已同意");
                document.getElementById("specialTeamApplication"+specialTeamApplicationId).remove();
            },
            error:function () {
                alert("提交失败!");
            }
        });
    }
</script>









</html>