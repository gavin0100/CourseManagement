package com.example.back_end_fams.service;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.response.FileMaterialResponse;
import com.example.back_end_fams.model.response.SyllabusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileMaterialService {
    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private RestTemplate restTemplate;
    private String apiUrl = "http://localhost:8080/api/syllabus/find_material_by_contentid/";
    private String apiDeleteMaterialContent = "http://localhost:8080/api/syllabus/delete_material_content/";
    public List<FileMaterialResponse> findMaterialByContentId(int contentId) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl + contentId);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<FileMaterialResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<FileMaterialResponse> fileMaterialResponseList = responseEntity.getBody();
            if (fileMaterialResponseList != null) {
                return fileMaterialResponseList;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean deleteMaterialByContentIdAndFileId(int fileId, int contentId) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiDeleteMaterialContent + contentId + "/"+ fileId);
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

            String responses = responseEntity.getBody();
            if (responses != null && responses.contains("Successfully deleted")) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            return false;
        }
    }
}
