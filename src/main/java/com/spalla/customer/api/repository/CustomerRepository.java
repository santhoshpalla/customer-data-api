package com.spalla.customer.api.repository;

import com.spalla.customer.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmailIdIgnoreCase(String emailId);
}
