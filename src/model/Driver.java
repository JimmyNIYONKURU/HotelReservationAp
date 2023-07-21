/**
 * model classes are created in order to model our data objects for the reservation hotel reservation domain
 */
package model;

public class Driver {
    public Driver() {
    }

    public static void main(String[] args) {
        try {
            Customer customer = new Customer("First", "Second", "j@gmail.com");
            System.out.println(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email");
        }
    }
}
