package com.example.banco.util;

import com.example.banco.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
@Component
public class ClienteExistenteValidator implements ConstraintValidator<ClienteExistente, Long> {

    ClienteRepository clienteRepository;
    @Override
    public void initialize(ClienteExistente constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Autowired
    public ClienteExistenteValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @Override
    public boolean isValid(Long idCliente, ConstraintValidatorContext context) {

        return idCliente == null || clienteRepository.findById(idCliente).isPresent();


    }



}
