package com.example.androidworkshopapp3;

public class SinglePokeApiResponse {

    public String name;
    public String base_experience;
    public String height;
    public String weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExp() {
        return base_experience;
    }

    public void setExp(String exp) {
        this.base_experience = exp;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
