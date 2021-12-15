package com.example.programing;

import java.util.ArrayList;

public class User {
    private String idToken; // Firebase Uid (고유 토큰 정보)
    private String emailId; // 이메일 아이디
    private String password; // 비밀번호
    private String nickname;
    private ArrayList<UserToDoList> userToDoLists;

    public ArrayList<UserToDoList> getUserToDoLists() {
        return userToDoLists;
    }

    public void setUserToDoLists(ArrayList<UserToDoList> userToDoLists) {
        this.userToDoLists = userToDoLists;
    }


    public  User(){
        this.emailId="";
        this.idToken="";
        this.password="";
        this.nickname="";
        this.userToDoLists= new ArrayList<>();
    }

    public User(String idToken, String emailId, String password,String nickname, ArrayList<UserToDoList>userToDoLists)
    {
        this.emailId=emailId;
        this.idToken=idToken;
        this.password=password;
        this.nickname=nickname;
        this.userToDoLists = userToDoLists;
    }


    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
