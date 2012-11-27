alter table tags change id id integer not null auto_increment;
alter table tags change story_id story_id integer not null;
 
alter table tags add constraint tags_story_fk foreign key (story_id) references stories(id);

--//@UNDO

alter table tags drop foreign key tags_story_fk;

alter table tags change id id bigint not null auto_increment;
alter table tags change story_id story_id bigint not null;
