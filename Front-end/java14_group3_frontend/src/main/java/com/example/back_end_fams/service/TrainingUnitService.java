package com.example.back_end_fams.service;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.request.TrainingUnitRequest;
import com.example.back_end_fams.model.request.TrainingUnitRequestEdit;
import com.example.back_end_fams.model.response.SyllabusResponse;
import com.example.back_end_fams.model.response.TrainingUnitResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TrainingUnitService {
    private static final Logger logger = LoggerFactory.getLogger(SyllabusService.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtFilter jwtFilter;

    private String apiUnit_Create = "http://localhost:8080/api/syllabus/outline/";
    private String apiUnit_Update = "http://localhost:8080/api/syllabus/edit/outline/";


    public ResponseEntity<TrainingUnitResponse> save(TrainingUnitRequest unit, int syllabusId) {
        try {
            String apiFix = apiUnit_Create + syllabusId + "/trainingUnit";
            String accessToken = jwtFilter.getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("AUTHORIZATION", accessToken);

            HttpEntity httpEntity = new HttpEntity<>(unit, headers);

            ResponseEntity<TrainingUnitResponse> responseEntity = restTemplate.exchange(
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
                    return ResponseEntity.status(ex.getStatusCode()).body(new TrainingUnitResponse());
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TrainingUnitResponse());
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TrainingUnitResponse());
    }

    public ResponseEntity<List<TrainingUnitResponse>> edit(List<TrainingUnitRequestEdit>  unit, int syllabusId){
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUnit_Update + syllabusId + "/trainingUnits");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            logger.debug("Access Token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(unit, headers);
            ResponseEntity<List<TrainingUnitResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    return ResponseEntity.status(ex.getStatusCode()).body(new ArrayList<>());
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }
}
