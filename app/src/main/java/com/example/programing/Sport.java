package com.example.programing;

public class Sport {

    private String name;
    private int drawable_id;
    private String content;
    private String uri_content;

    public String getUri_content() {
        return uri_content;
    }

    public void setUri_content(String uri_content) {
        this.uri_content = uri_content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
        this.content="";
        this.uri_content="";
    }

    public Sport(String name, int drawable_id,String content,String uri_content){
        this.name=name;
        this.drawable_id=drawable_id;
        this.content = content;
        this.uri_content = uri_content;
    }


}
