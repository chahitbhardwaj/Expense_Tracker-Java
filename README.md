# Java Expense Tracker

A console-based personal expense tracking application built with Core Java.

## Features

- Add expenses
- View all expenses
- Delete expenses
- Filter expenses by category
- Search by description or category
- Calculate total spending
- View category-wise spending
- Automatically save data to CSV
- Automatically load saved expenses when the application starts
- Input validation and exception handling

## Technologies

- Java 17+
- OOP
- Collections Framework
- Streams API
- File I/O
- LocalDate
- Enum

## Project Structure

```text
JavaExpenseTracker/
├── src/
│   ├── Main.java
│   ├── Expense.java
│   ├── ExpenseManager.java
│   ├── FileManager.java
│   └── Category.java
├── data/
│   └── expenses.csv
└── README.md
```

## How to Run

### IntelliJ IDEA / Eclipse / VS Code

Open the `JavaExpenseTracker` folder as a Java project and run:

`src/Main.java`

Make sure you are using Java 17 or newer.

### Command Line

From the project root:

```bash
javac -d out src/*.java
java -cp out Main
```

The application creates `data/expenses.csv` automatically after the first expense is added.

## Example

```text
1. Add Expense
2. View All Expenses
3. Delete Expense
4. View by Category
5. Search Expenses
6. View Total Spending
7. Spending by Category
8. Exit
```

## Concepts Demonstrated

This project demonstrates classes and objects, encapsulation, constructors, enums, ArrayList/List, HashMap/EnumMap, Java Streams, lambda expressions, file handling, exception handling, and date handling.

## Future Improvements

- JavaFX GUI
- SQLite/MySQL database
- Monthly reports
- Budget limits
- Charts
- Login/user accounts
- Export reports to PDF
