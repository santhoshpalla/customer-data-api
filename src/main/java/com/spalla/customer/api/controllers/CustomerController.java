package com.spalla.customer.api.controllers;

import com.spalla.customer.api.entity.Customer;
import com.spalla.customer.api.services.CustomerDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerDataService customerDataService;

    @GetMapping
    public List<Customer> listAllCustomers(){
        log.debug("get all customers request");
        return customerDataService.getAllCustomers();
    }

    @GetMapping("/customer/details")
    public Customer getCustomerByEmailId(@RequestParam("emailId") final String emailId){
        log.debug("get customer details by emailId: {}", emailId);
        return customerDataService.getCustomerByEmailId(emailId);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable final Long id){
        log.debug("get customer details by id: {}", id);
        return customerDataService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody final Customer newCustomer){
        log.debug("request to create new customer {}", newCustomer.toString());
        return customerDataService.createCustomer(newCustomer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@RequestBody final Customer updatedCustomer, @PathVariable final Long id){
        log.debug("request to update customer id: {} with details: {}", id, updatedCustomer.toString());
        return customerDataService.updateCustomer(updatedCustomer, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable final Long id){
        log.debug("request to delete customer id: {}", id);
        customerDataService.deleteCustomer(id);
    }
}
