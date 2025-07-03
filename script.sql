-- Criar o banco de dados e selecioná-lo
CREATE DATABASE Mercado;
USE Mercado;
select * from Cliente;
select * from Funcionario;
select * from Produto;
select * from Venda;
select * from VendaProduto;
alter table Cliente name Clientes;
-- Tabela Cliente
CREATE TABLE Cliente(
    idCliente INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    nomeCliente VARCHAR(255) NOT NULL,
    cpfCliente VARCHAR(11) NOT NULL,
    dataNasc DATE NOT NULL,
    telefone VARCHAR(11),
    email VARCHAR(255),
    endereco VARCHAR(255) NOT NULL,
    genero VARCHAR(1) NOT NULL
);

-- Tabela Funcionario
CREATE TABLE Funcionario (
    idFuncionario INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    nomeFuncionario VARCHAR(255) NOT NULL,
    cpfFuncionario VARCHAR(11) NOT NULL,
    dataNasc DATE NOT NULL,
    telefone VARCHAR(11),
    email VARCHAR(255),
    endereco VARCHAR(255) NOT NULL,
    genero VARCHAR(1) NOT NULL,
    cargo VARCHAR(20) NOT NULL,
    nivel INT NOT NULL,
	salario money default 1512.00
);

-- Tabela Produto
CREATE TABLE Produto (
    idProduto INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    nomeProduto VARCHAR(255) NOT NULL,
    codBarra VARCHAR(13) NOT NULL,
    tipoUn VARCHAR(2) NOT NULL,
    precoUn MONEY NOT NULL,
    estoque INT,
    dataFab DATE NOT NULL,
    dataVal DATE
);

-- Tabela Venda
CREATE TABLE Venda (
    idVenda INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    idCliente INT NOT NULL FOREIGN KEY REFERENCES Cliente(idCliente),
    idFuncionario INT NOT NULL FOREIGN KEY REFERENCES Funcionario(idFuncionario),
    valorTotal MONEY NOT NULL,
    quantTotal INT NOT NULL,
    dataVenda DATE NOT NULL DEFAULT GETDATE()
);

-- Tabela VendaProduto
CREATE TABLE VendaProduto (
    idVendaProduto INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    idVenda INT NOT NULL FOREIGN KEY REFERENCES Venda(idVenda),
    idProduto INT NOT NULL FOREIGN KEY REFERENCES Produto(idProduto),
    precoUn MONEY NOT NULL,
    quantidade FLOAT NOT NULL,
    precoTotal MONEY NOT NULL
);

-- View de Relatório de Vendas
CREATE VIEW VW_RelatrioVendas AS 
SELECT v.idVenda, c.nomeCliente, f.nomeFuncionario, p.nomeProduto,
       p.precoUn, vp.quantidade, vp.precoTotal, v.dataVenda
FROM Venda v
JOIN Cliente c ON v.idCliente = c.idCliente
JOIN Funcionario f ON v.idFuncionario = f.idFuncionario
JOIN VendaProduto vp ON vp.idVenda = v.idVenda
JOIN Produto p ON p.idProduto = vp.idProduto;

-- View Produtos a Vencer em até 45 dias
CREATE VIEW VW_ProdutoAVencer AS 
SELECT nomeProduto, dataFab, dataVal, DATEDIFF(DAY, GETDATE(), dataVal) AS diasVencer
FROM Produto 
WHERE dataVal IS NOT NULL AND DATEDIFF(DAY, GETDATE(), dataVal) < 45;

-- View de Estoque Baixo
CREATE VIEW VW_EstoqueBaixo AS
SELECT nomeProduto, precoUn, dataFab, dataVal, estoque 
FROM Produto
WHERE estoque < 20;

-- View de Top Clientes
CREATE VIEW VW_TopClientes AS 
SELECT c.cpfCliente, SUM(v.valorTotal) AS valorGasto 
FROM Cliente c 
JOIN Venda v ON v.idCliente = c.idCliente
GROUP BY c.cpfCliente;

-- View de Clientes Top com nome
CREATE VIEW VW_ClientesTop AS 
SELECT c.nomeCliente, vw.cpfCliente, vw.valorGasto 
FROM Cliente c 
JOIN VW_TopClientes vw ON c.cpfCliente = vw.cpfCliente;

-- View de unidades vendidas por produto
CREATE VIEW VW_UnVendidas AS 
SELECT p.idProduto, SUM(vp.quantidade) AS unVendas 
FROM Produto p 
JOIN VendaProduto vp ON p.idProduto = vp.idProduto 
GROUP BY p.idProduto;

