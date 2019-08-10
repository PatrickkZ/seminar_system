package com.loha.flippedclassroom.service;

import com.loha.flippedclassroom.dao.*;
import com.loha.flippedclassroom.entity.*;
import com.loha.flippedclassroom.entity.strategy.*;
import com.loha.flippedclassroom.util.SortEnrollList;
import com.loha.flippedclassroom.util.SortMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * team service
 * 学生组队模块
 *
 * @author zhoujian
 * @date 2018/12/16
 */
@Service
@Slf4j
public class TeamService {

    private final TeamDao teamDao;
    private final SeminarDao seminarDao;
    private final StrategyDao strategyDao;
    private final StudentDao studentDao;
    private final ApplicationDao applicationDao;
    private final KlassDao klassDao;
    private final RoundDao roundDao;
    private final CourseDao courseDao;

    @Autowired
    TeamService(TeamDao teamDao,SeminarDao seminarDao,StrategyDao strategyDao,StudentDao studentDao,ApplicationDao applicationDao,KlassDao klassDao,RoundDao roundDao,CourseDao courseDao){
        this.klassDao=klassDao;
        this.teamDao=teamDao;
        this.seminarDao=seminarDao;
        this.strategyDao=strategyDao;
        this.studentDao=studentDao;
        this.applicationDao=applicationDao;
        this.roundDao=roundDao;
        this.courseDao=courseDao;
    }

    /**
     * 判断某个队伍是否符合策略模式
     */
    private boolean teamIsValid(Long teamId,Long courseId) throws Exception{
        //先用team的id查出team对象
        Team team=teamDao.getTeamAndMembers(teamId,courseId);

        //某个班的策略
        AbstractStrategy abstractStrategy =strategyDao.getCourseStrategy(courseId);

        return abstractStrategy.checkValid(team);
    }

    /**
     * 获取某个学生在某个课程下所属的team在某次讨论课时的状态
     * 讨论课有未开始，进行中，开始状态，小组有报名和未报名状态
     * 前提:学生必须加入了小组
     */
    public String getTeamSeminarStatus(Long studentId,Long klassId,Long courseId,Long seminarId) throws Exception{
        Team team=teamDao.getTeamByCourseAndStudentId(courseId,studentId);

        KlassSeminar klassSeminar=seminarDao.getKlassSeminar(klassId,seminarId);

        //讨论课状态
        Integer seminarStatus=klassSeminar.getStatus();

        //小组参加状态
        Attendance attend=teamDao.getAttendanceRecord(team.getId(),klassSeminar.getId());

        if (seminarStatus==0)
        {
            if(attend==null)
            {
                return "unOpenUnregister";
            }
            else {
                return "unOpenRegister";
            }
        }
        else if (seminarStatus==1){
            if(attend==null)
            {
                return "underwayUnregister";
            }
            else {
                return "underwayRegister";
            }
        }
        else {
            if(attend==null)
            {
                return "finishedUnregister";
            }
            else {
                return "finishedRegister";
            }
        }
    }


    /**
     * 某个小组在某次讨论课下的attendance对象
     */
    public Attendance getAttendanceUnderSeminar(Long klassId,Long seminarId,Long teamId) throws Exception{
        Long klassSeminarId=seminarDao.getKlassSeminar(klassId,seminarId).getId();
        return teamDao.getAttendanceRecord(teamId,klassSeminarId);
    }

    /**
     * 获取某次讨论课的报名列表
     */
    public List<Attendance> getEnrollList(Long klassId, Long seminarId) throws Exception{
        KlassSeminar klassSeminar=seminarDao.getKlassSeminar(klassId,seminarId);

        List<Attendance> enrollList=teamDao.getEnrollList(klassSeminar.getId());

        int teamLimit= seminarDao.getSeminarById(seminarId).getTeamLimit();

        return SortEnrollList.sort(enrollList,teamLimit);
    }


