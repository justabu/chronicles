					
ALTER TABLE user ADD COLUMN firstname varchar(255);
ALTER TABLE user ADD COLUMN lastname varchar(255);
ALTER TABLE user ADD COLUMN shortdescription TINYTEXT;
ALTER TABLE user ADD COLUMN dateofjoining DATE;
ALTER TABLE user ADD COLUMN homeoffice varchar(255);
ALTER TABLE user ADD COLUMN currentoffice varchar(255);

--//@UNDO

DROP TABLE IF EXISTS user;