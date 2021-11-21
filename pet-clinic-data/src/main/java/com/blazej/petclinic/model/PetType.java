package com.blazej.petclinic.model;

public class PetType extends BaseEntity {
    private String name;

    public PetType(String name) {
        this.name = name;
    }

    public PetType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PetType{" +
                "name='" + name + '\'' +
                '}';
    }
}
