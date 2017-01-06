package com.company;

import java.util.*;
import java.io.*;

public class Main {

    public static void main (String [] args) throws IOException {
        Scanner scInt = new Scanner(System.in); // Integer scanner.
        Receipt receipt = new Receipt();
        int role = 0;
        boolean evaluating = true; boolean action = true;

        Customer person;
        String roleString;

        while (action) {
            while (evaluating) {
                System.out.println("Are you a regular customer, a member, or an employee?");
                System.out.println("(1) Regular Customer\n(2) Member\n(3) Employee");
                try {
                    role = scInt.nextInt();
                    if (role == 1 || role == 2 || role == 3) {
                        evaluating = false;
                    } else {
                        System.out.println("Invalid ID. Please input a number (1-3).");
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid ID. Please input a number.");
                }
            }
            evaluating = true;
            while (evaluating) {
                if (role == 1) {
                    person = new Customer(false);
                    roleString = "Regular_Customer";
                    evaluating = false; action = false;
                } else if (role == 2) {
                    person = new Member(true);
                    roleString = "Member";
                    // Verify that this person is a Member.
                    boolean verifying = true;
                    while (verifying) {

                    }
                    evaluating = false; action = false;
                } else {
                    person = new Employee(true);
                    roleString = "Employee";
                    // Verify that this person is an Employee.
                    boolean verifying = true;
                    while (verifying) {
                        // ...
                    }
                    evaluating = false; action = false;
                }
            }
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
                Scanner scanMemberIDS = new Scanner(new File("Members.txt"));
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
                FileWriter fw = new FileWriter("Members.txt");
                PrintWriter pw = new PrintWriter(fw);
                pw.write(ID + " 0 0 0");
                pw.close(); fw.close(); scanMemberIDS.close();
            }
            System.out.println("\nThank you for purchasing a membership!");
            System.out.println("Your membership ID is: " + ID);
        }
    }
}
