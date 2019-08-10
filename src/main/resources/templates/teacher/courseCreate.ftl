<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta name="description" content="A fully featured admin theme which can be used to build CRM, CMS, etc.">
    <meta name="author" content="Coderthemes">

    <link rel="shortcut icon" href="/img/favicon_1.ico">

    <title>新建课程</title>
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

    <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
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
                    <form action="/teacher/course" method="get">
                        <div class="pull-left">
                            <button class="button-menu-mobile">
                                <div class="glyphicon glyphicon-menu-left">
                                </div>
                            </button>
                        </div>
                    </form>

                    <div class="pull-left">
                        <div class="button-menu-mobile">
                            新建课程
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
                <div class="col-sm-12">
                    <div class="panel panel-default">
                        <div class="panel-body">


                            <label>课程名称</label>
                            <input id="courseName" type="text" class="form-control" name="courseName" value=""/>
                            <label>课程要求</label>
                            <input id="introduction" type="text" class="form-control" name="introduction" value=""/>
                            <hr/>


                            <label>成绩计算规则</label>


                            <div class="row">
                                <div class="col-xs-3 col-sm-3">课堂展示
                                </div>
                                <div class="col-xs-9 col-sm-9">
                                    <input id="presentationPercentage" type="text"
                                           class="form-control"
                                           name="presentationPercentage" value="40"/>
                                </div>
                            </div>

                            <br/>

                            <div class="row">
                                <div class="col-xs-3 col-sm-3">课堂提问
                                </div>
                                <div class="col-xs-9 col-sm-9">
                                    <input id="questionPercentage" type="text" class="form-control"
                                           name="questionPercentage" value="30"/>
                                </div>
                            </div>

                            <br/>

                            <div class="row">
                                <div class="col-xs-3 col-sm-3">书面报告
                                </div>
                                <div class="col-xs-9 col-sm-9">
                                    <input id="reportPercentage" type="text" class="form-control"
                                           name="reportPercentage" value="30"/>
                                </div>
                            </div>


                            <hr/>


                            <label>小组总人数</label>

                            <div class="row">
                                <div class="col-xs-6 col-sm-6">
                                    <input id="minMember" type="text" class="form-control" placeholder="下限"/>
                                </div>
                                <div class="col-xs-6 col-sm-6">
                                    <input id="maxMember" type="text" class="form-control" placeholder="上限"/>
                                </div>

                            </div>
                            <br/>
                            <label>组内选修课程人数</label>


                            <div id="optionCourseList">
                                <div>
                                    <select id="optionOtherCourse0" class="select2 form-control"
                                            onchange="changeOptionCourse(0)">
                                        <option value="0">无</option>
                                    <#list allCourseList as course>
                                    <option value="${course.id}">
                                        ${course.courseName}—${course.teacher.teacherName}
                                    </option>
                                    </#list>
                                    </select>
                                </div>

                                <br/>
                                <div class="row">
                                    <div class="col-xs-6 col-sm-6">
                                        <input id="minOptionNum0" type="text" class="form-control" placeholder="下限"/>
                                    </div>
                                    <div class="col-xs-6 col-sm-6">
                                        <input id="maxOptionNum0" type="text" class="form-control" placeholder="上限"/>
                                    </div>

                                </div>

                                <hr/>
                            </div>


                            <button class="btn  btn-primary  waves-effect waves-light pull-right"
                                    onclick="addOptionCourse()">新增
                            </button>

                            <br/>
                            <br/>
                            <br/>

                            <br/>
                            <label>冲突课程</label>
                            <button class="btn  btn-primary  waves-effect waves-light no-border"
                                    onclick="addAnd()">
                                <span class="glyphicon glyphicon-plus" aria-hidden="true">与</span>
                            </button>
                            <hr/>


                            <div id="conflictCourseList">


                            </div>


                            <br/>
                            <br/>

                            <hr/>
                            <label>组队开始时间</label>
                            <input id="teamStartTime" type='text' class="form-control"/>


                            <label>组队截止时间</label>
                            <input id="teamEndTime" type="text" class="form-control"/>

                            <br/>

                            <button class="btn btn-lg btn-default btn-block waves-effect waves-light "
                                    onclick="publishCourse()">
                                发布
                            </button>

                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- col -->


    </div> <!-- End row -->

