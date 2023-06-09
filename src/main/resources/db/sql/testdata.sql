INSERT INTO groups (group_name, level, points)
VALUES
    ('Group A', 1, 100),
    ('Group B', 2, 250),
    ('Group C', 3, 500),
    ('Group D', 4, 750),
    ('Group E', 1, 200);

INSERT INTO users (username, password, enabled, first_name, last_name, authority, email, birthdate, group_id)
VALUES
    ('john', '{noop}password123', true, 'John', 'Doe', 'USER', 'john@example.com', '1990-01-01', 1),
    ('jane', '{noop}password456', true, 'Jane', 'Doe', 'USER', 'jane@example.com', '1992-02-02', 1),
    ('bob', '{noop}password789', true, 'Bob', 'Smith', 'USER', 'bob@example.com', '1995-03-03', 2),
    ('alice', '{noop}password111', true, 'Alice', 'Johnson', 'USER', 'alice@example.com', '1998-04-04', 2),
    ('tom', '{noop}password222', true, 'Tom', 'Brown', 'USER', 'tom@example.com', '2000-05-05', 3),
    ('sarah', '{noop}password333', true, 'Sarah', 'Lee', 'USER', 'sarah@example.com', '2002-06-06', 3),
    ('david', '{noop}password444', true, 'David', 'Wilson', 'USER', 'david@example.com', '2004-07-07', 4),
    ('emily', '{noop}password555', true, 'Emily', 'Davis', 'USER', 'emily@example.com', '2006-08-08', 4),
    ('mike', '{noop}password666', true, 'Mike', 'Taylor', 'USER', 'mike@example.com', '2008-09-09', 5),
    ('olivia', '{noop}password777', true, 'Olivia', 'Clark', 'USER', 'olivia@example.com', '2010-10-10', 5);

INSERT INTO messages (senderName, group_id, message, is_deleted)
VALUES
    ('john', 1, 'Hello everyone!', false),
    ('jane', 2, 'Welcome to our group.', false),
    ('bob', 3, 'Lets discuss the upcoming event.', false),
	('alice', 4, 'Hi there!', false),
	('tom', 5, 'Has anyone seen my keys?', false),
	('sarah', 6, 'Good morning!', false),
	('david', 7, 'How is everyone doing?', false),
	('emily', 8, 'Just checking in.', false),
	('mike', 9, 'Whats the plan for today?', false),
    ('olivia', 10, 'I have an announcement to make.', false),
    ('john', 11, 'Great job, team!', false),
    ('jane', 12, 'Im excited about the upcoming project.', false),
	('bob', 13, 'Any updates on the deadline?', false),
	('alice', 14, 'Looking forward to the weekend.', false),
	('tom', 15, 'Im feeling under the weather today.', false),
    ('sarah', 16, 'Happy birthday!', false),
    ('david', 17, 'Congratulations on the promotion.', false),
    ('emily', 18, 'Can someone help me with this task?', false),
    ('mike', 19, 'Dont forget about the meeting tomorrow.', false),
	('olivia', 20, 'Thanks for your support.', false);

INSERT INTO allergy (allergy_name, allergy_description)
VALUES
    ('Peanut', 'Allergic reaction to peanuts.'),
    ('Lactose', 'Allergic reaction to lactose in dairy products.'),
    ('Shellfish', 'Allergic reaction to shellfish like shrimp, crab, or lobster.'),
    ('Gluten', 'Allergic reaction to gluten found in wheat, barley, and rye.'),
    ('Soy', 'Allergic reaction to soybeans and soy-based products.'),
    ('Tree nuts', 'Allergic reaction to tree nuts like almonds, walnuts, or cashews.'),
    ('Eggs', 'Allergic reaction to eggs.'),
    ('Fish', 'Allergic reaction to fish like salmon, tuna, or cod.'),
    ('Wheat', 'Allergic reaction to wheat.'),
    ('Sesame', 'Allergic reaction to sesame seeds or sesame-based products.'),
    ('Mustard', 'Allergic reaction to mustard.'),
    ('Sulfites', 'Allergic reaction to sulfites used as preservatives in certain foods and drinks.'),
    ('Celery', 'Allergic reaction to celery.'),
    ('Mollusks', 'Allergic reaction to mollusks like clams, mussels, or oysters.'),
    ('Cottonseed', 'Allergic reaction to cottonseed oil or cottonseed-based products.'),
    ('Sunflower', 'Allergic reaction to sunflower seeds or sunflower-based products.'),
    ('Meat', 'Allergic reaction to meat products.'),
    ('Corn', 'Allergic reaction to corn or corn-based products.'),
    ('Nightshades', 'Allergic reaction to nightshade vegetables like tomatoes, potatoes, or eggplants.');

