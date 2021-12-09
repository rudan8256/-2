package com.example.programing;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Post {
    private String writer_id;
    private String title;//게시글 제목
    private String contents;//게시글 내용
    private String p_nickname;//게시글 작성자 닉네임
    private String like; //게시글 좋아요 개수
    private Timestamp timestamp;
    private ArrayList<Comment> comments;
    private int coment_Num;

    public int getComent_Num() {
        return coment_Num;
    }

    public void setComent_Num(int coment_Num) {
        this.coment_Num = coment_Num;
    }

    private ArrayList<String> image_url;
    private String post_id;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }


    public String getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(String writer_id) {
        this.writer_id = writer_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getP_nickname() {
        return p_nickname;
    }

    public void setP_nickname(String p_nickname) {
        this.p_nickname = p_nickname;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(ArrayList<String> image_url) {
        this.image_url = image_url;
    }

    public Post() {
        //빈생성자 생성
        this.writer_id = "";
        this.title = "";
        this.contents = "";
        this.p_nickname = "";
        this.like = "";
        this.timestamp = null;
        this.comments = new ArrayList<>();
        this.image_url= new ArrayList<>();
        this.post_id = "";
        this.coment_Num=0;
    }

    public Post(String writer_id, String title, String contents, String p_nickname, String like, ArrayList<Comment> comments,  ArrayList<String> image_url, Timestamp timestamp,String post_id,int coment_Num) {
        this.writer_id = writer_id;
        this.title = title;
        this.contents = contents;
        this.p_nickname = p_nickname;
        this.like = like;
        this.timestamp = timestamp;
        this.comments = comments;
        this.image_url = image_url;
        this.post_id=post_id;
        this.coment_Num= coment_Num;
    }
}
