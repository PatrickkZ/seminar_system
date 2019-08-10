package com.loha.flippedclassroom.entity.strategy;

import com.loha.flippedclassroom.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 组队策略
 *
 * @author zhoujian
 * @date 2018/12/22
 */
@Getter
@Setter
public class TeamAbstractStrategy extends AbstractStrategy {
    private List<AbstractStrategy> strategies;

    @Override
    public boolean checkValid(Team team) {
        for(AbstractStrategy abstractStrategy :strategies){
            if(!abstractStrategy.checkValid(team)){
                return false;
            }
        }
        return true;
    }
}
