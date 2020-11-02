package com.company;

import com.company.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    /**
     * Method that displays a general menu
     * @param accounts : Array list with all users accounts
     * @param writer : To write in the txt file
     * @param customerStatement: Hash-map to access the  bank statement of all users
     */
    //------------------------------- Menus -----------------------------------


    public void general_menu(ArrayList<Customer> accounts, FileWriter writer, HashMap<Integer, BankStatement> customerStatement){
        String Option = "0";

        while(!Option.equals("4")) {
            UI.generalMenu();

            Option = UI.getOption();

            switch (Option){
                case "1":
                    normal_user_menu(accounts, writer, customerStatement);
                    break;
                case "2":
                    bank_manager_menu(accounts, customerStatement);
                    break;
                case "3":
                    transaction_reader(accounts, writer, customerStatement);
                case "4":
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Option not found!");
            }
        }
    }

    /**
     * Method that display the user's menu.
     *
     * @param accounts : Array list with all users accounts
     * @param writer : To write in the txt file
     * @param customerStatement: Hash-map to access the  bank statement of all users
     */

    public void normal_user_menu(ArrayList<Customer> accounts, FileWriter writer, HashMap<Integer, BankStatement> customerStatement){
        String Option;
        Customer customer, client;
        Account account;
        float total = 0;

        System.out.println("\nWho are you?");
        Option = UI.getOption();
        customer = getCustomer(accounts, Option);

        Option = "0";

        if(customer != null) {
            while (!Option.equals("8")) {
                UI.normalUserMenu();

                Option = UI.getOption();

                switch (Option) {
                    case "1":
                        System.out.println("\nInquire from which account type?");
                        Option = UI.getOption();
                        account = getAccount_Type(Option, customer);
                        if(account != null) {
                            System.out.println("\nYou balance is: " + account.inquire_balance());
                            log_Transactions("\n" + customer.getFirstName() + " " + customer.getLastName() + " made a balance inquiry on "
                                    + Option + "-" + account.getAccount_Number() + " with a new balance of: $" + account.getStarting_Balance(), writer);
                        }
                        break;
                    case "2":
                        System.out.println("\nDeposit to: Checking, Credit or Savings?");
                        Option = UI.getOption();
                        account = getAccount_Type(Option, customer);
                        if(account != null) {
                            System.out.println("Amount to deposit");
                            total = UI.getOptionFloat();
                            if(account.deposit_money(total)) {
                                if(account instanceof Savings) {
                                    customerStatement.get(customer.getIdentificationNum()).setTotal_credits(customerStatement.get(customer.getIdentificationNum()).getTotal_credits() + total);
                                    customerStatement.get(customer.getIdentificationNum()).setCredits(customerStatement.get(customer.getIdentificationNum()).getCredits() + 1);
                                    customerStatement.get(customer.getIdentificationNum()).add_transaction("" + account.getStarting_Balance(), "", "" + total, "DEPOSIT");
                                }
                                log_Transactions("\n" + customer.getFirstName() + " " + customer.getLastName() + " made a deposit on "
                                        + Option + "-" + account.getAccount_Number() + " with a new balance of: $" + account.getStarting_Balance(), writer);
                            }
                        }
                        break;
                    case "3":
                        System.out.println("\nWithdraw money from: Checking, Credit or Savings?");
                        Option = UI.getOption();
                        account = getAccount_Type(Option, customer);
                        if(account != null) {
                            System.out.println("Amount to withdraw");
                            total = UI.getOptionFloat();
                            if(account.withdraw_money(total)) {
                                if(account instanceof Savings) {
                                    customerStatement.get(customer.getIdentificationNum()).setTotal_debits(customerStatement.get(customer.getIdentificationNum()).getTotal_debits() + total);
                                    customerStatement.get(customer.getIdentificationNum()).setDebits(customerStatement.get(customer.getIdentificationNum()).getDebits() + 1);
                                    customerStatement.get(customer.getIdentificationNum()).add_transaction("" + account.getStarting_Balance(), "" + total, "", "WITHDRAW");
                                }
                                log_Transactions("\n" + customer.getFirstName() + " " + customer.getLastName() + " withdrew money from "
                                        + Option + "-" + account.getAccount_Number() + " with a new balance of: $" + account.getStarting_Balance(), writer);
                            }
                        }
                        break;
                    case "4":
                        System.out.println("Amount to transfer?");
                        total = UI.getOptionFloat();
                        customer.transfer_money(total, writer, customerStatement);
                        break;
                    case "5":
                        System.out.println("\nWho are you going to deposit to");
                        Option = UI.getOption();
                        client = getCustomer(accounts, Option);
                        if(client != null && client != customer) {
                            System.out.println("Amount to pay?");
                            total = UI.getOptionFloat();
                            customer.pay_someone(total, client, writer, customerStatement);
                        }
                        if(client == customer)
                            System.out.println("You cannot pay to yourself!");
                        break;
                    case "6":
                        if(customer.getChecking() != null)
                            System.out.println("Already have a Checking account!");
                        else {
                            customer.setChecking(new Checking(customer.getSavings().getAccount_Number() - 1000, 0));
                            System.out.println("Checking account created, your Checking account is: " + customer.getChecking().getAccount_Number());
                        }
                        break;
                    case "7":
                        if(customer.getCredit() != null)
                            System.out.println("Already have a Credit account!");
                        else {
                            customer.setCredit(new Credit(customer.getSavings().getAccount_Number() + 1000, 0));
                            System.out.println("Credit account created, your Credit account is: " + customer.getCredit().getAccount_Number());
                        }
                        break;
                    case "8":
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Option not found!");
                }
            }
        }

    }

    /**
     * Method that display the bank manager's menu.
     *
     * @param accounts : Array list with all users account
     * @param customerStatement: Hash-map to access the  bank statement of all users
     */

    public void bank_manager_menu(ArrayList<Customer> accounts, HashMap<Integer, BankStatement> customerStatement){
        String Option = "0";
        Customer customer;

        while(!Option.equals("6")) {
            UI.bankManagerMenu();

            Option = UI.getOption();

            switch (Option){
                case "1":
                    System.out.println("\nWho’s account would you like to inquire about?");
                    Option = UI.getOption();
                    customer = getCustomer(accounts, Option);
                    if(customer != null){
                        print_cutomer_info(customer);
                    }
                    break;
                case "2":
                    System.out.println("\nWhat account type?");
                    Option = UI.getOption();
                    customer = getCustomer_Account(accounts, Option);
                    if(customer != null){
                        print_cutomer_info(customer);
                    }
                    break;
                case "3":
                    System.out.println("-------- All Accounts --------");
                    for(int i = 0; i < accounts.size(); i++){
                        print_cutomer_info(accounts.get(i));
                        System.out.println("---------------------------");
                    }
                    break;
                case "4":
                    Customer newCustomer = create_user(accounts);
                    accounts.add(newCustomer);
                    customerStatement.put(newCustomer.getIdentificationNum(), new BankStatement(newCustomer, newCustomer.getSavings().getStarting_Balance()));
                    System.out.println("User created!");
                    break;
                case "5":
                    System.out.println("\nWho’s account would you like to generate a bank statement?");
                    Option = UI.getOption();
                    customer = getCustomer(accounts, Option);
                    if(customer != null){
                        write_bank_statement(customer.getIdentificationNum(), customerStatement);
                    }else
                        System.out.println("The user " + Option + " doesn't exists!");
                case "6":
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Option not found!");

            }
        }

    }

    public void transaction_reader(ArrayList<Customer> accounts, FileWriter writer, HashMap<Integer, BankStatement> customerStatement){
        Customer customer, client;
        String full_name, full_name_client;
        Account account;

        System.out.println("Please introduce the name of the file: ");
        String Path = UI.getOption();

        try (BufferedReader br = new BufferedReader(new FileReader(Path))) {
            String line;


            boolean first = true; //First is to identify first line
            while ((line = br.readLine()) != null) { //read all lines

                if(first){
                    first = false;
                }

                else{

                    //All values located inn the same line are save in the variable values after being separated by a comma
                    String[] values = line.split(",");

                    System.out.println("Action: " + values[3]);
                    switch (values[3]){
                        case "inquires":
                            // Get full name
                            full_name = values[0] + " " + values[1];

                            // Get Customer
                            customer = getCustomer(accounts, full_name);

                            account = getAccount_Type(values[2], customer);
                            if(account != null && customer != null) {
                                System.out.println("\nYou balance is: " + account.inquire_balance());
                                log_Transactions("\n" + customer.getFirstName() + " " + customer.getLastName() + " made a balance inquiry on "
                                        + values[2] + "-" + account.getAccount_Number() + " with a new balance of: $" + account.getStarting_Balance(), writer);
                            }
                            if(customer == null)
                                System.out.println("The customer " + full_name + "doesn't exists!");
                            break;
                        case "withdraws":
                            // Get full name
                            full_name = values[0] + " " + values[1];

                            // Get Customer
                            customer = getCustomer(accounts, full_name);

                            account = getAccount_Type(values[2], customer);
                            if(account != null && customer != null) {
                                if(account.withdraw_money(Float.parseFloat(values[7]))) {
                                    if (account instanceof Savings) {
                                        customerStatement.get(customer.getIdentificationNum()).setTotal_debits(customerStatement.get(customer.getIdentificationNum()).getTotal_debits() + Float.parseFloat(values[7]));
                                        customerStatement.get(customer.getIdentificationNum()).setDebits(customerStatement.get(customer.getIdentificationNum()).getDebits() + 1);
                                        customerStatement.get(customer.getIdentificationNum()).add_transaction("" + account.getStarting_Balance(), values[7], "", "WITHDRAW");
                                    }
                                    log_Transactions("\n" + customer.getFirstName() + " " + customer.getLastName() + " withdrew money from "
                                            + values[2] + "-" + account.getAccount_Number() + " with a new balance of: $" + account.getStarting_Balance(), writer);
                                }
                            }
                            if(customer == null)
                                System.out.println("The customer " + full_name + "doesn't exists!");
                            break;
                        case "deposits":
                            // Get full name
                            full_name = values[4] + " " + values[5];

                            // Get Customer
                            customer = getCustomer(accounts, full_name);

                            account = getAccount_Type(values[6], customer);
                            if(account != null && customer != null) {
                                if (account.deposit_money(Float.parseFloat(values[7]))) {
                                    if(account instanceof Savings) {
                                        customerStatement.get(customer.getIdentificationNum()).setTotal_credits(customerStatement.get(customer.getIdentificationNum()).getTotal_credits() + Float.parseFloat(values[7]));
                                        customerStatement.get(customer.getIdentificationNum()).setCredits(customerStatement.get(customer.getIdentificationNum()).getCredits() + 1);
                                        customerStatement.get(customer.getIdentificationNum()).add_transaction("" + account.getStarting_Balance(), "", values[7], "DEPOSIT");
                                    }
                                    log_Transactions("\n" + customer.getFirstName() + " " + customer.getLastName() + " made a deposit on "
                                            + values[6] + "-" + account.getAccount_Number() + " with a new balance of: $" + account.getStarting_Balance(), writer);

                                }
                            }
                            if(customer == null)
                                System.out.println("The customer " + full_name + "doesn't exists!");
                            break;
                        case "pays":
                            // Get full name
                            full_name = values[0] + " " + values[1];

                            // Get Customer
                            customer = getCustomer(accounts, full_name);

                            // Get full name client
                            full_name_client = values[4] + " " + values[5];

                            // Get Client
                            client = getCustomer(accounts, full_name_client);

                            if(client != null && client != customer && customer != null) {
                                customer.pay_someone(Float.parseFloat(values[7]), client, writer, values[2], customerStatement);
                            }
                            if(client == customer)
                                System.out.println("You cannot pay to yourself!");
                            if(customer == null)
                                System.out.println("The customer " + full_name + "doesn't exists!");
                            if(client == null)
                                System.out.println("The customer " + full_name_client + "doesn't exists!");
                            break;
                        case "transfers":
                            // Get full name
                            full_name = values[0] + " " + values[1];

                            // Get Customer
                            customer = getCustomer(accounts, full_name);

                            // Get full name client
                            full_name_client = values[4] + " " + values[5];

                            // Get Client
                            client = getCustomer(accounts, full_name_client);

                            if(client == customer)
                                customer.transfer_money(Float.parseFloat(values[7]), writer, values[2], values[6], customerStatement);
                            else
                                System.out.println("You cannot transfer to someone else!");
                            break;



                    }

                }

            }
            // Catch any errors found in the code above
        } catch (FileNotFoundException e) {
            //Is the address of the error
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------------------------------- Input -----------------------------------



    //------------------------------- Users/Accounts Information -----------------------------------

    /**
     * Method to get a specific type of account
     *
     * @param Option : Name of account type
     * @param customer: Personal info
     * @return Account: Return an account type
     */

    public Account getAccount_Type(String Option, Customer customer){
        if(Option.equals("Credit") && customer.getCredit() != null)
            return customer.getCredit();
        if(Option.equals("Checking") && customer.getChecking() != null)
            return customer.getChecking();
        if(Option.equals("Savings"))
            return customer.getSavings();

        System.out.println("Account type: " + Option + " doesn't exists or you don't have one!");
        return null;
    }

    public Customer create_user(ArrayList<Customer> accounts){
        String user_data[] = new String[5];
        System.out.println("------- Create User -------");
        while(true) {
            System.out.println("First Name: ");
            user_data[0] = UI.getOption();
            if (user_data[0].isEmpty()) {
                System.out.println("First name can't be empty");
                continue;
            }
            System.out.println("Last Name: ");
            user_data[1] = UI.getOption();
            if (user_data[1].isEmpty()) {
                System.out.println("Last name can't be empty");
                continue;
            }
            System.out.println("Date of Birth: ");
            user_data[2] = UI.getOption();
            if (user_data[2].isEmpty()) {
                System.out.println("Date of Bird can't be empty");
                continue;
            }
            System.out.println("Address: ");
            user_data[3] = UI.getOption();
            if (user_data[3].isEmpty()) {
                System.out.println("Address can't be empty");
                continue;
            }
            System.out.println("Phone Number: ");
            user_data[4] = UI.getOption();
            if (user_data[4].isEmpty()) {
                System.out.println("Phone number can't be empty");
                continue;
            }
            break;
        }

        return new Customer(user_data[0], user_data[1], user_data[2], accounts.get(accounts.size() -1 ).getIdentificationNum() + 1,user_data[3], user_data[4], 0, accounts.get(accounts.size() -1 ).getSavings().getAccount_Number() + 1);
    }

    /**
     * Method that retrieves an account with a specific Account number
     *
     * @param accounts: ArrayList that contains all users accounts
     * @param Option: Type account
     * @return Customer: Personal Info
     */
    public Customer getCustomer_Account(ArrayList<Customer> accounts, String Option){
        System.out.println("What is the account number?");
        int type = UI.getOptionInt();

        for(int i = 0; i < accounts.size(); i++) {
            if (Option.equals("Credit") && type == accounts.get(i).getCredit().getAccount_Number() || Option.equals("Checking") && type == accounts.get(i).getChecking().getAccount_Number() || Option.equals("Savings") && type == accounts.get(i).getSavings().getAccount_Number())
                return accounts.get(i);
        }

        return null;
    }

    /**
     * Method that print all the Customer information.
     * @param customer: Personal Info
     */
    public void print_cutomer_info(Customer customer){
        System.out.println("\nFull name: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("Date of birth: " + customer.getDOB());
        System.out.println("Identification number: " + customer.getIdentificationNum());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Phone number: " + customer.getPhoneNum());
        if(customer.getChecking() != null) {
            System.out.println("Checking Account: " + customer.getChecking().getAccount_Number());
            System.out.println("Checking Balance: " + customer.getChecking().inquire_balance());
        }
        System.out.println("Savings Account: " + customer.getSavings().getAccount_Number());
        System.out.println("Savings Balance: " + customer.getSavings().inquire_balance());
        if(customer.getCredit() != null) {
            System.out.println("Credit Account: " + customer.getCredit().getAccount_Number());
            System.out.println("Credit Balance: " + customer.getCredit().inquire_balance() + "\n");
        }
    }

    /**
     * Retrieves a user with a specific name
     * @param accounts: ArrayList that contains all users accounts
     * @param name: name of account username
     * @return Customer: Personal Info
     */

    public Customer getCustomer(ArrayList<Customer> accounts, String name){
        for(int i = 0; i < accounts.size(); i++){
            if(name.equals(accounts.get(i).getFirstName() + " " + accounts.get(i).getLastName()))
                return accounts.get(i);
        }
        return null;
    }

    /**
     * Method used to write in a TXT file.
     *
     * @param Message: Message generated in the txt file
     * @param writer: To write in the txt file
     */

    public void log_Transactions(String Message, FileWriter writer){
        try {
            writer.write(Message);
            //writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //------------------------------- CSV -----------------------------------

    /**
     * Method that writes a CSV File.
     *
     * @param accounts: ArrayList that contains all users accounts
     */

    public void write_csv(ArrayList<Customer> accounts){

        try (PrintWriter writer = new PrintWriter(new File("Updated_Book.csv"))) {

            StringBuilder sb = new StringBuilder();
            sb.append("IdentificationNumber");
            sb.append(',');
            sb.append("Savings Account Number");
            sb.append(',');
            sb.append("Last Name");
            sb.append(',');
            sb.append("Date of Birth");
            sb.append(',');
            sb.append("Checking Account Number");
            sb.append(',');
            sb.append("Credit Account Number");
            sb.append(',');
            sb.append("Phone Number");
            sb.append(',');
            sb.append("Checking Starting Balance");
            sb.append(',');
            sb.append("Savings Starting Balance");
            sb.append(',');
            sb.append("Credit Max");
            sb.append(',');
            sb.append("Credit Starting Balance");
            sb.append(',');
            sb.append("Address");
            sb.append(',');
            sb.append("First Name");








            sb.append('\n');

            for(int i = 0; i < accounts.size(); i++) {
                sb.append(accounts.get(i).getFirstName());
                sb.append(',');
                sb.append(accounts.get(i).getLastName());
                sb.append(',');
                sb.append(accounts.get(i).getDOB());
                sb.append(',');
                sb.append(accounts.get(i).getIdentificationNum());
                sb.append(',');
                sb.append(accounts.get(i).getAddress());
                sb.append(',');
                sb.append(accounts.get(i).getPhoneNum());
                sb.append(',');
                if(accounts.get(i).getChecking() != null)
                    sb.append(accounts.get(i).getChecking().getAccount_Number());
                else
                    sb.append(" ");
                sb.append(',');
                sb.append(accounts.get(i).getSavings().getAccount_Number());
                sb.append(',');
                if(accounts.get(i).getCredit() != null)
                    sb.append(accounts.get(i).getCredit().getAccount_Number());
                else
                    sb.append(" ");
                sb.append(',');
                if(accounts.get(i).getChecking() != null)
                    sb.append(accounts.get(i).getChecking().getStarting_Balance());
                else
                    sb.append(" ");
                sb.append(',');

                sb.append(accounts.get(i).getSavings().getStarting_Balance());
                sb.append(',');
                if(accounts.get(i).getCredit() != null)
                    sb.append(accounts.get(i).getCredit().getStarting_Balance());
                else
                    sb.append(" ");
                sb.append('\n');
            }

            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Generate a bank statement
     * @param id: User identification number
     * @param customerStatement : Hash-map containing all user bank statement
     */

    public void write_bank_statement(int id, HashMap<Integer, BankStatement> customerStatement) {
        String name = "User_" + id + ".csv";
        if (customerStatement.get(id).getTransactions().size() > 0) {
            try (PrintWriter writer = new PrintWriter(new File(name))) {

                StringBuilder sb = new StringBuilder();
                sb.append("UTEP BANK");
                sb.append(',');
                sb.append(',');
                sb.append(',');
                sb.append("Statement Ending " + customerStatement.get(id).getDate());
                sb.append('\n');
                sb.append('\n');
                sb.append("RETURN SERVICE REQUESTED");
                sb.append(',');
                sb.append(',');
                sb.append(',');
                sb.append("Managing Your Accounts");
                sb.append('\n');
                sb.append(',');
                sb.append(',');
                sb.append(',');
                sb.append("Primary Branch,");
                sb.append("UTEP");
                sb.append('\n');
                sb.append(',');
                sb.append(',');
                sb.append(',');
                sb.append("Phone Number,");
                sb.append("(000) 123-456-789");
                sb.append('\n');
                sb.append(customerStatement.get(id).getCustomer().getFirstName() + " " + customerStatement.get(id).getCustomer().getLastName());
                sb.append(',');
                sb.append(',');
                sb.append(',');
                sb.append("Web Page,");
                sb.append("https://www.utep.edu/");
                sb.append('\n');
                sb.append(customerStatement.get(id).getCustomer().getAddress());
                sb.append(',');
                sb.append(',');
                sb.append(',');
                sb.append("Bank Address,");
                sb.append("2522 Sun Bowl Dr. El Paso. TX 79902");
                sb.append('\n');
                sb.append('\n');
                sb.append("Summary of Accounts,");
                sb.append('\n');
                sb.append("Account Type");
                sb.append(',');
                sb.append(',');
                sb.append("Account Number,");
                sb.append("Ending Balance");
                sb.append('\n');
                sb.append("Savings");
                sb.append(',');
                sb.append(',');
                sb.append(customerStatement.get(id).getCustomer().getSavings().getAccount_Number() + ",");
                sb.append(customerStatement.get(id).getCustomer().getSavings().getStarting_Balance());
                sb.append('\n');
                sb.append('\n');
                sb.append("PRIMARY SAVINGS,");
                sb.append('\n');
                sb.append('\n');
                sb.append("Account Summary");
                sb.append('\n');
                sb.append("Date,");
                sb.append("Description,");
                sb.append(',');
                sb.append("Amount");
                sb.append('\n');
                sb.append(customerStatement.get(id).getDate() + ",");
                sb.append("Beginning Balance,");
                sb.append(',');
                sb.append(customerStatement.get(id).getStarting_balance());
                sb.append('\n');
                sb.append(',');
                sb.append(customerStatement.get(id).getCredits() + " Credit(s) This Period");
                sb.append(',');
                sb.append(customerStatement.get(id).getTotal_credits());
                sb.append('\n');
                sb.append(',');
                sb.append(customerStatement.get(id).getDebits() + " Debits(s) This Period");
                sb.append(',');
                sb.append(customerStatement.get(id).getTotal_debits());
                sb.append('\n');
                sb.append(customerStatement.get(id).getTransactions().get(customerStatement.get(id).getTransactions().size() - 1).getTransaction_date() + ",");
                sb.append("Ending Balance");
                sb.append(',');
                sb.append(customerStatement.get(id).getCustomer().getSavings().getStarting_Balance());
                sb.append('\n');
                sb.append('\n');
                sb.append("Account Activity");
                sb.append('\n');
                sb.append("Post Date,");
                sb.append("Description,");
                sb.append("Debits,");
                sb.append("Credits,");
                sb.append(',');
                sb.append("Balance");
                sb.append('\n');
                sb.append(customerStatement.get(id).getDate() + ",");
                sb.append("Beginning Balance,");
                sb.append(',');
                sb.append(',');
                sb.append(',');
                sb.append(customerStatement.get(id).getStarting_balance());
                sb.append('\n');

                for (int i = 0; i < customerStatement.get(id).getTransactions().size(); i++) {
                    sb.append(customerStatement.get(id).getTransactions().get(i).getTransaction_date() + ",");
                    sb.append(customerStatement.get(id).getTransactions().get(i).getTransaction() + ",");
                    sb.append(customerStatement.get(id).getTransactions().get(i).getDebits() + ",");
                    sb.append(customerStatement.get(id).getTransactions().get(i).getCredit() + ",");
                    sb.append(',');
                    sb.append(customerStatement.get(id).getTransactions().get(i).getBalance());
                    sb.append('\n');
                }
                sb.append(customerStatement.get(id).getTransactions().get(customerStatement.get(id).getTransactions().size() - 1).getTransaction_date() + ",");
                sb.append("Ending Balance,");
                sb.append(',');
                sb.append(',');
                sb.append(',');
                sb.append(customerStatement.get(id).getCustomer().getSavings().getStarting_Balance());

                writer.write(sb.toString());

                System.out.println("Bank Statement Successfully created as " + name);

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("You don't have enough information to generate a Bank Statement");
        }
    }
}
