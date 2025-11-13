# Sistema de Vendas de Ingressos - Padrões GRASP

Este projeto demonstra a aplicação dos principais padrões GRASP (General Responsibility Assignment Software Patterns) em um sistema de vendas de ingressos para shows.

## 📚 Sobre os Padrões GRASP

GRASP é um conjunto de princípios fundamentais para atribuição de responsabilidades em design orientado a objetos. Este projeto implementa os seguintes padrões:

## 🎯 Padrões GRASP Aplicados

### 1. Information Expert (Especialista da Informação)

**Definição**: Atribua uma responsabilidade à classe que possui as informações necessárias para cumpri-la.

**Aplicação no Projeto**:

#### Classe `Ingresso` (src/model/Ingresso.java)

```java
public boolean verificarDisponibilidade(int quantidade) {
    return quantidadeDisponivel >= quantidade;
}

public void vender(int quantidade) {
    if (verificarDisponibilidade(quantidade)) {
        quantidadeDisponivel -= quantidade;
    } else {
        throw new IllegalStateException("Ingressos insuficientes: " + tipo);
    }
}
```

**Justificativa**: A classe `Ingresso` é a especialista sobre seus próprios dados (tipo, preço, quantidade disponível). Portanto, ela é responsável por:
- Verificar sua própria disponibilidade
- Gerenciar seu próprio estoque
- Validar operações de venda

**Benefícios**:
- ✅ Alta coesão - a classe gerencia apenas suas próprias informações
- ✅ Baixo acoplamento - outras classes não precisam conhecer a estrutura interna do Ingresso
- ✅ Encapsulamento - os dados internos são protegidos

---

### 2. Creator (Criador)

**Definição**: Atribua à classe B a responsabilidade de criar instâncias da classe A se B contém, agrega, registra ou usa A.

**Aplicação no Projeto**:

#### Classe `Compra` (conceitual)

A classe `Compra` é responsável por criar instâncias de `ItemCompra`:

```java
public void adicionarItem(Ingresso ingresso, int quantidade) {
    ItemCompra item = new ItemCompra(ingresso, quantidade); // Creator pattern
    itens.add(item);
    ingresso.vender(quantidade);
}
```

**Justificativa**: `Compra` é o criador natural de `ItemCompra` porque:
- Contém/agrega múltiplos `ItemCompra`
- Registra os itens da compra
- Tem os dados necessários para inicializar `ItemCompra`

**Benefícios**:
- ✅ Responsabilidade clara de criação
- ✅ Reduz dependências desnecessárias
- ✅ Facilita manutenção

---

### 3. Controller (Controlador)

**Definição**: Atribua a responsabilidade de lidar com eventos do sistema a uma classe que representa o sistema como um todo ou um cenário de caso de uso.

**Aplicação no Projeto**:

#### Classe `CompraController` (src/control/CompraController.java)

```java
public class CompraController {
    private CompraRepository compraRepository;
    private int contadorCompras;

    public Compra iniciarNovaCompra(Cliente cliente) {
        String codigoCompra = "C" + String.format("%04d", contadorCompras++);
        Compra compra = new Compra(codigoCompra, cliente);
        return compra;
    }

    public void adicionarItemNaCompra(Compra compra, Ingresso ingresso, int quantidade) {
        try {
            compra.adicionarItem(ingresso, quantidade);  // Delega para o especialista
        } catch (IllegalStateException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public void finalizarCompra(Compra compra) {
        compra.finalizarCompra();  // Delega para o especialista
        compraRepository.save(compra);
    }
}
```

**Justificativa**: `CompraController` atua como controlador porque:
- Coordena operações de compra
- Recebe requisições da camada de apresentação
- Delega responsabilidades aos especialistas (Compra, Ingresso)
- Não contém lógica de negócio, apenas coordenação

**Benefícios**:
- ✅ Centraliza o fluxo de controle
- ✅ Separa interface da lógica de negócio
- ✅ Facilita testes e manutenção
- ✅ Promove baixo acoplamento

