package com.loha.flippedclassroom.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 小组合法性请求
 *
 * @author zhoujian
 * @date 2018/12/28
 */
@Getter
@Setter
public class TeamValidApp {
    private Long id;
    private Long teamId;
    private Long teacherId;
    private String reason;
    private Integer status;

    private Team team;
}
