package com.example.programing;

public class UserToDoList {

    private String sel_date, sel_time,sel_map;

    public UserToDoList(){
        this.sel_date="";
        this.sel_map="" ;
        this.sel_time="";
    }

    public UserToDoList(String sel_date, String sel_time, String sel_map){
        this.sel_date=sel_date;
        this.sel_map=sel_map ;
        this.sel_time=sel_time;
    }


    public String getSel_date() {
        return sel_date;
    }

    public void setSel_date(String sel_date) {
        this.sel_date = sel_date;
    }

    public String getSel_time() {
        return sel_time;
    }

    public void setSel_time(String sel_time) {
        this.sel_time = sel_time;
    }

    public String getSel_map() {
        return sel_map;
    }

    public void setSel_map(String sel_map) {
        this.sel_map = sel_map;
    }
}
