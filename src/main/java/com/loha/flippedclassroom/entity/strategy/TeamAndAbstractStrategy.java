package com.loha.flippedclassroom.entity.strategy;

import com.loha.flippedclassroom.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 与策略
 *
 * @author zhoujian
 * @date 2018/12/22
 */
@Setter
@Getter
public class TeamAndAbstractStrategy extends AbstractStrategy {
    List<StrategyInfo> strategyInfos;
    List<AbstractStrategy> strategies;

    @Override
    public boolean checkValid(Team team) {
       for (AbstractStrategy abstractStrategy :strategies){
           if(!abstractStrategy.checkValid(team)){
               return false;
           }
       }
       return true;
    }
}
