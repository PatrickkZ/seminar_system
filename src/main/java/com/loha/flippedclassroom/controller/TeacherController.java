package com.loha.flippedclassroom.controller;

import com.loha.flippedclassroom.entity.*;
import com.loha.flippedclassroom.entity.strategy.ConflictCourseAbstractStrategy;
import com.loha.flippedclassroom.entity.strategy.CourseMemberLimitAbstractStrategy;
import com.loha.flippedclassroom.entity.strategy.MemberLimitAbstractStrategy;
import com.loha.flippedclassroom.pojo.dto.CourseDTO;
import com.loha.flippedclassroom.pojo.dto.RoundSettingDTO;
import com.loha.flippedclassroom.pojo.dto.SeminarDTO;
import com.loha.flippedclassroom.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 教师移动端controller
 *
 * @author zhoujian
 * @date 2018/12/15
 */
@Controller
@RequestMapping(value = "/teacher")
@SessionAttributes("curTeacherId")
@Slf4j
public class TeacherController {

    private final TeacherService teacherService;
    private final FileService fileService;
    private final TeamService teamService;
    private final GeneralService generalService;

    @Autowired
    TeacherController(TeacherService teacherService,FileService fileService,TeamService teamService,GeneralService generalService){
        this.teacherService=teacherService;
        this.fileService=fileService;
        this.teamService=teamService;
        this.generalService=generalService;
    }

    @GetMapping(value = "/activation")
    public String activation(){
        return "teacher/activation";
    }

