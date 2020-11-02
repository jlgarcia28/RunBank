package com.company.Test;

import com.company.Account;
import com.company.Checking;
import com.company.Credit;
import com.company.Savings;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestClassTest {

    @Test
    public void withdraw() {
        Account testAccountChecking = new Checking(1045, 603);
        boolean success = TestClass.withdraw(300, testAccountChecking);

        assertTrue(success);

        Account testAccountChecking2 = new Checking(1046, 703);
        success = TestClass.withdraw(200, testAccountChecking2);

        assertTrue(success);

        Account testAccountSavings = new Savings(2045, 100);
        success = TestClass.withdraw(100, testAccountSavings);

        assertTrue(success);

        Account testAccountSavings2 = new Savings(2046, 10);
        success = TestClass.withdraw(9, testAccountSavings2);

        assertTrue(success);

        Account testAccountCredit = new Credit(3045, -4500);
        success = TestClass.withdraw(-700, testAccountCredit);

        assertTrue(success);
    }

    @Test
    public void inquire() {

        Account testAccountChecking = new Checking(1045, 603);
        float success = TestClass.inquire(testAccountChecking);

        assertEquals(603, success, 0);

        Account testAccountSavings = new Savings(2045, 100);
        success = TestClass.inquire(testAccountSavings);

        assertEquals(100, success, 0);

        Account testAccountChecking2 = new Checking(1046, 8903);
         success = TestClass.inquire(testAccountChecking2);

        assertEquals(8903, success, 0);

        Account testAccountSavings2 = new Savings(2046, 10);
        success = TestClass.inquire(testAccountSavings2);

        assertEquals(10, success, 0);

        Account testAccountCredit = new Credit(3045, -4500);
        success = TestClass.inquire(testAccountCredit);

        assertEquals(-100, success, 0);

    }

    @Test
    public void deposit() {
        Account testAccountChecking = new Checking(1045, 603);
        float success = TestClass.deposit(397, testAccountChecking);

        assertEquals(1000, success, 0);

        Account testAccountSavings = new Savings(2045, 100);
        success = TestClass.deposit(20, testAccountSavings);

        assertEquals(120, success, 0);

        Account testAccountChecking2 = new Checking(1046, 8903);
        success = TestClass.deposit(7, testAccountChecking2);

        assertEquals(8910, success, 0);

        Account testAccountSavings2 = new Savings(2046, 10);
        success = TestClass.deposit(10, testAccountSavings2);

        assertEquals(20, success, 0);

        Account testAccountCredit = new Credit(3045, -4500);
        success = TestClass.deposit(500, testAccountCredit);

        assertEquals(-4000, success, 0);
    }

    @Test
    public void paysomeone() {

    }
}