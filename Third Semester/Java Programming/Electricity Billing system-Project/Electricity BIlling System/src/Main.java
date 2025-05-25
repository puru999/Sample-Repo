import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Electricity Billing System =====");
            System.out.println("1. Add New Customer and Generate Bill");
            System.out.println("2. View All Saved Bills");
            System.out.println("3. Search Bill by Customer ID");
            System.out.println("4. Delete a Bill by Customer ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("❌ Invalid input! Please enter a number between 1 and 5.");
                sc.next(); // discard invalid
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            if (choice == 1) {
                System.out.print("Enter Customer Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Customer Address: ");
                String address = sc.nextLine();

                System.out.print("Enter Customer ID: ");
                int customerID = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter Units Consumed: ");
                int units = sc.nextInt();

                Customer customer = new Customer(name, address, customerID, units);
                System.out.println("\nElectricity Bill Details:");
                customer.displayCustomerInfo();

                // ✅ Save to MySQL database
                BillManager.saveBillToDatabase(customer);
            }

            else if (choice == 2) {
                // ✅ View all bills from database
                BillManager.readBillsFromDatabase();
            }

            else if (choice == 3) {
                System.out.print("Enter Customer ID to search: ");
                int searchID = sc.nextInt();
                // ✅ Search bill in database
                BillManager.searchBillByID(searchID);
            }

            else if (choice == 4) {
                System.out.print("Enter Customer ID to delete: ");
                int deleteID = sc.nextInt();
                // ✅ Delete bill from database
                BillManager.deleteBillByID(deleteID);
            }

            else if (choice == 5) {
                System.out.println("Exiting Electricity Billing System. Thank you!");
                break;
            }

            else {
                System.out.println("❌ Invalid choice! Please enter a number between 1 and 5.");
            }
        }

        sc.close();
    }
}
