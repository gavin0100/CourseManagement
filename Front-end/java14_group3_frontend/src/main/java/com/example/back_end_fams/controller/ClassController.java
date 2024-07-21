package com.example.back_end_fams.controller;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.UI.ClassDateRequestUI;
import com.example.back_end_fams.model.UI.ClassRequestUI;
import com.example.back_end_fams.model.request.ClassRequest;
import com.example.back_end_fams.model.response.ClassDateResponse;
import com.example.back_end_fams.model.response.ClassResponse;
import com.example.back_end_fams.model.response.TrainingProgramResponse;
import com.example.back_end_fams.model.response.UserResponse;
import com.example.back_end_fams.service.ClassService;
import com.example.back_end_fams.service.InputService;
import com.example.back_end_fams.service.TrainginProgramService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private ClassService classService;

    @Autowired
    private InputService inputService;

    @Autowired
    private TrainginProgramService trainginProgramService;

    private String message = null;
    private String errorMessage = null;
    @GetMapping("/get_list")
    public String getViewUser(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        List<ClassResponse> classResponses = classService.findAll();
        // Custom comparator for sorting in descending order based on modifiedDate
        Comparator<ClassResponse> comparator = Comparator.comparing(ClassResponse::getModifiedDate, Comparator.nullsLast(Comparator.reverseOrder()));

        // Sort the list using the comparator
        Collections.sort(classResponses, comparator);
        List<TrainingProgramResponse> trainingProgramResponses = trainginProgramService.findAll();
        model.addAttribute("classResponses", classResponses);
        model.addAttribute("trainingProgramResponses", trainingProgramResponses);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui_class";
    }

    @PostMapping("create")
    public String create(
                         @ModelAttribute ClassRequestUI classRequestUI,
                         Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        if (classRequestUI.getClassName() == null || classRequestUI.getStatus() == null ||
                classRequestUI.getLocation() == null || classRequestUI.getStartDate() == null ||
                classRequestUI.getEndDate() == null || classRequestUI.getTrainingProgramCode() == -1 ||
                !inputService.isValidInput(classRequestUI.getClassName()) ||
                !inputService.isValidInput(classRequestUI.getStatus()) ||
                !inputService.isValidInput(classRequestUI.getLocation()) ||
                !classRequestUI.getStartDate().toInstant().isBefore(classRequestUI.getEndDate().toInstant())){
            errorMessage = "Dữ liệu đầu vào không hợp lệ! Không được để trống input, chỉ được chứa UTF-8, \"@\", \"(\", \")\", \",\", \".\", \"!\" và khoảng trắng, start day sau end day.";
            return "redirect:/class/get_list";
        }
        String className = classRequestUI.getClassName();
        String status = classRequestUI.getStatus();
        String location = classRequestUI.getLocation();
        Date startDate = classRequestUI.getStartDate();
        Date endDate = classRequestUI.getEndDate();
        int trainingProgramCode = classRequestUI.getTrainingProgramCode();
        boolean rusult = classService.createClass(className, status, location, startDate, endDate, trainingProgramCode);
        if (rusult == true ){
            message = "Thêm thành công";
        } else {
            errorMessage = "Thêm không thành công";
        }

        return "redirect:/class/get_list";
    }

    @PostMapping("/edit")
    public String edit(
            @ModelAttribute ClassRequestUI classRequestUI,
            Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        if (classRequestUI.getClassName() == null || classRequestUI.getStatus() == null ||
                classRequestUI.getLocation() == null || classRequestUI.getStartDate() == null ||
                classRequestUI.getEndDate() == null || classRequestUI.getTrainingProgramCode() == -1 ||
                !inputService.isValidInput(classRequestUI.getClassName()) ||
                !inputService.isValidInput(classRequestUI.getStatus()) ||
                !inputService.isValidInput(classRequestUI.getLocation()) ||
                !classRequestUI.getStartDate().toInstant().isBefore(classRequestUI.getEndDate().toInstant())){
            errorMessage = "Dữ liệu đầu vào không hợp lệ! Không được để trống input, chỉ được chứa UTF-8, \"@\", \"(\", \")\", \",\", \".\", \"!\" và khoảng trắng, start day sau end day.";
            return "redirect:/class/get_list";
        }
        int classId = classRequestUI.getClassId();
        String className = classRequestUI.getClassName();
        String status = classRequestUI.getStatus();
        String location = classRequestUI.getLocation();
        Date startDate = classRequestUI.getStartDate();
        Date endDate = classRequestUI.getEndDate();
        int trainingProgramCode = classRequestUI.getTrainingProgramCode();
        boolean rusult = classService.updateClass(className, location, status, startDate, endDate, trainingProgramCode, classId);
        if (rusult == true ){
            message = "Cập nhật thành công";
        } else {
            errorMessage = "Cập nhật không thành công";
        }
        return "redirect:/class/get_list";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute ClassRequestUI classRequestUI, Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        int id = classRequestUI.getClassId();
        boolean rusult = classService.deleteClass(id);
        if (rusult == true ){
            message = "Xóa thành công";
        } else {
            errorMessage = "Xóa không thành công";
        }
        return "redirect:/class/get_list";

    }

    @PostMapping("/duplicate")
    public String duplicate(@ModelAttribute ClassRequestUI classRequestUI, Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        int id = classRequestUI.getClassId();
        boolean rusult = classService.duplicateClass(id);
        if (rusult == true ){
            message = "Nhân bản thành công";
        } else {
            errorMessage = "Nhân bản không thành công";
        }
        return "redirect:/class/get_list";

    }

    @PostMapping("/detail")
    public String detail(
                         @ModelAttribute ClassRequestUI classRequestUI,
                         Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        int id = classRequestUI.getClassId();
        List<ClassDateResponse> classDateResponses = classService.findClassDateByClassId(id);
        String partOfDate;
        if (classDateResponses == null || classDateResponses.size() == 0){
            partOfDate = "";
        }
        else{
            partOfDate = classDateResponses.get(0).getPartOfDate();
        }

        List<Date> dates = new ArrayList<>();
        classDateResponses.forEach(classDateResponse -> dates.add(classDateResponse.getDay()));
        List<String> dateStrings = dates.stream()
                .map(date -> new SimpleDateFormat("yyyy-MM-dd").format(date))
                .collect(Collectors.toList());
        dateStrings = dateStrings.stream()
                .map(dateString -> "\"" + dateString + "\"")
                .collect(Collectors.toList());

        List<ClassDateResponse> classDateResponses2 = classService.findClassDateByClassId(id);
        List<Date> date2s = new ArrayList<>();
        classDateResponses2.forEach(classDateResponse -> date2s.add(classDateResponse.getDay()));
        List<String> dateStrings2 = date2s.stream()
                .map(date -> new SimpleDateFormat("yyyy-MM-dd").format(date))
                .collect(Collectors.toList());
        dateStrings2 = dateStrings2.stream()
                .map(dateString -> dateString)
                .collect(Collectors.toList());



        ClassResponse classResponse = classService.findById(id);
        model.addAttribute("classResponse", classResponse);
        model.addAttribute("dates", dateStrings);
        model.addAttribute("date2s", dateStrings2);
        model.addAttribute("partOfDate", partOfDate);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui_class_detail";

    }

    @PostMapping("/edit_class_date")
    public String editClassDate(
                         @ModelAttribute ClassDateRequestUI classDateRequestUI,
                         Model model) throws JsonProcessingException {
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        String listOfDate = classDateRequestUI.getListOfDate();
        String partOfDate = classDateRequestUI.getPartOfDate();
        int classId = classDateRequestUI.getClassId();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Date> dateList = new ArrayList<>();
        try {
            List<String> dateStrings = objectMapper.readValue(listOfDate, List.class);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


            for (String dateString : dateStrings) {
                if(dateString == null){
                    continue;
                }
                Date date = dateFormat.parse(dateString);
                dateList.add(date);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean addClassDateResponse = classService.addClassDateByClassId(dateList, partOfDate, classId );
        if (addClassDateResponse == true ){
            message = "Cập nhật thành công";
        } else {
            errorMessage = "Cập nhật không thành công";
        }
        List<ClassDateResponse> classDateResponses = classService.findClassDateByClassId(classId);
        String partOfDate2;
        if (classDateResponses == null || classDateResponses.size() == 0){
            partOfDate2 = "";
        }
        else{
            partOfDate2 = classDateResponses.get(0).getPartOfDate();
        }

        List<Date> dates = new ArrayList<>();
        classDateResponses.forEach(classDateResponse -> dates.add(classDateResponse.getDay()));
        List<String> dateStrings = dates.stream()
                .map(date -> new SimpleDateFormat("yyyy-MM-dd").format(date))
                .collect(Collectors.toList());
        dateStrings = dateStrings.stream()
                .map(dateString -> "\"" + dateString + "\"")
                .collect(Collectors.toList());

        List<ClassDateResponse> classDateResponses2 = classService.findClassDateByClassId(classId);
        List<Date> date2s = new ArrayList<>();
        classDateResponses2.forEach(classDateResponse -> date2s.add(classDateResponse.getDay()));
        List<String> dateStrings2 = date2s.stream()
                .map(date -> new SimpleDateFormat("yyyy-MM-dd").format(date))
                .collect(Collectors.toList());
        dateStrings2 = dateStrings2.stream()
                .map(dateString -> dateString)
                .collect(Collectors.toList());



        ClassResponse classResponse = classService.findById(classId);
        model.addAttribute("classResponse", classResponse);
        model.addAttribute("dates", dateStrings);
        model.addAttribute("date2s", dateStrings2);
        model.addAttribute("partOfDate", partOfDate2);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;

        return "ui_class_detail";
    }



}
