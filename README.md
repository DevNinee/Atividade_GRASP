
# Sistema de Vendas de Ingressos — Padrões GRASP

Atividade prática de aplicação dos padrões GRASP em Java.

Este projeto apresenta um sistema simples para vendas de ingressos de shows, implementado em Java e estruturado com padrões GRASP para garantir melhor organização, clareza, manutenibilidade e evolução do código.

##  Índice

* [Funcionalidades do Sistema](#-funcionalidades-do-sistema)
* [Estrutura das Classes](#%EF%B8%8F-estrutura-das-classes)
* [Padrões GRASP Aplicados](#-padrões-grasp-aplicados)
    * [Information Expert](#%EF%B8%8F-1-information-expert-especialista)
    * [Creator](#%EF%B8%8F-2-creator-criador)
    * [Controller](#%EF%B8%8F-3-controller)
    * [Low Coupling](#%EF%B8%8F-4-low-coupling-baixo-acoplamento)
    * [High Cohesion](#%EF%B8%8F-5-high-cohesion-alta-coesão)
    * [Polymorphism](#%EF%B8%8F-6-polymorphism-polimorfismo)
    * [Indirection](#%EF%B8%8F-7-indirection-indireção)
* [Exemplos de Execução](#-exemplos-de-execução)
* [Como Executar](#-como-executar)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Autores](#-autores)

---

##  Funcionalidades do Sistema

* Cadastro e gerenciamento de diferentes tipos de ingressos (VIP, Pista, Camarote etc.)
* Controle de estoque de ingressos
* Registro de vendas
* Interface simples pelo terminal
* Aplicação prática dos padrões GRASP (Specialist, Creator, Controller, Polymorphism, Low Coupling, High Cohesion etc.)

---

##  Estrutura das Classes (Visão Geral)

* **`Ingresso`**: Representa um tipo de ingresso. Guarda nome, preço e estoque. Aplica **GRASP Information Expert** ao encapsular os dados do ingresso.
* **`Venda`**: Registra uma venda realizada. Aplica **GRASP Creator**, pois é responsável por criar objetos relacionados a uma venda.
* **`VendaController`**: Coordena a lógica das transações. Aplica **GRASP Controller**, centralizando o fluxo da aplicação.
* **`LojaIngressos`**: Contém o "catálogo" de ingressos. Aplica **Low Coupling** e **High Cohesion** ao manter suas responsabilidades bem definidas.
* **`Main`**: Ponto de entrada da aplicação. Apenas delega tarefas ao controller (boa prática de Controller + Low Coupling).

---

##  Padrões GRASP Aplicados

Aqui está a explicação objetiva, clara e específica sobre onde cada padrão aparece no projeto:

###  1. Information Expert (Especialista)
> **Definição:** A responsabilidade deve ser atribuída à classe que possui a informação necessária.

* **Onde foi aplicado:**
    * `Classe Ingresso`: Ela própria controla nome, preço, estoque e métodos como `reduzirEstoque()` e `validarEstoque()`.
    * `Classe Venda`: Guarda informações da venda (tipo, quantidade, valor total, data).
* **Por que?** Essas classes possuem naturalmente os dados necessários para realizar suas responsabilidades — seguindo o GRASP.

###  2. Creator (Criador)
> **Definição:** A classe que possui ou usa muitos objetos de um tipo deve ser responsável por criá-los.

* **Onde foi aplicado:**
    * `Classe LojaIngressos`: Cria e registra os ingressos disponíveis na loja.
    * `Classe VendaController`: Cria objetos `Venda` quando o cliente realiza uma compra.
* **Por que?** Essas classes têm dependências diretas com os objetos criados, sendo os melhores candidatos para criá-los.

###  3. Controller
> **Definição:** O Controlador deve lidar com os eventos externos e a coordenação de ações do sistema.

* **Onde foi aplicado:**
    * `Classe VendaController`: Recebe a ação do usuário (“comprar ingresso”), verifica estoque, solicita atualização ao `Ingresso` e registra a `Venda`.
* **Por que?** Mantém o código organizado, evitando lógica pesada na classe `Main` e separando responsabilidades.

###  4. Low Coupling (Baixo Acoplamento)
> **Definição:** Minimizar dependências entre classes.

* **Onde foi aplicado:**
    * `Main` não sabe nada sobre lógica interna: delega tudo para o `VendaController`.
    * `Venda` não depende de implementação de estoque; apenas recebe dados já processados.
    * `LojaIngressos` apenas gerencia ingressos, sem depender do controller.
* **Por que?** Isso deixa o sistema mais flexível e fácil de alterar.

###  5. High Cohesion (Alta Coesão)
> **Definição:** Cada classe deve ter responsabilidades claras e bem definidas.

* **Onde foi aplicado:**
    * `Ingresso`: Somente dados e regras do ingresso.
    * `Venda`: Somente dados de venda.
    * `LojaIngressos`: Somente catálogo.
    * `VendaController`: Somente a lógica de venda.
    * `Main`: Apenas menus e interação simples.
* **Por que?** Evita classes gigantes com funções demais (“God class”).

###  6. Polymorphism (Polimorfismo)
> **Definição:** Usado quando há comportamentos variáveis que podem ser abstraídos por interfaces ou classes pai.

* **Onde foi aplicado:**
    * Na estrutura que permite criar diferentes tipos de ingressos. Se no futuro for criado `IngressoVIP` ou `IngressoMeiaEntrada`, basta herdar de `Ingresso`.
* **Por que?** O sistema foi preparado para expansão, mesmo ainda usando uma classe única.

###  7. Indirection (Indireção)
> **Definição:** Criar uma classe intermediária para reduzir acoplamento entre dois módulos.

* **Onde foi aplicado:**
    * `VendaController` funciona como intermediário entre `Main` e `LojaIngressos`.
* **Por que?** Assim `Main` não precisa conhecer detalhes sobre estoque, regras ou validações.

---

##  Exemplos de Execução

**1) Listar ingressos**
```
--- INGRESSOS DISPONÍVEIS ---
1. VIP - R$ 150.00 (Estoque: 20)
2. Pista - R$ 80.00 (Estoque: 50)
3. Camarote - R$ 250.00 (Estoque: 10)
2) Comprar ingresso
Bash

Digite o número do ingresso que deseja comprar: 1
Quantidade: 2

Compra realizada com sucesso!
Ingresso: VIP
Quantidade: 2
Total: R$ 300.00
3) Estoque insuficiente

Digite o número do ingresso que deseja comprar: 3
Quantidade: 20

Estoque insuficiente! Apenas 10 disponíveis.
4) Sair

Obrigado por usar o sistema! Até a próxima!


 Como Executar

1. Clone o repositório: https://github.com/DevNinee/Atividade_GRASP.git](https://github.com/DevNinee/Atividade_GRASP.git
2.    
3. Navegue até o diretório do projeto: cd Atividade_GRASP
4.    
5. Compile os arquivos Java: javac src/*.java
6.    
7. Execute a classe principal: java src/Main
8.     (Caso a pasta do projeto use outro layout, adapte os comandos conforme necessário.)

```
Tecnologias Utilizadas

 Java 8+ , 
 Paradigma de Orientação a Objetos (OO) , 
 Padrões GRASP.
 
 Autores
 Fabiana Souza e Erick Ferreira
