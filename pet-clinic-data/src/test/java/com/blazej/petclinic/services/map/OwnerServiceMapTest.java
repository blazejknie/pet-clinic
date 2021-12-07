package com.blazej.petclinic.services.map;

import com.blazej.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerServiceMap ownerServiceMap;

    Long ownerId = 1L;

    String lastName = "Smith";

    Owner owner = Owner.builder().id(ownerId).lastName(lastName).build();

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        ownerServiceMap.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();

        assertEquals(1, ownerSet.size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(owner);

        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner owner2 = Owner.builder().id(id).build();

        Owner savedOwner = ownerServiceMap.save(owner2);

        assertEquals(id, owner2.getId());
    }

    @Test
    void saveNoId() {
        Owner noIdOwner = Owner.builder().build();

        Owner savedNoIdOwner = ownerServiceMap.save(noIdOwner);

        assertNotNull(savedNoIdOwner);
        assertNotNull(savedNoIdOwner.getId());
        assertEquals(2L, savedNoIdOwner.getId());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerId);

        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void findById() {
        Owner byId = ownerServiceMap.findById(ownerId);

        assertEquals(ownerId, byId.getId());
    }

    @Test
    void findByLastName() {
        Owner byLastName = ownerServiceMap.findByLastName(lastName);

        assertNotNull(byLastName);

        assertEquals(ownerId, byLastName.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner byLastName = ownerServiceMap.findByLastName("foo");

        assertNull(byLastName);
    }
}