<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">

    <title>共享新增</title>

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
    <script type="text/javascript">
        function sendSharing() {
            confirm("确认发送共享请求？")
        }
    </script>
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
                            新增共享
                        </div>
                    </div>
                    <ul class="nav navbar-nav navbar-right pull-right">
                        <li class="dropdown">
                            <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><img
                                    src="/img/avatar-1.jpg" alt="user-img" class="img-circle"> </a>
                            <ul class="dropdown-menu dropdown-menu-lg">
                                <li><a href="/teacher/backlog"><h4><i class="md md-home"></i>&nbsp;待办</h4></a></li>
                                <li><a href="/teacher/index"><h4><i class="md md-home"></i>&nbsp;个人页</h4></a></li>
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
    <br/>


<div class="content">
<div class="container">
    <!-- col -->

<div class="row">
<div class="col-sm-12">
<div class="panel panel-default">
<div class="panel-body">

    <label class="control-label col-md-2">共享类型</label>
    <div class="col-md-10">


        <select id="shareType" class="select2 form-control"
                data-placeholder="Choose a Country...">
            <option value="1">共享分组</option>
        </select>

    </div>

    <br/>
    <br/>
    <label class="control-label col-md-2">被共享课程</label>
<div class="col-md-10">
<select id="subCourse" class="select2 form-control" data-placeholder="Choose a Country...">
                                    <#list courseList as course>
                                    <option value=${course.id}>${course.teacher.teacherName}——${course.courseName}</option>
                                    </#list>
                                </select>
                            </div>

                            <br/>
                            <br/>



                            <br/>
                            <div class="col-md-12">
                                <button class="btn btn-lg btn-default btn-block waves-effect waves-light "
                                        onclick="publishSharing()">确认共享
                                </button>
                            </div>


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
    function publishSharing()
    {
        var subCourseId=document.getElementById("subCourse").value;
        $.ajax({
            url:"/teacher/course/share/create",
            method: "post",
            data:{
                "mainCourseId":${courseId},
                "subCourseId":subCourseId
            },
            success:function () {
                alert("新建共享成功!");
            },
            error:function () {
                alert("新建共享失败!")
            }
        });
    }
</script>


</body>
</html>