package com.example.banco.conta;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
@Validated
public class ContaController {
    private final ContaService service;

    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public List<ContaDTO> listarTodos() {
        return this.service.listarContas().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public ResponseEntity<ContaDTO>buscarPorId (@PathVariable("id") Long id) {
        var livro = this.service.buscarPorId(id);
        return livro.map(contaDTO -> ResponseEntity.ok(this.modelMapper.map(contaDTO, ContaDTO.class)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).ADMIN.name())")
    public ResponseEntity excluir (@PathVariable("id") Long id, Principal principal) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).ADMIN.name())")
    public ResponseEntity<ContaDTO> atualizar (@PathVariable("id") Long id, @RequestBody ContaDTO dto) {
        dto.setId(id);
        return new ResponseEntity<>(this.service.atualizar(dto), HttpStatus.OK);
    }

    @PutMapping("/sacar/{id}")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).CLIENTE.name())")
    public ResponseEntity<ContaDTO> sacar (@PathVariable("id") Long id, @RequestBody ContaDTO dto) {
        dto.setId(id);
        return new ResponseEntity<>(this.service.sacar(dto), HttpStatus.OK);
    }

    @PutMapping("/transferir/{id}")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).CLIENTE.name())")
    public ResponseEntity<ContaDTO> transferir (@PathVariable("id") Long id, @RequestBody ContaDTO dto) {
        dto.setId(id);
        return new ResponseEntity<>(this.service.transferir(dto), HttpStatus.OK);
    }

    @PutMapping("/depositar/{id}")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).CLIENTE.name())")
    public ResponseEntity<ContaDTO> depositar (@PathVariable("id") Long id, @RequestBody ContaDTO dto) {
        dto.setId(id);
        return new ResponseEntity<>(this.service.depositar(dto), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public ResponseEntity<ContaDTO> inserir (@Valid @RequestBody ContaDTO dto) {
        return new ResponseEntity<>(this.service.salvar(dto), HttpStatus.CREATED);
    }



}
