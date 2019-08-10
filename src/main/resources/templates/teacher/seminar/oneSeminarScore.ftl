<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">

    <title>讨论课</title>

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
                            讨论课
                        </div>
                    </div>
                    <ul class="nav navbar-nav navbar-right pull-right">
                        <li class="dropdown">
                            <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><img
                                    src="/img/avatar-1.jpg" alt="user-img" class="img-circle">
                            </a>
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

            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default ">
                        <div class="panel-heading"><h3 class="panel-title">讨论课</h3></div>
                        <div class="panel-body">




                            <div class="row">
                                <div class="col-xs-2 col-sm-2">
                                    小组
                                </div>
                                <div class="col-xs-3 col-sm-3">
                                    展示
                                </div>
                                <div class="col-xs-3 caol-sm-3">
                                    提问
                                </div>
                                <div class="col-xs-3 col-sm-3">
                                    报告
                                </div>
                                <div class="col-xs-1 col-sm-1">

                                </div>
                            </div>




                        <br/>
                            <#list scoreList as teamScore>
                            <div class="row">
                                <div class="col-xs-2 col-sm-2">
                                    ${teamScore.team.klass.klassSerial}—${teamScore.team.teamSerial}
                                </div>

                                <#--<div id="totalScore${teamScore.team.id}" class="col-xs-6 col-sm-6">-->
                                    <#--总分：${teamScore.totalScore!""}-->
                                <#--</div>-->

                                <div id="presentationScore" class="col-xs-3 col-sm-3">
                                    <input id="presentationScore${teamScore.team.id}" type="text" class="form-control" value="${teamScore.presentationScore!""}"/>
                                </div>
                                <div id="questionScore" class="col-xs-3 caol-sm-3">
                                    <input id="questionScore${teamScore.team.id}" type="text" class="form-control" value="${teamScore.questionScore!""}"/>
                                </div>
                                <div id="reportScore" class="col-xs-3 col-sm-3">
                                    <input id="reportScore${teamScore.team.id}" type="text" class="form-control" value="${teamScore.reportScore!""}"/>
                                </div>

                                <div class="col-xs-1 col-sm-1">
                                   <span class="glyphicon glyphicon-pencil"
                                         aria-hidden="true"
                                         onclick="modifyAllScore(${teamScore.team.id})">
                                        </span>
                                </div>

                            </div>
                            </#list>


                        </div>
                    </div>

                    </div> <!-- panel-body -->
                </div> <!-- panel -->


            </div>

        </div>
    </div>

</div>
<!-- END wrapper -->

<!-- jQuery  -->
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>
<script>
    function modifyAllScore(teamId) {


        var preScore = document.getElementById("presentationScore"+teamId).value;
        var queScore = document.getElementById("questionScore"+teamId).value;
        var reportScore = document.getElementById("reportScore"+teamId).value;

        var obj={
            "klassSeminarId":${scoreList[0].klassSeminarId},
            "teamId":teamId,
            "presentationScore": preScore,
            "questionScore": queScore,
            "reportScore": reportScore
        };

        $.ajax({
            url: "/teacher/seminar/info/score/modify",
            method: "post",
            data: JSON.stringify(obj),
            contentType: "application/json;charset=utf-8",
            success: function (totalScore) {
                alert("修改成功!");
            },
            error:function () {
                alert("修改失败!");
            }

        });

    }




</script>
</body>
</html>