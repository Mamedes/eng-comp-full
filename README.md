API desenvolvida em **Java 21 + Spring Boot** para gerenciamento de artistas, álbuns e imagens associadas, com persistência relacional e armazenamento de imagens em **S3 (MinIO)**.

---

## 📑 Sumário

* [Visão Geral](#-visão-geral)
* [Arquitetura](#Arquitetura)
* [Padrão de Identificadores e Auditoria](#-padrão-de-identificadores-e-auditoria)
* [Pré-requisitos](#-pré-requisitos)
* [Execução com Docker (Recomendado)](#-execução-com-docker-recomendado)
* [Execução Local (Sem Docker)](#-execução-local-sem-docker)
* [API / Swagger](#api--swagger)
* [📊 Diagrama de Entidades (Resumo)](#-diagrama-de-entidades-resumo)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Estrutura do Projeto](#-estrutura-do-projeto)
* [Segurança e Autenticação](#-segurança-e-autenticação)
* [Monitoramento e Health Checks](#-monitoramento-e-health-checks)
* [Sincronização de Regionais](#-sicronizacao-regionais)
* [Estratégia de Testes](#-estrategia-testes)
---

##  Visão Geral

O projeto tem como objetivo disponibilizar uma API REST para:

* Cadastro e consulta de **artistas** (cantor ou banda)
* Gerenciamento de **álbuns musicais**
* Relacionamento **muitos-para-muitos** entre artistas e álbuns
* Upload e gerenciamento de **imagens de álbuns** via S3 (MinIO)
* Importação e sincronização de dados externos por região

---

## Arquitetura

O backend segue uma separação em camadas inspirada em **Clean Architecture**:

* **domain** → Entidades e regras de negócio
* **application** → Casos de uso e serviços
* **infra** → Controllers, repositórios, integrações externas e configurações
* Programação Funcional com Vavr: Uso da classe Either para tratamento de erros expressivo, separando fluxos de sucesso (Right) e falha (Left) de forma declarativa.
* Notification Pattern: Em vez de disparar exceções custosas, a aplicação utiliza o padrão Notification para coletar e retornar todos os erros de validação de uma vez.
* Gateway Pattern: Interfaces como ArtistaGateway e ArtistaQueryGateway desacoplam a lógica de negócio do banco de dados, permitindo trocas tecnológicas sem impacto no domínio.

O projeto utiliza **Maven multi-módulo**, com build centralizado no POM pai.

---

##  Padrão de Identificadores e Auditoria

A arquitetura do projeto adota um **padrão explícito de identificação e auditoria**, alinhado a boas práticas de segurança, rastreabilidade e desacoplamento entre camadas internas e externas.

###  Identificação

* Cada entidade possui:

    * **`id`** → Identificador técnico (chave primária) utilizado **exclusivamente para operações internas**, relacionamentos e persistência.
    * **`secureId (UUID)`** → Identificador público, **imutável**, exposto ao **frontend e APIs externas**.

O **UUID é o único identificador trafegado nas requisições externas**, evitando exposição de IDs sequenciais e aumentando a segurança da API.

---

### ⏱️ Auditoria

Todas as entidades persistentes seguem um padrão de auditoria temporal:

```java
@Column(name = "created_at", nullable = false, updatable = false)
private Instant createdAt;

@Column(name = "updated_at", nullable = false)
private Instant updatedAt;
```

* **`createdAt`** → Data/hora de criação do registro (imutável)
* **`updatedAt`** → Data/hora da última atualização

Esse padrão garante:

* Rastreabilidade completa dos dados

---

## Pré-requisitos

### Para execução com Docker (recomendado)

* Docker Desktop
* Docker Compose (v2+)

### Para execução local (sem Docker)

* Java **21**
* Maven **3.9+**
* PostgreSQL
* MinIO (ou outro S3 compatível)

---

## Execução com Docker (Recomendado)

### Clonar o repositório

```bash
git clone https://github.com/Mamedes/mamedeseronildesdecastrojunior048766.git
cd mamedeseronildesdecastrojunior048766
```

### Subir toda a stack

```bash
docker compose build
docker compose up -d
```
---

## Execução Local (Sem Docker)

### Build do projeto

Na raiz do backend:

```bash
mvn clean package install
```

### Executar a aplicação

```bash
cd infra
mvn spring-boot:run
```

### Executar testes Local

```bash
mvn clean test
```

---

### Serviços disponíveis

| Serviço   | URL |
|----------|-----|
| Frontend | http://localhost:3000 |
| Backend  | http://localhost:8081 |
| Swagger  | http://localhost:8081/swagger-ui/index.html |
| MinIO    | http://localhost:9001 |
| PostgreSQL | localhost:5432 |

> 💡 O frontend se comunica com o backend internamente via Docker network.

---
## API / Swagger

Após subir a aplicação, a documentação interativa da API estará disponível em:

```
http://localhost:8081/swagger-ui.html
```

ou

```
http://localhost:8081/swagger-ui/index.html
```

---

## 📊 Diagrama de Entidades (Resumo)

### **artista**

Armazena os dados do músico ou banda, incluindo o tipo (**Cantor** ou **Banda**).

### **album**

Contém o título e demais informações da obra musical.

### **artista_album**

Tabela de ligação responsável pelo relacionamento **Muitos-para-Muitos** entre artistas e álbuns.

### **album_imagem**

Armazena os metadados das imagens dos álbuns, incluindo as chaves de armazenamento no S3 (MinIO).

### **regional**

Estrutura destinada à importação e sincronização de dados provenientes de fontes externas.

---

## 🚀 Tecnologias Utilizadas

* Java 21
* Spring Boot 3.x
* Spring Data JPA
* Hibernate
* Maven
* Swagger / OpenAPI
* MinIO (S3)
* Docker / Docker Compose
* React + Vite (Frontend)

---

## Monitoramento e Health Checks

```text
Dashboard de Status (Frontend)
O frontend conta com uma página dedicada (/status) que consome os dados do Actuator via RxJS, permitindo que administradores visualizem a integridade de:

Conexão com Banco de Dados

Conectividade com MinIO (S3)

Espaço em disco e status da JVM
```

---

##  Estrutura do Projeto

```text
eng-comp-full
├── back-end
│   ├── domain        # Entidades e regras de negócio
│   ├── application   # Casos de uso e serviços
│   ├── infra         # Controllers, repositórios, configs, API
│   └── pom.xml       # POM pai (multi-módulo)
│
├── front-end
│   └── artists-manager   # React + Vite + Nginx
│
├── docker-compose.yml
└── README.md
```

---
## Segurança e Autenticação

```text
* accessToken: duração (5 minutos).
* refreshToken: Longa duração.

### Fluxo de Renovação Automática (Frontend)
O cliente (React) implementa um Interseptor:
1. requisição `401`.
2. O sistema entra em modo `isRefreshing`, enfileirando as demais requisições pendentes.
3. É feita uma chamada automática ao endpoint `/auth/refresh-token`.
4. 200 o sistema atualiza o `localStorage` e reprocessa todas as requisições da fila.
5. Caso o Refresh Token também esteja expirado, o usuário é redirecionado para o `/login`.

### Principais Endpoints de Auth
| `POST` | `/auth/login` | Autentica usuário e gera tokens exemplo no body.
{
"username": "admin",
"password": "password"
}
| `POST` | `/auth/refresh-token` | Renova o par de tokens. | passar no header `refresh-token` + refreshToken
```

---

##  Sicronização Regionais
```text
Novo no endpoint→ Inserir novo registro com ativo = true

Não disponível no endpoint→ Inativar (ativo = false) o registro local ativo

Qualquer atributo alterado (ex: nome)→ Inativar o registro atual e criar um novo com a nova denominação

Mapeamento das regionais locais ativas por external_id

Iteração única sobre a lista recebida da API externa

Segunda iteração para identificar registros locais ausentes no endpoint

🌐 API Interna

Endpoint de Sincronização Manual

POST /v1/regionais/sync
📥 Payload:
{
  "regionais": [
    { "id": 9, "nome": "REGIONAL DE CUIABÁ" }
  ]
}
   Resposta de sucesso
{
  "success": true,
  "processados": 5
}

```
##  Estratégia de Testes

```text
Teste de Unitarios (Application & Domain)
Tecnologias: JUnit 5, Mockito.

O que é testado:

Criação, atualização e deleção de álbuns e artistas.

Validações de campos obrigatórios e regras de notificação de erros.

Garantia de que o secureId (UUID) é gerado e mantido corretamente.

Teste de Integração (Infra)
Mocks de Dependências: Nos testes de Caso de Uso (ex: DefaultCreateAlbumUseCaseTest), utilizamos o Mockito para simular o comportamento dos Gateways e do serviço de notificação, isolando a regra de negócio.

## Estratégia de Testes - Frontend

Os testes foram implementados utilizando **Vitest** pela performance e compatibilidade com Vite.

* **Testes de Store (Estado):** Validam a integridade do estado global, garantindo que mutações, paginação e carregamento funcionem conforme o esperado.
* **Testes de Facade:** Garantem que a lógica de orquestração (ex: alternar ordenação ASC/DESC) dispare as ações corretas nos serviços e stores.
* **Testes de Serviço:** Validam as chamadas HTTP e a correta passagem de parâmetros para a API.
* **Testes de Infraestrutura (HTTP Client):** Utiliza **MSW** para simular cenários reais de rede, como a renovação automática de tokens após erro `401 Unauthorized`.

### Como executar os testes
```bash
# No diretório do frontend:
npm test         # Executa os testes uma vez
npm run test:ui  # Abre a interface visual do Vitest
```
##  Checklist de Requisitos

### Backend
- [x] Java 21 / Spring Boot 3 + Docker Compose.
- [x] Autenticação JWT (5 min) + Refresh Token.
- [x] CRUD completo (POST, PUT, GET, DELETE).
- [x] Paginação e filtros de busca (ASC/DESC).
- [x] Integração S3 (MinIO) com Presigned URLs (30 min).
- [x] Flyway Migrations.
- [x] Rate Limit (10 req/min).
- [x] WebSocket para notificações de novos álbuns.
- [x] Health Checks (Liveness/Readiness).

### Frontend
- [x] React + TypeScript + Tailwind CSS.
- [x] Gestão de estado com BehaviorSubject.
- [x] Lazy Loading e Rotas protegidas.
- [x] Upload de imagens e listagem responsiva.

---

## 📌 Observações

* O projeto segue uma separação em camadas inspirada em **Clean Architecture**
* O padrão **ID interno + UUID público** evita exposição de chaves técnicas
* As imagens dos álbuns não são armazenadas no banco, apenas seus metadados
* Ideal para extensão com autenticação.
