package com.loha.flippedclassroom.mapper;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 *
 *班级小组mapper
 * @author zhoujian
 * @date 2018/12/29
 */
@Repository
public interface KlassTeamMapper {

    /**
     * 删除班级下的team
     * @param map
     * @throws Exception
     */
    void deleteKlassTeamByKey(Map<String,Long> map) throws Exception;

    /**
     * 查找该记录是否存在
     * @param map
     * @return Integer
     * @throws Exception
     */
    Integer selectKlassTeamByKey (Map<String,Long> map) throws Exception;

    /**
     * 在班级下插入一个team
     * @param map 包含"klassId"和"teamId"
     * @throws Exception
     */
    void insertKlassTeam(Map<String,Long> map) throws Exception;

    /**
     * 删除小组
     * @param klassId
     * @throws Exception
     */
    void deleteTeamByKlassId(Long klassId) throws Exception;
}
