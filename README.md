# Sistema de Votação Sicredi

Sistema backend desenvolvido em Spring Boot para gerenciar o processo completo de votação em assembleias, incluindo cadastro de associados, criação de pautas, abertura de sessões de votação, registro de votos e consolidação de resultados.

## 📋 Sobre o Projeto

Este projeto foi desenvolvido como **teste técnico** por **Fábio Vasconcelos** para a vaga de **Java Sênior**.

O sistema demonstra boas práticas de desenvolvimento de software, arquitetura limpa, testes robustos e integração com sistemas externos, além de documentação de API interativa.

## 📚 Documentação

Para informações detalhadas sobre o sistema, consulte a documentação completa:

- **[Objetivo Geral e Funcionalidades](documentos/01-objetivo-geral.md)** - Visão geral do sistema, objetivo e funcionalidades principais
- **[Fluxo do Sistema](documentos/02-fluxo-sistema.md)** - Diagramas em ASCII mostrando os fluxos principais do sistema
- **[Estórias de Usuário](documentos/03-estorias-usuario.md)** - Estórias de usuário detalhadas com critérios de aceite e regras de negócio

## 🚀 Funcionalidades Principais

- **Gestão de Associados**: Cadastro, listagem, busca e exclusão de associados
- **Gestão de Pautas**: Criação, listagem, consulta e exclusão de pautas de votação
- **Gestão de Sessões**: Abertura de sessões de votação com duração configurável
- **Registro de Votos**: Sistema de votação com validação de CPF via serviço externo
- **Consolidação de Resultados**: Consulta de resultados de votação com contagem de votos SIM e NÃO

## 🛠️ Tecnologias Utilizadas

* **Java 21 (LTS)**: Linguagem de programação moderna e performática
* **Spring Boot 3.2.0**: Framework para construção rápida de aplicações Java
* **Spring Data JPA**: Para persistência de dados e interação com o banco de dados
* **MySQL**: Banco de dados relacional
* **Liquibase 4.32.0**: Ferramenta para controle de versão do esquema do banco de dados
* **Lombok 1.18.30**: Reduz a verbosidade do código Java
* **JUnit 5 (Jupiter)**: Framework para testes unitários e de integração
* **Mockito 5.7.0**: Framework para criação de mocks em testes unitários
* **Springdoc OpenAPI UI (Swagger) 2.5.0**: Para documentação interativa da API
* **Maven**: Gerenciador de dependências e build

## ⚙️ Configuração do Ambiente

### Pré-requisitos

* **Java Development Kit (JDK) 21**
* **Maven**
* **MySQL Server**: Configurado e rodando localmente (ou em um ambiente acessível)

### Configuração do Banco de Dados

1. Crie um banco de dados MySQL vazio com o nome `sicredi`.
2. Verifique as configurações de conexão no arquivo `votacao-scredi/src/main/resources/application.properties` e ajuste `spring.datasource.username` e `spring.datasource.password` se necessário.

```properties
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.url=jdbc:mysql://localhost:3306/sicredi
```

### Limpeza e Build do Projeto

Na raiz do projeto (`/votacao-scredi`), execute o seguinte comando para limpar o cache do Maven, baixar todas as dependências e compilar o projeto:

```bash
mvn clean install -U
```

### Limpeza de Checksums do Liquibase

**Importante na primeira execução ou após atualização**

Se houver erros de checksum do Liquibase (Validation Failed: X changesets check sum), execute:

```bash
mvn liquibase:clearCheckSums
```

Isso resetará os checksums do Liquibase no banco de dados para corresponder aos arquivos de changelog atuais.

## 🏃 Como Rodar a Aplicação

Na raiz do projeto (`/votacao-scredi`), execute:

```bash
mvn spring-boot:run
```

A aplicação estará acessível em `http://localhost:8080`.

## 🧪 Como Rodar os Testes

Na raiz do projeto (`/votacao-scredi`), execute:

```bash
mvn test
```

## 📖 Documentação da API (Swagger UI)

Com a aplicação rodando, acesse a documentação interativa da API em:

**http://localhost:8080/swagger-ui.html**

A documentação Swagger permite testar todos os endpoints da API de forma interativa.

## 📁 Estrutura do Projeto

```
sicredi/
├── documentos/                    # Documentação do projeto
│   ├── 01-objetivo-geral.md       # Objetivo geral e funcionalidades
│   ├── 02-fluxo-sistema.md        # Fluxos do sistema em ASCII
│   └── 03-estorias-usuario.md     # Estórias de usuário
├── votacao-scredi/                # Projeto principal
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── votacao/scredi/
│   │   │   │       ├── client/           # Cliente para serviços externos
│   │   │   │       ├── config/           # Configurações
│   │   │   │       ├── controller/       # Controllers REST
│   │   │   │       ├── dto/              # Data Transfer Objects
│   │   │   │       ├── entity/           # Entidades JPA
│   │   │   │       ├── enumerate/        # Enums
│   │   │   │       ├── exception/        # Exceções customizadas
│   │   │   │       ├── repository/       # Repositórios JPA
│   │   │   │       └── service/          # Lógica de negócio
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── db/                   # Scripts Liquibase
│   │   └── test/                         # Testes
│   └── pom.xml
└── README.md
```

## 🔗 Endpoints Principais

### Associados
- `POST /v1/associados` - Criar associado
- `GET /v1/associados` - Listar associados
- `GET /v1/associados/buscar/{cpf}` - Buscar por CPF
- `DELETE /v1/associados/{id}` - Deletar associado

### Pautas
- `POST /v1/pautas` - Criar pauta
- `GET /v1/pautas` - Listar pautas
- `GET /v1/pautas/{id}` - Buscar por ID
- `DELETE /v1/pautas/{id}` - Deletar pauta

### Sessões e Votos
- `POST /v1/sessoes` - Abrir sessão de votação
- `GET /v1/sessoes` - Listar sessões
- `GET /v1/sessoes/{id}` - Buscar sessão por ID
- `POST /v1/sessoes/{id}/voto` - Registrar voto
- `GET /v1/sessoes/{id}/resultado` - Consultar resultado da votação

## 📝 Licença

Este projeto foi desenvolvido como teste técnico.
