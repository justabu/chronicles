ALTER TABLE story add column ispublished boolean not null;
UPDATE story set ispublished = true;

--//@UNDO

ALTER TABLE story drop column ispublished;