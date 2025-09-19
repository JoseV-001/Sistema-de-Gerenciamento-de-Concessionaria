package com.meuPI.gestao_concessionaria.controller;

import com.meuPI.gestao_concessionaria.model.Carro;
import com.meuPI.gestao_concessionaria.model.Cliente;
import com.meuPI.gestao_concessionaria.repository.CarroRepository;
import com.meuPI.gestao_concessionaria.repository.ClienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/concessionaria")
public class ConcessionariaController {

    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    public ConcessionariaController(CarroRepository carroRepository, ClienteRepository clienteRepository) {
        this.carroRepository = carroRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cadastrar-veiculos")
    public String cadastrarVeiculos(Model model) {
        List<Carro> carros = carroRepository.findAll();  // Obtém todos os veículos, sem filtrar por "vendido"
        model.addAttribute("carros", carros);
        return "cadastrar-veiculos";
    }

    @PostMapping("/salvar-veiculo")
    public String salvarVeiculo(@ModelAttribute Carro carro) {
        carroRepository.save(carro);
        return "redirect:/concessionaria/cadastrar-veiculos";
    }

    @PostMapping("/remover-veiculo")
    public String removerVeiculo(@RequestParam("id") Long id) {
        carroRepository.deleteById(id);
        return "redirect:/concessionaria/cadastrar-veiculos";
    }

    @GetMapping("/cadastrar-clientes")
    public String cadastrarClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "cadastrar-clientes";
    }

    @PostMapping("/salvar-cliente")
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/concessionaria/cadastrar-clientes";
    }

    @PostMapping("/remover-cliente")
    public String removerCliente(@RequestParam("id") Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/concessionaria/cadastrar-clientes";
    }

}
