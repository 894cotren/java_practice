CREATE TABLE phone_numbers (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               name VARCHAR(100) NOT NULL,
                               phone_number VARCHAR(20) NOT NULL,
                               status VARCHAR(20) NOT NULL,
                               email VARCHAR(100),
                               city VARCHAR(50),
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);