
# Pagamento API

Este é um projeto para aprendizado criando uma API para gerenciamento de pagamentos, construída em Java usando Spring Boot e PostgreSQL.

## Pré-requisitos

- Docker
- Docker Compose

## Como Executar o Projeto

1. Clone este repositório:

   ```bash
   git clone git@github.com:gui-costads/pagamento-api.git
   cd pagamento
   ```

Certifique-se de que o Docker e o Docker Compose estão instalados.
Execute o seguinte comando para iniciar a aplicação e o banco de dados:

```bash
docker-compose up
```

A API estará disponível em http://localhost:8080.
Configuração do Banco de Dados
O projeto utiliza o PostgreSQL como banco de dados. As credenciais e configurações do banco de dados podem ser ajustadas no arquivo docker-compose.yml.

Endpoints
Exemplo de Endpoints
POST /pagamento: Cria um novo pagamento.
GET /pagamento/{id}: Recupera informações de um pagamento específico.
Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir um issue ou enviar um pull request.

Licença
Este projeto está licenciado sob a MIT License.