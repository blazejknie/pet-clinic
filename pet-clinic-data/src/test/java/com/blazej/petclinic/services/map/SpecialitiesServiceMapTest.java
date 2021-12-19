package com.blazej.petclinic.services.map;

import com.blazej.petclinic.model.Specialty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialitiesServiceMapTest {

    public static final Long SPECIALTY_ID = 1L;
    public static final Specialty SPECIALTY = Specialty.builder().id(SPECIALTY_ID).build();

    SpecialitiesServiceMap specialitiesServiceMap;

    @BeforeEach
    void setUp() {
        specialitiesServiceMap = new SpecialitiesServiceMap();
        specialitiesServiceMap.save(SPECIALTY);
    }

    @Test
    void findAll() {
        //given
        Long expectedSize = 1L;
        //when
        int actualSize = specialitiesServiceMap.findAll().size();
        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findById() {
        //given
        Long expectedId = SPECIALTY_ID;
        //when
        Specialty specialty = specialitiesServiceMap.findById(expectedId);
        //then
        assertNotNull(specialty);
    }

    @Test
    void findByIdNotFound() {
        //given
        Long expectedNotFoundId = 2L;
        //when
        Specialty specialty = specialitiesServiceMap.findById(expectedNotFoundId);
        //then
        assertNull(specialty);
    }

    @Test
    void saveExistedId() {
        //given
        Long savedSpecialityId = 2L;
        Long expectedSetSize = 2L;
        Specialty specialtyWithId = Specialty.builder().id(savedSpecialityId).build();
        //when
        Specialty save = specialitiesServiceMap.save(specialtyWithId);
        //then
        int actualSize = specialitiesServiceMap.findAll().size();
        assertEquals(expectedSetSize, actualSize);
        assertNotNull(save);
        assertEquals(savedSpecialityId, save.getId());
    }

    @Test
    void saveNoId() {
        //given
        Long expectedSetSize = 2L;
        Specialty specialtyWithoutId = Specialty.builder().build();
        //when
        Specialty savedSpecialty = specialitiesServiceMap.save(specialtyWithoutId);
        //then
        int size = specialitiesServiceMap.findAll().size();
        assertEquals(expectedSetSize, size);
        assertTrue(specialitiesServiceMap.findAll().contains(savedSpecialty));
        specialitiesServiceMap.findAll()
                .stream()
                .map(Specialty::getId)
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void deleteById() {
        //given
        int expectedPetSetSize = 0;
        //when
        specialitiesServiceMap.deleteById(SPECIALTY_ID);
        //then
        assertEquals(expectedPetSetSize, specialitiesServiceMap.findAll().size());
    }

    @Test
    void delete() {
        //given
        int expectedPetSetSize = 0;
        //when
        specialitiesServiceMap.delete(SPECIALTY);
        //then
        assertNull(specialitiesServiceMap.findById(1L));
        assertEquals(expectedPetSetSize, specialitiesServiceMap.findAll().size());
    }
}