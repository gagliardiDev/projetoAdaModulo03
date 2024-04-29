package com.example.banco.conta;


import com.example.banco.cliente.ClienteRepository;
import com.example.banco.cliente.ClienteService;
import com.example.banco.usuario.Usuario;
import com.example.banco.usuario.UsuarioRepository;
import com.example.banco.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    private final ClienteRepository clienteRepository;

    private final ModelMapper modelMapper;
    private final UsuarioService usuarioService;
    @Autowired
    private UserDetailsService userDetailsService;



    private ContaDTO convertDto(Conta conta) {
        return this.modelMapper.map(conta, ContaDTO.class);
    }

    private Conta convertFromDto(ContaDTO contaDto) {
        return this.modelMapper.map(contaDto, Conta.class);
    }

    public List<ContaDTO> listarContas() {
        return this.contaRepository.findAll().stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    public ContaDTO salvar(ContaDTO contaDto) {
        var cliente = clienteRepository.findById(contaDto.getClienteId()).orElseThrow();
        var conta = new Conta();
        conta.setCliente(cliente);
        conta.setSaldo(contaDto.getSaldo());
        var savedConta = this.contaRepository.save(conta);
        return this.convertDto(savedConta);
    }

    public Optional<ContaDTO> buscarPorId(Long id) {
        return this.contaRepository.findById(id).map(this::convertDto);
    }

    public void excluir(Long id) {
        var conta = this.contaRepository.findById(id).orElseThrow();
        this.contaRepository.delete(conta);
    }

    public ContaDTO atualizar(ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        conta.setSaldo(contaDto.getSaldo());
        return this.convertDto(contaRepository.save(conta));
    }

    public ContaDTO sacar(ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        var cliente = conta.getCliente();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Obter o nome de usuário do usuário autenticado
            String username = authentication.getName();

            // Carregar o usuário do banco de dados com base no nome de usuário
            Usuario usuario = usuarioService.getByUsernameEntity(username);

            // Verificar se o cliente associado à conta é o mesmo que o cliente associado ao usuário autenticado
            if (usuario.getCliente().getId().equals(cliente.getId())) {
                // Verificar se o saldo é suficiente para o saque
                if (conta.getSaldo().compareTo(contaDto.getValor()) >= 0 && conta.getSaldo()>0) {
                    conta.setSaldo(conta.getSaldo() - contaDto.getValor());
                    return this.convertDto(contaRepository.save(conta));
                } else {
                    throw new RuntimeException("Saldo insuficiente para efetuar o saque");
                }
            } else {
                throw new RuntimeException("Você não tem permissão para sacar dessa conta");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }
    }

    public ContaDTO transferir (ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        var contaDestino = this.contaRepository.findById(contaDto.getIdContaDestino()).orElseThrow();
        var cliente = conta.getCliente();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Obter o nome de usuário do usuário autenticado
            String username = authentication.getName();

            // Carregar o usuário do banco de dados com base no nome de usuário
            Usuario usuario = usuarioService.getByUsernameEntity(username);

            // Verificar se o cliente associado à conta é o mesmo que o cliente associado ao usuário autenticado
            if (usuario.getCliente().getId().equals(cliente.getId())) {
                // Verificar se o saldo é suficiente para o saque
                if (conta.getSaldo().compareTo(contaDto.getValor()) >= 0 && contaDto.getValor()>0) {
                    conta.setSaldo(conta.getSaldo() - contaDto.getValor());
                    contaDestino.setSaldo(contaDestino.getSaldo() + contaDto.getValor());
                    this.convertDto(contaRepository.save(conta));

                    return this.convertDto(contaRepository.save(contaDestino));
                } else {
                    throw new RuntimeException("Saldo insuficiente para efetuar o saque");
                }
            } else {
                throw new RuntimeException("Você não tem permissão para sacar dessa conta");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }
    }

    public ContaDTO depositar(ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        var cliente = conta.getCliente();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // Obter o nome de usuário do usuário autenticado
            String username = authentication.getName();

            // Carregar o usuário do banco de dados com base no nome de usuário
            Usuario usuario = usuarioService.getByUsernameEntity(username);

            // Verificar se o cliente associado à conta é o mesmo que o cliente associado ao usuário autenticado
            if (usuario.getCliente().getId().equals(cliente.getId())) {
                // Verificar se o saldo é suficiente para o saque
                if (contaDto.getValor()>0) {
                    conta.setSaldo(conta.getSaldo() + contaDto.getValor());
                    return this.convertDto(contaRepository.save(conta));
                } else {
                    throw new RuntimeException("Valor depositado menor ou igual a 0");
                }
            } else {
                throw new RuntimeException("Você não tem permissão para sacar dessa conta");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }
    }
}