---

### 4. Low Coupling (Baixo Acoplamento)

**Definição**: Minimize as dependências entre classes para reduzir o impacto de mudanças.

**Aplicação no Projeto**:

#### Arquitetura MVC (Model-View-Controller)

```
src/
├── model/          # Classes de domínio (Ingresso, Cliente, Compra)
├── view/           # Interface com usuário (CompraView)
└── control/        # Coordenadores (CompraController)
```

**Exemplo de Baixo Acoplamento**:

```java
// CompraController coordena sem conhecer detalhes internos
public void adicionarItemNaCompra(Compra compra, Ingresso ingresso, int quantidade) {
    compra.adicionarItem(ingresso, quantidade);  // Delega, não implementa
}
```

**Justificativa**:
- A camada View não conhece detalhes do Model
- O Controller não implementa lógica de negócio
- Classes podem ser modificadas independentemente

**Benefícios**:
- ✅ Facilita manutenção
- ✅ Permite reutilização
- ✅ Reduz impacto de mudanças
- ✅ Facilita testes unitários

---

### 5. High Cohesion (Alta Coesão)

**Definição**: Mantenha as responsabilidades de cada classe focadas e relacionadas.

**Aplicação no Projeto**:

#### Classe `Ingresso`
- **Única responsabilidade**: Gerenciar informações e operações relacionadas a ingressos
- Métodos coesos: `verificarDisponibilidade()`, `vender()`, getters/setters

#### Classe `CompraController`
- **Única responsabilidade**: Coordenar operações de compra
- Métodos coesos: `iniciarNovaCompra()`, `adicionarItemNaCompra()`, `finalizarCompra()`

#### Classe `CompraView`
- **Única responsabilidade**: Apresentar informações ao usuário
- Métodos coesos: `exibirComprasRealizadas()`, `exibirResumoFinanceiro()`

**Benefícios**:
- ✅ Classes fáceis de entender
- ✅ Código mais manutenível
- ✅ Maior reusabilidade
- ✅ Facilita testes

---

### 6. Polymorphism (Polimorfismo)

**Definição**: Use operações polimórficas para lidar com alternativas baseadas em tipo.

**Aplicação Potencial no Projeto**:

Embora não explicitamente implementado, o projeto poderia estender para usar polimorfismo:

```java
// Interface para diferentes tipos de desconto
public interface CalculadorDesconto {
    double calcularDesconto(double valorTotal);
}

// Diferentes estratégias de desconto
public class DescontoVIP implements CalculadorDesconto {
    public double calcularDesconto(double valorTotal) {
        return valorTotal * 0.10; // 10% desconto
    }
}

public class DescontoEstudante implements CalculadorDesconto {
    public double calcularDesconto(double valorTotal) {
        return valorTotal * 0.20; // 20% desconto
    }
}
```

---

### 7. Pure Fabrication (Fabricação Pura)

**Definição**: Crie classes artificiais que não representam conceitos do domínio quando necessário para manter design sólido.

**Aplicação no Projeto**:

#### Classes de Serviço (Service)
```java
// IngressoService - não é um conceito do domínio real
public class IngressoService {
    private List<Ingresso> ingressos;
    
    public void criarIngresso(String tipo, double preco, int quantidade) { ... }
    public Optional<Ingresso> getIngresso(String tipo) { ... }
    public List<Ingresso> listarIngressos() { ... }
}
```

#### Repositórios (Repository)
```java
// CompraRepository - fabricação pura para persistência
public class CompraRepository {
    public void save(Compra compra) { ... }
    public List<Compra> findAll() { ... }
}
```

**Justificativa**:
- Serviços e repositórios não são conceitos do mundo real
- São criados para manter alta coesão e baixo acoplamento
- Separam concerns de persistência e operações CRUD

**Benefícios**:
- ✅ Mantém classes de domínio limpas
- ✅ Centraliza operações de persistência
- ✅ Facilita mudanças de tecnologia

