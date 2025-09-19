package com.meuPI.gestao_concessionaria.controller;

import com.meuPI.gestao_concessionaria.model.Cliente;
import com.meuPI.gestao_concessionaria.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller da API REST para gerenciar as operações de Clientes.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Busca e retorna uma lista de todos os clientes cadastrados.
     */
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Recebe os dados de um novo cliente, valida e o salva no banco.
     */
    @PostMapping
    public Cliente salvarCliente(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Exclui um cliente do banco de dados a partir do seu ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"message\": \"Cliente deletado com sucesso!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Cliente não encontrado!\"}");
        }
    }

    /**
     * Atualiza as informações de um cliente existente, localizado pelo ID.
     * Os dados recebidos também são validados antes de salvar.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @Valid @RequestBody Cliente detalhesCliente) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setNome(detalhesCliente.getNome());
                    clienteExistente.setCpf(detalhesCliente.getCpf());
                    clienteExistente.setCep(detalhesCliente.getCep());
                    clienteExistente.setEndereco(detalhesCliente.getEndereco());
                    clienteExistente.setEmail(detalhesCliente.getEmail());
                    clienteExistente.setTelefone(detalhesCliente.getTelefone());
                    
                    Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
                    return ResponseEntity.ok(clienteAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}