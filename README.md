# FitFlow - Gerenciamento de Academia

Sistema de gerenciamento de academia em Java puro (console), com persistencia em PostgreSQL via Hibernate ORM.

## Especificacoes do projeto

- Linguagem: Java 21
- Paradigma: POO
- Tipo: Aplicacao console
- Build: Maven
- Persistencia: Hibernate ORM + PostgreSQL
- API de persistencia: Jakarta Persistence (JPA)

## Funcionalidades principais

- Gestao de alunos (cadastrar, listar, alterar, remover)
- Gestao de personais
- Gestao de funcionarios
- Gestao de planos
- Gestao de treinos
- Gestao de pagamentos
- Gestao de desempenho fisico (IMC)
- Login por perfil (aluno, personal e funcionario)

## Estrutura de pastas

- `src/Entidade`: modelos de dominio e entidades JPA
- `src/Repositorio`: acesso a dados (Hibernate Session)
- `src/Servico`: regras de negocio
- `src/Gui`: menus e fluxo da aplicacao
- `src/Infra`: infraestrutura (HibernateUtil)
- `src/main/resources`: configuracoes (`hibernate.cfg.xml`)
- `sql`: scripts SQL de criacao do banco e schema

## Entidades persistidas

- `Aluno`
- `Personal`
- `Funcionario`
- `Plano`
- `Treino`
- `Pagamento`
- `Desempenho`

## Pre-requisitos para rodar na sua maquina

- JDK 21 instalado e configurado
- PostgreSQL instalado e em execucao
- Maven instalado (ou IntelliJ com suporte Maven)
- IntelliJ IDEA (recomendado)

## Configuracao do banco

1. Crie o banco `fitflow` e o schema com os scripts:
   - `sql/01_create_database.sql`
   - `sql/02_create_schema.sql`
2. Ajuste usuario/senha/URL em `src/main/resources/hibernate.cfg.xml`:
   - `hibernate.connection.url`
   - `hibernate.connection.username`
   - `hibernate.connection.password`

Exemplo de URL:

```text
jdbc:postgresql://localhost:5432/fitflow
```

## Como rodar o projeto (IntelliJ)

1. Abra a pasta do projeto.
2. Aguarde o IntelliJ reconhecer o `pom.xml`.
3. Em `Project Structure`:
   - `Project SDK`: JDK 21
   - `Modules`: pasta `src` marcada como `Sources`
4. Crie/edite a configuracao de execucao:
   - Main class: `Gui.MenuInicial`
   - Module: `fitflow-gerenciamento-de-academia` (ou o modulo do projeto)
5. Execute `Run`.

## Como rodar com Maven (opcional)

Compilar:

```bash
mvn -DskipTests compile
```

Executar pela IDE usando a classe principal `Gui.MenuInicial`.

## Como verificar dados no PostgreSQL (pgAdmin)

1. Abra `Databases > fitflow > Schemas > public > Tables`.
2. Clique com botao direito na tabela (ex.: `aluno`).
3. Selecione `View/Edit Data > All Rows`.

Ou use Query Tool:

```sql
SELECT * FROM aluno;
SELECT * FROM personal;
SELECT * FROM funcionario;
SELECT * FROM plano;
SELECT * FROM treino;
SELECT * FROM pagamento;
SELECT * FROM desempenho;
```

## Observacoes

- O projeto nao usa Spring Boot.
- A criacao/atualizacao de schema no Hibernate esta configurada por `hibernate.hbm2ddl.auto`.
- Se aparecer muito log no console, ajuste `show_sql` e `format_sql` no `hibernate.cfg.xml`.
