ALTER TABLE stories CHANGE COLUMN date_added date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

--//@UNDO

ALTER TABLE stories CHANGE COLUMN date_added date_added TIMESTAMP;
