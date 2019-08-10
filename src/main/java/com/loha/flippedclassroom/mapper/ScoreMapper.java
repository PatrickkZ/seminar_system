package com.loha.flippedclassroom.mapper;

import com.loha.flippedclassroom.entity.RoundScore;
import com.loha.flippedclassroom.entity.Seminar;
import com.loha.flippedclassroom.entity.SeminarScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 与讨论课成绩相关的mapper
 *
 * @author zhoujian
 * @date 2018/12/17
 */
@Repository
public interface ScoreMapper {

    /**
     * 某个队伍在某一轮下的成绩情况
     * @param roundScore 利用其roundId和teamId去查询成绩情况
     * @return a RoundScore
     * @throws Exception
     */
    RoundScore selectRoundScore(RoundScore roundScore) throws Exception;

    /**
     * 某个队伍在某一次讨论课下的成绩情况
     * @param map 其中存放有klassId,seminarId和teamId
     * @return a SeminarScore
     * @throws Exception
     */
    SeminarScore selectSeminarScore(Map map) throws Exception;

    /**
     * 更新队伍在某一次讨论课下展示成绩
     * @param seminarScore
     * @throws Exception
     */
    void updateTeamPreScore(SeminarScore seminarScore) throws Exception;

    /**
     * 更新队伍在某一次讨论课下的提问成绩
     * @param seminarScore
     * @throws Exception
     */
    void updateTeamQuestionScore(SeminarScore seminarScore) throws Exception;

    /**
     * 第一次提交成绩时创建一条记录
     * @param seminarScore
     * @throws Exception
     */
    void insertSeminarScore(SeminarScore seminarScore) throws Exception;

    /**
     * 第一次创建轮时候创建记录
     * @param roundScore
     * @throws Exception
     */
    void insertRoundScore(RoundScore roundScore) throws Exception;

    /**
     * 选出某个队伍对应的一条记录
     * @param seminarScore
     * @return SeminarScore
     * @throws Exception
     */
    SeminarScore selectSeminarScoreByKey(SeminarScore seminarScore) throws Exception;

    /**
     * 修改讨论课分数
     * @param seminarScore
     * @throws Exception
     */
    void updateSeminarScore(SeminarScore seminarScore) throws Exception;

    /**
     * 修改讨论课报告分数
     * @param seminarScore
     * @throws Exception
     */
    void updateTeamReportScore(SeminarScore seminarScore) throws Exception;

    /**
     * 修改讨论课总分
     * @param seminarScore
     * @throws Exception
     */
    void updateTeamTotalScore(SeminarScore seminarScore) throws Exception;

    /**
     * 展示最高分
     * @param teamId 小组id
     * @param trueKlassSeminarIds  参加的讨论课id
     * @return bigDecimal
     * @throws Exception
     */
    BigDecimal selectMaxPreScore(@Param("teamId") Long teamId, @Param("trueKlassSeminarIds") List<Long> trueKlassSeminarIds) throws Exception;

    /**
     * 展示平均分
     * @param teamId 小组id
     * @param trueKlassSeminarIds  参加的讨论课id
     * @return bigDecimal
     * @throws Exception
     */
    BigDecimal selectAvgPreScore(@Param("teamId") Long teamId, @Param("trueKlassSeminarIds") List<Long> trueKlassSeminarIds) throws Exception;

    /**
     * 报告最高分
     * @param teamId 小组id
     * @param trueKlassSeminarIds  参加的讨论课id
     * @return bigDecimal
     * @throws Exception
     */
    BigDecimal selectMaxReportScore(@Param("teamId") Long teamId, @Param("trueKlassSeminarIds") List<Long> trueKlassSeminarIds) throws Exception;

    /**
     * 报告平均分
     * @param teamId 小组id
     * @param trueKlassSeminarIds  参加的讨论课id
     * @return bigDecimal
     * @throws Exception
     */
    BigDecimal selectAvgReportScore(@Param("teamId") Long teamId, @Param("trueKlassSeminarIds") List<Long> trueKlassSeminarIds) throws Exception;

    /**
     * 提问最高分
     * @param teamId 小组id
     * @param klassSeminarIds  参加的讨论课id
     * @return bigDecimal
     * @throws Exception
     */
    BigDecimal selectMaxQuestionScore(@Param("teamId") Long teamId, @Param("klassSeminarIds") List<Long> klassSeminarIds) throws Exception;

    /**
     * 提问平均分
     * @param teamId 小组id
     * @param klassSeminarIds  参加的讨论课id
     * @return bigDecimal
     * @throws Exception
     */
    BigDecimal selectAvgQuestionScore(@Param("teamId") Long teamId, @Param("klassSeminarIds") List<Long> klassSeminarIds) throws Exception;

}