INSERT INTO product (ean, item_name, description)
VALUES
    (100000000001, 'Apple', 'Fresh and juicy apple.'),
    (100000000002, 'Banana', 'Ripe and yellow banana.'),
    (100000000003, 'Orange', 'Sweet and tangy orange.'),
    (100000000004, 'Strawberry', 'Plump and red strawberry.'),
    (100000000005, 'Grapes', 'Juicy and sweet grapes.'),
    (100000000006, 'Watermelon', 'Refreshing and juicy watermelon.'),
    (100000000007, 'Carrot', 'Crunchy and nutritious carrot.'),
    (100000000008, 'Broccoli', 'Healthy and nutritious broccoli.'),
    (100000000009, 'Spinach', 'Fresh and leafy spinach.'),
    (100000000010, 'Tomato', 'Plump and red tomato.'),
    (100000000011, 'Cucumber', 'Crisp and refreshing cucumber.'),
    (100000000012, 'Lettuce', 'Fresh and crispy lettuce.'),
    (100000000013, 'Potato', 'Versatile and starchy potato.'),
    (100000000014, 'Onion', 'Flavorful and aromatic onion.'),
    (100000000015, 'Garlic', 'Pungent and aromatic garlic.'),
    (100000000016, 'Avocado', 'Creamy and healthy avocado.'),
    (100000000017, 'Cheese', 'Rich and flavorful cheese.'),
    (100000000018, 'Yogurt', 'Creamy and probiotic-rich yogurt.'),
    (100000000019, 'Milk', 'Fresh and nutritious milk.'),
    (100000000020, 'Butter', 'Smooth and creamy butter.'),
    (100000000021, 'Bread', 'Fresh and fluffy bread.'),
    (100000000022, 'Rice', 'Versatile and staple rice.'),
    (100000000023, 'Pasta', 'Al dente and flavorful pasta.'),
    (100000000024, 'Chicken', 'Lean and protein-rich chicken.'),
    (100000000025, 'Beef', 'Tender and juicy beef.'),
    (100000000026, 'Pork', 'Savory and succulent pork.'),
    (100000000027, 'Fish', 'Fresh and flavorful fish.'),
    (100000000028, 'Shrimp', 'Sweet and succulent shrimp.'),
    (100000000029, 'Salmon', 'Rich and omega-3 packed salmon.'),
    (100000000030, 'Tuna', 'Flaky and delicious tuna.'),
    (100000000031, 'Crab', 'Sweet and delicate crab meat.'),
    (100000000032, 'Lobster', 'Indulgent and flavorful lobster meat.'),
    (100000000033, 'Eggs', 'Fresh and protein-rich eggs.'),
    (100000000034, 'Bacon', 'Savory and crispy bacon.'),
    (100000000035, 'Sausages', 'Juicy and flavorful sausages.'),
    (100000000036, 'Ham', 'Smoky and delicious ham.'),
    (100000000037, 'Soup', 'Comforting and flavorful soup.'),
    (100000000038, 'Chips', 'Crunchy and addictive chips.'),
    (100000000039, 'Cookies', 'Sweet and delicious cookies.'),
    (100000000040, 'Chocolate', 'Rich and indulgent chocolate.');

