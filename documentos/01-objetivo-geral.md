# Objetivo Geral do Sistema

## Sobre o Projeto

Este projeto foi desenvolvido como **teste técnico** por **Fábio Vasconcelos** para a vaga de **Java Sênior**.

## Objetivo

O Sistema de Votação Sicredi é uma aplicação backend desenvolvida em Spring Boot que permite gerenciar o processo completo de votação em assembleias, incluindo o cadastro de associados, criação de pautas, abertura de sessões de votação, registro de votos e consolidação de resultados.

## O que o Sistema Faz

O sistema oferece as seguintes funcionalidades principais:

### 1. Gestão de Associados
- **Cadastro de Associados**: Permite cadastrar novos associados no sistema com nome e CPF
- **Listagem de Associados**: Visualiza todos os associados cadastrados
- **Busca por CPF**: Localiza um associado específico através do CPF
- **Exclusão de Associados**: Remove associados do sistema

### 2. Gestão de Pautas
- **Criação de Pautas**: Cria novas pautas de votação com título e descrição
- **Listagem de Pautas**: Visualiza todas as pautas cadastradas
- **Consulta de Pauta**: Busca uma pauta específica por ID
- **Exclusão de Pautas**: Remove pautas do sistema

### 3. Gestão de Sessões de Votação
- **Abertura de Sessão**: Cria uma nova sessão de votação para uma pauta específica
  - Duração padrão de 1 minuto
  - Possibilidade de definir duração customizada em minutos
- **Listagem de Sessões**: Visualiza todas as sessões de votação
- **Consulta de Sessão**: Busca uma sessão específica por ID

### 4. Registro de Votos
- **Validação de CPF**: Integração com serviço externo para validar se o CPF está habilitado para votar
- **Registro de Voto**: Permite que um associado vote em uma sessão específica
  - Validação de sessão aberta (não permite votar em sessões encerradas)
  - Validação de voto único (um associado não pode votar duas vezes na mesma sessão)
  - Validação de CPF habilitado (verifica com serviço externo)
- **Opções de Voto**: SIM ou NÃO

### 5. Consolidação de Resultados
- **Resultado da Votação**: Consolida e retorna o resultado de uma sessão de votação
  - Total de votos SIM
  - Total de votos NÃO
  - Informações da pauta e sessão

## Características Técnicas

- **Arquitetura RESTful**: API REST bem estruturada com versionamento (v1)
- **Validação de Dados**: Validação de entrada e regras de negócio
- **Integração Externa**: Integração com serviço externo para validação de CPF
- **Tratamento de Exceções**: Tratamento robusto de erros com mensagens claras
- **Documentação de API**: Documentação interativa via Swagger/OpenAPI
- **Testes**: Cobertura de testes unitários e de integração
- **Controle de Versão de Banco**: Utiliza Liquibase para versionamento do schema do banco de dados

## Tecnologias Utilizadas

- Java 21 (LTS)
- Spring Boot 3.2.0
- Spring Data JPA
- MySQL
- Liquibase 4.32.0
- Lombok
- JUnit 5 e Mockito
- Springdoc OpenAPI (Swagger)

