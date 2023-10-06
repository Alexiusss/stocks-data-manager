CREATE TABLE IF NOT EXISTS companies
(
    id         VARCHAR(255) PRIMARY KEY NOT NULL,
    symbol     VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    is_enabled BOOLEAN DEFAULT FALSE
);

CREATE UNIQUE INDEX companies_symbol_index ON companies (symbol);


CREATE TABLE IF NOT EXISTS stocks
(
    id              varchar(255) PRIMARY KEY not null,
    symbol          VARCHAR(255) NOT NULL,
    latest_price    DECIMAL,
    change          DECIMAL,
    company_name    VARCHAR(255) NOT NULL,
    previous_volume INT,
    volume          INT,
    company_id      VARCHAR(255) NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX stocks_symbol_index ON stocks (symbol);