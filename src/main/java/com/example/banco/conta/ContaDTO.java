package com.example.banco.conta;
import com.example.banco.cliente.ClienteDTO;
import com.example.banco.util.ClienteExistente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContaDTO {

    private Long id;
    @NotNull(message = "Saldo não pode ser nulo")
    @Positive(message = "Saldo precisa ser um valor acima de 0")
    private Double saldo;
    @NotNull
    @ClienteExistente
    private Long clienteId;
    private ClienteDTO cliente;
    @Positive(message = "Valor precisa ser um valor acima de 0")
    private Double valor;
    private Long idContaDestino;


}
