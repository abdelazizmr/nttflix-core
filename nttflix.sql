-- database nttflix
use nttflix;
-- category table
INSERT INTO categorie (name) VALUES 
('Horror'),
('Comedy'),
('Drama'),
('Thriller'),
('Action');
-- movie table 
INSERT INTO movie (title, description, rating, pic, idCateg) VALUES 
('The Conjuring', 'A terrifying tale of a family haunted by evil spirits', 8.5, 'conjuring.jpg', 1),
('The Hangover', 'A wild adventure of friends waking up from a bachelor party', 7.8, 'hangover.jpg', 2),
('The Shawshank Redemption', 'A gripping story of hope and redemption in a prison', 9.3, 'shawshank.jpg', 3),
('Inception', 'A mind-bending journey into the depths of dreams and reality', 8.8, 'inception.jpg', 4),
('Die Hard', 'An action-packed thriller featuring a cop fighting terrorists', 7.6, 'diehard.jpg', 5);
-- comment table
INSERT INTO comment (message, email, movie_id) VALUES 
('Scared me to death!', 'example1@example.com', 1),
('Hilarious!', 'example2@example.com', 2),
('Touching story', 'example3@example.com', 3),
('Mind-blowing!', 'example4@example.com', 4),
('Action-packed!', 'example5@example.com', 5);

