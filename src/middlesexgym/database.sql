DROP DATABASE IF EXISTS GYM;

CREATE DATABASE GYM;

USE GYM;

CREATE TABLE client (
   id int PRIMARY KEY AUTO_INCREMENT,
   name varchar(255) NOT NULL,
   birthYear int,
   gender CHAR,
   height int,
   weight int
);

CREATE TABLE staff (
   id int PRIMARY KEY AUTO_INCREMENT,
   name varchar(255) NOT NULL
);

CREATE TABLE trainer (id int UNIQUE, speciality int);

CREATE TABLE focus (
   id int PRIMARY KEY AUTO_INCREMENT,
   name varchar(255) NOT NULL
);

CREATE TABLE bookings (
   id int PRIMARY KEY AUTO_INCREMENT,
   client int NOT NULL,
   trainer int NOT NULL,
   date DATE NOT NULL,
   startTime TIME(0) NOT NULL,
   endTime TIME(0) NOT NULL,
   focus int
);

ALTER TABLE
   trainer
ADD
   FOREIGN KEY (id) REFERENCES staff (id);

ALTER TABLE
   trainer
ADD
   FOREIGN KEY (speciality) REFERENCES focus (id);

ALTER TABLE
   bookings
ADD
   FOREIGN KEY (client) REFERENCES client (id);

ALTER TABLE
   bookings
ADD
   FOREIGN KEY (trainer) REFERENCES trainer (id);

ALTER TABLE
   bookings
ADD
   FOREIGN KEY (focus) REFERENCES focus (id);

INSERT INTO
   GYM.client (
      `id`,
      `name`,
      `birthyear`,
      `gender`,
      `height`,
      `weight`
   )
VALUES
   (
      '1',
      'William J. Decker',
      '1941',
      'M',
      '165',
      '95.3'
   ),
   (
      '2',
      'Heather M. Williams',
      '1945',
      'F',
      '164',
      '49.6'
   ),
   ('3', 'Kiera Noble', '1996', 'F', '155', '59.8'),
   (
      '4',
      'Thomas K. Kirby',
      '1997',
      'M',
      '180',
      '87.6'
   ),
   ('5', 'Jodie Conway', '1970', 'F', '164', '72.3'),
   ('6', 'Alan R. Tower', '1999', 'M', '189', '92.6'),
   (
      '7',
      'Sheila M. Beltran',
      '1993',
      'F',
      '158',
      '80.9'
   );

INSERT INTO
   GYM.staff (`id`, `name`)
VALUES
   ('1', 'John Pinkerton'),
   ('2', 'Jeff Sweat'),
   ('3', 'Bianca Simon'),
   ('4', 'Corina Cooper'),
   ('5', 'Pauline Sasse'),
   ('6', 'Joyce Tapia'),
   ('7', 'Helga Levesque');

INSERT INTO
   GYM.trainer (`id`, `speciality`)
VALUES
   ('1', '2'),
   ('2', '7'),
   ('4', '1'),
   ('5', '6'),
   ('7', '3');

INSERT INTO
   GYM.focus (`id`, `name`)
VALUES
   ('1', 'Chest'),
   ('2', 'Back'),
   ('3', 'Shoulders'),
   ('4', 'Legs'),
   ('5', 'Biceps'),
   ('6', 'Triceps'),
   ('7', 'Calves');

INSERT INTO
   GYM.trainer (`id`, `speciality`)
VALUES
   ('1', '2'),
   ('2', '7'),
   ('4', '3'),
   ('5', '6'),
   ('7', '4');

INSERT INTO
   GYM.bookings (
      `id`,
      `client`,
      `trainer`,
      `date`,
      `startTime`,
      `endTime`,
      `focus`
   )
VALUES
   (
      '1',
      '4',
      '2',
      '2020-02-01',
      '09:00:00',
      '09:30:00',
      '2'
   ),
   (
      '2',
      '3',
      '7',
      '2020-02-04',
      '13:00:00',
      '15:00:00',
      '6'
   ),
   (
      '3',
      '2',
      '4',
      '2020-02-07',
      '16:00:00',
      '17:30:00',
      '7'
   ),
   (
      '4',
      '7',
      '5',
      '2020-02-05',
      '10:00:00',
      '13:00:00',
      '1'
   ),
   (
      '5',
      '1',
      '2',
      '2020-02-02',
      '17:30:00',
      '19:00:00',
      '4'
   );