</div>

</div>
<!-- END wrapper -->

<!-- jQuery  -->
<script src="/js/modernizr.min.js"></script>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
<script>

    var optionCourseNum = 0;
    var optionCourseList = [[0, 0, 0]];
</script>

<script>


    function addOptionCourse() {
        optionCourseNum++;
        var tmp1 = document.getElementById("optionOtherCourse0").innerHTML;
        var tmp2 = "";
        tmp2 += " <div><select id=\"optionOtherCourse";
        tmp2 += optionCourseNum;
        tmp2+="\" class=\"select2 form-control\" onchange=\"changeOptionCourse( ";
        tmp2+=optionCourseNum;
        tmp2+=")\">";
        tmp2+=tmp1;
        tmp2 += "</select> </div><br/><div class=\"row\"><div class=\"col-xs-6 col-sm-6\"><input id=\"minOptionNum";
        tmp2 += optionCourseNum;
        tmp2 += "\" type=\"text\" class=\"form-control\" placeholder=\"下限\"/></div><div class=\"col-xs-6 col-sm-6\"><input id=\"maxOptionNum";
        tmp2 += optionCourseNum;
        tmp2 += "\" type=\"text\" class=\"form-control\" placeholder=\"上限\"/></div></div><hr/>";
        document.getElementById("optionCourseList").innerHTML += tmp2;
        optionCourseList.push([0,0,0]);
    }


</script>


<script>
    function changeOptionCourse(optionOrder) {
        var courseId = document.getElementById("optionOtherCourse" + optionOrder).value;
        optionCourseList[optionOrder][0] = courseId;
    }
</script>

<script>
    var andOrItemList = [];
    var curAndNum = 0;

    function addAnd() {
        curAndNum++;
        // var tmp="";
        // tmp+="<label class=\"control-label col-md-2\">冲突或课程</label><div class=\"col-md-10\"><select id=\"sharedCourse";
        // tmp+=curAndNum;
        // tmp+="\" class=\"select2 form-control\" data-placeholder=\"Choose a Country...\" ></select></div><br/><div id=\"conflictCourse";
        // tmp+=curAndNum;
        // tmp+="\"> <label class=\"control-label col-xs-6 col-md-6 pull-left\">冲突课程, 课程名或教师名(点击添加) </label><div class=\"col-xs-3 col-md-3 \"><input id=\"searchCourse(";
        // tmp+=curAndNum;
        // tmp+=")\">搜索</button></div> </div> <br/> <br/><div id=\"searchStudentList";
        // tmp+=curAndNum;
        // tmp+="\" class=\"col-md-12\"><button class=\"btn btn-lg btn-default btn-block waves-effect waves-light \">教师列表 </button> </div> <hr/>";

        tmp = " <label class=\"control-label col-md-2\">冲突课程</label><div class=\"col-md-10\"><select id=\"sharedCourse";
        tmp += curAndNum;
        tmp += "\" class=\"select2 form-control\"\n";
        tmp += "data-placeholder=\"Choose a Country...\"></select> </div><br/><br/><label class=\"control-label col-xs-6 col-md-6 pull-left\">\n";
        tmp += "课程名或教师名(点击添加)</label><div class=\"col-xs-3 col-md-3 \"><input id=\"searchCourse";
        tmp += curAndNum;
        tmp += "\" type=\"text\" class=\"form-control\" name=\"courseName\"\n";
        tmp += "value=\"\"/></div><div class=\"col-xs-2 col-md-2 \"><button class=\" btn btn-primary waves-effect waves-light pull-left\" type=\"submit\"\n";
        tmp += "onclick=\"searchCourseButton(";
        tmp += curAndNum;
        tmp += ")\">搜索</button></div><br/><br/><div id=\"searchStudentList";
        tmp += curAndNum;
        tmp += "\" class=\"col-md-12\">\n";
        tmp += "<button id=\"courseList";
        tmp += curAndNum;
        tmp += "\"class=\"btn btn-lg btn-default btn-block waves-effect waves-light ";
        tmp += "\">教师列表</button></div><br/><br/>";
        document.getElementById("conflictCourseList").innerHTML += tmp;
        andOrItemList.push([]);


    }


