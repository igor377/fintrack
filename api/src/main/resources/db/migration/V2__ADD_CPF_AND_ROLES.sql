ALTER TABLE users 
    ADD COLUMN cpf VARCHAR(11) NOT NULL,
    ADD COLUMN role VARCHAR(10) NOT NULL DEFAULT 'USER',
    ADD CONSTRAINT unique_cpf UNIQUE (cpf),
    ADD CONSTRAINT check_cpf CHECK (cpf ~ '^[0-9]{11}$'),
    ADD CONSTRAINT check_role CHECK (role IN ('USER', 'ADMIN'))
