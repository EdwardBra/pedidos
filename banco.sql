CREATE DATABASE nome_do_banco_de_dados;
USE nome_do_banco_de_dados;

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_controle VARCHAR(255) NOT NULL UNIQUE,
    data_cadastro DATE,
    nome VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    quantidade INT DEFAULT 1,
    codigo_cliente INT,
    valor_total DECIMAL(10, 2),
    FOREIGN KEY (codigo_cliente) REFERENCES clientes(id)
);

-- Inserir clientes fictícios
INSERT INTO clientes (nome) VALUES ('Cliente 1'), ('Cliente 2'), ('Cliente 3'), ('Cliente 4'), ('Cliente 5'), ('Cliente 6'), ('Cliente 7'), ('Cliente 8'), ('Cliente 9'), ('Cliente 10');