</script>


<script>
    function searchCourseButton(position) {
        var keyWord = document.getElementById("searchCourse" + position).value;
        console.log(keyWord);
        $.ajax({
            url: "/teacher/sharing",
            method: "post",
            data: {
                "keyWord": keyWord
            },
            success: function (data) {

                var courseList = JSON.stringify(data);
                var course = JSON.parse(courseList);
                var tmp = "";
                tmp += "<button id=\"course";
                tmp += course.id;
                tmp += "\" class=\"btn btn-lg btn-default btn-block waves-effect waves-light\" onclick=\"addCourse(";
                tmp += course.id;
                tmp += ",";
                tmp += position;
                tmp += ")\" value=";
                tmp += course.id;
                tmp += " name=";
                tmp += "\"";
                tmp += course.courseName;
                tmp += "\"";
                tmp += ">";
                tmp += course.courseName;
                tmp += course.teacherName;
                tmp += "</button>";
                document.getElementById("searchStudentList" + position).innerHTML += tmp;
            },
            error: function () {
                alert("搜索失败!");
            }
        });
    }
</script>


<script>
    function addCourse(courseId, position) {
        courseId = document.getElementById("course" + courseId).value;
        courseName = document.getElementById("course" + courseId).name;
        var msg = confirm("是否添加该课程?");
        if (msg == true) {

            document.getElementById("course" + courseId).remove();
            var tmp = "";
            tmp += "<option value=\"";
            tmp += courseId;
            tmp += "\">";
            tmp += courseName;
            tmp += "</option>";
            document.getElementById("sharedCourse" + position).innerHTML += tmp;



            andOrItemList[position-1].push(courseId);



        }
    }
</script>


<script>
    function publishCourse() {
        var startDate = document.getElementById("teamStartTime").value;
        var endDate = document.getElementById("teamEndTime").value;
        var courseName = document.getElementById("courseName").value;
        var introduction = document.getElementById("introduction").value;
        var maxNum = document.getElementById("maxMember").value;
        var minNum = document.getElementById("minMember").value;
        for (var i=0;i<optionCourseNum+1;i++)
        {
            optionCourseList[i][1]=document.getElementById("minOptionNum"+i).value;
            optionCourseList[i][2]=document.getElementById("maxOptionNum"+i).value;
        }
        var presentationPercentage = document.getElementById("presentationPercentage").value;
        var questionPercentage = document.getElementById("questionPercentage").value;
        var reportPercentage = document.getElementById("reportPercentage").value;
        var sum = parseInt(presentationPercentage) + parseInt(questionPercentage) + parseInt(reportPercentage);
        var numReg = /^(0|[1-9]\d*)\b/;

        if (!startDate || !endDate || !courseName || !presentationPercentage ||
                !questionPercentage || !reportPercentage) {
            alert("字段不能为空!");
        } else if (sum != 100) {
            alert("成绩计算和不为100!");
        } else if (!numReg.test(maxNum) || !numReg.test(minNum) || maxNum < minNum) {
            alert("人数限制不正确!");
        } else {

            var obj = {
                "startDate": startDate,
                "endDate": endDate,
                "courseName": courseName,
                "introduction": introduction,
                "prePercentage": presentationPercentage,
                "questionPercentage": questionPercentage,
                "reportPercentage": reportPercentage,
                "andOrItemList":andOrItemList,
                "maxNum": maxNum,
                "minNum": minNum,
                "optionCourseList":optionCourseList
            };
            console.log(JSON.stringify(obj));

            $.ajax({
                url: "/teacher/course/create/save",
                method: "post",
                data: JSON.stringify(obj),
                contentType: "application/json;charset=utf-8",
                success: function () {
                    alert("创建课程成功!");
                    //window.location.href = "/teacher/course";
                },
                error: function () {
                    alert("创建课程失败!");

                }
            });
        }

    }


    $(function () {
        $("#teamStartTime").datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            locale: moment.locale('zh-cn')
        });
    });
    $(function () {
        $("#teamEndTime").datetimepicker({
            format: 'YYYY-MM-DD HH:mm:ss',
            locale: moment.locale('zh-cn')
        });
    });

</script>


</body>
</html>