delete from tags where story_id = null;
alter table tags modify column story_id int(11) not null;

--//@UNDO

alter table tags modify column story_id int(11);