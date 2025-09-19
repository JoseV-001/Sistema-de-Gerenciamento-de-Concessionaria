package com.meuPI.gestao_concessionaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;

/**
 * Representa a entidade Venda, que formaliza a transação de um Carro para um Cliente.
 * Esta classe serve como uma tabela de junção no banco de dados, armazenando também
 * informações próprias da venda, como a data e o valor final.
 */
@Entity
public class Venda {

    /**
     * Identificador único da venda, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A referência ao Carro que foi vendido nesta transação.
     * A anotação @ManyToOne indica que muitas vendas podem estar associadas a um carro (embora na nossa regra, um carro só possa ser vendido uma vez).
     */
    @ManyToOne
    @JoinColumn(name = "carro_id") // Define a coluna de chave estrangeira no banco.
    private Carro carro;

    /**
     * A referência ao Cliente que realizou a compra.
     * @ManyToOne indica que um cliente pode ter muitas vendas associadas.
     */
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Data e hora em que a venda foi registrada.
    private Date dataVenda;
    
    // O valor final da venda, copiado do preço do carro no momento da transação.
    private double valorVenda;

    /**
     * Construtor padrão que define a data da venda como o momento da criação.
     * Necessário para o JPA/Hibernate.
     */
    public Venda() {
        this.dataVenda = new Date();
    }

    /**
     * Construtor principal para criar uma nova venda a partir de um carro e um cliente.
     * Define automaticamente a data e o valor da venda com base no preço do carro.
     */
    public Venda(Carro carro, Cliente cliente) {
        this.carro = carro;
        this.cliente = cliente;
        this.dataVenda = new Date();
        // A lógica principal: o valor da venda é definido pelo preço do carro.
        this.valorVenda = carro.getPreco(); 
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }
}