    /**
     * 某一课程下的所有team
     */
    public List<Team> getTeamsByCourseId(Long courseId) throws Exception{
        //课程下的所有team
        List<Team> teamList=teamDao.getAllTeamsByCourseId(courseId);

        List<Team> teams=new LinkedList<>();
        for(Team team:teamList){
            team=teamDao.getTeamAndMembers(team.getId(),courseId);
            teams.add(team);
        }
        return teams;
    }

    /**
     * 某一课程下我的team
     */
    public Team getMyTeamUnderCourse(Long teamId,Long courseId) throws Exception{
        Team team=teamDao.getTeamAndMembers(teamId,courseId);
        List<Student> member=team.getMember();
        Student leader=team.getLeader();
        for(Student temp:member){
            if(temp.getId().equals(leader.getId())){
                member.remove(temp);
                break;
            }
        }
        team.setMember(member);
        return team;
    }

    /**
     * 某一课程下的所有未组队的学生
     */
    public List<Student> getStudentsNotInTeamByCourseId(Long courseId,Long studentId) throws Exception{
        List<Student> studentInTeam=studentDao.getStudentsInTeamByCourseId(courseId);
        List<Student> studentsInCourse=studentDao.selectStudentByCourseId(courseId);

        List<Student> notInTeam=new LinkedList<>();
        boolean inTeam;
        for(Student studentOne:studentsInCourse){
            inTeam=false;
            for(Student studentTwo:studentInTeam){
                if(studentOne.getId().equals(studentTwo.getId())){
                    inTeam=true;
                    break;
                }
            }
            if (!inTeam){
                notInTeam.add(studentOne);
            }
        }

        for(Student student:notInTeam){
            if(student.getId().equals(studentId)){
                notInTeam.remove(student);
                break;
            }
        }
        return notInTeam;
    }


    /**
     * 删除队伍中的一个学生
     */
    public void deleteStudentInTeam(Map<String,Long> map,Long courseId) throws Exception{
        teamDao.deleteStudentInTeam(map);

        Date curDate=new Date();
        Course course=courseDao.getCourseById(courseId);
        Date teamEndTime=course.getTeamEndTime();

        Team team=new Team();
        team.setId(map.get("teamId"));
        if(curDate.after(teamEndTime)){
            team.setStatus(0);
        }
        else {
            boolean valid=teamIsValid(map.get("teamId"),courseId);
            if(valid){
                team.setStatus(1);
            }else {
                team.setStatus(0);
            }
        }

        teamDao.modifyTeamValid(team);
    }

    /**
     * 添加队伍中的一个学生
     */
    public void addStudentInTeam(Map<String,Long> map,Long courseId) throws Exception{
        teamDao.addStudentInTeam(map);
        Date curDate=new Date();
        Course course=courseDao.getCourseById(courseId);
        Date teamEndTime=course.getTeamEndTime();
        Team team=new Team();
        team.setId(map.get("teamId"));
        if(curDate.after(teamEndTime)){
            team.setStatus(0);
        }
        else {
            boolean valid=teamIsValid(map.get("teamId"),courseId);
            if(valid){
                team.setStatus(1);
            }else {
                team.setStatus(0);
            }
        }
        teamDao.modifyTeamValid(team);
    }

    /**
     * 解散小组
     */
    public void disbandTeam(Long teamId,Long courseId,Long klassId) throws Exception{
        Map<String,Long> map=new HashMap<>(16);
        map.put("teamId",teamId);

        //修改team_student表中的相关记录
        List<Student> member=teamDao.getMemberInTeam(teamId,courseId);
        for (Student student:member){
            map.put("studentId",student.getId());
            teamDao.deleteStudentInTeam(map);
        }

        //删除klass_team表的记录
        Map<String,Long> deleteMap=new HashMap<>(16);
        deleteMap.put("klassId",klassId);
        deleteMap.put("teamId",teamId);
        teamDao.deleteKlassTeamByKey(deleteMap);

        //删除team表相关记录
        teamDao.deleteTeamById(teamId);
    }

