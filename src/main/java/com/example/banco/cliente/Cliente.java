package com.example.banco.cliente;

import com.example.banco.util.Pessoa;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Cliente extends Pessoa {

    @Column(unique = true)
    private String cpf;
    private LocalDate data_nascimento;
    private String status;


}
