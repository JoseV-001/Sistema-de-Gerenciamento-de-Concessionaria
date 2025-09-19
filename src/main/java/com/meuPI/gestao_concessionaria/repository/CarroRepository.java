package com.meuPI.gestao_concessionaria.repository;

import com.meuPI.gestao_concessionaria.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    // Método para buscar carros vendidos
    List<Carro> findByVendidoTrue();

    // Método para buscar carros não vendidos
    List<Carro> findByVendidoFalse();

    // Método para buscar carros por marca (exemplo)
    List<Carro> findByMarca(String marca);

    // Método para buscar carros por modelo (exemplo)
    List<Carro> findByModelo(String modelo);

    // Método para buscar carros por ano (exemplo)
    List<Carro> findByAno(int ano);

    // Se você precisar de um método para buscar por faixa de preço, pode adicionar:
    List<Carro> findByPrecoBetween(double precoMin, double precoMax);
}
