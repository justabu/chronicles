CREATE TABLE favourites (id INT NOT NULL AUTO_INCREMENT,
                         user_id INT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users(id),
                         story_id INT NOT NULL,
                         FOREIGN KEY (story_id) REFERENCES stories(id),
                         PRIMARY KEY (ID)) ENGINE=INNODB;

--//@UNDO

DROP TABLE IF EXISTS favourites;
