package com.blazej.petclinic.bootstrap;

import com.blazej.petclinic.model.Owner;
import com.blazej.petclinic.model.PetType;
import com.blazej.petclinic.model.Vet;
import com.blazej.petclinic.services.OwnerService;
import com.blazej.petclinic.services.PetTypeService;
import com.blazej.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }


    @Override
    public void run(String... args) throws Exception {

        PetType catPetType = new PetType();
        catPetType.setName("cat");
        PetType savedCatPetType = petTypeService.save(catPetType);

        PetType dogPetType = new PetType();
        catPetType.setName("dog");
        PetType savedDogPetType = petTypeService.save(dogPetType);


        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");

        ownerService.save(owner2);

        System.out.println("Loaded Owners....");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vetService.save(vet2);

        System.out.println("Loaded Vets....");
    }
}
