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
                        <form action="/../teacher/course/seminar/info" method="post">
                        <#--<input name="seminarid" value=${seminar.id}>-->
                            <button class="button-menu-mobile">
                                <div class="glyphicon glyphicon-menu-left"></div>
                            </button>
                        </form>
                    </div>
                    <div class="pull-left">
                        <div class="button-menu-mobile">
                        ${klass.course.courseName}-书面报告
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

            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default ">
                        <div class="panel-heading"><h3 class="panel-title">讨论课</h3></div>
                        <div class="panel-body">

                            <table class="table">

                                <div class="row">


                                <#list enrollList as attendance>

                                    <#if attendance.id??>
                                    <div class="col-xs-3 col-sm-3"><p>第${attendance.teamOrder}组</p></div>


                                    <div class="col-xs-3 col-sm-3">
                                        <p>
                                        <button disabled="disabled" style="border: transparent;"
                                                class="md-trigger btn btn-default waves-effect waves-light pull-right">

                                                <#if attendance.teamId??>
                                                    <#if attendance.reportName??>
                                                <form id="submitFrom" action="/teacher/seminar/info/registerInfo/downloadReport"
                                                      method="post">
                                                    <input type="hidden" name="attendanceId" value=${attendance.id}>
                                                    <button class="md-trigger btn btn-primary waves-effect waves-light pull-right"
                                                            onclick="downloadReport()">
                                                        ${attendance.reportName}
                                                    </button>
                                                </form>
                                              </button>

                                                    <#else >未提交报告
                                                    </#if>
                                                <#else> —
                                                </#if>

                                            </button>
                                        </p>
                                    </div>


                                        <#if attendance.teamId??>
                                    <div id="" class="col-xs-3 col-sm-3">
                                        <input id="reportScore${attendance.id}" type="text" class="form-control"
                                               value=""/>
                                    </div>
                                        <#else >
                                        <div id="" class="col-xs-3 col-sm-3">
                                            <input id="reportScore" type="text" class="form-control"
                                                   value=""/>
                                        </div>
                                        </#if>

                                        <#if attendance.teamId??>
                                    <div class="col-xs-3 col-sm-3">
                                   <span class="glyphicon glyphicon-pencil"
                                         aria-hidden="true"
                                         onclick="modifyTotalScore(${attendance.id},${attendance.klassSeminarId},${attendance.teamId})">
                                        </span>
                                        <hr/>
                                    </div>
                                        <#else >
                                         <div class="col-xs-3 col-sm-3">
                                              <span class="glyphicon glyphicon-pencil"
                                                    aria-hidden="true">
                                        </span>
                                             <hr/>
                                         </div>
                                        </#if>

                                    </#if>
                                </#list>

                                </div>


                                </tbody>
                            </table>
                        </div>

                    </div> <!-- panel-body -->
                </div> <!-- panel -->
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
    function downloadReport() {
        document.getElementById("submitForm").submit;
    }
</script>

<script>
    function modifyTotalScore(attendanceId,klassSeminarId,teamId) {
        var newGrade=document.getElementById("reportScore"+attendanceId).value;
        $.ajax({
            url:"/teacher/seminar/info/registerInfo/scoreReport",
            method:"post",
            data:{
                "klassSeminarId":klassSeminarId,
                "teamId":teamId,
                "reportScore":newGrade,
                "courseId":${klass.courseId},
                "klassId":${klass.id},
                "roundId":${roundId}
            },
            success:function () {
                alert("提交成功!");
            },
            error:function () {
                alert("修改失败!");
            }
        });
    }
</script>


</body>
</html>