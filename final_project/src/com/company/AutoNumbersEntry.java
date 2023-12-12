package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * this class shows some information of AutoEntry
 */
public class AutoNumbersEntry extends Entry implements java.io.Serializable{

    public AutoNumbersEntry(String memberId, String mode, int id){
        super(memberId, id);
        if (mode.equals("T")){
        super.setNumbers(createNumbers(super.getEntryId() - 1));
    }else {
            super.setNumbers(createNumbers());
        }
    }

    /**
     * Generates a string of pseudo-random numbers
     * @param seed Use seed to control the generation of random numbers
     * @return Return random number
     */
    public static int[] createNumbers (int seed) {
        ArrayList<Integer> validList = new ArrayList<Integer>();
        int[] tempNumbers = new int[7];
        for (int i = 1; i <= 35; i++) {
            validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < 7; i++) {
            tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }

    /**
     * Generates a string of truly random numbers
     * @return Return random number
     */
    public static int[] createNumbers () {
        ArrayList<Integer> validList = new ArrayList<Integer>();
	int[] tempNumbers = new int[7];
        for (int i = 1; i <= 35; i++) {
    	    validList.add(i);
        }
        Collections.shuffle(validList, new Random());
        for (int i = 0; i < 7; i++) {
    	    tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }

    /**
     * Display the ID and number of Entry
     * @return String of showing information
     */
    public String showInfo(){
        return super.showInfo() + " [Auto]";
    }

    /**
     * Displays all information about Member and Entry
     * @return String of showing all information
     */
    public String showLuckyCompetitionInfo(DataProvider myDataProvider){
        return super.showLuckyCompetitionInfo(myDataProvider) + " [Auto]";
    }
}
