package com.example.back_end_fams.controller;

import com.example.back_end_fams.exception.FileStorageException;
import com.example.back_end_fams.model.entity.*;

import com.example.back_end_fams.model.request.TrainingContentDTO;
import com.example.back_end_fams.model.response.SyllabusResponse;
import com.example.back_end_fams.model.response.SyllabusResponseV2;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import com.example.back_end_fams.model.mapper.FileMaterialMapper;
import com.example.back_end_fams.model.mapper.SyllabusMapper;
import com.example.back_end_fams.model.mapper.TrainingContentMapper;
import com.example.back_end_fams.model.response.FileMaterialResponse;
import com.example.back_end_fams.repository.LearningObjectiveRepository;
import com.example.back_end_fams.repository.SyllabusRepository;
import com.example.back_end_fams.repository.TrainingContentRepository;
import com.example.back_end_fams.repository.TrainingUnitRepository;
import com.example.back_end_fams.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import java.util.*;

@RestController
@RequestMapping("/api/syllabus")
public class SyllabusController {
    private static final Logger logger = LoggerFactory.getLogger(SyllabusController.class);
    @Autowired
    SyllabusService syllabusService;
    @Autowired
    FileMaterialMapper fileMaterialMapper;

    @Autowired
    SyllabusMapper syllabusMapper;
    @Autowired
    SyllabusRepository syllabusRepository;
    @Autowired
    LearningObjectiveService learningObjectiveService;
    @Autowired
    LearningObjectiveRepository learningObjectiveRepository;
    @Autowired
    TrainingUnitRepository trainingUnitRepository;
    @Autowired
    TrainingUnitService trainingUnitService;
    @Autowired
    TrainingContentRepository trainingContentRepository;
    @Autowired
    TrainingContentService trainingContentService;

    @Autowired
    FileMaterialService fileMaterialService;

    @Autowired
    TrainingContentMapper trainingContentMapper;


    @GetMapping("/list")
    public ResponseEntity<?> getAllSysExisted(){
        List<Syllabus> syl =syllabusService.findAll();
        return ResponseEntity.ok(syllabusMapper.toSyllabusListDTO(syl));
    }

    @PostMapping("/general")
    public ResponseEntity<Syllabus> saveGenerals(@RequestBody Syllabus syllabus, UriComponentsBuilder builder) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Date currentDate = new Date();
        syllabus.setCreatedDate(currentDate);
        syllabus.setCreatedBy(currentUser);
        syllabus.setVersion("1.0");
        syllabus.setPublishStatus("Draft");
        syllabusService.saveGeneral(syllabus);
        return ResponseEntity.status(HttpStatus.CREATED).body(syllabus);

                /*{
                    "topicName":"test2",
                    "trainingAudience":20,
                    "version":"i1",
                    "technicalGroup":"MySQL",
                    "level":"Advanced"
}               */

    }


