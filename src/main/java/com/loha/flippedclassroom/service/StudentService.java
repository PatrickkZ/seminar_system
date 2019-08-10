package com.loha.flippedclassroom.service;

import com.loha.flippedclassroom.dao.*;
import com.loha.flippedclassroom.entity.*;
import com.loha.flippedclassroom.pojo.dto.ScoreInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * student service
 *
 * @author zhoujian
 * @date 2018/12/16
 */
@Service
@Slf4j
public class StudentService {
    private final StudentDao studentDao;
    private final ScoreDao scoreDao;
    private final SeminarDao seminarDao;
    private final TeamDao teamDao;
    private final RoundDao roundDao;

    @Autowired
    StudentService(StudentDao studentDao,ScoreDao scoreDao,SeminarDao seminarDao,TeamDao teamDao,RoundDao roundDao){
        this.studentDao=studentDao;
        this.scoreDao=scoreDao;
        this.seminarDao=seminarDao;
        this.teamDao=teamDao;
        this.roundDao=roundDao;
    }

    /**
     * 获取某个学生在某个课程下的队伍信息
     */
    public Team getMyTeamUnderCourse(Long courseId,Long studentId) throws Exception{
        return  teamDao.getTeamByCourseAndStudentId(courseId,studentId);
    }

    /**
     * 第一次登录时获取该学生信息
     */
    public Student getCurStudent(){
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String studentNum=userDetails.getUsername();
        return studentDao.getCurStudent(studentNum);
    }

    /**
     * 根据Id获取学生信息
     */
    public Student getStudentById(Long studentId) throws Exception{
        return studentDao.getStudentById(studentId);
    }

    /**
     * 激活学生账号
     */
    public void activateStudent(String password,String email,Long studentId) throws Exception{
        Student student=studentDao.getStudentById(studentId);
        student.setPassword(password);
        student.setEmail(email);
        studentDao.activateStudent(student);
    }

    /**
     * 获取该学生所有课程及所在班级信息
     */
    public List<Klass> getCourseAndKlass(Long studentId) throws Exception{
        return studentDao.getCourseAndKlass(studentId);
    }

    /**
     * 修改学生密码
     */
    public void modifyStudentPwdById(Long studentId,String password) throws Exception{
        Student student=studentDao.getStudentById(studentId);
        student.setPassword(password);
        studentDao.modifyStudentPwd(student);
    }

    /**
     * 修改学生邮箱
     */
    public void modifyStudentEmail(Long studentId,String email) throws Exception{
        Student student=studentDao.getStudentById(studentId);
        student.setEmail(email);
        studentDao.modifyStudentEmail(student);
    }


    /**
     * 查询所有轮及其所属的讨论课的成绩
     */
    public List<ScoreInfo> getMyScore(Long klassId, Long courseId, Long studentId) throws Exception{
        //获得所有轮(同时也获得了所有的讨论课)，对每一轮遍历
        List<Round> rounds=roundDao.getRoundAndSeminar(courseId);
        //获得当前所在的team
        //Team team = getMyTeamUnderKlass(klassId,studentId);
        Team team=getMyTeamUnderCourse(courseId,studentId);

        List<ScoreInfo> list=new LinkedList<>();

        //修改处
        if(team==null){
            return list;
        }
        //修改处

        for(Round round:rounds){
            list.add(scoreDao.getOneTeamScoreUnderOneRound(klassId,round,team.getId()));
        }
        return list;
    }



    /**
     * 获取当前讨论课
     */
    public Seminar getSeminarById(Long seminarId) throws Exception{
        return seminarDao.getSeminarById(seminarId);
    }

    /**
     * 报名某一次讨论课，或者修改一次讨论课的报名次序
     */
    public void registerSeminar(Long klassId,Long seminarId,Long teamId,Integer teamOrder) throws Exception{
        Long klassSeminarId=seminarDao.getKlassSeminar(klassId,seminarId).getId();
        teamDao.registerSeminar(klassSeminarId,teamId,teamOrder);
    }

    /**
     * 取消报名
     */
    public void cancelRegister(Long klassId,Long seminarId,Long teamId) throws Exception{
        Long klassSeminarId=seminarDao.getKlassSeminar(klassId,seminarId).getId();
        teamDao.cancelRegister(klassSeminarId,teamId);
    }


    /**
     * 获取attendance对象
     */
    public Attendance getAttendanceById(Long attendanceId) throws Exception{
        return teamDao.getAttendanceById(attendanceId);
    }

    /**
     * 获取某一轮的成绩
     */
    public SeminarScore getOneSeminarScore(Long klassId,Long seminarId,Long teamId) throws Exception{
        return scoreDao.getOneSeminarScore(klassId,seminarId,teamId);
    }

}
