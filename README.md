# Sistema Cadastral com Persistência de Dados em Banco de Dados

## Descrição do Projeto
Este projeto é um sistema cadastral desenvolvido em Java que utiliza um banco de dados relacional para armazenar informações de forma persistente. O sistema integra o Java Database Connectivity (JDBC) para realizar a conexão com o banco de dados e aplica o padrão de projeto DAO (Data Access Object) para organizar a lógica de acesso aos dados, garantindo uma separação clara entre a lógica de negócio e as operações de persistência.

## Objetivos
- Implementar a persistência de dados em um banco de dados relacional usando JDBC.
- Aplicar o padrão DAO para gerenciar as operações de CRUD (Create, Read, Update, Delete).
- Realizar o mapeamento objeto-relacional (ORM) para facilitar a manipulação dos dados.
- Criar um sistema que permita cadastrar, alterar, excluir e consultar registros no banco de dados.

## Tecnologias Utilizadas
- **Java**: Linguagem de programação para implementar a lógica do sistema.
- **JDBC (Java Database Connectivity)**: Middleware para conectar e executar operações em bancos de dados.
- **Banco de Dados Relacional**: Armazenamento dos registros de forma persistente.
- **Padrão DAO**: Organização do código para separação das operações de acesso ao banco de dados.

## Funcionalidades
1. **Cadastro de Pessoas Físicas e Jurídicas**: Permite inserir novos registros de pessoas físicas e jurídicas no banco de dados.
2. **Alteração de Registros**: Modifica os dados de um registro existente.
3. **Exclusão de Registros**: Remove registros do banco de dados.
4. **Consulta de Registros**: Realiza a busca de um registro específico pelo ID ou lista todos os registros cadastrados.
5. **Persistência dos Dados**: Armazena os dados de forma persistente no banco relacional, garantindo que as informações permaneçam mesmo após o encerramento do sistema.

## Como Executar
### Pré-requisitos
- Java JDK 11 ou superior.
- Banco de dados SQL Server.
- Driver JDBC apropriado para o banco de dados utilizado.
