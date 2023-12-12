package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * this class shows some information of simple competitions
 */
public class SimpleCompetitions {
    private boolean whetherActive;
    private DataProvider myDataProvider;

    private Bill currentBill = null;
    private ArrayList<Competition> userCompetition = new ArrayList<>();
    private ArrayList<Bill> allBills = new ArrayList<>();
    private String mode;
    private int completed;
    private int active;

    /**
     * Main program that uses the main SimpleCompetitions class
     * @param args main program arguments
     */
    public static void main(String[] args) {
        SimpleCompetitions sc = new SimpleCompetitions();
        sc.startCompetitions();
    }

    private void startCompetitions(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");
        askLoad(keyboard);
        openCsvFile(keyboard);
        askOptions(keyboard);
        keyboard.close();
    }

    private void askLoad(Scanner keyboard){
        System.out.println("Load competitions from file? (Y/N)?");
        String loadAns = keyboard.nextLine().toUpperCase();
        switch (loadAns) {
            case "Y" ->
                openBinaryFile(keyboard);//待验证
            case "N" ->
                askMode(keyboard);
            default -> {
                System.out.println("Unsupported option. Please try again!");
                askLoad(keyboard);
            }
        }
    }

    public void openBinaryFile(Scanner keyboard){
        System.out.println("File name:");
        String fileName = keyboard.nextLine();
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(fileName));
            userCompetition = (ArrayList<Competition>) reader.readObject();
            if(accessCurrentCompetition().getIsTestingMode()){
                this.mode = "T";
            }else{
                this.mode = "N";
            }
            whetherActive = accessCurrentCompetition().whetherActive();
//            Entry.setTotalEntry(accessCurrentCompetition().getEntriesSize());
            reader.close();
        }catch(FileNotFoundException e){
            //change later
            System.out.println("Cannot found File");
        }catch(IOException e){
            //change later
            System.out.println("IO Exception");
        }catch(Exception e){
            //change later
            System.out.println("Exception");
        }