---

### 8. Indirection (Indireção)

**Definição**: Atribua responsabilidade a um objeto intermediário para mediar entre componentes.

**Aplicação no Projeto**:

#### CompraController como Mediador

```java
// A View não comunica diretamente com o Model
CompraView (Interface) --> CompraController (Mediador) --> Compra/Ingresso (Model)
```

**Exemplo**:
```java
// Sem indireção (acoplamento direto):
// view.exibirCompra(compra.calcularTotal()); // View conhece Compra

// Com indireção (usando Controller):
// CompraController medeia a comunicação
public void exibirResumoFinanceiro() {
    double total = controller.calcularTotalCompras(); // Controller medeia
    System.out.printf("Total: R$ %.2f\n", total);
}
```

**Benefícios**:
- ✅ Reduz acoplamento direto entre View e Model
- ✅ Facilita mudanças independentes
- ✅ Centraliza lógica de coordenação

---

### 9. Protected Variations (Variações Protegidas)

**Definição**: Proteja elementos contra variações em outros elementos usando interfaces e polimorfismo.

**Aplicação no Projeto**:

#### Interface de Repositório
```java
// Interface protege contra mudanças na implementação de persistência
public interface RepositorioCompras {
    void salvar(Compra compra);
    List<Compra> buscarTodas();
}

// Implementação pode variar sem afetar o Controller
public class CompraRepositoryMemoria implements RepositorioCompras { ... }
public class CompraRepositoryBancoDados implements RepositorioCompras { ... }
```

**Benefícios**:
- ✅ Facilita troca de implementações
- ✅ Protege código cliente de mudanças
- ✅ Permite testes com mocks

---

## 🏗️ Estrutura do Projeto

```
Atividade_GRASP/
├── src/
│   ├── model/              # Entidades do domínio
│   │   ├── Cliente.java
│   │   ├── Ingresso.java
│   │   ├── Compra.java (referenciado)
│   │   ├── ItemCompra.java (referenciado)
│   │   └── ...
│   ├── view/               # Camada de apresentação
│   │   ├── CompraView.java
│   │   └── ...
│   ├── control/            # Controladores
│   │   ├── CompraController.java
│   │   └── ...
│   ├── service/            # Serviços (Pure Fabrication)
│   │   ├── IngressoService.java (referenciado)
│   │   └── ClienteService.java (referenciado)
│   └── repository/         # Repositórios (Pure Fabrication)
│       └── CompraRepository.java (referenciado)
└── README.md
```

---

## 🧪 Passo a Passo para Testes

### Pré-requisitos

- Java JDK 8 ou superior instalado
- Conhecimento básico de linha de comando

### Opção 1: Teste Manual Passo a Passo

#### 1. Verificar Instalação do Java

```bash
java -version
javac -version
```

Deve exibir a versão do Java instalada (recomendado: Java 8+).

#### 2. Navegar até o Diretório do Projeto

```bash
cd /caminho/para/Atividade_GRASP
```

#### 3. Criar Classes Faltantes (se necessário)

O projeto referencia algumas classes que podem não estar completas. Antes de compilar, verifique se todas as classes existem:

**Classes necessárias para compilação completa:**
- `src/model/Compra.java`
- `src/model/ItemCompra.java`
- `src/service/IngressoService.java`
- `src/service/ClienteService.java`
- `src/repository/CompraRepository.java`

#### 4. Compilar o Projeto

```bash
# Criar diretório para arquivos compilados
mkdir -p bin

# Compilar todos os arquivos Java
javac -d bin src/**/*.java src/*.java
```

**Nota**: Se houver erros de compilação devido a classes faltantes, será necessário implementá-las primeiro.

#### 5. Executar o Sistema

```bash
# Executar a classe principal
java -cp bin com.example.SistemaIngressosShows
```

#### 6. Verificar Saída Esperada

