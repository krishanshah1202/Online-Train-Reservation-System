-- Create the database
CREATE DATABASE TrainReservationSystem;

-- Use the database
USE TrainReservationSystem;
ALTER TABLE Bookings ADD username VARCHAR(50);

-- Table for storing information about trains
CREATE TABLE Trains (
    train_no INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(100) NOT NULL,
    from_location VARCHAR(100) NOT NULL,
    to_location VARCHAR(100) NOT NULL,
    departure_date DATE NOT NULL,
    class VARCHAR(20) NOT NULL,
    quota VARCHAR(20) NOT NULL
);
ALTER TABLE Trains
DROP COLUMN departure_date;
-- Table for storing information about users
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    mobile_no VARCHAR(15) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Table for storing information about bookings

DROP DATABASE TrainReservationSystem;
select * from bookings;
select * from users;

drop table Trains;
drop table users;

-- Create the Trains table without departure_date, class, and quota fields
CREATE TABLE Trains (
    train_no INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(100) NOT NULL,
    from_location VARCHAR(100) NOT NULL,
    to_location VARCHAR(100) NOT NULL
);

-- Insert data into the Trains table
INSERT INTO Trains (train_no, train_name, from_location, to_location)
VALUES
    (1001, 'Express', 'New Delhi', 'Mumbai'),
    (1002, 'Rajdhani Express', 'Mumbai', 'New Delhi'),
    (1003, 'Shatabdi Express', 'Chennai', 'Bangalore'),
    (1004, 'Duronto Express', 'Kolkata', 'Pune'),
    (1005, 'Garib Rath', 'Hyderabad', 'Ahmedabad'),
    (1006, 'Jan Shatabdi', 'Pune', 'Chennai'),
    (1007, 'Tejas Express', 'Bangalore', 'Mumbai'),
    (1008, 'Duronto Express', 'Ahmedabad', 'New Delhi'),
    (1009, 'Shatabdi Express', 'Mumbai', 'Chennai'),
    (1010, 'Rajdhani Express', 'Kolkata', 'Hyderabad'),
    (1011, 'Garib Rath', 'New Delhi', 'Bangalore'),
    (1012, 'Duronto Express', 'Chennai', 'Kolkata'),
    (1013, 'Jan Shatabdi', 'Bangalore', 'Pune'),
    (1014, 'Tejas Express', 'Mumbai', 'Ahmedabad'),
    (1015, 'Express', 'Pune', 'New Delhi');


CREATE TABLE Bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pnr VARCHAR(10) UNIQUE,
    train_no INT,
    name VARCHAR(255),
    age INT,
    gender VARCHAR(10),
    uid VARCHAR(255),
    departure_date DATE,
    class VARCHAR(20),
    quota VARCHAR(20),
    source VARCHAR(255),
    destination VARCHAR(255)
);

select * from Bookings;

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    date_of_birth DATE,
    gender VARCHAR(10),
    mobile_no VARCHAR(15),
    username VARCHAR(50),
    password VARCHAR(255) -- Storing passwords securely, consider using hashing algorithms
);
ALTER TABLE Users MODIFY COLUMN date_of_birth VARCHAR(20);
ALTER TABLE Bookings
DROP COLUMN booking_id;

