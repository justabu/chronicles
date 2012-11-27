alter table user change name username varchar(30) not null;

--//@UNDO

alter table user change username name varchar(30) not null;

