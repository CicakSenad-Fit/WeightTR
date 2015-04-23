package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sicha on 4/5/2015.
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String pass;
    private int age;
    private boolean sex;
    private int height;
    private ArrayList<MeasureRecord> measures;

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
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getSex() {
        return sex;
    }

    public int getHeight() {
        return height;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<MeasureRecord> getMeasures() {
        return measures;
    }

    public void setMeasures(ArrayList<MeasureRecord> measures) {
        this.measures = measures;
    }


    public int getMeasuresLenght()
    {
        if (measures != null)
            return measures.size();
        else
            return 0;
    }

    public void addNewMeasure(MeasureRecord newMeasure)
    {
        if (measures != null)
            measures.add(newMeasure);
        else
        {
            measures = new ArrayList<MeasureRecord>();
            measures.add(newMeasure);
        }
    }

}
