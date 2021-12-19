package com.blazej.petclinic.services.map;

import com.blazej.petclinic.model.Owner;
import com.blazej.petclinic.model.Pet;
import com.blazej.petclinic.model.PetType;
import com.blazej.petclinic.model.Visit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VisitServiceMapTest {

    public static final Long VISIT_ID = 1L;
    public static final Visit VISIT = Visit.builder().id(VISIT_ID).build();
    public static final Owner OWNER = Owner.builder().build();
    public static final PetType PET_TYPE = PetType.builder().name("Dog").build();
    public static final Pet PET = Pet.builder().build();
    VisitServiceMap visitServiceMap;
    OwnerServiceMap ownerServiceMap;
    PetServiceMap petServiceMap;
    PetTypeServiceMap petTypeServiceMap;


    @BeforeEach
    void setUp() {
        visitServiceMap = new VisitServiceMap();
        petServiceMap = new PetServiceMap();
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), petServiceMap);
        petTypeServiceMap = new PetTypeServiceMap();

        petTypeServiceMap.save(PET_TYPE);
        PET.setPetType(PET_TYPE);
        petServiceMap.save(PET);
        OWNER.addPet(PET);
        ownerServiceMap.save(OWNER);
        VISIT.setPet(PET);
        visitServiceMap.save(VISIT);
    }

    @Test
    void findAll() {
        //given
        Long expectedVisitSize = 1L;
        //when
        int actualVisitsSize = visitServiceMap.findAll().size();
        //then
        assertEquals(expectedVisitSize, actualVisitsSize);
    }

    @Test
    void findById() {
        //given
        Long expectedVisitId = 1L;
        //when
        Visit visit = visitServiceMap.findById(1L);
        Long actualVisitId = visit.getId();
        //then
        assertEquals(expectedVisitId, actualVisitId);
    }

    @Test
    void findByIdNotFound() {
        //given
        Long shouldNotExistedId = 2L;
        //when
        Visit shouldNotExistedVisit = visitServiceMap.findById(shouldNotExistedId);
        //then
        assertNull(shouldNotExistedVisit);
    }

    @Test
    void saveExistedIdAndPet() {
        //given
        Long savedVisitId = 2L;
        Long expectedPetSetSize = 2L;
        Visit savedVisit = Visit.builder().id(savedVisitId).build();
        savedVisit.setPet(PET);
        //when
        visitServiceMap.save(savedVisit);
        int actualVisitSize = visitServiceMap.findAll().size();
        //then
        assertEquals(expectedPetSetSize, actualVisitSize);
        assertNotNull(visitServiceMap.findById(savedVisitId));
    }

    @Test
    void saveExistedPetNoId() {
        //given
        Long expectedPetSetSize = 2L;
        Visit visit = Visit.builder().build();
        visit.setPet(PET);
        //when
        visitServiceMap.save(visit);
        int actualVisitSize = visitServiceMap.findAll().size();
        //then
        assertEquals(expectedPetSetSize, actualVisitSize);
        assertTrue(visitServiceMap.findAll().contains(visit));
        visitServiceMap.findAll()
                .stream()
                .map(Visit::getId)
                .forEach(Assertions::assertNotNull);
    }

    @Test
    void delete() {
        //given
        int expectedVisitSize = 0;
        //when
        visitServiceMap.delete(VISIT);
        //then
        assertNull(visitServiceMap.findById(VISIT_ID));
        assertEquals(expectedVisitSize, visitServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        //given
        int expectedVisitSize = 0;
        //when
        visitServiceMap.deleteById(VISIT_ID);
        //then
        assertNull(visitServiceMap.findById(VISIT_ID));
        assertEquals(expectedVisitSize, visitServiceMap.findAll().size());
    }
}