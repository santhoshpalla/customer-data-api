package com.spalla.customer.api.services;

import com.spalla.customer.api.entity.Customer;

import java.util.List;

public interface CustomerDataService {

    List<Customer> getAllCustomers();

    Customer getCustomerByEmailId(String emailId);

    Customer getCustomerById(Long id);

    Customer createCustomer(Customer newCustomer);

    Customer updateCustomer(Customer updatedCustomer, Long id);

    void deleteCustomer(Long id);
}
