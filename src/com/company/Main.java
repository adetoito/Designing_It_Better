package com.company;

import java.util.*;
import java.io.*;

public class Main {

    public static void main (String [] args) throws IOException {
        Scanner scInt = new Scanner(System.in);
        Receipt receipt = new Receipt();
        int role = 0;
        boolean evaluating = true; boolean looping = true;

        Customer person;
        String roleString;

        while (looping) {
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
            Scanner scString = new Scanner(System.in);
            while (evaluating) {
                if (role == 1) {
                    person = new Customer(false);
                    roleString = "Regular_Customer";
                    evaluating = false; looping = false;
                    tieLooseEnds(person, roleString, receipt);
                } else if (role == 2) {
                    roleString = "Member";
                    // Verify that this person is a Member.
                    boolean verifying = true;
                    while (verifying) {
                        System.out.println("Input your member ID.");
                        String presumedID = scString.nextLine();
                        if (presumedID.length() == 6 && !presumedID.equals("-1")) {
                            Scanner scanMemberIDS = new Scanner(new File("Members.txt"));
                            while (scanMemberIDS.hasNext()) {
                                String line = scanMemberIDS.nextLine();
                                String [] divisions = line.split("\\s+");
                                if (divisions[0].equals(presumedID)) {
                                    verifying = false;
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Member IDs are 6 digits long.");
                        }
                    }
                    person = new Member(true);
                    evaluating = false; looping = false;
                    tieLooseEnds(person, roleString, receipt);
                } else {
                    roleString = "Employee";
                    // Verify that this person is an Employee.
                    boolean verifying = true;
                    while (verifying) {
                        System.out.println("Input your SSN.");
                        String presumedID = scString.nextLine();
                        if (presumedID.length() == 11) {
                            Scanner scanEmployeeSSNs = new Scanner(new File("Employees.txt"));
                            while (scanEmployeeSSNs.hasNext()) {
                                String line = scanEmployeeSSNs.nextLine();
                                String[] divisions = line.split("\\s+");
                                if (divisions[0].equals(presumedID)) {
                                    verifying = false;
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Member IDs are 6 digits long.");
                        }
                    }
                    person = new Employee(true);
                    evaluating = false;
                    tieLooseEnds(person, roleString, receipt);
                }
            }
        }
    }

    public static void tieLooseEnds (Customer person, String roleString, Receipt receipt) throws IOException {
        receipt.receiptMenu(person.isMember(), roleString);
        receipt.viewReceipt();
        boolean looping = true;
        System.out.println("Thank you for shopping!");
        if (receipt.purchasedMembership()) {
            String ID = "000000"; StringBuffer sb = new StringBuffer();
            Random rand = new Random();
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
                FileWriter fw = new FileWriter("Members.txt", true);
                PrintWriter pw = new PrintWriter(fw);
                pw.write("\n" + ID + " 0 0 0");
                pw.close(); fw.close(); scanMemberIDS.close();
            }
            System.out.println("\nThank you for purchasing a membership!");
            System.out.println("Your membership ID is: " + ID);
        }
    }
}