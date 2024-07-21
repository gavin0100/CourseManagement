package com.example.back_end_fams.repository;

import com.example.back_end_fams.model.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Integer> {
    @Query("SELECT s FROM Syllabus s")
    List<Syllabus> findAllSyl();

    @Query("SELECT s FROM Syllabus s WHERE s.topicName LIKE %:name%")
    List<Syllabus> findSyllabusByTopicName(@Param("name") String name);

    @Query("SELECT s FROM Syllabus s LEFT JOIN FETCH s.topicOutline WHERE s.topicCode=:code")
    Syllabus findSyllabusByTopicCode(@Param("code") int code);

}
