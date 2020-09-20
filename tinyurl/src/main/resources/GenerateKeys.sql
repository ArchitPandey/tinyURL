DROP PROCEDURE IF EXISTS generateKeys;
commit; 

DELIMITER //

CREATE procedure generateKeys()

BEGIN

   DECLARE seq BIGINT;

   SET seq = 1000;

   label1: WHILE seq <= 10000 DO
     insert into tu_keys(key_value, used_flag) values (seq, 'N');
     SET seq = seq+1;
   END WHILE label1;
   commit;

END; //

DELIMITER ;

call generateKeys();