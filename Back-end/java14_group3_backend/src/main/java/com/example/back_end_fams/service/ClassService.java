package com.example.back_end_fams.service;

import com.example.back_end_fams.exception.NotFoundException;
import com.example.back_end_fams.model.entity.*;
import com.example.back_end_fams.model.entity.Class;
import com.example.back_end_fams.model.mapper.ClassMapper;
import com.example.back_end_fams.model.mapper.TrainingProgramMapper;
import com.example.back_end_fams.model.mapper.TrainingProgramMapper2;
import com.example.back_end_fams.model.mapper.TrainingProgramSyllabusMapper;
import com.example.back_end_fams.model.request.ClassRequest;
import com.example.back_end_fams.model.response.ClassResponse;
import com.example.back_end_fams.model.response.SyllabusResponse;
import com.example.back_end_fams.model.response.TrainingProgramResponse;
import com.example.back_end_fams.model.response.TrainingProgramSyllabusResponse;
import com.example.back_end_fams.repository.ClassRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ClassService {
    private int autoId = 0;
    private final String CLASS_NOT_FOUND = "Class not found";

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TrainingProgramMapper2 trainingProgramMapper;

    @Autowired
    private TrainingProgramSyllabusMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SyllabusService syllabusService;

    @Autowired
    private TrainingProgramSyllabusService trainingProgramSyllabusService;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private TrainingProgramService trainingProgramService;

    public List<Class> getAllClass(){
        return classRepository.findAll();
    }

    public ClassResponse getClassById(int id){
        Class classEntity = classRepository.findById(id).orElse(null);
        if(classEntity == null){
            throw new NotFoundException(CLASS_NOT_FOUND);
        }
        ClassResponse classResponse = classMapper.toResponse(classEntity);
        List<SyllabusResponse> syllabusResponseList = new ArrayList<>();
        List<TrainingProgramSyllabus> list = trainingProgramSyllabusService.getTrainingProgramSyllabusByTrainingProgramCode(classResponse.getTrainingProgramCode());
        list.forEach(l->{
            TrainingProgramSyllabusResponse response = mapper.toResponse(l);
            syllabusResponseList.add(response.getSyllabus());
        });
        classResponse.setSyllabusList(syllabusResponseList);
        return classResponse;
    }

    public List<Class> getClassByStatus(String status){
        List<Class> classList = classRepository.findByStatus(status);
        if(classList.isEmpty()){
            throw new NotFoundException(CLASS_NOT_FOUND);
        }
        return classList;
    }

    public Page<Class> getClassByPage(String search, String location, String status, String sort, int page, int size){
        Pageable pageable;
        Page<Class> pageResult;
        if(search.isEmpty()){
            Specification<Class> specification = specification(location, status);
            pageable = sort(sort, page, size);
            pageResult = classRepository.findAll(specification, pageable);
        }else{
            Specification<Class> specification = specification(location, status);
            pageable = sort(sort, page, size);
            pageResult = classRepository.findByClassNameOrClassCode(specification, search, search, pageable);
        }

        if(pageResult.getContent().isEmpty()){
            throw new NotFoundException(CLASS_NOT_FOUND);
        }
        return pageResult;
    }

    private Specification<Class> locationContains(List<String> location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("location")).value(location);
    }

    private Specification<Class> statusContains(List<String> status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("status")).value(status);
    }

    private Specification<Class> specification(String location, String status){
        List<String> locations = Arrays.asList(location.split(","));
        List<String> statuses = Arrays.asList(status.split(","));
        Specification<Class> locationSpec = locationContains(locations);
        Specification<Class> statusSpec = statusContains(statuses);
        Specification<Class> specification = Specification.where(null);

        if(!location.isEmpty()){
            specification = specification.and(locationSpec);
        }
        if(!status.isEmpty()){
            specification = specification.and(statusSpec);
        }
        return specification;
    }

    private Pageable sort(String sort, int page, int size){
        return switch (sort) {
            case "class_name_asc" -> PageRequest.of(page, size, Sort.by("className").ascending());
            case "class_name_desc" -> PageRequest.of(page, size, Sort.by("className").descending());
            case "class_code_asc" -> PageRequest.of(page, size, Sort.by("classCode").ascending());
            case "class_code_desc" -> PageRequest.of(page, size, Sort.by("classCode").descending());
            case "start_date_asc" -> PageRequest.of(page, size, Sort.by("startDate").ascending());
            case "start_date_desc" -> PageRequest.of(page, size, Sort.by("startDate").descending());
            case "created_by_asc" -> PageRequest.of(page, size, Sort.by("createdBy.name").ascending());
            case "created_by_desc" -> PageRequest.of(page, size, Sort.by("createdBy.name").descending());
            case "status_asc" -> PageRequest.of(page, size, Sort.by("status").ascending());
            case "status_desc" -> PageRequest.of(page, size, Sort.by("status").descending());
            case "duration_asc" -> PageRequest.of(page, size, Sort.by("duration").ascending());
            case "duration_desc" -> PageRequest.of(page, size, Sort.by("duration").descending());
            case "location_asc" -> PageRequest.of(page, size, Sort.by("location").ascending());
            case "location_desc" -> PageRequest.of(page, size, Sort.by("location").descending());
            default -> PageRequest.of(page, size, Sort.by("modifiedDate").descending());
        };
    }

    public ClassResponse saveClass(ClassRequest classRequest, HttpServletRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError());
        }
        User user = userService.getCurrentUser(request);
        Class classEntity = classMapper.toEntity(classRequest);
        TrainingProgram trainingProgram = trainingProgramService.getTrainingProgramByCode(classRequest.getTrainingProgramCode());
        classEntity.setCreatedDate(new Date());
        classEntity.setModifiedDate(new Date());
        classEntity.setTrainingProgram(trainingProgram);
        classEntity.setDuration(trainingProgram.getDuration());
        classEntity.setStatus(classRequest.getStatus());
        classEntity.setTrainingProgramCode(classRequest.getTrainingProgramCode());
        classEntity.setClassCode(locationCode(classRequest.getLocation())
                .concat(currentYear(classRequest.getStartDate()))
                .concat(increaseId()));
        classEntity.setCreatedBy(user);
        classEntity.setModifiedBy(user);
        classRepository.saveAndFlush(classEntity);
        return classMapper.toResponse(classEntity);
    }

    public ClassResponse updateClass(int id, ClassRequest classRequest, HttpServletRequest request, BindingResult bindingResult){
        Class classEntity = classRepository.findById(id).orElse(null);
        if(classEntity == null){
            throw new NotFoundException(CLASS_NOT_FOUND);
        }
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError());
        }
        User user = userService.getCurrentUser(request);
        TrainingProgram trainingProgram = trainingProgramService.getTrainingProgramByCode(classRequest.getTrainingProgramCode());
        classEntity.setClassName(classRequest.getClassName());
        classEntity.setStartDate(classRequest.getStartDate());
        classEntity.setEndDate(classRequest.getEndDate());
        classEntity.setLocation(classRequest.getLocation());
        classEntity.setTrainingProgramCode(classRequest.getTrainingProgramCode());
        classEntity.setTrainingProgram(trainingProgram);
        classEntity.setDuration(trainingProgram.getDuration());
        classEntity.setStatus(classRequest.getStatus());
        classEntity.setModifiedDate(new Date());
        classEntity.setModifiedBy(user);
        classRepository.saveAndFlush(classEntity);
        ClassResponse classResponse = classMapper.toResponse(classEntity);
        List<TrainingProgramSyllabus> list = trainingProgramSyllabusService.getTrainingProgramSyllabusByTrainingProgramCode(classResponse.getTrainingProgramCode());
