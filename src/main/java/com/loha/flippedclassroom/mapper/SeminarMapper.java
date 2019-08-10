package com.loha.flippedclassroom.mapper;

import com.loha.flippedclassroom.entity.Seminar;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 与讨论课相关的mapper
 *
 * @author zhoujian
 * @date 2018/12/16
 */
@Repository
public interface SeminarMapper {

    /**
     * fetch a seminar
     * @param seminarId seminar's Id
     * @return a seminar
     * @throws Exception
     */
    Seminar selectSeminarById(Long seminarId) throws Exception;

    /**
     * fetch a seminar
     * @param roundId round's Id
     * @return a seminar
     * @throws Exception
     */
    List<Seminar> selectSeminarByRoundId(Long roundId) throws Exception;

    /**
     * 用roundid选所有讨论课id
     * @param roundId round's Id
     * @return list
     * @throws Exception
     */
    List<Long> selectSeminarIdByRoundId(Long roundId) throws Exception;

    /**
     * 新建讨论课
     * @param seminar Object
     * @throws Exception
     */
    void insertSeminar(Seminar seminar) throws Exception;

    /**
     * 根据课程id找到所有讨论课id
     * @param courseId
     * @return List
     * @throws Exception
     */
    List<Long> selectSeminarIdByCourseId(Long courseId) throws Exception;
}
