API desenvolvida em **Java 21 + Spring Boot** para gerenciamento de artistas, álbuns e imagens associadas, com persistência relacional e armazenamento de imagens em **S3 (MinIO)**.

---

## 📑 Sumário

* [Visão Geral](#-visão-geral)
* [Arquitetura](#Arquitetura)
* [Pré-requisitos](#-pré-requisitos)
* [Execução com Docker (Recomendado)](#-execução-com-docker-recomendado)
* [Execução Local (Sem Docker)](#-execução-local-sem-docker)
* [API / Swagger](#api--swagger)
* [📊 Diagrama de Entidades (Resumo)](#-diagrama-de-entidades-resumo)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Estrutura do Projeto](#-estrutura-do-projeto)
* [Sincronização de Regionais](#-sicronizacao-regionais)
---

## 🔎 Visão Geral

O projeto tem como objetivo disponibilizar uma API REST para:

* Cadastro e consulta de **artistas** (cantor ou banda)
* Gerenciamento de **álbuns musicais**
* Relacionamento **muitos-para-muitos** entre artistas e álbuns
* Upload e gerenciamento de **imagens de álbuns** via S3 (MinIO)
* Importação e sincronização de dados externos por região

---

##  Arquitetura

O backend segue uma separação em camadas inspirada em **Clean Architecture**:

* **domain** → Entidades e regras de negócio
* **application** → Casos de uso e serviços
* **infra** → Controllers, repositórios, integrações externas e configurações

O projeto utiliza **Maven multi-módulo**, com build centralizado no POM pai.

##  Pré-requisitos

### Para execução com Docker (recomendado)

* Docker Desktop
* Docker Compose (v2+)

### Para execução local (sem Docker)

* Java **21**
* Maven **3.9+**
* PostgreSQL
* MinIO (ou outro S3 compatível)

---

##  Execução com Docker (Recomendado)

###  Clonar o repositório

```bash
git clone https://github.com/Mamedes/eng-comp-full.git
cd eng-comp-full
```

###   Subir toda a stack

```bash
docker compose build
docker compose up -d
```
---
##  Execução Local (Sem Docker)

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

### Executar testes

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

## 🧱 Estrutura do Projeto

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

## 🧱 Sicronização Regionais

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

## 📌 Observações

* O projeto segue uma separação em camadas inspirada em **Clean Architecture**
* As imagens dos álbuns não são armazenadas no banco, apenas seus metadados
* Ideal para extensão com autenticação, cache e mensageria

---
