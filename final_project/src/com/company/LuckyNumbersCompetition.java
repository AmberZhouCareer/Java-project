package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class LuckyNumbersCompetition extends Competition implements java.io.Serializable{
    private int[] luckyNumber;

    public LuckyNumbersCompetition(Scanner keyboard, String mode, int id){
        super(keyboard, mode, id);
        super.setType("LuckyNumbersCompetition");
        System.out.println(showNewCompetitionInfo());
    }

    public void generateEntries(Bill currentBill, Scanner keyboard, String mode){
        double money = currentBill.getMoney();
        int number = (int) (money / 50);
        System.out.println("This bill ($" + money +") is eligible for " + number
                + " entries. How many manual entries did the customer fill up?: ");

        int manual;
        while(true) {
            manual = keyboard.nextInt();
            keyboard.nextLine();
            if (manual > number) {
                System.out.println("The number must be in the range from 1 to " + number + ". Please try again.");
            } else {
                break;
            }
        }
        int i = 0;
        for(; i < manual; i++){
            Entry userEntry = new Entry(keyboard, currentBill.getMemberID(), getEntries().size() + 1);
            addEntries(userEntry);
        }

        for(; i < (int)(money / 50); i++){
            AutoNumbersEntry userEntry = new AutoNumbersEntry(currentBill.getMemberID(), mode, getEntriesSize() + 1);
            addEntries(userEntry);
        }
        System.out.println("The following entries have been added:");
        showNewEntries(number);
    }

    public boolean drawWinners(DataProvider myDataProvider){
        ArrayList<Entry> entries = getEntries();
        if(entries.size() == 0){
            System.out.println("The current competition has no entries yet!");
            return false;
        }

        System.out.println(showCompetitionInfo());
        if(this.getIsTestingMode()){
            luckyNumber = AutoNumbersEntry.createNumbers(getId());
        }else{
            luckyNumber = AutoNumbersEntry.createNumbers();
        }

        System.out.println(getLuckyNumberString());

        for (int i = 0; i < entries.size(); i++) {
            int price = decideWinPrize(entries.get(i));
            entries.get(i).setPrize(price);
        }

        for (Entry temp : entries) {
            if (temp.getPrize() >= 50) {
                getAwardEntries().add(temp);
            }
        }

        ArrayList<Entry> removeDuplicationAward = new ArrayList<>();
        for(Entry i : getAwardEntries()){
            boolean memberContain = false;
            for(Entry temp: removeDuplicationAward){
                if(temp.getMemberId().equals(i.getMemberId())){
                    memberContain = true;
                }
            }

            if(memberContain){
                continue;
            }

            boolean isMax = true;
            for(Entry j: getAwardEntries()){
                if(i.getMemberId().equals(j.getMemberId()) && i.getPrize() < j.getPrize()){
                    isMax = false;
                    break;
                }
            }
            if(isMax){
                removeDuplicationAward.add(i);
            }
        }
        setAwardEntries(removeDuplicationAward);
//
//        if (getAwardEntries().size() > 1) {
//            for (int i = 1; i < getAwardEntries().size(); i++) {
//                Entry beforeEntry = getAwardEntries().get(i - 1);
//                Entry currentEntry = getAwardEntries().get(i);
//                if (beforeEntry.getMemberId().equals(currentEntry.getMemberId())) {
//                    if (beforeEntry.getPrize() > currentEntry.getPrize()) {
//                        getAwardEntries().remove(currentEntry);
//                    } else {
//                        getAwardEntries().remove(beforeEntry);
//                    }
//                }
//            }
//        }

        System.out.println("Winning entries:");
        for (Entry temp : getAwardEntries()) {
            System.out.println(temp.showLuckyCompetitionInfo(myDataProvider));
            addSumPrize(temp.getPrize());
        }

        setActive("no");
        return true;
    }

    private int decideWinPrize(Entry oneEntry) {
        int[] entryNumber = oneEntry.getNumbers();
        int sameNumber = 0;
        for (int i : entryNumber) {
            for (int j : luckyNumber) {
                if (i == j) {
                    sameNumber++;
                }
            }
        }
        if (sameNumber == 2) {
            return 50;
        } else if (sameNumber == 3) {
            return 100;
        } else if (sameNumber == 4) {
            return 500;
        } else if (sameNumber == 5) {
            return 1000;
        } else if (sameNumber == 6) {
            return 5000;
        } else if (sameNumber == 7) {
            return 50000;
        } else {
            return 0;
        }
    }

    private String getLuckyNumberString(){
        String luckyNumberString = "Lucky Numbers:";
        for (int i : luckyNumber) {
            luckyNumberString = luckyNumberString + String.format("%3d", i);
        }
        return luckyNumberString + " [Auto]";
    }
}
