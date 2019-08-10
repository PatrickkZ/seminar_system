package com.loha.flippedclassroom.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 正在进行的讨论课
 *
 * @author zhoujian
 * @date 2018/12/18
 */
@Getter
@Setter
@ToString
public class SeminarDTO {
    private Long klassId;
    private Long seminarId;
}
