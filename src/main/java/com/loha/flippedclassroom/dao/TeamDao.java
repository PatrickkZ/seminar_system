package com.loha.flippedclassroom.dao;

import com.loha.flippedclassroom.entity.*;
import com.loha.flippedclassroom.mapper.*;
import com.loha.flippedclassroom.pojo.exception.CanNotRegisterException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与Team有关的dao
 *
 * @author zhoujian
 * @date 2018/12/18
 */
@Repository
public class TeamDao {

    private final AttendanceMapper attendanceMapper;
    private final TeamMapper teamMapper;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final KlassTeamMapper klassTeamMapper;

    TeamDao(AttendanceMapper attendanceMapper,TeamMapper teamMapper,StudentMapper studentMapper,CourseMapper courseMapper,KlassTeamMapper klassTeamMapper){
        this.attendanceMapper=attendanceMapper;
        this.teamMapper=teamMapper;
        this.studentMapper=studentMapper;
        this.courseMapper=courseMapper;
        this.klassTeamMapper=klassTeamMapper;
    }

    /**
     * 获取一个小组
     */
    public Team getTeamById(Long teamId) throws Exception{
        return teamMapper.selectTeamById(teamId);
    }

    /**
     * 获取attendance对象
     */
    public Attendance getAttendanceById(Long attendanceId) throws Exception{
        return attendanceMapper.selectAttendanceById(attendanceId);
    }

    /**
     * 修改小组展示状态
     */
    public void updateIsPresentStatus(Attendance attendance) throws Exception{
        attendanceMapper.updateIsPresent(attendance);
    }

   /**
     * 根据teamId和klassSeminarId从attendance表取出相应记录
     * 若某个小组没有报名，则返回null
     */
    public Attendance getAttendanceRecord(Long teamId,Long klassSeminarId) throws Exception{
        Attendance attendance=new Attendance();
        attendance.setTeamId(teamId);
        attendance.setKlassSeminarId(klassSeminarId);
        return attendanceMapper.selectTeamByKlassSeminarId(attendance);
    }

    /**
     * 获取某个学生在某课程下所属的team
     */
    public Team getTeamByCourseAndStudentId(Long courseId,Long studentId) throws Exception{
        Course course=courseMapper.selectCourseById(courseId);
        if(course.getTeamMainCourseId()!=null){
            courseId=course.getTeamMainCourseId();
        }
        Map<String,Long> map=new HashMap<>(16);
        map.put("courseId",courseId);
        map.put("studentId",studentId);
        return teamMapper.selectTeamByCourseAndStudentId(map);
    }

    /**
     * 获取某次讨论课下的Attendance对象，从而获得报名该次讨论课的小组
     */
    public List<Attendance> getEnrollList(Long klassSeminarId) throws Exception{
        return attendanceMapper.selectTeamListByKlassSeminarId(klassSeminarId);
    }

    /**
     * 报名（或者修改报名）某一次讨论课
     */
    public void registerSeminar(Long klassSeminarId,Long teamId,Integer teamOrder) throws Exception{
        Attendance attendance=new Attendance();
        attendance.setKlassSeminarId(klassSeminarId);
        attendance.setTeamId(teamId);
        attendance.setTeamOrder(teamOrder);

        //判断讨论课的某次顺序是否已经被报名
        Attendance temp1=attendanceMapper.selectTeamByKlassSeminarIdAndTeamOrder(attendance);
        if(temp1!=null) {
            throw new CanNotRegisterException();
        }
        else {
            //判断该小组是否已经报名，是的话则修改报名次序
            Attendance temp2=attendanceMapper.selectTeamByKlassSeminarId(attendance);
            if(temp2!=null) {
                attendanceMapper.updateTeamOrder(attendance);
            }
            else {
                attendanceMapper.insertAttendance(attendance);
            }
        }
    }

