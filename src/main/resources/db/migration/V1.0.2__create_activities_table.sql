
CREATE TABLE IF NOT EXISTS getyourguide.activity (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255),
    price FLOAT,
    currency VARCHAR(16),
    rating FLOAT,
    special_offer BOOLEAN,
    supplier_id BIGINT,
    CONSTRAINT fk_supplier FOREIGN KEY (supplier_id) REFERENCES getyourguide.supplier(id) ON DELETE CASCADE
);


INSERT INTO getyourguide.activity (title, price, currency, rating, special_offer, supplier_id) VALUES
('German Tour: Parliament Quarter & Reichstag glass dome', 14, '$', 4.8, FALSE, 1),
( 'Skip the Line: Pergamon Museum Berlin Tickets', 21, '$', 4.8, FALSE, 2),
( 'Berlin WelcomeCard: Transport, Discounts & Guide Book', 10, '$', 4.6, FALSE, 2),
( 'Skip the Line: Berlin TV Tower Ticket', 143, '$', 4.6, FALSE, 2),
( '1-Hour City Tour by Boat with Guaranteed Seating', 14, '$', 4.5, FALSE, 1),
( 'Berlin Hop-on Hop-off Bus Tour: 1- or 2-Day Ticket', 210, '$', 4.3, FALSE,3),
( 'German Tour: Reichstag with Plenary Chamber & Cuppola', 59, '$', 4.8, FALSE,4),
( 'Berlin: 2.5-Hour Boat Tour Along the River Spree', 41, '$', 4.5, TRUE, 1),
( 'Berlin: 2.5-Hour Boat Tour Along the River Spree', 45, '$', 4.7, TRUE, 1),
( 'Berlin: 2.5-Hour Boat Tour Along the River Spree', 49, '$', 4.7, TRUE, 1),
( 'Museum Pass Berlin: 3-Day Entry to Over 40 Top Museums', 14, '$', 4.5, FALSE,4),
( 'Reichstag: Rooftop Coffee Break at Käfer', 50, '$', 4.6, TRUE, 2),
( 'Skip the Line: Neues Museum Berlin Tickets', 46, '$', 4.9, TRUE,4),
( 'German Tour: Reichstag with dome Chamber &', 87, '$', 4.8, FALSE,3),
( 'Berlin Hop-on Hop-off Bus Tour with Live Commentary', 8, '$', 4.3, FALSE, 1),
( 'Skip the Line: TV Tower Early Bird Tickets', 140, '$', 4.5, FALSE, 2),
( 'City Pass: 3 Days Ticket', 149, '$', 4.2, FALSE, 5);


