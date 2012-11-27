insert into users (username,email,firstname,lastname) values ('dianne','mymail@email.com','dianne','dianne');

--//@UNDO

delete from users where username='dianne' and email='mymail@email.com';