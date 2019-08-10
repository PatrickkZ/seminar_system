package com.loha.flippedclassroom.mapper;

import com.loha.flippedclassroom.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 与学生相关的mapper
 *
 * @author zhoujian
 * @date 2018/12/15
 */
@Repository
public interface StudentMapper {

    /**
     * 忘记密码
     * @param account 学生学号
     * @return Map
     * @throws Exception
     */
    Map<String,String> selectPwdAndEmailByAccount(String account) throws Exception;

    /**
     * fetch student
     * @param studentNum student's number
     * @return a student
     * @throws Exception
     */
    Student selectStudentByNum(String studentNum);

    /**
     * 根据id查询学生信息
     * @param studentId 学生的学号
     * @return Student
     * @throws Exception
     */
    Student selectStudentById(Long studentId) throws Exception;

    /**
     * 根据team id和courseId查询team中的学生
     * @param teamId
     * @return List<Student>
     * @throws Exception
     */
    List<Student> selectStudentByTeamIdAndCourseId(@Param("teamId") Long teamId, @Param("courseId")Long courseId) throws Exception;

    /**
     * 根据课程ID查询该课程下的所有学生
     * @param courseId 课程id
     * @return List<Student>
     * @throws Exception
     */
    List<Student> selectStudentByCourseId(Long courseId) throws Exception;

    /**
     * 激活学生账号并修改密码邮箱
     * @param student Object
     * @throws Exception
     */
    void updatePwdAndEmailById(Student student) throws Exception;


    /**
     * 修改密码
     * @param student Object
     * @throws Exception
     */
    void modifyPwdById(Student student) throws Exception;

    /**
     * 修改邮箱
     * @param student Object
     * @throws Exception
     */
    void modifyEmailById(Student student) throws Exception;

    /**
     * 插入一条学生记录
     * @param student Object
     * @throws Exception
     */
    void insertStudent(Student student) throws Exception;
}
