package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BankStatement {
    private Customer customer;
    private float starting_balance;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Date date = new Date();
    private ArrayList<Transaction> transactions;
    private int debits, credits;

    public float getTotal_debits() {
        return total_debits;
    }

    public void setTotal_debits(float total_debits) {
        this.total_debits = total_debits;
    }

    public float getTotal_credits() {
        return total_credits;
    }

    public void setTotal_credits(float total_credits) {
        this.total_credits = total_credits;
    }

    private float total_debits, total_credits;

    public BankStatement(Customer customer, float starting_balance) {
        this.customer = customer;
        this.starting_balance = starting_balance;
        this.credits = 0;
        this.credits = 0;
        this.total_credits = 0;
        this.total_debits = 0;
        this.transactions = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getStarting_balance() {
        return starting_balance;
    }

    public void setStarting_balance(float starting_balance) {
        this.starting_balance = starting_balance;
    }

    public void add_transaction(String balance, String debits, String credit, String transaction){
        this.transactions.add(new Transaction(balance, debits, credit, transaction));
    }
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDebits() {
        return debits;
    }

    public void setDebits(int debits) {
        this.debits = debits;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
