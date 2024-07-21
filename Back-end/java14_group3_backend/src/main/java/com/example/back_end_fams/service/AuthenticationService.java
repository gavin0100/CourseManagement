package com.example.back_end_fams.service;


import com.example.back_end_fams.authentication.JwtService;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.mapper.UserMapper;
import com.example.back_end_fams.model.request.AuthenticationRequest;
import com.example.back_end_fams.model.response.AuthenticateResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    @Autowired
    private UserMapper userMapper;

    public String authenticateJPA(AuthenticationRequest request){
        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    request.getPassword(),
                    userDetails.getAuthorities()
            );
            System.out.println(2);
            manager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
        }catch (AuthenticationException e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Invalid username/password supplied");
        }
        return "login success";

    }



    public AuthenticateResponse authenticate(AuthenticationRequest request, HttpServletRequest req){
        System.out.println("truy cap vao authenticate cua authenticationService");
        HttpSession session = req.getSession(true);
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            );
            System.out.println("token trong authenticationService: " + token);
            System.out.println("token trong authenticationService: " + token.getCredentials());
            manager.authenticate(token);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//            System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        }catch (AuthenticationException e){
//            System.out.println(e.getMessage());
            throw new RuntimeException("Invalid username/password supplied");
        }
        User user = userService.getUserByEmail(request.getEmail());
        String token = jwtService.generateToken(user);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        authenticateResponse.setAccessToken(token);
        authenticateResponse.setEmail(user.getEmail());
        authenticateResponse.setDob(user.getDob());
        authenticateResponse.setGender(user.getGender());
        authenticateResponse.setCreatedDate(user.getCreatedDate());
        authenticateResponse.setPhone(user.getPhone());
        authenticateResponse.setCreatedBy(userMapper.toResponse( user.getCreatedBy()));
        authenticateResponse.setName(user.getName());
        return authenticateResponse;
    }


}
