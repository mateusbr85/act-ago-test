## Visão Geral do Projeto

Este repositório contém um exemplo de microserviços Java com Spring Boot organizados em múltiplos módulos, mostrando como desenvolver, containerizar e orquestrar serviços separados:

- **products-service** – Serviço de produtos
- **payments-service** – serviço de processamento de pagamentos
- **api-act-orders** –  Serviço de Ordens

---

## Arquitetura

```
act-ago-test/
├── api-act-orders/       # API de pedidos (gateway/aggregator)
├── products-service/     # Microserviço de produtos
├── payments-service/     # Microserviço de pagamentos
├── compose.yml           # Orquestração Docker Compose
└── pom.xml               # POM pai para build multi-módulo
```

---

## Pré-requisitos

- **Java 17+** JDK instalado
- **Maven 3.6+** (para build dos módulos)
- **Docker & Docker Compose** (para deploy em containers)

---

## Como Executar

1. **Clone o repositório**
   ```bash
   git clone https://github.com/mateusbr85/act-ago-test.git
   cd act-ago-test
   ```

2. **Faça o build de todos os módulos**
   ```bash
   mvn clean install
   ```


4. **Acesse as APIs**
    - `http://localhost:8082/products` – **products-service**
    - `http://localhost:8083/payments` – **payments-service**
    - `http://localhost:8081/orders`   – **api-act-orders**

---

## Descrição dos Serviços

### products-service

- **Porta:** `8082`
- **Endpoints Principais:**
    - `GET /products` – lista todos os produtos
    - `POST /products` – cria um novo produto
    - `GET /products/{id}` – busca produto por ID

### payments-service

- **Porta:** `8083`
- **Endpoints Principais:**
    - `POST /payments` – inicia um pagamento
    - `GET /payments/{id}` – consulta status do pagamento

### api-act-orders

- **Porta:** `8081`
- **Endpoints Principais:**
    - `GET /orders` – lista todos os produtos
    - `POST /orders` – cria um novo produto
    - `GET /orders/{id}` – busca produto por ID

---

## Stack de Tecnologias

- **Spring Boot** – Framework Java para microserviços
- **Maven** – Ferramenta de build e gerenciamento de dependências
- **Kafka**, **PostgreSQL**, etc., conforme configurado no Compose


