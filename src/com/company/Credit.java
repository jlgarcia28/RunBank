package com.company;

import com.company.Account;

public class Credit extends Account {

    private float credit_max;

    public Credit(int account_Number, float starting_Balance) {
        super(account_Number, starting_Balance);
    }

    public Credit(int account_Number, float starting_Balance, float credit_max) {
        super(account_Number, starting_Balance);
        this.credit_max = credit_max;
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
     * @param total :Quatity to deposit
     */

    @Override
    public boolean deposit_money(float total) {

        if(total < 0){
            print_response("Transaction Failed: Invalid amount: " + total + ", you can't deposit negative amounts");
        }else if(total > (getStarting_Balance() * -1)){
            print_response("Transaction Failed: Can't deposit more than: " + (getStarting_Balance() * -1));
        }
        else{
            setStarting_Balance(total + getStarting_Balance());
            print_response("Transaction Successful, you new balance is: " + getStarting_Balance());
            return true;
        }

        return false;

    }

    /**
     * Method to withdraw money
     * @param total_withdraw: Quantity to take out
     * @return boolean
     */

    @Override
    public boolean withdraw_money(float total_withdraw) {

        if(total_withdraw > 0 && (getStarting_Balance() - total_withdraw) <= (-1 * getCredit_max())){
            setStarting_Balance(getStarting_Balance() - total_withdraw);
            print_response("Transaction Successful, you new balance is: " + getStarting_Balance());
            return true;
        }
        print_response("Transaction Failed: Invalid amount");
        return false;
    }

    public float getCredit_max() {
        return credit_max;
    }

    public void setCredit_max(float credit_max) {
        this.credit_max = credit_max;
    }
}
