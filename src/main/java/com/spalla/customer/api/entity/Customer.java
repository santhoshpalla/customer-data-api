package com.spalla.customer.api.entity;

import com.spalla.customer.api.utils.CustomerDataUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Entity
public class Customer {

    private @Id
    @Column(nullable = false)
    @GeneratedValue Long id;

    private String prefix;

    private String suffix;

    private String firstName;

    private String middleName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String emailId;

    private String phoneNumber;

    public Customer() {}

    public Customer(String prefix, String suffix, String firstName, String middleName, String lastName, String emailId, String phoneNumber) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailId = StringUtils.toRootLowerCase(emailId);
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = StringUtils.toRootLowerCase(emailId);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id,
                StringUtils.toRootLowerCase(this.prefix),
                StringUtils.toRootLowerCase(this.firstName),
                StringUtils.toRootLowerCase(this.middleName),
                StringUtils.toRootLowerCase(this.lastName),
                StringUtils.toRootLowerCase(this.suffix),
                StringUtils.toRootLowerCase(this.emailId),
                StringUtils.toRootLowerCase(this.phoneNumber));
    }

    @Override
    public boolean equals(Object obj) {
        //returns true if both object references are equal
        if(obj == this)
            return true;
        //returns false when object is not an instance of Customer
        //or if object is null
        if(!(obj instanceof Customer)){ return false; }
        //type cast object and compare fields of two objects for equality
        Customer customerObj = (Customer) obj;
        return Objects.equals(this.id, customerObj.getId())
                && StringUtils.equals(this.prefix, customerObj.getPrefix())
                && StringUtils.equals(this.firstName, customerObj.getFirstName())
                && StringUtils.equals(this.middleName, customerObj.getMiddleName())
                && StringUtils.equals(this.lastName, customerObj.getLastName())
                && StringUtils.equals(this.suffix, customerObj.getSuffix())
                && StringUtils.equals(this.emailId, customerObj.getEmailId())
                && StringUtils.equals(this.phoneNumber, customerObj.getPhoneNumber());
    }

    @Override
    public String toString() {
        return String.format("Customer{id= %d, prefix= %s, firstName= %s, middleName= %s, " +
                "lastName= %s, suffix= %s, email= %s, phoneNumber= %s}",
                this.id, this.prefix, CustomerDataUtils.maskNameField(this.firstName), this.middleName,
                CustomerDataUtils.maskNameField(this.lastName), this.suffix,
                CustomerDataUtils.maskEmailField(this.emailId), CustomerDataUtils.maskPhoneField(this.phoneNumber));
    }
}
