package com.meuPI.gestao_concessionaria.controller;

import com.meuPI.gestao_concessionaria.model.Carro;
import com.meuPI.gestao_concessionaria.model.Cliente;
import com.meuPI.gestao_concessionaria.model.Venda;
import com.meuPI.gestao_concessionaria.repository.CarroRepository;
import com.meuPI.gestao_concessionaria.repository.ClienteRepository;
import com.meuPI.gestao_concessionaria.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller da API REST para gerenciar as operações de Carros.
 */
@RestController
@RequestMapping("/api/veiculos")
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VendaRepository vendaRepository;

    /**
     * Busca e retorna uma lista de todos os carros cadastrados.
     */
    @GetMapping
    public List<Carro> getAllVeiculos() {
        return carroRepository.findAll();
    }

    /**
     * Recebe os dados de um novo carro e o salva no banco.
     */
    @PostMapping
    public Carro salvarVeiculo(@RequestBody Carro carro) {
        return carroRepository.save(carro);
    }

    /**
     * Exclui um carro do banco de dados a partir do seu ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarro(@PathVariable Long id) {
        Optional<Carro> carroOptional = carroRepository.findById(id);
        if (carroOptional.isPresent()) {
            carroRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Carro deletado com sucesso!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"success\": false, \"message\": \"Carro não encontrado!\"}");
        }
    }

    /**
     * Atualiza as informações de um carro existente, localizado pelo ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVeiculo(@PathVariable Long id, @RequestBody Carro carro) {
        Optional<Carro> carroExistente = carroRepository.findById(id);

        if (carroExistente.isPresent()) {
            Carro carroAtualizado = carroExistente.get();
            carroAtualizado.setMarca(carro.getMarca());
            carroAtualizado.setModelo(carro.getModelo());
            carroAtualizado.setAno(carro.getAno());
            carroAtualizado.setPreco(carro.getPreco());

            carroRepository.save(carroAtualizado);
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Veículo atualizado com sucesso!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"success\": false, \"message\": \"Veículo não encontrado!\"}");
        }
    }

    /**
     * Associa um carro a um cliente, registrando uma venda e marcando o carro como vendido.
     */
    @PutMapping("/{id}/vender/{clienteId}")
    public ResponseEntity<?> venderCarro(@PathVariable Long id, @PathVariable Long clienteId) {
        Optional<Carro> carroOptional = carroRepository.findById(id);
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);

        if (carroOptional.isPresent() && clienteOptional.isPresent()) {
            Carro carro = carroOptional.get();
            Cliente cliente = clienteOptional.get();

            if (carro.isVendido()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{\"success\": false, \"message\": \"Carro já foi vendido!\"}");
            }

            Venda venda = new Venda();
            venda.setCarro(carro);
            venda.setCliente(cliente);
            vendaRepository.save(venda);

            carro.setVendido(true);
            carroRepository.save(carro);

            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Venda registrada com sucesso!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"success\": false, \"message\": \"Carro ou Cliente não encontrado!\"}");
        }
    }

    /**
     * Busca e retorna uma lista apenas dos carros marcados como vendidos.
     */
    @GetMapping("/vendidos")
    public List<Carro> getCarrosVendidos() {
        return carroRepository.findByVendidoTrue();
    }

    /**
     * Método público para buscar um carro específico pelo ID.
     */
    public Carro buscarCarroPorId(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro com ID " + id + " não encontrado"));
    }
}