package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String balance, debits, credit;
    private String transaction, transaction_date;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Date date = new Date();


    public Transaction(String balance, String debits, String credit, String transaction) {
        this.balance = balance;
        this.debits = debits;
        this.credit = credit;
        this.transaction = transaction;
        this.transaction_date = this.formatter.format(this.date);
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDebits() {
        return debits;
    }

    public void setDebits(String debits) {
        this.debits = debits;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }
}
