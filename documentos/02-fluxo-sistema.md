# Fluxo do Sistema de Votação

## Fluxo Principal de Votação

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    SISTEMA DE VOTAÇÃO SICREDI                           │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────┐
│  1. CADASTRO    │
│   ASSOCIADO     │
└────────┬────────┘
         │
         ▼
    ┌─────────┐
    │ Associado│
    │  (CPF)  │
    └─────────┘
         │
         │
         ▼
┌─────────────────┐
│  2. CADASTRO    │
│     PAUTA       │
└────────┬────────┘
         │
         ▼
    ┌─────────┐
    │  Pauta  │
    │(Título) │
    └─────────┘
         │
         │
         ▼
┌─────────────────┐
│  3. ABERTURA    │
│    SESSÃO       │
└────────┬────────┘
         │
         │ [Duração: 1 min (padrão) ou customizada]
         ▼
    ┌─────────┐
    │ Sessão  │
    │ Aberta  │
    └────┬────┘
         │
         │
         ▼
┌─────────────────────────────────────────────────────────┐
│  4. REGISTRO DE VOTOS                                   │
│                                                          │
│  Para cada Associado que deseja votar:                 │
│                                                          │
│  ┌──────────────────────────────────────────────┐     │
│  │ 4.1. Validação de Sessão                      │     │
│  │     ✓ Sessão está aberta?                    │     │
│  │     ✗ Se não → ERRO: Sessão encerrada        │     │
│  └──────────────────────────────────────────────┘     │
│                    │                                    │
│                    ▼                                    │
│  ┌──────────────────────────────────────────────┐     │
│  │ 4.2. Validação de Voto Único                 │     │
│  │     ✓ Associado já votou nesta sessão?       │     │
│  │     ✗ Se sim → ERRO: Já votou                │     │
│  └──────────────────────────────────────────────┘     │
│                    │                                    │
│                    ▼                                    │
│  ┌──────────────────────────────────────────────┐     │
│  │ 4.3. Validação de CPF (Serviço Externo)     │     │
│  │     → Chama API Externa                      │     │
│  │     ✓ CPF habilitado para votar?            │     │
│  │     ✗ Se não → ERRO: CPF não habilitado     │     │
│  └──────────────────────────────────────────────┘     │
│                    │                                    │
│                    ▼                                    │
│  ┌──────────────────────────────────────────────┐     │
│  │ 4.4. Registro do Voto                        │     │
│  │     ✓ Salva voto (SIM ou NÃO)               │     │
│  └──────────────────────────────────────────────┘     │
└─────────────────────────────────────────────────────────┘
         │
         │ [Após encerrar a sessão]
         ▼
┌─────────────────┐
│  5. CONSOLIDAÇÃO│
│    RESULTADO    │
└────────┬────────┘
         │
         ▼
    ┌──────────────┐
    │  Resultado   │
    │  - Votos SIM │
    │  - Votos NÃO │
    └──────────────┘
```

## Fluxo Detalhado de Registro de Voto

```
┌─────────────────────────────────────────────────────────────────┐
│              FLUXO DE REGISTRO DE VOTO                           │
└─────────────────────────────────────────────────────────────────┘

Cliente
   │
   │ POST /v1/sessoes/{id}/voto
   │ { "cpf": "12345678900", "voto": "SIM" }
   ▼
Controller (SessaoVotoController)
   │
   │ votar(idSessao, votoDTO)
   ▼
Service (SessaoService)
   │
   ├─→ 1. Verifica se sessão existe
   │      └─→ Se não existe → ERRO 404
   │
   ├─→ 2. Verifica se sessão está aberta
   │      └─→ Se encerrada → ERRO 400: "Sessão já está encerrada"
   │
   ├─→ 3. Busca associado por CPF
   │      └─→ Se não existe → ERRO 404
   │
   ├─→ 4. Valida CPF no serviço externo
   │      │
   │      └─→ ClienteValidacaoCpf.validarCpf(cpf)
   │           │
   │           └─→ GET {url-servico}/cpf/{cpf}
   │                │
   │                ├─→ Se CPF não encontrado → ERRO 404
   │                │
   │                └─→ Se CPF não habilitado → ERRO 400: "CPF não habilitado"
   │
   ├─→ 5. Verifica se associado já votou
   │      └─→ Se já votou → ERRO 400: "O associado já votou"
   │
   └─→ 6. Salva o voto
        │
        └─→ VotoRepository.save(voto)
             │
             └─→ Voto salvo com sucesso → HTTP 201
