


-- Trigger when a record is added in the enrollments table

create or replace TRIGGER enrollment_insert
AFTER INSERT ON enrollments
FOR EACH ROW
DECLARE

log_no varchar2(14);
log_op_name varchar2(12);
log_table varchar2(12) default 'enrollments';
log_oper varchar2(12) default 'insert';
log_key_val varchar2(45);
b#V students.b#%type;
classidV classes.classid%type;

BEGIN
    b#V:= :new.b#;
    classidV := :new.classid;
    select user into log_op_name from dual;
    log_no:= log_seq.nextval;
    log_key_val := (b#V||','||classidV);

    insert into logs values(log_no,log_op_name,sysdate,log_table,log_oper,log_key_val);

    update classes
    set class_size=class_size+1 where classid=:new.classid;
 --   DBMS_OUTPUT.PUT_LINE('Class size decremented by one');
    --commit;
END;
/







-- Trigger when a record is deleted from the enrollments table

create or replace TRIGGER enrollment_drop
AFTER delete ON enrollments
FOR EACH ROW
DECLARE

class_size_val classes.class_size%type;
no_of_class number(4);

log_no varchar2(14);
log_op_name varchar2(12);
log_table_name varchar2(12) default 'enrollments';
log_operation varchar2(12) default 'delete';
log_key_val varchar2(45);
b#V students.b#%type;
classidV classes.classid%type;

BEGIN

    b#V:= :old.b#;
    classidV := :old.classid;

    select user into log_op_name from dual;
    log_no:= log_seq.nextval;
    log_key_val := (b#V||','||classidV);

    insert into logs values(log_no,log_op_name,sysdate,log_table_name,log_operation,log_key_val);

-- decrement the class size
    update classes
    set class_size=class_size-1 where classid=:old.classid;
 --   DBMS_OUTPUT.PUT_LINE('Class size decremented by one');

END;

/





-- Trigger before a record is deleted from the students table

create or replace TRIGGER delete_student_before
BEFORE DELETE ON students
FOR EACH ROW
DECLARE

log_no varchar2(14);
log_op_name varchar2(12);
log_table varchar2(12) default 'students';
log_oper varchar2(12) default 'delete';
log_key_val varchar2(45);
b#V students.b#%type;

BEGIN
    b#V:= :old.b#;

    select user into log_op_name from dual;
    log_no:= log_seq.nextval;
    log_key_val := b#V;

    -- insert into logs values(log_no,log_op_name,sysdate,log_table,log_oper,log_key_val);
    delete from enrollments where b#=b#V;
    update classes set TA_B#=null where classid in (select classid from classes where ta_b#=b#V) ;
    delete from tas where b#=b#V;

END;
/






-- Trigger after a record is deleted from the students table

create or replace TRIGGER delete_student_after
AFTER DELETE ON students
FOR EACH ROW
DECLARE

log_no varchar2(14);
log_op_name varchar2(12);
log_table varchar2(12) default 'students';
log_oper varchar2(12) default 'delete';
log_key_val varchar2(45);
b#V students.b#%type;

BEGIN
    b#V := :old.b#;

    select user into log_op_name from dual;
    log_no:= log_seq.nextval;
    log_key_val := b#V;

    insert into logs values(log_no,log_op_name,sysdate,log_table,log_oper,log_key_val);

END;

/






