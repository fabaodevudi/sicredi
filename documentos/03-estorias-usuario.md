# Estórias de Usuário

## EP01 - Gestão de Associados

### US01 - Cadastrar Associado
**Como** um administrador do sistema  
**Eu quero** cadastrar um novo associado  
**Para que** ele possa participar das votações

**Critérios de Aceite:**
- Deve ser possível cadastrar um associado informando nome e CPF
- O CPF deve ser válido (formato)
- Não deve ser possível cadastrar dois associados com o mesmo CPF
- Deve retornar status HTTP 201 quando criado com sucesso
- Deve retornar status HTTP 422 quando o CPF já existe

**Endpoint:** `POST /v1/associados`

---

### US02 - Listar Associados
**Como** um administrador do sistema  
**Eu quero** visualizar todos os associados cadastrados  
**Para que** eu possa gerenciar os associados

**Critérios de Aceite:**
- Deve retornar uma lista com todos os associados
- Cada associado deve conter: id, nome e CPF
- Deve retornar status HTTP 200

**Endpoint:** `GET /v1/associados`

---

### US03 - Buscar Associado por CPF
**Como** um administrador do sistema  
**Eu quero** buscar um associado específico pelo CPF  
**Para que** eu possa localizar informações de um associado

**Critérios de Aceite:**
- Deve retornar os dados do associado quando o CPF for encontrado
- Deve retornar status HTTP 200 quando encontrado
- Deve retornar status HTTP 404 quando não encontrado

**Endpoint:** `GET /v1/associados/buscar/{cpf}`

---

### US04 - Deletar Associado
**Como** um administrador do sistema  
**Eu quero** deletar um associado  
**Para que** eu possa remover associados do sistema

**Critérios de Aceite:**
- Deve remover o associado do sistema
- Deve retornar status HTTP 204 quando deletado com sucesso
- Deve retornar status HTTP 404 quando o associado não existe

**Endpoint:** `DELETE /v1/associados/{id}`

---

## EP02 - Gestão de Pautas

### US05 - Cadastrar Pauta
**Como** um administrador do sistema  
**Eu quero** cadastrar uma nova pauta  
**Para que** ela possa ser votada em uma sessão

**Critérios de Aceite:**
- Deve ser possível cadastrar uma pauta informando título e descrição
- O título deve ser único no sistema
- Não deve ser possível cadastrar duas pautas com o mesmo título
- Deve retornar status HTTP 201 quando criada com sucesso
- Deve retornar status HTTP 422 quando o título já existe

**Endpoint:** `POST /v1/pautas`

---

### US06 - Listar Pautas
**Como** um administrador do sistema  
**Eu quero** visualizar todas as pautas cadastradas  
**Para que** eu possa gerenciar as pautas

**Critérios de Aceite:**
- Deve retornar uma lista com todas as pautas
- Cada pauta deve conter: id, título e descrição
- Deve retornar status HTTP 200

**Endpoint:** `GET /v1/pautas`

---

### US07 - Buscar Pauta por ID
**Como** um administrador do sistema  
**Eu quero** buscar uma pauta específica pelo ID  
**Para que** eu possa visualizar os detalhes de uma pauta

**Critérios de Aceite:**
- Deve retornar os dados da pauta quando o ID for encontrado
- Deve retornar status HTTP 200 quando encontrada
- Deve retornar status HTTP 404 quando não encontrada

**Endpoint:** `GET /v1/pautas/{id}`

---

### US08 - Deletar Pauta
**Como** um administrador do sistema  
**Eu quero** deletar uma pauta  
**Para que** eu possa remover pautas do sistema

**Critérios de Aceite:**
- Deve remover a pauta do sistema
- Deve retornar status HTTP 204 quando deletada com sucesso
- Deve retornar status HTTP 404 quando a pauta não existe

**Endpoint:** `DELETE /v1/pautas/{id}`

---

## EP03 - Gestão de Sessões de Votação

### US09 - Abrir Sessão de Votação
**Como** um administrador do sistema  
**Eu quero** abrir uma sessão de votação para uma pauta  
**Para que** os associados possam votar nessa pauta

