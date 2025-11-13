# 🎟️ Sistema de Vendas de Ingressos — Padrões GRASP

## 🎯 Introdução
Este projeto apresenta um **sistema simples para vendas de ingressos de shows**, desenvolvido em **Java** e aplicando os **padrões GRASP (General Responsibility Assignment Software Patterns)** para organizar o código, melhorar a manutenção e facilitar a evolução do sistema.

---

## 🏗️ Estrutura das Classes

| Classe | Responsabilidade |
|:-------|:----------------|
| **Ingresso** | Gerencia tipos, preço e estoque de ingressos. |
| **Cliente** | Modela dados do comprador. |
| **ItemCompra** | Vincula ingresso à quantidade comprada e calcula o subtotal. |
| **Compra** | Agrega itens e calcula o total da compra de cada cliente. |
| **CompraController** | Coordena todas as operações do sistema (iniciar compra, adicionar item, finalizar, listar compras). |
| **CompraView (opcional)** | Apresenta informações ao usuário. |

---

## 📊 Diagrama de Relacionamento entre Classes

```
┌─────────────────────┐
│ CompraController    │
│ (Controller)        │
│                     │
│ - compraRepository  │
│ - contadorCompras   │
│                     │
│ + iniciarNovaCompra()│
│ + adicionarItem()   │
│ + finalizarCompra() │
│ + listarCompras()   │
└──────────┬──────────┘
           │ coordena
           ▼
    ┌─────────────┐
    │   Compra    │
    │  (Creator)  │
    │             │
    │ - codigo    │
    │ - cliente   │◄──────────┐
    │ - itens[]   │           │
    │ - status    │           │ possui
    │             │           │
    │ + adicionar()│          │
    │ + calcular() │          │
    └──────┬──────┘           │
           │ cria e agrega    │
           ▼                  │
    ┌──────────────┐          │
    │  ItemCompra  │          │
    │  (Expert)    │          │
    │              │          │
    │ - ingresso   │◄─────┐   │
    │ - quantidade │      │   │
    │              │      │   │
    │ + calcular() │      │   │
    └──────────────┘      │   │
                          │   │
         ┌────────────────┼───┘
         │                │
         ▼                ▼
  ┌─────────────┐  ┌──────────┐
  │  Ingresso   │  │ Cliente  │
  │  (Expert)   │  │          │
  │             │  │ - nome   │
  │ - tipo      │  │ - email  │
  │ - preco     │  │          │
  │ - estoque   │  └──────────┘
  │             │
  │ + verificar()│
  │ + vender()  │
  └─────────────┘
```

---

## 📚 Padrões GRASP Aplicados

### 1️⃣ Information Expert (Especialista na Informação)
Cada classe executa operações relacionadas aos seus próprios dados, mantendo o código **coeso** e de fácil compreensão.

**Onde é aplicado:**
- `Ingresso`: verifica e atualiza o estoque.  
- `ItemCompra`: calcula o subtotal.  
- `Compra`: soma os valores dos itens.

**Exemplo:**
```java
public boolean verificarDisponibilidade(int quantidade) {
    return quantidadeDisponivel >= quantidade;
}
```

### 2️⃣ Creator (Criador)
Classes que agregam outros objetos são responsáveis por criá-los, mantendo o controle do ciclo de vida dos objetos relacionados.

**Onde é aplicado:**

- `Compra`: cria e adiciona `ItemCompra` em sua lista.

**Exemplo:**
```java
public void adicionarItem(Ingresso ingresso, int quantidade) {
    ItemCompra item = new ItemCompra(ingresso, quantidade);
    itens.add(item);
}
```

### 3️⃣ Controller (Controlador)
Centraliza e organiza as operações principais, separando a lógica de negócio da interface de apresentação.

**Onde é aplicado:**

- Classe: `CompraController`
- Métodos: `iniciarNovaCompra`, `adicionarItemNaCompra`, `finalizarCompra`, etc.

**Exemplo:**
```java
public Compra iniciarNovaCompra(Cliente cliente) {
    return new Compra(gerarCodigo(), cliente);
}
```

### 4️⃣ Low Coupling (Baixo Acoplamento)
Reduz dependências diretas entre as classes, tornando o sistema mais flexível e fácil de manter.

**Onde é aplicado:**

- O código principal (`Main`) interage apenas com `CompraController`.

**Exemplo:**
```java
CompraController controller = new CompraController();
controller.adicionarItemNaCompra(compra, ingresso, 2);
```

### 5️⃣ High Cohesion (Alta Coesão)
Cada classe permanece focada em sua responsabilidade específica, evitando sobrecarga de funções.

**Onde é aplicado:**

- `Ingresso` cuida apenas dos dados de ingresso.
- `CompraController` apenas coordena operações.

---

## 🧩 Benefícios Gerais
✅ Código mais modular e reutilizável.

✅ Facilidade de manutenção e testes.

✅ Organização clara das responsabilidades.

✅ Facilidade de evolução do sistema.

---

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 8 ou superior instalado

### Passos

1. **Clone o repositório:**
```bash
git clone https://github.com/DevNinee/Atividade_GRASP.git
cd Atividade_GRASP
```

2. **Compile o projeto:**
```bash
# Crie um diretório para as classes compiladas
mkdir -p build

# Compile todos os arquivos necessários
javac -d build src/model/Ingresso.java src/model/Cliente.java \
      src/model/ItemCompra.java src/model/Compra.java \
      src/repository/CompraRepository.java \
      src/service/IngressoService.java src/service/ClienteService.java \
      src/control/CompraController.java src/view/CompraView.java \
      src/SistemaIngressosShows.java
```

3. **Execute:**
```bash
java -cp build com.example.SistemaIngressosShows
```

### Saída Esperada
O sistema demonstrará:
- ✅ Criação de ingressos (Pista, VIP, Camarote)
- ✅ Cadastro de clientes
- ✅ Fluxo completo de duas compras
- ✅ Controle de estoque automático
- ✅ Relatório de vendas e resumo financeiro

---

## 📁 Estrutura do Projeto

```
Atividade_GRASP/
├── src/
│   ├── model/
│   │   ├── Ingresso.java
│   │   ├── Cliente.java
│   │   ├── ItemCompra.java
│   │   └── Compra.java
│   ├── repository/
│   │   └── CompraRepository.java
│   ├── service/
│   │   ├── IngressoService.java
│   │   └── ClienteService.java
│   ├── control/
│   │   └── CompraController.java
│   ├── view/
│   │   └── CompraView.java
│   └── SistemaIngressosShows.java
└── README.md
```

---

## 📄 Licença
Este projeto é distribuído sob a licença MIT.  
Sinta-se livre para usar e modificar conforme suas necessidades.

---

**Desenvolvido com 💻 e boas práticas GRASP.**
