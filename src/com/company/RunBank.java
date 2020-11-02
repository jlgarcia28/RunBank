package com.company;

import java.io.*;
import java.util.*;

public class RunBank {



    public static int findFormatIndex(String header, String[] format){
        for(int i = 0; i < format.length; i++){
            if(header.equals(format[i])){
                return i;
            }
        }
        return -1;
    }
    /**
     * Read a CSV and returns an ArrayList with the contents of the CVS file.
     *
     * @param Path: Read the name of the csv file to open
     * @param customerStatement: Hash-map containing all user bank statement
     * @return ArrayList: Containing all users accounts
     *
     */
    public static ArrayList read_csv(String Path, HashMap<Integer, BankStatement> customerStatement){
        ArrayList<Customer> accounts= new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Path))) {
            String line;
            // Read the heading of a CSV
            line = br.readLine();
            // Making format array
            String[] format = line.split(",");
            // For person Object
            int firstName = findFormatIndex("First Name", format);
            int lastName = findFormatIndex("Last Name", format);
            int dateOfBirth = findFormatIndex("Date of Birth", format);
            int phoneNumber = findFormatIndex("Phone Number", format);
            int idNum = findFormatIndex("Identification Number", format);
            int address = findFormatIndex("Address", format);
            int email = findFormatIndex("Email", format);
            int password = findFormatIndex("Password", format);
            // For Other Account information
            int savingsAccountNumber = findFormatIndex("Savings Account Number", format);
            int savingsStartBalance = findFormatIndex("Savings Starting Balance", format);

            int checkingAccountNumber = findFormatIndex("Checking Account Number", format);
            int checkingStartingBalance = findFormatIndex("Checking Starting Balance", format);

            int creditMax = findFormatIndex("Credit Max", format);
            int creditNumber = findFormatIndex("Credit Account Number", format);
            int creditStartingBalance = findFormatIndex("Credit Starting Balance", format);


            while ((line = br.readLine()) != null) { //read all lines

                //All values located inn the same line are save in the variable values after being separated by a comma
                String[] values = line.split(",");

                String address2 = values[address + 1] + ", " + values[address + 2] + ", " + values[address + 3];

                accounts.add(new Customer(
                        values[firstName + 3],
                        values[lastName],
                        values[dateOfBirth] +"," +values[dateOfBirth + 1],
                        Integer.parseInt(values[idNum]),
                        address2,
                        values[phoneNumber + 1],
                        values[email + 3],
                        values[password + 1],
                        Float.parseFloat(values[checkingStartingBalance + 1]),
                        Integer.parseInt(values[checkingAccountNumber + 1]),
                        Float.parseFloat(values[savingsStartBalance + 1]),
                        Integer.parseInt(values[savingsAccountNumber]),
                        Float.parseFloat(values[creditStartingBalance + 1]),
                        Integer.parseInt(values[creditNumber + 1]),
                        Float.parseFloat(values[creditMax + 3])));
                customerStatement.put(Integer.parseInt(values[idNum]), new BankStatement(accounts.get(accounts.size() - 1), accounts.get(accounts.size() - 1).getSavings().getStarting_Balance()));

            }
            // Catch any errors found in the code above
        } catch (FileNotFoundException e) {
            //Is the address of the error
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(accounts, new Customer.sort_by_id());

        return accounts;
    }


    public static void main(String[] args) {
        try {
            HashMap<Integer, BankStatement> customerStatement = new HashMap<Integer, BankStatement>();
            FileWriter writer = new FileWriter("log.txt", true);
            ArrayList<Customer> accounts = read_csv("Input/Bank_Users.csv", customerStatement);
            Menu menu = new Menu();
            menu.general_menu(accounts, writer, customerStatement);

            for(int i = 0; i < accounts.size(); i++){
                menu.print_cutomer_info(accounts.get(i));
            }

            writer.close();
            menu.write_csv(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
