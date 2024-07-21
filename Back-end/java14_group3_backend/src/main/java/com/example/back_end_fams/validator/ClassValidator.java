package com.example.back_end_fams.validator;

import com.example.back_end_fams.model.request.ClassRequest;
import jakarta.validation.ConstraintViolation;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class ClassValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ClassRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClassRequest classRequest = (ClassRequest) target;

    }
}
