CREATE OR REPLACE FUNCTION upsert_company(is_enabled_param BOOLEAN, name_param VARCHAR, symbol_param VARCHAR (255),
                                          id_param VARCHAR (255))
    RETURNS VOID AS
$$
BEGIN
    INSERT INTO companies (id, symbol, name, is_enabled)
    VALUES (id_param, symbol_param, name_param, is_enabled_param)
    ON CONFLICT (symbol)
        DO UPDATE SET name = excluded.name, is_enabled = excluded.is_enabled;
END;
$$
    LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION calculate_change_in_percent(latest_price DOUBLE PRECISION, change DOUBLE PRECISION)
    RETURNS DOUBLE PRECISION AS
$$
BEGIN
    RETURN (change / (latest_price - change) * 100)::numeric(3, 2);
END;
$$
    LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION upsert_stock(change_param DOUBLE PRECISION, company_id_param VARCHAR, company_name_param VARCHAR, latest_price_param DOUBLE PRECISION,
                                        previous_volume_param INT, symbol_param VARCHAR, volume_param INT,
                                        id_param VARCHAR)
    RETURNS void AS
'
    INSERT INTO stocks (id, symbol, latest_price, change, company_name, previous_volume, volume, company_id)
    VALUES (id_param, symbol_param, latest_price_param, calculate_change_in_percent(latest_price_param, change_param), company_name_param, previous_volume_param, volume_param, company_id_param)
    ON CONFLICT (symbol)
        DO UPDATE SET latest_price    = excluded.latest_price,
                      change          = calculate_change_in_percent(excluded.latest_price, (excluded.latest_price - stocks.latest_price)),
                      company_name    = excluded.company_name,
                      previous_volume = stocks.previous_volume,
                      volume          = excluded.previous_volume
'
    LANGUAGE SQL;