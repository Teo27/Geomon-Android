package com.example.teo.myapplication.Models;

/**
 * Created by Teo on 2016-06-05.
 */
public class LocalGeomonModel {
    int Id;
    String Name;
    int Rarity;

    public LocalGeomonModel(int id, String name, int rarity) {
        this.Id = id;
        this.Name = name;
        this.Rarity = rarity;
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
    }

    public int getRarity() {
        return this.Rarity;
    }

    public void setRarity(int rarity) {
    }
}