    /**
     * 接受共享之后需要做一些处理
     */
    public void acceptTeamShare(ShareTeamApp shareTeamApp) throws Exception{
        //找到从课程所有班级
        List<Klass> klasses=klassDao.getKlassByCourseId(shareTeamApp.getSubCourseId());
        //删除所有班级中的所有队伍(klass_team表)
        List<Team> teams;
        for(Klass klass:klasses){
            Long klassId=klass.getId();
            teams=teamDao.getAllTeamsByKlassId(klass.getId());
            if(teams.size()!=0){
                for(Team team:teams){
                    disbandTeam(team.getId(),shareTeamApp.getSubCourseId(),klassId);
                }
            }
        }
        //用于查询班级id的map
        Map<String,Long> klassIdMap=new HashMap<>(16);
        klassIdMap.put("courseId",shareTeamApp.getSubCourseId());
        //记录班级id
        Long klassId;
        //用于记录每个班级人数的map
        Map<Long,Integer> klassStudentMap;
        List<Team> mainCourseTeam=teamDao.getAllTeamsByCourseId(shareTeamApp.getMainCourseId());
        for(Team team:mainCourseTeam){
            klassStudentMap=new HashMap<>(16);
            List<Student> member=teamDao.getMemberInTeam(team.getId(),shareTeamApp.getMainCourseId());
            //找到每个学生在从课程所属的班级
            for(Student student:member){
                klassIdMap.put("studentId",student.getId());
                klassId=klassDao.getKlassIdByCourseAndStudentId(klassIdMap);
                if(klassId!=null){
                    if(klassStudentMap.containsKey(klassId)){
                        klassStudentMap.put(klassId,klassStudentMap.get(klassId)+1);
                    }
                    else {
                        klassStudentMap.put(klassId,1);
                    }
                }
            }

            //找到klassStudentmap中的最大值
            klassId= SortMap.getMapMaxKey(klassStudentMap);
            Map<String,Long> insertMap=new HashMap<>(16);
            insertMap.put("klassId",klassId);
            insertMap.put("teamId",team.getId());
            teamDao.insertKlassTeam(insertMap);
        }

        Map<String,Long> updateMap=new HashMap<>(16);
        updateMap.put("teamMainCourseId",shareTeamApp.getMainCourseId());
        updateMap.put("id",shareTeamApp.getSubCourseId());
        courseDao.updateTeamMainCourse(updateMap);

        shareTeamApp.setStatus(1);
        applicationDao.updateShareTeamAppStatus(shareTeamApp);

    }

    /**
     * 创建小组
     */
    public  void createTeam(Team team,String[] memberAccount,Long courseId,Long klassId) throws Exception{
        //先默认不合法
        team.setStatus(0);

        int number=teamDao.getAllTeamsByKlassId(klassId).size();
        team.setTeamSerial(number+1);

        Long teamId=teamDao.createTeam(team);

        Map<String,Long> map=new HashMap<>(16);
        map.put("teamId",teamId);
        for(String account:memberAccount){
            Long studentId=studentDao.getCurStudent(account).getId();
            map.put("studentId",studentId);
            teamDao.addStudentInTeam(map);
        }
        map.put("studentId",team.getLeaderId());
        teamDao.addStudentInTeam(map);

        boolean valid=teamIsValid(teamId,courseId);
        Team temp=new Team();
        temp.setId(teamId);
        if(valid){
            temp.setStatus(1);
        }else {
            temp.setStatus(0);
        }
        teamDao.modifyTeamValid(team);

        //插入klass_team表
        Map<String,Long> insertMap=new HashMap<>(16);
        insertMap.put("klassId",klassId);
        insertMap.put("teamId",teamId);
        teamDao.insertKlassTeam(insertMap);
    }

