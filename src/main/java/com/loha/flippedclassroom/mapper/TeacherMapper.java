package com.loha.flippedclassroom.mapper;

import com.loha.flippedclassroom.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 与教师相关的mapper
 *
 * @author sulingqi
 * @date 2018/12/19
 */
@Repository
public interface TeacherMapper {

    /**
     * 忘记密码
     * @param account 老师账号
     * @return Map
     * @throws Exception
     */
    Map<String,String> selectPwdAndEmailByAccount(String account) throws Exception;

    /**
     * fetch teacher
     * @param teacherNum teacher's number
     * @return a teacher
     */
    Teacher selectTeacherByNum(String teacherNum);


    /**
     * 根据id查询教师信息
     * @param teacherId 教师的教工号
     * @return teacher
     * @throws Exception
     */
    Teacher selectTeacherById(Long teacherId) throws Exception;


    /**
     * 激活教师账号并修改密码
     * @param  teacher Object
     * @throws Exception
     */
    void updatePwdById(Teacher teacher) throws Exception;

    /**
     * 修改邮箱
     * @param teacher Object
     * @throws Exception
     */
    void modifyEmailById(Teacher teacher) throws Exception;

    /**
     * 修改密码
     * @param teacher Object
     * @throws Exception
     */
    void modifyPsdById(Teacher teacher)throws Exception;
}
