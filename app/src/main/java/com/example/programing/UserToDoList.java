package com.example.programing;

import java.util.ArrayList;

public class UserToDoList {

    private String sel_date, sel_time,sel_map;
    private Sport sel_sport;
    private boolean complete;

    public boolean getComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Sport getSel_sport() {
        return sel_sport;
    }

    public void setSel_sport(Sport sel_sport) {
        this.sel_sport = sel_sport;
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

    public UserToDoList(){
        this.sel_date="";
        this.sel_map="" ;
        this.sel_time="";
        this.sel_sport=new Sport();
        this.complete = false;
    }

    public UserToDoList(String sel_date, String sel_time, String sel_map, Sport sel_sport, boolean complete){
        this.sel_date=sel_date;
        this.sel_map=sel_map ;
        this.sel_time=sel_time;
        this.sel_sport=sel_sport;
        this.complete = complete;
    }
}
