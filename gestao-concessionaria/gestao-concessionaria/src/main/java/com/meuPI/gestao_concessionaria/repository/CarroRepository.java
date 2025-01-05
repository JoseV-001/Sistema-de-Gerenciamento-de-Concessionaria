package com.meuPI.gestao_concessionaria.repository;

import com.meuPI.gestao_concessionaria.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {


}
