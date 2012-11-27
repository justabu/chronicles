ALTER TABLE users CHANGE shortdescription shortdescription VARCHAR(2000);

--//@UNDO

ALTER TABLE users CHANGE shortdescription shortdescription TINYTEXT;
