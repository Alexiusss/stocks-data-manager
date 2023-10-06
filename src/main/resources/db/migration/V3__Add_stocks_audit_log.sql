-- https://vladmihalcea.com/postgresql-audit-logging-triggers/

CREATE TYPE dml_type AS ENUM ('INSERT', 'UPDATE', 'DELETE');

CREATE TABLE IF NOT EXISTS stocks_audit_log  (
    company_id VARCHAR (255) NOT NULL,
    dml_type dml_type,
    created_at TIMESTAMP DEFAULT now() NOT NULL,
    old_stock_data JSONB,
    new_stock_data JSONB,
    PRIMARY KEY (company_id, dml_type, created_at)
);