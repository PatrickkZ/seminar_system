package com.loha.flippedclassroom.dao;

import com.loha.flippedclassroom.entity.*;
import com.loha.flippedclassroom.mapper.*;
import com.loha.flippedclassroom.pojo.dto.ScoreInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 与成绩相关的dao
 *
 * @author zhoujian
 * @date 2018/12/15
 */
@Repository
@Slf4j
public class ScoreDao {

    private final ScoreMapper scoreMapper;
    private final KlassSeminarMapper klassSeminarMapper;
    private final TeamMapper teamMapper;
    private final AttendanceMapper attendanceMapper;
    private final QuestionMapper questionMapper;


    @Autowired
    ScoreDao(ScoreMapper scoreMapper,KlassSeminarMapper klassSeminarMapper,TeamMapper teamMapper,AttendanceMapper attendanceMapper,QuestionMapper questionMapper){
        this.scoreMapper=scoreMapper;
        this.klassSeminarMapper=klassSeminarMapper;
        this.teamMapper=teamMapper;
        this.attendanceMapper=attendanceMapper;
        this.questionMapper=questionMapper;
    }

    public RoundScore selectRoundScoreByKey(RoundScore roundScore) throws Exception{
        return scoreMapper.selectRoundScore(roundScore);
    }

    /**
     * 根据team的id和round的id查询某个team在某一轮下的成绩情况
     * teamId根据klassStudent表查找，通过课程找round（round中有seminars）,再用seminarid和classid和teamid找成绩
     */
    public ScoreInfo getOneTeamScoreUnderOneRound(Long klassId, Round round, Long teamId) throws Exception{

        //获取小组信息
        Team team=teamMapper.selectTeamById(teamId);

        RoundScore temp=new RoundScore();
        temp.setRoundId(round.getId());
        temp.setTeamId(teamId);
        //拿到某一轮的总成绩,roundScore中有round
        RoundScore roundScore=scoreMapper.selectRoundScore(temp);
        List<SeminarScore> seminarScoreList=new LinkedList<>();
        for(Seminar seminar:round.getSeminars()){

            //用于查询的map
            Map<String,Long> findScoreMap=new HashMap(16);
            findScoreMap.put("klassId",klassId);
            findScoreMap.put("seminarId",seminar.getId());
            findScoreMap.put("teamId",teamId);

            SeminarScore seminarScore=scoreMapper.selectSeminarScore(findScoreMap);
            if(seminarScore!=null){
                KlassSeminar klassSeminar=klassSeminarMapper.selectKlassSeminarById(seminarScore.getKlassSeminarId());
                Seminar setSeminar=klassSeminar.getSeminar();
                seminarScore.setSeminar(setSeminar);

                seminarScoreList.add(seminarScore);
            }

        }

        //DTO
        ScoreInfo scoreInfo=new ScoreInfo();
        scoreInfo.setTeam(team);
        scoreInfo.setRoundScore(roundScore);
        scoreInfo.setSeminarScores(seminarScoreList);
        return scoreInfo;
    }

    /**
     *某一次讨论课的成绩
     */
    public SeminarScore getOneSeminarScore(Long klassId,Long seminarId,Long teamId) throws Exception{
        Map<String,Long> map=new HashMap<>(16);
        map.put("klassId",klassId);
        map.put("seminarId",seminarId);
        map.put("teamId",teamId);
        return scoreMapper.selectSeminarScore(map);
    }

    /**
     *向seminar_score表插入一条记录，提问成绩默认设置为0
     */
    public void insertSeminarScore(SeminarScore seminarScore) throws Exception{
        scoreMapper.insertSeminarScore(seminarScore);
    }

    /**
     *修改小组的展示成绩
     */
    public void modifyTeamPreScore(SeminarScore seminarScore) throws Exception{
        if(scoreMapper.selectSeminarScoreByKey(seminarScore)==null){
            scoreMapper.insertSeminarScore(seminarScore);
        }
        scoreMapper.updateTeamPreScore(seminarScore);
    }

    /**
     *修改小组提问成绩
     */
    public void modifyTeamQuestionScore(SeminarScore seminarScore) throws Exception{
        if(scoreMapper.selectSeminarScoreByKey(seminarScore)==null){
            scoreMapper.insertSeminarScore(seminarScore);
        }
        scoreMapper.updateTeamQuestionScore(seminarScore);
    }

    public SeminarScore selectSeminarScoreByKey(SeminarScore seminarScore) throws Exception{
        return scoreMapper.selectSeminarScoreByKey(seminarScore);
    }

    public void insertRoundScore(RoundScore roundScore) throws Exception{
        scoreMapper.insertRoundScore(roundScore);
    }

    public void updateTeamReportScore(SeminarScore seminarScore) throws Exception{
        scoreMapper.updateTeamReportScore(seminarScore);
    }

    public void updateTeamTotalScore(SeminarScore seminarScore) throws Exception{
        scoreMapper.updateTeamTotalScore(seminarScore);
    }

    public BigDecimal selectMaxPreScore(Long teamId, List<Long> trueKlassSeminarIds) throws Exception{
        return scoreMapper.selectMaxPreScore(teamId, trueKlassSeminarIds);
    }

    public BigDecimal selectAvgPreScore(Long teamId, List<Long> trueKlassSeminarIds) throws Exception{
        return scoreMapper.selectAvgPreScore(teamId, trueKlassSeminarIds);
    }

    public BigDecimal selectMaxReportScore(Long teamId, List<Long> trueKlassSeminarIds) throws Exception{
        return scoreMapper.selectMaxReportScore(teamId, trueKlassSeminarIds);
    }

    public BigDecimal selectAvgReportScore(Long teamId, List<Long> trueKlassSeminarIds) throws Exception{
        return scoreMapper.selectAvgReportScore(teamId, trueKlassSeminarIds);
    }

    public BigDecimal selectMaxQuestionScore(Long teamId, List<Long> klassSeminarIds) throws Exception{
        return scoreMapper.selectMaxQuestionScore(teamId, klassSeminarIds);
    }

    public BigDecimal selectAvgQuestionScore(Long teamId, List<Long> klassSeminarIds) throws Exception{
        return scoreMapper.selectAvgQuestionScore(teamId, klassSeminarIds);
    }

    public void updateSeminarScore(SeminarScore seminarScore) throws Exception{
        scoreMapper.updateSeminarScore(seminarScore);
    }
}
