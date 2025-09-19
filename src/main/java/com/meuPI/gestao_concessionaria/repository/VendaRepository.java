package com.meuPI.gestao_concessionaria.repository;

import com.meuPI.gestao_concessionaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    // Métodos personalizados podem ser definidos aqui, se necessário 
}
