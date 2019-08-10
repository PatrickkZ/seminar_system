package com.loha.flippedclassroom.service;

import com.loha.flippedclassroom.dao.QuestionDao;
import com.loha.flippedclassroom.dao.ScoreDao;
import com.loha.flippedclassroom.dao.TeamDao;
import com.loha.flippedclassroom.entity.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * websocket service
 *
 * @author zhoujian
 * @date 2018/12/20
 */
@Service
public class WebsocketService {
    private final TeamDao teamDao;
    private final QuestionDao questionDao;
    private final ScoreDao scoreDao;

    WebsocketService(TeamDao teamDao,QuestionDao questionDao,ScoreDao scoreDao){
        this.teamDao=teamDao;
        this.questionDao=questionDao;
        this.scoreDao=scoreDao;
    }

    public void updateIsPreStatus(Attendance attendance) throws Exception{
        teamDao.updateIsPresentStatus(attendance);
    }

    public void insertQuestion(Question question) throws Exception{
        questionDao.insertQuestion(question);
    }

    public Question getOneQuestion(Question question) throws Exception{
        List<Question> questions=questionDao.getAllQuestion(question);
        int questionQueue=questions.size();
        Question selectQuestion=null;
        if(questionQueue!=0)
        {
            int random=(int)(Math.random()*questionQueue);
            selectQuestion=questions.get(random);
            selectQuestion.setSelected(true);
            questionDao.updateQuestionStatus(selectQuestion);
        }

        return selectQuestion;
    }

    //修改所有小组的提问成绩，存入seminarScore表
    public void setAllTeamsQuestionScore(Long klassSeminarId,Long klassId) throws Exception{
        List<Team> teams=teamDao.getAllTeamsByKlassId(klassId);
        Question question=new Question();
        question.setKlassSeminarId(klassSeminarId);
        SeminarScore seminarScore=new SeminarScore();
        seminarScore.setKlassSeminarId(klassSeminarId);
        for(Team team:teams){
            question.setTeamId(team.getId());
            BigDecimal maxQuestionScore=questionDao.selectMaxTeamQuestionScore(question);
            if(maxQuestionScore!=null){
                seminarScore.setQuestionScore(maxQuestionScore);
                seminarScore.setTeamId(team.getId());
                scoreDao.modifyTeamQuestionScore(seminarScore);
            }
        }

    }


}