    /**
     * 创建规则
     */
    public void createTeamRules(Long courseId, MemberLimitAbstractStrategy memberLimitStrategy, List<CourseMemberLimitAbstractStrategy> courseMemberLimitStrategies,
                                List<List<ConflictCourseAbstractStrategy>> conflictCourseStrategies){
        Long memberLimitStrategyId=strategyDao.insertMemberLimitStrategy(memberLimitStrategy);
        List<StrategyInfo> strategyInfos=new LinkedList<>();
        StrategyInfo strategyInfo;
        for(CourseMemberLimitAbstractStrategy courseMemberLimitStrategy:courseMemberLimitStrategies){
            strategyInfo=new StrategyInfo();
            strategyInfo.setStrategyName("CourseMemberLimitAbstractStrategy");
            Long courseMemberLimitStrategyId=strategyDao.insertCourseMemberLimitStrategy(courseMemberLimitStrategy);
            strategyInfo.setStrategyId(courseMemberLimitStrategyId);
            strategyInfos.add(strategyInfo);
        }
        Long teamOrStrategyId=strategyDao.insertTeamOrStrategy(strategyInfos);

        List<StrategyInfo> strategyInfos2=new LinkedList<>();
        strategyInfo=new StrategyInfo();
        strategyInfo.setStrategyName("MemberLimitAbstractStrategy");
        strategyInfo.setStrategyId(memberLimitStrategyId);
        strategyInfos2.add(strategyInfo);

        strategyInfo=new StrategyInfo();
        strategyInfo.setStrategyName("TeamOrAbstractStrategy");
        strategyInfo.setStrategyId(teamOrStrategyId);
        strategyInfos2.add(strategyInfo);

        Long teamAndStrategyId=strategyDao.insertTeamAndStrategy(strategyInfos2);

        List<TeamStrategyInfo> teamStrategyInfos=new LinkedList<>();
        TeamStrategyInfo teamStrategyInfo=new TeamStrategyInfo();

        teamStrategyInfo.setCourseId(courseId);
        teamStrategyInfo.setStrategySerial(1);
        teamStrategyInfo.setStrategyTableName("TeamAndAbstractStrategy");
        teamStrategyInfo.setStrategyId(teamAndStrategyId);

        teamStrategyInfos.add(teamStrategyInfo);

        if(conflictCourseStrategies.size()!=0){
            int i=2;
            for(List<ConflictCourseAbstractStrategy> temp:conflictCourseStrategies){
                teamStrategyInfo=new TeamStrategyInfo();
                Long conflictCourseStrategyId=strategyDao.insertConflictCourseStrategy(temp);
                teamStrategyInfo.setCourseId(courseId);
                teamStrategyInfo.setStrategySerial(i);
                teamStrategyInfo.setStrategyTableName("ConflictCourseAbstractStrategy");
                teamStrategyInfo.setStrategyId(conflictCourseStrategyId);
                teamStrategyInfos.add(teamStrategyInfo);
                i++;
            }
        }


        strategyDao.insertTeamStrategy(teamStrategyInfos);

    }

    public void createTeamValidApp(TeamValidApp teamValidApp) throws Exception{
        applicationDao.insertTeamValidApp(teamValidApp);
        Team team=new Team();
        team.setStatus(2);
        team.setId(teamValidApp.getTeamId());
        teamDao.modifyTeamValid(team);
    }


    public boolean teamEnrollPermission(Long teamId,Long klassId,Long seminarId)throws Exception
    {
        Seminar seminar = seminarDao.getSeminarById(seminarId);
        Round round=roundDao.getRoundById(seminar.getRoundId());
        int enrollnumber=roundDao.getEnrollNumber(round.getId(), klassId);
        List<KlassSeminar> klassSeminarList=new ArrayList<>();
        for (Seminar seminar1:round.getSeminars())
        {
            klassSeminarList.add(seminarDao.getKlassSeminar(klassId, seminar1.getId()));
        }
        int count=0;
        for (KlassSeminar klassSeminar: klassSeminarList)
        {
            if (teamDao.getAttendanceRecord(teamId,klassSeminar.getId())!=null)
            {
                count++;
            }
        }
        System.out.println(count);
        return count < enrollnumber;
    }

    public void acceptTeamValid(TeamValidApp teamValidApp,Long teamId) throws Exception{
        applicationDao.updateTeamValidAppStatus(teamValidApp);
        Team team=teamDao.getTeamById(teamId);
        team.setStatus(1);
        teamDao.modifyTeamValid(team);
    }


}