INSERT INTO items_allergies (ean, allergy_name)
VALUES
    (100000000001, 'Peanut'),
    (100000000001, 'Tree nuts'),
    (100000000001, 'Soy'),
    (100000000002, 'Lactose'),
    (100000000002, 'Soy'),
    (100000000002, 'Wheat'),
    (100000000002, 'Eggs'),
    (100000000003, 'Lactose'),
    (100000000003, 'Soy'),
    (100000000004, 'Sesame'),
    (100000000004, 'Corn'),
    (100000000004, 'Soy'),
    (100000000004, 'Mollusks'),
    (100000000005, 'Mollusks'),
    (100000000005, 'Sulfites'),
    (100000000006, 'Peanut'),
    (100000000007, 'Eggs'),
    (100000000008, 'Sesame'),
    (100000000008, 'Eggs'),
    (100000000009, 'Peanut'),
    (100000000009, 'Eggs'),
    (100000000009, 'Lactose'),
    (100000000010, 'Sesame');

INSERT INTO users_allergies (username, allergy_name)
VALUES
    ('john', 'Peanuts'),
    ('jane', 'Eggs'),
    ('bob', 'Shellfish'),
    ('alice', 'Milk'),
    ('tom', 'Wheat'),
    ('sarah', 'Soy'),
    ('david', 'Tree Nuts'),
    ('jane', 'Fish'),
    ('mike', 'Soy'),
    ('david', 'Peanuts'),
    ('sarah', 'Eggs'),
    ('john', 'Milk'),
    ('bob', 'Soy'),
    ('tom', 'Eggs');

INSERT INTO FRIDGE (group_id)
VALUES
    (1),
    (2),
    (3),
    (4),
    (5);

INSERT INTO FRIDGE_PRODUCT (fridge_id, ean)
VALUES
    (1, 1000000000001),
    (1, 1000000000002),
    (1, 1000000000003),
    (1, 1000000000004),
    (1, 1000000000005),
    (2, 1000000000002),
    (2, 1000000000003),
    (2, 1000000000006),
    (2, 1000000000007),
    (2, 1000000000008),
    (3, 1000000000001),
    (3, 1000000000003),
    (3, 1000000000004),
    (3, 1000000000006),
    (3, 1000000000009),
    (4, 1000000000002),
    (4, 1000000000004),
    (4, 1000000000007),
    (4, 1000000000008),
    (4, 1000000000010),
    (5, 1000000000003),
    (5, 1000000000005),
    (5, 1000000000006),
    (5, 1000000000007),
    (5, 1000000000010);

INSERT INTO shopping_list (group_id)
VALUES
    (1),
    (2),
    (3),
    (4),
    (5);

INSERT INTO shopping_list_product (shopping_list_id, ean)
VALUES
    (1, 100000000001),
    (1, 100000000002),
    (1, 100000000003),
    (1, 100000000004),
    (1, 100000000005),
    (2, 100000000006),
    (2, 100000000007),
    (2, 100000000008),
    (2, 100000000009),
    (2, 100000000010),
    (3, 100000000011),
    (3, 100000000012),
    (3, 100000000013),
    (3, 100000000014),
    (3, 100000000015),
    (4, 100000000016),
    (4, 100000000017),
    (4, 100000000018),
    (4, 100000000019),
    (4, 100000000020),
    (5, 100000000021),
    (5, 100000000022),
    (5, 100000000023),
    (5, 100000000024),
    (5, 100000000025),
    (1, 100000000026),
    (1, 100000000027),
    (1, 100000000028),
    (1, 100000000029),
    (1, 100000000030),
    (2, 100000000031),
    (2, 100000000032),
    (2, 100000000033),
    (2, 100000000034),
    (2, 100000000035),
    (3, 100000000036),
    (3, 100000000037),
    (3, 100000000038),
    (3, 100000000039),
    (3, 100000000040);

INSERT INTO recipe (recipe_name, description)
VALUES
    ('Spaghetti Bolognese', 'Classic Italian pasta dish with meat sauce.'),
    ('Chicken Alfredo', 'Creamy and rich pasta dish with chicken.'),
    ('Beef Stroganoff', 'Russian dish with tender beef and mushroom sauce.'),
    ('Baked Ziti', 'Baked pasta dish with meat, cheese, and tomato sauce.'),
    ('Lemon Garlic Shrimp', 'Shrimp cooked in a tangy lemon garlic sauce.'),
    ('Vegetable Stir-Fry', 'Stir-fried vegetables with a savory sauce.'),
    ('Chicken Fajitas', 'Mexican dish with marinated chicken and peppers.'),
    ('Beef and Broccoli', 'Chinese dish with tender beef and broccoli.'),
    ('Spinach and Ricotta Stuffed Shells', 'Pasta shells filled with spinach and ricotta cheese.'),
    ('Baked Chicken Parmesan', 'Breaded and baked chicken with marinara sauce and cheese.');