//    @PostMapping("/learningObjective/{syllabusId}")
//    public ResponseEntity<LearningObjective> saveLearningObjective(@PathVariable int syllabusId, @RequestBody LearningObjective learningObjective) {
//        if (learningObjectiveRepository.existsById(syllabusId)){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        else {
//            learningObjective.setCode(syllabusId);
//
//            Syllabus syllabus = syllabusService.getSyllabusByCode(syllabusId);
//            syllabus.addLearningObjective(learningObjective);
//
//            learningObjectiveService.saveObjective(learningObjective);
//            return new ResponseEntity<>(learningObjective, HttpStatus.CREATED);
//        }
//    }


    //OUTLINE
    @PostMapping("/outline/{syllabusId}/trainingUnit")
    public ResponseEntity<TrainingUnit> saveOutline(@PathVariable int syllabusId, @RequestBody TrainingUnit trainingUnit,UriComponentsBuilder builder){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Date currentDate = new Date();
        Syllabus syllabus = syllabusService.getSyllabusByCode(syllabusId);
        Set<LearningObjective> learn = syllabus.getLearningObjectives();
        System.out.println(learn);
        syllabus.setModifiedDate(currentDate);
        syllabus.setCreatedBy(currentUser);
        syllabus.setPublishStatus("Draft");
        syllabusService.saveOutline(syllabus);
        trainingUnit.setSyllabus(syllabus);
        trainingUnitService.saveTrainingUnit(trainingUnit);
        return new ResponseEntity<>(trainingUnit, HttpStatus.CREATED);
        /*{

                "unitName":"test2",
                "numberOfHours":20,
                "day":"Day 1",
                "unit":"Unit 1"

        }*/
    }

    @PostMapping("/{syllabusId}/trainingContent/{unitCode}")
    public ResponseEntity<TrainingContent> saveContent(@PathVariable int syllabusId,@PathVariable int unitCode, @RequestBody TrainingContent trainingContent) {
        Syllabus syllabus = syllabusService.getSyllabusByCode(syllabusId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Date currentDate = new Date();
        syllabus.setModifiedDate(currentDate);
        syllabus.setCreatedBy(currentUser);
        syllabus.setPublishStatus("Draft");
        Set<LearningObjective> learningObjectives = new HashSet<>();
        learningObjectives.add(trainingContent.getLearningObjective());
        syllabus.setLearningObjectives(learningObjectives);
        syllabusService.saveOther(syllabus);
        trainingContent.setTrainingUnit(trainingUnitService.getTrainUnitById(unitCode));
        trainingContentService.saveContent(trainingContent);
        return new ResponseEntity<>(trainingContent, HttpStatus.CREATED);
    }

    //Training Principle
    @PostMapping("/other/{syllabusId}")
    public ResponseEntity<Syllabus> saveOther(@PathVariable int syllabusId, @RequestBody Syllabus syllabusTP){
        Syllabus syllabus = syllabusService.getSyllabusByCode(syllabusId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Date currentDate = new Date();
        Set<LearningObjective> learn = syllabus.getLearningObjectives();
        System.out.println(learn);
        syllabus.setModifiedDate(currentDate);
        syllabus.setCreatedBy(currentUser);
        syllabus.setPublishStatus("Active");
        syllabus.setTrainingPrinciples(syllabusTP.getTrainingPrinciples());
        syllabusService.saveOutline(syllabus);
        return new ResponseEntity<>(syllabus, HttpStatus.CREATED);
    }

    @PostMapping("/{contentId}/file/uploadNew")
    public ResponseEntity<?> uploadFile(@PathVariable("contentId") int contentId,@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }
        try {
            FileMaterial fileMaterial = new FileMaterial();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            Date currentDate = new Date();
            fileMaterial.setCreatedBy(currentUser);
            fileMaterial.setCreatedDate(currentDate);
            String fileName =fileMaterialService.storeFile(file);
            fileMaterial.setName(fileName);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/syllabus/fileUpload/")
                    .path(fileName)
                    .toUriString();
            fileMaterial.setUrl(fileDownloadUri);
            fileMaterialService.uploadFile(fileMaterial);
            TrainingContent trainingContent = trainingContentService.getContentById(contentId);
            if (trainingContent != null) {
                List<TrainingContent> trainingContents = new ArrayList<>();
                trainingContents.add(trainingContent);
                fileMaterial.setTrainingContents(trainingContents);
                List<FileMaterial> fileMaterials = new ArrayList<>();
                fileMaterials.add(fileMaterial);
                trainingContent.setTrainingMaterials(fileMaterials);
                TrainingContentFileMaterial trainingContentFileMaterial = new TrainingContentFileMaterial();
                trainingContentFileMaterial.setFileMaterial(fileMaterial);
                trainingContentFileMaterial.setTrainingContent(trainingContent);
                fileMaterialService.saveFile(trainingContentFileMaterial);
            } else {
                // Xử lý khi không tìm thấy TrainingContent theo contentId
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(fileMaterial, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }
    }
    @GetMapping("/fileUpload/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileMaterialService.loadFileAsResource(fileName);
        System.out.println("Hello " + resource);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/findByName")
    public ResponseEntity<?> findName(@RequestParam String syllabusName){
        List<Syllabus> syllabusList = syllabusService.findByName(syllabusName);
        return new ResponseEntity<>(syllabusMapper.toSyllabusListDTO(syllabusList),HttpStatus.OK);
    }

    @GetMapping("/findByCode")
    public ResponseEntity<?> findCode(@RequestParam int code){
        Syllabus syllabus = syllabusRepository.findSyllabusByTopicCode(code);
        return new ResponseEntity<>(syllabusMapper.toResponse(syllabus),HttpStatus.OK);
    }

    @GetMapping("/detail/{syllabusId}")
    public ResponseEntity<Syllabus> detail(@PathVariable int syllabusId){
        Syllabus syllabus = syllabusRepository.findById(syllabusId).orElse(null);
        return new ResponseEntity<>(syllabus, HttpStatus.OK);
    }
    @GetMapping("/detail2/{syllabusId}")
    public ResponseEntity<SyllabusResponseV2> detail2(@PathVariable int syllabusId){
        return new ResponseEntity<>(syllabusService.getDetail(syllabusId), HttpStatus.OK);
    }
    @PostMapping("/edit/general/{syllabusId}")
    public ResponseEntity<Syllabus> updateGeneral(@PathVariable int syllabusId, @RequestBody Syllabus updateSyllabus){
        Syllabus existingSyllabus = syllabusService.getSyllabusByCode(syllabusId);

        if(existingSyllabus != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            Date currentDate = new Date();

            existingSyllabus.setModifiedBy(currentUser);
            existingSyllabus.setModifiedDate(currentDate);
            existingSyllabus.setTopicName(updateSyllabus.getTopicName());
            existingSyllabus.setLevel(updateSyllabus.getLevel());
            existingSyllabus.setTechnicalGroup(updateSyllabus.getTechnicalGroup());
            existingSyllabus.setTrainingAudience(updateSyllabus.getTrainingAudience());
            existingSyllabus.setVersion(updateSyllabus.getVersion());

            Syllabus updatedSyllabus = syllabusService.editSyllabus(existingSyllabus);

            if(updatedSyllabus != null) {
                return new ResponseEntity<>(updatedSyllabus, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/edit/learningObjective/{syllabusId}")
    public ResponseEntity<LearningObjective> updateLearningObjective(@PathVariable int syllabusId, @RequestBody LearningObjective updatedLearningObjective) {
        LearningObjective existingLearningObjective = learningObjectiveRepository.findById(syllabusId).orElse(null);

        if (existingLearningObjective != null) {
            // Cập nhật thông tin từ updatedLearningObjective vào existingLearningObjective
            existingLearningObjective.setName(updatedLearningObjective.getName());
            existingLearningObjective.setType(updatedLearningObjective.getType());
            existingLearningObjective.setDescription(updatedLearningObjective.getDescription());

            LearningObjective savedLearningObjective = learningObjectiveRepository.save(existingLearningObjective);

            if (savedLearningObjective != null) {
                return new ResponseEntity<>(savedLearningObjective, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/edit/outline/{syllabusId}/trainingUnits")
    public ResponseEntity<List<TrainingUnit>> updateTrainingUnits(@PathVariable int syllabusId, @RequestBody List<TrainingUnit> updatedTrainingUnits) {
        Syllabus existingSyllabus = syllabusService.getSyllabusByCode(syllabusId);

        if (existingSyllabus != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            Date currentDate = new Date();

            // Cập nhật thông tin từ danh sách updatedTrainingUnits vào đề cương khóa học và danh sách đơn vị đào tạo
            existingSyllabus.setModifiedDate(currentDate);
            existingSyllabus.setCreatedBy(currentUser);
            syllabusService.saveOutline(existingSyllabus);

            List<TrainingUnit> syllabusTrainingUnits = trainingUnitService.getTrainingUnitsBySyllabusId(syllabusId); // Lấy danh sách TrainingUnit từ Syllabus
            TrainingUnit newTrainingUnit = new TrainingUnit();
            List<TrainingUnit> savedTrainingUnits = new ArrayList<>();
            boolean foundExistingUnit;

            if (syllabusTrainingUnits != null) {
                for (TrainingUnit updatedTrainingUnit : updatedTrainingUnits) {
                    foundExistingUnit = false;

                    for (TrainingUnit existingTrainingUnit : syllabusTrainingUnits) {
                        if (existingTrainingUnit.getUnitCode()==updatedTrainingUnit.getUnitCode()) {
                            // Tìm thấy đơn vị đào tạo hiện tại, cập nhật và đánh dấu là đã tìm thấy
                            existingTrainingUnit.setUnitName(updatedTrainingUnit.getUnitName());
                            existingTrainingUnit.setNumberOfHours(updatedTrainingUnit.getNumberOfHours());
                            existingTrainingUnit.setUnit(updatedTrainingUnit.getUnit());
                            existingTrainingUnit.setDay(updatedTrainingUnit.getDay());
                            trainingUnitService.saveTrainingUnit(existingTrainingUnit);
                            foundExistingUnit = true;
                        }
                    }

                    if (!foundExistingUnit) {
                        // Không tìm thấy, thêm mới đơn vị đào tạo
                        newTrainingUnit.setUnitName(updatedTrainingUnit.getUnitName());
                        newTrainingUnit.setNumberOfHours(updatedTrainingUnit.getNumberOfHours());
                        newTrainingUnit.setUnit(updatedTrainingUnit.getUnit());
                        newTrainingUnit.setDay(updatedTrainingUnit.getDay());
                        newTrainingUnit.setSyllabus(existingSyllabus);
                        TrainingUnit savedTrainingUnit = trainingUnitService.saveTrainingUnit(newTrainingUnit);
                        savedTrainingUnits.add(savedTrainingUnit);
                    }
                }
            }

            if (savedTrainingUnits.isEmpty()) {
                // Không có đơn vị đào tạo mới được thêm, trả về danh sách đã có
                return new ResponseEntity<>(syllabusTrainingUnits, HttpStatus.OK);
            } else {
                // Trả về danh sách đã cập nhật và mới được thêm
                return new ResponseEntity<>(savedTrainingUnits, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        /*
        {
            "unitCode": 9,
            "unitName": "Updated Unit Name 1",
            "numberOfHours": 30,
            "day":"12",
            "unit":"12"
        }*/
    }
    @PostMapping("/edit/trainingUnits/{unitCode}/content")
    public ResponseEntity<List<TrainingContent>> updateTrainingContent(@PathVariable int unitCode, @RequestBody List<TrainingContent> updatedTrainingContents) {
        TrainingUnit existingTrainingUnit = trainingUnitService.getTrainUnitById(unitCode);

        if (existingTrainingUnit != null) {
            List<TrainingContent> trainingContents = trainingContentService.getContentByUnitCode(unitCode);
            List<TrainingContent> savedTrainingContents = new ArrayList<>();
            TrainingContent newTrainingContent = new TrainingContent();
            if (trainingContents != null) {
                for (TrainingContent existingTrainingContent : trainingContents) {
                    for (TrainingContent updatedTrainingContent : updatedTrainingContents) {
                        if (existingTrainingContent.getId() == updatedTrainingContent.getId()) {
                            existingTrainingContent.setContent(updatedTrainingContent.getContent());
                            existingTrainingContent.setTrainingFormat(updatedTrainingContent.isTrainingFormat());
                            existingTrainingContent.setDeliveryType(updatedTrainingContent.getDeliveryType());
                            existingTrainingContent.setDuration(updatedTrainingContent.getDuration());
                            existingTrainingContent.setNote(updatedTrainingContent.getNote());
                            TrainingContent savedTrainingContent = trainingContentService.saveContent(existingTrainingContent);
                            savedTrainingContents.add(savedTrainingContent);
                        }
                        else {
                            // Nếu danh sách TrainingContents trống, tạo mới

                                newTrainingContent.setDuration(updatedTrainingContent.getDuration());
                                newTrainingContent.setContent(updatedTrainingContent.getContent());
                                newTrainingContent.setTrainingFormat(updatedTrainingContent.isTrainingFormat());
                                newTrainingContent.setNote(updatedTrainingContent.getNote());
                                newTrainingContent.setDeliveryType(updatedTrainingContent.getDeliveryType());
                                TrainingContent savedTrainingContent = trainingContentService.saveContent(newTrainingContent);

                                savedTrainingContents.add(savedTrainingContent);

                        }
                    }
                }
            } else {
                // Nếu danh sách TrainingContents trống, tạo mới
                for (TrainingContent updatedTrainingContent : updatedTrainingContents) {
                    newTrainingContent.setDuration(updatedTrainingContent.getDuration());
                    newTrainingContent.setContent(updatedTrainingContent.getContent());
                    newTrainingContent.setTrainingFormat(updatedTrainingContent.isTrainingFormat());
                    newTrainingContent.setNote(updatedTrainingContent.getNote());
                    newTrainingContent.setDeliveryType(updatedTrainingContent.getDeliveryType());
                    TrainingContent savedTrainingContent = trainingContentService.saveContent(newTrainingContent);
                    savedTrainingContents.add(savedTrainingContent);
                }
            }

            if (!savedTrainingContents.isEmpty()) {
                return new ResponseEntity<>(savedTrainingContents, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/other/{syllabusId}")
    public ResponseEntity<Syllabus> updateOther(@PathVariable int syllabusId, @RequestBody Syllabus updateSyllabus){
        Syllabus syllabus = syllabusService.getSyllabusByCode(syllabusId);
        if (syllabus != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            Date currentDate = new Date();
            Set<LearningObjective> learn = syllabus.getLearningObjectives();
            System.out.println(learn);
            syllabus.setModifiedDate(currentDate);
            syllabus.setCreatedBy(currentUser);
            syllabus.setPublishStatus("Active");
            syllabus.setTrainingPrinciples(updateSyllabus.getTrainingPrinciples());
            syllabusService.saveOutline(syllabus);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(syllabus, HttpStatus.CREATED);
    }


    @PostMapping("/delete/{syllabusId}")
    public ResponseEntity<Syllabus> deleteSyllabus(@PathVariable int syllabusId){
        Syllabus syllabus = syllabusService.getSyllabusByCode(syllabusId);
        if(syllabus != null){
            syllabus.setPublishStatus("Inactive");
            syllabus.setModifiedDate(new Date());
            syllabusRepository.save(syllabus);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(syllabus, HttpStatus.CREATED);
    }

    @PostMapping("/duplicate/{syllabusId}")
    public ResponseEntity<?> duplicate(@PathVariable int syllabusId){
        System.out.println("duplicate syllabus");
        Syllabus syllabus = syllabusService.duplicate(syllabusId);
        return new ResponseEntity<>(syllabusMapper.toResponse(syllabus), HttpStatus.OK);
    }
    @PutMapping("/approve/{syllabusId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CLASS_ADMIN')")
    public String approve(@PathVariable int syllabusId){
        return syllabusService.approveSyllabus(syllabusId);
    }
    @PutMapping("/reject/{syllabusId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CLASS_ADMIN')")
    public String reject(@PathVariable int syllabusId){
        return syllabusService.rejectSyllabus(syllabusId);
    }

    @PostMapping("/import")
    public ResponseEntity<?> importSyllabus(@RequestParam("syllabus") MultipartFile file) throws IOException {
        return syllabusService.importSyllabus(file);
    }
    @PostMapping("/edit1/trainingUnits/{unitCode}/content")
    public ResponseEntity<?> updateTrainingContentV2(@PathVariable int unitCode, @RequestBody List<TrainingContentDTO> TrainingContentDTO) {
        return syllabusService.updateTrainingContentV2(unitCode, TrainingContentDTO);
    }
    @GetMapping("/find_all_learning_objective")
    public ResponseEntity<?> findAllLearningObjective(){
        return new ResponseEntity<>(syllabusService.findAllLearningObjective(), HttpStatus.OK) ;
    }

    @GetMapping("/find_material_by_contentid/{contentId}")
    public ResponseEntity<?> findMaterialByContentId(@PathVariable("contentId") int contentId){
        return new ResponseEntity<>(fileMaterialMapper.toFileMaterialListDTO(fileMaterialService.findMaterialByContentId(contentId)), HttpStatus.OK);
    }

    @GetMapping("/get_all_material_training_content")
    public ResponseEntity<?> findMaterialByContentId(){
        return new ResponseEntity<>(fileMaterialMapper.toFileMaterialListDTO(fileMaterialService.getAllMaterialTrainingContent()), HttpStatus.OK);
    }

    @DeleteMapping("/delete_material_content/{content_id}/{file_id}")
    public ResponseEntity<?> deleteMaterialContent(@PathVariable("content_id") int contentId,
                                                   @PathVariable("file_id") int fileId){
        return new ResponseEntity<>(fileMaterialService.deleteMaterialTrainingContentByFileIdAndContentId(fileId, contentId), HttpStatus.OK);
    }
}
