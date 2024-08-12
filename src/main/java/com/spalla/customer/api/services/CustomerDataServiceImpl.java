package com.spalla.customer.api.services;

import com.spalla.customer.api.entity.Customer;
import com.spalla.customer.api.exceptions.CustomerNotFoundException;
import com.spalla.customer.api.exceptions.CustomerRequestInvalidException;
import com.spalla.customer.api.repository.CustomerRepository;
import com.spalla.customer.api.utils.CustomerDataUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDataServiceImpl implements CustomerDataService{

    private static final Logger log = LoggerFactory.getLogger(CustomerDataServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        if(log.isDebugEnabled())
            log.debug("customer list {}",customerList.stream()
                    .map(Customer::toString).reduce(";", String::concat));
        return customerList;
    }

    @Override
    public Customer getCustomerByEmailId(String emailId) {
        if(StringUtils.isBlank(emailId) || !CustomerDataUtils.isValidEmailId(emailId)){
            log.error("Invalid or blank email id received in getCustomerByEmailId method");
            throw new CustomerRequestInvalidException(emailId);
        }
        return Optional.ofNullable(customerRepository.findByEmailIdIgnoreCase(emailId))
                .orElseThrow(() -> new CustomerNotFoundException(emailId));
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public Customer createCustomer(Customer newCustomer) {
        if(StringUtils.isBlank(newCustomer.getEmailId()) || !CustomerDataUtils.isValidEmailId(newCustomer.getEmailId()))
        {
            log.error("Invalid or blank email id received in create customer method");
            throw new CustomerRequestInvalidException(newCustomer.getEmailId());
        }
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer updateCustomer(Customer updatedCustomer, Long id) {
        if(StringUtils.isBlank(updatedCustomer.getEmailId()) ||
                !CustomerDataUtils.isValidEmailId(updatedCustomer.getEmailId())){
            log.error("Invalid or blank email id received in update customer method");
            throw new CustomerRequestInvalidException(updatedCustomer.getEmailId());
        }
        return customerRepository.findById(id)
                .map(customer -> {
                    log.debug("found customer with id={}", id);
                    customer.setPrefix(updatedCustomer.getPrefix());
                    customer.setFirstName(updatedCustomer.getFirstName());
                    customer.setMiddleName(updatedCustomer.getMiddleName());
                    customer.setLastName(updatedCustomer.getLastName());
                    customer.setSuffix(updatedCustomer.getSuffix());
                    customer.setEmailId(updatedCustomer.getEmailId());
                    customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> {
                    log.debug("customer id={} not found; creating new", id);
                    return customerRepository.save(updatedCustomer);
                });
    }

    @Override
    public void deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        log.debug("customer id={} present: {}", id, customer.isPresent());
        if(customer.isPresent()){
            customerRepository.deleteById(id);
            log.info("deleted customer id={}", id);
        } else {
            log.error("customer id={} not found", id);
            throw new CustomerNotFoundException(id);
        }
    }

}
