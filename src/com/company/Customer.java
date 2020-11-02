package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class Customer extends Person implements Comparable<Customer> {
    private Scanner sc = new Scanner(System.in);

    private Checking checking;
    private Savings savings;
    private Credit credit;

    public Customer(String firstName, String lastName, String DOB, int identificationNum,
                    String address, String phoneNum, float Checking_Balance, int Checking_Account,
                    float Savings_Balance, int Savings_Account, float Credit_Balance, int Credit_Account) {

        super(firstName, lastName, DOB, identificationNum, address, phoneNum);
        this.checking = new Checking(Checking_Account,Checking_Balance);
        this.savings = new Savings(Savings_Account,Savings_Balance);
        this.credit = new Credit(Credit_Account,Credit_Balance);
    }

    public Customer(String firstName, String lastName, String DOB, int identificationNum,
                    String address, String phoneNum, float Checking_Balance, int Checking_Account,
                    float Savings_Balance, int Savings_Account, float Credit_Balance, int Credit_Account, float credit_max) {

        super(firstName, lastName, DOB, identificationNum, address, phoneNum);
        this.checking = new Checking(Checking_Account,Checking_Balance);
        this.savings = new Savings(Savings_Account,Savings_Balance);
        this.credit = new Credit(Credit_Account,Credit_Balance, credit_max);
    }

    public Customer(String firstName, String lastName, String DOB, int identificationNum,
                    String address, String phoneNum, float Savings_Balance, int Savings_Account) {

        super(firstName, lastName, DOB, identificationNum, address, phoneNum);
        this.savings = new Savings(Savings_Account,Savings_Balance);
    }



    public Checking getChecking() {
        return checking;
    }

    public void setChecking(Checking checking) {
        this.checking = checking;
    }

    public Savings getSavings() {
        return savings;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    /**
     * Method that writes in a TXT file.
     * @param Message
     * @param writer
     */

    private void log_Transactions(String Message, FileWriter writer){
        try {
            writer.write(Message);
            //writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Method that verifies whether a transaction was successful.
     * @param src
     * @param dest
     * @param value
     * @return boolean
     */
    private boolean transaction(Account src, Account dest, float value){
        if(src.withdraw_money(value)) {
            dest.deposit_money(value);
            System.out.println("Transaction Successfully Completed! New Balance: " + src.getStarting_Balance());
            return true;
        }
        return false;
    }

    /**
     * Method that reads a String
     * @param Message
     * @return String
     */

    private String getOption(String Message){
        System.out.println(Message);
        System.out.print("> ");
        return sc.nextLine();
    }

    /**
     * Method that retrieves an specific type of account
     * @param Message
     * @param ignore
     * @return Account
     */

    private Account getDest(String Message, String ignore){
        String Option = getOption(Message);

        if(Option.equals("Cr") ||  Option.equals("Credit") && !ignore.equals("ICr"))
            return getCredit();
        if(Option.equals("Ch") || Option.equals("Checking")  && !ignore.equals("ICh"))
            return getChecking();
        if(Option.equals("S") || Option.equals("Savings") && !ignore.equals("IS"))
            return getSavings();

        System.out.println("Option" + Option +" not found.");
        return null;
    }

    private Account getDest1(String Option, String ignore){

        if(Option.equals("Cr") ||  Option.equals("Credit") && !ignore.equals("ICr"))
            return getCredit();
        if(Option.equals("Ch") || Option.equals("Checking")  && !ignore.equals("ICh"))
            return getChecking();
        if(Option.equals("S") || Option.equals("Savings") && !ignore.equals("IS"))
            return getSavings();

        System.out.println("Option" + Option +" not found.");
        return null;
    }

    /**
     * Method to transfer money
     * @param total_transfer: Total quantity to transfer
     * @param writer: To write in the txt file
     * @param customerStatement: Hash-map to access the  bank statement of all users
     */

    @Override
    public void transfer_money(float total_transfer, FileWriter writer, HashMap<Integer, BankStatement> customerStatement) {
        String Where;

        if( total_transfer <= 0){
            System.out.println("Transaction Failed: Invalid amount: " + total_transfer + ", you can't deposit negative amounts");
        }else {
            String Option;
            Account dest;

            Option = getOption("Transfer from Checking(Ch), Savings(S) or Credit(Cr)");

            switch (Option){
                case "Checking":
                case "Ch":
                    dest = getDest("Transfer to Savings(S) or Credit(Cr)", "ICh");
                    if(dest != null) {
                        if (transaction(getChecking(), dest, total_transfer)) {
                            Where = dest.getAccount_Number() < 3000 ? "Savings" : "Credit";
                            log_Transactions("\n" + getFirstName() + " " + getLastName() + " transferred from Checking-"
                                    + getChecking().getAccount_Number() + " to " + Where + "-" + dest.getAccount_Number() + " with a new balance in checking of: $" + getChecking().getStarting_Balance(), writer);

                        }
                    }
                    break;
                case "Savings":
                case "S":
                    dest = getDest("Transfer to Checking(Ch) or Credit(Cr)", "IS");
                    if(dest != null) {
                        if (transaction(getSavings(), dest, total_transfer)) {
                            Where = dest.getAccount_Number() < 3000 ? "Checking" : "Credit";
                            customerStatement.get(getIdentificationNum()).setDebits(customerStatement.get(getIdentificationNum()).getDebits() + 1);
                            customerStatement.get(getIdentificationNum()).setTotal_debits(customerStatement.get(getIdentificationNum()).getTotal_debits() + total_transfer);
                            customerStatement.get(getIdentificationNum()).add_transaction("" + getSavings().getStarting_Balance(), "" + total_transfer, "", "TRANSFER TO " + Where);
                            log_Transactions("\n" + getFirstName() + " " + getLastName() + " transferred from Savings-"
                                    + getSavings().getAccount_Number() + " to " + Where + "-" + dest.getAccount_Number() + " with a new balance in savings of: $" + getSavings().getStarting_Balance(), writer);
                        }
                    }
                    break;
                case "Credit":
                case "Cr":
                    dest = getDest("Transfer to Savings(S) or Checking(Ch)", "ICr");
                    if(dest != null) {
                        if (transaction(getCredit(), dest, total_transfer)) {
                            Where = dest.getAccount_Number() < 2000 ? "Checking" : "Savings";
                            log_Transactions("\n" + getFirstName() + " " + getLastName() + " transferred from Credit-"
                                    + getCredit().getAccount_Number() + " to " + Where + "-" + dest.getAccount_Number() + " with a new balance in checking of: $" + getCredit().getStarting_Balance(), writer);
                        }
                    }
                    break;
                default:
                    System.out.println("Option not found");
            }
        }

    }

    /**
     * Method to pay someone
     * @param total_payment : Quantity to pay
     * @param customer : Personal Info
     * @param writer : To write in the txt file
     * @param customerStatement: Hash-map to access the  bank statement of all users
     */
    @Override
    public void pay_someone(float total_payment, Customer customer, FileWriter writer, HashMap<Integer, BankStatement> customerStatement) {
        if( total_payment <= 0){
            System.out.println("Transaction Failed: Invalid amount: " + total_payment + ", you can't deposit negative amounts");
        }else{
            String Option;

            Option = getOption("Pay from Checkings(Ch), Savings(S) or Credit(Cr)");

            switch (Option){
                case "Checking":
                case "Ch":
                    if(transaction(getChecking(), customer.getChecking(), total_payment)){
                        log_Transactions("\n" + getFirstName() + " " + getLastName() + " paid " + customer.getFirstName()+ " " + customer.getLastName() + " from Checking-"
                                + getChecking().getAccount_Number() + " with a new balance in checking of: $" + getChecking().getStarting_Balance() + ". " + customer.getFirstName()+ " " + customer.getLastName() + "'s new balance for Checking-"
                                + customer.getChecking().getAccount_Number() + ": $" + customer.getChecking().getStarting_Balance(), writer);
                    }
                    break;
                case "Savings":
                case "S":
                    if(transaction(getSavings(), customer.getChecking(), total_payment)){
                        customerStatement.get(getIdentificationNum()).setTotal_debits(customerStatement.get(getIdentificationNum()).getTotal_debits() + total_payment);
                        customerStatement.get(getIdentificationNum()).setDebits(customerStatement.get(getIdentificationNum()).getDebits() + 1);
                        customerStatement.get(getIdentificationNum()).add_transaction("" + getSavings().getStarting_Balance(), "" + total_payment, "", "PAY TO " + customer.getFirstName()+ " " + customer.getLastName() + " " + customer.getChecking().getAccount_Number());
                        log_Transactions("\n" + getFirstName() + " " + getLastName() + " paid " + customer.getFirstName()+ " " + customer.getLastName() + " from Savings-"
                                + getSavings().getAccount_Number() + " with a new balance in checking of: $" + getSavings().getStarting_Balance() + ". " + customer.getFirstName()+ " " + customer.getLastName() + "'s new balance for Checking-"
                                + customer.getChecking().getAccount_Number() + ": $" + customer.getChecking().getStarting_Balance(), writer);
                    }
                    break;
                case "Credit":
                case "Cr":
                    if(transaction(getCredit(), customer.getChecking(), total_payment)){
                        log_Transactions("\n" + getFirstName() + " " + getLastName() + " paid " + customer.getFirstName()+ " " + customer.getLastName() + " from Credit-"
                                + getCredit().getAccount_Number() + " with a new balance in checking of: $" + getCredit().getStarting_Balance() +". " + customer.getFirstName()+ " " + customer.getLastName() + "'s new balance for  Checking-"
                                + customer.getChecking().getAccount_Number() + ": $" + customer.getChecking().getStarting_Balance(), writer);
                    }
                    break;
                default:
                    System.out.println("Option not found");
            }
        }
    }

    @Override
    public void transfer_money(float total_transfer, FileWriter writer, String Option, String Option2,HashMap<Integer, BankStatement> customerStatement) {
        String Where;

        if( total_transfer <= 0){
            System.out.println("Transaction Failed: Invalid amount: " + total_transfer + ", you can't deposit negative amounts");
        }else {
            Account dest;


            switch (Option){
                case "Checking":
                case "Ch":
                    dest = getDest1(Option2, "ICh");
                    if(dest != null) {
                        if (transaction(getChecking(), dest, total_transfer)) {
                            Where = dest.getAccount_Number() < 3000 ? "Savings" : "Credit";
                            log_Transactions("\n" + getFirstName() + " " + getLastName() + " transferred from Checking-"
                                    + getChecking().getAccount_Number() + " to " + Where + "-" + dest.getAccount_Number() + " with a new balance in checking of: $" + getChecking().getStarting_Balance(), writer);

                        }
                    }
                    break;
                case "Savings":
                case "S":
                    dest = getDest1(Option2, "IS");
                    if(dest != null) {
                        if (transaction(getSavings(), dest, total_transfer)) {
                            Where = dest.getAccount_Number() < 3000 ? "Checking" : "Credit";
                            customerStatement.get(getIdentificationNum()).setTotal_debits(customerStatement.get(getIdentificationNum()).getTotal_debits() + total_transfer);
                            customerStatement.get(getIdentificationNum()).setDebits(customerStatement.get(getIdentificationNum()).getDebits() + 1);
                            customerStatement.get(getIdentificationNum()).add_transaction("" + getSavings().getStarting_Balance(), "" + total_transfer, "", "TRANSFER TO " + Where);
                            log_Transactions("\n" + getFirstName() + " " + getLastName() + " transferred from Savings-"
                                    + getSavings().getAccount_Number() + " to " + Where + "-" + dest.getAccount_Number() + " with a new balance in savings of: $" + getSavings().getStarting_Balance(), writer);
                        }
                    }
                    break;
                case "Credit":
                case "Cr":
                    dest = getDest1(Option2, "ICr");
                    if(dest != null) {
                        if (transaction(getCredit(), dest, total_transfer)) {
                            Where = dest.getAccount_Number() < 2000 ? "Checking" : "Savings";
                            log_Transactions("\n" + getFirstName() + " " + getLastName() + " transferred from Credit-"
                                    + getCredit().getAccount_Number() + " to " + Where + "-" + dest.getAccount_Number() + " with a new balance in checking of: $" + getCredit().getStarting_Balance(), writer);
                        }
                    }
                    break;
                default:
                    System.out.println("Transaction failed: Account not found!");
            }
        }
    }

    @Override
    public void pay_someone(float total_payment, Customer customer, FileWriter writer, String Option, HashMap<Integer, BankStatement> customerStatement) {
        if( total_payment <= 0){
            System.out.println("Transaction Failed: Invalid amount: " + total_payment + ", you can't deposit negative amounts");
        }else{
            switch (Option){
                case "Checking":
                case "Ch":
                    if(transaction(getChecking(), customer.getChecking(), total_payment)){
                        log_Transactions("\n" + getFirstName() + " " + getLastName() + " paid " + customer.getFirstName()+ " " + customer.getLastName() + " from Checking-"
                                + getChecking().getAccount_Number() + " with a new balance in checking of: $" + getChecking().getStarting_Balance() + ". " + customer.getFirstName()+ " " + customer.getLastName() + "'s new balance for Checking-"
                                + customer.getChecking().getAccount_Number() + ": $" + customer.getChecking().getStarting_Balance(), writer);
                    }
                    break;
                case "Savings":
                case "S":
                    if(transaction(getSavings(), customer.getChecking(), total_payment)){
                        customerStatement.get(getIdentificationNum()).setTotal_debits(customerStatement.get(getIdentificationNum()).getTotal_debits() + total_payment);
                        customerStatement.get(getIdentificationNum()).setDebits(customerStatement.get(getIdentificationNum()).getDebits() + 1);
                        customerStatement.get(getIdentificationNum()).add_transaction("" + getSavings().getStarting_Balance(), "" + total_payment, "", "PAY TO " + customer.getFirstName()+ " " + customer.getLastName() + " " + customer.getChecking().getAccount_Number());
                        log_Transactions("\n" + getFirstName() + " " + getLastName() + " paid " + customer.getFirstName()+ " " + customer.getLastName() + " from Savings-"
                                + getSavings().getAccount_Number() + " with a new balance in savings of: $" + getSavings().getStarting_Balance() + ". " + customer.getFirstName()+ " " + customer.getLastName() + "'s new balance for Checking-"
                                + customer.getChecking().getAccount_Number() + ": $" + customer.getChecking().getStarting_Balance(), writer);
                    }
                    break;
                case "Credit":
                case "Cr":
                    if(transaction(getCredit(), customer.getChecking(), total_payment)){
                        log_Transactions("\n" + getFirstName() + " " + getLastName() + " paid " + customer.getFirstName()+ " " + customer.getLastName() + " from Credit-"
                                + getCredit().getAccount_Number() + " with a new balance in credit of: $" + getCredit().getStarting_Balance() +". " + customer.getFirstName()+ " " + customer.getLastName() + "'s new balance for  Checking-"
                                + customer.getChecking().getAccount_Number() + ": $" + customer.getChecking().getStarting_Balance(), writer);
                    }
                    break;
                default:
                    System.out.println("Transaction failed: Account not found!");
            }
        }
    }

    public static class sort_by_id implements Comparator<Customer>{

        @Override
        public int compare(Customer o, Customer t1) {
            return Integer.valueOf(o.getIdentificationNum()).compareTo(t1.getIdentificationNum());
        }
    }

    @Override
    public int compareTo(Customer customer) {
        return this.getIdentificationNum() > customer.getIdentificationNum() ? 1 : 0;
    }
}
