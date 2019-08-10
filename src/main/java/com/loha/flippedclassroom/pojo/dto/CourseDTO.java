package com.loha.flippedclassroom.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 创建课程时用于接收前端传来的课程信息
 *
 * @author zhoujian
 * @date 2018/12/25
 */
@Getter
@Setter
@ToString
public class CourseDTO {
    private Long id;
    private String teacherName;
    private String courseName;
    private String introduction;
    private Integer prePercentage;
    private Integer questionPercentage;
    private Integer reportPercentage;
    private String startDate;
    private String endDate;
    private List<List<Long>> andOrItemList;
    private List<List<Long>> optionCourseList;
    private Integer maxNum;
    private Integer minNum;
}
