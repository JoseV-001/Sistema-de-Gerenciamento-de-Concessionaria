document.querySelector("form").addEventListener("submit", function(event) {
    // Recuperar os valores dos campos para o formulário de veículos
    let marca = document.getElementById("marca").value;
    let modelo = document.getElementById("modelo").value;
    let ano = document.getElementById("ano").value;
    let preco = document.getElementById("preco").value;

    // Recuperar os valores dos campos para o formulário de clientes
    let nome = document.getElementById("nome").value;
    let cpf = document.getElementById("cpf").value;
    let endereco = document.getElementById("endereco").value;

    // Validar se todos os campos de veículo foram preenchidos
    if (!marca || !modelo || !ano || !preco) {
        event.preventDefault(); // Impede o envio do formulário
        alert("Por favor, preencha todos os campos do veículo!");
        return; // Interrompe a execução
    }

    // Validar se todos os campos de cliente foram preenchidos
    if (!nome || !cpf || !endereco) {
        event.preventDefault(); // Impede o envio do formulário
        alert("Por favor, preencha todos os campos do cliente!");
        return; // Interrompe a execução
    }

    // Caso tudo esteja ok, o formulário será enviado normalmente
});
