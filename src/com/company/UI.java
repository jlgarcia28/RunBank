package com.company;

import java.util.Scanner;

public class UI {
    public static void generalMenu() {
        System.out.println("Select an option from the list\n");
        System.out.println("1) Normal User");
        System.out.println("2) Bank Manager");
        System.out.println("3) Transaction Reader");
        System.out.println("4) Exit\n");
    }

    public static void normalUserMenu(){
        System.out.println("\nSelect an option from the list\n");
        System.out.println("1) Inquire balance");
        System.out.println("2) Deposit money");
        System.out.println("3) Withdraw money");
        System.out.println("4) Transfer money");
        System.out.println("5) Pay someone");
        System.out.println("6) Create Checking Account");
        System.out.println("7) Create Credit Account");
        System.out.println("8) Exit\n");
    }
    public static void bankManagerMenu(){
        System.out.println("\nSelect an option from the list\n");
        System.out.println("1) Inquire account by name");
        System.out.println("2) Inquire account by type/number");
        System.out.println("3) Inquire all accounts");
        System.out.println("4) Create User");
        System.out.println("5) Generate Bank Statement");
        System.out.println("6) Exit\n");


    }
    /**
     * Method that reads a String
     * @return : the next string that the user inputs
     */
    public static String getOption(){
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");
        return sc.nextLine();
    }
    /**
     * Method that reads an Integer
     * @return : the next int that the user inputs
     */
    public static int getOptionInt(){
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");
        return sc.nextInt();
    }
    /**
     * Method that reads a Float
     * @return : the next float that the user inputs
     */
    public static float getOptionFloat(){
        Scanner sc = new Scanner(System.in);
        System.out.print("> ");
        return sc.nextFloat();
    }

}

