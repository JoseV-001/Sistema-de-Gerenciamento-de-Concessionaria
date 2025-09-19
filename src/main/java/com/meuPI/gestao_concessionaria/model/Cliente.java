package com.meuPI.gestao_concessionaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Representa a entidade Cliente no banco de dados.
 * Esta classe define a estrutura de dados de um cliente e também contém
 * as regras de validação para cada campo.
 */
@Entity
public class Cliente {

    /**
     * Identificador único do cliente, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Regras de validação para o nome do cliente.
    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String nome;

    // Regra para garantir que o CPF não seja vazio e siga o formato XXX.XXX.XXX-XX.
    @NotBlank(message = "O CPF não pode estar em branco.")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato de CPF inválido. Use XXX.XXX.XXX-XX.")
    private String cpf;

    // Garante que o endereço não seja deixado em branco.
    @NotBlank(message = "O endereço não pode estar em branco.")
    private String endereco;

    // Valida o formato do CEP (XXXXX-XXX).
    @NotBlank(message = "O CEP não pode estar em branco.")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Formato de CEP inválido. Use XXXXX-XXX.")
    private String cep;

    // Valida o formato do telefone ((XX) XXXXX-XXXX).
    @NotBlank(message = "O telefone não pode estar em branco.")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Formato de telefone inválido. Use (XX) XXXXX-XXXX.")
    private String telefone;

    // Garante que o e-mail não seja vazio e tenha um formato válido.
    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    /**
     * Construtor padrão sem argumentos.
     * Necessário para o funcionamento de frameworks como o JPA/Hibernate.
     */
    public Cliente() {
    }

    /**
     * Construtor com todos os parâmetros para facilitar a criação de objetos Cliente.
     */
    public Cliente(Long id, String nome, String cpf, String endereco, String cep, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.cep = cep;
        this.telefone = telefone;
        this.email = email;
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}