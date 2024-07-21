package com.example.back_end_fams.service.impl;

import com.example.back_end_fams.model.entity.TrainingProgram;
import com.example.back_end_fams.model.entity.TrainingProgramSyllabus;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.entity.*;
import com.example.back_end_fams.model.entity.Class;
import com.example.back_end_fams.model.mapper.TrainingProgramMapper;
import com.example.back_end_fams.model.request.TrainingProgramCreateRequest;
import com.example.back_end_fams.model.request.TrainingProgramUpdateRequest;
import com.example.back_end_fams.model.response.TrainingProgramResponse;
import com.example.back_end_fams.repository.ClassRepository;
import com.example.back_end_fams.repository.FileMaterialRepository;
import com.example.back_end_fams.repository.TrainingProgramRepository;
import com.example.back_end_fams.repository.TrainingProgramSyllabusRepository;
import com.example.back_end_fams.service.FileMaterialService;
import com.example.back_end_fams.service.SyllabusService;
import com.example.back_end_fams.service.TrainingProgramService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TrainingProgramServiceImpl implements TrainingProgramService {
    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainingProgramMapper trainingProgramMapper;
    private final SyllabusService syllabusService;
    private final TrainingProgramSyllabusRepository trainingProgramSyllabusRepository;
    private final ClassRepository classRepository;
    private final FileMaterialRepository fileMaterialRepository;
    private final FileMaterialService fileMaterialService;

    @Override
    public ResponseEntity<?> getAll() {
        var trainingPrograms = trainingProgramRepository.findAll();
        List<TrainingProgramResponse> trainingProgramResponses = trainingProgramMapper.toListResponse(trainingPrograms);
        return ResponseEntity.ok(trainingProgramResponses);
    }

    @Override
    public ResponseEntity<?> getTrainingProgramForSuperAmin(String search, Integer trainingProgramCode, String name, String createdBy_name, Integer duration, String status, String sort) {
        List<TrainingProgram> trainingPrograms;

        if(search.isEmpty()){
            Specification<TrainingProgram> specification = specification(trainingProgramCode, name, createdBy_name, duration, status);

            trainingPrograms = trainingProgramRepository.findAll(specification, sort(sort));
        }else {
            Integer trainingProgramCode_;
            Integer duration_;
            if (search.matches("[0-9]+")){
                trainingProgramCode_ = Integer.parseInt(search);
                duration_ = Integer.parseInt(search);
            }
            else {
                trainingProgramCode_ = trainingProgramCode;
                duration_ = duration;
            }
            Specification<TrainingProgram> specification = specification(trainingProgramCode_, search, search, duration_, search);

            trainingPrograms = trainingProgramRepository.findAll(specification, sort(sort));

        }

        if(trainingPrograms.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found any training program!");
        }
        List<TrainingProgramResponse> trainingProgramResponses = trainingProgramMapper.toListResponse(trainingPrograms);
        return ResponseEntity.ok(trainingProgramResponses);
    }


    @Override
    public ResponseEntity<?> getTrainingProgramForUser(String search, Integer trainingProgramCode, String name, String createdBy_name, Integer duration, String sort) {
        String status_ = "Active";
        return getTrainingProgramForSuperAmin(search, trainingProgramCode, name, createdBy_name, duration, status_, sort);
    }

    @Override
    public ResponseEntity<?> createTrainingProgram(TrainingProgramCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Exception("Please fill all requirement!"));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        var trainingProgram = trainingProgramMapper.toCreateEntity(request);
        trainingProgram.setCreatedBy(currentUser);
        trainingProgram.setModifiedBy(currentUser);
        trainingProgram.setCreatedDate(new Date(System.currentTimeMillis()));
        trainingProgram.setModifiedDate(new Date(System.currentTimeMillis()));
        List<Class> classes = new ArrayList<>();
        if (request.getListTrainingProgramClass_ClassId() != null){
            for (Integer cl : request.getListTrainingProgramClass_ClassId()){
                classes.add(classRepository.findById(cl).orElse(null));
            }
        }

        trainingProgram.setClasses(classes);
        List<FileMaterial> fileMaterials = new ArrayList<>();
        if (request.getListFileMaterial_FileId() != null){
            for (Integer file : request.getListFileMaterial_FileId()){
                fileMaterials.add(fileMaterialRepository.findById(file).orElse(null));
            }
        }

        trainingProgram.setTrainingMaterials(fileMaterials);


        List<TrainingProgramSyllabus> trainingProgramSyllabusList = new ArrayList<>();
        if (request.getListTrainingProgramSyllabus_Syllabus_TopicCode() != null){
            for (Integer tr : request.getListTrainingProgramSyllabus_Syllabus_TopicCode()){
                TrainingProgramSyllabus trainingProgramSyllabus = trainingProgramSyllabusRepository
                        .findTrainingProgramSyllabusBySyllabus_TopicCode(tr).orElse(new TrainingProgramSyllabus());
                trainingProgramSyllabus.setTrainingProgram(trainingProgram);
                trainingProgramSyllabus.setSyllabus(syllabusService.getSyllabusByCode(tr));
                trainingProgramSyllabusList.add(trainingProgramSyllabus);
            }
        }

        trainingProgram.setTrainingProgramSyllabuses(trainingProgramSyllabusList);

        trainingProgramRepository.save(trainingProgram);
        trainingProgramSyllabusRepository.saveAll(trainingProgramSyllabusList);
        fileMaterialRepository.saveAll(fileMaterials);
        classRepository.saveAll(classes);


        return ResponseEntity.ok("Created training program successfully!");
    }
    @Override
    public ResponseEntity<?> updateTrainingProgramDetails(Integer id, TrainingProgramUpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Request has invalid field. Please try again later!");
        var trainingProgram = trainingProgramRepository.findById(id);
        if (trainingProgram.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found training program with ID: " + id);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        trainingProgram.get().setName(request.getName());
        trainingProgram.get().setGeneralInformation(request.getGeneralInformation());
        trainingProgram.get().setDuration(request.getEstimatedDuration());
        trainingProgram.get().setModifiedBy(currentUser);
        trainingProgram.get().setModifiedDate(new Date(System.currentTimeMillis()));

        List<TrainingProgramSyllabus> trainingProgramSyllabusList = new ArrayList<>();
        if (request.getListTrainingProgramSyllabus_Syllabus_TopicCode() != null){
            for (Integer tr : request.getListTrainingProgramSyllabus_Syllabus_TopicCode()){
                TrainingProgramSyllabus trainingProgramSyllabus = trainingProgramSyllabusRepository
                        .findTrainingProgramSyllabusBySyllabus_TopicCode(tr).orElse(new TrainingProgramSyllabus());
                trainingProgramSyllabus.setTrainingProgram(trainingProgram.get());
                trainingProgramSyllabus.setSyllabus(syllabusService.getSyllabusByCode(tr));
                trainingProgramSyllabusList.add(trainingProgramSyllabus);
            }
        }

        trainingProgram.get().setTrainingProgramSyllabuses(trainingProgramSyllabusList);
        trainingProgramRepository.save(trainingProgram.get());
        trainingProgramSyllabusRepository.saveAll(trainingProgramSyllabusList);

        return ResponseEntity.ok("Updated training program successfully with id: " + id);
    }

    @Override
    public ResponseEntity<?> duplicateTrainingProgram(Integer id) {
        var trainingProgramEntity = trainingProgramRepository.findById(id);
        if (trainingProgramEntity.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found training program with ID: " + id);
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        var trainingProgramDuplicateRequest = trainingProgramMapper.toCloneTrainingProgramEntity(trainingProgramEntity.get());
        var trainingProgram = trainingProgramMapper.toTrainingProgramEntity(trainingProgramDuplicateRequest);
        List<TrainingProgramSyllabus> trainingProgramSyllabusList = new ArrayList<>();
        for (TrainingProgramSyllabus tr : trainingProgram.getTrainingProgramSyllabuses()){
            TrainingProgramSyllabus trainingProgramSyllabus = new TrainingProgramSyllabus();
            trainingProgramSyllabus.setTrainingProgram(trainingProgram);
            trainingProgramSyllabus.setSyllabus(tr.getSyllabus());
            trainingProgramSyllabusList.add(trainingProgramSyllabus);
        }


        List<FileMaterial> fileMaterialList = new ArrayList<>();
        for (FileMaterial file : trainingProgram.getTrainingMaterials()){
            FileMaterial fileMaterial = trainingProgramMapper.toCloneFileMaterialEntity(file);
            fileMaterial.getTrainingPrograms().add(trainingProgram);
            fileMaterialRepository.save(fileMaterial);
            fileMaterialList.add(fileMaterial);
        }

        List<Class> classes = new ArrayList<>();
        for (Class class_ : trainingProgram.getClasses()){
            Class class__ = trainingProgramMapper.toCloneClassEntity(class_);
            class__.setTrainingProgram(trainingProgram);
            classes.add(class__);
        }

        trainingProgram.setCreatedBy(currentUser);
        trainingProgram.setModifiedBy(currentUser);
        trainingProgram.setCreatedDate(new Date(System.currentTimeMillis()));
        trainingProgram.setModifiedDate(new Date(System.currentTimeMillis()));
        trainingProgram.setStatus("Drafting");
        trainingProgram.setClasses(classes);
        trainingProgram.setTrainingProgramSyllabuses(trainingProgramSyllabusList);
        trainingProgram.setTrainingMaterials(fileMaterialList);

        trainingProgramRepository.save(trainingProgram);
        trainingProgramSyllabusRepository.saveAll(trainingProgramSyllabusList);
        fileMaterialRepository.saveAll(fileMaterialList);
        classRepository.saveAll(classes);
        return ResponseEntity.ok("Duplicated training program successfully!");
    }

    @Override
    public ResponseEntity<?> activeTrainingProgram(Integer id) {
        var trainingProgram = trainingProgramRepository.findById(id);
        if (trainingProgram.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found training program with ID: " + id);
        }
        if (trainingProgram.get().getStatus().equals("Active")){
            trainingProgram.get().setStatus("Inactive");
            trainingProgramRepository.save(trainingProgram.get());
            return ResponseEntity.ok("Inactive training program: " + trainingProgram.get().getTrainingProgramCode());
        }
        else {
            trainingProgram.get().setStatus("Active");
            trainingProgramRepository.save(trainingProgram.get());
            return ResponseEntity.ok("Activated training program: " + trainingProgram.get().getTrainingProgramCode());
        }
    }

    @Override
    public ResponseEntity<?> getTrainingProgramDetails(Integer id) {
        var trainingProgram = trainingProgramRepository.findById(id);
        if (trainingProgram.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found any detail in program!");
        }
        var trainingProgramDetail = trainingProgramMapper.toDetailResponse(trainingProgram.get());
        return ResponseEntity.ok(trainingProgramDetail);
    }

    private Specification<TrainingProgram> isTrainingProgramCode(Integer trainingProgramCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("trainingProgramCode"), trainingProgramCode);
    }
    private Specification<TrainingProgram> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
    private Specification<TrainingProgram> createdByContains(String createdBy) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("createdBy").get("name"), "%" + createdBy + "%");
    }
    private Specification<TrainingProgram> durationContains(String duration) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("duration"), duration);
    }

    private Specification<TrainingProgram> statusContains(String status) {
        return (root, query, criteriaBuilder) ->
               criteriaBuilder.like(root.get("status"), "%" + status + "%");
    }

    private Specification<TrainingProgram> specification(Integer trainingProgramCode, String name, String createdBy, Integer duration, String status){
        Specification<TrainingProgram> trainingProgramCodeSpec = isTrainingProgramCode(trainingProgramCode);
        Specification<TrainingProgram> nameSpec = nameContains(name);
        Specification<TrainingProgram> statusSpec = statusContains(status);
        Specification<TrainingProgram> createdBySpec = createdByContains(createdBy);
        Specification<TrainingProgram> durationSpec = durationContains(duration.toString());
        Specification<TrainingProgram> specification = Specification.where(null);

        if (trainingProgramCode.equals(duration) || (name.equals(status) && name.equals(createdBy))){
            if (trainingProgramCode.equals(duration) && trainingProgramCode.toString().matches("[0-9]+") && trainingProgramCode != 0){
                return Specification.where(trainingProgramCodeSpec.or(statusSpec));
            }
            if (name.equals(status) && name.equals(createdBy) && !name.isEmpty())
                return Specification.where(nameSpec.or(statusSpec).or(createdBySpec));
        }

        if (trainingProgramCode != 0){
            System.out.println(trainingProgramCode);
            specification = specification.and(trainingProgramCodeSpec);
        }
        if(!name.isEmpty()) {
            specification = specification.and(nameSpec);
        }
        if(!status.isEmpty()){
            specification = specification.and(statusSpec);
        }
        if(createdBy != null && !createdBy.isEmpty()){
            System.out.println(createdBy);
            specification = specification.and(createdBySpec);
        }
        if(duration != 0){
            specification = specification.and(durationSpec);
        }
        return specification;
    }


    private Sort sort(String sort){
        return switch (sort) {
            case "training_program_code_asc" ->  Sort.by("trainingProgramCode").ascending();
            case "training_program_code_desc" -> Sort.by("trainingProgramCode").descending();
            case "name_asc" -> Sort.by("name").ascending();
            case "name_desc" -> Sort.by("name").descending();
            case "created_by_asc" -> Sort.by("createdBy.name").ascending();
            case "created_by_desc" -> Sort.by("createdBy.name").descending();
            case "status_asc" -> Sort.by("status").ascending();
            case "status_desc" -> Sort.by("status").descending();
            case "duration_asc" -> Sort.by("duration").ascending();
            case "duration_desc" -> Sort.by("duration").descending();
            case "created_date_asc" -> Sort.by("createdDate").ascending();
            case "created_date_dsc" -> Sort.by("createdDate").descending();
            default -> Sort.by("createdBy.name").descending();
        };
    }

    @Override
    public TrainingProgram getTrainingProgramByCode(int code) {
        return trainingProgramRepository.findById(code).orElse(null);
    }

    @Override
    public ResponseEntity<?> deleteTrainingProgram(Integer id) {
        var trainingProgram = trainingProgramRepository.findById(id);
        if (trainingProgram.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found training program with ID: " + id);
        }
        trainingProgramRepository.delete(trainingProgram.get());
        return ResponseEntity.ok("Deleted training program successfully!");
    }

    @Override
    public ResponseEntity<?> addSyllabusToTrainingProgram(Integer id, Integer syllabusId) {
        try {
            var trainingProgramSyllabus = trainingProgramSyllabusRepository.findByTrainingProgramTrainingProgramCodeAndSyllabusTopicCode(id, syllabusId);
            if (trainingProgramSyllabus.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Existed syllabus in training program!");
            }
            TrainingProgramSyllabus trainingProgramSyllabus1 = new TrainingProgramSyllabus();
            trainingProgramSyllabus1.setSyllabus(syllabusService.getSyllabusByCode(syllabusId));
            trainingProgramSyllabus1.setTrainingProgram(trainingProgramRepository.findById(id).orElseThrow());
            trainingProgramSyllabusRepository.save(trainingProgramSyllabus1);
            return ResponseEntity.ok("Added syllabus have id: " + syllabusId + " to training program with id: " + id);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteSyllabusFromTrainingProgram(Integer id, Integer syllabusId) {
        try {
            var trainingProgramSyllabus = trainingProgramSyllabusRepository.findAllByTrainingProgram_TrainingProgramCodeAndSyllabus_TopicCode(id, syllabusId).orElseThrow();
            trainingProgramSyllabusRepository.deleteById(trainingProgramSyllabus.getId());
            return ResponseEntity.ok("Deleted syllabus have id: " + syllabusId + " in training program with id: " + id);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public List<Map<String, String>> uploadFile(Integer id, MultipartFile[] file, FileMaterialService fileMaterialService){
        List<Map<String, String>> list = new ArrayList<>();
        for (MultipartFile file_ : file) {
            String fileName = fileMaterialService.storeFile(file_);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/training_program/")
                    .path("/external/download/" + id + "/")
                    .path(fileName)
                    .toUriString();
            String contentType = file_.getContentType();
            Map<String, String> map = new HashMap<>();
            map.put("filename", fileName);
            map.put("fileDownloadUri", fileDownloadUri);
            map.put("contentType", contentType);
            map.put("id", id.toString());
            list.add(map);
        }
        return list;
    }
    @Override
    public ResponseEntity<?> uploadFileMaterialForTrainingProgram(Integer id, MultipartFile[] file) {
        var trainingProgram = trainingProgramRepository.findById(id);
        if (trainingProgram.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found training program with ID: " + id);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Date currentDate = new Date(System.currentTimeMillis());

        List<FileMaterial> fileMaterials = new ArrayList<>();
        List<Map<String, String>> map = uploadFile(id, file, fileMaterialService);
        for (Map<String, String> i : map){
            FileMaterial fileMaterial = new FileMaterial();
            fileMaterial.setName(i.get("filename"));
            fileMaterial.setUrl(i.get("fileDownloadUri"));
            fileMaterial.setCreatedBy(currentUser);
            fileMaterial.setCreatedDate(currentDate);
            fileMaterials.add(fileMaterial);
        }

        List<FileMaterial> trainingMaterials = trainingProgram.get().getTrainingMaterials();
        trainingMaterials.addAll(fileMaterials);
        trainingProgram.get().setTrainingMaterials(trainingMaterials);
        fileMaterialRepository.saveAll(fileMaterials);
        trainingProgramRepository.save(trainingProgram.get());
        return ResponseEntity.status(200).body("Added " + fileMaterials.size() + " file Material to Training Program id: " + id);
    }

    @Override
    public ResponseEntity<?> downloadFileMaterialForTrainingProgram(Integer id, String fileName, HttpServletRequest request) {
        var trainingProgram = trainingProgramRepository.findById(id);
        if (trainingProgram.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found training program with ID: " + id);
        }
        List<FileMaterial> trainingMaterials = trainingProgram.get().getTrainingMaterials();
        var fileMaterial = fileMaterialRepository.findAllByName(fileName);
        boolean flag = false;
        for (FileMaterial file : trainingMaterials){
            if (fileMaterial.isPresent() && file.getFileId() == fileMaterial.get().getFileId()){
                flag = true;
                break;
            }
        }
        //var fileMaterials = fileMaterialRepository.getFileMaterial(materialId, id);
        if (flag){
            Resource resource = fileMaterialService.loadFileAsResource(fileMaterial.get().getName());
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not determine file type.");
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
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Did not found any Material to download!");
    }

    @Override
    public ResponseEntity<?> deleteFileMaterialForTrainingProgram(Integer id, String fileName) {
        var trainingProgram = trainingProgramRepository.findById(id);
        if (trainingProgram.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found training program with ID: " + id);
        }
        var fileMaterial = fileMaterialRepository.findAllByName(fileName);

        if (fileMaterial.isPresent()){
            List<FileMaterial> trainingMaterials = trainingProgram.get().getTrainingMaterials();
            trainingMaterials.removeIf(file -> file.getFileId() == fileMaterial.get().getFileId());
            fileMaterialRepository.delete(fileMaterial.get());
            trainingProgram.get().setTrainingMaterials(trainingMaterials);
            trainingProgramRepository.save(trainingProgram.get());
            return ResponseEntity.ok("Successfully deleted file material id: " + fileMaterial.get().getFileId() + " and name: " + fileName);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found any file to delete!");
    }

    @Override
    public ResponseEntity<?> getFileMaterialForTrainingProgram(Integer id) {
        var trainingProgram = trainingProgramRepository.findById(id);
        if (trainingProgram.isEmpty()){
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Not found training program with ID: " + id);
        }
        var listFileMaterial = trainingProgramMapper.toListTrainingProgramFileMaterialDTOs(
                trainingProgram.get().getTrainingMaterials());
        return ResponseEntity.ok(listFileMaterial);
    }

    @Override
    public ResponseEntity<?> importTrainingProgram(MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Date currentDate = new Date(System.currentTimeMillis());
        if (checkExcelFormat(file)){
            List<TrainingProgram> trainingPrograms = toTrainingPrograms(file.getInputStream());
            for (TrainingProgram tr : trainingPrograms){
                tr.setModifiedDate(currentDate);
                tr.setCreatedDate(currentDate);
                tr.setCreatedBy(currentUser);
                tr.setModifiedBy(currentUser);
            }
            trainingProgramRepository.saveAll(trainingPrograms);
            return ResponseEntity.ok("Imported file to list training program!");
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload is not match format, please try again!");
    }


    public boolean checkExcelFormat(MultipartFile file){
        String contentType = file.getContentType();
        if (contentType == null) throw new AssertionError();
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
    public List<TrainingProgram> toTrainingPrograms(InputStream inputStream){
        List<TrainingProgram> trainingPrograms = new ArrayList<>();
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("training_program");
            int rowNumber = 0;

            for (Row row : sheet) {
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                TrainingProgram trainingProgram = new TrainingProgram();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid) {
                        case 0 -> trainingProgram.setTrainingProgramCode((int) cell.getNumericCellValue());
                        case 1 -> trainingProgram.setCreatedDate(cell.getDateCellValue());
                        case 2 -> trainingProgram.setDuration((int) cell.getNumericCellValue());
                        case 3 -> trainingProgram.setModifiedDate(cell.getDateCellValue());
                        case 4 -> trainingProgram.setName(cell.getStringCellValue());
                        case 5 -> trainingProgram.setStartTime(cell.getDateCellValue());
                        case 6 -> trainingProgram.setStatus(cell.getStringCellValue());
                        case 7 -> trainingProgram.setTopicCode((int) cell.getNumericCellValue());
                        case 8 -> trainingProgram.setGeneralInformation(cell.getStringCellValue());
                        default -> {
                        }
                    }
                    cid++;
                }
                if (trainingProgram.getDuration() == 0 && trainingProgram.getName().isEmpty()
                    && trainingProgram.getStatus().isEmpty()){
                    continue;
                }
                trainingPrograms.add(trainingProgram);
            }


        } catch (Exception e){
            throw new RuntimeException("Error when convert file csv!");
        }
        return trainingPrograms;
    }

    @Override
    public String approveTrainingProgram(int id){
        try {
            TrainingProgram trainingProgram = trainingProgramRepository.findById(id).orElse(null);
            if (trainingProgram == null){
                return "Không có TrainingProgram";
            }
            trainingProgram.setStatus("Active");
            trainingProgramRepository.save(trainingProgram);
            return "Phê duyệt thành công TrainingProgram id: "+id;
        }catch (Exception err){
            return "Lỗi khi phê duyệt: "+err;
        }
    }
    @Override
    public String rejectTrainingProgram(int id){
        try {
            TrainingProgram trainingProgram = trainingProgramRepository.findById(id).orElse(null);
            if (trainingProgram == null){
                return "Không có TrainingProgram";
            }
            trainingProgram.setStatus("Inactive");
            trainingProgramRepository.save(trainingProgram);
            return "Từ chối TrainingProgram id: "+id;
        }catch (Exception err){
            return "Lỗi khi từ chối: "+err;
        }
    }
}
