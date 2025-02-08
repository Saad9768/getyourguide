create schema if not exists getyourguide;
CREATE TABLE getyourguide.supplier (
  id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name VARCHAR(255),
  address VARCHAR(255),
  zip VARCHAR(20),
  city VARCHAR(100),
  country VARCHAR(100)
);


INSERT INTO getyourguide.supplier ( name, address, zip, city, country) VALUES
('John Doe', '123 Main St', '12345', 'Anytown', 'USA'),
( 'Jane Doe', '456 Main St', '12345', 'Anytown', 'USA'),
('Charlie Doe', '678 Main St', '12345', 'Anytown', 'USA'),
('Jackie Chan', '789 Main St', '10000', 'Hong Kong', 'China'),
('Ion Popescu', 'Str. Veseliei, Nr. 4', '253445', 'Bucharest', 'Romania');

