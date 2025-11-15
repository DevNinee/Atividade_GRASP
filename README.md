# Sistema de Vendas de Ingressos — Padrões GRASP

Atividade prática de aplicação dos padrões GRASP em Java.

Esse é um projeto didática de um sistema de vendas de ingressos para shows, estruturado para demonstrar decisões de design baseadas em GRASP (Information Expert, Creator, Controller, Indirection, Low Coupling, High Cohesion).

## Índice

- [Funcionalidades do Sistema](#funcionalidades-do-sistema)
- [Visão Geral das Classes](#visão-geral-das-classes)
- [Padrões GRASP Aplicados](#padrões-grasp-aplicados)
- [Exemplos de Execução](#exemplos-de-execução)
- [Como Executar e Testar](#como-executar-e-testar)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Autores](#autores)

---

## Funcionalidades do Sistema

- Cadastro e listagem de ingressos (tipos: Camarote, VIP, Pista)
- Controle simples de estoque
- Registro de compras (vendas)
- Fluxo de compra demonstrativo pelo terminal

---

## Visão Geral das Classes

- `com.example.model.Ingresso` — representa um tipo de ingresso (nome, preço, quantidade disponível).
- `com.example.model.Compra` / `ItemCompra` — agregam itens e calculam totais.
- `com.example.controller.CompraController` — orquestra fluxos de compra e interação entre services/repositories.
- `com.example.repository.*Repository` — classes de persistência em memória (Maps) para clientes, ingressos e compras.
- `com.example.service.*Service` — camada de serviço que encapsula operações e usa os repositórios.
- `com.example.SistemaIngressosShows` — classe de execução/demonstração (main) que simula compras com dados de exemplo.

Para explicações estendidas sobre por que cada padrão GRASP foi usado, veja `PATTERNS.md` na raiz do projeto.

---

## Padrões GRASP Aplicados

Resumo rápido (veja `PATTERNS.md` para detalhes e exemplos de código):

- Information Expert: responsabilidade de cálculo e validação alocada às entidades do domínio (`Compra`, `Ingresso`).
- Creator: objetos do domínio são criados por classes que têm os dados necessários (`CompraController`, services).
- Controller: `CompraController` recebe operações de alto nível e coordena serviços e repositórios.
- Indirection / Pure Fabrication: repositórios em memória isolam a lógica de persistência do domínio.
- Low Coupling e High Cohesion: camadas separadas (`controller` / `service` / `repository` / `model`) e classes com responsabilidades claras.

---

## Exemplos de Execução

O `SistemaIngressosShows` já contém um fluxo de demonstração com dados de exemplo (clientes e compras). A execução produz mensagens de: listagem de ingressos, fluxo de compra para duas compras de exemplo, resumo de vendas e atualização de estoque.

Trecho representativo da saída de demonstração:

```
SISTEMA DE VENDAS DE INGRESSOS — Aplicando Padrões GRASP

--- 1. CRUD de Ingressos ---

[READ] Ingressos disponíveis:
Ingresso [Camarote] R$ 380,00 - Disponível: 20
Ingresso [VIP] R$ 220,00 - Disponível: 40
Ingresso [Pista] R$ 120,00 - Disponível: 100

=== NOVA COMPRA INICIADA ===
Número: C0001
Cliente: Ana Souza
-> Item adicionado com sucesso!
-> Compra registrada no sistema!

=== COMPRAS REALIZADAS ===
... (resumo e total de vendas) ...

FIM DA DEMONSTRAÇÃO
```

---

## Como Executar e Testar

Recomendo usar Maven (projeto já contém `pom.xml`) e JDK 17+. Alternativamente você pode compilar manualmente, mas o fluxo abaixo usa Maven.

1) Entrar no diretório do projeto (onde está o `pom.xml`):

```bash
cd /caminho/para/Atividade_GRASP
```

2) Rodar os testes unitários (JUnit 5):

```bash
mvn -DskipTests=false test
```

3) Executar a demonstração (executa `com.example.SistemaIngressosShows`):

```bash
mvn -DskipTests=true compile exec:java -Dexec.mainClass=com.example.SistemaIngressosShows
```

Saída esperada: listagem de ingressos; duas compras de exemplo (C0001 e C0002) registradas; resumo financeiro com total de vendas (R$ 1580,00 no cenário de demonstração) e estoque atualizado.

Se preferir executar sem Maven (compilação manual):

```bash
# compilar todas as classes Java (ajuste caminhos se necessário)
javac -d out $(find src/main/java -name "*.java")
# executar
java -cp out com.example.SistemaIngressosShows
```

---

## Tecnologias Utilizadas

- Java 17
- Maven
- JUnit 5 (tests)
- Padrões GRASP (documentação aplicada)

---

## Autores

- Fabiana Souza
- Erick Ferreira

---

