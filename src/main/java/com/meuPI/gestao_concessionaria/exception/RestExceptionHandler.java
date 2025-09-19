package com.meuPI.gestao_concessionaria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manipulador de exceções global para a API REST.
 * A anotação @RestControllerAdvice permite que esta classe capture exceções
 * lançadas por qualquer @RestController na aplicação, centralizando o tratamento de erros.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Captura exceções do tipo MethodArgumentNotValidException.
     * Esta exceção é lançada automaticamente pelo Spring quando a validação de um objeto
     * anotado com @Valid (nos controllers) falha.
     * O método formata os erros em um JSON claro para o frontend.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Define que a resposta HTTP será sempre 400 Bad Request.
    @ExceptionHandler(MethodArgumentNotValidException.class) // Especifica que este método lida com este tipo de exceção.
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Cria um mapa para armazenar os erros no formato "nomeDoCampo": "mensagemDeErro".
        Map<String, String> errors = new HashMap<>();

        // Percorre a lista de todos os erros de validação encontrados.
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // Extrai o nome do campo que falhou na validação (ex: "cpf").
            String fieldName = ((FieldError) error).getField();
            // Extrai a mensagem de erro definida na anotação de validação (ex: "Formato de CPF inválido.").
            String errorMessage = error.getDefaultMessage();
            // Adiciona o erro ao mapa.
            errors.put(fieldName, errorMessage);
        });

        // Retorna o mapa de erros, que será convertido para JSON na resposta da API.
        return errors;
    }
}