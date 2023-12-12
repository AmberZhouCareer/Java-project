package com.company;

public class Bill {
    private String billID;
    private String memberID;
    private double money;
    private boolean whetherUsed;

    public Bill(){

    }

    public Bill(String billID, String memberID, double money, boolean whetherUsed){
        this.billID = billID;
        this.memberID = memberID;
        this.money = money;
        this.whetherUsed = whetherUsed;
    }

    public Bill(Bill otherBill){
        this.billID = otherBill.billID;
        this.memberID = otherBill.memberID;
        this.money = otherBill.money;
        this.whetherUsed = otherBill.whetherUsed;
    }

    public String getBillID() {
        return billID;
    }

    public String getMemberID(){
        return memberID;
    }

    public boolean isWhetherUsed() {
        return whetherUsed;
    }

    public void setWhetherUsed(boolean whetherUsed) {
        this.whetherUsed = whetherUsed;
    }

    public double getMoney(){
        return money;
    }

//    public String toString(){
//        return "BillID: " + billID + " MemberID:" + memberID + " Money: " + money +
//                " Used: " + whetherUsed;
//    };
    public String csvString(){
        return billID + "," + memberID + "," + money + "," + whetherUsed+"\n";
    }
}
