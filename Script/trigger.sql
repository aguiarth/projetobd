CREATE DEFINER = root@localhost TRIGGER trg_MateriaPrima_CustoTotal_BeforeInsert
BEFORE INSERT ON MateriaPrima
FOR EACH ROW
BEGIN
    SET NEW.custo_total = NEW.quantidade * NEW.custo_unitario;
END;

CREATE DEFINER = root@localhost TRIGGER trg_MateriaPrima_CustoTotal_BeforeUpdate
BEFORE UPDATE ON MateriaPrima
FOR EACH ROW
BEGIN
    IF OLD.quantidade != NEW.quantidade OR OLD.custo_unitario != NEW.custo_unitario THEN
        SET NEW.custo_total = NEW.quantidade * NEW.custo_unitario;
    END IF;
END;
