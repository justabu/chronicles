alter table users drop column email;

--//@UNDO

alter table users add column email varchar(30) not null;