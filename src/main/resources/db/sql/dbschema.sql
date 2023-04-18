CREATE TABLE users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

CREATE TABLE profiles (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    birthdate DATE NOT NULL,
    creation_time TIMESTAMP NOT NULL DEFAULT current_timestamp,
    group_id BIGINT NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (group_id),
    CONSTRAINT fk_profile_user FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX ix_profiles_username ON profiles (username, id);

CREATE TABLE groups(
    group_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(50) NOT NULL,
    level INT NOT NULL DEFAULT 0,
    points INT NOT NULL DEFAULT 0
);

CREATE TABLE messages (
    message_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    senderName VARCHAR(50) NOT NULL,
    group_id BIGINT NOT NULL,
    message VARCHAR(255) NOT NULL,
    message_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (group_id) REFERENCES groups (group_id),
    FOREIGN KEY (senderName) REFERENCES users (username)
);

CREATE TABLE allergy (
    allergy_name VARCHAR(50) NOT NULL PRIMARY KEY,
    allergy_description VARCHAR(255) NOT NULL
);


CREATE TABLE product (
    ean BIGINT NOT NULL PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE items_allergies (
    ean BIGINT NOT NULL,
    allergy_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_item_allergy PRIMARY KEY (ean, allergy_name),
    FOREIGN KEY (ean) REFERENCES product (ean),
    FOREIGN KEY (allergy_name) REFERENCES allergy (allergy_name)
);

CREATE TABLE users_allergies (
    username BIGINT NOT NULL,
    allergy_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_user_category PRIMARY KEY (username, allergy_name),
    FOREIGN KEY (username) REFERENCES users (username),
    FOREIGN KEY (allergy_name) REFERENCES allergy (allergy_name)
);

CREATE TABLE FRIDGE (
    fridge_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE FRIDGE_PRODUCT (
    fridge_id BIGINT NOT NULL,
    ean BIGINT NOT NULL,
    CONSTRAINT PK_fridge_group PRIMARY KEY (fridge_id,ean),
    FOREIGN KEY (fridge_id) REFERENCES FRIDGE(fridge_id),
    FOREIGN KEY (ean) REFERENCES product(ean)
);

CREATE TABLE shopping_list (
    shopping_list_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
);

CREATE TABLE shopping_list_product(
    shopping_list_id BIGINT NOT NULL,
    ean BIGINT NOT NULL,
    CONSTRAINT PK_shopping_list_group PRIMARY KEY (shopping_list_id,ean),
    FOREIGN KEY (shopping_list_id) REFERENCES FRIDGE(fridge_id),
    FOREIGN KEY (ean) REFERENCES product(ean)
);

CREATE TABLE recipe(
    recipe_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    recipe_name VARCHAR(50) NOT NULL,
    description VARCHAR(500) NOT NULL
);

CREATE TABLE recipes_products(
    recipe_id BIGINT NOT NULL,
    ean BIGINT NOT NULL,
    CONSTRAINT PK_recipe_product PRIMARY KEY (recipe_id,ean),
    FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id),
    FOREIGN KEY (ean) REFERENCES product(ean)
);

CREATE TABLE favorite(
    recipe_id BIGINT NOT NULL,
    username varchar(50) NOT NULL,
    CONSTRAINT PK_recipe_user PRIMARY KEY (recipe_id,username),
    FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE WASTES(
    waste_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT NOT NULL,
    ean BIGINT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount DOUBLE NOT NULL,
    FOREIGN KEY (group_id) references groups(group_id),
    FOREIGN KEY (ean) references product(ean)
)