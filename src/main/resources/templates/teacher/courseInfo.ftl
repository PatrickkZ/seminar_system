<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/images/favicon_1.ico">

    <title>课程</title>

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
                            ${course.courseName}
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
                        <div class="panel-body">

                            <table class="table">

                                <tbody>
                                <tr>
                                    <td><p>课程简介：</p>
                                        <p>${course.introduction}</p>
                                    </td>

                                </tr>

                                </tbody>
                            </table>

                            <table class="table">

                                <tbody>

                                <tr>
                                    <td rowspan="3"><p>成绩计算规则：</p></td>
                                    <td>

                                        <p class="pull-right">课堂展示：${course.prePercentage}</p>

                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <p class="pull-right">课堂提问：${course.questionPercentage}</p>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <p class="pull-right">报告分数：${course.reportPercentage}</p>
                                    </td>
                                </tr>


                                <tr>
                                    <td><p>小组人数：</p></td>
                                    <td><p class="pull-right">最少人数——最多人数</p></td>
                                </tr>

                                <tr>
                                    <td><p>组队开始时间：</p></td>
                                    <td><p class="pull-right">${course.teamStartTime?datetime}</p></td>
                                </tr>

                                <tr>
                                    <td><p>组队结束时间：</p></td>
                                    <td><p class="pull-right">${course.teamEndTime?datetime}</p></td>
                                </tr>

                                <tr>
                                    <td><p>组员性别要求：</p></td>
                                    <td><p class="pull-right">无</p></td>
                                </tr>

                                <tr>
                                    <td><p>冲突课程：</p></td>
                                    <td><p class="pull-right">.Net(XX老师）</p></td>
                                </tr>


                                </tbody>
                            </table>

                            <button class="btn btn-lg btn-purple btn-block waves-effect waves-light" onclick="deleteCourse()">删除课程
                            </button>
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
    function deleteCourse() {
        var msg=confirm("是否删除该门课程?");
        if(msg==true)
        {
            $.ajax({
                url:"/teacher/course/delete",
                method:"post",
                data:{
                    "courseId": ${course.id}
                },
                success: function () {
                    alert("删除成功！");
                    window.location.href="/teacher/course";

                },
                error:function () {
                    alert("删除失败!")
                }
            })
        }

    }
</script>



</body>
</html>