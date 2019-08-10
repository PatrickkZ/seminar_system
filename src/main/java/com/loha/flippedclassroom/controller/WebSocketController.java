package com.loha.flippedclassroom.controller;

import com.loha.flippedclassroom.dao.SeminarDao;
import com.loha.flippedclassroom.dao.TeamDao;
import com.loha.flippedclassroom.entity.Attendance;
import com.loha.flippedclassroom.entity.KlassSeminar;
import com.loha.flippedclassroom.entity.Question;
import com.loha.flippedclassroom.entity.Student;
import com.loha.flippedclassroom.pojo.websocket.EndMessage;
import com.loha.flippedclassroom.pojo.websocket.QuestionMessage;
import com.loha.flippedclassroom.pojo.websocket.WebSocketMessage;
import com.loha.flippedclassroom.service.TeacherService;
import com.loha.flippedclassroom.service.WebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * websocket controller
 *
 * @author zhoujian
 * @date 2018/12/25
 */
@Controller
@Slf4j
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WebsocketService websocketService;
    private final TeacherService teacherService;

    @Autowired
    WebSocketController(SimpMessagingTemplate simpMessagingTemplate,WebsocketService websocketService,TeacherService teacherService){
        this.simpMessagingTemplate=simpMessagingTemplate;
        this.websocketService=websocketService;
        this.teacherService=teacherService;
    }


    @MessageMapping("/nextPre")
    public void nextTeamPre(Message message,@RequestBody Map<String,String> map) throws Exception{

        Attendance attendance=new Attendance();
        attendance.setId(Long.parseLong(map.get("curAttendanceId")));
        attendance.setIsPresent(0);
        websocketService.updateIsPreStatus(attendance);

        attendance.setId(Long.parseLong(map.get("nextAttendanceId")));
        attendance.setIsPresent(1);
        websocketService.updateIsPreStatus(attendance);

        WebSocketMessage webSocketMessage=new WebSocketMessage();
        webSocketMessage.setStatus("2");
        webSocketMessage.setTeamOrder(map.get("nextTeamOrder"));
        webSocketMessage.setAttendanceId(map.get("nextAttendanceId"));
        simpMessagingTemplate.convertAndSend("/topic/getResponse"+map.get("klassSeminarId"),webSocketMessage);
    }

    @MessageMapping("/student/question")
    public void submitQuestion(Message message, @RequestBody Question question) throws Exception{
        question.setSelected(false);
        websocketService.insertQuestion(question);

        WebSocketMessage webSocketMessage=new WebSocketMessage();
        webSocketMessage.setStatus("1");
        simpMessagingTemplate.convertAndSend("/topic/getResponse"+question.getKlassSeminarId(),webSocketMessage);
    }

    @MessageMapping("/teacher/question")
    public void selectQuestion(Message message,@RequestBody Question question) throws Exception{
        question.setSelected(false);
        Question selectedQuestion=websocketService.getOneQuestion(question);

        QuestionMessage questionMessage=new QuestionMessage();
        questionMessage.setStatus("3");
        questionMessage.setQuestionId(selectedQuestion.getId());
        questionMessage.setName(selectedQuestion.getStudent().getStudentName());
        String teamSerial=selectedQuestion.getTeam().getKlass().getKlassSerial()+"-"+selectedQuestion.getTeam().getTeamSerial();
        questionMessage.setTeamSerial(teamSerial);
        simpMessagingTemplate.convertAndSend("/topic/getResponse"+question.getKlassSeminarId(),questionMessage);
    }

    @MessageMapping("/teacher/end")
    public void endSeminar(Message message, @RequestBody Map<String,String> map) throws Exception{
        KlassSeminar klassSeminar=new KlassSeminar();
        klassSeminar.setId(Long.parseLong(map.get("id")));
        klassSeminar.setKlassId(Long.parseLong(map.get("klassId")));
        klassSeminar.setSeminarId(Long.parseLong(map.get("seminarId")));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        klassSeminar.setReportDdl(sdf.parse(map.get("reportDdl")));
        klassSeminar.setStatus(Integer.parseInt(map.get("status")));

        teacherService.updateKlassSeminarStatus(klassSeminar);

        //更新小组的提问分数
        websocketService.setAllTeamsQuestionScore(klassSeminar.getId(),klassSeminar.getKlassId());

        EndMessage endMessage=new EndMessage();
        endMessage.setStatus("4");
        endMessage.setReportDDL(klassSeminar.getReportDdl());

        simpMessagingTemplate.convertAndSend("/topic/getResponse"+klassSeminar.getId(),endMessage);
    }


}
