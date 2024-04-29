package com.example.banco.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaiorDeIdadeValidator.class)
@Documented
public @interface MaiorDeIdade {
    String message() default "Cliente deve ter 18 anos ou mais";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
