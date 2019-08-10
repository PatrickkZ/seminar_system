package com.loha.flippedclassroom.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 共享分组请求
 *
 * @author zhoujian
 * @date 2018/12/28
 */
@Getter
@Setter
public class ShareTeamApp {
    private Long id;
    private Long mainCourseId;
    private Long subCourseId;
    private Long subCourseTeacherId;
    private Integer status;

    private Course mainCourse;
    private Course subCourse;
}
