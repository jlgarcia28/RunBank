package com.company;

public abstract class Account {
    private int Account_Number;
    private float Starting_Balance;

    public Account(int account_Number, float starting_Balance) {
        Account_Number = account_Number;
        Starting_Balance = starting_Balance;
    }

    public int getAccount_Number() {
        return Account_Number;
    }

    public void setAccount_Number(int account_Number) {
        Account_Number = account_Number;
    }

    public float getStarting_Balance() {
        return Starting_Balance;
    }

    public void setStarting_Balance(float starting_Balance) {
        Starting_Balance = starting_Balance;
    }

    public abstract float inquire_balance();
    public abstract boolean deposit_money(float total_deposit);
    public abstract boolean withdraw_money(float total_withdraw);

    public void print_response(String message){

        System.out.println(message);

    }
}