A execução deve mostrar:
- ✅ CRUD de Ingressos (Create, Read)
- ✅ CRUD de Clientes (Create)
- ✅ Fluxo de Compra 1 (Ana Souza)
- ✅ Fluxo de Compra 2 (Carlos Lima)
- ✅ Relatório de Vendas
- ✅ Resumo Financeiro
- ✅ Estoque atualizado

---

### Opção 2: Teste dos Padrões GRASP Individualmente

#### Teste 1: Information Expert (Ingresso)

```bash
# Criar arquivo de teste
cat > TestIngresso.java << 'EOF'
import model.Ingresso;

public class TestIngresso {
    public static void main(String[] args) {
        System.out.println("=== TESTE: Information Expert ===");
        
        // Criar ingresso
        Ingresso ingresso = new Ingresso("VIP", 200.0, 10);
        System.out.println("Ingresso criado: " + ingresso);
        
        // Testar verificação de disponibilidade (Information Expert)
        boolean disponivel = ingresso.verificarDisponibilidade(5);
        System.out.println("Disponível 5 ingressos? " + disponivel);
        
        // Testar venda (Information Expert)
        ingresso.vender(5);
        System.out.println("Após venda: " + ingresso);
        
        // Tentar venda com quantidade insuficiente
        try {
            ingresso.vender(10);
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
        
        System.out.println("✅ Teste concluído!");
    }
}
EOF

# Compilar e executar
javac -cp src TestIngresso.java
java -cp .:src TestIngresso
```

**Saída esperada:**
```
=== TESTE: Information Expert ===
Ingresso criado: Ingresso [VIP] R$ 200.00 - Disponível: 10
Disponível 5 ingressos? true
Após venda: Ingresso [VIP] R$ 200.00 - Disponível: 5
Erro esperado: Ingressos insuficientes: VIP
✅ Teste concluído!
```

#### Teste 2: Controller (CompraController)

```bash
# Criar arquivo de teste
cat > TestController.java << 'EOF'
import controller.CompraController;
import model.Cliente;
import model.Ingresso;

public class TestController {
    public static void main(String[] args) {
        System.out.println("=== TESTE: Controller Pattern ===");
        
        // Criar dependências
        Cliente cliente = new Cliente("João Silva", "joao@email.com");
        Ingresso ingresso = new Ingresso("Pista", 100.0, 50);
        
        // Usar o Controller (padrão Controller)
        CompraController controller = new CompraController();
        
        // Controller coordena operações
        Compra compra = controller.iniciarNovaCompra(cliente);
        controller.adicionarItemNaCompra(compra, ingresso, 3);
        controller.finalizarCompra(compra);
        
        // Verificar resultado
        controller.listarComprasRealizadas();
        System.out.println("Total: R$ " + controller.calcularTotalCompras());
        
        System.out.println("✅ Teste concluído!");
    }
}
EOF
```

#### Teste 3: High Cohesion & Low Coupling

```bash
# Verificar que cada classe tem responsabilidade única
echo "=== TESTE: High Cohesion & Low Coupling ==="
echo ""
echo "Verificando responsabilidades das classes:"
echo ""
echo "Ingresso:"
grep -n "public.*(" src/model/Ingresso.java | grep -E "(verificar|vender|get|set)"
echo ""
echo "CompraController:"
grep -n "public.*(" src/control/CompraController.java | grep -E "(iniciar|adicionar|finalizar|listar|calcular)"
echo ""
echo "CompraView:"
grep -n "public.*(" src/view/CompraView.java | grep -E "(exibir)"
echo ""
echo "✅ Cada classe tem métodos coesos relacionados à sua responsabilidade!"
```

---

### Opção 3: Teste com Casos de Uso Específicos

#### Caso de Uso 1: Compra Simples

```bash
# Teste: Cliente compra 2 ingressos de Pista
# Padrões testados: Information Expert, Controller, Creator
```

**Passos:**
1. Criar cliente
2. Criar ingresso com estoque
3. Usar controller para iniciar compra
4. Adicionar itens
5. Finalizar compra
6. Verificar estoque atualizado

