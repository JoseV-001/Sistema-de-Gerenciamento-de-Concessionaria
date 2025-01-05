package com.meuPI.gestao_concessionaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // Marca a classe como uma entidade JPA
public class Carro {

    @Id // Marca o campo 'id' como chave primária
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private double preco;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