    /**
     * 取消报名
     */
    public void cancelRegister(Long klassSeminarId,Long teamId) throws Exception{
        Attendance attendance=new Attendance();
        attendance.setKlassSeminarId(klassSeminarId);
        attendance.setTeamId(teamId);

        Attendance temp=attendanceMapper.selectTeamByKlassSeminarId(attendance);
        if(temp!=null){
            attendanceMapper.deleteRegisterRecord(attendance);
        }
    }

    /**
     * 组员上传ppt
     */
    public void submitPowerPoint(Long attendanceId,String pptName,String pptUrl) throws Exception{
        Attendance attendance=new Attendance();
        attendance.setId(attendanceId);
        attendance.setPptName(pptName);
        attendance.setPptUrl(pptUrl);
        attendanceMapper.updatePowerPointByAttendanceId(attendance);
    }

    /**
     * 组员上传报告
     */
    public void submitReport(Long attendanceId,String reportName,String reportUrl) throws Exception{
        Attendance attendance=new Attendance();
        attendance.setId(attendanceId);
        attendance.setReportName(reportName);
        attendance.setReportUrl(reportUrl);
        attendanceMapper.updateReportByAttendanceId(attendance);
    }

    /**
     * 获取一个team，包括组长及其成员
     */
    public Team getTeamAndMembers(Long teamId,Long courseId) throws Exception{
        Team team=teamMapper.selectTeamById(teamId);
        List<Student> member=studentMapper.selectStudentByTeamIdAndCourseId(teamId,courseId);
        team.setMember(member);
        return team;
    }

    public List<Student> getMemberInTeam(Long teamId,Long courseId) throws Exception{
        return studentMapper.selectStudentByTeamIdAndCourseId(teamId,courseId);
    }

    /**
     * 获取某个课程下的所有team
     */
    public List<Team> getAllTeamsByCourseId(Long courseId) throws Exception{
        //判断主从课程
        Course course=courseMapper.selectCourseById(courseId);
        if(course.getTeamMainCourseId()!=null){
            courseId=course.getTeamMainCourseId();
        }
        return teamMapper.selectTeamByCourseId(courseId);
    }

    /**
     * 获取某个班级下的所有team
     */
    public List<Team> getAllTeamsByKlassId(Long klassId) throws Exception{
        return teamMapper.selectTeamByKlassId(klassId);
    }


    /**
     * 删除一个小组的某个组员
     */
    public void deleteStudentInTeam(Map<String,Long> map) throws Exception{
        teamMapper.deleteMemberInTeam(map);
    }

    /**
     * 添加一个小组的某个组员
     */
    public void addStudentInTeam(Map<String,Long> map) throws Exception{
        teamMapper.addMemberInTeam(map);
    }

    /**
     * 解散一个小组
     */
    public void deleteTeamById(Long teamId) throws Exception{
        teamMapper.deleteTeamById(teamId);
    }

    /**
     * 创建一个小组
     */
    public Long createTeam(Team team) throws Exception{
         teamMapper.insertOneTeam(team);
         return team.getId();
    }

    /**
     * 修改小组状态
     */
    public void modifyTeamValid(Team team) throws Exception{
        teamMapper.updateTeamValide(team);
    }

    /**
     * 删除一个班级中的队伍(针对klass_team表)
     */
    public void deleteKlassTeamByKey(Map<String,Long> map) throws Exception{
        Integer count=klassTeamMapper.selectKlassTeamByKey(map);
        if (count!=0)
        {
            klassTeamMapper.deleteKlassTeamByKey(map);
        }
    }

    /**
     * 添加一个班级中的队伍
     */
    public void insertKlassTeam(Map<String,Long> map) throws Exception{
        klassTeamMapper.insertKlassTeam(map);
    }

    public List<Long> selectKlassSeminarIdAttended(Long teamId,List<Long> klassSeminarIds) throws Exception{
        return attendanceMapper.selectKlassSeminarIdAttended(teamId, klassSeminarIds);
    }

    public void deleteTeamByKlassId(Long klassId) throws Exception{
        klassTeamMapper.deleteTeamByKlassId(klassId);
    }

}