package com.example.banco.conta;

import javax.persistence.*;

import com.example.banco.cliente.Cliente;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Setter
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double saldo;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;


}
