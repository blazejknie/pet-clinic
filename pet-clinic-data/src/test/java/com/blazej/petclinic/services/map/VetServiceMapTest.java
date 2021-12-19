package com.blazej.petclinic.services.map;

import com.blazej.petclinic.model.Pet;
import com.blazej.petclinic.model.Vet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetServiceMapTest {

    public static final Long VET_ID = 1L;
    public static final Vet VET = Vet.builder().id(VET_ID).build();
    VetServiceMap vetServiceMap;

    @BeforeEach
    void setUp() {
        vetServiceMap = new VetServiceMap(new SpecialitiesServiceMap());
        vetServiceMap.save(VET);
    }

    @Test
    void findAll() {
        //given
        int expectedSize = 1;
        //when
        Set<Vet> vets = vetServiceMap.findAll();
        int actualSize = vets.size();
        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findById() {
        //given
        Long expectedId = 1L;
        //when
        Vet foundVet = vetServiceMap.findById(expectedId);
        //then
        assertNotNull(foundVet);
    }

    @Test
    void findByIdNotFound() {
        //given
        Long shouldNotExistedId = 2L;
        //when
        Vet shouldNotExistedVet = vetServiceMap.findById(shouldNotExistedId);
        //then
        assertNull(shouldNotExistedVet);
    }

    @Test
    void saveExistedId() {
        //given
        Long savedVetId = 2L;
        Long expectedVetSetSize = 2L;
        Vet vet = Vet.builder().id(savedVetId).build();
        //when
        vetServiceMap.save(vet);
        int actualVetSetSize = vetServiceMap.findAll().size();
        //then
        assertEquals(expectedVetSetSize, actualVetSetSize);
        assertNotNull(vetServiceMap.findById(savedVetId));
    }

    @Test
    void saveNoId() {
        //given
        Long savedVetId = 2L;
        Long expectedVetSetSize = 2L;
        Vet vet = Vet.builder().id(savedVetId).build();
        //when
        vetServiceMap.save(vet);
        int actualVetSetSize = vetServiceMap.findAll().size();
        //then
        assertEquals(expectedVetSetSize, actualVetSetSize);
        assertTrue(vetServiceMap.findAll().contains(vet));
        vetServiceMap.findAll()
                .stream()
                .map(Vet::getId)
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void delete() {
        //given
        int expectedVetSetSize = 0;
        //when
        vetServiceMap.delete(VET);
        //then
        assertNull(vetServiceMap.findById(1L));
        assertEquals(expectedVetSetSize, vetServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        //given
        int expectedVetSetSize = 0;
        //when
        vetServiceMap.deleteById(1L);
        //then
        assertEquals(expectedVetSetSize, vetServiceMap.findAll().size());
    }
}