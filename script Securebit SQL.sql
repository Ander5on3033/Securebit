-- Criação do Banco de Dados Securebit
CREATE DATABASE IF NOT EXISTS securebit_db;
USE securebit_db;

-- 1. Tabela de Usuários (Suporte ao AutenticacaoService e RNF003)
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_mestra_hash VARCHAR(255) NOT NULL, -- Armazena o hash seguro
    biometria_ativa BOOLEAN DEFAULT FALSE,   -- Configuração de biometria
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabela de Credenciais / Vault (Suporte a Login, LoginItem e Vault)
CREATE TABLE IF NOT EXISTS credenciais (
    id_credencial INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    titulo_servico VARCHAR(100) NOT NULL,    -- Ex: "Google", "GitHub"
    login_usuario VARCHAR(255) NOT NULL,     -- E-mail ou username da conta
    senha_encriptada TEXT NOT NULL,          -- Senha cifrada via CriptografiaUtil
    url_site VARCHAR(255),
    anotacoes TEXT,                          -- Informações adicionais do cofre
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- 3. Tabela de Tags / Categorias (Suporte a RF002X)
CREATE TABLE IF NOT EXISTS tags (
    id_tag INT AUTO_INCREMENT PRIMARY KEY,
    nome_tag VARCHAR(50) NOT NULL UNIQUE     -- Ex: "Trabalho", "Banco", "Pessoal"
);

-- 4. Tabela Associativa Credencial-Tags (Relação N:N)
CREATE TABLE IF NOT EXISTS credencial_tags (
    id_credencial INT NOT NULL,
    id_tag INT NOT NULL,
    PRIMARY KEY (id_credencial, id_tag),
    FOREIGN KEY (id_credencial) REFERENCES credenciais(id_credencial) ON DELETE CASCADE,
    FOREIGN KEY (id_tag) REFERENCES tags(id_tag) ON DELETE CASCADE
);

-- 5. Tabela de Logs de Auditoria (Suporte ao AuditorLog)
CREATE TABLE IF NOT EXISTS logs_auditoria (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,                          -- Referência ao usuário logado
    acao_realizada VARCHAR(255) NOT NULL,    -- Ex: "Tentativa de Login", "Credencial Criada"
    ip_origem VARCHAR(45),                   -- IP de origem da requisição
    data_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE SET NULL
);