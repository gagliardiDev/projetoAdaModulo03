package com.example.banco.usuario;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public List<UsuarioDTO> listarTodos() {
        return this.service.listarUsuarios().stream()
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{cpf}")
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public ResponseEntity excluir (@PathVariable("cpf") String cpf) {
        service.excluir(cpf);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cpf}")
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public ResponseEntity<UsuarioDTO> atualizar (@PathVariable("cpf") String cpf, @RequestBody UsuarioDTO dto) {
        dto.setCpf(cpf);
        return new ResponseEntity<>(this.service.atualizar(dto), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public ResponseEntity<UsuarioDTO> inserir (@Valid @RequestBody UsuarioDTO dto) {
        return new ResponseEntity<>(this.service.salvar(dto), HttpStatus.CREATED);
    }

}
