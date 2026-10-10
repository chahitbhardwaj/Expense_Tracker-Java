import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ExpenseManager manager = new ExpenseManager();

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("          JAVA EXPENSE TRACKER");
        System.out.println("==========================================");

        boolean running = true;

        while (running) {

            printMenu();

            int choice = readInt("Choose an option: ");

            switch (choice) {

                case 1:
                    addExpense();
                    break;

                case 2:
                    viewExpenses(
                            manager.getAllExpenses(),
                            "ALL EXPENSES"
                    );
                    break;

                case 3:
                    deleteExpense();
                    break;

                case 4:
                    viewByCategory();
                    break;

                case 5:
                    searchExpenses();
                    break;

                case 6:
                    showTotal();
                    break;

                case 7:
                    showCategorySummary();
                    break;

                case 8:
                    System.out.println(
                            "\nThank you for using Java Expense Tracker!"
                    );
                    running = false;
                    break;

                default:
                    System.out.println(
                            "\nInvalid option. Please choose 1-8."
                    );
            }
        }

        scanner.close();
    }

    private static void printMenu() {

        System.out.println("\n--------------- MENU ----------------");
        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. Delete Expense");
        System.out.println("4. View by Category");
        System.out.println("5. Search Expenses");
        System.out.println("6. View Total Spending");
        System.out.println("7. Spending by Category");
        System.out.println("8. Exit");
        System.out.println("-------------------------------------");
    }

    private static void addExpense() {

        System.out.println("\n--- Add Expense ---");

        double amount;

        do {
            amount = readDouble("Amount (₹): ");

            if (amount <= 0) {
                System.out.println(
                        "Amount must be greater than 0."
                );
            }

        } while (amount <= 0);

        String description;

        do {
            description = readString("Description: ");

            if (description.isBlank()) {
                System.out.println(
                        "Description cannot be empty."
                );
            }

        } while (description.isBlank());

        Category category = chooseCategory();

        LocalDate date = readDate();

        Expense expense = manager.addExpense(
                amount,
                description,
                category,
                date
        );

        System.out.println("\nExpense added successfully!");
        System.out.println(expense);
    }

    private static void deleteExpense() {

        System.out.println("\n--- Delete Expense ---");

        if (manager.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        viewExpenses(
                manager.getAllExpenses(),
                "EXPENSES"
        );

        int id = readInt(
                "\nEnter expense ID to delete: "
        );

        if (manager.deleteExpense(id)) {

            System.out.println(
                    "Expense deleted successfully."
            );

        } else {

            System.out.println(
                    "No expense found with ID " + id + "."
            );
        }
    }

    private static void viewByCategory() {

        System.out.println("\n--- View by Category ---");

        Category category = chooseCategory();

        List<Expense> results =
                manager.getExpensesByCategory(category);

        viewExpenses(
                results,
                category + " EXPENSES"
        );
    }

    private static void searchExpenses() {

        System.out.println("\n--- Search Expenses ---");

        String keyword = readString(
                "Enter keyword: "
        );

        List<Expense> results =
                manager.searchExpenses(keyword);

        viewExpenses(
                results,
                "SEARCH RESULTS"
        );
    }

    private static void showTotal() {

        System.out.printf(
                "\nTotal spending: ₹%.2f%n",
                manager.getTotalSpending()
        );
    }

    private static void showCategorySummary() {

        System.out.println(
                "\n--- SPENDING BY CATEGORY ---"
        );

        Map<Category, Double> summary =
                manager.getSpendingByCategory();

        if (summary.isEmpty()) {

            System.out.println(
                    "No expenses available."
            );

            return;
        }

        summary.entrySet()
                .stream()
                .sorted(
                        Map.Entry
                                .<Category, Double>
                                comparingByValue()
                                .reversed()
                )
                .forEach(entry ->
                        System.out.printf(
                                "%-18s ₹%.2f%n",
                                entry.getKey(),
                                entry.getValue()
                        )
                );
    }

    private static void viewExpenses(
            List<Expense> expenses,
            String title
    ) {

        System.out.println(
                "\n--- " + title + " ---"
        );

        if (expenses.isEmpty()) {

            System.out.println(
                    "No expenses found."
            );

            return;
        }

        System.out.println(
                "ID   |     AMOUNT | CATEGORY       | DESCRIPTION                | DATE"
        );

        System.out.println(
                "-----+------------+----------------+---------------------------+------------"
        );

        for (Expense expense : expenses) {
            System.out.println(expense);
        }

        System.out.printf(
                "%nShowing %d expense(s).%n",
                expenses.size()
        );
    }

    private static Category chooseCategory() {

        Category[] categories =
                Category.values();

        System.out.println("\nCategories:");

        for (int i = 0; i < categories.length; i++) {

            System.out.println(
                    (i + 1) + ". " + categories[i]
            );
        }

        while (true) {

            int choice =
                    readInt("Choose category: ");

            if (
                    choice >= 1 &&
                    choice <= categories.length
            ) {

                return categories[choice - 1];
            }

            System.out.println(
                    "Invalid category. Choose 1-"
                            + categories.length
                            + "."
            );
        }
    }

    private static LocalDate readDate() {

        while (true) {

            String input = readString(
                    "Date (YYYY-MM-DD) or press Enter for today: "
            );

            if (input.isBlank()) {
                return LocalDate.now();
            }

            try {

                return LocalDate.parse(input);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date. Example: 2026-09-10"
                );
            }
        }
    }

    private static String readString(
            String prompt
    ) {

        System.out.print(prompt);

        return scanner.nextLine().trim();
    }

    private static int readInt(
            String prompt
    ) {

        while (true) {

            try {

                return Integer.parseInt(
                        readString(prompt)
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid whole number."
                );
            }
        }
    }

    private static double readDouble(
            String prompt
    ) {

        while (true) {

            try {

                return Double.parseDouble(
                        readString(prompt)
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}