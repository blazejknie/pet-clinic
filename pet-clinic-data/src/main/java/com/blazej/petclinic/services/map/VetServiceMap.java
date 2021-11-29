package com.blazej.petclinic.services.map;

import com.blazej.petclinic.model.Specialty;
import com.blazej.petclinic.model.Vet;
import com.blazej.petclinic.services.SpecialityService;
import com.blazej.petclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Vet vet) {
        super.delete(vet);
    }

    @Override
    public Vet save(Vet vet) {
        if (vet == null) {
            return null;
        }

        vet.getSpecialities().forEach(speciality -> {
            if (speciality != null) {
                if (speciality.getId() == null) {
                    Specialty savedSpecialty = specialityService.save(speciality);
                    speciality.setId(savedSpecialty.getId());
                }
            }
        });

        return super.save(vet);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}
