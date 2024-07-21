package com.example.back_end_fams.controller;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.UI.SyllabusRequestUI;
import com.example.back_end_fams.model.request.*;
import com.example.back_end_fams.model.response.*;
import com.example.back_end_fams.service.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@Slf4j
@RequestMapping("/syllabus")
public class SyllabusController {
    private final Logger logger = LoggerFactory.getLogger(SyllabusController.class);

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private SyllabusService syllabusService;

    @Autowired
    private FileMaterialService fileMaterialService;
    @Autowired
    private InputService inputService;
    @Autowired
    private TrainingUnitService trainingUnitService;

    @Autowired
    private TrainingContentService trainingContentService;
    
    @Autowired
    private LearningObjectiveService learningObjectiveService;

    private String message = null;
    private String errorMessage = null;

    private MaterialTrainingContentRequestIUI materialTrainingContentRequestIUIGlobal;

    // Global Variable to save Data
    private SyllabusRequest syllabusRequest;
    private List<TrainingUnitRequest> trainingUnitRequestList = new ArrayList<>();
    private List<TrainingContentRequest> trainingContentRequestList = new ArrayList<>();

    @GetMapping("/list")
    public String getViewSyllabus(Model model) {
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
        List<SyllabusResponse> syllabusResponse = syllabusService.findAllUi();
        Comparator<SyllabusResponse> comparator = Comparator.comparing(SyllabusResponse::getModifiedDate, Comparator.nullsLast(Comparator.reverseOrder()));
        Collections.sort(syllabusResponse, comparator);
        model.addAttribute("syllabus", syllabusResponse);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;

        return "ui_syllabus";
    }

    @GetMapping("/detail/{id}")
    public String getDetailSyllabus(@PathVariable("id") int syllabusId,
                                    Model model) {
//        if (jwtFilter.getAccessToken() == null){
//            if(message != null){
//                model.addAttribute("message", message);
//            }
//            if(errorMessage != null){
//                model.addAttribute("errorMessage", errorMessage);
//            }
//            message = null;
//            errorMessage = null;
//            return "redirect:/";
//        }
//        List<SyllabusResponse> syllabusResponse = syllabusService.findAllUi();
//        model.addAttribute("syllabus", syllabusResponse);
//        model.addAttribute("message", message);
//        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;

        return "ui_syllabus-detail";
    }

