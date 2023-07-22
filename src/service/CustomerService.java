package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.Customer;

public class CustomerService {
    private static CustomerService instance;
    private Map<String, Customer> customers = new HashMap();

    private CustomerService() {
    }

    /**
     * Adds a new customer
     *
     * @param email             customer email
     * @param firstName         customer first name
     * @param lastName          customer last name
     */
    public void addCustomer(String email, String firstName, String lastName)
    {
        Customer customer = new Customer(firstName, lastName, email);
        // adding the customer to the map
        this.customers.put(email, customer);
    }

    /**
     * Allow access to customer whose email is passed in parameter
     *
     * @param customerEmail     customer email
     * @return                  customer whose email is parameter
     */
    public Customer getCustomer(String customerEmail) {
        //Accessing the map value by key
        return this.customers.get(customerEmail);
    }

    /**
     * Allow access to all customers
     *
     * @return       return all customers
     */
    public Collection<Customer> getAllCustomers() {
        //accessing the map values
        return this.customers.values();
    }

    /**
     * Ensure the existence of only one instance of the class
     *
     * @return      class instance
     */
    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }

        return instance;
    }
}

