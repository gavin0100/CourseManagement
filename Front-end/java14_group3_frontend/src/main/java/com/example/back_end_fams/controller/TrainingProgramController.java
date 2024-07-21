package com.example.back_end_fams.controller;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.UI.TrainingProgramRequestUI;
import com.example.back_end_fams.model.request.TrainingProgramRequest;
import com.example.back_end_fams.model.response.*;
import com.example.back_end_fams.service.SyllabusService;
import com.example.back_end_fams.service.TrainginProgramService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/training_program")
public class TrainingProgramController {
    private final Logger logger = LoggerFactory.getLogger(TrainingProgramController.class);
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private TrainginProgramService trainingProgramService;

    @Autowired
    private SyllabusService syllabusService;

    private String message = null;
    private String errorMessage = null;

    @GetMapping("get_list")
    public String getAllTrainingProgram(Model model){
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
        List<TrainingProgramResponse> trainingProgramResponses = trainingProgramService.findAll();
        Comparator<TrainingProgramResponse> comparator = Comparator.comparing(TrainingProgramResponse::getModifiedDate, Comparator.nullsLast(Comparator.reverseOrder()));

        // Sort the list using the comparator
        Collections.sort(trainingProgramResponses, comparator);
        model.addAttribute("trainingPrograms", trainingProgramResponses);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui_training";
    }

