package com.example.back_end_fams.service;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.response.LearningObjectiveResponse;
import com.example.back_end_fams.model.response.SyllabusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LearningObjectiveService {
    private static final Logger logger = LoggerFactory.getLogger(LearningObjectiveService.class);

    @Autowired
    private RestTemplate restTemplate;

    private String apiLearningObjectiveList = "";
    private String apiLearningObjectiveByID = "";

    @Autowired
    private JwtFilter jwtFilter;

    public List<LearningObjectiveResponse> findAll() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiLearningObjectiveList + "/list");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            logger.debug("Access Token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<LearningObjectiveResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {
                    }
            );
            List<LearningObjectiveResponse> learningObjectiveResponse = responseEntity.getBody();
            if (learningObjectiveResponse != null) {
                logger.debug("learningObjective Response: {}", learningObjectiveResponse);
                return learningObjectiveResponse;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public LearningObjectiveResponse findById(int id){
        LearningObjectiveResponse learningObjectiveResponse = new LearningObjectiveResponse();
        return learningObjectiveResponse;
    }
}
