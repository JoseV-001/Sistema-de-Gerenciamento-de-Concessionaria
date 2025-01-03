package com.meuPI.gestao_concessionaria.controller;

import com.meuPI.gestao_concessionaria.model.Carro;
import com.meuPI.gestao_concessionaria.model.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/concessionaria")
public class ConcessionariaController {

    private List<Carro> carros = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    // Página inicial
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Página para cadastro de veículos
    @GetMapping("/cadastrar-veiculos")
    public String cadastrarVeiculos(Model model) {
        model.addAttribute("carros", carros);
        return "cadastrar-veiculos";
    }

    // Endpoint para salvar veiculo
    @PostMapping("/salvar-veiculo")
    public String salvarVeiculo(@ModelAttribute Carro carro) {
        carros.add(carro);
        return "redirect:/concessionaria/cadastrar-veiculos";
    }

    // Endpoint para remover veiculo
    @PostMapping("/remover-veiculo")
    public String removerVeiculo(@RequestParam("id") int id) {
        carros.removeIf(c -> c.getId() == id);
        return "redirect:/concessionaria/cadastrar-veiculos";
    }

    // Página para cadastro de clientes
    @GetMapping("/cadastrar-clientes")
    public String cadastrarClientes(Model model) {
        model.addAttribute("clientes", clientes);
        return "cadastrar-clientes";
    }

    // Endpoint para salvar cliente
    @PostMapping("/salvar-cliente")
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        clientes.add(cliente);
        return "redirect:/concessionaria/cadastrar-clientes";
    }

    // Endpoint para remover cliente
    @PostMapping("/remover-cliente")
    public String removerCliente(@RequestParam("id") int id) {
        clientes.removeIf(c -> c.getId() == id);
        return "redirect:/concessionaria/cadastrar-clientes";
    }
}
