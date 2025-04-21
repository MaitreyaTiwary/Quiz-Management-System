-- Drop the database if it exists
DROP DATABASE IF EXISTS quiz;

-- Create the database
CREATE DATABASE quiz;
USE quiz;

-- Create student_login table
CREATE TABLE IF NOT EXISTS student_login (
    Stu_id INT PRIMARY KEY AUTO_INCREMENT,
    stu_rollno INT NOT NULL,
    stu_name VARCHAR(50) NOT NULL,
    stu_username VARCHAR(50) NOT NULL,
    stu_password VARCHAR(50) NOT NULL,
    marks INT DEFAULT 0
);

-- Create teacher_login table
CREATE TABLE IF NOT EXISTS teacher_login (
    teach_id INT PRIMARY KEY AUTO_INCREMENT,
    teach_name VARCHAR(50) NOT NULL,
    teach_username VARCHAR(50) NOT NULL,
    teach_password VARCHAR(50) NOT NULL
);

-- Create Admin_login table
CREATE TABLE Admin_login (
    ad_id INT PRIMARY KEY AUTO_INCREMENT,
    adm_name VARCHAR(100),
    adm_username VARCHAR(50),
    adm_password VARCHAR(50)
);

-- Create quizzes table
CREATE TABLE IF NOT EXISTS quizzes (
    quiz_id INT PRIMARY KEY AUTO_INCREMENT,
    quiz_name VARCHAR(100) NOT NULL,
    created_by INT,
    time_limit INT,
    total_marks INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES teacher_login(teach_id)
);

-- Create questions table (now independent of quizzes)
CREATE TABLE IF NOT EXISTS questions (
    question_id INT PRIMARY KEY AUTO_INCREMENT,
    question TEXT NOT NULL,
    opt1 VARCHAR(255) NOT NULL,
    opt2 VARCHAR(255) NOT NULL,
    opt3 VARCHAR(255) NOT NULL,
    opt4 VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    difficulty ENUM('easy', 'medium', 'hard') NOT NULL,
    subject ENUM('english', 'maths', 'science') NOT NULL,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES teacher_login(teach_id)
);

