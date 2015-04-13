package model;

import java.io.Serializable;

/**
 * Created by Sicha on 4/5/2015.
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String pass;
    private int Age;
    private boolean Sex;
    private int Height;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public int getAge() {
        return Age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getSex() {
        return Sex;
    }

    public int getHeight() {
        return Height;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setSex(boolean sex) {
        Sex = sex;
    }

    public void setHeight(int height) {
        Height = height;
    }
}
