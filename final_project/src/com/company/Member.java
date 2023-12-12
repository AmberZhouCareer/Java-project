package com.company;

public class Member {
    private String memberID;
    private String name;
    private String email;

    public Member(){

    }

    public Member(String memberID, String name, String email) {
        this.memberID = memberID;
        this.name = name;
        this.email = email;
    }

    public Member(Member otherMember){
        this.memberID = otherMember.memberID;
        this.name = otherMember.name;
        this.email = otherMember.email;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }
}
