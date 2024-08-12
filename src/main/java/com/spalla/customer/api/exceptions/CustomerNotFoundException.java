package com.spalla.customer.api.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id){
        super("Could not find customer by id: "+id);
    }

    public CustomerNotFoundException(String emailId){
        super("Could not find customer by emailId: "+emailId);
    }
}
