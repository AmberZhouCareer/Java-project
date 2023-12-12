package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataProvider {
    ArrayList<Bill> bills = new ArrayList<>();
    ArrayList<Member> members = new ArrayList<>();
    String billFileName;

    private final int ID_LENGTH = 6;

    /**
     * @param memberFile A path to the member file (e.g., members.csv)
     * @param billFile   A path to the bill file (e.g., bills.csv)
     * @throws DataAccessException If a file cannot be opened/read
     * @throws DataFormatException If the format of the the content is incorrect
     */
    public DataProvider(String memberFile, String billFile)
            throws DataAccessException, DataFormatException {
        try {
            BufferedReader inputStream =
                    new BufferedReader(new FileReader(memberFile));
            String oneLine = inputStream.readLine();
            while (oneLine != null) {
                String[] words = oneLine.split(",");
                String memberID = words[0];
                String name = words[1];
                String email = words[2];
                Member oneMember = new Member(memberID, name, email);
                members.add(oneMember);
                oneLine = inputStream.readLine();
            }

            inputStream = new BufferedReader(new FileReader(billFile));
            oneLine = inputStream.readLine();
            while (oneLine != null) {
                String[] words = oneLine.split(",");
                String billID = words[0];
                String memberID = words[1];
                double money = Double.parseDouble(words[2]);
                boolean whetherUsed = Boolean.parseBoolean(words[3]);
                Bill oneBill = new Bill(billID, memberID, money, whetherUsed);
                bills.add(oneBill);
                oneLine = inputStream.readLine();
            }
            billFileName = billFile;

        } catch (FileNotFoundException e) {
            //change later
            System.out.println("FileNotFound");
        } catch (IOException e) {
            //change later
            System.out.println("IOException");
        }
    }

    public String getMemberInfo(String memberID) {
        for (Member i : members) {
            if (i.getMemberID().equals(memberID)) {
                return "Member ID: " + i.getMemberID() + ", Member Name: " + i.getName();
            }
        }
        return "Cannot find Member ID : " + memberID;
    }

    public Bill getBill(Scanner keyboard) {
        System.out.println("Bill ID: ");
        String billID = keyboard.nextLine();

        if (!isValidNumber(billID)) {
            System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
            return getBill(keyboard);
        }

        for (Bill i : bills) {
            if (i.getBillID().equals(billID)) {
                if (i.getMemberID().equals("")) {
                    System.out.println("This bill has no member id. Please try again.");
                    return getBill(keyboard);
                }
                if (i.isWhetherUsed()) {
                    System.out.println("This bill has already been used for a competition." +
                            " Please try again.");
                    return getBill(keyboard);
                }
                return new Bill(i);
            }
        }
        System.out.println("This bill does not exist. Please try again.");
        return getBill(keyboard);
    }

    public boolean isValidNumber(String userId) {
        for (int i = 0; i < userId.length(); i++) {
            if (!Character.isDigit(userId.charAt(i))) {
                return false;
            }
        }
        return userId.length() == ID_LENGTH;
    }

    public void setBillUsed(String billID) {
        for (Bill i : bills) {
            if (i.getBillID().equals(billID)) {
                i.setWhetherUsed(true);
            }
        }
    }

    public void updateBillFile(){
        try {
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter(billFileName));
            for(Bill i : bills){
                writer.write(i.csvString());
            }
            writer.close();
        }catch (FileNotFoundException e) {
            //change later
            System.out.println("FileNotFound");
        } catch (IOException e) {
            //change later
            System.out.println("IOException");
        }
    }
}
