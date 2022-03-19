CREATE SEQUENCE msgID_seq START WITH 28000;

CREATE LANGUAGE plpgsql;
CREATE OR REPLACE FUNCTION add_msgID()
RETURNS "trigger" AS
$BODY$
BEGIN
    NEW.msgId = nextval('msgID_seq');
    RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

CREATE TRIGGER add_msgID_trigger BEFORE INSERT
ON MESSAGE FOR EACH ROW
EXECUTE PROCEDURE add_msgID()