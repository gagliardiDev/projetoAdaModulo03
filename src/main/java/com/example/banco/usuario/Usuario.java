package com.example.banco.usuario;

import com.example.banco.cliente.Cliente;
import com.example.banco.util.Pessoa;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Where(clause = "active = true")
public class Usuario extends Pessoa implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Column(unique = true)
    private String cpf;
    private String username;
    private String password;
    private String telefone;
    private String roles;
    private boolean active;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roleList = this.roles.split(",");
        return Arrays.stream(roleList).map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

    @PrePersist
    @PreUpdate
    public void preUpdate() {
        validateCpf();
        validateRole();
    }

    private void validateCpf() {
        if (cliente != null && !cliente.getCpf().equals(cpf)) {
            throw new IllegalArgumentException("O CPF do usuário deve ser igual ao CPF do cliente associado");
        }
    }

    private void validateRole() {
        if (this.roles.equals("CLIENTE")) {
            if (cliente == null) {
                throw new IllegalArgumentException("Um cliente deve ser associado a este usuário quando o papel (role) é CLIENTE.");
            }
        } else {
            if (cliente != null) {
                throw new IllegalArgumentException("Um cliente não deve ser associado a este usuário quando o papel (role) não é CLIENTE.");
            }
        }
    }

}
