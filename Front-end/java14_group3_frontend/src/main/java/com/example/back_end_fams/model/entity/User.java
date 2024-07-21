package com.example.back_end_fams.model.entity;

import com.fasterxml.jackson.annotation.*;
//import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.security.Permission;
import java.util.Date;
import java.util.List;

//@Setter
//@Getter
//@ToString
//@Entity
//@Table(name = "user")
//@NoArgsConstructor
//@AllArgsConstructor
public class User implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int userId;
//    @Column(name = "name")
//    private String name;
//    @Column(name = "email")
//    private String email;
//    @Column(name = "phone")
//    private String phone;
//    @Column(name = "dob")
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date dob;
//    @Column(name = "gender")
//    private String gender;
//    @ManyToOne
//    @JoinColumn(name = "user_permission_id")
//    @JsonInclude(JsonInclude.Include.NON_NULL) // Include non-null fields
//    private UserPermission userPermission;
//    //chỗ này mà sài LAZY là bên JPA nó không lấy data từ database lên kiểu thông thường
//    // mà nó sài kiểu proxy gì đó, cho dù sài @JsonIgore bên UserPermission cho property users
//    // cũng không có chặn được việc tạo JSON cho cái trường này đâu.
//    // Trường hợp này là đối với object là kiểu dữ liệu của một trường, không phải là list<object>!@#$%^
//    @Column(name = "status")
//    private boolean status;
//    @ManyToOne
//    @JoinColumn(name = "created_by_user_id")
//    private User createdBy;
//    @Column(name="created_date")
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createdDate;
//    @ManyToOne
//    @JoinColumn(name = "modified_by_user_id")
//    private User modifiedBy;
//    @Column(name="modified_date")
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date modifiedDate;
//    @Column(name="password", length = 50)
//    private String password;
//
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinTable(
//            name = "user_class",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "class_id")
//    )
//    private List<Class> classes;
//
//    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Class> createdClassList;
//    @OneToMany(mappedBy = "modifiedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Class> modifiedClassList;
//
//    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<TrainingProgram> createdTrainingProgramList;
//    @OneToMany(mappedBy = "modifiedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<TrainingProgram> modifiedTrainingProgramList;
//
//    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Syllabus> createdSyllabusList;
//    @OneToMany(mappedBy = "modifiedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Syllabus> modifiedSyllabusList;
//
//    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Class> createdFileMaterialList;
}
