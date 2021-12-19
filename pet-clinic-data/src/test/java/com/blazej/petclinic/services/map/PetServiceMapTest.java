package com.blazej.petclinic.services.map;

import com.blazej.petclinic.model.Pet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceMapTest {

    public static final Long PET_ID = 1L;
    public static final Pet PET = Pet.builder().id(PET_ID).build();
    PetServiceMap petServiceMap;

    @BeforeEach
    void setUp() {
        petServiceMap = new PetServiceMap();
        petServiceMap.save(PET);
    }

    @Test
    void findAll() {
        Set<Pet> pets = petServiceMap.findAll();

        assertEquals(1L, pets.size());
    }

    @Test
    void findById() {
        //given
        Long expectedId = 1L;
        //when
        Pet foundPet = petServiceMap.findById(expectedId);

        //then
        assertNotNull(foundPet);
    }

    @Test
    void findByIdNotFound() {
        //given
        Long shouldNotExistedId = 2L;
        //when
        Pet shouldNotExistedPet = petServiceMap.findById(shouldNotExistedId);
        //then
        assertNull(shouldNotExistedPet);

    }

    @Test
    void saveExistedId() {
        //given
        Long savedPetId = 2L;
        Long expectedPetSetSize = 2L;
        Pet savedPet = Pet.builder().id(savedPetId).build();
        //when
        petServiceMap.save(savedPet);
        //then
        int actualPetSetSize = petServiceMap.findAll().size();
        assertEquals(expectedPetSetSize, actualPetSetSize);
        assertNotNull(petServiceMap.findById(savedPetId));

    }

    @Test
    void saveNoId() {
        //given
        Long expectedPetSetSize = 2L;
        Pet pet = Pet.builder().build();
        //when
        Pet savedPet = petServiceMap.save(pet);
        //then
        int actualPetSetSize = petServiceMap.findAll().size();
        assertEquals(expectedPetSetSize, actualPetSetSize);
        assertTrue(petServiceMap.findAll().contains(savedPet));
        petServiceMap.findAll()
                .stream()
                .map(Pet::getId)
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void delete() {
        //given
        int expectedPetSetSize = 0;
        //when
        petServiceMap.delete(PET);
        //then
        assertNull(petServiceMap.findById(1L));
        assertEquals(expectedPetSetSize, petServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        //given
        int expectedPetSetSize = 0;
        //when
        petServiceMap.deleteById(PET_ID);
        //then
        assertEquals(expectedPetSetSize, petServiceMap.findAll().size());
    }
}