-- View de produtos mais vendidos
CREATE VIEW VW_TopProdutos AS 
SELECT p.nomeProduto, p.precoUn, vw.unVendas 
FROM Produto p 
JOIN VW_UnVendidas vw ON p.idProduto = vw.idProduto;

-- Trigger para atualizar estoque ao vender
CREATE TRIGGER TR_AtualizarEstoque
ON VendaProduto
FOR INSERT 
AS 
BEGIN 
    DECLARE @idProduto INT, @quantidade FLOAT;
    SELECT @idProduto = idProduto, @quantidade = quantidade FROM inserted;

    IF (SELECT estoque FROM Produto WHERE idProduto = @idProduto) >= @quantidade 
    BEGIN
        UPDATE Produto 
        SET estoque = estoque - @quantidade 
        WHERE idProduto = @idProduto;
    END
    ELSE 
    BEGIN
        RAISERROR('Erro! Quantidade vendida é maior que a quantidade em estoque!', 16, 1);
        ROLLBACK TRANSACTION;
    END
END;

INSERT INTO Cliente (nomeCliente, cpfCliente, dataNasc, telefone, email, endereco, genero) VALUES
('Ana Souza', '12345678901', '1990-05-12', '62999990001', 'ana@email.com', 'Rua das Flores, 123', 'F'),
('Bruno Lima', '23456789012', '1985-08-23', '62999990002', 'bruno@email.com', 'Av. Central, 456', 'M'),
('Carla Mendes', '34567890123', '1992-11-30', '62999990003', 'carla@email.com', 'Rua Azul, 789', 'F'),
('Daniel Rocha', '45678901234', '1988-03-15', '62999990004', 'daniel@email.com', 'Rua Verde, 321', 'M'),
('Eduarda Silva', '56789012345', '1995-07-19', '62999990005', 'eduarda@email.com', 'Av. Brasil, 654', 'F'),
('Felipe Torres', '67890123456', '1991-01-10', '62999990006', 'felipe@email.com', 'Rua A, 111', 'M'),
('Gabriela Costa', '78901234567', '1993-09-25', '62999990007', 'gabriela@email.com', 'Rua B, 222', 'F'),
('Henrique Dias', '89012345678', '1987-12-05', '62999990008', 'henrique@email.com', 'Rua C, 333', 'M'),
('Isabela Freitas', '90123456789', '1996-06-14', '62999990009', 'isabela@email.com', 'Rua D, 444', 'F'),
('João Pedro', '01234567890', '1994-04-02', '62999990010', 'joao@email.com', 'Rua E, 555', 'M');


INSERT INTO Funcionario (nomeFuncionario, cpfFuncionario, dataNasc, telefone, email, endereco, genero, cargo, nivel, salario) VALUES
('Lucas Martins', '11122233344', '1980-02-20', '62999991001', 'lucas@email.com', 'Rua Alfa, 1', 'M', 'Gerente', 3, 5000.00),
('Mariana Lopes', '22233344455', '1985-06-18', '62999991002', 'mariana@email.com', 'Rua Beta, 2', 'F', 'Vendedor', 1, 1800.00),
('Ricardo Alves', '33344455566', '1990-09-12', '62999991003', 'ricardo@email.com', 'Rua Gama, 3', 'M', 'Caixa', 1, 1600.00),
('Tatiane Moura', '44455566677', '1992-03-08', '62999991004', 'tatiane@email.com', 'Rua Delta, 4', 'F', 'Estoquista', 1, 1512.00),
('Vinícius Prado', '55566677788', '1988-11-30', '62999991005', 'vinicius@email.com', 'Rua Épsilon, 5', 'M', 'Supervisor', 2, 3200.00),
('Larissa Nunes', '66677788899', '1993-07-22', '62999991006', 'larissa@email.com', 'Rua Zeta, 6', 'F', 'RH', 2, 2800.00);


INSERT INTO Produto (nomeProduto, codBarra, tipoUn, precoUn, estoque, dataFab, dataVal) VALUES
('Leite Integral', '7891000100066', 'LT', 4.79, 120, '2025-02-15', '2025-08-15'),
('Sabonete Neutro', '7891000100077', 'UN', 1.99, 200, '2025-01-10', '2027-01-10'),
('Shampoo Anticaspa', '7891000100088', 'UN', 9.99, 75, '2025-03-01', '2027-03-01'),
('Detergente Líquido', '7891000100099', 'UN', 2.50, 100, '2025-01-01', '2027-01-01'),
('Desinfetante Floral', '7891000100100', 'LT', 4.50, 90, '2025-01-01', '2027-01-01'),
('Creme Dental', '7891000100111', 'UN', 3.49, 150, '2025-02-10', '2027-02-10'),
('Papel Higiênico c/4', '7891000100122', 'UN', 6.99, 60, '2025-01-20', NULL),
('Escova Dental', '7891000100133', 'UN', 4.29, 80, '2025-02-01', NULL),
('Farinha de Trigo', '7891000100144', 'KG', 3.99, 100, '2025-03-01', '2026-03-01'),
('Achocolatado em Pó', '7891000100155', 'KG', 8.49, 50, '2025-03-10', '2026-03-10'),