```

## Fluxo de Abertura de Sessão

```
┌─────────────────────────────────────────────────────────────────┐
│           FLUXO DE ABERTURA DE SESSÃO                           │
└─────────────────────────────────────────────────────────────────┘

Cliente
   │
   │ POST /v1/sessoes?idPauta=1&duracaoMinutos=5
   ▼
Controller (SessaoVotoController)
   │
   │ criar(idPauta, duracaoMinutos)
   ▼
Service (SessaoService)
   │
   ├─→ 1. Valida se pauta existe
   │      └─→ PautaService.obterPorId(idPauta)
   │           └─→ Se não existe → ERRO 404
   │
   ├─→ 2. Cria nova Sessão
   │      │
   │      ├─→ inicioSessao = LocalDateTime.now()
   │      │
   │      └─→ finalSessao = inicioSessao + duracaoMinutos
   │           (padrão: 1 minuto se não informado)
   │
   └─→ 3. Salva sessão
        │
        └─→ SessaoRepository.save(sessao)
             │
             └─→ Sessão criada → HTTP 201
```

## Fluxo de Consolidação de Resultados

```
┌─────────────────────────────────────────────────────────────────┐
│        FLUXO DE CONSOLIDAÇÃO DE RESULTADOS                      │
└─────────────────────────────────────────────────────────────────┘

Cliente
   │
   │ GET /v1/sessoes/{id}/resultado
   ▼
Controller (SessaoVotoController)
   │
   │ obterResultadoVotacao(idSessao)
   ▼
Service (SessaoService)
   │
   └─→ consolidarVotos(idSessao)
        │
        └─→ SessaoRepository.consolidarVotosPorSessao(idSessao)
             │
             ├─→ Busca todos os votos da sessão
             │
             ├─→ Conta votos SIM
             │
             ├─→ Conta votos NÃO
             │
             └─→ Retorna ResultadoVotacaoDTO
                  │
                  └─→ {
                       "idSessao": 1,
                       "idPauta": 1,
                       "tituloPauta": "...",
                       "totalVotosSim": 10,
                       "totalVotosNao": 5,
                       "totalVotos": 15
                      }
```

## Diagrama de Entidades e Relacionamentos

```
┌─────────────┐
│  Associado  │
├─────────────┤
│ id          │
│ nome        │
│ cpf         │
└──────┬──────┘
       │
       │ 1:N
       │
       ▼
┌─────────────┐      ┌─────────────┐
│    Voto     │      │   Sessao    │
├─────────────┤      ├─────────────┤
│ id          │      │ id          │
│ voto        │◄─────┤ inicioSessao│
│ (SIM/NÃO)   │  N:1 │ finalSessao │
└─────────────┘      └──────┬──────┘
                            │
                            │ 1:1
                            │
                            ▼
                    ┌─────────────┐
                    │    Pauta    │
                    ├─────────────┤
                    │ id          │
                    │ titulo      │
                    │ descricao   │
                    └─────────────┘
```

## Estados da Sessão

```
┌─────────────────────────────────────────────────────────────┐
│                    ESTADOS DA SESSÃO                        │
└─────────────────────────────────────────────────────────────┘

Sessão Criada
     │
     │ [inicioSessao = now]
     ▼
┌─────────────────┐
│  SESSÃO ABERTA  │ ← Aceita votos
│                 │
│  inicioSessao   │
│  < now() <      │
│  finalSessao    │
└────────┬────────┘
         │
         │ [now() >= finalSessao]
         ▼
┌─────────────────┐
│ SESSÃO ENCERRADA│ ← Não aceita mais votos
│                 │
│  now() >=       │
│  finalSessao    │
└─────────────────┘
```

