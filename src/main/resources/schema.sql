CREATE TABLE users (
   id integer identity NOT NULL,
   userName varchar(50) NOT NULL,
   firstName varchar(50) NOT NULL,
   lastName varchar(50) NOT NULL,
   emailId varchar(50) NOT NULL,
   CONSTRAINT pk_users_id PRIMARY KEY (id)
   );