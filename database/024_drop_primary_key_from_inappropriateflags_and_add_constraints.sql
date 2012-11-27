alter table inappropriate_flags change column id id int(11) not null;
alter table inappropriate_flags drop primary key;
alter table inappropriate_flags change column id id int(11) not null primary key auto_increment;
alter table inappropriate_flags add unique index(user_id, story_id);

--//@UNDO

alter table inappropriate_flags drop unique index(user_id, story_id);
alter table inappropriate_flags change column id id int(11) not null;
alter table inappropriate_flags drop primary key;
alter table inappropriate_flags add primary key(id, user_id, story_id);
alter table inappropriate_flags change column id id int(11) not null auto_increment;

