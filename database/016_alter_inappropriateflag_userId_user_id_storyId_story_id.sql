alter table inappropriateflags change storyId story_id Integer;
alter table inappropriateflags change userId user_id Integer;

--//@UNDO

alter table inappropriateflags change story_id storyId Integer;
alter table inappropriateflags change user_id userId Integer;

