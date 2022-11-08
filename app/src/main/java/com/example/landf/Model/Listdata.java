package com.example.landf.Model;

public class Listdata {


// urlidtv
public String getUrlidtv() {
    return urlidtv;
}

    public void setUrlidtv(String urlidtv) {
        this.urlidtv = urlidtv;
    }


    //title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //password

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //date

    public String getPosteddate() {
        return posteddate;
    }
    public void setPosteddate(String posteddate) {
        this.posteddate = posteddate;
    }

    //dec

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    //id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;
    public String title;
    public String desc;
    public String posteddate;
    public String password;
    public  String urlidtv;

    public Listdata() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Listdata(String id,String title, String desc, String posteddate, String password, String urlidtv) {
        this.id=id;
        this.title = title;
        this.desc = desc;
        this.posteddate = posteddate;
        this.password = password;
        this.urlidtv = urlidtv;


    }



}