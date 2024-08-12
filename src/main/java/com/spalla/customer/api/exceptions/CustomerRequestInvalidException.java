package com.spalla.customer.api.exceptions;

import com.spalla.customer.api.utils.CustomerDataUtils;

public class CustomerRequestInvalidException extends RuntimeException{
    public CustomerRequestInvalidException(String emailId){
        super("email id is either blank or invalid: "+ CustomerDataUtils.maskEmailField(emailId));
    }
}
