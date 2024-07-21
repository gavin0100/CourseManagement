package com.example.back_end_fams.service;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.request.ClassRequest;
import com.example.back_end_fams.model.request.TrainingProgramRequest;
import com.example.back_end_fams.model.response.ClassDateResponse;
import com.example.back_end_fams.model.response.ClassResponse;
import com.example.back_end_fams.model.response.TrainingProgramDetailResponse;
import com.example.back_end_fams.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class ClassService {
    @Autowired
    private RestTemplate restTemplate;

    private String apiClassList = "http://localhost:8080/api/class";

    private String apiCreateClass = "http://localhost:8080/api/class/create";

    private String apiUpdateClass = "http://localhost:8080/api/class/update/";

    private String apiDeleteClass = "http://localhost:8080/api/class/delete/";

    private String apiDuplicateClass = "http://localhost:8080/api/class/duplicate/";

    private String apiGetClassDate = "http://localhost:8080/api/class/get_class_date/";
    private String apiAddClassDate = "http://localhost:8080/api/class/add_class_date";

    private String apiGetAllClassDate = "http://localhost:8080/api/class/get_all_class_date";
    @Autowired
    private JwtFilter jwtFilter;

    public List<ClassResponse> findAll() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiClassList);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<ClassResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<ClassResponse> classResponses = responseEntity.getBody();
            if (classResponses != null) {
                return classResponses;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean createClass(String className, String status, String location,
                               Date startDate, Date endDate, int trainingProgramCode) {
        try {
            Map<String, String> params = new HashMap<>();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiCreateClass);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            ClassRequest classRequest = new ClassRequest();
            classRequest.setClassName(className);
            classRequest.setStatus(status);
            classRequest.setLocation(location);
            classRequest.setStartDate(startDate);
            classRequest.setEndDate(endDate);
            classRequest.setTrainingProgramCode(trainingProgramCode);

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(classRequest, headers);
            ResponseEntity<ClassResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            ClassResponse classResponse = responseEntity.getBody();
            if (classResponse != null && classResponse.getClassName() != null) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateClass( String className, String location, String status,
                                Date startDate, Date endDate, int trainingProgramCode,
                                int classId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiUpdateClass + classId;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            ClassRequest classRequest = new ClassRequest();
            classRequest.setClassName(className);
            classRequest.setStatus(status);
            classRequest.setLocation(location);
            classRequest.setStartDate(startDate);
            classRequest.setEndDate(endDate);
            classRequest.setTrainingProgramCode(trainingProgramCode);

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(classRequest, headers);
            ResponseEntity<ClassResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {
                    }
            );
            ClassResponse classResponse = responseEntity.getBody();
            if (classResponse != null && classResponse.getClassName() != null) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }
    public boolean deleteClass(int id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiDeleteClass + id;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.DELETE,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String deleteClassResponse = responseEntity.getBody();
            if (deleteClassResponse != null && deleteClassResponse.contains("Delete success")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean duplicateClass(int id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiDuplicateClass + id;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String trainingProgramDetailResponse = responseEntity.getBody();
            if (trainingProgramDetailResponse != null && trainingProgramDetailResponse.contains("Duplicated class successfully")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public ClassResponse findById(int id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiClassList + "/" + id;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ClassResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            ClassResponse classResponse = responseEntity.getBody();
            if (classResponse != null) {
                return classResponse;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<ClassDateResponse> findClassDateByClassId(int id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiGetClassDate  + id;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<ClassDateResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            List<ClassDateResponse> classDateResponses = responseEntity.getBody();
            if (classDateResponses != null) {
                return classDateResponses;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean addClassDateByClassId(List<Date> dateList, String partOfDate, int classId) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("partOfDate", partOfDate);
            params.put("classId", String.valueOf(classId));
//            String api = apiAddClassDate  + classId;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiAddClassDate);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(dateList, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String addClassDateResponse = responseEntity.getBody();
            if (addClassDateResponse != null && addClassDateResponse.contains("Them thanh cong")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<ClassDateResponse> getAllClassDate() {
        try {
            Map<String, String> params = new HashMap<>();
//            String api = apiAddClassDate  + classId;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiGetAllClassDate);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<ClassDateResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            List<ClassDateResponse> classDateResponseList = responseEntity.getBody();
            return classDateResponseList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<ClassDateResponse> getClassDateListByPartOfDay(List<ClassDateResponse> classDateResponseList,
                                                               String partOfDate,
                                                               String searchWord,
                                                               String status,
                                                               Date date,
                                                               String mode){
        List<ClassDateResponse> result = new ArrayList<>();
        if (mode.equals("") || mode.equals("Week")){
            Calendar specifiedCalendar = Calendar.getInstance();
            specifiedCalendar.setTime(date);
            int specifiedWeek = specifiedCalendar.get(Calendar.WEEK_OF_YEAR);
            classDateResponseList.forEach(classDateResponse -> {
                if (classDateResponse.getPartOfDate().equals(partOfDate)
                        && classDateResponse.getClassResponse().getClassName().contains(searchWord)
                        && classDateResponse.getClassResponse().getStatus().contains(status)){
                    Calendar dateCalendar = Calendar.getInstance();
                    dateCalendar.setTime(classDateResponse.getDay());
                    int dateWeek = dateCalendar.get(Calendar.WEEK_OF_YEAR);
                    if (specifiedWeek == dateWeek) {
                        result.add(classDateResponse);
                    }
                }
            });
        } else {
            classDateResponseList.forEach(classDateResponse -> {
                if (classDateResponse.getPartOfDate().equals(partOfDate)
                        && classDateResponse.getClassResponse().getClassName().contains(searchWord)
                        && classDateResponse.getClassResponse().getStatus().contains(status)
                        && (classDateResponse.getDay().compareTo(date) == 0) ){
                    result.add(classDateResponse);
                }
            });
        }



        return result;
    }
}
