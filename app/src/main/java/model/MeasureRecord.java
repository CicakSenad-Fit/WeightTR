package model;

import java.util.Date;

/**
 * Created by Sicha on 4/5/2015.
 */
public class MeasureRecord {
    private int id;
    private Date date;
    private float value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
