package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomPickCompetition extends Competition implements java.io.Serializable{
    private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};

    private final int MAX_WINNING_ENTRIES = 3;

    public RandomPickCompetition(Scanner keyboard, String mode, int id){
        super(keyboard, mode, id);
        super.setType("RandomPickCompetition");
        System.out.println(showNewCompetitionInfo());
    }

    public void generateEntries(Bill currentBill, Scanner keyboard, String mode){
        double money = currentBill.getMoney();
        System.out.println("This bill ($" + money +") is eligible for " + (int) (money / 50)
                + " entries.");
        System.out.println("The following entries have been automatically generated:");
        for(int i = 0; i < (int) (money / 50); i++){
            AutoNumbersEntry userEntry = new AutoNumbersEntry(currentBill.getMemberID(), mode, getEntries().size() + 1);
            addEntries(userEntry);
            System.out.println("Entry ID: " + String.format("%-6d", userEntry.getEntryId()));
        }
    }

    public boolean drawWinners(DataProvider myDataProvider) {
        ArrayList<Entry> entries = getEntries();
        if(entries.size() == 0){
            System.out.println("The current competition has no entries yet!");
            return false;
        }

        Random randomGenerator = null;
        if (this.getIsTestingMode()) {
            randomGenerator = new Random(this.getId());
        } else {
            randomGenerator = new Random();
        }

        ArrayList<Integer> usedIndex = new ArrayList<>();

        int winningEntryCount = 0;
        while (winningEntryCount < MAX_WINNING_ENTRIES) {
            int winningEntryIndex;

            while(true){
                winningEntryIndex = randomGenerator.nextInt(entries.size());
                if(!usedIndex.contains(winningEntryIndex)){
                    usedIndex.add(winningEntryIndex);
                    break;
                }
            }

            Entry winningEntry = entries.get(winningEntryIndex);

            if (winningEntry.getPrize() == 0) {
                int currentPrize = prizes[winningEntryCount];
                winningEntry.setPrize(currentPrize);
                getAwardEntries().add(winningEntry);
                winningEntryCount++;
            }
        }

        for(int i = getAwardEntries().size() - 1; i > 0; i--){
            for(int j = i - 1; j >= 0; j--){
                if(getAwardEntries().get(i).getMemberId().equals(getAwardEntries().get(j).getMemberId())){
                    getAwardEntries().remove(i);
                    break;
                }
            }
        }

        for(int i = 0; i < getAwardEntries().size() - 1; i++){
            for(int j = i + 1; j < getAwardEntries().size(); j++){
                if(getAwardEntries().get(i).getEntryId() > getAwardEntries().get(j).getEntryId()){
                    Entry temp = new Entry(getAwardEntries().get(i));
                    getAwardEntries().set(i, getAwardEntries().get(j));
                    getAwardEntries().set(j, temp);
                }
            }
        }
//
//        for(Entry i : getAwardEntries()){
//            for(Entry j: getAwardEntries()){
//                if(i.getEntryId() != j.getEntryId() &&
//                        i.getMemberId().equals(j.getMemberId())
//                        && i.getPrize() > j.getPrize()){
//                    getAwardEntries().remove(j);
//                }
//            }
//        }

        System.out.println(showCompetitionInfo());
        System.out.println("Winning entries:");
        for(Entry i : getAwardEntries()){
            System.out.println(i.showRandomCompetitionInfo(myDataProvider));
            addSumPrize(i.getPrize());
        }

        setActive("no");
        return true;
    }
}
