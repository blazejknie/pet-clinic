package com.blazej.petclinic.formatters;

import com.blazej.petclinic.model.PetType;
import com.blazej.petclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String petTypeName, Locale locale) throws ParseException {
        Set<PetType> petTypes = petTypeService.findAll();
        for (PetType petType : petTypes) {
            if (petType.getName().equals(petTypeName)) {
                return petType;
            }
        }
        throw new ParseException("Type Not Found" + petTypeName, 0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
