package com.meuPI.gestao_concessionaria.service;

import com.meuPI.gestao_concessionaria.model.Carro;
import com.meuPI.gestao_concessionaria.model.Cliente;
import com.meuPI.gestao_concessionaria.model.Venda;
import com.meuPI.gestao_concessionaria.repository.CarroRepository;
import com.meuPI.gestao_concessionaria.repository.ClienteRepository;
import com.meuPI.gestao_concessionaria.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Camada de serviço que encapsula a lógica de negócio para operações de Venda.
 * Garante que operações complexas, como realizar ou cancelar uma venda, sejam
 * transacionais (ou tudo funciona, ou nada é alterado no banco de dados).
 */
@Service // Marca esta classe como um serviço do Spring
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private CarroRepository carroRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Executa a lógica de negócio para registrar uma nova venda. Garante que o
     * carro selecionado não tenha sido vendido anteriormente.
     */
    @Transactional
    public Venda realizarVenda(Long carroId, Long clienteId) {
        // Busca o carro e o cliente, ou lança um erro caso não existam.
        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado!"));

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
        // Regra de negócio: não permitir a venda de um carro já vendido.
        if (carro.isVendido()) {
            throw new IllegalStateException("Este carro já foi vendido!");
        }
        // Altera o estado do carro para vendido.
        carro.setVendido(true);
        carroRepository.save(carro);

        // Cria e salva o novo registro de venda.
        Venda venda = new Venda(carro, cliente);
        return vendaRepository.save(venda);
    }

    // --- MÉTODO ADICIONADO ---
    /**
     * Cancela uma venda existente. Esta operação é transacional: ou tudo
     * funciona, ou nada é alterado. 1. Encontra a venda. 2. Pega o carro
     * associado e o marca como NÃO vendido. 3. Salva o status do carro. 4.
     * Remove o registro da venda.
     *
     * @param vendaId O ID da venda a ser cancelada.
     */
    @Transactional
    public void cancelarVenda(Long vendaId) {
        // Busca a venda ou lança um erro se não encontrar
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda com ID " + vendaId + " não encontrada!"));

        Carro carro = venda.getCarro();

        // Verifica se existe um carro associado antes de tentar alterá-lo
        if (carro != null) {
            carro.setVendido(false); // Libera o carro para venda novamente
            carroRepository.save(carro);
        }

        vendaRepository.delete(venda); // Remove a venda
    }
}
