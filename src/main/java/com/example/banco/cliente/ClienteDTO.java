package com.example.banco.cliente;

import com.example.banco.util.MaiorDeIdade;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClienteDTO implements Serializable {


    private Long id;
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;
    @Past(message = "A data de nascimento deve estar no passado")
    @NotNull(message = "A data de nascimento não pode ser nula")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @MaiorDeIdade
    private LocalDate data_nascimento;
    @CPF
    private String cpf;
    private String status;
}
