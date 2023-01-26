CREATE TABLE person (
person_id VARCHAR(40) NOT NULL,
name VARCHAR(255),
birth_date DATE,
CONSTRAINT person_pk1 PRIMARY KEY(person_id)
);

CREATE TABLE address (
address_id VARCHAR(40) NOT NULL,
person_id VARCHAR(40) NOT NULL,
backyard VARCHAR(100),
cep VARCHAR(12),
number VARCHAR(20),
city VARCHAR(100),
primary_address BOOLEAN DEFAULT false,
CONSTRAINT address_pk1 PRIMARY KEY(address_id),
CONSTRAINT address_fk1 FOREIGN KEY(person_id) REFERENCES person(person_id)
);