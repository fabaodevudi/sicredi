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

### Opção 1: Execução Local (Maven)

Na raiz do projeto (`/votacao-scredi`), execute:

```bash
mvn spring-boot:run
```

A aplicação estará acessível em `http://localhost:8080`.

### Opção 2: Execução com Docker

O projeto inclui suporte completo para Docker, facilitando a execução sem necessidade de instalar Java, Maven ou MySQL localmente.

#### Pré-requisitos para Docker

* **Docker** instalado e rodando
* **Docker Compose** (opcional, mas recomendado)

#### Executando com Docker Compose (Recomendado)

Na pasta `votacao-scredi`, execute:

```bash
docker-compose up -d
```

Isso irá:
- Criar e iniciar o container MySQL
- Criar e iniciar o container da aplicação Spring Boot
- Configurar automaticamente a rede entre os containers
- Executar as migrações do Liquibase

A aplicação estará acessível em `http://localhost:8080` (ou na porta configurada no docker-compose.yml).

Para parar os containers:

```bash
docker-compose down
```

Para parar e remover os volumes (dados do banco):

```bash
docker-compose down -v
```

#### Executando com Docker Manualmente

1. **Criar a rede Docker:**

```bash
docker network create sicredi-network
```

2. **Iniciar o MySQL:**

```bash
docker run -d --name sicredi-mysql \
  --network sicredi-network \
  -e MYSQL_ROOT_PASSWORD=1234 \
  -e MYSQL_DATABASE=sicredi \
  -p 3306:3306 \
  mysql:8.0
```

3. **Aguardar o MySQL inicializar (aproximadamente 10-15 segundos)**

4. **Build da imagem da aplicação:**

Na pasta `votacao-scredi`, execute:

```bash
docker build -t votacao-scredi:latest .
```

5. **Executar o container da aplicação:**

```bash
docker run -d --name votacao-scredi-app \
  --network sicredi-network \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://sicredi-mysql:3306/sicredi?serverTimezone=America/Sao_Paulo&createDatabaseIfNotExist=true" \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=1234 \
  -e URL_SERVICO_VALIDACAO_CPF="http://host.docker.internal:8080/servico-validacao-cpf-externo" \
  -p 8080:8080 \
  votacao-scredi:latest
```

#### Verificando os Containers

Para verificar o status dos containers:

```bash
docker ps
```

Para ver os logs da aplicação:

```bash
docker logs votacao-scredi-app -f
```

Para ver os logs do MySQL:

```bash
docker logs sicredi-mysql -f
```

#### Características do Dockerfile

O Dockerfile utiliza **multi-stage build** para otimizar o tamanho da imagem final:

- **Stage 1 (Build)**: Usa imagem Maven para compilar a aplicação
- **Stage 2 (Runtime)**: Usa imagem Alpine minimalista com apenas JRE
- **Segurança**: Executa como usuário não-root
- **Healthcheck**: Configurado para verificar a saúde da aplicação
- **Tamanho otimizado**: Imagem final reduzida usando Alpine Linux

## 🧪 Como Rodar os Testes

Na raiz do projeto (`/votacao-scredi`), execute:

```bash
mvn test
```

## 📖 Documentação da API (Swagger UI)

Com a aplicação rodando, acesse a documentação interativa da API em:

**http://localhost:8080/swagger-ui.html**

> **Nota**: Se estiver usando Docker e a porta foi mapeada para outra (ex: 8083), ajuste a URL accordingly.

A documentação Swagger permite testar todos os endpoints da API de forma interativa.

## 📁 Estrutura do Projeto

```
sicredi/
├── documentos/                    # Documentação do projeto
│   ├── 01-objetivo-geral.md       # Objetivo geral e funcionalidades
│   ├── 02-fluxo-sistema.md        # Fluxos do sistema em ASCII
│   └── 03-estorias-usuario.md     # Estórias de usuário
├── votacao-scredi/                # Projeto principal
│   ├── Dockerfile                 # Dockerfile para containerização
│   ├── docker-compose.yml        # Configuração Docker Compose
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
