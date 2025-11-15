# Padrões GRASP neste projeto

Este documento explica por que os padrões GRASP foram aplicados (ou surgem naturalmente) no projeto `Atividade_GRASP` e aponta exemplos concretos em arquivos/classes do código.

Objetivo: dar contexto para as decisões de design e facilitar evolução/ensaios futuros.

---

## 1) Information Expert (Especialista em Informação)

- O que é: atribuir responsabilidade à classe que possui a informação necessária para cumpri-la.
- Onde aparece: `src/main/java/com/example/model/Compra.java`, `Ingresso.java`, `ItemCompra.java`, `Cliente.java`.
- Por que foi usado: a lógica de calcular o total (`Compra.calcularTotal()`), adicionar itens (`Compra.adicionarItem(...)`) e gerenciar estoque (`Ingresso.vender()`, `verificarDisponibilidade()`) depende diretamente dos dados guardados nessas entidades. Colocar essa lógica nas próprias classes mantém alta coesão e evita espalhar regras de negócio por várias camadas.

Exemplo prático:
- `Compra.calcularTotal()` sabe sobre seus itens e preços — é o lugar mais natural para implementar o cálculo.

Consequência:
- Facilita teste e manutenção; regras centradas no domínio.

---

## 2) Creator (Criador)

- O que é: delegar a criação de um objeto para a classe que tem a maior informação necessária para criá-lo.
- Onde aparece: `CompraController` e nos serviços (`ClienteService`, `IngressoService`) quando criam `Compra` e `ItemCompra`.
- Por que: o controller/serviço orquestra o fluxo e tem os dados necessários para construir instâncias do domínio. Isso mantém outros objetos livres da responsabilidade de saber como montar um novo agregado.

Consequência:
- Facilita centralizar a construção e initialização de objetos e manter o domínio consistente.

---

## 3) Controller

- O que é: objeto responsável por lidar com operações do sistema, recebendo eventos/entradas e coordenando outras classes.
- Onde aparece: `CompraController` e a classe de execução `SistemaIngressosShows` (orquestrador de demonstração).
- Por que: separar a lógica de aplicação (fluxos de compra, ordens de operação) da lógica de domínio (regras de preço, estoque) melhora organização e permite testes focados.

Consequência:
- Melhor separação de responsabilidades; `CompraController` traduz ações do sistema em chamadas para services e repositórios.

---

## 4) Low Coupling (Baixo Acoplamento) e High Cohesion (Alta Coesão)

- O que são: projetar classes com responsabilidades bem definidas (coesa) e minimizar dependências fortes entre módulos (baixo acoplamento).
- Onde appear: arquitetura em camadas (pacotes `controller`, `service`, `repository`, `model`, `view`).
- Por que: cada camada tem um papel (apresentação, orquestração, persistência, domínio). Isso facilita alterações isoladas; por exemplo, trocar a implementação de persistência só deve afetar `repository`.

Consequência:
- Código mais fácil de entender, testar e modificar. Torna mais simples introduzir injeção de dependência ou persistência real futuramente.

---

## 5) Indirection (Indireção)

- O que é: introduzir um objeto intermediário para reduzir dependências diretas entre módulos.
- Onde aparece: as classes `*Repository` (por exemplo `IngressoRepository`) atuam de intermediárias entre serviços/controllers e a fonte de dados (in-memory Map).
- Por que: isola a lógica de armazenamento, permitindo trocar o mecanismo sem mexer no consumidor.

Consequência:
- Facilita substituição/inversão de dependência (por exemplo trocar por um repositório JDBC/ORM).

---

## 6) Pure Fabrication

- O que é: criar classes artificiais para suportar responsabilidades que não pertencem a um conceito do domínio, com objetivo de baixo acoplamento ou alta coesão.
- Onde aparece: `CompraRepository`, `ClienteRepository`, `IngressoRepository` e possivelmente `CompraView`.
- Por que: persistência e apresentação não são conceitos de domínio puro aqui; são responsabilidades auxiliares criadas para manter o domínio limpo.

Consequência:
- Melhora separação e testabilidade; justifica a existência de classes que não modelam entidades do mundo real, mas ajudam o design.

---

## 7) Oportunidade: Polymorphism / Protected Variations

- Estado atual: ainda não há interfaces formais para repositórios, mas a separação facilita a introdução.
- Sugestão: extrair interfaces (`IngressoRepository`, `CompraRepository`, etc.) e programar contra as interfaces. Assim garante-se o isolamento frente a mudanças na implementação e aplica-se Protected Variations.

Benefício:
- Torna mais simples trocar implementações e facilita mocks em testes unitários.

---

## Recomendações práticas para evoluir o design

1. Extrair interfaces para os repositórios e usar injeção de dependência (construtor) nas services e controller.
2. Mover validações de domínio que dependem de dados do modelo para as próprias entidades (Information Expert).
3. Tornar repositórios thread-safe se for necessário suportar concorrência.
4. Adicionar testes unitários que verifiquem comportamento das entidades (e.g., `Compra`, `Ingresso`) isoladamente.

---

## Referências rápidas

- Exercício GRASP: Craig Larman — "Applying UML and Patterns" (capítulo sobre GRASP)
- Notas de design: priorizar clareza e evolutibilidade; os padrões GRASP são guias, não regras rígidas.

---

## Próximos passos que posso executar

- Gerar automaticamente interfaces para os repositórios e adaptar `*Service`/`*Controller` para depender das interfaces (faço as alterações e rodo os testes).
- Criar um `PATTERNS-README.md` com trechos de código anotados explicando onde cada padrão aparece.
- Apenas listar as linhas/trechos nos arquivos que exemplificam cada padrão (se preferir não modificar o código agora).

Indique qual opção prefere.
