package com.company;

import java.util.*;
import java.text.*;

public class Receipt {

    public String [] purchased = new String [3];
    public int [] quantity = new int [3];
    public double [] costs = new double [3];
    public int index;

    public Receipt () {
        for (int i = 0; i < purchased.length; i++) {
            purchased[i] = "-";
        }
        for (int i = 0; i < quantity.length; i++) {
            quantity[i] = 0;
        }
        for (int i = 0; i < costs.length; i++) {
            costs[i] = -23.45;
        }
        index = 0;
    }

    public void receiptMenu (boolean isMember, String role) {
        Scanner scInt = new Scanner(System.in);
        Scanner scInt2 = new Scanner(System.in);

        String [] itemsPurchasable = new String [3]; // IMPORTANT
        double [] costs = new double [3]; // IMPORTANT
        int [] itemIDS = new int [3]; // IMPORTANT
        double retail = 1.00;

        if (!isMember) {
            itemsPurchasable[0] = "Membership"; itemIDS[0] = 1; costs[0] = 6.50;
            itemsPurchasable[1] = "Classes"; itemIDS[1] = 2; costs[1] = 10.00;
            itemsPurchasable[2] = "Personal Trainer"; itemIDS[2] = 3; costs[2] = 25.00;
            retail = 1.00;
            asked = false;
        } else {
            if (role.equals("Member")) {
                itemsPurchasable[0] = "Classes"; itemIDS[0] = 1; costs[0] = 8.00;
                itemsPurchasable[1] = "Aquatics"; itemIDS[1] = 2; costs[1] = 10.00;
                itemsPurchasable[2] = "Personal Trainer"; itemIDS[2] = 3; costs[2] = 20.00;
                retail = 0.80;
                asked = true;
            } else if (role.equals("Employee")) {
                itemsPurchasable[0] = "Classes"; itemIDS[0] = 1; costs[0] = 3.00;
                itemsPurchasable[1] = "Aquatics"; itemIDS[1] = 2; costs[1] = 7.00;
                itemsPurchasable[2] = "Personal Trainer"; itemIDS[2] = 3; costs[2] = 15.00;
                retail = 0.80;
                asked = true;
            }
        }

        if (!asked) {
            role = informAboutMembership(retail, itemsPurchasable, costs);
            if (role.equals("Member")) {
                isMember = true;
            } else {
                isMember = false;
            }
        }

        if (!isMember) {
            itemsPurchasable[0] = "Membership"; itemIDS[0] = 1; costs[0] = 6.50;
            itemsPurchasable[1] = "Classes"; itemIDS[1] = 2; costs[1] = 10.00;
            itemsPurchasable[2] = "Personal Trainer"; itemIDS[2] = 3; costs[2] = 25.00;
            retail = 1.00;
            asked = false;
        } else {
            if (role.equals("Member")) {
                itemsPurchasable[0] = "Classes"; itemIDS[0] = 1; costs[0] = 8.00;
                itemsPurchasable[1] = "Aquatics"; itemIDS[1] = 2; costs[1] = 10.00;
                itemsPurchasable[2] = "Personal Trainer"; itemIDS[2] = 3; costs[2] = 20.00;
                retail = 0.80;
                asked = true;
            } else if (role.equals("Employee")) {
                itemsPurchasable[0] = "Classes"; itemIDS[0] = 1; costs[0] = 3.00;
                itemsPurchasable[1] = "Aquatics"; itemIDS[1] = 2; costs[1] = 7.00;
                itemsPurchasable[2] = "Personal Trainer"; itemIDS[2] = 3; costs[2] = 15.00;
                retail = 0.80;
                asked = true;
            }
        }

        int action; boolean evaluating = true;
        while (evaluating) {
            System.out.println("\nRECEIPT MENU\nType in number ID.\n===========");
            System.out.println("(1) RESET RECEIPT.\n(2) View receipt.\n(3) Add item.\n(4) Finish and pay for items.");
            action = scInt.nextInt();
            if (action == 1) {
                resetReceipt();
                System.out.println("\nReceipt reset!");
            } else if (action == 2) {
                viewReceipt();
            } else if (action == 3) {
                int item = 1;
                boolean evaluating2 = true;
                while (evaluating2) {
                    System.out.println("What item would you like to add to the receipt?");
                    for (int i = 0; i < itemsPurchasable.length; i++) {
                        System.out.println("(" + itemIDS[i] + ") " + itemsPurchasable[i] + " // Cost: " + "$" + costs[i]);
                    }
                    try {
                        item = scInt2.nextInt();
                        evaluating2 = false;
                    } catch (InputMismatchException ime) {
                        System.out.println("Invalid ID. Please input a number.");
                    }
                }
                boolean isMembership = false;
                if (item == 1 && role.equals("Membership")) {
                    isMembership = true;
                }
                addItem(item, retail, itemsPurchasable, costs, isMembership);
            } else if (action == 4) {
                if (index == 0) {
                    System.out.println("There aren't any items in the cart!");
                } else {
                    evaluating = false;
                }
            } else {
                System.out.println("Invalid ID. Please input a number (1-4).");
            }
        }
    }

