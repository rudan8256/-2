package com.example.programing;

public class User {
    private String idToken; // Firebase Uid (고유 토큰 정보)
    private String emailId; // 이메일 아이디
    private String password; // 비밀번호
    private String nickname;

    public  User(){
        this.emailId="";
        this.idToken="";
        this.password="";
        this.nickname="";
    }

    public User(String idToken, String emailId, String password,String nickname)
    {
        this.emailId=emailId;
        this.idToken=idToken;
        this.password=password;
        this.nickname=nickname;
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
