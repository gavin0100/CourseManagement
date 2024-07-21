package com.example.back_end_fams.controller;

import com.example.back_end_fams.model.mapper.ClassDateMapper;
import com.example.back_end_fams.model.mapper.ClassMapper;
import com.example.back_end_fams.model.request.ClassRequest;
import com.example.back_end_fams.model.response.ClassResponse;
import com.example.back_end_fams.service.ClassDateService;
import com.example.back_end_fams.service.ClassService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.back_end_fams.model.entity.Class;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @Autowired
    private ClassDateService classDateService;
    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private ClassDateMapper classDateMapper;

    @GetMapping
    public ResponseEntity<?> getAllClass(){
        List<Class> classList = classService.getAllClass();
        return ResponseEntity.ok(classMapper.toClassDTO(classList));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getClassByPage(
            @RequestParam (defaultValue = "") String search,
            @RequestParam (defaultValue = "") String location,
            @RequestParam (defaultValue = "") String status,
            @RequestParam (defaultValue = "") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size){
        return ResponseEntity.ok(classService.getClassByPage(search, location, status, sort, page, size));
    }


    @PostMapping("/create")
    public ResponseEntity<?> createClass(@RequestBody @Valid ClassRequest classRequest,
                                         HttpServletRequest request,
                                         BindingResult bindingResult){
        ClassResponse classResponse = classService.saveClass(classRequest, request, bindingResult);
        return ResponseEntity.ok(classResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClass(@PathVariable int id,
                                         @RequestBody @Valid ClassRequest classRequest,
                                         HttpServletRequest request,
                                         BindingResult bindingResult){
        ClassResponse classResponse = classService.updateClass(id, classRequest, request, bindingResult);
        return ResponseEntity.ok(classResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable int id){
        classService.deleteClass(id);
        return ResponseEntity.ok("Delete success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClassById(@PathVariable int id){
        return ResponseEntity.ok(classService.getClassById(id));
    }

    @PostMapping("/duplicate/{id}")
    public ResponseEntity<?> duplicateClass(@PathVariable("id") int id){
        return classService.duplicateClass(id);
    }

    @GetMapping("/get_class_date/{id}")
    public ResponseEntity<?> getClassDateByClassId(@PathVariable("id") int classId){
        return ResponseEntity.ok(classDateMapper.toClassDateDTO( classDateService.findByClassId(classId)));
    }

    @PostMapping("/add_class_date")
    public ResponseEntity<?> addClassDateByClassId(@RequestBody @DateTimeFormat(pattern = "yyyy-MM-dd") List<Date> dateList,
                                                   @RequestParam("partOfDate") String partOfDate,
                                                   @RequestParam("classId") int classId){
        return ResponseEntity.ok(classDateService.addListClassDate(dateList, partOfDate, classId));
    }

    @GetMapping("/get_all_class_date")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(classDateMapper.toClassDateDTO(classDateService.findAll()));
    }
}
