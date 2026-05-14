

CREATE TABLE IF NOT EXISTS aluno (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    cep VARCHAR(12),
    bairro VARCHAR(120),
    nome_rua VARCHAR(120),
    complemento VARCHAR(120),
    num_casa INTEGER
);

CREATE TABLE IF NOT EXISTS personal (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    cep VARCHAR(12),
    bairro VARCHAR(120),
    nome_rua VARCHAR(120),
    complemento VARCHAR(120),
    num_casa INTEGER,
    craf VARCHAR(30) UNIQUE,
    salario DOUBLE PRECISION,
    horario_trabalho DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS funcionario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    cep VARCHAR(12),
    bairro VARCHAR(120),
    nome_rua VARCHAR(120),
    complemento VARCHAR(120),
    num_casa INTEGER,
    cargo VARCHAR(80),
    salario DOUBLE PRECISION,
    horario_trabalho DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS plano (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    valor DOUBLE PRECISION NOT NULL,
    duracao INTEGER NOT NULL,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS treino (
    id BIGSERIAL PRIMARY KEY,
    id_aluno BIGINT NOT NULL REFERENCES aluno(id) ON DELETE RESTRICT,
    id_personal BIGINT NOT NULL REFERENCES personal(id) ON DELETE RESTRICT,
    data_treino VARCHAR(40) NOT NULL,
    horario VARCHAR(40) NOT NULL,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pagamento (
    id BIGSERIAL PRIMARY KEY,
    aluno_id BIGINT NOT NULL REFERENCES aluno(id) ON DELETE RESTRICT,
    plano_id BIGINT NOT NULL REFERENCES plano(id) ON DELETE RESTRICT,
    valor DOUBLE PRECISION NOT NULL,
    data_pagamento DATE NOT NULL,
    data_vencimento DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    metodo_pagamento VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS desempenho (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    peso DOUBLE PRECISION NOT NULL,
    altura DOUBLE PRECISION NOT NULL,
    imc DOUBLE PRECISION NOT NULL,
    data_avaliacao DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS aluno_plano (
    aluno_id BIGINT NOT NULL REFERENCES aluno(id) ON DELETE CASCADE,
    plano_id BIGINT NOT NULL REFERENCES plano(id) ON DELETE CASCADE,
    PRIMARY KEY (aluno_id, plano_id)
);

CREATE TABLE IF NOT EXISTS aluno_treino (
    aluno_id BIGINT NOT NULL REFERENCES aluno(id) ON DELETE CASCADE,
    treino_id BIGINT NOT NULL REFERENCES treino(id) ON DELETE CASCADE,
    PRIMARY KEY (aluno_id, treino_id)
);

CREATE TABLE IF NOT EXISTS aluno_dias_treino (
    aluno_id BIGINT NOT NULL REFERENCES aluno(id) ON DELETE CASCADE,
    dia_treino DATE NOT NULL,
    PRIMARY KEY (aluno_id, dia_treino)
);

CREATE TABLE IF NOT EXISTS personal_alunos (
    personal_id BIGINT NOT NULL REFERENCES personal(id) ON DELETE CASCADE,
    nome_aluno VARCHAR(120) NOT NULL,
    PRIMARY KEY (personal_id, nome_aluno)
);

CREATE TABLE IF NOT EXISTS funcionario_dias_trabalhados (
    funcionario_id BIGINT NOT NULL REFERENCES funcionario(id) ON DELETE CASCADE,
    dia_trabalho DATE NOT NULL,
    PRIMARY KEY (funcionario_id, dia_trabalho)
);