('Macarrão Espaguete', '7891000100166', 'KG', 4.79, 95, '2025-04-01', '2026-04-01'),
('Molho de Tomate', '7891000100177', 'LT', 3.49, 100, '2025-02-05', '2026-02-05'),
('Maionese', '7891000100188', 'LT', 6.99, 70, '2025-03-01', '2025-09-01'),
('Ketchup Tradicional', '7891000100199', 'LT', 7.49, 80, '2025-02-10', '2026-02-10'),
('Mostarda', '7891000100200', 'LT', 5.49, 85, '2025-01-15', '2026-01-15'),
('Biscoito Recheado', '7891000100211', 'UN', 2.99, 120, '2025-03-01', '2026-03-01'),
('Refrigerante 2L', '7891000100222', 'LT', 6.89, 50, '2025-03-15', '2025-08-15'),
('Água Mineral 1,5L', '7891000100233', 'LT', 2.49, 100, '2025-01-01', NULL),
('Suco Uva 1L', '7891000100244', 'LT', 5.99, 70, '2025-03-01', '2025-09-01'),
('Cerveja Lata', '7891000100255', 'UN', 3.99, 200, '2025-01-20', '2025-07-20'),

('Cerveja Garrafa 600ml', '7891000100266', 'LT', 6.50, 80, '2025-02-01', '2025-08-01'),
('Sabão em Pó', '7891000100277', 'KG', 9.99, 100, '2025-01-01', '2027-01-01'),
('Amaciante', '7891000100288', 'LT', 7.99, 90, '2025-02-01', '2027-02-01'),
('Esponja de Aço', '7891000100299', 'UN', 1.79, 60, '2025-03-01', NULL),
('Saco de Lixo 30L', '7891000100300', 'UN', 4.59, 80, '2025-01-10', NULL),
('Panela Antiaderente', '7891000100311', 'UN', 49.90, 30, '2025-02-15', NULL),
('Vassoura', '7891000100322', 'UN', 9.90, 40, '2025-01-01', NULL),
('Pá de Lixo', '7891000100333', 'UN', 7.50, 40, '2025-01-01', NULL),
('Caderno 96 folhas', '7891000100344', 'UN', 10.99, 70, '2025-01-01', NULL),
('Caneta Azul', '7891000100355', 'UN', 1.50, 100, '2025-01-01', NULL),

('Lápis Preto', '7891000100366', 'UN', 0.99, 120, '2025-01-01', NULL),
('Borracha Escolar', '7891000100377', 'UN', 0.89, 110, '2025-01-01', NULL),
('Apontador', '7891000100388', 'UN', 1.29, 90, '2025-01-01', NULL),
('Envelope Carta', '7891000100399', 'UN', 0.79, 50, '2025-01-01', NULL),
('Pacote Papel A4 500f', '7891000100400', 'UN', 29.99, 40, '2025-01-01', NULL),
('Mouse USB', '7891000100411', 'UN', 24.90, 30, '2025-01-01', NULL),
('Teclado USB', '7891000100422', 'UN', 39.90, 25, '2025-01-01', NULL),
('Pendrive 16GB', '7891000100433', 'UN', 34.90, 60, '2025-01-01', NULL),
('Cabo USB', '7891000100444', 'UN', 12.00, 45, '2025-01-01', NULL),
('Carregador Celular', '7891000100455', 'UN', 29.90, 35, '2025-01-01', NULL),

('Fone de Ouvido', '7891000100466', 'UN', 49.90, 40, '2025-01-01', NULL),
('Capa de Celular', '7891000100477', 'UN', 19.90, 50, '2025-01-01', NULL),
('Lâmpada LED', '7891000100488', 'UN', 14.90, 100, '2025-01-01', NULL),
('Extensão Elétrica', '7891000100499', 'UN', 22.90, 35, '2025-01-01', NULL),
('Filtro de Linha', '7891000100500', 'UN', 28.90, 30, '2025-01-01', NULL);