**Comportamento esperado:**
- ✅ Compra criada com sucesso
- ✅ Estoque decrementado corretamente
- ✅ Total calculado corretamente

#### Caso de Uso 2: Tentativa de Compra Sem Estoque

```bash
# Teste: Cliente tenta comprar mais ingressos do que o disponível
# Padrões testados: Information Expert (validação)
```

**Passos:**
1. Criar ingresso com estoque limitado (ex: 5 unidades)
2. Tentar comprar mais que o disponível (ex: 10 unidades)
3. Verificar exceção lançada

**Comportamento esperado:**
- ✅ Exceção `IllegalStateException` lançada
- ✅ Mensagem de erro descritiva
- ✅ Estoque não alterado

#### Caso de Uso 3: Múltiplas Compras

```bash
# Teste: Vários clientes comprando ingressos
# Padrões testados: Controller, Low Coupling, High Cohesion
```

**Passos:**
1. Criar múltiplos clientes
2. Criar diferentes tipos de ingressos
3. Processar várias compras
4. Gerar relatório consolidado

**Comportamento esperado:**
- ✅ Todas as compras registradas
- ✅ Total de vendas correto
- ✅ Estoque de cada ingresso atualizado corretamente

---

## 📊 Checklist de Validação dos Padrões GRASP

Após os testes, verifique se os padrões estão funcionando:

- [ ] **Information Expert**: Ingresso gerencia seu próprio estoque
- [ ] **Creator**: Compra cria seus próprios ItemCompra
- [ ] **Controller**: CompraController coordena operações sem lógica de negócio
- [ ] **Low Coupling**: Mudanças em View não afetam Model
- [ ] **High Cohesion**: Cada classe tem responsabilidade única e focada
- [ ] **Pure Fabrication**: Services e Repositories existem para suporte
- [ ] **Indirection**: Controller medeia comunicação entre View e Model
- [ ] **Protected Variations**: Mudanças em implementação não afetam interfaces

---

## 🐛 Troubleshooting

### Erro: "package does not exist"

**Causa**: Classes referenciadas não foram encontradas.

**Solução**:
1. Verificar se todos os arquivos .java existem
2. Verificar estrutura de pacotes (package declarations)
3. Compilar na ordem correta (dependências primeiro)

### Erro: "class not found"

**Causa**: Classpath incorreto ao executar.

**Solução**:
```bash
# Especificar classpath explicitamente
java -cp bin:. com.example.SistemaIngressosShows
```

### Compilação com Avisos

**Causa**: Uso de tipos raw ou outras práticas desatualizadas.

**Solução**:
```bash
# Compilar com avisos detalhados
javac -Xlint:all -d bin src/**/*.java
```

---

## 📝 Exercícios Práticos

### Exercício 1: Adicionar Novo Padrão
Implemente desconto para estudantes usando o padrão **Polymorphism**.

### Exercício 2: Refatoração
Identifique uma classe com baixa coesão e refatore aplicando **High Cohesion**.

### Exercício 3: Extensão
Adicione persistência em banco de dados usando **Protected Variations**.

---

## 📚 Referências

- Livro: "Applying UML and Patterns" - Craig Larman
- Padrões GRASP: https://en.wikipedia.org/wiki/GRASP_(object-oriented_design)
- Clean Code: https://cleancoders.com/

---

## 👥 Autores

- **Fabiana Souza** - Desenvolvimento inicial
- Projeto acadêmico para demonstração de padrões GRASP

---

## 📄 Licença

Este projeto é de uso educacional.

---

## 🎓 Conclusão

Este projeto demonstra como os padrões GRASP ajudam a criar software:
- ✅ Mais manutenível
- ✅ Mais testável
- ✅ Mais reutilizável
- ✅ Com baixo acoplamento
- ✅ Com alta coesão

Cada padrão GRASP resolve problemas específicos de design e, quando aplicados juntos, resultam em uma arquitetura sólida e bem estruturada.
