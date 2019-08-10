package com.loha.flippedclassroom.dao;

import com.loha.flippedclassroom.entity.*;
import com.loha.flippedclassroom.mapper.*;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 与课程相关的dao
 *
 * @author zhoujian
 * @date 2018/12/15
 */
@Repository
public class CourseDao {

    private final CourseMapper courseMapper;

    CourseDao(CourseMapper courseMapper){
        this.courseMapper=courseMapper;
    }

    /**
     * 根据课程id获取课程信息
     */
    public Course getCourseById(Long id) throws Exception{
        return courseMapper.selectCourseById(id);
    }

    /**
     * 根据课程id删除课程信息
     */
    public void deleteCourseByCourseId(Long courseId) throws Exception{
        courseMapper.deleteCourseByCourseId(courseId);
    }

    /**
     * 新建课程
     */
    public Long createCourse(Course course) throws Exception{
        courseMapper.insertCourse(course);
        return course.getId();
    }

    public List<Course> getAllCourse() throws Exception{
        return courseMapper.selectAllCourse();
    }

    public void updateTeamMainCourse(Map<String,Long> map) throws Exception{
        courseMapper.updateTeamMainCourse(map);
    }

}
