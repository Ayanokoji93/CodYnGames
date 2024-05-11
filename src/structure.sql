
DROP DATABASE IF EXISTS exercises_db;

CREATE DATABASE exercises_db;

USE exercises_db;



CREATE TABLE exercises (
    exercise_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    code_languages VARCHAR(100)
);

CREATE TABLE programmingLanguages (
    language_id INT AUTO_INCREMENT PRIMARY KEY,
    language_name VARCHAR(50)
);

CREATE TABLE userattempts (
    attempt_id INT AUTO_INCREMENT PRIMARY KEY,
    exercise_id INT,
    user_id INT,
    language_id INT,
    code_attempt TEXT,
    is_successful BOOLEAN,
    error_message TEXT,
    created_at TIMESTAMP,
    FOREIGN KEY (exercise_id) REFERENCES Exercises(exercise_id),
    FOREIGN KEY (language_id) REFERENCES ProgrammingLanguages(language_id)
);

CREATE TABLE testcases (
    testcase_id INT AUTO_INCREMENT PRIMARY KEY,
    exercise_id INT,
    input_data TEXT,
    expected_output TEXT,
    FOREIGN KEY (exercise_id) REFERENCES Exercises(exercise_id)
);

CREATE TABLE exercisestats (
    exercise_id INT PRIMARY KEY,
    attempts INT,
    successes INT,
    failures INT,
    sessions INT,
    FOREIGN KEY (exercise_id) REFERENCES Exercises(exercise_id)
);
