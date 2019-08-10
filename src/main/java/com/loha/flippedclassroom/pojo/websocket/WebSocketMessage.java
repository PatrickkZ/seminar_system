package com.loha.flippedclassroom.pojo.websocket;

import com.loha.flippedclassroom.entity.Question;
import com.loha.flippedclassroom.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketMessage {
    //1举手，2下一组展示
    private String status;
    private String teamOrder;
    private String attendanceId;
    private Student student;
    private Question question;
}
