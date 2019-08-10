package com.loha.flippedclassroom.service;

import com.loha.flippedclassroom.dao.*;
import com.loha.flippedclassroom.entity.*;
import com.loha.flippedclassroom.pojo.dto.ScoreInfo;
import com.loha.flippedclassroom.pojo.dto.SeminarDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * teacher service
 *
 * @author zhoujian
 * @date 2018/12/16
 */
@Service
@Slf4j
public class TeacherService {

    private final TeacherDao teacherDao;
    private final CourseDao courseDao;
    private final RoundDao roundDao;
    private final KlassDao klassDao;
    private final TeamDao teamDao;
    private final ScoreDao scoreDao;
    private final SeminarDao seminarDao;
    private final QuestionDao questionDao;
    private final ApplicationDao applicationDao;

    @Autowired
    TeacherService(TeacherDao teacherDao,CourseDao courseDao,RoundDao roundDao,KlassDao klassDao,TeamDao teamDao,ScoreDao scoreDao,SeminarDao seminarDao,QuestionDao questionDao,ApplicationDao applicationDao){
        this.teacherDao=teacherDao;
        this.courseDao=courseDao;
        this.roundDao=roundDao;
        this.klassDao=klassDao;
        this.teamDao=teamDao;
        this.scoreDao=scoreDao;
        this.seminarDao=seminarDao;
        this.questionDao=questionDao;
        this.applicationDao=applicationDao;
    }


    public List<Course> getTeacherCourses(Long teacherId) throws Exception{
        return teacherDao.getTeacherCourses(teacherId);
    }

    public Teacher getCurTeacher(){
        UserDetails userDetails=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String teacherNum=userDetails.getUsername();
        return teacherDao.getCurTeacher(teacherNum);
    }


    public Course getCourseById(Long id) throws Exception{
        return courseDao.getCourseById(id);
    }

    public List<Round> getRoundAndSeminar(Long courseId) throws Exception{
        return roundDao.getRoundAndSeminar(courseId);
    }


    public  void activateTeacher(String password, Long teacherId) throws Exception{
        Teacher teacher=teacherDao.getTeacherById(teacherId);
        teacher.setPassword(password);
        teacherDao.activateTeacher(teacher);
    }

    public Teacher getTeacherById(Long teacherId) throws Exception{
        return teacherDao.getTeacherById(teacherId);
    }

    public void modifyTeacherEmail(Long teacherId, String email) throws Exception{
        Teacher teacher=teacherDao.getTeacherById(teacherId);
        teacher.setEmail(email);
        teacherDao.modifyTeacherEmail(teacher);
    }

    public void modifyTeacherPwdById(Long teacherId, String password) throws Exception{
        Teacher teacher=teacherDao.getTeacherById(teacherId);
        teacher.setPassword(password);
        teacherDao.modifyTeacherPsd(teacher);
    }



    /**
     * 通过课程id删除课程
     */
    public void deleteCourseByCourseId(Long courseId) throws Exception{
        courseDao.deleteCourseByCourseId(courseId);
    }

    /**
     * 新建课程
     */
    public Long createCourse(Course course) throws Exception{
        return courseDao.createCourse(course);
    }



    public void createKlass(Klass klass) throws Exception{
        klassDao.createKlass(klass);
    }

    public Long selectKlassId(Long courseId, Integer grade, Integer klassSerial) throws Exception{
        return klassDao.selectKlassId(courseId,grade,klassSerial);
    }

    public void deleteKlassByKlassId(Long klassId) throws Exception{
        klassDao.deleteKlassByKlassId(klassId);
    }



    /**
     *查询所有队伍的每一轮的讨论课成绩
     */
    public List<List<ScoreInfo>> getAllTeamsScore(Long courseId) throws Exception{
        //获取所有轮
        List<Round> rounds=roundDao.getRoundAndSeminar(courseId);

        List<List<ScoreInfo>> allTeamAllRound=new LinkedList<>();
        List<ScoreInfo> allTeamOneRound;
        for (Round round:rounds){
            allTeamOneRound=new LinkedList<>();
            for(Klass klass:klassDao.getKlassByCourseId(courseId))
            {
                for(Team team:teamDao.getAllTeamsByKlassId(klass.getId())){
                    allTeamOneRound.add(scoreDao.getOneTeamScoreUnderOneRound(klass.getId(),round,team.getId()));
                }
            }
            allTeamAllRound.add(allTeamOneRound);
        }

        return allTeamAllRound;
    }

    public Integer getSeminarStatus(Long klassId,Long seminarId) throws Exception{
        return seminarDao.getKlassSeminar(klassId, seminarId).getStatus();
    }

    public Seminar getSeminarById(Long seminarId) throws Exception{
        return seminarDao.getSeminarById(seminarId);
    }

    public Round getRoundById(Long roundId) throws Exception{
        return roundDao.getRoundById(roundId);
    }

