package com.loha.flippedclassroom.dao;


import com.loha.flippedclassroom.entity.Attendance;
import com.loha.flippedclassroom.entity.KlassSeminar;
import com.loha.flippedclassroom.entity.Seminar;
import com.loha.flippedclassroom.mapper.AttendanceMapper;
import com.loha.flippedclassroom.mapper.KlassSeminarMapper;
import com.loha.flippedclassroom.mapper.SeminarMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 与讨论课相关的dao
 *
 * @author zhoujian
 * @date 2018/12/18
 */
@Repository
public class SeminarDao {

    private final KlassSeminarMapper klassSeminarMapper;
    private final SeminarMapper seminarMapper;

    SeminarDao(KlassSeminarMapper klassSeminarMapper,SeminarMapper seminarMapper){
        this.klassSeminarMapper=klassSeminarMapper;
        this.seminarMapper=seminarMapper;
    }

    /**
     * 获取KlassSeminar对象，用于后续获取KlassSeminar的id
     */
    public KlassSeminar getKlassSeminar(Long klassId,Long seminarId) throws Exception {
        KlassSeminar klassSeminar=new KlassSeminar();
        klassSeminar.setKlassId(klassId);
        klassSeminar.setSeminarId(seminarId);
        return klassSeminarMapper.selectKlassSeminarByKlassSeminarId(klassSeminar);
    }

    /**
     * 根据Id获取当前的讨论课
     */
    public Seminar getSeminarById(Long seminarId) throws Exception{
        return seminarMapper.selectSeminarById(seminarId);
    }

    /**
     * 新建讨论课
     */
    public Long insertSeminar(Seminar seminar) throws Exception{
        seminarMapper.insertSeminar(seminar);
        return seminar.getId();
    }

    /**
     * 新建讨论课之后向klass_seminar表中插入相应记录
     */
    public Long insertKlassSeminar(KlassSeminar klassSeminar) throws Exception{
        klassSeminarMapper.insertKlassSeminar(klassSeminar);
        return klassSeminar.getId();
    }

    /**
     * 修改讨论课状态信息，同时也可修改报告截至日期
     */
    public void updateKlassSeminarStatus(KlassSeminar klassSeminar) throws Exception{
        klassSeminarMapper.updateKlassSeminarStatus(klassSeminar);
    }

    public List<Seminar> selectSeminarByRoundId(Long roundId) throws Exception{
        return seminarMapper.selectSeminarByRoundId(roundId);
    }

    public List<Long> selectSeminarIdByRoundId(Long roundId) throws Exception{
        return seminarMapper.selectSeminarIdByRoundId(roundId);
    }

    public List<Long> selectOneTeamKlassSeminars(Long klassId,List<Long> seminarIds){
        return klassSeminarMapper.selectOneTeamKlassSeminars(klassId, seminarIds);
    }

    public List<Long> selectSeminarIdByCourseId(Long courseId) throws Exception{
        return seminarMapper.selectSeminarIdByCourseId(courseId);
    }

    public KlassSeminar getUnderwaySeminar(List<Long> klassIds,List<Long> seminarIds) throws Exception{
        return klassSeminarMapper.selectUnderwaySeminar(klassIds, seminarIds);
    }
}
