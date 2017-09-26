package com.example.anchalgarg.myapplication;

/**
 * Created by anchalgarg on 15/02/17.
 */

public class Blog {
    //private String desc;
    private String title;
    private String image;
   // private String username;

    public Blog()
    {

    }

    public Blog(String desc, String title, String image,String username) {
     //   this.desc = desc;
        this.title = title;
        this.image = image;
       // this.username=username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
