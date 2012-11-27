update user set firstname='' where firstname is NULL;
update user set lastname='' where lastname is NULL;
ALTER TABLE user CHANGE firstname firstname VARCHAR(255) NOT NULL;
ALTER TABLE user CHANGE lastname lastname VARCHAR(255) NOT NULL;

--//@UNDO

alter table user change firstname firstname varchar(255);
alter table user change lastname lastname varchar(255);
update user set firstname=NULL where firstname='';
update user set lastname=NULL where lastname='';