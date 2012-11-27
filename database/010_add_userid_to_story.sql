alter table story add column author_user_id Integer not null;
update story set author_user_id=(Select id from user where username='rod' limit 1);
alter table story add constraint story_user_fk foreign key (author_user_id) references user(id);

--//@UNDO

alter table story drop foreign key story_user_fk;
alter table story drop column author_user_id;

