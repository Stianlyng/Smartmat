INSERT INTO users (username, password, enabled)
VALUES 
	('john', '{noop}password123', true),
	('jane', '{noop}password456', true),
	('bob', '{noop}password789', true),
	('alice', '{noop}password111', true),
	('tom', '{noop}password222', true),
    ('sarah', '{noop}password333', true),
    ('david', '{noop}password444', true),
    ('emily', '{noop}password555', true),
    ('mike', '{noop}password666', true),
    ('olivia', '{noop}password777', true);

INSERT INTO authorities (username, authority)
VALUES 
	('john', 'USER'),
	('jane', 'ADMIN'),
	('bob', 'USER'),
	('alice', 'USER'),
	('tom', 'USER'),
    ('sarah', 'USER'),
    ('david', 'ADMIN'),
    ('emily', 'USER'),
    ('mike', 'USER'),
    ('olivia', 'ADMIN');

INSERT INTO profiles (username, first_name, last_name,email, birthdate)
VALUES
    ('john','John','Smith','johnSmith@gmail.com','1998-05-07'),
    ('jane','jane','Lee','HappyJane@gmail.com','1973-02-14'),
    ('bob','Bob','Garcia','BigGarcia@gmail.com','1958-12-23'),
    ('alice','Alice','Lee','johnSmit2h@gmail.com','2002-01-04'),
    ('tom','Tom','Johnson','johnSmith3@gmail.com','1993-11-21'),
    ('sarah', 'Sarah', 'Taylor', 'sarahTaylor@gmail.com', '1987-09-18'),
    ('david', 'David', 'Miller', 'davidMiller@gmail.com', '1979-07-25'),
    ('emily', 'Emily', 'Chen', 'emilyChen@gmail.com', '1990-03-12'),
    ('mike', 'Mike', 'Johnson', 'mikeJohnson@gmail.com', '1985-06-30'),
    ('olivia', 'Olivia', 'Davis', 'oliviaDavis@gmail.com', '2000-10-15');