    public List<SeminarScore> getAllTeamOneSeminarScore(Long klassId,Long seminarId) throws Exception{
        Long klassSeminarId=seminarDao.getKlassSeminar(klassId,seminarId).getId();
        List<Attendance> attendances=teamDao.getEnrollList(klassSeminarId);

        List<SeminarScore> seminarScores=new LinkedList<>();
        SeminarScore seminarScore;
        for(Attendance attendance:attendances){
            seminarScore=scoreDao.getOneSeminarScore(klassId,seminarId,attendance.getTeamId());
            seminarScores.add(seminarScore);
        }
        return seminarScores;
    }

    /**
     *修改轮的成绩计算方法
     */
    public void modifyRoundScoreMethod(Round round) throws Exception{
        roundDao.modifyRoundScoreMethod(round);
    }

    public void modifyKlassStudent(Map<Long,Object> map,Long roundId) throws Exception{
        Map<String,Long> temp=new HashMap<>(16);
        temp.put("roundId",roundId);

        for(Long klassId:map.keySet()){
            temp.put("klassId",klassId);
            temp.put("enrollNumber",Long.parseLong((String) map.get(klassId)));
            roundDao.modifyKlassRound(temp);
        }
    }

    /**
     *新建讨论课
     */
    public void newSeminar(Seminar seminar) throws Exception{
        Long seminarId=seminarDao.insertSeminar(seminar);
        //获取课程下所有班级
        List<Klass> klassList=klassDao.getKlassByCourseId(seminar.getCourseId());
        KlassSeminar klassSeminar=new KlassSeminar();
        SeminarScore seminarScore=new SeminarScore();
        seminarScore.setQuestionScore(new BigDecimal(0));

        klassSeminar.setSeminarId(seminarId);
        klassSeminar.setStatus(0);
        for (Klass klass:klassList){
            klassSeminar.setKlassId(klass.getId());
            Long klassSeminarId=seminarDao.insertKlassSeminar(klassSeminar);
            seminarScore.setKlassSeminarId(klassSeminarId);
            for(Team team:teamDao.getAllTeamsByKlassId(klass.getId())){
                seminarScore.setTeamId(team.getId());
                scoreDao.insertSeminarScore(seminarScore);
            }
        }
    }

    public void updateShareTeamAppStatus(ShareTeamApp shareTeamApp) throws Exception{
        applicationDao.updateShareTeamAppStatus(shareTeamApp);
    }

    public void updateTeamValidAppStatus(TeamValidApp teamValidApp) throws Exception{
        applicationDao.updateTeamValidAppStatus(teamValidApp);
    }

    /**
     *修改讨论课状态
     */
    public void updateKlassSeminarStatus(KlassSeminar klassSeminar) throws Exception{
        seminarDao.updateKlassSeminarStatus(klassSeminar);
    }

    /**
     *修改正在展示的状态
     */
    public void updateIsPresentStatus(Attendance attendance) throws Exception{
        teamDao.updateIsPresentStatus(attendance);
    }

    /**
     *修改小组展示成绩
     */
    public void modifyTeamPreScore(SeminarScore seminarScore) throws Exception{
        scoreDao.modifyTeamPreScore(seminarScore);
    }

    /**
     *修改某一位同学的提问成绩
     */
    public void modifyStudentQuestionScore(Question question) throws Exception{
        questionDao.updateQuestionScore(question);
    }

    public void createShareTeamApp(ShareTeamApp shareTeamApp) throws Exception{
        applicationDao.insertShareTeamApp(shareTeamApp);
    }

    public List<ShareTeamApp> getMainCourseList(ShareTeamApp shareTeamApp) throws Exception{
        return applicationDao.selectMainCourseList(shareTeamApp);
    }

    public List<ShareTeamApp> getSubCourseList(ShareTeamApp shareTeamApp) throws Exception{
        return applicationDao.selectSubCourseList(shareTeamApp);
    }

    public List<TeamValidApp> getTeamValidApp(Long teacherId) throws Exception{
        return applicationDao.getTeamValidApp(teacherId);
    }

    public List<ShareTeamApp> getShareTeamApp(Long teacherId) throws Exception{
        return applicationDao.getShareTeamApp(teacherId);
    }

    public List<Course> getAllCourse() throws Exception{
        return courseDao.getAllCourse();
    }

    public void modifyTeamSeminarScore(SeminarScore seminarScore) throws Exception{
        scoreDao.updateSeminarScore(seminarScore);
    }

    public Long createNewRound(Long courseId) throws Exception{
        Round round=new Round();
        round.setCourseId(courseId);
        Integer serial=roundDao.getRoundAndSeminar(courseId).size()+1;
        round.setRoundSerial(serial);
        round.setPreScoreMethod(0);
        round.setQuestionScoreMethod(1);
        round.setReportScoreMethod(0);
        Long roundId= roundDao.insertRound(round);

        RoundScore roundScore=new RoundScore();
        roundScore.setRoundId(roundId);
        //新建round_score
        List<Klass> klasses=klassDao.getKlassByCourseId(courseId);
        for(Klass klass:klasses){
            for (Team team:teamDao.getAllTeamsByKlassId(klass.getId())){
                roundScore.setTeamId(team.getId());
                scoreDao.insertRoundScore(roundScore);
            }
        }
        return roundId;
    }


