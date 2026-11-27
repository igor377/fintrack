CREATE TABLE users (
    id         UUID         PRIMARY KEY DEFAULT uuidv7(),
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE categories (
    id      UUID         PRIMARY KEY DEFAULT uuidv7(),
    user_id UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name    VARCHAR(100) NOT NULL,
    type    VARCHAR(100) NOT NULL CHECK (type IN ('RECEITA', 'DESPESA'))
);

CREATE TABLE accounts (
    id         UUID          PRIMARY KEY DEFAULT uuidv7(),
    user_id    UUID          NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name       VARCHAR(100)  NOT NULL,
    type       VARCHAR(30)   NOT NULL CHECK (type IN ('CORRENTE', 'POUPANCA', 'CARTAO')),
    balance    NUMERIC(19,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE TABLE transactions (
    id              UUID          PRIMARY KEY DEFAULT uuidv7(),
    account_id      UUID          NOT NULL REFERENCES accounts (id) ON DELETE CASCADE,
    category_id     UUID          REFERENCES categories (id),
    amount          NUMERIC(19,2) NOT NULL,
    description     VARCHAR(255),
    created_at      TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    comprovante_key VARCHAR(500)
);

CREATE TABLE transfers (
    id                  UUID          PRIMARY KEY DEFAULT uuidv7(),
    source_account      UUID          NOT NULL REFERENCES accounts (id) ON DELETE CASCADE,
    destination_account UUID          NOT NULL REFERENCES accounts (id) ON DELETE CASCADE,
    amount              NUMERIC(19,2) NOT NULL,
    created_at          TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE TABLE budgets (
    id              UUID          PRIMARY KEY DEFAULT uuidv7(),
    account_id      UUID          NOT NULL REFERENCES accounts (id) ON DELETE CASCADE,
    category_id     UUID          REFERENCES categories (id),
    reference_month DATE          NOT NULL CHECK (EXTRACT(DAY FROM reference_month) = 1),
    limit_amount    NUMERIC(19,2) NOT NULL,
    created_at      TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);
