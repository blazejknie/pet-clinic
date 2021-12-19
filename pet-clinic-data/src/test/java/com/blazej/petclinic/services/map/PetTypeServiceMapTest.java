package com.blazej.petclinic.services.map;

import com.blazej.petclinic.model.PetType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetTypeServiceMapTest {
    private static final Long PET_TYPE_ID = 1L;
    private static final String PET_TYPE_NAME = "test";
    public static final PetType PET_TYPE = PetType.builder().name(PET_TYPE_NAME).id(PET_TYPE_ID).build();

    PetTypeServiceMap petTypeServiceMap;

    @BeforeEach
    void setUp() {
        petTypeServiceMap = new PetTypeServiceMap();
        petTypeServiceMap.save(PET_TYPE);
    }

    @Test
    void findAll() {
        //given
        int expectedSize = 1;
        //when
        int actualSize = petTypeServiceMap.findAll().size();
        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findById() {
        //given
        Long expectedId = PET_TYPE_ID;
        //when
        PetType foundPetType = petTypeServiceMap.findById(expectedId);
        //then
        assertNotNull(foundPetType);
    }

    @Test
    void findByIdNotFound() {
        //given
        Long notExpectedId = 2L;
        //when
        PetType shouldNotExistPetType = petTypeServiceMap.findById(notExpectedId);
        //then
        assertNull(shouldNotExistPetType);
    }

    @Test
    void deleteById() {
        //given
        int expectedSize = 0;
        //when
        petTypeServiceMap.deleteById(PET_TYPE_ID);
        int actualSize = petTypeServiceMap.findAll().size();
        //then
        assertNull(petTypeServiceMap.findById(PET_TYPE_ID));
        assertEquals(expectedSize, actualSize);

    }

    @Test
    void delete() {
        //given
        int expectedSize = 0;
        //when
        petTypeServiceMap.delete(PET_TYPE);
        int actualSize = petTypeServiceMap.findAll().size();
        //then
        assertNull(petTypeServiceMap.findById(PET_TYPE_ID));
        assertEquals(expectedSize, actualSize);

    }

    @Test
    void saveExistingId() {
        //given
        Long savedPetTypeId = 2L;
        Long expectedPetTypeSetSize = 2L;
        PetType petType = PetType.builder().id(savedPetTypeId).build();
        //when
        petTypeServiceMap.save(petType);
        //then
        int actualPetTypeSetSize = petTypeServiceMap.findAll().size();
        assertEquals(expectedPetTypeSetSize,actualPetTypeSetSize);
        assertNotNull(petTypeServiceMap.findById(2L));
    }

    @Test
    void saveNoId() {
        //given
        Long expectedPetTypeSetSize = 2L;
        PetType petType = PetType.builder().build();
        //when
        PetType savedPetType = petTypeServiceMap.save(petType);
        //then
        int actualPetTypeSetSize = petTypeServiceMap.findAll().size();
        assertEquals(expectedPetTypeSetSize,actualPetTypeSetSize);
        assertTrue(petTypeServiceMap.findAll().contains(savedPetType));
        petTypeServiceMap.findAll()
                .stream()
                .map(PetType::getId)
                .forEach(Assertions::assertNotNull);
    }
}