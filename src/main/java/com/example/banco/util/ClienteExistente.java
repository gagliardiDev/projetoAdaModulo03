package com.example.banco.util;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClienteExistenteValidator.class)
@Documented
public @interface ClienteExistente {
    String message() default "Cliente não encontrado.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}