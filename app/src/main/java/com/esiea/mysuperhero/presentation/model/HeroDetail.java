package com.esiea.mysuperhero.presentation.model;

public class HeroDetail {


    public String getResponse () {
        return response;
    }

    public void setResponse (String response) {
        this.response = response;
    }

    private String response;

    public String getId () {
        return Id;
    }

    public void setId (String id) {
        Id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getIntelligence () {
        return intelligence;
    }

    public void setIntelligence (String intelligence) {
        this.intelligence = intelligence;
    }

    public String getStrength () {
        return strength;
    }

    public void setStrength (String strength) {
        this.strength = strength;
    }

    public String getSpeed () {
        return speed;
    }

    public void setSpeed (String speed) {
        this.speed = speed;
    }

    public String getDurability () {
        return durability;
    }

    public void setDurability (String durability) {
        this.durability = durability;
    }

    public String getPower () {
        return power;
    }

    public void setPower (String power) {
        this.power = power;
    }

    public String getCombat () {
        return combat;
    }

    public void setCombat (String combat) {
        this.combat = combat;
    }

    private String Id;

    private String name;

    private String intelligence;

    private String strength;

    private String speed;

    private String durability;

    private String power;

    private String combat;

    public String getUrlIm () {
        return urlIm;
    }

    public void setUrlIm (String urlIm) {
        this.urlIm = urlIm;
    }

    private String urlIm;

}
