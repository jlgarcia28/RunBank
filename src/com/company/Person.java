package com.company;

import java.io.FileWriter;
import java.util.HashMap;

public abstract class Person {
    private String firstName;
    private String lastName;
    private String DOB;
    private int identificationNum;
    private String Address;
    private String phoneNum;

    // Empty Constructor
    public Person(){

    }

    // 2nd Constructor is to create an object with all desired attributes


    public Person(String firstName, String lastName, String DOB, int identificationNum, String address, String phoneNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.identificationNum = identificationNum;
        Address = address;
        this.phoneNum = phoneNum;
    }

    public abstract void transfer_money(float total_transfer, FileWriter writer, HashMap<Integer, BankStatement> customerStatement);
    public abstract void pay_someone(float total_payment, Customer customer, FileWriter writer, HashMap<Integer, BankStatement> customerStatement);

    public abstract void transfer_money(float total_transfer, FileWriter writer, String Option, String Option2, HashMap<Integer, BankStatement> customerStatement);
    public abstract void pay_someone(float total_payment, Customer customer, FileWriter writer, String Option, HashMap<Integer, BankStatement> customerStatement);


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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getIdentificationNum() {
        return identificationNum;
    }

    public void setIdentificationNum(int identificationNum) {
        this.identificationNum = identificationNum;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
