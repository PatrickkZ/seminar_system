<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">

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
                        <form action="/../teacher/course" method="get">
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
                        <div class="panel panel-default panel-fill">

                            <#list roundAndSeminarList as list1>
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion-test" href="#round${list1.id}"
                                           class="collapsed">
                                            第${list1.roundSerial}轮
                                        </a>
                                    </h4>
                                </div>

                                <div id="round${list1.id}" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <form action="/teacher/seminar/courseSeminar/roundSetting" method="post">
                                            <input type="hidden" name="courseId" value=${courseId}>
                                            <input type="hidden" name="roundId" value=${list1.id}>
                                            <button class=" btn btn-lg btn-primary btn-block  waves-effect waves-light "
                                                    type="submit">该轮轮次设置
                                            </button>
                                        </form>
                                        <div class="panel-group panel-group-joined" id="accordion-test1">
                                            <div class="panel panel-default">

                                <#list list1.seminars as seminars>
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion-test1"
                                               href="#seminar${seminars.id}" class="collapsed">
                                                ${seminars.seminarName}
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="seminar${seminars.id}" class="panel-collapse collapse">
                                        <div class="panel-body">
                                            <table class="table">
                                                <tbody>

                                    <#list klassList as klass>
                                    <tr>

                                        <td>
                                            <form action="/teacher/seminar/info" method="post">
                                                <input type="hidden" name="seminarId" value=${seminars.id}>
                                                <input type="hidden" name="klassId" value=${klass.id}>
                                                <button style="border: transparent" class="  btn-lg btn-default btn-block  waves-effect waves-light ">
                                                    ${klass.grade}—(${klass.klassSerial})
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                    </#list>

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </#list>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>

                        </div>
                        <br/>
                        <form action="/teacher/seminar/create" method="post">
                            <input  type="hidden" name="courseId" value="${courseId}">
                            <button class="btn btn-lg btn-default btn-block waves-effect waves-light ">
                                新建讨论课
                            </button>
                        </form>


                        <tr>
                            <td>
                                <br/>

                                    <button class="  btn btn-lg btn-primary btn-block waves-effect waves-light pull-right" onclick="enterSeminar(${courseId})">
                                        正在进行的讨论课
                                    </button>
                            </td>


                        </tr>

                    </div>
                </div> <!-- end row -->
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
        function enterSeminar(courseId) {
            $.ajax({
                url:"/teacher/seminar/enterSeminar",
                method:"post",
                data:{
                    "courseId":courseId
                },
                success:function (data) {
                    var str=JSON.stringify(data);
                    var obj=JSON.parse(str);
                    if(obj.klassId==null){
                        alert("当前没有正在进行的讨论课");
                    }
                    else {
                        window.location.href="/teacher/seminar/info/progressing?klassId="+obj.klassId+"&seminarId="+obj.seminarId;
                    }
                },
                error:function () {
                    alert("修改失败!");
                }
            });
        }
    </script>


</body>
</html>