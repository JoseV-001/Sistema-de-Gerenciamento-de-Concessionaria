package com.meuPI.gestao_concessionaria.controller;

import com.meuPI.gestao_concessionaria.model.Carro;
import com.meuPI.gestao_concessionaria.model.Cliente;
import com.meuPI.gestao_concessionaria.model.Venda;
import com.meuPI.gestao_concessionaria.repository.CarroRepository;
import com.meuPI.gestao_concessionaria.repository.ClienteRepository;
import com.meuPI.gestao_concessionaria.repository.VendaRepository;
import com.meuPI.gestao_concessionaria.service.VendaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por gerenciar as requisições web e renderizar as páginas HTML (views) da aplicação.
 * Este controller lida com a interação do usuário através das telas do sistema.
 */
@Controller
@RequestMapping("/concessionaria")
public class ConcessionariaController {

    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;
    private final VendaRepository vendaRepository;
    private final VendaService vendaService;

    /**
     * Injeta as dependências necessárias (repositórios e serviços) através do construtor.
     * Esta é a forma recomendada de injeção de dependência no Spring (Constructor Injection).
     */
    public ConcessionariaController(CarroRepository carroRepository, ClienteRepository clienteRepository, VendaRepository vendaRepository, VendaService vendaService) {
        this.carroRepository = carroRepository;
        this.clienteRepository = clienteRepository;
        this.vendaRepository = vendaRepository;
        this.vendaService = vendaService;
    }

    /**
     * Mapeia a URL raiz ("/") e retorna o nome da view da página inicial.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Prepara os dados para a página de cadastro de veículos.
     */
    @GetMapping("/cadastrar-veiculos")
    public String cadastrarVeiculos(Model model) {
        List<Carro> carros = carroRepository.findAll();
        // Adiciona a lista de carros ao modelo para ser usada pelo Thymeleaf na renderização da tabela.
        model.addAttribute("carros", carros);
        return "cadastrar-veiculos";
    }

    /**
     * Processa o envio do formulário para salvar um novo veículo.
     */
    @PostMapping("/salvar-veiculo")
    public String salvarVeiculo(@ModelAttribute Carro carro) {
        carroRepository.save(carro);
        // Redireciona para a página de veículos para mostrar a lista atualizada.
        return "redirect:/concessionaria/cadastrar-veiculos";
    }

    /**
     * Processa a requisição para remover um veículo pelo ID.
     */
    @PostMapping("/remover-veiculo")
    public String removerVeiculo(@RequestParam("id") Long id) {
        carroRepository.deleteById(id);
        return "redirect:/concessionaria/cadastrar-veiculos";
    }

    /**
     * Prepara os dados para a página de cadastro de clientes.
     */
    @GetMapping("/cadastrar-clientes")
    public String cadastrarClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "cadastrar-clientes";
    }

    /**
     * Processa o envio do formulário para salvar um novo cliente.
     */
    @PostMapping("/salvar-cliente")
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/concessionaria/cadastrar-clientes";
    }

    /**
     * Processa a requisição para remover um cliente pelo ID.
     */
    @PostMapping("/remover-cliente")
    public String removerCliente(@RequestParam("id") Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/concessionaria/cadastrar-clientes";
    }
    
    /**
     * Prepara os dados para a página de cadastro de vendas.
     */
    @GetMapping("/cadastrar-vendas")
    public String cadastrarVendas(Model model) {
        // Busca apenas os carros que ainda não foram vendidos.
        List<Carro> carrosDisponiveis = carroRepository.findByVendidoFalse();
        // Busca todos os clientes para o menu de seleção.
        List<Cliente> clientes = clienteRepository.findAll();
        // Busca todas as vendas já realizadas para listar na tabela.
        List<Venda> vendas = vendaRepository.findAll();

        model.addAttribute("carros", carrosDisponiveis);
        model.addAttribute("clientes", clientes);
        model.addAttribute("vendas", vendas);
        return "cadastrar-vendas";
    }

    /**
     * Processa o formulário de nova venda, utilizando o VendaService para a lógica de negócio.
     */
    @PostMapping("/salvar-venda")
    public String salvarVenda(@RequestParam("carroId") Long carroId, @RequestParam("clienteId") Long clienteId) {
        vendaService.realizarVenda(carroId, clienteId);
        return "redirect:/concessionaria/cadastrar-vendas";
    }

    /**
     * Processa a requisição para cancelar uma venda, utilizando o VendaService.
     */
    @PostMapping("/remover-venda")
    public String removerVenda(@RequestParam("id") Long id) {
        vendaService.cancelarVenda(id);
        return "redirect:/concessionaria/cadastrar-vendas";
    }
}