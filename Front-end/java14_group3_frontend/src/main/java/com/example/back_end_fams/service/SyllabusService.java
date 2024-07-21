package com.example.back_end_fams.service;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.request.SyllabusRequest;
import com.example.back_end_fams.model.response.LearningObjectiveResponse;
import com.example.back_end_fams.model.response.SyllabusResponse;
import com.example.back_end_fams.model.response.TrainingProgramDetailResponse;
import com.example.back_end_fams.model.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SyllabusService {
    @Autowired
    private RestTemplate restTemplate;
    private String apiImportCSV = "http://localhost:8080/api/syllabus/import";
    private static final Logger logger = LoggerFactory.getLogger(SyllabusService.class);
    @Autowired
    private JwtFilter jwtFilter;
    private String apiUrl = "http://localhost:8080/api/syllabus/list";
    private String apiSyllabus = "http://localhost:8080/api/syllabus";
    private String apiSyllabus_Create = "http://localhost:8080/api/syllabus/general";
    private String apiSyllabus_delete = "http://localhost:8080/api/syllabus/delete/";
    private String apiSyllabus_duplicate = "http://localhost:8080/api/syllabus/duplicate/";

    private String apiSyllabus_approve = "http://localhost:8080/api/syllabus/approve/";

    private String apiSyllabus_edit = "http://localhost:8080/api/syllabus/edit/general/";

    public List<SyllabusResponse> findAll() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<SyllabusResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<SyllabusResponse> syllabusResponses = responseEntity.getBody();
            if (syllabusResponses != null) {
                return syllabusResponses;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<LearningObjectiveResponse> findAllLearningObjective() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiSyllabus + "/find_all_learning_objective");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<LearningObjectiveResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<LearningObjectiveResponse> learningObjectiveResponseList = responseEntity.getBody();
            if (learningObjectiveResponseList != null) {
                return learningObjectiveResponseList;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<SyllabusResponse> findAllUi() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiSyllabus + "/list");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            logger.debug("Access Token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<SyllabusResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {
                    }
            );
            List<SyllabusResponse> syllabusResponses = responseEntity.getBody();
            if (syllabusResponses != null) {
                logger.debug("Syllabus Response: {}", syllabusResponses);
                return syllabusResponses;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }


    public ResponseEntity<SyllabusResponse> save(SyllabusRequest syllabus) {
        try {
            String accessToken = jwtFilter.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("AUTHORIZATION", accessToken);

            HttpEntity httpEntity = new HttpEntity<>(syllabus, headers);

            ResponseEntity<SyllabusResponse> responseEntity = restTemplate.exchange(
                    apiSyllabus_Create,
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    return ResponseEntity.status(ex.getStatusCode()).body(new SyllabusResponse());
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SyllabusResponse());
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SyllabusResponse());
    }
    public boolean importFile(MultipartFile[] files) {
        try {
            Map<String, String> params = new HashMap<>();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiImportCSV);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }


            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token

            LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            for (MultipartFile fileChild : files) {
                body.add("syllabus", fileChild.getResource());
            }

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            // Create an HttpEntity with the headers
//            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    requestEntity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String trainingProgramDetailResponse = responseEntity.getBody();
            if (trainingProgramDetailResponse != null
                    && trainingProgramDetailResponse.contains("Imported file to list syllabus")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }
    public String deleteSyllabus(int userId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiSyllabus_delete + userId;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token




            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String syllabusResponses = responseEntity.getBody();
            if (syllabusResponses != null) {
                return syllabusResponses;
            }
        } catch (Exception ex) {
            return "";
        }
        return "";
    }
    public boolean duplicateSyllabus(int userId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiSyllabus_duplicate + userId;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token




            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String syllabusResponses = responseEntity.getBody();
            if (syllabusResponses != null) {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    public String approveSyllabus(int userId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiSyllabus_approve + userId;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token




            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String syllabusResponses = responseEntity.getBody();
            if (syllabusResponses != null) {
                return syllabusResponses;
            }
        } catch (Exception ex) {
            System.out.println("Xóa User that bai!");
            return "";
        }
        return "";
    }

    public ResponseEntity<SyllabusResponse> detail(int syllabusId){
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiSyllabus + "/detail2/" + syllabusId);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            logger.debug("Access Token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<SyllabusResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {
                    }
            );

            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    return ResponseEntity.status(ex.getStatusCode()).body(new SyllabusResponse());
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SyllabusResponse());
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SyllabusResponse());
    }

    public ResponseEntity<SyllabusResponse> edit(SyllabusRequest request, int syllabusId){
        try {
            String accessToken = jwtFilter.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("AUTHORIZATION", accessToken);

            HttpEntity httpEntity = new HttpEntity<>(request, headers);

            ResponseEntity<SyllabusResponse> responseEntity = restTemplate.exchange(
                    apiSyllabus_edit + syllabusId,
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    return ResponseEntity.status(ex.getStatusCode()).body(new SyllabusResponse());
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SyllabusResponse());
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SyllabusResponse());
    }
}
