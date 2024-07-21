package com.example.back_end_fams.service;

import com.example.back_end_fams.config.ConvertToDate;
import com.example.back_end_fams.model.entity.*;
import com.example.back_end_fams.model.importRequest.SyllabusImport;
import com.example.back_end_fams.model.importRequest.TrainingContentImport;
import com.example.back_end_fams.model.importRequest.TrainingUnitImport;
import com.example.back_end_fams.model.mapper.SyllabusMapper;

import com.example.back_end_fams.model.request.TrainingContentDTO;
import com.example.back_end_fams.model.response.SyllabusResponseV2;
import com.example.back_end_fams.model.response.TrainingUnitResponseV2;
import com.example.back_end_fams.repository.LearningObjectiveRepository;
import com.example.back_end_fams.repository.SyllabusRepository;
import com.example.back_end_fams.repository.TrainingContentRepository;
import com.example.back_end_fams.repository.TrainingUnitRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SyllabusService {
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private SyllabusMapper syllabusMapper;

    @Autowired
    private TrainingUnitRepository trainingUnitRepository;

    @Autowired
    private TrainingContentRepository trainingContentRepository;

    @Autowired
    private LearningObjectiveRepository learningObjectiveRepository;

    @Autowired
    private TrainingContentService trainingContentService;

    @Autowired
    private TrainingUnitService trainingUnitService;

    public List<Syllabus> findAll(){
        try {
            List<Syllabus> sylls = syllabusRepository.findAllSyl();
            return sylls;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<LearningObjective> findAllLearningObjective(){
        try{
            return learningObjectiveRepository.findAllLearningObjective();
        } catch (Exception ex){
            throw ex;
        }
    }

    public Syllabus saveGeneral(Syllabus syllabus){
        return syllabusRepository.save(syllabus);
    }
    public SyllabusResponseV2 getDetail(int syllabusId){
        Syllabus syllabus = syllabusRepository.findById(syllabusId).orElse(null);
        return syllabusMapper.toSyllabusDetailResponse(syllabus);
    }

    public Syllabus duplicate(int id){
        Syllabus syllabusold = syllabusRepository.findById(id).orElse(null);
        Syllabus syllabusNew = new Syllabus();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        LocalDateTime localDateTime  = LocalDateTime.now();
        Date createdDate = ConvertToDate.convertToDateViaSqlTimestamp(localDateTime);
        syllabusNew.setPublishStatus(syllabusold.getPublishStatus());
        syllabusNew.setTechnicalGroup(syllabusold.getTechnicalGroup());
        syllabusNew.setTopicName(syllabusold.getTopicName());
        syllabusNew.setTrainingAudience(syllabusold.getTrainingAudience());
        syllabusNew.setTrainingPrinciples(syllabusold.getTrainingPrinciples());
        syllabusNew.setVersion(syllabusold.getVersion());
        syllabusNew.setLevel(syllabusold.getLevel());
        syllabusNew.setDuration(syllabusold.getDuration());
        syllabusNew.setLearningObjective(syllabusold.getLearningObjective());
        syllabusNew.setCreatedDate(createdDate);
        syllabusNew.setModifiedDate(new Date());
        syllabusNew.setCreatedBy(currentUser);
        syllabusNew.setModifiedBy(currentUser);
        return syllabusRepository.save(syllabusNew);
    }
    public String approveSyllabus(int id){
        try {
            Syllabus syllabusNew = syllabusRepository.findById(id).orElse(null);
            syllabusNew.setPublishStatus("Active");
            syllabusNew.setModifiedDate(new Date());
            syllabusRepository.save(syllabusNew);
            return "Phê duyệt thành công Syllasbus id: "+id;
        }catch (Exception err){
            return "Lỗi khi phê duyệt: "+err;
        }

    }
    public String rejectSyllabus(int id){
        try {
            Syllabus syllabusNew = syllabusRepository.findById(id).orElse(null);
            syllabusNew.setPublishStatus("Inactive");
            syllabusNew.setModifiedDate(new Date());
            syllabusRepository.save(syllabusNew);
            return "Từ chối Syllasbus id: "+id;
        }catch (Exception err){
            return "Lỗi khi Từ chối: "+err;
        }

    }
    public Syllabus getSyllabusByCode(int code){
        return syllabusRepository.findById(code).orElse(null);
    }
    public Syllabus saveOutline(Syllabus syllabus){return syllabusRepository.save(syllabus);}
    public Syllabus saveOther(Syllabus syllabus){return syllabusRepository.save(syllabus);}

    public Syllabus editSyllabus(Syllabus syllabus){
        return syllabusRepository.save(syllabus);
    }
    public List<Syllabus> findByName(String name){
        return syllabusRepository.findSyllabusByTopicName(name);
    }

    public boolean checkExcelFormat(MultipartFile file){
        String contentType = file.getContentType();
        if (contentType == null) throw new AssertionError();
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
    public ResponseEntity<?> importSyllabus(MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Date currentDate = new Date(System.currentTimeMillis());
        if (checkExcelFormat(file)){
            Map<String, List<?>> result = toSyllabus(file.getInputStream());
            List<SyllabusImport> syllabusList = (List<SyllabusImport>) result.get("syllabus");
            List<TrainingUnitImport> trainingUnitList = (List<TrainingUnitImport>) result.get("training_unit");
            List<TrainingContentImport> trainingContentList = (List<TrainingContentImport>) result.get("training_content");


            // phai tao cac file import model boi vi doan import content
            // co phan set unit_code, cai nay khong co trong entity
            // chi can sai reposity.getById la toang ngay vi id bi dat tam bay

            syllabusList.forEach(syllabus -> {
                System.out.println("syllabus code: " + syllabus.getTopicCode());
                Syllabus syllabus1 = new Syllabus();
                syllabus1.setTopicName(syllabus.getTopicName());
                syllabus1.setLevel(syllabus.getLevel());
                syllabus1.setVersion(syllabus.getVersion());
                syllabus1.setPublishStatus(syllabus.getPublishStatus());
                syllabus1.setTrainingAudience(syllabus.getTrainingAudience());
                syllabus1.setTechnicalGroup(syllabus.getTechnicalGroup());
                syllabus1.setCreatedDate(currentDate);
                syllabus1.setModifiedDate(currentDate);
                syllabus1.setCourseObjective(syllabus.getTopicName());
                syllabus1.setTrainingPrinciples(syllabus.getTopicName());
                syllabus1.setCreatedBy(currentUser);
                syllabusRepository.save(syllabus1);
                System.out.println("da save syllabus");

                Iterator<TrainingUnitImport> iteratorTrainingUnitImport = trainingUnitList.iterator();
                while (iteratorTrainingUnitImport.hasNext()) {
                    TrainingUnitImport trainingUnit = iteratorTrainingUnitImport.next();
                    System.out.println("training unit: " + trainingUnit.getUnitCode());
                    if (trainingUnit.getTopic_code() == syllabus.getTopicCode()){
                        TrainingUnit trainingUnit1 = new TrainingUnit();
                        trainingUnit1.setUnitName(trainingUnit.getUnitName());
                        trainingUnit1.setUnit(trainingUnit.getUnit());
                        trainingUnit1.setDay(trainingUnit.getDay());
                        trainingUnit1.setNumberOfHours(trainingUnit.getNumberOfHours());
                        trainingUnit1.setSyllabus(syllabus1);
                        trainingUnitRepository.save(trainingUnit1);
                        System.out.println("da save training unit");

                        Iterator<TrainingContentImport> iteratorTrainingContentImport = trainingContentList.iterator();
                        while (iteratorTrainingContentImport.hasNext()) {
                            TrainingContentImport trainingContent = iteratorTrainingContentImport.next();

                            if (trainingContent.getUnit_code() == trainingUnit.getUnitCode()) {
                                TrainingContent newTrainingContent = new TrainingContent();
                                newTrainingContent.setContent(trainingContent.getContent());
                                newTrainingContent.setDeliveryType(trainingContent.getDeliveryType());
                                newTrainingContent.setDuration(trainingContent.getDuration());
                                newTrainingContent.setTrainingFormat(trainingContent.isTrainingFormat());
                                newTrainingContent.setNote(trainingContent.getNote());
                                newTrainingContent.setTrainingUnit(trainingUnit1);
                                trainingContentRepository.save(newTrainingContent);
                                System.out.println("da save training content");

                                // Remove the current element from the list
                                iteratorTrainingContentImport.remove();
                                System.out.println("da remove training content");
                            }
                        }
                        iteratorTrainingUnitImport.remove();
                        System.out.println("da remove training unit");
                    }

                }
            });


            return ResponseEntity.ok("Imported file to list syllabus!");
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload is not match format, please try again!");
    }

    public Map<String, List<?>> toSyllabus(InputStream inputStream){
        List<SyllabusImport> syllabusList = new ArrayList<>();
        List<TrainingUnitImport> trainingUnitList = new ArrayList<>();
        List<TrainingContentImport> trainingContentList = new ArrayList<>();
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("syllabus");
            int rowNumber = 0;
            for (Row row : sheet) {
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                SyllabusImport syllabusImport = new SyllabusImport();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid) {
                        case 0 -> syllabusImport.setTopicCode((int) cell.getNumericCellValue());
                        case 1 -> syllabusImport.setTopicName(cell.getStringCellValue());
                        case 2 -> syllabusImport.setLevel(cell.getStringCellValue());
                        case 3 -> syllabusImport.setVersion(cell.getStringCellValue());
                        case 4 -> syllabusImport.setPublishStatus(cell.getStringCellValue());
                        case 5 -> syllabusImport.setTrainingAudience((int) cell.getNumericCellValue());
                        case 6 -> syllabusImport.setTechnicalGroup(cell.getStringCellValue());
                        case 7 -> syllabusImport.setCreatedDate(cell.getDateCellValue());
                        case 8 -> syllabusImport.setCourseObjective(cell.getStringCellValue());
                        case 9 -> syllabusImport.setTrainingPrinciples(cell.getStringCellValue());
                        default -> {
                        }
                    }
                    cid++;
                }
                syllabusList.add(syllabusImport);
            }
            XSSFSheet sheet1 = workbook.getSheet("training_unit");
            int rowNumber1 = 0;
            for (Row row1 : sheet1) {
                if (rowNumber1 == 0) {
                    rowNumber1++;
                    continue;
                }
                Iterator<Cell> cells1 = row1.iterator();
                int cid1 = 0;
                TrainingUnitImport trainingUnitImport = new TrainingUnitImport();
                while (cells1.hasNext()) {
                    Cell cell1 = cells1.next();
                    switch (cid1) {
                        case 0 -> {trainingUnitImport.setUnitCode((int) cell1.getNumericCellValue());

//                            System.out.println(trainingUnitImport.getUnitCode());
                        }
                        case 1 -> trainingUnitImport.setUnitName(cell1.getStringCellValue());
                        case 2 -> trainingUnitImport.setUnit(cell1.getStringCellValue());
                        case 3 -> trainingUnitImport.setDay(cell1.getStringCellValue());
                        case 4 -> trainingUnitImport.setNumberOfHours((int) cell1.getNumericCellValue());
                        case 5 -> trainingUnitImport.setTopic_code((int) cell1.getNumericCellValue());
                        default -> {
                        }
                    }
                    cid1++;
                }
                trainingUnitList.add(trainingUnitImport);
            }

            XSSFSheet sheet2 = workbook.getSheet("training_content");
            int rowNumber2 = 0;

            for (Row row1 : sheet2) {
                if (rowNumber2 == 0) {
                    rowNumber2++;
                    continue;
                }
                Iterator<Cell> cells2 = row1.iterator();
                int cid1 = 0;

                TrainingContentImport trainingContentImport = new TrainingContentImport();
                while (cells2.hasNext()) {
                    Cell cell1 = cells2.next();
                    switch (cid1) {
                        case 0 -> trainingContentImport.setId((int) cell1.getNumericCellValue());
                        case 1 -> trainingContentImport.setContent(cell1.getStringCellValue());
                        case 2 -> trainingContentImport.setDeliveryType(TrainingContent.DeliveryTypeEnum.valueOf(cell1.getStringCellValue()));
                        case 3 -> trainingContentImport.setDuration((int) cell1.getNumericCellValue());
                        case 4 -> trainingContentImport.setTrainingFormat(cell1.getBooleanCellValue());
                        case 5 -> trainingContentImport.setNote(cell1.getStringCellValue());
                        case 6 -> trainingContentImport.setUnit_code((int) cell1.getNumericCellValue());
                        default -> {
                        }
                    }
                    cid1++;
                }
                trainingContentList.add(trainingContentImport);
            }


        } catch (Exception e){
            throw new RuntimeException("Error when convert file csv!" + e);
        }

        HashMap<String, List<?>> map = new HashMap<>();
        map.put("syllabus", syllabusList);
        map.put("training_unit", trainingUnitList);
        map.put("training_content", trainingContentList);
        return map;
    }

    public ResponseEntity<?> updateTrainingContentV2(int unitCode, List<TrainingContentDTO> trainingContentDTO) {

        TrainingUnit trainingUnit = trainingUnitRepository.findById(unitCode).orElseThrow();
        List<TrainingContent> trainingContents = new ArrayList<>();
        for (TrainingContentDTO trainingContentDTO_ : trainingContentDTO){
            TrainingContent trainingContent = new TrainingContent();
            trainingContent.setId(trainingContentDTO_.getId());
            trainingContent.setContent(trainingContentDTO_.getContent());
            trainingContent.setDeliveryType(trainingContentDTO_.getDeliveryType());
            trainingContent.setDuration(trainingContentDTO_.getDuration());
            trainingContent.setNote(trainingContentDTO_.getNote());
            trainingContent.setTrainingFormat(trainingContentDTO_.getTrainingFormat());
            LearningObjective learningObjective = learningObjectiveRepository.findById(trainingContentDTO_.getLearningObjectiveId()).orElse(null);
            trainingContent.setLearningObjective(learningObjective);
            trainingContent.setTrainingUnit(trainingUnit);
            trainingContents.add(trainingContent);
        }
        trainingContentRepository.saveAll(trainingContents);
        return ResponseEntity.ok(trainingContents);
    }



}
