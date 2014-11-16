

INSERT INTO role (id,name) VALUES (1,'Admin');
INSERT INTO role (id,name) VALUES (2,'Employee');
INSERT INTO role (id,name) VALUES (3,'Customer');
INSERT INTO users (id,username,password,active,roleId,deletable) VALUES (1,'admin', '69f16d0e0b12d534b1e480ba3442343577e72275535ff68f229f0d89d7b2294d', 1,1,0);

INSERT INTO account (accountNumber,description, userId, accountType, balance) VALUES (1,'Bank Capitol', 1, 2, 1000000);