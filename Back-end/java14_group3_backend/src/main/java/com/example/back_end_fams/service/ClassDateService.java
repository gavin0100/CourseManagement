package com.example.back_end_fams.service;

import com.example.back_end_fams.exception.NotFoundException;
import com.example.back_end_fams.model.entity.ClassDate;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.repository.ClassDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ClassDateService {
    @Autowired
    private ClassDateRepository classDateRepository;

    @Autowired
    private ClassService classService;
    public List<ClassDate> findByClassId(int classId){
        return classDateRepository.findByClassId(classId);
    }

    public String addListClassDate(List<Date> dateList, String partOfDate, int classId){
        try{
            classDateRepository.deleteAllByClassId(classId);
            dateList.forEach(date -> {
                ClassDate classDate = new ClassDate();
                classDate.setDay(date);
                classDate.setPartOfDate(partOfDate);
                classDate.setClass_room(classService.findById(classId));
                classDateRepository.save(classDate);
            });
            return "Them thanh cong";
        }
        catch (Exception ex){
            return "Them that bai";
        }
    }

    public List<ClassDate> findAll(){
        return classDateRepository.findAll();
    }

}
