package com.loha.flippedclassroom.dao;

import com.loha.flippedclassroom.entity.strategy.*;
import com.loha.flippedclassroom.mapper.StrategyMapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * 与策略相关的dao
 *
 * @author zhoujian
 * @date 2018/12/22
 */
@Repository
public class StrategyDao {
    private final StrategyMapper strategyMapper;

    StrategyDao(StrategyMapper strategyMapper) {
        this.strategyMapper = strategyMapper;
    }

    private AbstractStrategy findStrategyByTableName(String strategyTableName, Long strategyId) throws Exception {
        switch (strategyTableName) {
            case "TeamAndAbstractStrategy": {
                List<StrategyInfo> strategyInfos=strategyMapper.selectTeamAndStrategy(strategyId);
                TeamAndAbstractStrategy teamAndStrategy=new TeamAndAbstractStrategy();
                teamAndStrategy.setStrategyInfos(strategyInfos);
                return teamAndStrategy;
            }
            case "TeamOrAbstractStrategy": {
                List<StrategyInfo> strategyInfos=strategyMapper.selectTeamOrStrategy(strategyId);
                TeamOrAbstractStrategy teamOrStrategy=new TeamOrAbstractStrategy();
                teamOrStrategy.setStrategyInfos(strategyInfos);
                return teamOrStrategy;
            }
            case "CourseMemberLimitAbstractStrategy": {
                return strategyMapper.selectCourseMemberLimitStrategyById(strategyId);
            }
            case "MemberLimitAbstractStrategy": {
                return strategyMapper.selectMemberLimitStrategyById(strategyId);
            }
            case "ConflictCourseAbstractStrategy": {
                List<Long> courseIds=strategyMapper.selectConflictCourseId(strategyId);
                ConflictCourseAbstractStrategy conflictCourseStrategy=new ConflictCourseAbstractStrategy();
                conflictCourseStrategy.setCourseIds(courseIds);
                return conflictCourseStrategy;
            }
            default:
                return null;
        }
    }

    private void setSubStrategy(AbstractStrategy abstractStrategy) throws Exception {
        //子策略列表
        List<AbstractStrategy> strategies=new LinkedList<>();
        List<StrategyInfo> strategyInfos;
        AbstractStrategy subAbstractStrategy;

        if(abstractStrategy instanceof TeamAndAbstractStrategy){
            strategyInfos=((TeamAndAbstractStrategy) abstractStrategy).getStrategyInfos();
            for (StrategyInfo strategyInfo:strategyInfos){
                subAbstractStrategy =findStrategyByTableName(strategyInfo.getStrategyName(),strategyInfo.getStrategyId());
                if(subAbstractStrategy instanceof TeamAndAbstractStrategy || subAbstractStrategy instanceof TeamOrAbstractStrategy){
                    setSubStrategy(subAbstractStrategy);
                }
                strategies.add(subAbstractStrategy);
            }
            ((TeamAndAbstractStrategy) abstractStrategy).setStrategies(strategies);
            return;
        }


        if(abstractStrategy instanceof TeamOrAbstractStrategy){
            strategyInfos=((TeamOrAbstractStrategy) abstractStrategy).getStrategyInfos();
            for (StrategyInfo strategyInfo:strategyInfos){
                subAbstractStrategy =findStrategyByTableName(strategyInfo.getStrategyName(),strategyInfo.getStrategyId());
                if(subAbstractStrategy instanceof TeamAndAbstractStrategy || subAbstractStrategy instanceof TeamOrAbstractStrategy){
                    setSubStrategy(subAbstractStrategy);
                }
                strategies.add(subAbstractStrategy);
            }
            ((TeamOrAbstractStrategy) abstractStrategy).setStrategies(strategies);
            return;
        }




    }

    /**
     * 获取某个课程的策略对象
     */
    public AbstractStrategy getCourseStrategy(Long courseId) throws Exception {
        List<TeamStrategyInfo> teamStrategyInfos=strategyMapper.selectTeamStrategyByCourseId(courseId);

        TeamAbstractStrategy teamStrategy=new TeamAbstractStrategy();
        List<AbstractStrategy> strategies=new LinkedList<>();

        for(TeamStrategyInfo teamStrategyInfo:teamStrategyInfos){
            String strategyTableName = teamStrategyInfo.getStrategyTableName();
            Long strategyId = teamStrategyInfo.getStrategyId();
            AbstractStrategy curAbstractStrategy = findStrategyByTableName(strategyTableName, strategyId);
            if (curAbstractStrategy instanceof TeamAndAbstractStrategy || curAbstractStrategy instanceof TeamOrAbstractStrategy) {
                setSubStrategy(curAbstractStrategy);
            }
            strategies.add(curAbstractStrategy);
        }
        teamStrategy.setStrategies(strategies);
        return teamStrategy;
    }

    public Long insertMemberLimitStrategy(MemberLimitAbstractStrategy memberLimitStrategy){
        strategyMapper.insertMemberLimitStrategy(memberLimitStrategy);
        return memberLimitStrategy.getId();
    }

    public Long insertCourseMemberLimitStrategy(CourseMemberLimitAbstractStrategy courseMemberLimitStrategy){
        strategyMapper.insertCourseMemberLimitStrategy(courseMemberLimitStrategy);
        return courseMemberLimitStrategy.getId();
    }

    public Long insertConflictCourseStrategy(List<ConflictCourseAbstractStrategy> conflictCourseStrategies){
        strategyMapper.insertConflictCourseStrategy(conflictCourseStrategies);
        return strategyMapper.selectConflictCourseStrategyLastInsert();
    }

    public Long insertTeamAndStrategy(List<StrategyInfo> strategyInfos){
        strategyMapper.insertTeamAndStrategy(strategyInfos);
        return strategyMapper.selectTeamAndStrategyLastInsert();
    }

    public Long insertTeamOrStrategy(List<StrategyInfo> strategyInfos){
        strategyMapper.insertTeamOrStrategy(strategyInfos);
        return strategyMapper.selectTeamOrStrategyLastInsert();
    }

    public void insertTeamStrategy(List<TeamStrategyInfo> teamStrategyInfos){
        for(TeamStrategyInfo teamStrategyInfo:teamStrategyInfos){
            strategyMapper.insertTeamStrategy(teamStrategyInfo);
        }

    }


}
