import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_PATH = "data/expenses.csv";

    public void saveExpenses(List<Expense> expenses) {
        File file = new File(FILE_PATH);

        try {
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("id,amount,description,category,date");
                writer.newLine();

                for (Expense expense : expenses) {
                    writer.write(expense.toCsv());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not save expenses: " + e.getMessage());
        }
    }

    public List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return expenses;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",", 5);

                if (parts.length != 5) {
                    continue;
                }

                int id = Integer.parseInt(parts[0]);
                double amount = Double.parseDouble(parts[1]);
                String description = parts[2];
                Category category = Category.valueOf(parts[3]);
                LocalDate date = LocalDate.parse(parts[4]);

                expenses.add(new Expense(id, amount, description, category, date));
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Could not load expenses: " + e.getMessage());
        }

        return expenses;
    }
}
