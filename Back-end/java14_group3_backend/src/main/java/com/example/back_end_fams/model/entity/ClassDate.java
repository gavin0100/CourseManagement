package com.example.back_end_fams.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "class_date")
@NoArgsConstructor
@AllArgsConstructor
public class ClassDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "part_of_date")
    private String partOfDate;
    @Column(name = "day")
    private Date day;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class class_room;
}