-- Create quiz_questions table to map questions to quizzes
CREATE TABLE IF NOT EXISTS quiz_questions (
    quiz_id INT NOT NULL,
    question_id INT NOT NULL,
    que_no INT NOT NULL,
    PRIMARY KEY (quiz_id, question_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE
);

-- Create student_quiz_results table
CREATE TABLE IF NOT EXISTS student_quiz_results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    quiz_id INT NOT NULL,
    score INT NOT NULL,
    taken_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student_login(Stu_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
);

-- Create quiz_attempts table
CREATE TABLE IF NOT EXISTS quiz_attempts (
    attempt_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    quiz_id INT NOT NULL,
    marks INT NOT NULL,
    attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES student_login(stu_id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id)
);

-- Insert test data for Admin
INSERT INTO Admin_login (adm_name, adm_username, adm_password) 
VALUES ('Admin', 'admin', 'admin123');

-- Insert test data for Teacher
INSERT INTO teacher_login (teach_name, teach_username, teach_password) 
VALUES ('Teacher', 'teacher', 'teacher123');

-- Insert test data for Student
INSERT INTO student_login (stu_rollno, stu_name, stu_username, stu_password, marks) 
VALUES (1, 'Student', 'student', 'student123', 0);

-- Insert sample data
INSERT INTO student_login (stu_rollno, stu_name, stu_username, stu_password, marks) VALUES
(101, 'John Doe', 'john', '1234', 0),
(102, 'Jane Smith', 'jane', '1234', 0);

INSERT INTO teacher_login (teach_name, teach_username, teach_password) VALUES
('Mr. Teacher', 'teacher', 'teach@123');

-- Insert sample questions for English
INSERT INTO questions (question, opt1, opt2, opt3, opt4, answer, difficulty, subject, created_by) VALUES
-- Easy questions
('What is the past tense of "run"?', 'ran', 'runned', 'running', 'runs', 'ran', 'easy', 'english', 1),
('Which word is a noun?', 'quickly', 'beautiful', 'house', 'running', 'house', 'easy', 'english', 1),
('What is the plural of "child"?', 'childs', 'children', 'childes', 'childen', 'children', 'easy', 'english', 1),
('Which sentence is correct?', 'She go to school', 'She goes to school', 'She going to school', 'She gone to school', 'She goes to school', 'easy', 'english', 1),
('What is the opposite of "happy"?', 'sad', 'happier', 'happiness', 'happily', 'sad', 'easy', 'english', 1),

-- Medium questions
('Identify the adverb in: "She quickly ran to the store"', 'she', 'quickly', 'ran', 'store', 'quickly', 'medium', 'english', 1),
('Which sentence uses correct punctuation?', 'What time is it.', 'What time is it?', 'What time is it!', 'What time is it,', 'What time is it?', 'medium', 'english', 1),
('What is the main verb in: "The cat was sleeping on the mat"?', 'cat', 'was', 'sleeping', 'mat', 'sleeping', 'medium', 'english', 1),
('Which word is an adjective?', 'quickly', 'beautiful', 'run', 'happily', 'beautiful', 'medium', 'english', 1),
('What is the correct spelling?', 'accomodate', 'acommodate', 'accommodate', 'accomodate', 'accommodate', 'medium', 'english', 1),

-- Hard questions
('Which sentence uses the subjunctive mood?', 'I wish I was there', 'I wish I were there', 'I wish I am there', 'I wish I will be there', 'I wish I were there', 'hard', 'english', 1),
('Identify the dangling modifier in: "Running down the street, the dog chased the ball"', 'running', 'down', 'street', 'chased', 'running', 'hard', 'english', 1),
('Which sentence uses the passive voice?', 'The cat chased the mouse', 'The mouse was chased by the cat', 'The cat is chasing the mouse', 'The mouse chases the cat', 'The mouse was chased by the cat', 'hard', 'english', 1),
('What is the correct use of "affect" vs "effect"?', 'The medicine will effect the patient', 'The medicine will affect the patient', 'The medicine will have an affect', 'The medicine will have an effect', 'The medicine will affect the patient', 'hard', 'english', 1),
('Which sentence uses correct parallel structure?', 'She likes hiking, swimming, and to ride bikes', 'She likes hiking, swimming, and riding bikes', 'She likes to hike, swimming, and riding bikes', 'She likes to hike, to swim, and riding bikes', 'She likes hiking, swimming, and riding bikes', 'hard', 'english', 1),

-- Extra unused questions
('What is the correct form of "to be" in: "They ___ going to the park"?', 'is', 'are', 'am', 'be', 'are', 'easy', 'english', 1),
('Which word is a preposition?', 'quickly', 'under', 'run', 'happy', 'under', 'medium', 'english', 1),
('What is the correct use of "their", "there", and "they''re"?', 'Their going to the store', 'There going to the store', 'They''re going to the store', 'They are going to the store', 'They''re going to the store', 'hard', 'english', 1);

-- Insert sample questions for Maths
INSERT INTO questions (question, opt1, opt2, opt3, opt4, answer, difficulty, subject, created_by) VALUES
-- Easy questions
('What is 5 + 7?', '10', '11', '12', '13', '12', 'easy', 'maths', 1),
('What is 3 × 4?', '7', '12', '15', '16', '12', 'easy', 'maths', 1),
('What is 20 ÷ 5?', '3', '4', '5', '6', '4', 'easy', 'maths', 1),
('What is the next number in the sequence: 2, 4, 6, 8, ___?', '9', '10', '11', '12', '10', 'easy', 'maths', 1),
('What is 15 - 8?', '6', '7', '8', '9', '7', 'easy', 'maths', 1),

-- Medium questions
('What is 25% of 80?', '15', '20', '25', '30', '20', 'medium', 'maths', 1),
('Solve for x: 2x + 5 = 15', '5', '6', '7', '8', '5', 'medium', 'maths', 1),
('What is the area of a rectangle with length 6 and width 4?', '20', '24', '28', '32', '24', 'medium', 'maths', 1),
('What is 3/4 + 1/2?', '1', '1 1/4', '1 1/2', '2', '1 1/4', 'medium', 'maths', 1),
('What is the square root of 64?', '6', '7', '8', '9', '8', 'medium', 'maths', 1),

-- Hard questions
('What is the value of x in the equation: 3x² - 12 = 0?', '2', '-2', '2 or -2', '4', '2 or -2', 'hard', 'maths', 1),
('What is the slope of the line y = 2x + 3?', '2', '3', '1/2', '-2', '2', 'hard', 'maths', 1),
('What is the derivative of x²?', 'x', '2x', 'x²', '2x²', '2x', 'hard', 'maths', 1),
('What is the value of sin(30°)?', '0.5', '0.707', '0.866', '1', '0.5', 'hard', 'maths', 1),
('What is the probability of rolling a 6 on a fair die?', '1/2', '1/3', '1/6', '1/4', '1/6', 'hard', 'maths', 1),

-- Extra unused questions
('What is 12 × 11?', '121', '132', '143', '154', '132', 'easy', 'maths', 1),
('What is the value of π (pi) to two decimal places?', '3.14', '3.15', '3.16', '3.17', '3.14', 'medium', 'maths', 1),
('What is the integral of 2x?', 'x', 'x²', '2x²', 'x² + C', 'x² + C', 'hard', 'maths', 1);

-- Insert sample questions for Science
INSERT INTO questions (question, opt1, opt2, opt3, opt4, answer, difficulty, subject, created_by) VALUES
-- Easy questions
('What is the chemical symbol for water?', 'H2O', 'CO2', 'O2', 'H2', 'H2O', 'easy', 'science', 1),
('Which planet is known as the Red Planet?', 'Venus', 'Mars', 'Jupiter', 'Saturn', 'Mars', 'easy', 'science', 1),
('What is the largest organ in the human body?', 'Heart', 'Brain', 'Skin', 'Liver', 'Skin', 'easy', 'science', 1),
('What force keeps us on the ground?', 'Magnetism', 'Gravity', 'Friction', 'Electricity', 'Gravity', 'easy', 'science', 1),
('What is the process by which plants make food?', 'Respiration', 'Photosynthesis', 'Digestion', 'Transpiration', 'Photosynthesis', 'easy', 'science', 1),

-- Medium questions
('What is the atomic number of Carbon?', '4', '6', '8', '12', '6', 'medium', 'science', 1),
('Which gas is most abundant in Earth''s atmosphere?', 'Oxygen', 'Carbon Dioxide', 'Nitrogen', 'Hydrogen', 'Nitrogen', 'medium', 'science', 1),
('What is the unit of electrical resistance?', 'Volt', 'Ampere', 'Ohm', 'Watt', 'Ohm', 'medium', 'science', 1),
('Which blood type is the universal donor?', 'A', 'B', 'AB', 'O', 'O', 'medium', 'science', 1),
('What is the chemical formula for table salt?', 'NaCl', 'H2O', 'CO2', 'O2', 'NaCl', 'medium', 'science', 1),

-- Hard questions
('What is the speed of light in a vacuum?', '300,000 km/s', '299,792 km/s', '300,000 m/s', '299,792 m/s', '299,792 km/s', 'hard', 'science', 1),
('What is the process of nuclear fusion?', 'Splitting atoms', 'Combining atoms', 'Breaking bonds', 'Creating ions', 'Combining atoms', 'hard', 'science', 1),
('What is the function of the mitochondria?', 'Protein synthesis', 'Energy production', 'Waste removal', 'Cell division', 'Energy production', 'hard', 'science', 1),
('What is the pH of pure water?', '5', '6', '7', '8', '7', 'hard', 'science', 1),
('What is the principle behind a lever?', 'Conservation of energy', 'Conservation of momentum', 'Mechanical advantage', 'Thermodynamics', 'Mechanical advantage', 'hard', 'science', 1),

-- Extra unused questions
('What is the chemical symbol for gold?', 'Ag', 'Au', 'Fe', 'Cu', 'Au', 'easy', 'science', 1),
('What is the largest planet in our solar system?', 'Saturn', 'Jupiter', 'Neptune', 'Uranus', 'Jupiter', 'medium', 'science', 1),
('What is the process of converting light energy into chemical energy?', 'Respiration', 'Photosynthesis', 'Combustion', 'Oxidation', 'Photosynthesis', 'hard', 'science', 1);

-- Create quizzes and associate questions
-- English Quiz 1
INSERT INTO quizzes (quiz_name, created_by, time_limit, total_marks) VALUES ('English Basics Quiz', 1, 30, 50);
SET @quiz_id = LAST_INSERT_ID();
INSERT INTO quiz_questions (quiz_id, question_id, que_no) VALUES 
(@quiz_id, 1, 1), (@quiz_id, 2, 2), (@quiz_id, 3, 3), (@quiz_id, 4, 4), (@quiz_id, 5, 5),
(@quiz_id, 6, 6), (@quiz_id, 7, 7), (@quiz_id, 8, 8), (@quiz_id, 9, 9), (@quiz_id, 10, 10);

-- English Quiz 2
INSERT INTO quizzes (quiz_name, created_by, time_limit, total_marks) VALUES ('Advanced English Quiz', 1, 45, 50);
SET @quiz_id = LAST_INSERT_ID();
INSERT INTO quiz_questions (quiz_id, question_id, que_no) VALUES 
(@quiz_id, 11, 1), (@quiz_id, 12, 2), (@quiz_id, 13, 3), (@quiz_id, 14, 4), (@quiz_id, 15, 5),
(@quiz_id, 6, 6), (@quiz_id, 7, 7), (@quiz_id, 8, 8), (@quiz_id, 9, 9), (@quiz_id, 10, 10);

-- Maths Quiz 1
INSERT INTO quizzes (quiz_name, created_by, time_limit, total_marks) VALUES ('Basic Mathematics Quiz', 1, 30, 50);
SET @quiz_id = LAST_INSERT_ID();
INSERT INTO quiz_questions (quiz_id, question_id, que_no) VALUES 
(@quiz_id, 16, 1), (@quiz_id, 17, 2), (@quiz_id, 18, 3), (@quiz_id, 19, 4), (@quiz_id, 20, 5),
(@quiz_id, 21, 6), (@quiz_id, 22, 7), (@quiz_id, 23, 8), (@quiz_id, 24, 9), (@quiz_id, 25, 10);

-- Maths Quiz 2
INSERT INTO quizzes (quiz_name, created_by, time_limit, total_marks) VALUES ('Advanced Mathematics Quiz', 1, 45, 50);
SET @quiz_id = LAST_INSERT_ID();
INSERT INTO quiz_questions (quiz_id, question_id, que_no) VALUES 
(@quiz_id, 26, 1), (@quiz_id, 27, 2), (@quiz_id, 28, 3), (@quiz_id, 29, 4), (@quiz_id, 30, 5),
(@quiz_id, 21, 6), (@quiz_id, 22, 7), (@quiz_id, 23, 8), (@quiz_id, 24, 9), (@quiz_id, 25, 10);

-- Science Quiz 1
INSERT INTO quizzes (quiz_name, created_by, time_limit, total_marks) VALUES ('Basic Science Quiz', 1, 30, 50);
SET @quiz_id = LAST_INSERT_ID();
INSERT INTO quiz_questions (quiz_id, question_id, que_no) VALUES 
(@quiz_id, 31, 1), (@quiz_id, 32, 2), (@quiz_id, 33, 3), (@quiz_id, 34, 4), (@quiz_id, 35, 5),
(@quiz_id, 36, 6), (@quiz_id, 37, 7), (@quiz_id, 38, 8), (@quiz_id, 39, 9), (@quiz_id, 40, 10);

-- Science Quiz 2
INSERT INTO quizzes (quiz_name, created_by, time_limit, total_marks) VALUES ('Advanced Science Quiz', 1, 45, 50);
SET @quiz_id = LAST_INSERT_ID();
INSERT INTO quiz_questions (quiz_id, question_id, que_no) VALUES 
(@quiz_id, 41, 1), (@quiz_id, 42, 2), (@quiz_id, 43, 3), (@quiz_id, 44, 4), (@quiz_id, 45, 5),
(@quiz_id, 36, 6), (@quiz_id, 37, 7), (@quiz_id, 38, 8), (@quiz_id, 39, 9), (@quiz_id, 40, 10); 