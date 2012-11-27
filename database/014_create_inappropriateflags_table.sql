CREATE TABLE inappropriateflags (id INT primary key auto_increment, storyId INT,
								userId INT) ENGINE=INNODB;
--//@UNDO

DROP TABLE IF EXISTS inappropriateflags;