package com.loha.flippedclassroom.service;

import com.loha.flippedclassroom.dao.*;
import com.loha.flippedclassroom.entity.Course;
import com.loha.flippedclassroom.entity.Klass;
import com.loha.flippedclassroom.entity.KlassSeminar;
import com.loha.flippedclassroom.entity.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * file service
 *
 * @author zhoujian
 * @date 2018/12/20
 */
@Service
public class GeneralService {
    private final JavaMailSender mailSender;
    private final StudentDao studentDao;
    private final TeacherDao teacherDao;
    private final RoundDao roundDao;
    private final KlassDao klassDao;
    private final SeminarDao seminarDao;
    private final CourseDao courseDao;

    @Autowired
    GeneralService(JavaMailSender mailSender,StudentDao studentDao,TeacherDao teacherDao,RoundDao roundDao,KlassDao klassDao,SeminarDao seminarDao,CourseDao courseDao){
        this.mailSender=mailSender;
        this.studentDao=studentDao;
        this.teacherDao=teacherDao;
        this.roundDao=roundDao;
        this.klassDao=klassDao;
        this.seminarDao=seminarDao;
        this.courseDao=courseDao;
    }

    public void sendSimpleEmail(String account) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("patrickkz@163.com");
        Map<String,String> map=studentDao.getPwdAndEmail(account);
        if(map.get("password")==null){
            map=teacherDao.getPwdAndEmail(account);
        }
        message.setTo(map.get("email"));

        message.setSubject("找回密码");
        message.setText(map.get("password"));
        mailSender.send(message);
    }

    /**
     * 获取所有轮及讨论课
     */
    public List<Round> getRoundAndSeminars(Long courseId) throws Exception{
        return roundDao.getRoundAndSeminar(courseId);
    }

    /**
     * 获取课程下的所有班级
     */
    public List<Klass> getKlassByCourseId(Long courseId) throws Exception{
        return klassDao.getKlassByCourseId(courseId);
    }

    /**
     * 获取班级
     */
    public Klass getKlassById(Long klassId) throws Exception{
        return klassDao.getKlassById(klassId);
    }

    /**
     * 获取班级讨论课对象
     */
    public KlassSeminar getKlassSeminar(Long klassId, Long seminarId) throws Exception{
        return seminarDao.getKlassSeminar(klassId,seminarId);
    }

    /**
     * 获取当前的轮
     */
    public Round getRoundById(Long roundId) throws Exception{
        return roundDao.getRoundById(roundId);
    }

    /**
     * 获取课程对象
     */
    public Course getCourseById(Long courseId) throws Exception{
        return courseDao.getCourseById(courseId);
    }
}
