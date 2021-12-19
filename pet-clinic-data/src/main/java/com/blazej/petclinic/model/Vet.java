package com.blazej.petclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vets")
public class Vet extends Person {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialities", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Specialty> specialities = new HashSet<>();

    @Builder
    public Vet(Long id, String firstName, String lastName, Set<Specialty> specialities) {
        super(id, firstName, lastName);
        if (specialities != null){
            this.specialities.addAll(specialities);
        }
    }
}
