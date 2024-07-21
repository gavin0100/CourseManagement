package com.example.back_end_fams.service;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.request.TrainingProgramRequest;
import com.example.back_end_fams.model.response.TrainingProgramDetailResponse;
import com.example.back_end_fams.model.response.TrainingProgramFileMaterialDTO;
import com.example.back_end_fams.model.response.TrainingProgramResponse;
import com.example.back_end_fams.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainginProgramService {
    @Autowired
    private RestTemplate restTemplate;

    private String apiGetListTrainingProgram = "http://localhost:8080/api/training_program/all";

    private String apiTrainingProgramById = "http://localhost:8080/api/training_program/";

    private String apiUpdateTrainingProgram = "http://localhost:8080/api/training_program/";

    private String apiCreateTrainingProgram = "http://localhost:8080/api/training_program/create";
    private String apiDuplicateTrainingProgram = "http://localhost:8080/api/training_program/";

    private String apiImportCSV = "http://localhost:8080/api/training_program/import";
    @Autowired
    private JwtFilter jwtFilter;

    public List<TrainingProgramResponse> findAll() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiGetListTrainingProgram);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<TrainingProgramResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            List<TrainingProgramResponse> trainingProgramResponses = responseEntity.getBody();
            if (trainingProgramResponses != null) {
                return trainingProgramResponses;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public TrainingProgramDetailResponse findById(int id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiTrainingProgramById + id;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<TrainingProgramDetailResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            TrainingProgramDetailResponse trainingProgramDetailResponse = responseEntity.getBody();
            if (trainingProgramDetailResponse != null) {
                return trainingProgramDetailResponse;
            }

            return null;
        } catch (Exception ex) {
            apiTrainingProgramById = "http://localhost:8080/api/training_program/find/";
            return null;
        }
    }

    public boolean updateTrainingProgram(int id, String name, int duration, String generalInformation) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiUpdateTrainingProgram + id;
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            TrainingProgramRequest trainingProgramRequest = new TrainingProgramRequest();
            trainingProgramRequest.setName(name);
            trainingProgramRequest.setEstimatedDuration(duration);
            trainingProgramRequest.setGeneralInformation(generalInformation);

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(trainingProgramRequest, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String trainingProgramDetailResponse = responseEntity.getBody();
            if (trainingProgramDetailResponse != null && trainingProgramDetailResponse.contains("Updated training program successfully with")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean createTrainingProgram(String name, int duration, String status, String generalInformation) {
        try {
            Map<String, String> params = new HashMap<>();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiCreateTrainingProgram);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            TrainingProgramRequest trainingProgramRequest = new TrainingProgramRequest();
            trainingProgramRequest.setName(name);
            trainingProgramRequest.setEstimatedDuration(duration);
            trainingProgramRequest.setStatus(status);
            trainingProgramRequest.setGeneralInformation(generalInformation);

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(trainingProgramRequest, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String trainingProgramDetailResponse = responseEntity.getBody();
            if (trainingProgramDetailResponse != null && trainingProgramDetailResponse.contains("Created training program successfully")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean duplicateTrainingProgram(int id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiDuplicateTrainingProgram + id;
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
            if (trainingProgramDetailResponse != null && trainingProgramDetailResponse.contains("Duplicated training program successfully")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean activeTrainingProgram(int id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiDuplicateTrainingProgram + id + "/active";
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
            if (trainingProgramDetailResponse != null
                    && (trainingProgramDetailResponse.contains("Inactive training program") ||
                    trainingProgramDetailResponse.contains("Activated training program"))) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteTrainingProgram(int id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiDuplicateTrainingProgram + id;
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
            String trainingProgramDetailResponse = responseEntity.getBody();
            if (trainingProgramDetailResponse != null && trainingProgramDetailResponse.contains("Deleted training program successfully")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean addSyllabusIntoTrainingProgram(int trainingProgramId, int syllabusId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiTrainingProgramById + trainingProgramId + "/syllabus/"+ syllabusId;
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
            if (trainingProgramDetailResponse != null
                    && (trainingProgramDetailResponse.contains("Added syllabus have id") || trainingProgramDetailResponse.contains("Existed syllabus in training program") )) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteSyllabusIntoTrainingProgram(int trainingProgramId, int syllabusId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiTrainingProgramById + trainingProgramId + "/syllabus/"+ syllabusId;
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
            String trainingProgramDetailResponse = responseEntity.getBody();
            if (trainingProgramDetailResponse != null
                    && trainingProgramDetailResponse.contains("Deleted syllabus have id")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }
    public boolean addMaterial(MultipartFile[] files, int trainingProgramId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiTrainingProgramById + trainingProgramId + "/external/upload";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }


            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token

            LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            for (MultipartFile fileChild : files) {
                body.add("file", fileChild.getResource());
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
                    && trainingProgramDetailResponse.contains("file Material to Training Program id")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
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
                body.add("file", fileChild.getResource());
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
                    && trainingProgramDetailResponse.contains("Imported file to list training program")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<TrainingProgramFileMaterialDTO> getFileMaterialByTrainingProgramId(int trainingProgramId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiTrainingProgramById + trainingProgramId + "/external/view";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<TrainingProgramFileMaterialDTO>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            List<TrainingProgramFileMaterialDTO> trainingProgramFileMaterialDTOList = responseEntity.getBody();
            if (trainingProgramFileMaterialDTOList != null) {
                return trainingProgramFileMaterialDTOList;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean deleteMaterialFromTrainingProgram(int trainingProgramId, String fileName) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = apiTrainingProgramById + trainingProgramId + "/external/delete/" + fileName;
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
            String trainingProgramDetailResponse = responseEntity.getBody();
            if (trainingProgramDetailResponse != null
                    && trainingProgramDetailResponse.contains("Successfully deleted file material id")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }
}

