import java.time.LocalDate;

public class Expense {
    private final int id;
    private final double amount;
    private final String description;
    private final Category category;
    private final LocalDate date;

    public Expense(int id, double amount, String description, Category category, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return String.format(
                "ID: %-4d | ₹%9.2f | %-14s | %-25s | %s",
                id, amount, category, description, date
        );
    }

    public String toCsv() {
        return id + "," + amount + "," + escape(description) + "," + category + "," + date;
    }

    private String escape(String value) {
        return value.replace(",", " ");
    }
}
