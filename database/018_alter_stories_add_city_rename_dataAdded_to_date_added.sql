alter table stories add column city varchar(50) not null default '';
alter table stories change column dataAdded date_added TIMESTAMP;

--//@UNDO

alter table stories drop column city;
alter table stories change column date_added dataAdded TIMESTAMP;