    @PostMapping("/add_syllabus_page")
    public String viewSyllabus(
                               @ModelAttribute TrainingProgramRequestUI trainingProgramRequestUI,
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
        int id = trainingProgramRequestUI.getTrainingProgramId();
        TrainingProgramDetailResponse trainingProgramDetailResponse = trainingProgramService.findById(id);
        if (trainingProgramDetailResponse == null){
            trainingProgramDetailResponse = new TrainingProgramDetailResponse();
        }
        List<TrainingProgramFileMaterialDTO> trainingProgramFileMaterialDTOList =
                trainingProgramService.getFileMaterialByTrainingProgramId(id);
//        logger.error("error {}", trainingProgramResponse);
        List<SyllabusResponse> syllabusResponses = syllabusService.findAll();
        model.addAttribute("trainingProgram", trainingProgramDetailResponse);
        model.addAttribute("syllabusResponses", syllabusResponses);
        model.addAttribute("syllabusBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramSyllabusDTOS());
        model.addAttribute("trainingProgramFileMaterialDTOList", trainingProgramFileMaterialDTOList);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui_training-create";
    }

    @PostMapping("/detail")
    public String detailTrainingProgram(
            @ModelAttribute TrainingProgramRequestUI trainingProgramRequestUI,
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
        int id = trainingProgramRequestUI.getTrainingProgramId();
        TrainingProgramDetailResponse trainingProgramDetailResponse = trainingProgramService.findById(id);
        if (trainingProgramDetailResponse == null){
            trainingProgramDetailResponse = new TrainingProgramDetailResponse();
        }

        List<TrainingProgramFileMaterialDTO> trainingProgramFileMaterialDTOList =
                trainingProgramService.getFileMaterialByTrainingProgramId(id);
//        logger.error("error {}", trainingProgramResponse);
        model.addAttribute("trainingProgram", trainingProgramDetailResponse);
        model.addAttribute("syllabusBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramSyllabusDTOS());
        model.addAttribute("classBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramClassDTOS());
        model.addAttribute("trainingProgramFileMaterialDTOList", trainingProgramFileMaterialDTOList);
        return "ui_training_detail";
    }

    @PostMapping("/create")
    public String create(@RequestParam("name") String name,
                         @RequestParam("estimatedDuration") int estimatedDuration,
                         @RequestParam("status") String status,
                         @RequestParam("generalInformation") String generalInformation,
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

        boolean rusult = trainingProgramService.createTrainingProgram(name,estimatedDuration, status,generalInformation);
        if (rusult == true ){
            message = "Thêm thành công";
        } else {
            errorMessage = "Thêm không thành công";
        }
        return "redirect:/training_program/get_list";

    }

    @PostMapping("/edit")
    public String edit(
            @RequestParam("trainingProgramId") int trainingProgramId,
            @RequestParam("name") String name,
                         @RequestParam("duration") int duration,
                         @RequestParam("generalInformation") String generalInformation,
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
        boolean rusult = trainingProgramService.updateTrainingProgram(trainingProgramId,name,duration,generalInformation);
        if (rusult == true ){
            message = "Cập nhật thành công";
        } else {
            errorMessage = "Cập nhật không thành công";
        }
        return "redirect:/training_program/get_list";
    }


    @GetMapping("/duplicate/{id}")
    public String duplicate(@PathVariable("id") int id, Model model){
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

        boolean rusult = trainingProgramService.duplicateTrainingProgram(id);
        if (rusult == true ){
            message = "Nhân bản thành công";
        } else {
            errorMessage = "Nhân bản không thành công";
        }
        return "redirect:/training_program/get_list";

    }

    @GetMapping("/active/{id}")
    public String active(@PathVariable("id") int id, Model model){
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

        boolean rusult = trainingProgramService.activeTrainingProgram(id);
        if (rusult == true ){
            message = "Cập nhật thành công";
        } else {
            errorMessage = "Cập nhật không thành công";
        }
        return "redirect:/training_program/get_list";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model){
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

        boolean rusult = trainingProgramService.deleteTrainingProgram(id);
        if (rusult == true ){
            message = "Xóa thành công";
        } else {
            errorMessage = "Xóa không thành công";
        }
        return "redirect:/training_program/get_list";

    }

    @PostMapping("/add_syllabus")
    public String addSyllbus(@ModelAttribute TrainingProgramRequestUI trainingProgramRequestUI,
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
        int trainingProgramId = trainingProgramRequestUI.getTrainingProgramId();
        int syllabusId = trainingProgramRequestUI.getSyllabusId();
        boolean rusult = trainingProgramService.addSyllabusIntoTrainingProgram(trainingProgramId, syllabusId);
        if (rusult == true ){
            message = "Thêm syllabus thành công";
        } else {
            errorMessage = "Thêm syllabuss không thành công";
        }
        TrainingProgramDetailResponse trainingProgramDetailResponse = trainingProgramService.findById(trainingProgramId);
        if (trainingProgramDetailResponse == null){
            trainingProgramDetailResponse = new TrainingProgramDetailResponse();
        }

        List<TrainingProgramFileMaterialDTO> trainingProgramFileMaterialDTOList =
                trainingProgramService.getFileMaterialByTrainingProgramId(trainingProgramId);
//        logger.error("error {}", trainingProgramResponse);
        List<SyllabusResponse> syllabusResponses = syllabusService.findAll();
        model.addAttribute("syllabusResponses", syllabusResponses);
        model.addAttribute("trainingProgram", trainingProgramDetailResponse);
        model.addAttribute("syllabusBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramSyllabusDTOS());
        model.addAttribute("classBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramClassDTOS());
        model.addAttribute("trainingProgramFileMaterialDTOList", trainingProgramFileMaterialDTOList);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui_training-create";

    }

    @PostMapping("/delete_syllabus")
    public String deleteSyllbus(@ModelAttribute TrainingProgramRequestUI trainingProgramRequestUI,
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
        int trainingProgramId = trainingProgramRequestUI.getTrainingProgramId();
        int syllabusId = trainingProgramRequestUI.getSyllabusId();
        if (syllabusId != -1){
            boolean rusult = trainingProgramService.deleteSyllabusIntoTrainingProgram(trainingProgramId, syllabusId);
            if (rusult == true ){
                message = "Xóa syllabus thành công";
            } else {
                errorMessage = "Xóa syllabuss không thành công";
            }
        }

        TrainingProgramDetailResponse trainingProgramDetailResponse = trainingProgramService.findById(trainingProgramId);
        if (trainingProgramDetailResponse == null){
            trainingProgramDetailResponse = new TrainingProgramDetailResponse();
        }

        List<TrainingProgramFileMaterialDTO> trainingProgramFileMaterialDTOList =
                trainingProgramService.getFileMaterialByTrainingProgramId(trainingProgramId);
//        logger.error("error {}", trainingProgramResponse);
        List<SyllabusResponse> syllabusResponses = syllabusService.findAll();
        model.addAttribute("syllabusResponses", syllabusResponses);
        model.addAttribute("trainingProgram", trainingProgramDetailResponse);
        model.addAttribute("syllabusBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramSyllabusDTOS());
        model.addAttribute("classBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramClassDTOS());
        model.addAttribute("trainingProgramFileMaterialDTOList", trainingProgramFileMaterialDTOList);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui_training-create";

    }
    @PostMapping("/add_material")
    public String addMaterial(@RequestParam("trainingProgramId") int trainingProgramId,
                                @RequestParam("file") MultipartFile[] files,
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
        for (int i =0; i < files.length; i ++){
            if (files[i].getOriginalFilename().contains(" ")){
                errorMessage = "Tên file không được chứa dấu cách.";
            }
        }
        if (errorMessage == null){
            try{
                boolean result = trainingProgramService.addMaterial(files, trainingProgramId);
//        boolean result = true;
                if (result == true ){
                    message = "Thêm material thành công";
                } else {

                }
            } catch (Exception ex){
                errorMessage = "Thêm material không thành công";
            }
        }

        TrainingProgramDetailResponse trainingProgramDetailResponse = trainingProgramService.findById(trainingProgramId);
        if (trainingProgramDetailResponse == null){
            trainingProgramDetailResponse = new TrainingProgramDetailResponse();
        }

        List<TrainingProgramFileMaterialDTO> trainingProgramFileMaterialDTOList =
                trainingProgramService.getFileMaterialByTrainingProgramId(trainingProgramId);
//        logger.error("error {}", trainingProgramResponse);
        List<SyllabusResponse> syllabusResponses = syllabusService.findAll();
        model.addAttribute("syllabusResponses", syllabusResponses);
        model.addAttribute("trainingProgram", trainingProgramDetailResponse);
        model.addAttribute("syllabusBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramSyllabusDTOS());
        model.addAttribute("classBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramClassDTOS());
        model.addAttribute("trainingProgramFileMaterialDTOList", trainingProgramFileMaterialDTOList);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui_training-create";

    }
    @PostMapping("/delete_material")
    public String deleteMaterial(
            @ModelAttribute TrainingProgramRequestUI trainingProgramRequestUI,
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
        int id = trainingProgramRequestUI.getTrainingProgramId();
        String materialName = trainingProgramRequestUI.getMaterialName();
        boolean rusult = trainingProgramService.deleteMaterialFromTrainingProgram(id, materialName);
        if (rusult == true ){
            message = "Xóa thành công";
        } else {
            errorMessage = "Xóa không thành công";
        }
        TrainingProgramDetailResponse trainingProgramDetailResponse = trainingProgramService.findById(id);
        if (trainingProgramDetailResponse == null){
            trainingProgramDetailResponse = new TrainingProgramDetailResponse();
        }

        List<TrainingProgramFileMaterialDTO> trainingProgramFileMaterialDTOList =
                trainingProgramService.getFileMaterialByTrainingProgramId(id);
//        logger.error("error {}", trainingProgramResponse);
        List<SyllabusResponse> syllabusResponses = syllabusService.findAll();
        model.addAttribute("syllabusResponses", syllabusResponses);
        model.addAttribute("trainingProgram", trainingProgramDetailResponse);
        model.addAttribute("syllabusBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramSyllabusDTOS());
        model.addAttribute("classBelongTrainingProgram", trainingProgramDetailResponse.getTrainingProgramClassDTOS());
        model.addAttribute("trainingProgramFileMaterialDTOList", trainingProgramFileMaterialDTOList);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        return "ui_training-create";

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
            boolean result = trainingProgramService.importFile(files);
//        boolean result = true;
            if (result == true ){
                message = "Import và cập nhật thành công";
            } else {

            }
        } catch (Exception ex){
            errorMessage = "Import và cập nhật không thành công";
        }

        return "redirect:/training_program/get_list";

    }
}
