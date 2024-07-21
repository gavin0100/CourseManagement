package com.example.back_end_fams.service;
import com.example.back_end_fams.authentication.JwtService;
import com.example.back_end_fams.exception.NotFoundException;

import com.example.back_end_fams.config.ConvertToDate;
import com.example.back_end_fams.model.entity.Class;
import com.example.back_end_fams.model.entity.EmailDetails;
import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.entity.UserPermission;
import com.example.back_end_fams.model.mapper.UserMapper;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.repository.EmailService;
import com.example.back_end_fams.repository.FileMaterialRepository;
import com.example.back_end_fams.repository.UserPermissionRepository;
import com.example.back_end_fams.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private FileMaterialRepository fileMaterialRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired private EmailService emailService;

    public List<User> findAll(){
//        createUserList();
        try {
            List<User> users = userRepository.findAll();
            return users;
        } catch (Exception ex) {
            throw ex;
        }
    }
    public void createUserList(){
        for (int i = 0; i <= 2; i++) {
            User newUser = new User();
            newUser.setName("vo van duc" + i);
            newUser.setEmail("voduc0100@gmail.com");
            newUser.setPhone("0869990186");
            newUser.setDob(new Date("02/11/2002"));
            newUser.setGender("Male");
            newUser.setUserPermission(userPermissionRepository.findById(1).orElse(null));
            newUser.setStatus(true);
            newUser.setCreatedBy(null);
            newUser.setCreatedDate(new Date());
            newUser.setModifiedBy(null);
            newUser.setModifiedDate(new Date());
            newUser.setPassword("duc2112002");
            userRepository.save(newUser);
        }

    }
    public String createUser(UserRequest userRequest){
        try {
            User oldUser = userRepository.findByEmail(userRequest.getEmail()).orElse(null);
            if(oldUser!=null)
            {
                return "Email đã tồn tại";
            }
            User user = new User();
            user = userMapper.toEntity(userRequest);
            LocalDateTime localDateTime  = LocalDateTime.now();
            Date createdDate = ConvertToDate.convertToDateViaSqlTimestamp(localDateTime);
            user.setCreatedDate(createdDate);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String password = RandomStringUtils.randomAlphanumeric(8);
            user.setPassword(password);
            user.setUserPermission(userPermissionRepository.findById(userRequest.getUserPermissionId()).orElse(null));
            user.setCreatedBy(userRepository.findById(userRequest.getCreatedByUserId()).orElse(null));
            userRepository.save(user);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setSubject("Thông báo: Tài khoản của bạn đã được tạo thành công");
            emailDetails.setRecipient(user.getEmail());
            emailDetails.setMsgBody("Chào "+userRequest.getName() +
                    ",\n Chúng tôi rất vui thông báo rằng tài khoản của bạn đã được tạo thành công tại Fresher Academy. Dưới đây là thông tin tài khoản của bạn:\n"
                    + "\n Tên đăng nhập: "+userRequest.getEmail()
                    + "\n Mật khẩu: "+password
                    + "\n\n Với tài khoản này, bạn có thể truy cập Fresher Academy và tận hưởng các dịch vụ và tính năng mà chúng tôi cung cấp."
                    + "\n\n Nếu bạn có bất kỳ câu hỏi hoặc cần hỗ trợ gì, xin đừng ngần ngại liên hệ với chúng tôi tại fa.java14.group3@gmail.com ."
                    + "\n\n Chúng tôi rất mong được phục vụ bạn và chúc bạn có trải nghiệm tuyệt vời với Fresher Academy."
                    + "\n\n Xin chân thành cảm ơn đã lựa chọn chúng tôi."
                    + "Trân trọng,\n" +
                    "Group3 of JAVA14 Fresher Academy");
            emailService.sendSimpleMail(emailDetails);
            return "Create user Successfully...";
        }catch (Exception e)
        {
            return "Error while creating user!!!";
        }
    }
    public String updatePermissonToUser(int id,UserRequest userRequest){
        try {

            User user = userRepository.findById(id).get();
            user.setUserPermission(userPermissionRepository.findById(userRequest.getUserPermissionId()).orElse(null));
            LocalDateTime localDateTime  = LocalDateTime.now();
            System.out.println(userRequest);
            Date updatedDate = ConvertToDate.convertToDateViaSqlTimestamp(localDateTime);
            user.setModifiedDate(updatedDate);
            user.setModifiedBy(userRepository.findById(userRequest.getModifiedByUserId()).orElse(null));
            userRepository.save(user);
            return "Update Permission User Successfully...";
        }catch (Exception e){
            return "Error while updating Permission User!!!";
        }
    }
    public String updateUser(int id,UserRequest userRequest){
        try{
            User oldUser = userRepository.findById(id).get();
            LocalDateTime localDateTime  = LocalDateTime.now();
            Date updatedDate = ConvertToDate.convertToDateViaSqlTimestamp(localDateTime);
            oldUser.setModifiedDate(updatedDate);
            oldUser.setUserPermission(userPermissionRepository.findById(userRequest.getUserPermissionId()).orElse(null));
            oldUser.setName(userRequest.getName());
            oldUser.setPhone(userRequest.getPhone());
            oldUser.setDob(userRequest.getDob());
            oldUser.setGender(userRequest.getGender());
            oldUser.setStatus(userRequest.isStatus());
            oldUser.setModifiedBy(userRepository.findById(userRequest.getModifiedByUserId()).orElse(null));
            userRepository.save(oldUser);
            return "Update user Successfully...";
        }catch (Exception e){
            return "Error while updating user!!!";
        }
    }
    public String deleteUser(int id){
        try {
            userRepository.deleteById(id);
            String result = "Delete user id: "+id+" Successfully";
            return result;
        }catch (Exception e){
            return "Error while deleting user!!!";
        }
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getUserByName(String name){
        return userRepository.findByName(name).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Email not found"));
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Không tìm email: "+ email));
    }

    public void updateUserPassword(int id, String password){
        User oldUser = getUserById(id);
        if(oldUser == null){
            throw new NotFoundException("Không tìm thấy người dùng: "+ id);
        }
//        oldUser.setPassword(new BCryptPasswordEncoder().encode(password));
        oldUser.setPassword(password);
        userRepository.save(oldUser);
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("Không tìm thấy người dùng: "+ id));
    }

    public User getCurrentUser(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token;
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            String email = jwtService.extractEmail(token);
            if(email!=null){
              return getByEmail(email);
            }else{
                throw new NotFoundException("Không tìm thấy email");
            }
        }
        return null;
    }


    public List<UserPermission> fillAllUserPermission(){
        try {
            return userPermissionRepository.findAll();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<UserPermission> updateUserPermission(List<UserPermission> userPermission){
        try{
            userPermissionRepository.saveAll(userPermission);
        }
        catch (Exception e){
            throw e;
        }
        return  userPermission;
    }

    private Pageable sort(String sort, int page, int size){
        return switch (sort) {
            case "user_name_asc" -> PageRequest.of(page, size, Sort.by("name").ascending());
            case "user_name_desc" -> PageRequest.of(page, size, Sort.by("name").descending());
            case "email_asc" -> PageRequest.of(page, size, Sort.by("email").ascending());
            case "email_desc" -> PageRequest.of(page, size, Sort.by("email").descending());
            case "dob_asc" -> PageRequest.of(page, size, Sort.by("dob").ascending());
            case "dob_desc" -> PageRequest.of(page, size, Sort.by("dob").descending());
            case "gender_asc" -> PageRequest.of(page, size, Sort.by("gender").ascending());
            case "gender_desc" -> PageRequest.of(page, size, Sort.by("gender").descending());
            case "permission_asc" -> PageRequest.of(page, size, Sort.by("userPermission.role").ascending());
            case "permission_desc" -> PageRequest.of(page, size, Sort.by("userPermission.role").descending());
            default -> PageRequest.of(page, size, Sort.by("userId").ascending());
        };
    }

    public String setStatusUser(int id){
        try{
            User oldUser = userRepository.findById(id).get();
            if(oldUser.isStatus()){
                oldUser.setStatus(false);
            }else {
                oldUser.setStatus(true);
            }
            userRepository.save(oldUser);
            return "Update Status User Successfully";
        }catch (Exception e){
            return "Error while setting status user!!!";
        }
    }

    public String updatePassword(UserRequest userRequest){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            User oldUser = userRepository.findById(currentUser.getUserId()).get();
            if(oldUser.getPassword().equals(userRequest.getOldPassword())){
                oldUser.setPassword(userRequest.getPassword());
                userRepository.save(oldUser);
                return "Update password Successfully";
            }else {
                return "Sai mật khẩu";
            }

        }catch (Exception e){
            return "Error while updating password!!!";
        }
    }







}
