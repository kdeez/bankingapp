

INSERT INTO role (id,name) VALUES (1,'Admin');
INSERT INTO role (id,name) VALUES (2,'Employee');
INSERT INTO role (id,name) VALUES (3,'Customer');
INSERT INTO users (id,username,password,active,roleId,deletable) VALUES (1,'admin', 'password', 1,1,0);