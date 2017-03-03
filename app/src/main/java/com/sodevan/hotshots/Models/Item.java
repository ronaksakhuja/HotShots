package com.sodevan.hotshots.Models;

/**
 * Created by ronaksakhuja on 03/03/17.
 */

public class Item {
    String Sno;
    String text;
    String people;
    String screenshot;
    String timestamp;

    public Item(String sno, String text, String people, String screenshot, String timestamp) {
        Sno = sno;
        this.text = text;
        this.people = people;
        this.screenshot = screenshot;
        this.timestamp = timestamp;
    }


    public String getSno() {

        return Sno;
    }

    public void setSno(String sno) {
        Sno = sno;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
