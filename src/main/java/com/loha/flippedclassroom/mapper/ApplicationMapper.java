package com.loha.flippedclassroom.mapper;

import com.loha.flippedclassroom.entity.ShareTeamApp;
import com.loha.flippedclassroom.entity.TeamValidApp;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 与请求相关的mapper
 *
 * @author zhoujian
 * @date 2018/12/28
 */
@Repository
public interface ApplicationMapper {

    /**
     * 添加一条组队申请
     * @param teamValidApp
     * @throws Exception
     */
    void insertTeamValidApp(TeamValidApp teamValidApp) throws Exception;

    /**
     * 添加一条共享小组的申请
     * @param shareTeamApp
     * @throws Exception
     */
    void insertShareTeamApp(ShareTeamApp shareTeamApp) throws Exception;

    /**
     * 选择某一门课程的主课程列表
     * @param shareTeamApp
     * @return List
     * @throws Exception
     */
    List<ShareTeamApp> selectMainCourseApp(ShareTeamApp shareTeamApp) throws Exception;

    /**
     * 选择某一门课程的从课程列表
     * @param shareTeamApp
     * @return List
     * @throws Exception
     */
    List<ShareTeamApp> selectSubCourseApp(ShareTeamApp shareTeamApp) throws Exception;


    /**
     * 小组合法性申请
     * @param teacherId
     * @return List
     * @throws Exception
     */
    List<TeamValidApp> selectTeamValidApp(Long teacherId) throws Exception;

    /**
     * 申请共享课程
     * @param teacherId
     * @return List
     * @throws Exception
     */
    List<ShareTeamApp> selectShareTeamAppBySubTeacherId(Long teacherId) throws Exception;

    /**
     * 修改小组合法申请的请求状态
     * @param teamValidApp object
     * @throws Exception
     */
    void updateTeamValidAppStatus(TeamValidApp teamValidApp) throws Exception;

    /**
     * 修改共享组队申请的请求状态
     * @param shareTeamApp object
     * @throws Exception
     */
    void updateShareTeamAppStatus(ShareTeamApp shareTeamApp) throws Exception;

}