//        System.out.println(classResponse.getTrainingProgramCode());
        List<SyllabusResponse> syllabus = new ArrayList<>();
        list.forEach(l->{
            TrainingProgramSyllabusResponse response = mapper.toResponse(l);
            syllabus.add(response.getSyllabus());
        });
        classResponse.setSyllabusList(syllabus);
        return classResponse;
    }

    public void deleteClass(int id){
        Class classEntity = classRepository.findById(id).orElse(null);
        if(classEntity == null){
            throw new NotFoundException(CLASS_NOT_FOUND);
        }
        classRepository.deleteById(id);
    }

    private String locationCode(String location){
        String[] locationSplit = location.split(" ");
        String locationCode = "";
        for (String s : locationSplit) {
            locationCode += s.charAt(0);
        }
        return locationCode;
    }

    private String currentYear(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        String[] dateSplit = format.split("-");
        return dateSplit[0].substring(2,4);
    }

    private String increaseId(){
        if(this.autoId > 99){
            this.autoId = 0;
        }
        this.autoId = this.autoId + 1;
        return autoId < 10 ? "0".concat(String.valueOf(autoId)) : String.valueOf(autoId);
    }

    public ResponseEntity<?> duplicateClass(int id) {
        var class_room = classRepository.findById(id);
        if (class_room.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found class with ID: " + id);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Class newClass = new Class();
        newClass = Class.builder()
                .trainingProgramCode(class_room.get().getTrainingProgramCode())
                .className(class_room.get().getClassName())
                .classCode(class_room.get().getClassCode())
                .duration(class_room.get().getDuration())
                .status(class_room.get().getStatus())
                .location(class_room.get().getLocation())
                .startDate(class_room.get().getStartDate())
                .endDate(class_room.get().getEndDate())
                .createdBy(class_room.get().getCreatedBy())
                .createdDate(new Date())
                .modifiedBy(currentUser)
                .modifiedDate(new Date())
                .trainingProgram(class_room.get().getTrainingProgram())
                .build();
        newClass.setStatus("Scheduled");
        classRepository.save(newClass);
        return ResponseEntity.ok("Duplicated class successfully!");
    }
    public Class findById(int id){
        return classRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Class not found"));
    }
}
