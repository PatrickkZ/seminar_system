package com.loha.flippedclassroom.mapper;

import com.loha.flippedclassroom.entity.strategy.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 策略mapper
 *
 * @author zhoujian
 * @date 2018/12/16
 */
@Repository
public interface StrategyMapper {

    /**
     * 根据课程id找到对应的策略对象所在的表名和策略id
     * @param courseId 课程的id
     * @return TeamAbstractStrategy 可以获取策略表的名字和策略id
     * @throws Exception
     */
    List<TeamStrategyInfo> selectTeamStrategyByCourseId(Long courseId) throws Exception;


    /**
     * 根据MemberLimitStrategy的id找到对应的策略
     * @param id MemberLimitStrategy的id
     * @return MemberLimitAbstractStrategy
     */
    MemberLimitAbstractStrategy selectMemberLimitStrategyById(Long id);

    /**
     * 根据CourseMemberLimitStrategy的id找到对应的策略
     * @param id CourseMemberLimitStrategy的id
     * @return CourseMemberLimitAbstractStrategy
     */
    CourseMemberLimitAbstractStrategy selectCourseMemberLimitStrategyById(Long id);


    /**
     * 冲突课程的id
     * @param id
     * @return List
     * @return CourseMemberLimitAbstractStrategy
     */
    List<Long> selectConflictCourseId(Long id);

    /**
     * 与策略
     * @param id
     * @return List
     */
    List<StrategyInfo> selectTeamAndStrategy(Long id);

    /**
     * 或策略
     * @param id
     * @return List
     */
    List<StrategyInfo> selectTeamOrStrategy(Long id);

    /**
     * 插入策略
     * @param memberLimitStrategy
     */
    void insertMemberLimitStrategy(MemberLimitAbstractStrategy memberLimitStrategy);

    /**
     * 插入策略
     * @param courseMemberLimitStrategy
     */
    void insertCourseMemberLimitStrategy(CourseMemberLimitAbstractStrategy courseMemberLimitStrategy);

    /**
     * 插入策略
     * @param conflictCourseStrategies
     */
    void insertConflictCourseStrategy(List<ConflictCourseAbstractStrategy> conflictCourseStrategies);

    /**
     * 插入策略
     * @param strategyInfos
     */
    void insertTeamAndStrategy(List<StrategyInfo> strategyInfos);

    /**
     * 插入策略
     * @param strategyInfos
     */
    void insertTeamOrStrategy(List<StrategyInfo> strategyInfos);

    /**
     * 插入策略
     * @param teamStrategyInfo
     */
    void insertTeamStrategy(TeamStrategyInfo teamStrategyInfo);

    /**
     * 选出上一次插入策略的id
     * @return id
     */
    Long selectConflictCourseStrategyLastInsert();

    /**
     * 选出上一次插入策略的id
     * @return id
     */
    Long selectTeamOrStrategyLastInsert();

    /**
     * 选出上一次插入策略的id
     * @return id
     */
    Long selectTeamAndStrategyLastInsert();

}
