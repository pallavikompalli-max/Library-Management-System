# 📚 Library Management System

A console-based Library Management System developed using **Java, MySQL, JDBC, and Maven** to simplify book inventory management, student registration, and book issue/return operations.

## 📌 Project Overview

The Library Management System provides a centralized way to manage library records through a Java console application connected to a MySQL relational database.

It allows library staff to maintain book information, register students, track borrowed books, and update book availability when books are issued or returned.

The project demonstrates Java programming, database connectivity, SQL operations, object-oriented programming concepts, and transaction management.

## 🎯 Objectives

- Digitize basic library management operations.
- Maintain book and student records in a relational database.
- Simplify book searching and inventory tracking.
- Record book issue and return transactions.
- Maintain accurate book availability through database updates.
- Practise Java, SQL, JDBC, and software development fundamentals.

## ✨ Features

### 📖 Book Management
- Add new books with title, author, category, and quantity.
- Display all book records.
- Search books by title or author.
- Update existing book details.
- Delete book records when permitted by database constraints.

### 👩‍🎓 Student Management
- Register students with their names and email addresses.
- Assign unique student IDs through the database.
- Prevent duplicate email addresses using a database constraint.

### 🔄 Book Issue and Return
- Issue available books to registered students.
- Verify book and student IDs before issuing.
- Record issue dates and due dates.
- Update available quantities when books are issued.
- Record returns and restore available quantities.
- Use database transactions to keep issue/return updates consistent.

### 🗄️ Database Management
- Store records persistently in MySQL.
- Use primary keys and foreign keys to maintain relationships.
- Use parameterized SQL queries through JDBC.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Application logic |
| MySQL 8.0 | Relational database |
| JDBC | Java–database connectivity |
| Maven | Dependency management and build |
| SQL | Database creation and data operations |
| Visual Studio Code | Development environment |
| Git and GitHub | Version control and source-code hosting |

## 🏗️ System Architecture

The application follows a simple layered structure:

1. **User Interface:** Console menu for accepting user input.
2. **Application Logic:** Java methods for validating and processing library operations.
3. **Data Access Layer:** JDBC and DAO methods for executing SQL statements.
4. **Database Layer:** MySQL tables for books, students, and issue records.

### Application Flow

```text
User
  |
  v
Java Console Menu
  |
  v
Application Logic
  |
  v
JDBC / DAO Layer
  |
  v
MySQL Database
  |
  v
Results displayed to the user
```

## 🗃️ Database Design

The project uses three relational tables.

### 1. Books

Stores book information.

- `book_id` — Primary key
- `title` — Book title
- `author` — Author name
- `category` — Book category
- `quantity` — Available copy count

### 2. Students

Stores registered student details.

- `student_id` — Primary key
- `name` — Student name
- `email` — Unique email address

### 3. Issued Books

Maintains borrowing and return records.

- `issue_id` — Primary key
- `book_id` — Foreign key referencing books
- `student_id` — Foreign key referencing students
- `issue_date` — Date the book was issued
- `due_date` — Expected return date
- `return_date` — Actual return date; `NULL` while the book remains unreturned

## 📂 Project Structure

```text
LibraryManagement/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java
│           ├── DBConnection.java
│           ├── BookDAO.java
│           └── LibraryService.java
├── pom.xml
├── database.sql
├── .gitignore
└── README.md
```

## ⚙️ Prerequisites

Install the following software before running the project:

- Java Development Kit (JDK) 17
- MySQL Server
- Apache Maven
- Visual Studio Code or another Java IDE

## 🚀 Installation and Setup

### Step 1: Clone the repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
cd LibraryManagement
```

Replace the placeholder with your actual repository URL after uploading the project.

### Step 2: Create the database

Open MySQL and execute the SQL script provided in `database.sql`.

The script should create the `library_db` database and its three tables.

### Step 3: Configure database credentials

Configure the MySQL connection in `DBConnection.java`.

Set your database username and password locally. Never commit actual credentials to the repository.

### Step 4: Compile the project

Run the following command from the project root:

```bash
mvn compile
```

### Step 5: Run the application

```bash
mvn org.codehaus.mojo:exec-maven-plugin:3.5.0:java "-Dexec.mainClass=Main"
```

## 🧪 Testing

Test the application using the following scenarios:

- Add a new book and verify that it appears in the database.
- Search for an existing book by title or author.
- Update book information and confirm the changes.
- Register a student and verify the generated student ID.
- Issue a book to a valid student.
- Attempt to issue a book using an invalid student ID.
- Return an issued book and verify the updated quantity.
- Attempt to return the same issue twice and verify that it is rejected.

## 🔒 Data Integrity and Reliability

- Parameterized SQL queries help prevent SQL injection.
- Primary keys uniquely identify records.
- Foreign keys protect relationships between books, students, and issue records.
- Database transactions help keep issue and return operations consistent.
- Input validation helps reject invalid user entries.

## 🚧 Future Enhancements

- Graphical user interface using Java Swing or JavaFX.
- Automatic fine calculation for overdue books.
- Student borrowing history and active-loan reports.
- Improved reporting and inventory summaries.
- Automated unit and integration tests.
- Role-based access for librarians and administrators.

## 🎓 Learning Outcomes

Through this project, I practised:

- Java programming and object-oriented concepts.
- JDBC database connectivity.
- SQL queries and relational database design.
- CRUD operations and prepared statements.
- Database constraints and transaction handling.
- Maven project configuration and GitHub version control.

## 👩‍💻 Author

**Pallavi Kompalli**  
B.Tech — Computer Science and Engineering  
VIT-AP University

