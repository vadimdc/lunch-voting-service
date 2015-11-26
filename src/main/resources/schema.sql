-- Create syntax for TABLE 'dishes'
CREATE TABLE IF NOT EXISTS dishes (
  dish_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(191) NOT NULL,
  PRIMARY KEY (dish_id),
  UNIQUE KEY dishes_uq (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'restaurants'
CREATE TABLE IF NOT EXISTS restaurants (
  restaurant_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(191) NOT NULL,
  PRIMARY KEY (restaurant_id),
  UNIQUE KEY restaurant_uq (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'menu_items'
CREATE TABLE IF NOT EXISTS menu_items (
  menu_item_id INT NOT NULL AUTO_INCREMENT,
  restaurant_id INT NOT NULL,
  dish_id INT NOT NULL,
  price DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (menu_item_id),
  CONSTRAINT menu_items_restaurants_fk FOREIGN KEY (restaurant_id) REFERENCES restaurants (restaurant_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT menu_items_dishes_fk FOREIGN KEY (dish_id) REFERENCES dishes (dish_id) ON DELETE CASCADE ON UPDATE CASCADE,
  UNIQUE KEY restaurant_uq (restaurant_id, dish_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'voting'
CREATE TABLE IF NOT EXISTS voting (
  username VARCHAR(64) NOT NULL,
  restaurant_id INT NOT NULL,
  PRIMARY KEY (username),
  CONSTRAINT voting_restaurants_fk FOREIGN KEY (restaurant_id) REFERENCES restaurants (restaurant_id)
--  UNIQUE KEY voting_uq (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;