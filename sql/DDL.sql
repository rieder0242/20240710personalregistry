-- DROP SCHEMA dbo;

CREATE SCHEMA dbo;


CREATE TABLE address (
	id int IDENTITY(1,1) NOT NULL,
	create_time datetime DEFAULT getdate() NULL,
	modified_date datetime DEFAULT getdate() NULL,
	deleted bit DEFAULT 0 NOT NULL,
	zip_code varchar(50) COLLATE Hungarian_CI_AS NOT NULL,
	settlement varchar(150) COLLATE Hungarian_CI_AS NOT NULL,
	address varchar(150) COLLATE Hungarian_CI_AS NOT NULL,
	CONSTRAINT PK__address__3213E83F047EE687 PRIMARY KEY (id)
);



CREATE TABLE contact (
	id int IDENTITY(1,1) NOT NULL,
	create_time datetime DEFAULT getdate() NULL,
	modified_date datetime DEFAULT getdate() NULL,
	deleted bit DEFAULT 0 NOT NULL,
	address_id int NOT NULL,
	value varchar(100) COLLATE Hungarian_CI_AS NOT NULL,
	kind varchar(100) COLLATE Hungarian_CI_AS NOT NULL,
	CONSTRAINT PK__contact__3213E83FF3412F8E PRIMARY KEY (id),
	CONSTRAINT FK__contact__address__797309D9 FOREIGN KEY (address_id) REFERENCES address(id)
);


CREATE TABLE person (
	id int IDENTITY(1,1) NOT NULL,
	create_time datetime DEFAULT getdate() NULL,
	modified_date datetime DEFAULT getdate() NULL,
	deleted bit DEFAULT 0 NOT NULL,
	first_name varchar(50) COLLATE Hungarian_CI_AS NOT NULL,
	last_name varchar(50) COLLATE Hungarian_CI_AS NOT NULL,
	constant_address_id int NULL,
	temporary_address_id int NULL,
	CONSTRAINT PK__person__3213E83FCA9602B2 PRIMARY KEY (id),
	CONSTRAINT FK_person_constant_address_id FOREIGN KEY (constant_address_id) REFERENCES address(id),
	CONSTRAINT FK_person_temporary_address_id FOREIGN KEY (temporary_address_id) REFERENCES address(id)
);