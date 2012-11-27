CREATE TABLE comments (id int NOT NULL AUTO_INCREMENT,
					  story_id int NOT NULL,
					  date_added TIMESTAMP NOT NULL,
					  content TEXT NOT NULL,
					  author_user_id int NOT NULL,
					  reply_to_id int,
					  PRIMARY KEY (ID)) ENGINE=INNODB;

--//@UNDO

DROP TABLE IF EXISTS comments;