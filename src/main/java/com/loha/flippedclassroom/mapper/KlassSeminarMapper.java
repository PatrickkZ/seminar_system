package com.loha.flippedclassroom.mapper;

import com.loha.flippedclassroom.entity.KlassSeminar;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 与班级讨论课相关的mapper
 *
 * @author zhoujian
 * @date 2018/12/17
 */
@Repository
public interface KlassSeminarMapper {
    /**
     * fetch a KlassSeminar
     * @param klassSeminarId id
     * @return KlassSeminar
     * @throws Exception
     */
    KlassSeminar selectKlassSeminarById(Long klassSeminarId) throws Exception;

    /**
     * fetch a KlassSeminar
     * @param klassSeminar Object
     * @return KlassSeminar
     * @throws Exception
     */
    KlassSeminar selectKlassSeminarByKlassSeminarId(KlassSeminar klassSeminar) throws Exception;

    /**
     * 向klass_seminar表添加数据，在创建讨论课之后添加相应记录
     * @param klassSeminar Object
     * @throws Exception
     */
    void insertKlassSeminar(KlassSeminar klassSeminar) throws Exception;

    /**
     * 修改讨论课状态
     * @param klassSeminar Object
     * @throws Exception
     */
    void updateKlassSeminarStatus(KlassSeminar klassSeminar) throws Exception;

    /**
     * 某个小组的班级讨论课id
     * @param klassId
     * @param  seminarIds
     * @return List
     * @throws Exception
     */
    List<Long> selectOneTeamKlassSeminars(@Param("klassId") Long klassId,@Param("seminarIds") List<Long> seminarIds);

    /**
     * 正在进行的讨论课
     * @param klassIds
     * @param  seminarIds
     * @return KlassSeminar
     * @throws Exception
     */
    KlassSeminar selectUnderwaySeminar(@Param("klassIds") List<Long> klassIds,@Param("seminarIds") List<Long> seminarIds);
}
