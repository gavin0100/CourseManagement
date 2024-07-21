package com.example.back_end_fams.service;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.request.TrainingContentDTO;
import com.example.back_end_fams.model.request.TrainingContentRequest;
import com.example.back_end_fams.model.request.TrainingContentRequestEdit;
import com.example.back_end_fams.model.request.TrainingUnitRequestEdit;
import com.example.back_end_fams.model.response.FileMaterialResponse;
import com.example.back_end_fams.model.response.TrainingContentResponse;
import com.example.back_end_fams.model.response.TrainingUnitResponse;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainingContentService {
    private static final Logger logger = LoggerFactory.getLogger(SyllabusService.class);

    @Autowired
    private RestTemplate restTemplate;

    private String apiContent_Create = "http://localhost:8080/api/syllabus/";
    private String apiContent_Update = "http://localhost:8080/api/syllabus/edit1/trainingUnits/";

    //http://localhost:8080/api/syllabus/1/file/uploadNew
    private String apiAddMaterial = "http://localhost:8080/api/syllabus/";


    @Autowired
    private JwtFilter jwtFilter;

    public ResponseEntity<TrainingContentResponse> save(TrainingContentRequest content, int syllabusId, int unitId) {
        try {
            String apiFix = apiContent_Create + syllabusId + "/trainingContent/" + unitId;
            String accessToken = jwtFilter.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("AUTHORIZATION", accessToken);

            HttpEntity httpEntity = new HttpEntity<>(content, headers);

            ResponseEntity<TrainingContentResponse> responseEntity = restTemplate.exchange(
                    apiFix,
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );

            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    return ResponseEntity.status(ex.getStatusCode()).body(new TrainingContentResponse());
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TrainingContentResponse());
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TrainingContentResponse());
    }

    public ResponseEntity<TrainingContentResponse> edit(List<TrainingContentDTO>  content, int unitCode){
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiContent_Update + unitCode + "/content");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            logger.debug("Access Token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(content, headers);
            ResponseEntity<List<TrainingContentResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {
                    }
            );

            TrainingContentResponse firstResponse = responseEntity.getBody().isEmpty() ? null : responseEntity.getBody().get(0);
            return ResponseEntity.status(responseEntity.getStatusCode()).body(firstResponse);
        } catch (HttpClientErrorException ex) {
            // Xử lý ngoại lệ HTTP client
            HttpStatusCode statusCode = ex.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                return ResponseEntity.status(statusCode).body(null);
            }
        } catch (Exception ex) {
            // Xử lý các ngoại lệ khác (ngoại trừ HTTP client)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }



    public boolean addMaterialToContent(MultipartFile file, int contentId) {
        try {
            Map<String, String> params = new HashMap<>();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiAddMaterial + contentId + "/file/uploadNew");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }


            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token

            LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", file.getResource());

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            // Create an HttpEntity with the headers
//            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<FileMaterialResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    requestEntity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            FileMaterialResponse fileMaterialResponse = responseEntity.getBody();
            if (fileMaterialResponse != null
                    && fileMaterialResponse.getName() != null) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }
}
