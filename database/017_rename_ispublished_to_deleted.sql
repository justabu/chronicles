ALTER TABLE stories ADD deleted boolean;
UPDATE stories set deleted = false where ispublished = true;
UPDATE stories set deleted = true where ispublished = false;
ALTER TABLE stories DROP ispublished;

--//@UNDO

ALTER TABLE stories ADD ispublished boolean;
UPDATE stories set ispublished = false where deleted = true;
UPDATE stories set ispublished = true where deleted = false;
ALTER TABLE stories DROP deleted;