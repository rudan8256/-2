package com.example.programing;

public class Sport {

    private String name;
    private int drawable_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable_id() {
        return drawable_id;
    }

    public void setDrawable_id(int drawable_id) {
        this.drawable_id = drawable_id;
    }

    public Sport(){
        this.drawable_id=0;
        this.name="";
    }

    public Sport(String name, int drawable_id){
        this.name=name;
        this.drawable_id=drawable_id;
    }


}
