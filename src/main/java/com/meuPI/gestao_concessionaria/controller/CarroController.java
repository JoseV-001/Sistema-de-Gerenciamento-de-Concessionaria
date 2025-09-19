package com.meuPI.gestao_concessionaria.controller;

import com.meuPI.gestao_concessionaria.model.Carro;
import com.meuPI.gestao_concessionaria.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/veiculos")
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;

    // Endpoint para pegar todos os veículos cadastrados
    @GetMapping
    public List<Carro> getAllVeiculos() {
        return carroRepository.findAll();
    }

    // Endpoint para salvar um veículo
    @PostMapping
    public Carro salvarVeiculo(@RequestBody Carro carro) {
        return carroRepository.save(carro);
    }

    // Endpoint para excluir um veículo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarro(@PathVariable Long id) {
        Optional<Carro> carroOptional = carroRepository.findById(id);
        if (carroOptional.isPresent()) {
            carroRepository.deleteById(id); // Exclui o carro do banco de dados
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Carro deletado com sucesso!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"success\": false, \"message\": \"Carro não encontrado!\"}");
        }
    }

    // Método para atualizar o veículo
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVeiculo(@PathVariable Long id, @RequestBody Carro carro) {
        Optional<Carro> carroExistente = carroRepository.findById(id);

        if (carroExistente.isPresent()) {
            Carro carroAtualizado = carroExistente.get();
            
            // Atualiza os dados do veículo
            carroAtualizado.setMarca(carro.getMarca());
            carroAtualizado.setModelo(carro.getModelo());
            carroAtualizado.setAno(carro.getAno());
            carroAtualizado.setPreco(carro.getPreco());
            
            // Salva as atualizações no banco de dados
            carroRepository.save(carroAtualizado);
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"Veículo atualizado com sucesso!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"success\": false, \"message\": \"Veículo não encontrado!\"}");
        }
    }
    
    
    public Carro buscarCarroPorId(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro com ID " + id + " não encontrado"));
    }
}
