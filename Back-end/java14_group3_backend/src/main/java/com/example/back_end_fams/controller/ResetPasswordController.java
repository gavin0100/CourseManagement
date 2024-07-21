package com.example.back_end_fams.controller;

import com.example.back_end_fams.authentication.JwtService;
import com.example.back_end_fams.exception.SuccessMessage;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.service.EmailDetailsService;
import com.example.back_end_fams.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.example.back_end_fams.util.Utility;

@RestController
@RequiredArgsConstructor
public class ResetPasswordController {
    private final UserService userService;
    private final EmailDetailsService emailDetailsService;
    private final JwtService jwtService;


    @PostMapping("/send-mail")
    public ResponseEntity<?> processForgotPassword(@RequestBody String email, HttpServletRequest request){
        System.out.println("Email: " + email);
        try{
            User user = userService.getUserByEmail(email);
            if (user == null){
                throw new BadCredentialsException("Lỗi khi gửi mail");
            }
            String token = jwtService.generatePasswordResetToken(email);
            String resetPasswordLink = Utility.CLIENT_SITE_URL + "/user/resetPassword?email="+email+"&token=" + token;
            System.out.println(resetPasswordLink);
            emailDetailsService.sendMail(email, resetPasswordLink);
        } catch (UnsupportedEncodingException | MessagingException ex){
            System.out.println(ex.getMessage());
            throw new BadCredentialsException("Lỗi khi gửi mail");
        }
        var success = SuccessMessage.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Gửi mail thành công")
                .timestamp(new Date())
                .build();
        return ResponseEntity.ok(success);
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam(value ="token") String token,
                                           @RequestParam(value = "email") String email,
                                           @RequestBody String newPassword){
        System.out.println(token);
        System.out.println(email);
        System.out.println("password: " + newPassword);
        try{
            if(jwtService.isValidPasswordResetToken(token)){
                User user = userService.getByEmail(email);
                System.out.println("ten user: " + user.getName() + " " + user.getUserId());
                userService.updateUserPassword(user.getUserId(), newPassword);
            } else {
                System.out.println("token khong hop le!");
            }
        }
        catch (ExpiredJwtException ex){
            throw new BadCredentialsException("Token hết hạn, hãy gửi lại mail");
        }
        var success = SuccessMessage.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Đặt lại mật khẩu thành công")
                .timestamp(new Date())
                .build();
        return ResponseEntity.ok(success);
    }


}