    @GetMapping("/detail2/{id}")
    public String getDetailSyllabus2(@PathVariable("id") int syllabusId,
                                    Model model) {
        if (jwtFilter.getAccessToken() == null) {
            if (message != null) {
                model.addAttribute("message", message);
            }
            if (errorMessage != null) {
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        ResponseEntity<SyllabusResponse> syllabusResponse = syllabusService.detail(syllabusId);
        if (syllabusResponse.getStatusCode() != HttpStatus.OK) {
        } else {
            SyllabusResponse syllabusResponse1 = syllabusResponse.getBody();
            List<TrainingUnitResponse> trainingUnitResponses = syllabusResponse1.getTopicOutline();

            Set<String> uniqueDays = new HashSet<>();
            List<TrainingUnitResponse> filteredUnits = new ArrayList<>();

            for (TrainingUnitResponse unit : trainingUnitResponses) {
                String day = unit.getDay();
                if (!uniqueDays.contains(day)) {
                    uniqueDays.add(day);
                    filteredUnits.add(unit);
                }
            }

            model.addAttribute("syllabus", syllabusResponse1);
            model.addAttribute("days", uniqueDays);
            model.addAttribute("units", trainingUnitResponses);
        }

        return "ui_syllabus-detail";
    }

    @GetMapping("/create")
    public String getViewCreateSyllabus(Model model) {
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
        List<LearningObjectiveResponse> learningObjectiveResponseList = syllabusService.findAllLearningObjective();
        model.addAttribute("learningObjectiveResponseList", learningObjectiveResponseList);
        model.addAttribute("syllabus", new SyllabusRequest());

        return "ui_syllabus-create";
    }

//    Chờ xong giao diện hết rồi dùng đến <3
@PostMapping("/create")
public String save(Model model,
                   @ModelAttribute("syllabus") @Valid SyllabusRequest syllabusModel,
                   BindingResult bindingResult) {
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
    try{
        if (!inputService.isValidInput(syllabusModel.getTopicName()) ||
                !inputService.isValidInput(syllabusModel.getTechnicalGroup()) ||
                !inputService.isValidInput(syllabusModel.getCourseObjective()) ||
                !inputService.isInteger(syllabusModel.getTrainingAudience()) ||
                !inputService.isValidInput(syllabusModel.getTrainingPrinciples()) ||
                syllabusModel.getTopicName() == null ||
                syllabusModel.getTechnicalGroup() == null ||
                syllabusModel.getCourseObjective() == null ||
                syllabusModel.getTrainingAudience() == -1 ||
                syllabusModel.getTrainingPrinciples() == null
        ){
            errorMessage = "Dữ liệu đầu vào không được để trống, chỉ chứa những ký tự UTF-8, chữ hoa, chữ thường, số, dấu @, dấu ngoặc (), dấu chấm \".\", dấu chấm than \"!\"";
            return "redirect:/syllabus/list";
        }
    } catch (Exception ex){
        errorMessage = "Dữ liệu đầu vào không được để trống, chỉ chứa những ký tự UTF-8, chữ hoa, chữ thường, số, dấu @, dấu ngoặc (), dấu chấm \".\", dấu chấm than \"!\"";
        return "redirect:/syllabus/list";
    }

    try{
        trainingUnitRequestList.forEach(trainingUnitRequest -> {
            if (!inputService.isValidInput(trainingUnitRequest.getUnitName()) ||
                    !inputService.isValidInput(trainingUnitRequest.getDay()) ||
                    !inputService.isValidInput(trainingUnitRequest.getUnit()) ||
                    trainingUnitRequest.getUnitName() == null ||
                    trainingUnitRequest.getDay() == null ||
                    trainingUnitRequest.getUnit() == null
            ){
                errorMessage = "Dữ liệu đầu vào không được để trống, chỉ chứa những ký tự UTF-8, chữ hoa, chữ thường, số, dấu @, dấu ngoặc (), dấu chấm \".\", dấu chấm than \"!\"";
            }
        });
        trainingContentRequestList.forEach(trainingContentRequest -> {
            if (!inputService.isValidInput(trainingContentRequest.getContent()) ||
                    !inputService.isValidInput(trainingContentRequest.getDeliveryType()) ||
                    !inputService.isInteger(trainingContentRequest.getDuration()) ||
                    !inputService.isInteger(trainingContentRequest.getLearningObjective().getCode()) ||
                    trainingContentRequest.getContent() == null ||
                    trainingContentRequest.getDeliveryType() == null
            ){
                errorMessage = "Dữ liệu đầu vào không được để trống, chỉ chứa những ký tự UTF-8, chữ hoa, chữ thường, số, dấu @, dấu ngoặc (), dấu chấm \".\", dấu chấm than \"!\"";
            }
        });
        if (errorMessage != null){
            return "redirect:/syllabus/list";
        }
    } catch (Exception ex){
        errorMessage = "Dữ liệu đầu vào không được để trống, chỉ chứa những ký tự UTF-8, chữ hoa, chữ thường, số, dấu @, dấu ngoặc (), dấu chấm \".\", dấu chấm than \"!\"";
        return "redirect:/syllabus/list";
    }


    ResponseEntity<SyllabusResponse> responseEntity_Syllabus = syllabusService.save(syllabusModel);
    if (responseEntity_Syllabus.getStatusCode() != HttpStatus.CREATED) {
        errorMessage = "Thêm syllabus không thành công";
        return "redirect:/syllabus/list";
    }

    SyllabusResponse syllabusResponse = responseEntity_Syllabus.getBody();
    int syllabusTopicCode = syllabusResponse.getTopicCode();
    for (TrainingUnitRequest unitRequest:trainingUnitRequestList) {
        ResponseEntity<TrainingUnitResponse> responseEntity_Unit = trainingUnitService.save(unitRequest, syllabusTopicCode);
        if (responseEntity_Unit.getStatusCode() != HttpStatus.CREATED) {
            errorMessage = "Thêm unit không thành công";
            return "redirect:/syllabus/list";
        }

        TrainingUnitResponse unitResponse = responseEntity_Unit.getBody();
        int unitCode = unitResponse.getUnitCode();
        for (TrainingContentRequest trainingContentRequest:trainingContentRequestList) {
            TrainingContentRequest trainingContentRequest2 = new TrainingContentRequest();
            if (trainingContentRequest.getUnit().equals(unitResponse.getUnit()) && trainingContentRequest.getDay().equals(unitResponse.getDay())){
                trainingContentRequest2 = trainingContentRequest;
                trainingContentRequest2.setDeliveryType(trainingContentRequest.getDeliveryType());

                ResponseEntity<TrainingContentResponse> responseEntity_Content = trainingContentService.save(trainingContentRequest2, syllabusTopicCode, unitCode);
                if (responseEntity_Content.getStatusCode() != HttpStatus.CREATED) {
                    errorMessage = "Thêm content không thành công";
                    return "redirect:/syllabus/list";
                }
            }
            else {
                continue;
            }

        }
    }


    message = "Thêm thành công";
    return "redirect:/syllabus/list";
}

    @PostMapping("/create/outline")
    public void takeUnit(@RequestBody TrainingUnitRequest request){
        try{
            if (trainingUnitRequestList.size() == 0){
                TrainingUnitRequest newTrainingUnit = new TrainingUnitRequest();
                newTrainingUnit = request;
                trainingUnitRequestList.add(newTrainingUnit);
            }
            else {
                for (TrainingUnitRequest trainingUnit:trainingUnitRequestList) {
                    if (request.getUnit().equals(trainingUnit.getUnit())
                            && request.getDay().equals(trainingUnit.getDay())){
                        trainingUnit.setUnitName(request.getUnitName());
                    }
                    trainingUnitRequestList.add(request);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @PostMapping("/create/outline/contentInUnit")
    public void takeContent(@RequestBody List<ContentRequestNew> request){
        try{
            for (ContentRequestNew contentRequestNew: request) {
                TrainingContentRequest trainingContentRequest = new TrainingContentRequest();
                trainingContentRequest.setContent(contentRequestNew.getContent());
                trainingContentRequest.setDuration(contentRequestNew.getDuration());
                trainingContentRequest.setNote(contentRequestNew.getNote());
                trainingContentRequest.setDay(contentRequestNew.getDay());
                trainingContentRequest.setDeliveryType(contentRequestNew.getDeliveryType());
                trainingContentRequest.setUnitName(contentRequestNew.getUnitName());
                trainingContentRequest.setTrainingFormat(contentRequestNew.isTrainingFormat());
                trainingContentRequest.setUnit(contentRequestNew.getUnit());

                LearningObjectiveRequest learningObjectiveRequest = new LearningObjectiveRequest();
                if (contentRequestNew.getLearningObjective().equals("HS4O")){
                    learningObjectiveRequest.setCode(1);
                } else if (contentRequestNew.getLearningObjective().equals("SP3k")) {
                    learningObjectiveRequest.setCode(2);
                }else {
                    learningObjectiveRequest.setCode(3);
                }
                trainingContentRequest.setLearningObjective(learningObjectiveRequest);

                trainingContentRequestList.add(trainingContentRequest);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @PostMapping("/import")
    public String importFile(@RequestParam("file") MultipartFile[] files,
                             Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
//            System.out.println(files.);
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        try{
            boolean result = syllabusService.importFile(files);
//        boolean result = true;
            if (result == true ){
                message = "Import và cập nhật thành công";
            } else {
                errorMessage = "Import và cập nhật không thành công";
            }
        } catch (Exception ex){
            errorMessage = "Import và cập nhật không thành công";
        }

        return "redirect:/syllabus/list";

    }
    @PostMapping("/delete")
    public String deleteSyllabus(
            @ModelAttribute SyllabusRequestUI syllabusRequestUI,
            Model model){
        int topicCode = syllabusRequestUI.getTopicCode();
        String delete =  syllabusService.deleteSyllabus(topicCode);

        if(!delete.equals("")){
            message = "Xóa thành công";
        } else{
            errorMessage = "Xóa không thành công";
        }
        return "redirect:/syllabus/list";
    }
//    @PostMapping("/duplicate")
//    public String duplicateSyllabus(
//            @ModelAttribute SyllabusRequestUI syllabusRequestUI,
//            Model model){
//        int topicCode = syllabusRequestUI.getTopicCode();
//        boolean duplicate =  syllabusService.duplicateSyllabus(topicCode);
//        if(duplicate == true){
//            message = "Nhân bản thành công";
//        } else{
//            errorMessage = "Nhân bản không thành công";
//        }
//        return "redirect:/syllabus/list";
//    }
    @PostMapping("/approve")
    public String approveSyllabus(
            @ModelAttribute SyllabusRequestUI syllabusRequestUI,
            Model model){
        int topicCode = syllabusRequestUI.getTopicCode();
        String duplicate =  syllabusService.approveSyllabus(topicCode);

        if(!duplicate.equals("")){
            message = "Thông qua thành công.";
        } else {
            errorMessage = "Thông qua không thành công.";
        }
        return "redirect:/syllabus/list";
    }

    @GetMapping("/update/{id}")
    public String getViewUpdSyllabus(@PathVariable("id") int syllabusId,
                                     Model model) {
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
        ResponseEntity<SyllabusResponse> syllabusResponse = syllabusService.detail(syllabusId);
        if (syllabusResponse.getStatusCode() != HttpStatus.OK) {
        }
        else {
            SyllabusResponse syllabusResponse1 = syllabusResponse.getBody();
            List<TrainingUnitResponse> trainingUnitResponses = syllabusResponse1.getTopicOutline();

            Set<String> uniqueDays = new HashSet<>();
            List<TrainingUnitResponse> filteredUnits = new ArrayList<>();

            for (TrainingUnitResponse unit : trainingUnitResponses) {
                String day = unit.getDay();
                if (!uniqueDays.contains(day)) {
                    uniqueDays.add(day);
                    filteredUnits.add(unit);
                }
            }

            model.addAttribute("syllabus", syllabusResponse1);
            model.addAttribute("days", uniqueDays);
            model.addAttribute("units", trainingUnitResponses);
        }

        return "ui_syllabus-update";
    }

    @PostMapping("/update/{id}")
    public String edit(Model model,
                       @ModelAttribute("syllabus") @Valid SyllabusRequest syllabusModel,
                       @PathVariable("id") int syllabusId,
                       BindingResult bindingResult) {
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
        ResponseEntity<SyllabusResponse> responseEntity_Syllabus = syllabusService.edit(syllabusModel, syllabusId);
        if (responseEntity_Syllabus.getStatusCode() != HttpStatus.CREATED) {
            errorMessage = "Update không thành công";
            return "redirect:/syllabus/list";
        }

        SyllabusResponse syllabusResponse = responseEntity_Syllabus.getBody();
        int syllabusTopicCode = syllabusResponse.getTopicCode();


//        for (TrainingUnitRequest unitRequest:trainingUnitRequestList) {
//            ResponseEntity<TrainingUnitResponse> responseEntity_Unit = trainingUnitService.save(unitRequest, syllabusTopicCode);
//            if (responseEntity_Unit.getStatusCode() != HttpStatus.CREATED) {
//                errorMessage = "Thêm unit không thành công";
//                return "redirect:/syllabus/list";
//            }
//
//            TrainingUnitResponse unitResponse = responseEntity_Unit.getBody();
//            int unitCode = unitResponse.getUnitCode();
//            for (TrainingContentRequest trainingContentRequest:trainingContentRequestList) {
//                TrainingContentRequest trainingContentRequest2 = new TrainingContentRequest();
//                if (trainingContentRequest.getUnit().equals(unitResponse.getUnit()) && trainingContentRequest.getDay().equals(unitResponse.getDay())){
//                    trainingContentRequest2 = trainingContentRequest;
//                }
//                trainingContentRequest2.setDeliveryType(trainingContentRequest.getDeliveryType());
//
//                ResponseEntity<TrainingContentResponse> responseEntity_Content = trainingContentService.save(trainingContentRequest2, syllabusTopicCode, unitCode);
//                if (responseEntity_Content.getStatusCode() != HttpStatus.CREATED) {
//                    errorMessage = "Thêm content không thành công";
//                    return "redirect:/syllabus/list";
//                }
//            }
//        }


        message = "Update thành công";
        return "redirect:/syllabus/list";
    }

    @PostMapping("/update/unit/{id}")
    public void editUnit(Model model,
                           @PathVariable("id") int syllabusId,
                           @RequestBody TrainingUnitRequestEdit unitRequest,
                           BindingResult bindingResult) {

        List<TrainingUnitRequestEdit> request = new ArrayList<>();
        request.add(unitRequest);

        ResponseEntity<List<TrainingUnitResponse>> responseEntity_Unit = trainingUnitService.edit(request, syllabusId);
        if (responseEntity_Unit.getStatusCode() != HttpStatus.CREATED) {
            errorMessage = "Thêm unit không thành công";
        }

        message = "Update thành công";
    }

    //Tinj sau
    @PostMapping("/update/content/{id}")
    public ResponseEntity<TrainingContentResponse> editContent(Model model,
                                                               @PathVariable("id") int unitCode,
                                                               @RequestBody TrainingContentRequestEdit contentRequest,
                                                               BindingResult bindingResult) {

        List<TrainingContentDTO> request = new ArrayList<>();
        TrainingContentDTO newContentRequest = new TrainingContentDTO();
        newContentRequest.setId(contentRequest.getId());
        newContentRequest.setContent(contentRequest.getContent());
        newContentRequest.setDeliveryType(contentRequest.getDeliveryType().replace("/","_"));
        newContentRequest.setDuration(contentRequest.getDuration());
        newContentRequest.setTrainingFormat(contentRequest.isTrainingFormat());
        newContentRequest.setNote(contentRequest.getNote());
        if(contentRequest.getLearningObjective().getName().equals("HS4O")){
            newContentRequest.setLearningObjectiveId(1);
        } else if (contentRequest.getLearningObjective().getName().equals("SP3k")) {
            newContentRequest.setLearningObjectiveId(2);
        }else {
            newContentRequest.setLearningObjectiveId(3);
        }

        request.add(newContentRequest);

        ResponseEntity<TrainingContentResponse> responseEntity_Content = trainingContentService.edit(request, unitCode);
        if (responseEntity_Content.getStatusCode() != HttpStatus.OK
                || responseEntity_Content.getBody() == null) {
            errorMessage = "Update content không thành công";
            return null;
        }

        message = "Update thành công";
        return ResponseEntity.status(responseEntity_Content.getStatusCode()).body(responseEntity_Content.getBody());
    }



    @PostMapping("/delete/{topicCode}")
    public String deleteSyllabus(@PathVariable("topicCode") int topicCode, Model model) {
        String delete = syllabusService.deleteSyllabus(topicCode);

        if (delete != null) {
            errorMessage = "Xóa thành công";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/syllabus/list";
        }
        return "redirect:/syllabus/list";
    }

//    @PostMapping("/duplicate/{topicCode}")
//    public String duplicateSyllabus(@PathVariable("topicCode") int topicCode, Model model) {
//        String duplicate = syllabusService.duplicateSyllabus(topicCode);
//
//        if (duplicate != null) {
//            errorMessage = "Xóa thành công";
//            model.addAttribute("errorMessage", errorMessage);
//            return "redirect:/syllabus/list";
//        }
//        return "redirect:/syllabus/list";
//    }

    @PostMapping("/add_material/{contentId}")
    public String approveSyllabus(@PathVariable("topicCode") int topicCode, Model model) {
        String duplicate = syllabusService.approveSyllabus(topicCode);

        if (duplicate != null) {
            errorMessage = "Xóa thành công";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/syllabus/list";
        }
        return "redirect:/syllabus/list";
    }

    @PostMapping("/add_material")
    public String addFileMaterial(
            @RequestParam("file") MultipartFile files,
            @RequestParam("contentIdAddMaterial") int contentId,
            Model model) {
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
//            System.out.println(files.);
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        if (files.getOriginalFilename().contains(" ") || files.getSize() > 1024 * 1024 * 5){
            errorMessage = "Tên file không được chứa dấu cách và nhỏ hơn 10 MB";
        } else{
            try {
                boolean result = trainingContentService.addMaterialToContent(files, contentId);
                if (result == true) {
                    message = "Upload thành công";
                } else {
                    errorMessage = "Upload không thành công";
                }
            } catch (Exception ex){
                errorMessage = "Upload không thành công";
            }
        }

        List<FileMaterialResponse> fileMaterialResponseList = fileMaterialService.findMaterialByContentId(materialTrainingContentRequestIUIGlobal.getContentId());
        model.addAttribute("fileMaterialList", fileMaterialResponseList);
        model.addAttribute("content", materialTrainingContentRequestIUIGlobal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui-material-training-content";
    }

    @GetMapping("/delete_material_content/{content_id}/{file_id}")
    public String addFileMaterial(
            @PathVariable("content_id") int contentId,
            @PathVariable("file_id") int fileId,
            Model model) {
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
//            System.out.println(files.);
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        try{
            boolean result = fileMaterialService.deleteMaterialByContentIdAndFileId(fileId, contentId);
            if (result == true ){
                message = "Xóa thành công";
            } else {
                errorMessage = "Xóa không thành công";
            }
            List<FileMaterialResponse> fileMaterialResponseList = fileMaterialService.findMaterialByContentId(materialTrainingContentRequestIUIGlobal.getContentId());
            model.addAttribute("fileMaterialList", fileMaterialResponseList);
            model.addAttribute("content", materialTrainingContentRequestIUIGlobal);
            model.addAttribute("message", message);
            model.addAttribute("errorMessage", errorMessage);
            message = null;
            errorMessage = null;
            return "ui-material-training-content";
        } catch (Exception ex){
            errorMessage = "Xóa không thành công";
            return "ui-material-training-content";
        }

    }

    @PostMapping("/duplicate")
    public String duplicateSyllabus(
            @ModelAttribute SyllabusRequestUI syllabusRequestUI,
            Model model){
        int topicCode = syllabusRequestUI.getTopicCode();
        boolean duplicate =  syllabusService.duplicateSyllabus(topicCode);
        if(duplicate == true){
            message = "Nhân bản thành công";
        } else{
            errorMessage = "Nhân bản không thành công";
        }
        return "redirect:/syllabus/list";
    }


    @PostMapping("/view_material_content")
    public String viewMaterialTrainingContent(
            @ModelAttribute MaterialTrainingContentRequestIUI materialTrainingContentRequestIUI,
            Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
//            System.out.println(files.);
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        message = null;
        errorMessage = null;
        materialTrainingContentRequestIUIGlobal = materialTrainingContentRequestIUI;
        List<FileMaterialResponse> fileMaterialResponseList = fileMaterialService.findMaterialByContentId(materialTrainingContentRequestIUI.getContentId());
        fileMaterialResponseList.forEach(fileMaterialResponse -> {
        });
        model.addAttribute("fileMaterialList", fileMaterialResponseList);
        model.addAttribute("content", materialTrainingContentRequestIUI);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui-material-training-content";
    }

}
