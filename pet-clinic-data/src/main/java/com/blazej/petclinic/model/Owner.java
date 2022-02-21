package com.blazej.petclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "telephone")
    private String telephone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    @Builder
    public Owner(Long id, String firstName, String lastName, String address,
                 String city, String telephone, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (pets != null) {
            this.pets.addAll(pets);
        }
    }

    public void addPet(Pet pet) {
        pet.setOwner(this);
        pets.add(pet);
    }

    /**
     * @return the Pet with the given name, or null if none found for this Owner
     *
     * @param petName to test
     * */
    public Pet getPet(String petName) {
        return getPet(petName, false);
    }

    /**
     * @return the Pet with the given name, or null if none found for this Owner
     *
     * @param petName to test
     * @param ignoreNew specify if new Pets should be ignored for searching
     *
    * */
    public Pet getPet(String petName, boolean ignoreNew) {
        String name = petName.toLowerCase();
        for (Pet pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName().toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }
}
