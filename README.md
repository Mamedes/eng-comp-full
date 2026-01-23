API desenvolvida em **Java 21 + Spring Boot** para gerenciamento de artistas, álbuns e imagens associadas, com persistência relacional e armazenamento de imagens em **S3 (MinIO)**.

---

## 📑 Sumário

* [Visão Geral](#-visão-geral)
* [Download](#download)
* [Execução](#execução)
* [API / Swagger](#api--swagger)
* [📊 Diagrama de Entidades (Resumo)](#-diagrama-de-entidades-resumo)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Estrutura do Projeto](#-estrutura-do-projeto)

---

## 🔎 Visão Geral

O projeto tem como objetivo disponibilizar uma API REST para:

* Cadastro e consulta de **artistas** (cantor ou banda)
* Gerenciamento de **álbuns musicais**
* Relacionamento **muitos-para-muitos** entre artistas e álbuns
* Upload e gerenciamento de **imagens de álbuns** via S3 (MinIO)
* Importação e sincronização de dados externos por região

---

## Download

1. Clone o repositório:

```bash
$ git clone https://github.com/Mamedes/eng-comp-full.git
```

2. Acesse o diretório do projeto:

```bash
$ cd seplag
```

---

## Execução

### Pré-requisitos

* Java **21**
* Maven **3.9+**
* Docker e Docker Compose (opcional, recomendado para MinIO e banco)

### Build do projeto

```bash
$ mvn clean install
```

### Executar a aplicação

```bash
$ mvn spring-boot:run
```

Ou execute diretamente pelo módulo **infra**:

```bash
$ cd infra
$ mvn spring-boot:run
```

### Executando os Testes

```bash
$ mvn clean test
```


---

## API / Swagger

Após subir a aplicação, a documentação interativa da API estará disponível em:

```
http://localhost:8080/swagger-ui.html
```

ou

```
http://localhost:8080/swagger-ui/index.html
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

---

## 🧱 Estrutura do Projeto

```text
seletivo
├── domain        # Entidades e regras de negócio
├── application   # Casos de uso e serviços
├── infra         # Controllers, repositórios, configurações
└── pom.xml       # POM pai (multi-módulo)
```

---

## 📌 Observações

* O projeto segue uma separação em camadas inspirada em **Clean Architecture**
* As imagens dos álbuns não são armazenadas no banco, apenas seus metadados
* Ideal para extensão com autenticação, cache e mensageria

---

📬 Em caso de dúvidas ou sugestões, fique à vontade para contribuir ou abrir uma issue.
