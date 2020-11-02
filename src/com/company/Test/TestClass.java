package com.company.Test;

import com.company.Account;

public class TestClass {

    public static boolean withdraw(float total, Account account){
        return account.withdraw_money(total);
    }

    public static float inquire(Account account){
        return account.inquire_balance();
    }

    public static float deposit(float total, Account account){
        account.deposit_money(total);

        return account.getStarting_Balance();
    }



}
