<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">

    <title>Moltran - Responsive Admin Dashboard Template</title>

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
                        <form action="/student/course" method="get">
                            <button class="button-menu-mobile">
                                <div class="glyphicon glyphicon-menu-left"></div>
                            </button>
                        </form>

                    </div>
                    <div class="pull-left">
                        <form action="/student/course" method="get">
                            <div class="button-menu-mobile">
                            <#--${course.courseName}-->
                            </div>
                        </form>
                    </div>
                    <ul class="nav navbar-nav navbar-right pull-right">
                        <li class="dropdown">
                            <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><img
                                    src="/img/avatar-1.jpg" alt="user-img" class="img-circle"> </a>
                            <ul class="dropdown-menu dropdown-menu-lg">
                                <li><a href="/student/index"><h4><i class="md md-home"></i>&nbsp;个人页</h4></a></li>
                                <li><a href="/student/chooseCourse"><h4><i class="md md-layers"></i>&nbsp;讨论课</h4></a>
                                </li>
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






                            <#list scoreList as list1>
                                <#if list1.roundScore??>
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion-test"
                                           href="#round${list1.roundScore.round.roundSerial}"
                                           class="collapsed">
                                            第${list1.roundScore.round.roundSerial}轮——总分:
                                            <#if list1.roundScore.totalScore??>
                                            ${list1.roundScore.totalScore}
                                            </#if>
                                        </a>
                                    </h4>
                                </div>


                                <div id="round${list1.roundScore.round.roundSerial}" class="panel-collapse collapse">
                                    <div class="panel-body">
                                        <div class="panel-group panel-group-joined" id="accordion-test1">
                                            <div class="panel panel-default">



                                            <#list list1.seminarScores as seminarScoreList>

                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion-test1"
                                                           href="#seminar${seminarScoreList.seminar.id}"
                                                           class="collapsed">
                                                            <label>${seminarScoreList.seminar.seminarName}
                                                                <#if seminarScoreList.totalScore??>
                                                                本次总成绩:${seminarScoreList.totalScore}
                                                                </#if>
                                                            </label>
                                                        </a>
                                                    </h4>
                                                </div>
                                                <div id="seminar${seminarScoreList.seminar.id}"
                                                     class="panel-collapse collapse">


                                                    <div class="panel-body">
                                                        <div class="row">
                                                            <#if seminarScoreList.presentationScore??>
                                                            <div class="col-xs-4 col-md-1.3">
                                                                展示:${seminarScoreList.presentationScore}</div>
                                                            </#if>

                                             <#if seminarScoreList.questionScore??>
                                                            <div class="col-xs-4 col-md-1.3">
                                                                提问:${seminarScoreList.questionScore}</div>
                                             </#if>
                                             <#if seminarScoreList.reportScore??>
                                                            <div class="col-xs-4 col-md-1.3">
                                                                报告:${seminarScoreList.reportScore}</div>
                                             </#if>
                                                        </div>

                                                    </div>
                                                </div>
                                            </#list>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                </#if>
                            </#list>

                        </div>
                    </div>
                </div> <!-- end row -->
            </div> <!-- End row -->
        </div>

    </div>
    <!-- END wrapper -->
    <!-- jQuery  -->
</div>
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>

</body>
</html>