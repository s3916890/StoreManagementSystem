package controller;

import controller.Account;

public class Customer extends Account {

    public Customer(){
    }

    public Customer(String userName, String password, String fullName, String phoneNumber) {
        super(userName, password, fullName, phoneNumber);
    }
}