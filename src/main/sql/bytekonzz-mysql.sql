
CREATE TABLE role(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE users(
	id INT NOT NULL AUTO_INCREMENT,
	active TINYINT NOT NULL DEFAULT 1,
	deletable TINYINT NOT NULL DEFAULT 1,
	roleId INT DEFAULT 3,
	username VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255),
	firstName VARCHAR(255) DEFAULT NULL,
	lastName VARCHAR(255) DEFAULT NULL,
	street VARCHAR(255) DEFAULT NULL,
	city VARCHAR(255) DEFAULT NULL,
	state VARCHAR(255) DEFAULT NULL,
	zipCode INT DEFAULT NULL,
	email VARCHAR(255) DEFAULT NULL,
	phone VARCHAR(255) DEFAULT NULL,
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT FK_USER2ROLE_1 FOREIGN KEY (roleId) REFERENCES role (id),
	PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;

INSERT INTO role (id,name) VALUES (1,'Admin');
INSERT INTO role (id,name) VALUES (2,'Employee');
INSERT INTO role (id,name) VALUES (3,'Customer');
INSERT INTO users (id,username,password,active,roleId,deletable) VALUES (1,'admin', '69f16d0e0b12d534b1e480ba3442343577e72275535ff68f229f0d89d7b2294d', 1,1,0);

CREATE TABLE account(
	accountNumber INT NOT NULL AUTO_INCREMENT,
	active TINYINT NOT NULL DEFAULT 1,
	description VARCHAR(255) NOT NULL,
	userId INT NOT NULL,
	accountType INT NOT NULL,
	balance DOUBLE(255,2) DEFAULT 0,
	dailyLimit DOUBLE(255,2) DEFAULT NULL,
	created TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT FK_USER2ACCOUNT_1 FOREIGN KEY (userId) REFERENCES users (id) ON DELETE CASCADE,
	PRIMARY KEY(accountNumber)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE transaction(
	id INT NOT NULL AUTO_INCREMENT,
	accountId INT NOT NULL,
	transactionType INT NOT NULL,
	amount DOUBLE(255,2) NOT NULL,
	balance DOUBLE(255,2) NOT NULL,
	description VARCHAR(255) NOT NULL,
	dateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT FK_ACCOUNT2TRANSACTION_1 FOREIGN KEY (accountId) REFERENCES account (accountNumber) ON DELETE CASCADE,
	PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO account (accountNumber,description, userId, accountType, balance) VALUES (1,'Bank Capitol', 1, 2, 1000000);

