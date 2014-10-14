

CREATE TABLE users(
	id INT NOT NULL AUTO_INCREMENT,
	active TINYINT DEFAULT 0,
	deletable TINYINT DEFAULT 1,
	roleId INT DEFAULT 2,
	username VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255),
	firstName VARCHAR(255) DEFAULT NULL,
	lastName VARCHAR(255) DEFAULT NULL,
	email VARCHAR(255) DEFAULT NULL,
	--CONSTRAINT FK_USER2ROLE_2 FOREIGN KEY (roleId) REFERENCES role (id),
	PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;