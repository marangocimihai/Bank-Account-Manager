CREATE TABLE clients
(
ClientID NUMBER,
Username VARCHAR(30),
Pswrd VARCHAR(30),
Firstname VARCHAR(30),
Lastname VARCHAR(30),
Genre VARCHAR(1),
Balance INTEGER,
Email VARCHAR(30),
Birthdate VARCHAR(30)
);

CREATE TABLE transactions
(
ClientID NUMBER,
TType VARCHAR(15),
Sender VARCHAR(25),
Receiver VARCHAR(25),
Amount NUMBER,
TDate VARCHAR(15),
THour VARCHAR(15)
);

CREATE TABLE users_photos 
(             
              name varchar(25) default NULL,          
              image blob                                          
);

INSERT INTO clients(clientid, username, pswrd, firstname, lastname, genre, balance, email, birthdate) VALUES(1, 'miTzuliK', 'chelseafan93', 'Marangoci', 'Mihai', 'M', 100, 'marangoci.mihai93@gmail.com', '13/10/1993');
INSERT INTO clients(clientid, username, pswrd, firstname, lastname, genre, balance, email, birthdate) VALUES(2, 'radu', 'radu93', 'Neamtu', 'Radu', 'M', 10, 'albeatza@yahoo.com', '3/07/1992');
INSERT INTO clients(clientid, username, pswrd, firstname, lastname, genre, balance, email, birthdate) VALUES(3, 'geo', 'geo93', 'Cretulescu', 'George', 'M', 1000, 'george15geo@yahoo.com' , '31/06/1992');
INSERT INTO clients(clientid, username, pswrd, firstname, lastname, genre, balance, email, birthdate) VALUES(4, 'mary', 'mary93', 'Marangoci', 'Mariana', 'F', 5600, 'mary2005@yahoo.com', '21/12/1969');
INSERT INTO clients(clientid, username, pswrd, firstname, lastname, genre, balance, email, birthdate) VALUES(5, 'deea', 'deea93', 'Mihaita', 'Andreea', 'F', 270, 'mihaita.andreea@gmail.com', '30/06/1997');

COMMIT;