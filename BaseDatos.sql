-- Create the Customer table inheriting from Person
CREATE TABLE Customer (
    customerId serial PRIMARY KEY,  -- Unique Primary Key
    password VARCHAR(100) NOT NULL,
    status VARCHAR(20),
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    identification VARCHAR(13) NOT NULL UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(20)
);

-- Create the Account table
CREATE TABLE Account (
    accountId serial PRIMARY KEY,  -- Unique Primary Key
    accountNumber VARCHAR(50),
    accountType VARCHAR(50) NOT NULL,
    initialBalance DECIMAL(15, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
	customerId INT NOT NULL UNIQUE,
    FOREIGN KEY (customerId) REFERENCES Customer(customerId) -- Clave foránea
);

-- Create the Transactions table
CREATE TABLE Transactions (
    transactionId serial PRIMARY KEY,  -- Unique Primary Key
    date VARCHAR(50) NOT NULL,
    transactionType VARCHAR(50) NOT NULL,
    value DECIMAL(15, 2) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    accountId INT NOT NULL,   -- Foreign Key referencing Account
    FOREIGN KEY (accountId) REFERENCES Account(accountId)
);
