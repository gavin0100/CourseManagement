package com.example.back_end_fams.service;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.User;

import com.example.back_end_fams.model.request.AuthenticationRequest;
import com.example.back_end_fams.model.request.UserPermissionRequest;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.model.response.AuthenticaResponse;
import com.example.back_end_fams.model.response.SuccessMessage;
import com.example.back_end_fams.model.response.UserPermissionResponse;
import com.example.back_end_fams.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    private String apiUrl = "http://localhost:8080/api/users";
    private String UserPermission = "http://localhost:8080/api/admin/user-permission";
    private String UserCreate = "http://localhost:8080/api/admin/user/create";
    private String deleteUser = "http://localhost:8080/api/admin/user/delete/";
    private String setStatus = "http://localhost:8080/api/admin/user/status/";
    private String UpdateUser = "http://localhost:8080/api/admin/user/update/";
    private String userUpdatePermission = "http://localhost:8080/api/admin/user/update-permission/";
    private String permissionUpdate = "http://localhost:8080/api/admin/user-permission/update";

    private String loginUrl = "http://localhost:8080/login";

    private String forgotPasswordUrl = "http://localhost:8080/send-mail";
    private String resetPasswordUrl = "http://localhost:8080/reset-password";
    private String updatePassword = "http://localhost:8080/api/users/updatePassword";

//    @Autowired
    @Autowired
    private JwtFilter jwtFilter;

    public List<UserResponse> findAll() {
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
            ResponseEntity<List<UserResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<UserResponse> userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }
    public List<UserPermissionResponse> findAllUserPermission() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UserPermission);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            // Create an HttpEntity with the headers
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<UserPermissionResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<UserPermissionResponse> userPermissionResponses = responseEntity.getBody();
            if (userPermissionResponses != null) {
                return userPermissionResponses;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public String addUser(UserRequest userRequest) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UserCreate);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token

            HttpEntity<?> entity = new HttpEntity<>(userRequest, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }
        } catch (Exception ex) {
            System.out.println("Them that bai!");
            return "";
        }
        return "";
    }

    public String updateUser(UserRequest userRequest) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = UpdateUser + userRequest.getUserId();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);

//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UpdateUser);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            headers.setContentType(MediaType.APPLICATION_JSON);


            HttpEntity<?> entity = new HttpEntity<>(userRequest, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }
        } catch (Exception ex) {
            System.out.println("Cập nhật User that bai!");
            return "";
        }
        return "";
    }

    public String deleteuser(int userId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = deleteUser + userId;
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
                    HttpMethod.DELETE,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }
        } catch (Exception ex) {
            System.out.println("Xóa User that bai!");
            return "";
        }
        return "";
    }

    public String setStatusUser(int userId) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = setStatus + userId;
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
            String userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }
        } catch (Exception ex) {
            System.out.println("Xóa User that bai!");
            return "";
        }
        return "";
    }

    public String changRoleUser(UserRequest userRequest) {
        try {
            Map<String, String> params = new HashMap<>();

            String api = userUpdatePermission + userRequest.getUserId();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token


            HttpEntity<?> entity = new HttpEntity<>(userRequest, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }

        } catch (Exception ex) {
            System.out.println("Cập nhật that bai!");
            return "";
        }
        return "";
    }

    public List<UserPermissionResponse> updatePermission(List<UserPermissionRequest> userPermissionRequests) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(permissionUpdate);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
            headers.setContentType(MediaType.APPLICATION_JSON);


            HttpEntity<?> entity = new HttpEntity<>(userPermissionRequests, headers);
            ResponseEntity<List<UserPermissionResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            List<UserPermissionResponse> userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }
        } catch (Exception ex) {
            System.out.println("Cập nhật that bai!");
            return null;
        }
        return null;
    }

    public String authenticate(String username, String password) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(loginUrl);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.setEmail(username);
            authenticationRequest.setPassword(password);


            HttpEntity<?> entity = new HttpEntity<>(authenticationRequest);
            ResponseEntity<AuthenticaResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            AuthenticaResponse authenticaResponse = responseEntity.getBody();
            if (authenticaResponse != null) {
                String token = "Bearer " + authenticaResponse.getAccessToken();
                jwtFilter.setAccessToken(token);
                jwtFilter.setAuthenticaResponse(authenticaResponse);
                return "Xac thuc thanh cong";
            }
        } catch (Exception ex) {
            return "";
        }
        return "";
    }

    public String forgotPassword(String email) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(forgotPasswordUrl);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            HttpEntity<?> entity = new HttpEntity<>(email);
            ResponseEntity<SuccessMessage> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            SuccessMessage successMessage = responseEntity.getBody();
            return successMessage.getMessage();
        } catch (Exception ex) {
//            System.out.println("Xac thuc that bai!");
            return "";
        }
    }

    public String resetPassword(String token, String email, String newPassword) {
        try {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("token", token);
            queryParams.put("email", email);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resetPasswordUrl);
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            // Create a request body with newPassword
//            Map<String, String> requestBody = new HashMap<>();
//            requestBody.put("newPassword", newPassword);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

//            HttpEntity<?> entity = new HttpEntity<>(requestBody, headers);
            HttpEntity<?> entity = new HttpEntity<>(newPassword, headers);
            ResponseEntity<SuccessMessage> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            SuccessMessage successMessage = responseEntity.getBody();
            return successMessage.getMessage();
        } catch (Exception ex) {
//            System.out.println("Xac thuc that bai!");
            return "";
        }
    }

    public void logout(){
        jwtFilter.setAccessToken(null);
    }
    public String addUser() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UserCreate);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token


            UserRequest userRequest = new UserRequest();
//            userRequest.setUserPermissionId(userPermissionId);
            userRequest.setName("thai");
            userRequest.setEmail("thai@gmail.com");
            userRequest.setPhone("051561651");
//            userRequest.setDob(dob);
            userRequest.setGender("Nam");
            userRequest.setStatus(true);
            userRequest.setUserPermissionId(3);


            HttpEntity<?> entity = new HttpEntity<>(userRequest, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }
        } catch (Exception ex) {
            return "";
        }
        return "";
    }
    public String updatePassword(String oldPassword, String password) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(updatePassword);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token

            UserRequest userRequest = new UserRequest();
            userRequest.setOldPassword(oldPassword);
            userRequest.setPassword(password);



            HttpEntity<?> entity = new HttpEntity<>(userRequest, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            String userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }
        } catch (Exception ex) {
            System.out.println("Fail change password!");
            return "";
        }
        return "";
    }

}