INSERT INTO recipes_products (recipe_id, ean)
VALUES
    (4,100000000023),
    (4,100000000024),
    (4,100000000014),
    (5,100000000023),
    (5,100000000017),
    (5,100000000038),
    (6,100000000024),
    (6,100000000013),
    (6,100000000008),
    (7,100000000023),
    (7,100000000017),
    (7,100000000013),
    (7,100000000034),
    (8,100000000028),
    (8,100000000015),
    (8,100000000035),
    (9,100000000003),
    (9,100000000004),
    (9,100000000007),
    (9,100000000010),
    (9,100000000012),
    (10,100000000024),
    (10,100000000031),
    (10,100000000011),
    (10,100000000008),
    (11,100000000008),
    (11,100000000019),
    (11,100000000012),
    (11,100000000005),
    (12,100000000023),
    (12,100000000009),
    (12,100000000017),
    (13,100000000023),
    (13,100000000034),
    (13,100000000017),
    (13,100000000021),
    (13,100000000020);

INSERT INTO wastes (group_id, ean, timestamp, amount, unit)
VALUES
    (6, 100000000016, '2023-04-20 11:30:00', 2, 'pieces'),
    (2, 100000000028, '2023-04-20 12:00:00', 150, 'grams'),
    (5, 100000000037, '2023-04-20 12:30:00', 1, 'liter'),
    (3, 100000000009, '2023-04-20 13:00:00', 500, 'grams'),
    (4, 100000000022, '2023-04-20 13:30:00', 1, 'kilogram'),
    (6, 100000000005, '2023-04-20 14:00:00', 3, 'bunches'),
    (1, 100000000023, '2023-04-20 14:30:00', 250, 'grams'),
    (3, 100000000010, '2023-04-20 15:00:00', 2, 'pieces'),
    (5, 100000000038, '2023-04-20 15:30:00', 1, 'pack'),
    (4, 100000000024, '2023-04-20 16:00:00', 750, 'grams'),
    (3, 100000000040, '2023-04-20 16:30:00', 3, 'bars'),
    (1, 100000000012, '2023-04-20 17:00:00', 2, 'pieces'),
    (6, 100000000007, '2023-04-20 17:30:00', 500, 'grams'),
    (4, 100000000029, '2023-04-20 18:00:00', 1, 'kilogram'),
    (3, 100000000013, '2023-04-20 18:30:00', 750, 'grams'),
    (2, 100000000031, '2023-04-20 19:00:00', 300, 'grams'),
    (5, 100000000036, '2023-04-20 19:30:00', 6, 'pieces'),
    (6, 100000000003, '2023-04-20 20:00:00', 1, 'kilogram'),
    (1, 100000000034, '2023-04-20 20:30:00', 250, 'grams'),
    (4, 100000000002, '2023-04-20 21:00:00', 1, 'bunch');

INSERT INTO achievement  (achievement_name, achievement_description)
VALUES
    ('Novice', 'Reached level 10'),
    ('Apprentice', 'Reached level 20'),
    ('Journeyman', 'Reached level 30'),
    ('Expert', 'Reached level 40'),
    ('Master', 'Reached level 50'),
    ('Legend', 'Reached level 60'),
    ('Myth', 'Reached level 70'),
    ('Epic', 'Reached level 80'),
    ('Godlike', 'Reached level 90'),
    ('Immortal', 'Reached level 100');

INSERT INTO achievements_groups (achievement_name, group_id) VALUES
                                                                 ('Novice', 2),
                                                                 ('Apprentice', 2),
                                                                 ('Journeyman', 3),
                                                                 ('Expert', 4),
                                                                 ('Master', 5),
                                                                 ('Legend', 5),
                                                                 ('Myth', 5),
                                                                 ('Epic', 6),
                                                                 ('Godlike', 6),
                                                                 ('Immortal', 6);