    @PostMapping(value = "/activation")
    @ResponseBody
    public ResponseEntity activateTeacher(@ModelAttribute("curTeacherId") Long teacherId, String password) throws Exception{
        teacherService.activateTeacher(password,teacherId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/index")
    public String teacherIndex(Model model){
        Teacher teacher=teacherService.getCurTeacher();
        model.addAttribute("curTeacherId",teacher.getId());
        if(!teacher.isActivated()){
            return "redirect:/teacher/activation";
        }
        model.addAttribute("teacher",teacher);
        return "teacher/index";
    }

    @GetMapping(value = "/setting")
    public String getSetting(@ModelAttribute("curTeacherId") Long teacherId,Model model)throws Exception{
        model.addAttribute("teacher",teacherService.getTeacherById(teacherId));
        return "teacher/settings";
    }

    @GetMapping(value = "/setting/modifyEmail")
    public String modifyEmailPage(){
        return "teacher/modifyEmailPage";
    }

    @PostMapping(value = "/setting/modifyEmail")
    @ResponseBody
    public ResponseEntity modifyEmail(@ModelAttribute("curTeacherId") Long teacherId,String email) throws Exception{
        teacherService.modifyTeacherEmail(teacherId,email);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/setting/modifyPwd")
    public String modifyPwdPage(){
        return "teacher/modifyPwdPage";
    }

    @PostMapping(value = "/setting/modifyPwd")
    @ResponseBody
    public ResponseEntity modifyPwd(@ModelAttribute("curTeacherId") Long teacherId,String password) throws Exception{
        teacherService.modifyTeacherPwdById(teacherId,password);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/course")
    public String teacherCourseList(@ModelAttribute("curTeacherId")Long teacherId, Model model) throws Exception
    {
        model.addAttribute("courseList",teacherService.getTeacherCourses(teacherId));
        return "teacher/course";
    }

    @PostMapping("/course/info")
    public String courseInfo(Long courseId,Model model) throws Exception
    {
        model.addAttribute("course", generalService.getCourseById(courseId));
        return "teacher/courseInfo";
    }

    @GetMapping("/course/klass")
    public String getCourseKlassList(Long courseId,Model model) throws Exception{
        model.addAttribute("klassList", generalService.getKlassByCourseId(courseId));
        model.addAttribute("courseId", courseId);
        return "teacher/klassInfo";
    }

    @PostMapping(value = "/course/team")
    public String getTeamList(Long courseId,Model model) throws Exception{
        model.addAttribute("teamList",teamService.getTeamsByCourseId(courseId));
        return "teacher/teamListPage";
    }

    @PostMapping("/course/klass/delete")
    @ResponseBody
    public ResponseEntity deleteKlass(Long klassId)throws Exception
    {
        teacherService.deleteKlassByKlassId(klassId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    @PostMapping("/course/klass/create")
    public String createClass(Long courseId,Model model)
    {
        model.addAttribute("courseId",courseId);
        return "teacher/klassCreate";
    }

    @PostMapping(value = "/course/klassList/save")
    @ResponseBody
    public ResponseEntity submitXsl(@RequestParam("file") MultipartFile file, Long klassId) throws Exception{
        fileService.uploadStudentList(file,klassId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/course/klass/create/save1")
    @ResponseBody
    public Integer saveKlass1(@RequestParam("file") MultipartFile file,Long courseId, Integer grade,Integer klassSerial,String klassLocation,String klassTime) throws Exception{

        Klass klass=new Klass();
        klass.setGrade(grade);
        klass.setKlassSerial(klassSerial);
        klass.setLocation(klassLocation);
        klass.setTime(klassTime);
        klass.setCourseId(courseId);


        if(teacherService.selectKlassId(courseId,grade,klassSerial)==null)
        {
            teacherService.createKlass(klass);
            Long klassId=teacherService.selectKlassId(courseId,grade,klassSerial);
            fileService.uploadStudentList(file,klassId);

        }
        return 200;

    }

    @PostMapping(value = "/course/klass/create/save2")
    @ResponseBody
    public Integer saveKlass2(Long courseId, Integer grade,Integer klassSerial,String klassLocation,String klassTime) throws Exception{

        Klass klass=new Klass();
        klass.setGrade(grade);
        klass.setKlassSerial(klassSerial);
        klass.setLocation(klassLocation);
        klass.setTime(klassTime);
        klass.setCourseId(courseId);


        if(teacherService.selectKlassId(courseId,grade,klassSerial)==null)
        {
            teacherService.createKlass(klass);
        }
        return 200;

    }

    @PostMapping(value = "/course/delete")
    @ResponseBody
    public ResponseEntity deleteCourse(Long courseId) throws Exception{
        teacherService.deleteCourseByCourseId(courseId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/course/create")
    public String createCourse(Model model) throws Exception{
        model.addAttribute("allCourseList",teacherService.getAllCourse());
        return "teacher/courseCreate";
    }

    @PostMapping("/course/create/save")
    @ResponseBody
    public ResponseEntity createCourse(@ModelAttribute("curTeacherId")Long teacherId, @RequestBody CourseDTO courseDTO) throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Course course=new Course();
        course.setTeacherId(teacherId);
        course.setCourseName(courseDTO.getCourseName());
        course.setIntroduction(courseDTO.getIntroduction());
        course.setPrePercentage(courseDTO.getPrePercentage());
        course.setQuestionPercentage(courseDTO.getQuestionPercentage());
        course.setReportPercentage(courseDTO.getReportPercentage());
        course.setTeamStartTime(sdf.parse(courseDTO.getStartDate()));
        course.setTeamEndTime(sdf.parse(courseDTO.getEndDate()));
        Long courseId=teacherService.createCourse(course);

        MemberLimitAbstractStrategy memberLimitStrategy=new MemberLimitAbstractStrategy();
        memberLimitStrategy.setMaxMember(courseDTO.getMaxNum());
        memberLimitStrategy.setMinMember(courseDTO.getMinNum());

        List<CourseMemberLimitAbstractStrategy> courseMemberLimitStrategies=new LinkedList<>();
        for(List<Long> optionCourseList:courseDTO.getOptionCourseList()){
            CourseMemberLimitAbstractStrategy courseMemberLimitStrategy=new CourseMemberLimitAbstractStrategy();
            courseMemberLimitStrategy.setCourseId(optionCourseList.get(0));
            courseMemberLimitStrategy.setMaxMember(Integer.parseInt(optionCourseList.get(2).toString()));
            courseMemberLimitStrategy.setMinMember(Integer.parseInt(optionCourseList.get(1).toString()));
            courseMemberLimitStrategies.add(courseMemberLimitStrategy);
        }

        ConflictCourseAbstractStrategy conflictCourseStrategy;
        List<ConflictCourseAbstractStrategy> conflictCourseStrategyList;
        List<List<ConflictCourseAbstractStrategy>> conflictCourseStrategies=new LinkedList<>();
        if(courseDTO.getOptionCourseList().size()!=0){
            for(List<Long> longList:courseDTO.getAndOrItemList()){
                conflictCourseStrategyList=new LinkedList<>();
                for(Long temp:longList){
                    conflictCourseStrategy=new ConflictCourseAbstractStrategy();
                    conflictCourseStrategy.setCourseId(temp);
                    conflictCourseStrategyList.add(conflictCourseStrategy);
                }
                conflictCourseStrategies.add(conflictCourseStrategyList);
            }
        }


        teamService.createTeamRules(courseId,memberLimitStrategy,courseMemberLimitStrategies,conflictCourseStrategies);


        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/course/grade")
    public String getAllTeamsScore(Long courseId,Model model) throws Exception{
        model.addAttribute("roundTeamSeminarList",teacherService.getAllTeamsScore(courseId));
        return "teacher/studentScore";
    }

    @GetMapping(value = "/seminar")
    public String chooseCourse(@ModelAttribute("curTeacherId")Long teacherId,Model model) throws Exception{
        model.addAttribute("courseList",teacherService.getTeacherCourses(teacherId));
        return "teacher/seminar/chooseCourse";
    }

    @PostMapping(value = "/seminar/courseSeminar")
    public String getCourseSeminar(Long courseId,Model model) throws Exception{
        model.addAttribute("roundAndSeminarList",generalService.getRoundAndSeminars(courseId));
        model.addAttribute("klassList",generalService.getKlassByCourseId(courseId));
        model.addAttribute("courseId",courseId);
        return "teacher/seminar/courseSeminarPage";
    }

    @PostMapping(value = "/seminar/info")
    public String getSeminarInfoPage(Long seminarId,Long klassId,Model model) throws Exception{
        Integer status=teacherService.getSeminarStatus(klassId,seminarId);
        Seminar seminar=teacherService.getSeminarById(seminarId);
        model.addAttribute("klassId",klassId);
        model.addAttribute("status",status);
        model.addAttribute("seminar",seminar);
        model.addAttribute("round",teacherService.getRoundById(seminar.getRoundId()));
        return "teacher/seminar/seminarStatus";
    }

    @GetMapping(value = "/seminar/info/progressing")
    public String enterSeminar(Long klassId,Long seminarId,Model model) throws Exception{
        model.addAttribute("klassId",klassId);
        model.addAttribute("seminarId",seminarId);
        model.addAttribute("course",generalService.getKlassById(klassId).getCourse());
        model.addAttribute("klassSeminarId",generalService.getKlassSeminar(klassId,seminarId).getId());
        model.addAttribute("enrollList",teamService.getEnrollList(klassId, seminarId));
        return "teacher/seminar/underwaySeminar";
    }

    @PostMapping(value = "/seminar/enterSeminar")
    @ResponseBody
    public SeminarDTO getSeminarUnderway(Long courseId) throws Exception{
        return teacherService.getUnderwaySeminar(courseId);
    }

    @PostMapping(value = "/seminar/info/progressing/submitPrescore")
    @ResponseBody
    public ResponseEntity submitPrescore(@RequestBody SeminarScore seminarScore) throws Exception{
        log.info(seminarScore.getPresentationScore().toString());
        teacherService.modifyTeamPreScore(seminarScore);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/seminar/info/progressing/submitQuestionscore")
    @ResponseBody
    public ResponseEntity submitQuestionScore(@RequestBody Question question) throws Exception{
        log.info(question.getScore().toString());
        log.info(question.getId().toString());
        teacherService.modifyStudentQuestionScore(question);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    @PostMapping(value = "/seminar/info/start")
    @ResponseBody
    public ResponseEntity startSeminar(Long klassId,Long seminarId) throws Exception{
        KlassSeminar klassSeminar=new KlassSeminar();
        klassSeminar.setKlassId(klassId);
        klassSeminar.setSeminarId(seminarId);
        klassSeminar.setStatus(1);
        teacherService.updateKlassSeminarStatus(klassSeminar);

        List<Attendance> attendanceList=teamService.getEnrollList(klassId, seminarId);
        for(Attendance attendance:attendanceList){
            if(attendance.getId()!=null){
                attendance.setIsPresent(1);
                teacherService.updateIsPresentStatus(attendance);
                break;
            }
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/seminar/info/registerInfo")
    public String getRegisterTeamPpt(Long klassId,Long seminarId,Model model) throws Exception{
        model.addAttribute("klass",generalService.getKlassById(klassId));
        model.addAttribute("seminarId",seminarId);
        model.addAttribute("enrollList",teamService.getEnrollList(klassId,seminarId));
        return "teacher/seminar/enrollListPPT";
    }

    @PostMapping(value = "/seminar/info/registerInfo/download")
    @ResponseBody
    public ResponseEntity downloadPowerPoint(Long attendanceId, HttpServletResponse httpServletResponse) throws Exception{
        fileService.teamDownloadPowerPoint(httpServletResponse,attendanceId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/seminar/info/registerInfo/downloadReport")
    @ResponseBody
    public ResponseEntity downloadReport(Long attendanceId, HttpServletResponse httpServletResponse) throws Exception{
        fileService.teacherDownloadReport(httpServletResponse,attendanceId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    //修改报告分数
    @PostMapping(value = "/seminar/info/registerInfo/scoreReport")
    @ResponseBody
    public ResponseEntity gradeReport(Long klassSeminarId,Long teamId,Long roundId,Long klassId,String reportScore,Long courseId) throws Exception{
        SeminarScore seminarScore=new SeminarScore();
        seminarScore.setKlassSeminarId(klassSeminarId);
        seminarScore.setTeamId(teamId);
        seminarScore.setReportScore(new BigDecimal(reportScore));
        teacherService.updateTeamReportScore(seminarScore);
        //计算并更新本次讨论课的总分
        teacherService.calculateSeminarTotalScore(courseId,seminarScore);
        //计算并更新本轮讨论课总分
        teacherService.calculateRoundScore(roundId, klassId, teamId, courseId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/seminar/info/score")
    public String seminarScoreInfo(Long klassId,Long seminarId,Model model) throws Exception{
        model.addAttribute("klass",generalService.getKlassById(klassId));
        model.addAttribute("scoreList",teacherService.getAllTeamOneSeminarScore(klassId, seminarId));
        return "teacher/seminar/oneSeminarScore";
    }

    @PostMapping(value = "/seminar/info/score/modify")
    @ResponseBody
    public ResponseEntity modifySeminarScore(@RequestBody SeminarScore seminarScore) throws Exception{
        log.info(seminarScore.getPresentationScore().toString());
        teacherService.modifyTeamSeminarScore(seminarScore);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/seminar/courseSeminar/roundSetting")
    public String roundSettingPage(Long roundId,Long courseId,Model model)throws Exception{
        model.addAttribute("round",generalService.getRoundById(roundId));
        model.addAttribute("klassList",generalService.getKlassByCourseId(courseId));
        return "teacher/roundSetting";
    }

    @PostMapping(value = "/seminar/courseSeminar/roundSetting/modify")
    @ResponseBody
    public ResponseEntity modifyRoundSetting(@RequestBody RoundSettingDTO roundSettingDTO) throws Exception{
        Round round=new Round();
        round.setId(roundSettingDTO.getRoundId());
        round.setPreScoreMethod(roundSettingDTO.getPresentationMethod());
        round.setQuestionScoreMethod(roundSettingDTO.getQuestionMethod());
        round.setReportScoreMethod(roundSettingDTO.getReportMethod());

        teacherService.modifyRoundScoreMethod(round);
        teacherService.modifyKlassStudent(roundSettingDTO.getMap(),roundSettingDTO.getRoundId());

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/seminar/create")
    public String createSeminarPage(Long courseId,Model model) throws Exception{
        model.addAttribute("courseId",courseId);
        model.addAttribute("roundList",teacherService.getRoundAndSeminar(courseId));
        return "teacher/seminar/createSeminar";
    }

    @PostMapping(value = "/seminar/info/modify")
    public String modifySeminarPage(Long courseId,Long seminarId,Model model) throws Exception{
        model.addAttribute("seminar",teacherService.getSeminarById(seminarId));
        model.addAttribute("roundList",teacherService.getRoundAndSeminar(courseId));
        model.addAttribute("klassList",generalService.getKlassByCourseId(courseId));
        return "teacher/seminar/modifySeminar";
    }

    @PutMapping(value = "/seminar/create")
    @ResponseBody
    public ResponseEntity createSeminar(@RequestBody Map<String,String> map) throws Exception{
        String roundStatus="-1";
        String roundVisible="0";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Seminar seminar=new Seminar();
        seminar.setCourseId(Long.parseLong(map.get("courseId")));
        String id=map.get("roundId");
        Long roundId;
        if(roundStatus.equals(id))
        {
            roundId=teacherService.createNewRound(Long.parseLong(map.get("courseId")));
        }
        else {
            roundId=Long.parseLong(id);
        }
        seminar.setRoundId(roundId);
        seminar.setSeminarName(map.get("seminarName"));
        seminar.setIntroduction(map.get("introduction"));
        seminar.setTeamLimit(Integer.parseInt(map.get("teamLimit")));
        String isVisible=map.get("isVisible");

        if(roundVisible.equals(isVisible)){
            seminar.setVisible(false);
        }
        else {
            seminar.setVisible(true);
        }
        seminar.setSeminarSerial(Integer.parseInt(map.get("seminarSerial")));
        seminar.setEnrollStartTime(sdf.parse(map.get("enrollStartTime")));
        seminar.setEnrollEndTime(sdf.parse(map.get("enrollEndTime")));
        teacherService.newSeminar(seminar);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/seminar/info/report")
    public String getTeamReport(Long klassId,Long seminarId,Long roundId,Model model) throws Exception{
        model.addAttribute("enrollList",teamService.getEnrollList(klassId,seminarId));
        model.addAttribute("klass",generalService.getKlassById(klassId));
        model.addAttribute("roundId",roundId);
        return "teacher/seminar/teamReport";
    }

    /**
     * 下面是共享页面
     */
    @PostMapping(value = "/course/sharePage")
    public String getSharePage(Long courseId,Model model) throws Exception{
        ShareTeamApp shareTeamApp=new ShareTeamApp();
        shareTeamApp.setSubCourseId(courseId);
        shareTeamApp.setStatus(1);
        model.addAttribute("mainCourseList",teacherService.getMainCourseList(shareTeamApp));

        shareTeamApp.setMainCourseId(courseId);
        model.addAttribute("subCourseList",teacherService.getSubCourseList(shareTeamApp));

        model.addAttribute("courseId",courseId);
        return "teacher/shareSetting";
    }

    @PostMapping(value = "/course/share/new")
    public String createNewSharePage(Long courseId,Model model) throws Exception{
        model.addAttribute("courseId",courseId);
        model.addAttribute("courseList",teacherService.getAllCourse());
        return "teacher/createShare";
    }

    @PostMapping(value = "/course/share/cancel")
    @ResponseBody
    public ResponseEntity cancelShareTeam(Long courseId,Long shareTeamAppId) throws Exception{
        Map<String,Long> map=new HashMap<>(16);
        map.put("id",courseId);
        map.put("teamMainCourseId",null);
        teacherService.updateTeamMainCourse(map);

        teacherService.deleteTeamsInCourse(courseId);

        ShareTeamApp shareTeamApp=new ShareTeamApp();
        shareTeamApp.setId(shareTeamAppId);
        shareTeamApp.setStatus(0);
        teacherService.updateShareTeamAppStatus(shareTeamApp);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/course/share/create")
    @ResponseBody
    public ResponseEntity createNewShare(Long mainCourseId,Long subCourseId) throws Exception{
        Long subCourseTeacherId=teacherService.getCourseById(subCourseId).getTeacherId();
        ShareTeamApp shareTeamApp=new ShareTeamApp();
        shareTeamApp.setMainCourseId(mainCourseId);
        shareTeamApp.setSubCourseId(subCourseId);
        shareTeamApp.setSubCourseTeacherId(subCourseTeacherId);
        teacherService.createShareTeamApp(shareTeamApp);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/backlog")
    public String backlogPage(@ModelAttribute("curTeacherId")Long teacherId,Model model) throws Exception{
        model.addAttribute("teamValidAppList",teacherService.getTeamValidApp(teacherId));
        model.addAttribute("shareTeamAppList",teacherService.getShareTeamApp(teacherId));
        return "teacher/backlog";
    }

    //接受共享请求之后，先删除该课程下的所有team和所有team_student对应关系
    //然后分配team所在的班级（根据team在某班级下的人数）
    //最后再根据主课程的id去找team下的student，需要删除一些学生，在sql语句中完成

    @PostMapping(value = "/backlog/agreeShare")
    @ResponseBody
    public ResponseEntity acceptShareTeam(@RequestBody ShareTeamApp shareTeamApp) throws Exception{
        teamService.acceptTeamShare(shareTeamApp);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/sharing")
    @ResponseBody
    public CourseDTO getConflictCourse(String keyWord) throws Exception{
        Course course=teacherService.getCourseById(Long.parseLong(keyWord));
        CourseDTO courseDTO=new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setTeacherName(course.getTeacher().getTeacherName());

        return courseDTO;
    }

    @PostMapping(value = "/backlog/reject")
    @ResponseBody
    public ResponseEntity rejectShareTeam(Long shareTeamAppId) throws Exception{
        ShareTeamApp shareTeamApp=new ShareTeamApp();
        shareTeamApp.setId(shareTeamAppId);
        shareTeamApp.setStatus(0);
        teacherService.updateShareTeamAppStatus(shareTeamApp);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/backlog/acceptValid")
    @ResponseBody
    public ResponseEntity acceptTeamValidApp(Long id,Long teamId) throws Exception{
        TeamValidApp teamValidApp=new TeamValidApp();
        teamValidApp.setId(id);
        teamValidApp.setStatus(1);
        teamService.acceptTeamValid(teamValidApp,teamId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/backlog/rejectValid")
    @ResponseBody
    public ResponseEntity rejectTeamValidApp(Long teamApplicationId) throws Exception{
        TeamValidApp teamValidApp=new TeamValidApp();
        teamValidApp.setId(teamApplicationId);
        teamValidApp.setStatus(1);
        teacherService.updateTeamValidAppStatus(teamValidApp);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }




}
