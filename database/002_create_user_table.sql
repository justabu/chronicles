CREATE TABLE user (id INT NOT NULL AUTO_INCREMENT,
					name VARCHAR(30) NOT NULL,
					email VARCHAR(30) NOT NULL,
					PRIMARY KEY(ID)) ENGINE=INNODB;
					
--//@UNDO

DROP TABLE IF EXISTS user;