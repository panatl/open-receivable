package org.openreceivable.graphql.input;

import org.openreceivable.enums.CustomerStatus;
import org.openreceivable.enums.CustomerType;

/**
 * Input type for creating a customer
 */
public class CreateCustomerInput {
    private CustomerType customerType;
    private String firstName;
    private String lastName;
    private String businessName;
    private String email;
    private String phone;
    private AddressInput address;
    private String taxId;
    private Integer creditScore;
    private CustomerStatus status;

    // Getters and Setters
    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressInput getAddress() {
        return address;
    }

    public void setAddress(AddressInput address) {
        this.address = address;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
}
