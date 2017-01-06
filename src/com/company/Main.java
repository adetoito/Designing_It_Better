package com.company;

import java.util.*;
import java.io.*;

public class Main {

    public static void main (String [] args) throws IOException {
        Scanner scInt = new Scanner(System.in); // Integer scanner.
        Receipt receipt = new Receipt();
        int role = 0;
        boolean evaluating = true;
        while (evaluating) {
            System.out.println("Are you a regular customer, a member, or an employee?");
            System.out.println("(1) Regular Customer\n(2) Member\n(3) Employee");
            try {
                role = scInt.nextInt();
                evaluating = false;
            } catch (InputMismatchException ime) {
                System.out.println("Invalid ID. Please input a number.");
            }
        }
        Customer person;
        String roleString;
        if (role == 1) {
            person = new Customer(false);
            roleString = "Regular_Customer";
        } else if (role == 2) {
            person = new Member(true);
            roleString = "Member";
        } else {
            /*
            evaluating = true; int membership = 2;
            while (evaluating) {
                System.out.println("Is this employee a member?");
                System.out.println("(1) Yes\n(2) No");
                try {
                    membership = scInt.nextInt();
                    evaluating = false;
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid ID. Please input a number.");
                }
            }
            if (membership == 1) {
                hasMembership = true;
            } else {
                hasMembership = false;
            }
            */
            person = new Employee(true);
            roleString = "Employee";
        }

        boolean looping = true;
        while (looping) {
            int action = 0; evaluating = true;
            while (evaluating) {
                System.out.println("\nWhat would you like to do?\nType in number ID.\n================================");
                System.out.println("(1) View and edit receipt.");
                System.out.println("(2) Finish and pay for transactions.");
                try {
                    action = scInt.nextInt();
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid ID. Please input a number.");
                }
                if (action == 1) {
                    receipt.receiptMenu(person.isMember(), roleString);
                    evaluating = false;
                } else if (action == 2) {
                    evaluating = false; looping = false;
                } else {
                    System.out.println("Invalid ID. Please input a number (1 or 2).");
                }
            }
        }
        receipt.viewReceipt();
        if (receipt.purchasedMembership()) {
            String ID = "000000"; StringBuffer sb = new StringBuffer();
            Random rand = new Random();
            looping = true;
            while (looping) {
                for (int i = 0; i < 6; i++) {
                    int num = rand.nextInt(10);
                    sb.append(String.valueOf(num));
                }
                ID = sb.toString();
                Scanner scanMemberIDS = new Scanner(new File("Members"));
                boolean duplicateID = false;
                while (scanMemberIDS.hasNext()) {
                    String line = scanMemberIDS.nextLine();
                    String [] divisions = line.split("\\s+");
                    if (divisions[0].equals(ID)) {
                        duplicateID = true;
                    }
                }
                if (!duplicateID) {
                    looping = false;
                }
                scanMemberIDS.close();
            }
            System.out.println("\nThank you for purchasing a membership!");
            System.out.println("Your membership ID is: " + ID);
        }
    }
}
