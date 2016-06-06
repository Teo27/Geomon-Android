package com.example.teo.myapplication.Models;

/**
 * Created by Teo on 2016-06-05.
 */
public class AchievementModel {
    String Description;
    int Max;
    String Name;
    int Value;

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public int getValue() {
        return this.Value;
    }

    public void setValue(int value) {
        this.Value = value;
    }

    public int getMax() {
        return this.Max;
    }

    public void setMax(int max) {
        this.Max = max;
    }
}
