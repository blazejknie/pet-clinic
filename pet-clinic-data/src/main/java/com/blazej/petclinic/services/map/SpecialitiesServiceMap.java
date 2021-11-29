package com.blazej.petclinic.services.map;

import com.blazej.petclinic.model.Specialty;
import com.blazej.petclinic.services.SpecialityService;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class SpecialitiesServiceMap extends AbstractMapService<Specialty, Long> implements SpecialityService {

    @Override
    public Set<Specialty> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Specialty specialty) {
        super.delete(specialty);
    }

    @Override
    public Specialty save(Specialty specialty) {
        return super.save(specialty);
    }

    @Override
    public Specialty findById(Long id) {
        return super.findById(id);
    }
}
