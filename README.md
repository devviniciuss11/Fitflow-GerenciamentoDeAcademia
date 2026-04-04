<div align="center">
  <h1>FitFlow — Gerenciamento de Academia</h1>
  <p>
    Sistema em <b>Java</b> para gerenciamento de academia via console, com cadastro e administração de alunos, planos e treinos.
  </p>

  <!-- Badges (opcionais) -->
  <p>
    <img alt="Java" src="https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white">
    <img alt="Status" src="https://img.shields.io/badge/Status-Em%20desenvolvimento-blue">
    <img alt="License" src="https://img.shields.io/badge/License-MIT-green">
  </p>
</div>



<div class="card">
  <h2>Visão Geral</h2>
  <p class="muted">
    O <b>FitFlow</b> é um projeto acadêmico de gerenciamento de academia organizado em camadas simples:
    <b>Entidades</b> (modelos), <b>Repositórios</b> (armazenamento em memória), <b>Serviços</b> (regras de negócio)
    e <b>GUI</b> (menus de console).
  </p>

  <div>
    <span class="tag">Java 21</span>
    <span class="tag">Console App</span>
    <span class="tag">CRUD</span>
    <span class="tag">POO</span>
  </div>
</div>

<div class="grid">
  <div class="card">
    <h3>Principais Funcionalidades</h3>
    <ul>
      <li><b>Alunos</b>: cadastro, listagem, alteração e remoção</li>
      <li><b>Presença</b>: marcação de presença por aluno e histórico</li>
      <li><b>Planos</b>: estrutura para cadastro e gerenciamento</li>
      <li><b>Personal</b>: estrutura para cadastro e gerenciamento</li>
      <li><b>Treinos</b>: estrutura para ficha/treinos</li>
    </ul>
  </div>

  <div class="card">
    <h3>Arquitetura (Pastas)</h3>
    <ul>
      <li><span class="k">src/Entidade</span> — classes de domínio (Aluno, Plano, Treino, ...)</li>
      <li><span class="k">src/Repositorio</span> — armazenamento em memória (ex.: listas)</li>
      <li><span class="k">src/Servico</span> — regras e operações do sistema</li>
      <li><span class="k">src/Gui</span> — menus e interação via terminal</li>
      <li><span class="k">src/Interfacess</span> — contratos/ações (adicionar/remover/alterar)</li>
    </ul>
  </div>
</div>

<div class="card">
  <h2>Como Executar</h2>
  <p class="muted">
    Você pode rodar o projeto pela IDE (IntelliJ/Eclipse) ou pelo terminal.
  </p>

  <h3>Pré-requisitos</h3>
  <ul>
    <li>Java <b>21</b> instalado</li>
    <li>Git (opcional)</li>
  </ul>

  <h3>Executando pela IDE</h3>
  <ol>
    <li>Abra a pasta do projeto na IDE</li>
    <li>Localize a classe principal em <b>src/Servico</b> (ex.: <code>FitFlow</code>)</li>
    <li>Execute (Run)</li>
  </ol>