    public void resetReceipt () {
        for (int i = 0; i < purchased.length; i++) {
            purchased[i] = "-";
        }
        for (int i = 0; i < costs.length; i++) {
            costs[i] = -23.45;
        }
    }

    public void viewReceipt () {
        System.out.println("RECEIPT:");
        DecimalFormat df = new DecimalFormat("###,##0.##");
        for (int i = 0; i < purchased.length; i++) {
            if (purchased[i].equals("-")) {
                break;
            } else {
                System.out.println("- " + purchased[i] + " [Amount: " + quantity[i] + "] // $" + df.format(costs[i]));
            }
        }
        System.out.println("TOTAL: $" + df.format(calculateTotal()));
    }

    public double calculateTotal () {
        double sum = 0.0;
        for (int i = 0; i < costs.length; i++) {
            if (costs[i] == -23.45) {
                break;
            } else {
                sum += costs[i];
            }
        }
        return sum;
    }

    public void addItem (int item, double retail, String [] items, double [] c, boolean isMembership) {
        Scanner scInt = new Scanner(System.in);
        int amount = 0; boolean looping = true;
        if (item == 1 && isMembership) {
            amount = 1;
        } else {
            while (looping) {
                System.out.println("How many of this item do you want to buy?");
                try {
                    amount = scInt.nextInt();
                    if (amount <= 0) {
                        System.out.println("Please input a number more than 0.");
                    } else {
                        looping = false;
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Please input a number more than 0.");
                }
            }
        }
        purchased[index] = items[item - 1];
        quantity[index] = amount;
        if (item == 1 && items[item].equals("Membership")) {
            costs[index] = (c[item - 1] * retail) * amount;
        } else {
            costs[index] = c[item - 1] * amount; //Error here
        }
        System.out.println("Item added.");
        index++;
    }

    public boolean purchasedMembership () {
        boolean member = false;
        for (int i = 0; i < purchased.length; i++) {
            if (purchased[i].equals("Membership")) {
                member = true;
                break;
            }
        }
        return member;
    }

    public boolean wantsMembership;
    public boolean asked;

    public String informAboutMembership (double retail, String [] items, double [] c) {
        Scanner scString = new Scanner(System.in);
        String response; boolean looping = true;
        while (looping) {
            System.out.println("I see you do not have a membership yet!");
            System.out.println("Would you like to purchase a membership? Yes or No?");
            response = scString.nextLine().toLowerCase();
            if (response.equals("yes")) {
                wantsMembership = true; asked = true;
                addItem(1, retail, items, c, true);
                looping = false;
            } else if (response.equals("no")) {
                wantsMembership = false; asked = true;
                looping = false;
            } else {
                System.out.println("Invalid answer.");
            }
        }
        if (wantsMembership) {
            return "Member";
        } else {
            return "Regular_Customer";
        }
    }

}
