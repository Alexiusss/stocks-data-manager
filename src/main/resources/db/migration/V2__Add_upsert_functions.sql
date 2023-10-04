CREATE OR REPLACE FUNCTION upsert_company(is_enabled_param boolean, name_param varchar, symbol_param varchar(255),
                                          id_param varchar(255))
    RETURNS void AS
$$
BEGIN
    INSERT INTO companies (id, symbol, name, is_enabled)
    VALUES (id_param, symbol_param, name_param, is_enabled_param)
    ON CONFLICT (symbol)
        DO UPDATE SET name = excluded.name, is_enabled = excluded.is_enabled;
END;
$$
    LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION upsert_stock(change_param double precision, company_name_param varchar, latest_price_param double precision,
                                        previous_volume_param int, symbol_param varchar, volume_param int,
                                        id_param varchar)
    RETURNS void AS
'
    INSERT INTO stocks (id, symbol, latest_price, change, company_name, previous_volume, volume)
    VALUES (id_param, symbol_param, latest_price_param, change_param, company_name_param, previous_volume_param, volume_param)
    ON CONFLICT (symbol)
        DO UPDATE SET latest_price    = excluded.latest_price,
                      change          = excluded.change,
                      company_name    = excluded.company_name,
                      previous_volume = excluded.previous_volume,
                      volume          = excluded.volume;
'
    LANGUAGE SQL;