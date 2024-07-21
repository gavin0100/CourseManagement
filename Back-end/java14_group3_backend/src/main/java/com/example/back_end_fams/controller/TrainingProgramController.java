package com.example.back_end_fams.controller;

import com.example.back_end_fams.model.request.TrainingProgramCreateRequest;
import com.example.back_end_fams.model.request.TrainingProgramUpdateRequest;
import com.example.back_end_fams.service.TrainingProgramService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/training_program")
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTrainingProgram(){
        return trainingProgramService.getAll();
    }
    @GetMapping("/super_admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getTrainingProgramOnlyForSuperAdmin(@RequestParam(value = "search", required = false,  defaultValue = "") String search,
                                                                 @RequestParam(value = "trainingProgramCode", required = false, defaultValue = "0") Integer trainingProgramCode,
                                                                 @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                                 @RequestParam(value = "createdBy", required = false, defaultValue = "") String createdBy,
                                                                 @RequestParam(value = "duration", required = false, defaultValue = "0") Integer duration,
                                                                 @RequestParam(value = "status", required = false, defaultValue = "") String status,
                                                                 @RequestParam(value = "sort", required = false, defaultValue = "") String sort
                                                                 ){
        return trainingProgramService.getTrainingProgramForSuperAmin(search, trainingProgramCode, name, createdBy, duration, status, sort);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('CLASS_ADMIN', 'TRAINER', 'SUPER_ADMIN')")
    public ResponseEntity<?> getTrainingProgramForAllUser(@RequestParam(value = "search", required = false,  defaultValue = "") String search,
                                                          @RequestParam(value = "trainingProgramCode", required = false, defaultValue = "0") Integer trainingProgramCode,
                                                          @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                          @RequestParam(value = "createdBy", required = false, defaultValue = "") String createdBy,
                                                          @RequestParam(value = "duration", required = false, defaultValue = "0") Integer duration,
                                                          @RequestParam(value = "sort", required = false, defaultValue = "") String sort
    ){
        return trainingProgramService.getTrainingProgramForUser(search, trainingProgramCode, name, createdBy, duration, sort);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CLASS_ADMIN')")
    public ResponseEntity<?> createTrainingProgram(@Valid @RequestBody TrainingProgramCreateRequest request, BindingResult bindingResult){
        return trainingProgramService.createTrainingProgram(request, bindingResult);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CLASS_ADMIN', 'TRAINER')")
    public ResponseEntity<?> getTrainingProgramDetails(@PathVariable("id") Integer id){
        return trainingProgramService.getTrainingProgramDetails(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateTrainingProgramDetails(@PathVariable("id") Integer id, @Valid @RequestBody TrainingProgramUpdateRequest request, BindingResult bindingResult){
        return trainingProgramService.updateTrainingProgramDetails(id, request, bindingResult);
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> duplicateTrainingProgram(@PathVariable("id") int id){
        return trainingProgramService.duplicateTrainingProgram(id);
    }
    @PostMapping("/{id}/active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> activeTrainingProgram(@PathVariable("id") Integer id){
        return trainingProgramService.activeTrainingProgram(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteTrainingProgram(@PathVariable("id") Integer id){
        return trainingProgramService.deleteTrainingProgram(id);
    }

    @PostMapping("/{id}/syllabus/{syllabusId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addSyllabusToTrainingProgram(@PathVariable("id") Integer id,
                                                          @PathVariable("syllabusId") Integer syllabusId){
        return trainingProgramService.addSyllabusToTrainingProgram(id, syllabusId);
    }
    @DeleteMapping("/{id}/syllabus/{syllabusId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteSyllabusFromTrainingProgram(@PathVariable("id") Integer id,
                                                               @PathVariable("syllabusId") Integer syllabusId){
        return trainingProgramService.deleteSyllabusFromTrainingProgram(id, syllabusId);
    }
    @PostMapping("{id}/external/upload")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> uploadFileMaterialForTrainingProgram(@PathVariable("id") Integer id, @RequestParam("file")MultipartFile[] file){
        return trainingProgramService.uploadFileMaterialForTrainingProgram(id, file);
    }
    @GetMapping("/external/download/{id}/{file_name}")
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> downloadFileMaterialForTrainingProgram(@PathVariable("id") Integer id, @PathVariable("file_name") String fileName, HttpServletRequest request){
        return trainingProgramService.downloadFileMaterialForTrainingProgram(id, fileName ,request);
    }
    @DeleteMapping("{id}/external/delete/{file_name}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteFileMaterialForTrainingProgram(@PathVariable("id") Integer id, @PathVariable("file_name") String fileName){
        return trainingProgramService.deleteFileMaterialForTrainingProgram(id, fileName);
    }
    @GetMapping("{id}/external/view")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getFileMaterialForTrainingProgram(@PathVariable("id") Integer id){
        return trainingProgramService.getFileMaterialForTrainingProgram(id);
    }


    @PostMapping("/addSyllabus/{trainingProgramId}/{syllabusId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addSyllabus(@PathVariable("trainingProgramId") int trainingProgramId,
                                         @PathVariable("syllabusId") int syllabusId){
        return trainingProgramService.addSyllabusToTrainingProgram(syllabusId,trainingProgramId);
    }

    //Import nếu đã có trainingProgramCode thì cập nhật các thông tin của training Program thôi
    @PostMapping("/import")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CLASS_ADMIN')")
    public ResponseEntity<?> importTrainingProgram(@RequestParam("file") MultipartFile file) throws IOException {
        return trainingProgramService.importTrainingProgram(file);
    }

    @PutMapping("/approve/{trainingId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CLASS_ADMIN')")
    public String approve(@PathVariable int trainingId){
        return trainingProgramService.approveTrainingProgram(trainingId);
    }
    @PutMapping("/reject/{trainingId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CLASS_ADMIN')")
    public String reject(@PathVariable int trainingId){
        return trainingProgramService.rejectTrainingProgram(trainingId);
    }
}
