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

    // Validar se todos os campos foram preenchidos
    if (!marca || !modelo || !ano || !preco) {
        event.preventDefault();
        alert("Por favor, preencha todos os campos do veículo!");
    }

    if (!nome || !cpf || !endereco) {
        event.preventDefault();
        alert("Por favor, preencha todos os campos do cliente!");
    }
});
