package com.example.back_end_fams.controller;

import com.example.back_end_fams.model.entity.LearningObjective;
import com.example.back_end_fams.model.entity.Syllabus;
import com.example.back_end_fams.model.mapper.LearningObjectiveMapper;
import com.example.back_end_fams.service.LearningObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/learningObjective")
public class LearningObjectiveController {
    @Autowired
    LearningObjectiveService learningObjectiveService;
    @Autowired
    LearningObjectiveMapper learningObjectiveMapper;
    @GetMapping("/list")
    public ResponseEntity<?> findAll(){
        List<LearningObjective> learningObjectives = learningObjectiveService.getAllObjective();
        return ResponseEntity.ok(learningObjectiveMapper.toLearningObjectiveListDTO(learningObjectives));
    }
}
