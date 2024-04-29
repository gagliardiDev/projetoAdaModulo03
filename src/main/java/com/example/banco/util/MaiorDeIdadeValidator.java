package com.example.banco.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class MaiorDeIdadeValidator implements ConstraintValidator<MaiorDeIdade, LocalDate> {


    @Override
    public void initialize(MaiorDeIdade constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

    }


    @Override
    public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
        if (dataNascimento == null) {
            return false;
        }
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dataNascimento, currentDate).getYears();

        // Check if age is greater than or equal to 18
        return age >= 18;
    }



}
