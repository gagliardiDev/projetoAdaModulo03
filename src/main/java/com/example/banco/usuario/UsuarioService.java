package com.example.banco.usuario;


import com.example.banco.cliente.ClienteRepository;
import com.example.banco.conta.Conta;
import com.example.banco.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;


    private UsuarioDTO convertDto(Usuario usuario) {
        return this.modelMapper.map(usuario, UsuarioDTO.class);
    }

    private Usuario convertFromDto(UsuarioDTO usuarioDto) {
        return this.modelMapper.map(usuarioDto, Usuario.class);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return this.usuarioRepository.findAll().stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDto) {
        var usuario = this.convertFromDto(usuarioDto);
        if (usuarioDto.getClienteId()!=null) {
            var cliente = clienteRepository.findById(usuarioDto.getClienteId()).orElseThrow();
            usuario.setCliente(cliente);
        }
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setActive(true);


        var savedUsuario = this.usuarioRepository.save(usuario);
        return this.convertDto(savedUsuario);





    }

    public Optional<UsuarioDTO> buscarPorCpf(String cpf) {
        return this.usuarioRepository.findByCpf(cpf).map(this::convertDto);
    }

    public void excluir(String cpf) {
        var usuario = this.usuarioRepository.findByCpf(cpf).orElseThrow();
        System.out.println(usuario);
        this.usuarioRepository.delete(usuario);
    }

    public UsuarioDTO atualizar(UsuarioDTO usuarioDto) {
        var usuario = this.usuarioRepository.findByCpf(usuarioDto.getCpf()).orElseThrow();
        usuario.setNome(usuarioDto.getNome());
        return this.convertDto(usuarioRepository.save(usuario));
    }
    public Usuario getByUsernameEntity(String username) {
        return this.usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NaoEncontradoException("Usuário não encontrado"));
    }

}