**Critérios de Aceite:**
- Deve ser possível criar uma sessão informando o ID da pauta
- A pauta deve existir no sistema
- A sessão deve ter duração padrão de 1 minuto se não informada
- Deve ser possível definir uma duração customizada em minutos
- Deve registrar o horário de início e fim da sessão
- Deve retornar status HTTP 201 quando criada com sucesso
- Deve retornar status HTTP 404 quando a pauta não existe

**Endpoint:** `POST /v1/sessoes?idPauta={id}&duracaoMinutos={minutos}`

---

### US10 - Listar Sessões
**Como** um administrador do sistema  
**Eu quero** visualizar todas as sessões de votação  
**Para que** eu possa gerenciar as sessões

**Critérios de Aceite:**
- Deve retornar uma lista com todas as sessões
- Cada sessão deve conter: id, pauta, início e fim da sessão
- Deve retornar status HTTP 200

**Endpoint:** `GET /v1/sessoes`

---

### US11 - Buscar Sessão por ID
**Como** um administrador do sistema  
**Eu quero** buscar uma sessão específica pelo ID  
**Para que** eu possa visualizar os detalhes de uma sessão

**Critérios de Aceite:**
- Deve retornar os dados da sessão quando o ID for encontrado
- Deve retornar status HTTP 200 quando encontrada
- Deve retornar status HTTP 404 quando não encontrada

**Endpoint:** `GET /v1/sessoes/{id}`

---

## EP04 - Registro de Votos

### US12 - Registrar Voto
**Como** um associado  
**Eu quero** votar em uma sessão de votação  
**Para que** minha opinião seja registrada

**Critérios de Aceite:**
- Deve ser possível votar informando CPF do associado e opção (SIM ou NÃO)
- A sessão deve estar aberta (não encerrada)
- O associado deve existir no sistema
- O CPF do associado deve estar habilitado para votar (validação externa)
- Um associado não pode votar duas vezes na mesma sessão
- Deve retornar status HTTP 201 quando o voto for registrado com sucesso
- Deve retornar status HTTP 400 quando:
  - A sessão já está encerrada
  - O associado já votou nesta sessão
  - O CPF não está habilitado para votar
- Deve retornar status HTTP 404 quando:
  - A sessão não existe
  - O associado não existe

**Endpoint:** `POST /v1/sessoes/{id}/voto`

**Body:**
```json
{
  "cpf": "12345678900",
  "voto": "SIM"
}
```

---

## EP05 - Consolidação de Resultados

### US13 - Consultar Resultado da Votação
**Como** um administrador do sistema  
**Eu quero** consultar o resultado de uma sessão de votação  
**Para que** eu possa conhecer o resultado da votação

**Critérios de Aceite:**
- Deve retornar o total de votos SIM
- Deve retornar o total de votos NÃO
- Deve retornar o total geral de votos
- Deve retornar informações da pauta e sessão
- Deve retornar status HTTP 200 quando encontrada
- Deve retornar status HTTP 404 quando a sessão não existe

**Endpoint:** `GET /v1/sessoes/{id}/resultado`

**Resposta:**
```json
{
  "idSessao": 1,
  "idPauta": 1,
  "tituloPauta": "Aprovação de orçamento",
  "totalVotosSim": 10,
  "totalVotosNao": 5,
  "totalVotos": 15
}
```

---

## Regras de Negócio

### RN01 - Validação de CPF
- O sistema deve validar o CPF do associado através de um serviço externo antes de permitir o voto
- Se o CPF não estiver habilitado para votar, o voto não deve ser registrado

### RN02 - Voto Único
- Um associado pode votar apenas uma vez por sessão
- Tentativas de votar novamente na mesma sessão devem ser rejeitadas

### RN03 - Sessão Encerrada
- Não é possível votar em uma sessão que já foi encerrada
- Uma sessão é considerada encerrada quando a data/hora atual é maior ou igual à data/hora de fim da sessão

### RN04 - Duração da Sessão
- A duração padrão de uma sessão é de 1 minuto
- É possível definir uma duração customizada ao criar a sessão
- A duração deve ser maior que zero

### RN05 - Unicidade de Título de Pauta
- Não é possível cadastrar duas pautas com o mesmo título

### RN06 - Unicidade de CPF de Associado
- Não é possível cadastrar dois associados com o mesmo CPF