    //计算分数

    public void updateTeamReportScore(SeminarScore seminarScore) throws Exception{
        scoreDao.updateTeamReportScore(seminarScore);
    }

    public void calculateSeminarTotalScore(Long courseId,SeminarScore seminarScore) throws Exception{
        Course course=courseDao.getCourseById(courseId);
        double prePercentage=course.getPrePercentage()/100.0;
        double questionPercentage=course.getQuestionPercentage()/100.0;
        double reportPercentage=course.getReportPercentage()/100.0;

        SeminarScore temp=scoreDao.selectSeminarScoreByKey(seminarScore);
        double preScore=temp.getPresentationScore().doubleValue()*prePercentage;
        double questionScore=temp.getQuestionScore().doubleValue()*questionPercentage;
        double reportScore=temp.getReportScore().doubleValue()*reportPercentage;

        BigDecimal totalScore=new BigDecimal(preScore+questionScore+reportScore);

        seminarScore.setTotalScore(totalScore);
        scoreDao.updateTeamTotalScore(seminarScore);

    }

    //更新一轮的分数
    public void calculateRoundScore(Long roundId,Long klassId,Long teamId,Long courseId) throws Exception{
        //用roundId找到所有的讨论课
        List<Long> seminarIds=seminarDao.selectSeminarIdByRoundId(roundId);

        //用所有的seminarId和klassId找到klassSeminarId
       List<Long> klassSeminarIds = seminarDao.selectOneTeamKlassSeminars(klassId,seminarIds);

       //再去attendance表中筛选出该小组参加的讨论课的klassSeminarId
        List<Long> trueKlassSeminarIds=teamDao.selectKlassSeminarIdAttended(teamId,klassSeminarIds);

        BigDecimal roundPreScore;
        BigDecimal roundReportScore;
        BigDecimal roundQuestionScore;

        //用真正参加的计算展示和提问的分数
        Round round=roundDao.getRoundById(roundId);
        if(round.getPreScoreMethod()==0){
            roundPreScore=scoreDao.selectAvgPreScore(teamId,trueKlassSeminarIds);
        }
        else {
            roundPreScore=scoreDao.selectMaxPreScore(teamId,trueKlassSeminarIds);
        }

        if(round.getReportScoreMethod()==0){
            roundReportScore=scoreDao.selectAvgReportScore(teamId,trueKlassSeminarIds);
        }
        else {
            roundReportScore=scoreDao.selectMaxReportScore(teamId,trueKlassSeminarIds);
        }

        if(round.getQuestionScoreMethod()==0){
            roundQuestionScore=scoreDao.selectAvgQuestionScore(teamId,klassSeminarIds);
        }
        else {
            roundQuestionScore=scoreDao.selectMaxQuestionScore(teamId,klassSeminarIds);
        }

        Course course=courseDao.getCourseById(courseId);
        double prePercentage=course.getPrePercentage()/100.0;
        double questionPercentage=course.getQuestionPercentage()/100.0;
        double reportPercentage=course.getReportPercentage()/100.0;

        RoundScore roundScore=new RoundScore();
        roundScore.setTeamId(teamId);
        roundScore.setRoundId(roundId);
        RoundScore temp=scoreDao.selectRoundScoreByKey(roundScore);

        if(temp!=null){
            temp.setPresentationScore(roundPreScore);
            temp.setQuestionScore(roundQuestionScore);
            temp.setReportScore(roundReportScore);
            double scoreOne=roundPreScore.doubleValue()*prePercentage;
            double scoreTwo=roundQuestionScore.doubleValue()*questionPercentage;
            double scoreThree=roundReportScore.doubleValue()*reportPercentage;

            BigDecimal totalScore=new BigDecimal(scoreOne+scoreTwo+scoreThree);
            temp.setTotalScore(totalScore);
        }
    }

    public SeminarDTO getUnderwaySeminar(Long courseId) throws Exception{
        List<Long> klassIds=klassDao.selectKlassIdByCourseId(courseId);
        List<Long> seminarIds=seminarDao.selectSeminarIdByCourseId(courseId);

        KlassSeminar klassSeminar=seminarDao.getUnderwaySeminar(klassIds,seminarIds);
        SeminarDTO seminarDTO=new SeminarDTO();
        if(klassSeminar!=null){
            seminarDTO.setKlassId(klassSeminar.getKlassId());
            seminarDTO.setSeminarId(klassSeminar.getSeminarId());
        }
        else {
            seminarDTO.setKlassId(null);
            seminarDTO.setSeminarId(null);
        }
        return seminarDTO;

    }

    public void updateTeamMainCourse(Map<String,Long> map) throws Exception{
        courseDao.updateTeamMainCourse(map);
    }

    public void deleteTeamsInCourse(Long courseId) throws Exception{
        List<Klass> klasses=klassDao.getKlassByCourseId(courseId);
        for(Klass klass:klasses){
            teamDao.deleteTeamByKlassId(klass.getId());
        }
    }
}
