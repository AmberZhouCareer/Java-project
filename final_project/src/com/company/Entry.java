package com.company;

import java.util.Arrays;
import java.util.Scanner;

/**
 * this class shows some information of Entry
 */
public class Entry implements java.io.Serializable{
    private int entryId;
    private int[] numbers;

    private static int totalEntry;
    private String memberId;
    private int prize = 0;


    public Entry(){

    }

    public static int getTotalEntry(){
        return totalEntry;
    }

    public static void setTotalEntry(int total){
        totalEntry = total;
    }

    /**
     * Constructor of Entry and initialization of information
     * @param memberId Member ID of the user
     */
    public Entry(String memberId, int id) {
//        totalEntry = totalEntry + 1;
//        this.entryId = totalEntry;
        this.entryId = id;
        this.memberId = memberId;
    }

    public Entry(Entry otherEntry){
        this.entryId = otherEntry.entryId;
        this.memberId = otherEntry.memberId;
        this.prize = otherEntry.prize;
        this.numbers = new int[otherEntry.numbers.length];
        for(int i = 0; i < numbers.length; i++){
            this.numbers[i] = otherEntry.numbers[i];
        }
    }

    /**
     * Constructor of Entry and initialization of information
     * @param keyboard User input
     * @param memberId Member ID of the user
     */
    public Entry(Scanner keyboard, String memberId, int id) {
        this.numbers = createNumber(keyboard);
//        totalEntry = totalEntry + 1;
        this.entryId = id;
        this.memberId = memberId;
    }

    /**
     * Manually enter seven different numbers and determine if they are illegal
     * @param keyboard User input
     * @return Returns a valid input
     */
    public int[] createNumber(Scanner keyboard) {
        System.out.println("Please enter 7 different numbers " +
                "(from the range 1 to 35) separated by whitespace.");
        String userInt = keyboard.nextLine();
        String[] numbers = userInt.split(" ");

        if (!decideValid(numbers, keyboard)){
            return createNumber(keyboard);
        }

        int[] result = new int[7];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i].equals(numbers[j])) {
                    System.out.println("Invalid input! All numbers must be different!");
                    return createNumber(keyboard);
                }
            }
        }
        try {
            for (int i = 0; i < 7; i++) {
                result[i] = Integer.parseInt(numbers[i]);
                if(result[i] > 35 || result[i] < 0){
                    System.out.println("Invalid input! All numbers must be in the range from 1 to 35!");
                    return createNumber(keyboard);
                }
            }
        }catch(NumberFormatException e){
            System.out.println("Invalid input! Numbers are expected. Please try again!");
            return createNumber(keyboard);
        }
        Arrays.sort(result);
        return result;
    }

    /**
     * Determine whether the length of entries is valid
     * @param numbers The number entered by the user
     * @param keyboard User input
     * @return "false" means the length of entries is invalid
     *          "true" means the length of entries is valid
     */
    public boolean decideValid(String[] numbers, Scanner keyboard){
        if (numbers.length < 7) {
            System.out.println("Invalid input! Fewer than 7 numbers are provided. " +
                    "Please try again!");
            return false;
        } else if (numbers.length > 7) {
            System.out.println("Invalid input! More than 7 numbers are provided. " +
                    "Please try again!");
            return false;
        }
        return true;
    }

    /**
     * Display the ID and number of Entry
     * @return String of showing information
     */
    public String showInfo() {
        String result = "Entry ID: " + String.format("%-7d", entryId) + "Numbers:";
        for (int i : numbers) {
            result = result + String.format("%3d", i);
        }
        return result;
    }

    /**
     * Displays all information about Member and Entry
     * @return String of showing all information
     */
    public String showLuckyCompetitionInfo(DataProvider myDataProvider) {
        String result = myDataProvider.getMemberInfo(memberId) + ", Prize: " + String.format("%-5d", prize)
                + "\n--> Entry ID: " + entryId + ", Numbers:";
        for (int i : numbers) {
            result = result + String.format("%3d", i);
        }
        return result;
    }

    public String showRandomCompetitionInfo(DataProvider myDataProvider){
        return myDataProvider.getMemberInfo(memberId) + ", Entry ID: " + entryId + ", Prize: " +
                String.format("%-5d", prize);
    }

    /**
     * Set the information of numbers
     * @param numbers numbers
     */
    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * Transmit the entry id
     * @return return the entry id
     */
    public int getEntryId() {
        return entryId;
    }

    /**
     * Transmit the member Id
     * @return return the member Id
     */
    public String getMemberId() {
        return this.memberId;
    }

    /**
     * Set the price
     * @param prize price
     */
    public void setPrize(int prize) {
        this.prize = prize;
    }

    /**
     * Transmit the price
     * @return return price
     */
    public int getPrize() {
        return prize;
    }

    /**
     * Transmit the number of entries
     * @return return the number of entries
     */
    public int[] getNumbers() {
        return numbers;
    }

    public static void resetTotalEntry(){
        totalEntry = 0;
    }
}