insert into story (title,year,content,author,ispublished) values ('Title1','2010','Story 1','Author 1',true);
insert into story (title,year,content,author,ispublished) values ('Title2','2010','Story 2','Author 2',true);
insert into story (title,year,content,author,ispublished) values ('Title3','2010','Story 3','Author 3',true);

insert into user (username,email,firstname,lastname) values ('rod','email@email.com','rod','rod');


--//@UNDO

delete from story where title='Title1' and year='2010' and content='Story 1' and author='Author 1';
delete from story where title='Title2' and year='2010' and content='Story 2' and author='Author 2';
delete from story where title='Title3' and year='2010' and content='Story 3' and author='Author 3';

delete from user where username='rod' and email='email@email.com';
