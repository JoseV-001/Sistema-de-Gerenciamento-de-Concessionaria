package com.meuPI.gestao_concessionaria.controller;

import com.meuPI.gestao_concessionaria.model.Venda;
import com.meuPI.gestao_concessionaria.repository.VendaRepository;
import com.meuPI.gestao_concessionaria.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller da API REST para gerenciar as operações de Vendas.
 */
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    // Serviço que contém a lógica de negócio para criar e cancelar vendas.
    @Autowired
    private VendaService vendaService;

    // Repositório para operações simples de busca, como listar todas as vendas.
    @Autowired
    private VendaRepository vendaRepository;

    /**
     * Cria um novo registro de venda associando um carro a um cliente.
     * Utiliza o VendaService para encapsular a lógica de negócio e garantir a transação.
     *
     * @param carroId O ID do carro a ser vendido.
     * @param clienteId O ID do cliente que está comprando.
     * @return Um ResponseEntity com a Venda criada em caso de sucesso, ou uma mensagem de erro.
     */
    @PostMapping("/{carroId}/{clienteId}")
    public ResponseEntity<?> venderCarro(@PathVariable Long carroId, @PathVariable Long clienteId) {
        try {
            // Delega a lógica complexa para a camada de serviço.
            Venda novaVenda = vendaService.realizarVenda(carroId, clienteId);
            // Retorna o objeto da nova venda com status 200 OK.
            return ResponseEntity.ok(novaVenda);
        } catch (RuntimeException e) {
            // Em caso de erro (ex: carro já vendido), retorna uma mensagem com status 400 Bad Request.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Busca e retorna uma lista de todas as vendas registradas no sistema.
     */
    @GetMapping
    public List<Venda> getAllVendas() {
        return vendaRepository.findAll();
    }
}