//        Scanner inputStream = null;
//        try{
//            inputStream = new Scanner(new FileInputStream("demo.dat"));
//        }catch (FileNotFoundException e){
//            /////////////////////////////////////////////////////不在要干啥
//        }
//        inputStream.close();
    }

    public void askMode(Scanner keyboard){
        System.out.println("Which mode would you like to run? (Type T for Testing," +
                " and N for Normal mode):");
        String modeAns = keyboard.nextLine().toUpperCase();
        switch (modeAns) {
            case "T" ->
                this.mode = "T";
            case "N" ->
                this.mode = "N";
            default -> {
                System.out.println("Invalid mode! Please choose again.");
                askMode(keyboard);
            }
        }
    }

    public void openCsvFile(Scanner keyboard){
        System.out.println("Member file: ");
        String memberFile = keyboard.nextLine();
        System.out.println("Bill file: ");
        String billFile = keyboard.nextLine();
        try{
            myDataProvider = new DataProvider(memberFile, billFile);
        }catch (DataAccessException e){

        }catch (DataFormatException e){

        }
    }

    private void askOptions(Scanner keyboard) {
        menu();
        String optionString = keyboard.nextLine();
        try{
            int optionInt = Integer.parseInt(optionString);
            switch (optionInt) {
                case 1:
                    if (whetherActive) {
                        System.out.println("There is an active competition. SimpleCompetitions" +
                                " does not support concurrent competitions!");
                    } else {
                        userCompetition.add(addNewCompetition(keyboard));
                        whetherActive = true;
                    }
                    break;
                case 2:
                    if (whetherActive) {
                        billInfo(keyboard);
                    } else {
                        noActiveOutput();
                    }
                    break;
                case 3:
                    if (whetherActive) {
                        if(accessCurrentCompetition().drawWinners(myDataProvider)){
                            whetherActive = false;
                        }
                    } else {
                        noActiveOutput();
                    }
                    break;
                case 4:
                    report();
                    break;
                case 5:
                    askSave(keyboard);
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unsupported option. Please try again!");
                    break;
            }
        }catch (NumberFormatException e){
            System.out.println("A number is expected. Please try again.");
            askOptions(keyboard);
        }
        askOptions(keyboard);
    }

    /**
     * create a new competition
     * @param keyboard Enter Competition Name
     * @return return a new competition
     */
    public Competition addNewCompetition(Scanner keyboard) {
        String competitionType = askCompetitionType(keyboard);
        if(competitionType.equals("LuckyNumbersCompetition")){
            return new LuckyNumbersCompetition(keyboard, mode, userCompetition.size() + 1);
        }else{
            return new RandomPickCompetition(keyboard, mode, userCompetition.size() + 1);
        }
    }

    private String askCompetitionType(Scanner keyboard){
        System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
        String typeAns = keyboard.nextLine();
        switch (typeAns) {
            case "L" -> {
                return "LuckyNumbersCompetition";
            }
            case "R" -> {
                return "RandomPickCompetition";
            }
            default -> {
                System.out.println("Invalid competition type! Please choose again.");
                return askCompetitionType(keyboard);
            }
        }
    }

    /**
     * show the information of bill
     * @param keyboard user input
     */
    private void billInfo(Scanner keyboard) {
        currentBill = myDataProvider.getBill(keyboard);

        double money = currentBill.getMoney();
        if (money < 50) {
            System.out.println("This bill is not eligible for an entry. " +
                    "The total amount is smaller than $50.0");
        }else{
            accessCurrentCompetition().generateEntries(currentBill, keyboard, getMode());
            myDataProvider.setBillUsed(currentBill.getBillID());
        }

        askMoreBills(keyboard);
//
//        else if(accessCurrentCompetition().getType().equals("LuckyNumbersCompetition")){
//            System.out.println("This bill ($" + money +") is eligible for " + (int) (money / 50)
//                    + " entries. How many manual entries did the customer fill up?: ");
//
//            int manual = keyboard.nextInt();
//            keyboard.nextLine();
//
//            int i = 0;
//            for(; i < manual; i++){
//                Entry userEntry = new Entry(keyboard, currentBill.getMemberId());
//                accessCurrentCompetition().addEntries(userEntry);
//            }
//
//            for(; i < (int)(money / 50); i++){
//                AutoNumbersEntry userEntry = new AutoNumbersEntry(currentBill.getMemberId(), getMode());
//                accessCurrentCompetition().addEntries(userEntry);
//            }
//
//            System.out.println("The following entries have been added:");
//            accessCurrentCompetition().showNewEntries((int) (money / 50));
//        }else if(accessCurrentCompetition().getType().equals("RandomPickCompetition")){
//            System.out.println("This bill ($" + money +") is eligible for " + (int) (money / 50)
//                    + " entries.");
//            System.out.println("The following entries have been automatically generated:");
//            for(int i = 0; i < (int) (money / 50); i++){
//                AutoNumbersEntry userEntry = new AutoNumbersEntry(currentBill.getMemberId(), getType());
//                accessCurrentCompetition().addEntries(userEntry);
//                System.out.println("Entry ID: " + userEntry.getEntryId());
//            }
//        }
//        currentBill.setWhetherUsed(true);
//
//        while (true) {
//            System.out.println("Add more entries (Y/N)?");
//            String userChoice = keyboard.nextLine();
//            if (userChoice.equals("Y")) {
//                billInfo(keyboard);
//                break;
//            } else if (userChoice.equals("N")) {
//                askOptions(keyboard);
//                break;
//            } else {
//                System.out.println("Unsupported option. Please try again!");
//            }
//        }
    }
    public void askMoreBills(Scanner keyboard){
        System.out.println("Add more entries (Y/N)?");
        String userChoice = keyboard.nextLine();
        if (userChoice.equals("Y")) {
            billInfo(keyboard);
        } else if (userChoice.equals("N")) {
            askOptions(keyboard);
        } else {
            System.out.println("Unsupported option. Please try again!");
            askMoreBills(keyboard);
        }
    }
//
//    /**
//     * Ask for ID information
//     * @param keyboard User input
//     * @return return a valid id
//     */
//    private String askBillID(Scanner keyboard) {
//        System.out.println("Bill ID: ");
//        String billId = keyboard.nextLine();
//
//        if (!isNumber(billId)) {
//            System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
//            return askBillID(keyboard);
//        }
//
//        boolean whetherIdExist = false;
//        for(Bill temp : allBills){
//            if(temp.getBillId().equals(billId)){
//                whetherIdExist = true;
//                currentBill = temp;
//                break;
//            }
//        }
//        if(!whetherIdExist){
//            System.out.println("This bill does not exist. Please try again.");
//            askBillID(keyboard);
//        }
//
//        if(currentBill.getMemberId().equals("")) {
//            System.out.println("The bill has no member id. Please try again.");
//            askBillID(keyboard);
//        }
//
//        if(currentBill.isWhetherUsed()){
//            System.out.println("This bill has already been used for a competition. Pleas try again");
//            askBillID(keyboard);
//        }
//        return billId;
//    }

    /**
     * Displays the number of active competitions and displays the number of inactive competitions.
     */
    public void report() {
        if (userCompetition.size() == 0) {
            System.out.println("No competition has been created yet!");
        } else {
            if (accessCurrentCompetition().whetherActive()) {
                active = 1;
                completed = userCompetition.size() - 1;
            } else {
                completed = userCompetition.size();
                active = 0;
            }
            showSummary();
            for (Competition i : userCompetition) {
                i.report();
            }
        }
    }

    /**
     * show the information of summary report
     */
    public void showSummary() {
        System.out.println("----SUMMARY REPORT----");
        System.out.println("+Number of completed competitions: " + completed);
        System.out.println("+Number of active competitions: " + active);
    }

    /**
     * show information of menu
     */
    public void menu() {
        System.out.println("Please select an option. Type 5 to exit.");
        System.out.println("1. Create a new competition");
        System.out.println("2. Add new entries");
        System.out.println("3. Draw winners");
        System.out.println("4. Get a summary report");
        System.out.println("5. Exit");
    }

