package com.loha.flippedclassroom.dao;

import com.loha.flippedclassroom.entity.ShareTeamApp;
import com.loha.flippedclassroom.entity.TeamValidApp;
import com.loha.flippedclassroom.mapper.ApplicationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 与请求相关的dao
 *
 * @author zhoujian
 * @date 2018/12/15
 */
@Repository
public class ApplicationDao {
    private final ApplicationMapper applicationMapper;

    ApplicationDao(ApplicationMapper applicationMapper){
        this.applicationMapper=applicationMapper;
    }

    public void insertTeamValidApp(TeamValidApp teamValidApp) throws Exception{
        applicationMapper.insertTeamValidApp(teamValidApp);
    }

    public void insertShareTeamApp(ShareTeamApp shareTeamApp) throws Exception{
        applicationMapper.insertShareTeamApp(shareTeamApp);
    }

    public List<ShareTeamApp> selectMainCourseList(ShareTeamApp shareTeamApp) throws Exception{
        return applicationMapper.selectMainCourseApp(shareTeamApp);
    }

    public List<ShareTeamApp> selectSubCourseList(ShareTeamApp shareTeamApp) throws Exception{
        return applicationMapper.selectSubCourseApp(shareTeamApp);
    }

    public List<TeamValidApp> getTeamValidApp(Long teacherId) throws Exception{
        return applicationMapper.selectTeamValidApp(teacherId);
    }

    public List<ShareTeamApp> getShareTeamApp(Long teacherId) throws Exception{
        return applicationMapper.selectShareTeamAppBySubTeacherId(teacherId);
    }

    public void updateTeamValidAppStatus(TeamValidApp teamValidApp) throws Exception{
        applicationMapper.updateTeamValidAppStatus(teamValidApp);
    }

    public void updateShareTeamAppStatus(ShareTeamApp shareTeamApp) throws Exception{
        applicationMapper.updateShareTeamAppStatus(shareTeamApp);
    }
}
