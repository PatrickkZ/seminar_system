package com.loha.flippedclassroom.entity.strategy;

import com.loha.flippedclassroom.entity.Team;
import lombok.Getter;
import lombok.Setter;

/**
 * 小组中总人数限制
 *
 * @author zhoujian
 * @date 2018/12/22
 */
@Getter
@Setter
public class MemberLimitAbstractStrategy extends AbstractStrategy {
    private Long id;
    private Integer minMember;
    private Integer maxMember;

    @Override
    public boolean checkValid(Team team) {
        int memberSize=team.getMember().size();
        return memberSize<=maxMember&&memberSize>=minMember;
    }
}
