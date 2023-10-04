CREATE TABLE companies
(
    id         varchar(255) not null,
    symbol     varchar(255) not null,
    name       varchar(255) not null,
    is_enabled boolean default false,
    primary key (id)
);

CREATE UNIQUE INDEX companies_symbol_index ON companies (symbol);


CREATE TABLE stocks
(
    id              varchar(255) not null,
    symbol          varchar(255) not null,
    latest_price    decimal,
    change          decimal,
    company_name    varchar(255) not null,
    previous_volume int,
    volume          int,
    primary key (symbol)
);

CREATE UNIQUE INDEX stocks_symbol_index ON stocks (symbol);