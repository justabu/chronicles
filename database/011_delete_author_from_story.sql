alter table story drop column author;

--//@UNDO

alter table story add column author varchar(255);