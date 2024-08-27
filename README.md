Exam Builder System
Overview
The Exam Builder System is a Java-based application designed to facilitate the creation and management of exams. This system allows users to add, modify, and delete questions, both multiple-choice and open-ended, from a PostgreSQL database. The application also provides functionalities for users to register, log in, and manage their exam repositories by subject.

Features
1. User Management
Registration: New users can register by providing a unique username and password.
Login: Registered users can log in by entering their username and password. The system validates the credentials against the database.
User Authentication: The system ensures that only authenticated users can access and manage exam repositories.
2. Question Management
Multiple-Choice Questions:
Users can add multiple-choice questions to the repository.
The application allows users to manage the answers associated with each question, including setting the correct answer.
Open-Ended Questions:
Users can add open-ended questions to the repository.
The application allows users to manage the correct answer associated with each open-ended question.
3. Exam Repository Management
Subject-based Repositories:
Users can create repositories for different subjects. Each repository can store questions related to its subject.
Users can add and manage questions and answers within these repositories.
Dynamic Initialization:
The application initializes repositories from the database, ensuring that all existing questions and answers are loaded when the program starts.
4. Database Integration
PostgreSQL:
The application uses PostgreSQL as the backend database to store questions, answers, and user data.
Data Access Object (DAO):
The system uses the DAO pattern to handle all database operations, ensuring a clean separation between business logic and database access.
Triggers:
Database triggers are used to automatically update certain fields, such as the number_of_answers in the multiplechoice table, whenever changes are made to the related data.
5. Error Handling
Input Validation:
The system validates all user inputs to prevent invalid data from being entered into the system.
Exception Handling:
The application handles exceptions gracefully, providing meaningful messages to the user without crashing.
Installation and Setup
Prerequisites
PostgreSQL: Ensure that PostgreSQL is installed and running.
Steps
Clone the Repository:

bash
Copy code
git clone https://github.com/your-username/exam-builder.git
Set Up the Database:

Create a PostgreSQL database for the project.
Execute the SQL scripts provided in the db folder to create the necessary tables and triggers.
Update the DatabaseManager class with your PostgreSQL connection details.
Compile and Run the Project:

Navigate to the project directory.
Compile the project using your preferred IDE or build tool (e.g., javac or Maven).
Run the Main class to start the application.
Usage
Start the Application:

Run the Main class.
Log in or register to access the system.
Manage Repositories:

Create and manage subject-based repositories.
Add questions and answers to the repositories.
Create and Edit Exams:

Select questions from the repository to create an exam.
Save the exam for future use.
Contributing
If you'd like to contribute to this project, please fork the repository and use a feature branch. Pull requests are warmly welcome.
