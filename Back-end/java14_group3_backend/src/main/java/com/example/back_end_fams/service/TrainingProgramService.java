package com.example.back_end_fams.service;

import com.example.back_end_fams.model.request.TrainingProgramCreateRequest;
import com.example.back_end_fams.model.request.TrainingProgramUpdateRequest;
import com.example.back_end_fams.model.entity.TrainingProgram;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface TrainingProgramService {

    ResponseEntity<?> getTrainingProgramForSuperAmin(String search, Integer trainingProgramCode, String name, String createdBy, Integer duration, String status, String sort);

    ResponseEntity<?> getTrainingProgramForUser(String search, Integer trainingProgramCode, String name, String createdBy, Integer duration, String sort);

    ResponseEntity<?> createTrainingProgram(TrainingProgramCreateRequest request, BindingResult bindingResult);

    ResponseEntity<?> getTrainingProgramDetails(Integer id);

    ResponseEntity<?> getAll();

    ResponseEntity<?> updateTrainingProgramDetails(Integer id, TrainingProgramUpdateRequest request, BindingResult bindingResult);

    ResponseEntity<?> duplicateTrainingProgram(Integer id);

    ResponseEntity<?> activeTrainingProgram(Integer id);
    TrainingProgram getTrainingProgramByCode(int code);

    ResponseEntity<?> deleteTrainingProgram(Integer id);

    ResponseEntity<?> addSyllabusToTrainingProgram(Integer id, Integer syllabusId);


    ResponseEntity<?> deleteSyllabusFromTrainingProgram(Integer id, Integer syllabusId);

    String approveTrainingProgram(int id);
    String rejectTrainingProgram(int id);

    ResponseEntity<?> uploadFileMaterialForTrainingProgram(Integer id, MultipartFile[] file);

    ResponseEntity<?> downloadFileMaterialForTrainingProgram(Integer id, String fileName, HttpServletRequest request);

    ResponseEntity<?> deleteFileMaterialForTrainingProgram(Integer id, String fileName);

    ResponseEntity<?> getFileMaterialForTrainingProgram(Integer id);

    ResponseEntity<?> importTrainingProgram(MultipartFile file) throws IOException;
}
