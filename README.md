# Projeto de Sistema de Votação Sicredi

Este projeto é um sistema backend desenvolvido em Spring Boot para gerenciar pautas, associados, sessões de votação e votos. Ele foi aprimorado para demonstrar boas práticas de desenvolvimento de software, arquitetura limpa, testes robustos e integração com sistemas externos, além de documentação de API interativa.

---

## Tecnologias Utilizadas

* **Java 21 (LTS):** Linguagem de programação moderna e performática.
* **Spring Boot 3.2.0:** Framework para construção rápida de aplicações Java.
* **Spring Data JPA:** Para persistência de dados e interação com o banco de dados.
* **MySQL:** Banco de dados relacional.
* **Liquibase 4.32.0:** Ferramenta para controle de versão do esquema do banco de dados.
* **Lombok 1.18.30:** Reduz a verbosidade do código Java.
* **JUnit 5 (Jupiter):** Framework para testes unitários e de integração.
* **Mockito 5.7.0:** Framework para criação de mocks em testes unitários.
* **Springdoc OpenAPI UI (Swagger) 2.5.0:** Para documentação interativa da API.
* **Maven:** Gerenciador de dependências e build.

---

## Configuração do Ambiente

### Pré-requisitos

* **Java Development Kit (JDK) 21**
* **Maven**
* **MySQL Server:** Configurado e rodando localmente (ou em um ambiente acessível).

### Configuração do Banco de Dados

1.  Crie um banco de dados MySQL vazio com o nome `sicredi`.
2.  Verifique as configurações de conexão no arquivo `src/main/resources/application.properties` e ajuste `spring.datasource.username` e `spring.datasource.password` se necessário.
    ```properties
    spring.datasource.username=root
    spring.datasource.password=1234
    spring.datasource.url=jdbc:mysql://localhost:3306/sicredi
    ```

### Limpeza e Build do Projeto

Na raiz do projeto (`/votacao-scredi`), execute o seguinte comando para limpar o cache do Maven, baixar todas as dependências e compilar o projeto:

```bash
mvn clean install -U
