drop table tu_url_map;
commit;

create table tu_url_map (
	key_value BIGINT references tu_keys(key_value),
    long_url varchar(2048),
    creation_dt datetime,
    expiration_dt datetime
);
commit;

drop table tu_keys;
create table tu_keys (
    key_value BIGINT,
    used_flag char(1),
	primary key(key_value),
    CONSTRAINT key_value_unique UNIQUE (key_value)
    );
commit;
    
select * from tu_url_map
;

select count(1) from tu_keys
;

-----------------------------------------------------------------------

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

-----------------------------------------------------------------------

DROP PROCEDURE IF EXISTS tinyurl.createTmpKeyHolder;
commit; 

DELIMITER //

CREATE procedure createTmpKeyHolder()
	
BEGIN

drop temporary table if exists tmp_unused_keys;
create temporary table tmp_unused_keys(seq bigint);
commit;
END; //

DELIMITER ;

call createTmpKeyHolder();

-----------------------------------------------------------------------

DROP PROCEDURE IF EXISTS tinyurl.fetchKeys;
commit; 

DELIMITER //


CREATE procedure fetchKeys(in size INTEGER)
	
BEGIN

    declare seq integer default 0;
	declare unusedkeys cursor for select key_value from tu_keys where used_flag = 'N';
	
    call createTmpKeyHolder();
	
    open unusedkeys;
    
    l1: while size > 0 DO
		
		fetch unusedkeys into seq;
        
        insert into tmp_unused_keys(seq) values(seq);
        update tu_keys set used_flag = 'Y' where key_value = seq;
        
        set size = (size-1);
    END WHILE l1;
    
    close unusedkeys;
	commit;

END; //


DELIMITER ;

call fetchKeys(100);

select * from tmp_unused_keys;

select * from tu_keys
where key_value = '1250'
;

-----------------------------------------------------------------------

DROP PROCEDURE IF EXISTS tinyurl.returnKeys;
commit; 

DELIMITER //


CREATE procedure returnKeys()
	
BEGIN
	
    declare seqval integer default 0;
    DECLARE exit_loop BOOLEAN default false;
	declare unusedkeys cursor for select seq from tmp_unused_keys;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET exit_loop = TRUE;
    open unusedkeys;
    
    l1: LOOP
		
		fetch unusedkeys into seqval;
        
        IF exit_loop THEN
         CLOSE unusedkeys;
         LEAVE l1;
		END IF;
        
        update tu_keys set used_flag = 'N' where key_value = seqval;
			
    END loop l1;
    drop temporary table if exists tmp_unused_keys;
	commit;

END; //


DELIMITER ;

call returnKeys();

delete from tinyurl.tmp_unused_keys
where seq < 1300
;
-----------------------------------------------------------------------

DROP PROCEDURE IF EXISTS tinyurl.fetchLongURL;
commit; 

DELIMITER //


CREATE procedure fetchLongURL(IN tu_key BIGINT ,OUT longURL VARCHAR(2048))
	
BEGIN
    declare exp_dt datetime;
    
    select long_url, expiration_dt from tu_url_map where key_value = tu_key LIMIT 1
    into longURL, exp_dt;
    
    if exp_dt < NOW() then
		SET longURL = "Expired";
        delete from tu_url_map where key_value = tu_key LIMIT 1;
        update tu_keys set used_flag = 'N' where key_value = tu_key;
        commit;
    end if;
    
    if longURL is null then
		set longURL = "Not Found";
	end if;
    
END; //

DELIMITER ;
commit;