package com.example.banco.cliente;
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
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public List<ClienteDTO> listarTodos() {
        return this.service.listarClientes().stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public ResponseEntity<ClienteDTO> buscarPorId (@PathVariable("id") Long id) {
        var autor = this.service.findById(id);
        return autor.map(clienteDTO -> ResponseEntity.ok(this.modelMapper.map(clienteDTO, ClienteDTO.class)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).ADMIN.name())")
    public ResponseEntity excluir (@PathVariable("id") Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.banco.usuario.Role).ADMIN.name())")
    public ResponseEntity<ClienteDTO> atualizar (@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        return new ResponseEntity<>(this.service.atualizar(id,dto), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(T(com.example.banco.usuario.Role).ADMIN.name(),T(com.example.banco.usuario.Role).FUNCIONARIO.name())")
    public ResponseEntity<ClienteDTO> inserir (@Valid @RequestBody ClienteDTO dto) {
        return new ResponseEntity<>(this.service.salvar(dto), HttpStatus.CREATED);
    }




}
