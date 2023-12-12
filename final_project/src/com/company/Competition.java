package com.company;

import javax.xml.crypto.Data;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * this class shows some information of competition
 */
public abstract class Competition implements java.io.Serializable{
    private String name; //competition name
    private int id; //competition identifier
    private String type = null;
    private String mode;

    private static int competitionId;
    private final ArrayList<Entry> entries = new ArrayList<>();
    private ArrayList<Entry> awardEntries = new ArrayList<>();

    private String active = "yes";
    private int sumPrize = 0;

    public Competition() {
    }

    /**
     * create a new competition
     * @param keyboard user input
     */

    public Competition(Scanner keyboard, String mode, int id) {
        Entry.resetTotalEntry();
        this.mode = mode;
        System.out.println("Competition name: ");
        this.name = keyboard.nextLine();
        this.id = id;
//        competitionId = competitionId + 1;
//        this.id = competitionId;
    }

    public String showNewCompetitionInfo(){
        return "A new competition has been created!\n" + showCompetitionInfo();
    }

    public String showCompetitionInfo(){
        return "Competition ID: " + id+ ", Competition Name:" +
                " " + name + ", Type: " + type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    /**
     * add each entry into the arraylist of entries
     * @param aEntry each entry
     */
    public void addEntries(Entry aEntry) {
        entries.add(aEntry);
    }

    /**
     * Displays information about the newly entered Entry
     * @param newEntries new entries
     */
    public void showNewEntries(int newEntries) {
        for (int i = entries.size() - newEntries; i < entries.size(); i++) {
            System.out.println(entries.get(i).showInfo());
        }
    }

    public ArrayList<Entry> getEntries(){
        return this.entries;
    }

//    /**
//     * find winner
//     * @param luckyNumber lucky number
//     */
//    public void findWinners(int[] luckyNumber) {
//        for (int i = 0; i < entries.size(); i++) {
//            entries.get(i).setPrice(decideWinPrize(luckyNumber, entries.get(i).getNumbers()));
//        }
//
//        for (Entry temp : entries) {
//            if (temp.getPrice() >= 50) {
//                awardEntries.add(temp);
//            }
//        }
//
//        if (awardEntries.size() > 1) {
//            for (int i = 1; i < awardEntries.size(); i++) {
//                Entry beforeEntry = awardEntries.get(i - 1);
//                Entry currentEntry = awardEntries.get(i);
//                if (beforeEntry.getMemberId().equals(currentEntry.getMemberId())) {
//                    if (beforeEntry.getPrice() > currentEntry.getPrice()) {
//                        awardEntries.remove(currentEntry);
//                    } else {
//                        awardEntries.remove(beforeEntry);
//                    }
//                }
//            }
//        }
//
//        System.out.println("Winning entries:");
//        for (Entry temp : awardEntries) {
//            System.out.println(temp.showAllInfo());
//            sumPrize = sumPrize + temp.getPrice();
//        }
//
//        this.active = "no";
//    }

    /**
     * show summary report
     */
    public void report() {
        System.out.println();
        System.out.println("Competition ID: " + this.id + ", name: "
                + this.name + ", active: " + this.active);
        System.out.println("Number of entries: " + this.entries.size());
        if (this.active.equals("no")) {
            System.out.println("Number of winning entries: " + this.awardEntries.size());
            System.out.println("Total awarded prizes: " + this.sumPrize);
        }
    }

    /**
     * the size of entries
     * @return return the size of entries
     */
    public int getEntriesSize() {
        return entries.size();
    }

    /**
     * Transmit id
     * @return return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Transmit the name
     * @return return the name
     */
    public String getName() {
        return this.name;
    }

//    /**
//     * Determine the amount of money awarded for each Entry
//     * @param luckyNumber luck number
//     * @param entryNumber entry number
//     * @return The winning amount
//     */
//    public int decideWinPrize(int[] luckyNumber, int[] entryNumber) {
//        int sameNumber = 0;
//        for (int i : entryNumber) {
//            for (int j : luckyNumber) {
//                if (i == j) {
//                    sameNumber = sameNumber + 1;
//                }
//            }
//        }
//        if (sameNumber == 2) {
//            return 50;
//        } else if (sameNumber == 3) {
//            return 100;
//        } else if (sameNumber == 4) {
//            return 500;
//        } else if (sameNumber == 5) {
//            return 1000;
//        } else if (sameNumber == 6) {
//            return 5000;
//        } else if (sameNumber == 7) {
//            return 50000;
//        } else {
//            return 0;
//        }
//    }

    /**
     * Determine whether the competition is activated
     * @return return yes if the competition is activated
     */
    public boolean whetherActive() {
        return this.active.equals("yes");
    }

    public abstract void generateEntries(Bill currentBill, Scanner keyboard, String mode);

    public boolean getIsTestingMode(){
        return mode.equals("T");
    }

    public ArrayList<Entry> getAwardEntries(){
        return awardEntries;
    }

    public void setAwardEntries(ArrayList<Entry> awardEntries){
        this.awardEntries = awardEntries;
    }

    public int getSumPrize(){
        return sumPrize;
    }

    public void addSumPrize(int prize){
        sumPrize += prize;
    }

    public void setActive(String isActive){
        active = isActive;
    }

    public abstract boolean drawWinners(DataProvider myDataProvider);

}
