
DROP DATABASE IF EXISTS exercises_db;

CREATE DATABASE exercises_db;

USE exercises_db;

CREATE TABLE exercises (
    exercise_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    code_languages VARCHAR(100)
);
