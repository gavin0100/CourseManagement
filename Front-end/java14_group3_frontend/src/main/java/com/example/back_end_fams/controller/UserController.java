package com.example.back_end_fams.controller;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.UI.UserRequestUI;
import com.example.back_end_fams.model.request.UserPermissionRequest;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.model.response.UserPermissionResponse;
import com.example.back_end_fams.model.response.UserResponse;
import com.example.back_end_fams.service.InputService;
import com.example.back_end_fams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private InputService inputService;

    @Autowired
    private TestController testController;



    private String message = null;
    private String errorMessage = null;

    @GetMapping("/getList")
    public String getAllUser(Model model){
        List<UserResponse> userResponses = userService.findAll();
        model.addAttribute("users", userResponses);
        return "user";
    }

    @GetMapping("/login")
    public String login(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "ui_login";
        }
        return "redirect:/home";
    }

    @PostMapping("/login")
    public String authenticate (@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model){
        if (jwtFilter.getAccessToken() != null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        String accessToken =  userService.authenticate(username, password);
        if(accessToken.equals("")){
            errorMessage = "Xác thực không thành công";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/user/login";
        }
        model.addAttribute("accessToken", accessToken);
        return "redirect:/home";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "ui_forgotPass";
        }
        message = null;
        errorMessage = null;
        return "redirect:/home";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, Model model){
        System.out.println("email: " + email);
        message = userService.forgotPassword(email);
        if(message.equals("")){
            message = null;
            errorMessage = "Gửi email không thành công";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "redirect:/user/forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("email") String email,
                                Model model){


        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            model.addAttribute("token", token);
            model.addAttribute("email", email);
            return "ui_resetPassword";
        }
        return "redirect:/home";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword( @RequestParam("email") String email,
                                        @RequestParam("token") String token,
                                        @RequestParam("password") String password,
                                        @RequestParam("repeatpassword") String repeatpassword,
                                        Model model){
        if (password == null|| repeatpassword == null ||
                !inputService.isValidInput(password) || !inputService.isValidInput(repeatpassword) ||
        password.length() < 8){
            errorMessage = "Không được để trống, mật khẩu dài hơn 8 ký tự, chứa chữ thường, chữ hoa, số, ký tự, @, !, () !";
            return "redirect:/user/login";
        }
        if(password.equals(repeatpassword)){
            System.out.println("Chuan bi gui");
            System.out.println(email);
            System.out.println(password);
            message = userService.resetPassword(token, email, password);
            if(message.equals("")){
                message = null;
                errorMessage = "Thay đổi mật khẩu không thành công";
                model.addAttribute("errorMessage", errorMessage);
            }
        }
        else {

            errorMessage = "Mật khẩu không khớp";
        }
        return "redirect:/user/login";
    }


    @GetMapping("/logout")
    public String logout(){
        userService.logout();
        return "redirect:/user/login";
    }
    @GetMapping("/viewUser")
    public String getViewUser(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/user/login";
        }
        List<UserResponse> userResponses = new ArrayList<>();
        Comparator<UserResponse> comparator = new Comparator<UserResponse>() {
            @Override
            public int compare(UserResponse o1, UserResponse o2) {
                return 0;
            }
        };
        try{
            userResponses = userService.findAll();
            comparator = Comparator.comparing(UserResponse::getModifiedDate, Comparator.nullsLast(Comparator.reverseOrder()));
            Collections.sort(userResponses, comparator);
        } catch (Exception ex){
            TestController.errorMassage = "Bạn không có quyền truy cập trang này!";
            return "redirect:/home";
        }


        // Sort the list using the comparator


        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        model.addAttribute("users", userResponses);
        return "ui_users";
    }
    @GetMapping("/create")
    public String create(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/user/login";
        }
        userService.addUser();
        return "redirect:/user/viewUser";
    }

    @GetMapping("/user_permission")
    public String getUserPermission(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/user/login";
        }
        List<UserPermissionResponse> userPermissionResponses = userService.findAllUserPermission();
        if (userPermissionResponses == null || userPermissionResponses.size() == 0){
            TestController.errorMassage = "Bạn không có quyền truy cập trang này!";
            return "redirect:/home";
        }
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;
        model.addAttribute("users", userPermissionResponses);
        return "ui_user_permission";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute UserRequestUI userRequestUI,
                          Model model){

        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/user/login";
        }

        if (    userRequestUI.getUserPermissionId() == -1 || userRequestUI.getName() == "" ||
                userRequestUI.getGender() == "" || userRequestUI.getPhone() == "" ||
                userRequestUI.getDob() == null || userRequestUI.getEmail() == "" ||
                !inputService.isValidInput(userRequestUI.getName()) ||
                !inputService.isValidInput(userRequestUI.getPhone()) ||
                !inputService.isValidInput(userRequestUI.getEmail())
        ) {
            errorMessage = "Dữ liệu đầu vào không hợp lệ! Không được để trống input, chỉ được chứa UTF-8, \\\"@\\\", \\\"(\\\", \\\")\\\", \\\",\\\", \\\".\\\", \\\"!\\\" và khoảng trắng";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/user/viewUser";
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setUserPermissionId(userRequestUI.getUserPermissionId());
        userRequest.setName(userRequestUI.getName());
        userRequest.setGender(userRequestUI.getGender());
        userRequest.setPhone(userRequestUI.getPhone());
        userRequest.setDob(userRequestUI.getDob());
        userRequest.setStatus(userRequestUI.isStatus());
        userRequest.setEmail(userRequestUI.getEmail());

        String addUser1 =  userService.addUser(userRequest);

        System.out.println("addUser result: " + addUser1);
        if(addUser1 != null){
            if (addUser1.equals("Email đã tồn tại")){
                errorMessage = "Thêm thất bại! Email đã tồn tại";
                model.addAttribute("errorMessage", errorMessage);
            }else {
                message = "Thêm user thành công!";
                model.addAttribute("message", message);
            }

        }else{
            errorMessage = "Thêm thất bại!";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "redirect:/user/viewUser";


    }
    @PostMapping("/viewUser_changeRole")
    public String changRoleUser(@ModelAttribute UserRequestUI userRequestUI,
                          Model model){

        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/user/login";
        }
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(userRequestUI.getUserId());
        userRequest.setUserPermissionId(userRequestUI.getUserPermissionId());
        userRequest.setModifiedByUserId(userRequestUI.getModifiedById());

        String addUser2 =  userService.changRoleUser(userRequest);

        if(addUser2 != null){
            message = "Cập nhật type thành công";
            model.addAttribute("message", message);
        }else {
            errorMessage = "Cập nhật type thất bại";
            model.addAttribute("errorMessage", errorMessage);
        }
        model.addAttribute("users", addUser2);
        return "redirect:/user/viewUser";

    }

    @PostMapping("/viewUserEdit")
    public String editUser(@ModelAttribute UserRequestUI userRequestUI,
                           Model model){

        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/user/login";
        }

        if (    userRequestUI.getUserPermissionId() == -1 || userRequestUI.getName() == "" ||
                userRequestUI.getGender() == "" || userRequestUI.getPhone() == "" ||
                userRequestUI.getDob() == null || userRequestUI.getEmail() == "" ||
                !inputService.isValidInput(userRequestUI.getName()) ||
                !inputService.isValidInput(userRequestUI.getPhone()) ||
                !inputService.isValidInput(userRequestUI.getEmail())
        ) {
            errorMessage = "Dữ liệu đầu vào không hợp lệ! Không được để trống input, chỉ được chứa UTF-8, \\\"@\\\", \\\"(\\\", \\\")\\\", \\\",\\\", \\\".\\\", \\\"!\\\" và khoảng trắng";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/user/viewUser";
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(userRequestUI.getUserId());
        userRequest.setUserPermissionId(userRequestUI.getUserPermissionId());
        userRequest.setName(userRequestUI.getName());
        userRequest.setGender(userRequestUI.getGender());
        userRequest.setPhone(userRequestUI.getPhone());
        userRequest.setDob(userRequestUI.getDob());
        userRequest.setStatus(userRequestUI.isStatus());
        userRequest.setEmail(userRequestUI.getEmail());

        String editUser =  userService.updateUser(userRequest);

        if(!editUser.equals("")){
            message = "Cập nhật thành công";
            model.addAttribute("message", message);
        } else {
            errorMessage = "Cập nhật thất bại";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "redirect:/user/viewUser";

    }

    @PostMapping ("/delete")
    public String deleteUser(@ModelAttribute UserRequestUI userRequestUI,
                           Model model){
        int userId = userRequestUI.getUserId();
        System.out.println(userId);

        String delete =  userService.deleteuser(userId);
        if(delete != null){
            message = "Xóa thành công";
            model.addAttribute("message", message);
        }else {
            errorMessage = "Xóa thất bại";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "redirect:/user/viewUser";

    }

    @PostMapping("/setstatus")
    public String setStatusUser(@ModelAttribute UserRequestUI userRequestUI,
                             Model model){

        int userId = userRequestUI.getUserId();
        String setstatus =  userService.setStatusUser(userId);

        if(setstatus != null){
            message = "Cập nhật status thành công";
            model.addAttribute("message", message);
        }else {
            errorMessage = "Cập nhật status thất bại";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "redirect:/user/viewUser";

    }

    @PostMapping("/permissionUpdate")
    public String updatePermission(@RequestBody List<UserPermissionRequest> userPermissionRequests, Model model){

        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/user/login";
        }
        List<UserPermissionResponse> userPermissionResponses = userService.updatePermission(userPermissionRequests);
        if(userPermissionResponses != null){
            message = "Cập nhật thành công";
            model.addAttribute("message", message);
        }else {
            errorMessage = "Cập nhật thất bại";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "redirect:/user/user_permission";

    }
    @GetMapping("/updatepassword")
    public String updatePassword(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "ui_login";
        }
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        message = null;
        errorMessage = null;

        return "ui_updatepassword";
    }
    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String password,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {

        if( !inputService.isValidPassword(oldPassword) || !inputService.isValidPassword(password) || !inputService.isValidPassword(confirmPassword)){
            errorMessage = "Mật khẩu yêu cầu: chữ số, chữ hoa, chữ thường và ký tự đặc biệt.\nVí dụ: Abc123@def";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/user/updatepassword";
        } else if(!password.equals(confirmPassword)){
            errorMessage = "Mật khẩu mới và Mật khẩu xác nhận không khớp";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/user/updatepassword";
        }

        String updatePassword = userService.updatePassword(oldPassword, password);

        if (updatePassword != null) {
            if("Sai mật khẩu".equals(updatePassword)){
                errorMessage = "Sai mật khẩu";
                model.addAttribute("errorMessage", errorMessage);
            }
            else {
                message = updatePassword;
                model.addAttribute("message", message);
            }
        }
        return "redirect:/user/updatepassword";
    }


}
