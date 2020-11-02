package com.company;

import com.company.Account;

public class Savings extends Account {

    public Savings(int account_Number, float starting_Balance) {
        super(account_Number, starting_Balance);
    }

    /**
     * Method that retrieves the Balance
     * @return float
     */
    @Override
    public float inquire_balance() {

        return getStarting_Balance();
    }

    /**
     * Method to deposit money
     * @param total: Quantity to deposit
     */

    @Override
    public boolean deposit_money(float total) {
        if(total <= 0){
            print_response("Transaction Failed: Invalid amount: " + total + ", you can't deposit negative amounts");
            return false;
        }else{
            setStarting_Balance(total + getStarting_Balance());
            print_response("Transaction Successful, you new balance is: " + getStarting_Balance());
            return true;
        }

    }
    /**
     * Method to withdraw money
     * @param total_withdraw: Quantity to take out
     * @return boolean: Verify the previous transaction
     */

    @Override
    public boolean withdraw_money(float total_withdraw) {
        if(getStarting_Balance() >= total_withdraw && total_withdraw > 0){
            setStarting_Balance(getStarting_Balance() - total_withdraw);
            print_response("Transaction Successful, you new balance is: " + getStarting_Balance());
            return true;
        }else if(total_withdraw <= 0){
            print_response("Transaction Failed: Invalid amount: " + total_withdraw + ", you can't deposit negative amounts");
        }else{
            print_response("Transaction Failed: Not enough money.");
        }

        return false;
    }
}
