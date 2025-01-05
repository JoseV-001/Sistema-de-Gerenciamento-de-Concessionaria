package com.meuPI.gestao_concessionaria.controller;

import com.meuPI.gestao_concessionaria.model.Cliente;
import com.meuPI.gestao_concessionaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Endpoint para pegar todos os clientes cadastrados
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    // Endpoint para salvar um cliente
    @PostMapping
    public Cliente salvarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Endpoint para excluir um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            clienteRepository.deleteById(id); // Exclui o cliente do banco de dados
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Cliente deletado com sucesso!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"success\": false, \"message\": \"Cliente não encontrado!\"}");
        }
    }

    // Método para atualizar o cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);

        if (clienteExistente.isPresent()) {
            Cliente clienteAtualizado = clienteExistente.get();
            
            // Atualiza os dados do cliente
            clienteAtualizado.setNome(cliente.getNome());
            clienteAtualizado.setCpf(cliente.getCpf());
            clienteAtualizado.setEmail(cliente.getEmail());
            clienteAtualizado.setTelefone(cliente.getTelefone());
            
            // Salva as atualizações no banco de dados
            clienteRepository.save(clienteAtualizado);
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Cliente atualizado com sucesso!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"success\": false, \"message\": \"Cliente não encontrado!\"}");
        }
    }
}