//    /**
//     * choose mode
//     * @param keyboard User input
//     */
//    public void startMenu(Scanner keyboard) {
//        menu();
//        System.out.println("Which mode would you like to run? (Type T for Testing," +
//                " and N for Normal mode):");
//        String ans = keyboard.nextLine().toUpperCase();
//        switch (ans) {
//            case "T" -> type = "T";
//            case "N" -> type = "N";
//            default -> {
//                System.out.println("Invalid mode! Please choose again.");
//                startMenu(keyboard);
//            }
//        }
//        askAgain(keyboard);
//    }

//    /**
//     * The specific output corresponding to the menu
//     * @param keyboard User input
//     */


//    /**
//     * When the user makes an error, ask the user to retype
//     * @param keyboard User input
//     */
//    public void askAgain(Scanner keyboard){
//        menu();
//        opt = keyboard.nextLine();
//        options(keyboard);
//    }

    /**
     * When the match is not active, output the corresponding content
     */
    public void noActiveOutput(){
        System.out.println("There is no active competition. Please create one!");
    }
//
//
//    /**
//     * Determine if the ID is valid
//     * @param userId Input the id of the
//     * @return Returns a valid determination
//     */
//    public boolean isNumber(String userId) {
//        for (int i = 0; i < userId.length(); i++) {
//            if (!Character.isDigit(userId.charAt(i))) {
//                return false;
//            }
//        }
//        return userId.length() == 6;
//    }

    /**
     * Display information about winner
     */
//    private void winner() {
//        if (accessCurrentCompetition().entriesNum() == 0) {
//            System.out.println("The current competition has no entries yet!");
//            return;
//        }
//
//        System.out.println("Competition ID: "
//                + accessCurrentCompetition().getId()
//                + ", Competition Name: "
//                + accessCurrentCompetition().getName()
//                + ", Type: " +
//                accessCurrentCompetition().getType());
//
//        if (accessCurrentCompetition().getType().equals("LuckyNumbersCompetition")) {
//            luckyNumber = AutoNumbersEntry.createNumbers
//                    (accessCurrentCompetition().getId());
//        } else {
//            luckyNumber = AutoNumbersEntry.createNumbers();
//        }
//        luckyNumberInfo();
//    }

//    /**
//     * Information of lucky number
//     */
//    public void luckyNumberInfo(){
//        String luckyNumberString = "Lucky Numbers:";
//        for (int i : luckyNumber) {
//            luckyNumberString = luckyNumberString + String.format("%3d", i);
//        }
//        System.out.println(luckyNumberString + " [Auto]");
//        accessCurrentCompetition().findWinners(luckyNumber);
//        whetherActive = false;
//    }

    /**
     * Transmit information about type
     * @return return type
     */
    public String getMode() {
        return this.mode;
    }

    /**
     * Transmit the current competition
     * @return return the current competition
     */
    public Competition accessCurrentCompetition(){
        return userCompetition.get(userCompetition.size() - 1);
    }

    public void askSave(Scanner keyboard){
        System.out.println("Save competitions to file? (Y/N)?");
        String saveString = keyboard.nextLine().toUpperCase();
        if(saveString.equals("Y")){
            System.out.println("File name:");
            String fileName = keyboard.nextLine();
            try {
                ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(fileName));
                writer.writeObject(userCompetition);
            }catch(FileNotFoundException e){

            }catch(IOException e){

            }
            System.out.println("Competitions have been saved to file.");
            myDataProvider.updateBillFile();
            System.out.println("The bill file has also been automatically updated.");
        }else if (saveString.equals("N")){
            return;
        }else{
            System.out.println("Invalid input.");
            askSave(keyboard);
        }
    }
}
