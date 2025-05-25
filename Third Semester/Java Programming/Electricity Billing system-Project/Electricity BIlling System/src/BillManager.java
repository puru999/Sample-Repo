import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillManager {

    // ✅ Save customer bill to the database
    public static void saveBillToDatabase(Customer customer) {
        String sql = "INSERT INTO customers (name, address, customerID, unitsConsumed, billAmount) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.name);
            stmt.setString(2, customer.address);
            stmt.setInt(3, customer.customerID);
            stmt.setInt(4, customer.unitsConsumed);
            stmt.setDouble(5, customer.calculateBill());

            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Bill saved to database successfully!");
            } else {
                System.out.println("❌ Failed to save bill.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error saving bill: " + e.getMessage());
        }
    }

    // ✅ Read and display all saved bills from database
    public static void readBillsFromDatabase() {
        String sql = "SELECT * FROM customers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n===== Saved Bills =====");

            while (rs.next()) {
                System.out.println(
                        "Customer ID: " + rs.getInt("customerID") +
                                ", Name: " + rs.getString("name") +
                                ", Address: " + rs.getString("address") +
                                ", Units: " + rs.getInt("unitsConsumed") +
                                ", Bill: $" + rs.getDouble("billAmount")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error reading bills: " + e.getMessage());
        }
    }

    // ✅ Search for a bill by customerID
    public static void searchBillByID(int searchID) {
        String sql = "SELECT * FROM customers WHERE customerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, searchID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println(
                        "✅ Bill Found:\n" +
                                "Customer ID: " + rs.getInt("customerID") +
                                ", Name: " + rs.getString("name") +
                                ", Address: " + rs.getString("address") +
                                ", Units: " + rs.getInt("unitsConsumed") +
                                ", Bill: $" + rs.getDouble("billAmount")
                );
            } else {
                System.out.println("❌ No bill found for Customer ID: " + searchID);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error searching bill: " + e.getMessage());
        }
    }

    // ✅ Delete a bill by customerID
    public static void deleteBillByID(int deleteID) {
        String sql = "DELETE FROM customers WHERE customerID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, deleteID);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("✅ Bill deleted for Customer ID: " + deleteID);
            } else {
                System.out.println("❌ No bill found with Customer ID: " + deleteID);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error deleting bill: " + e.getMessage());
        }
    }
}
