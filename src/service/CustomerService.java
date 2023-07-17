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

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        this.customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) {
        return (Customer)this.customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return this.customers.values();
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }

        return instance;
    }
}

