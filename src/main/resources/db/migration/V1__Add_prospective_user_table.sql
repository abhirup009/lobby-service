CREATE TABLE prospective_user (
	id serial PRIMARY KEY,
	prospective_user_id VARCHAR ( 50 ) UNIQUE NOT NULL,
	name VARCHAR ( 50 ) NOT NULL
);