package com.example.banco.usuario;

import com.example.banco.cliente.ClienteDTO;
import com.example.banco.util.ClienteExistente;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {
    private Long id;
    @NotBlank
    private String nome;
    @Email(message = "email invalido")
    private String email;
    @CPF(message = "CPF Inválido")
    private String cpf;
    private String senha;
    private String telefone;
    private String username;
    private String password;
    private String roles;
    @ClienteExistente
    @Embedded
    private Long clienteId;
    private ClienteDTO cliente;

}

