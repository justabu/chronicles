alter table inappropriateflags change column id id int(11) not null;
alter table inappropriateflags drop primary key; 
alter table inappropriateflags add primary key (id, user_id, story_id);
alter table inappropriateflags change column id id int(11) not null auto_increment;
rename table inappropriateflags to inappropriate_flags;

--//@UNDO

rename table inappropriate_flags to inappropriateflags;
alter table inappropriateflags change column id id int(11) not null;
alter table inappropriateflags drop primary key; 
alter table inappropriateflags change column id id int(11) not null primary key auto_increment;