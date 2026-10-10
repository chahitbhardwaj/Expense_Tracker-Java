import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager {
    private final List<Expense> expenses;
    private final FileManager fileManager;
    private int nextId;

    public ExpenseManager() {
        fileManager = new FileManager();
        expenses = fileManager.loadExpenses();
        nextId = expenses.stream()
                .mapToInt(Expense::getId)
                .max()
                .orElse(0) + 1;
    }

    public Expense addExpense(double amount, String description, Category category, LocalDate date) {
        Expense expense = new Expense(nextId++, amount, description, category, date);
        expenses.add(expense);
        fileManager.saveExpenses(expenses);
        return expense;
    }

    public boolean deleteExpense(int id) {
        boolean removed = expenses.removeIf(expense -> expense.getId() == id);

        if (removed) {
            fileManager.saveExpenses(expenses);
        }

        return removed;
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public List<Expense> getExpensesByCategory(Category category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Expense> searchExpenses(String keyword) {
        String search = keyword.toLowerCase();

        return expenses.stream()
                .filter(expense ->
                        expense.getDescription().toLowerCase().contains(search) ||
                        expense.getCategory().name().toLowerCase().contains(search))
                .collect(Collectors.toList());
    }

    public double getTotalSpending() {
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Map<Category, Double> getSpendingByCategory() {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        () -> new EnumMap<>(Category.class),
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    public boolean isEmpty() {
        return expenses.isEmpty();
    }
}
