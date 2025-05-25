public class Customer {
    String name;
    String address;
    int customerID;
    int unitsConsumed;

    public Customer(String name, String address, int customerID, int unitsConsumed) {
        this.name = name;
        this.address = address;
        this.customerID = customerID;
        this.unitsConsumed = unitsConsumed;
    }

    public void displayCustomerInfo() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Units Consumed: " + unitsConsumed);
        System.out.println("Total Bill: $" + calculateBill());
    }

    public double calculateBill() {
        double rate;
        if (unitsConsumed <= 100) {
            rate = 1.5;
        } else if (unitsConsumed <= 300) {
            rate = 2.5;
        } else {
            rate = 3.0;
        }
        return unitsConsumed * rate;
    }
}
