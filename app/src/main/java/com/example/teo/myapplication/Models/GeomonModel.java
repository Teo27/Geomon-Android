package com.example.teo.myapplication.Models;

/**
 * Created by Teo on 2016-06-05.
 */
public class GeomonModel {
    String Id;
    int Max;
    String Name;
    String Picture;
    int Rarity;
    String Type;

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getRarity() {
        return this.Rarity;
    }

    public void setRarity(int rarity) {
        this.Rarity = rarity;
    }

    public String getPicture() {
        return this.Picture;
    }

    public void setPicture(String picture) {
        this.Picture = picture;
    }

    public int getMax() {
        return this.Max;
    }

    public void setMax(int max) {
        this.Max = max;
    }

    public String getType() {
        return this.Type;
    }

    public void setType(String type) {
        this.Type = type;
    }
}
