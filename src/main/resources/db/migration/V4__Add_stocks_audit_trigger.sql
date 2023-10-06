CREATE OR REPLACE FUNCTION stocks_audit_trigger_func()
    RETURNS trigger AS
$body$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO stocks_audit_log (company_id, dml_type, created_at, old_stock_data, new_stock_data)
        VALUES (NEW.company_id, 'INSERT', CURRENT_TIMESTAMP, null, to_jsonb(NEW));
        RETURN NEW;
    ELSEIF (TG_OP = 'UPDATE') THEN
        INSERT INTO stocks_audit_log (company_id, dml_type, created_at, old_stock_data, new_stock_data)
        VALUES (NEW.company_id, 'UPDATE', CURRENT_TIMESTAMP, to_jsonb(OLD), to_jsonb(NEW));
        RETURN NEW;
    ELSEIF (TG_OP = 'DELETE') THEN
        INSERT INTO stocks_audit_log (company_id, dml_type, created_at, old_stock_data, new_stock_data)
        VALUES (OLD.company_id, 'DELETE', CURRENT_TIMESTAMP, to_jsonb(OLD), null);
        RETURN OLD;
    END IF;
END;
$body$
    LANGUAGE plpgsql;



CREATE TRIGGER stocks_audit_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON stocks
    FOR EACH ROW
EXECUTE FUNCTION stocks_audit_trigger_func();